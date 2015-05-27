package com.lothrazar.samscontent.spell;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType; 
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;

public class SpellGhost implements ISpell
{ 
	private static final String KEY_BOOLEAN = "ghost_on";
	private static final String KEY_TIMER = "ghost_timer";
	private static final String KEY_EATLOC = "ghost_location";
	private static final String KEY_EATDIM = "ghost_dim";
	private static final int GHOST_SECONDS = 10;//so 30 seconds
	@Override
	public EnumSpellType getSpellType()
	{
		return EnumSpellType.chest;
	}

	@Override
	public void cast(World world, EntityPlayer player, BlockPos pos)
	{

		setPlayerGhostMode(player,player.worldObj);

		this.onCastSuccess(world, player, pos);
	}
	private void setPlayerGhostMode(EntityPlayer player, World par2World)
	{
		if(par2World.isRemote == false)  //false means serverside
		{ 
			player.setGameType(GameType.SPECTATOR);
			 
			Util.incrementPlayerIntegerNBT(player, KEY_TIMER, GHOST_SECONDS * Reference.TICKS_PER_SEC);
			player.getEntityData().setBoolean(KEY_BOOLEAN,true);
			player.getEntityData().setString(KEY_EATLOC, Util.posToStringCSV(player.getPosition()));
			player.getEntityData().setInteger(KEY_EATDIM, player.dimension);
		}
	}
	@Override
	public boolean canPlayerCast(EntityPlayer player)
	{
		//TODO: in future, we can check if its locked/unlocked here
		
		if(Util.getExpTotal(player) < getExpCost()) return false;
		
		return true;
	}

	@Override
	public void drainExpCost(EntityPlayer player)
	{ 
		 Util.drainExp(player, getExpCost());
	}

	private int cost = 10;
	@Override
	public void setExpCost(int c)
	{
		cost = c;
	}
	@Override
	public int getExpCost()
	{
		return cost;
	}

	public static void onPlayerUpdate(LivingUpdateEvent event) 
	{
		if(event.entityLiving instanceof EntityPlayer == false){return;}//just in case
		
		EntityPlayer player = (EntityPlayer)event.entityLiving;
		
		if(player.getEntityData().getBoolean(KEY_BOOLEAN))
		{ 
			int playerGhost = player.getEntityData().getInteger(KEY_TIMER);
			
			if(playerGhost > 0)
			{
				Util.incrementPlayerIntegerNBT(player, KEY_TIMER,-1);
			}
			else  
			{
				if(player.getEntityData().getInteger(KEY_EATDIM) != player.dimension)
				{
					//if the player changed dimension while a ghost, thats not allowed
	
					player.setGameType(GameType.SURVIVAL);
					player.attackEntityFrom(DamageSource.magic, 50); 
				}
				else
				{
					// : teleport back to source
					String posCSV = player.getEntityData().getString(KEY_EATLOC); 
					String[] p = posCSV.split(",");  
				
					player.fallDistance = 0.0F;
					player.setPositionAndUpdate(Double.parseDouble(p[0]),Double.parseDouble(p[1]),Double.parseDouble(p[2]));
					player.setGameType(GameType.SURVIVAL);
				} 
				 
				player.getEntityData().setBoolean(KEY_BOOLEAN, false);//then we are done
			}  
		}
	} 
	@Override
	public void onCastSuccess(World world, EntityPlayer player, BlockPos pos)
	{

		player.swingItem();
		
		Util.spawnParticle(world, EnumParticleTypes.CRIT, pos);
		
		Util.playSoundAt(player, Reference.sounds.bowtoss);
	}

	@Override
	public void onCastFailure(World world, EntityPlayer player, BlockPos pos)
	{

		
	}

}
