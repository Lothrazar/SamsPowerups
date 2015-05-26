package com.lothrazar.samscontent;

import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samscontent.item.ItemChestSackEmpty;
import com.lothrazar.samscontent.item.ItemFoodGhost;
import com.lothrazar.samscontent.item.ItemMagicHarvester;
import com.lothrazar.samscontent.item.ItemWallCompass;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SpellRegistry
{
	private static final String KEY_PLAYER = "SPELL";	
	private static int fiveSeconds = Reference.TICKS_PER_SEC * 5;//TODO : config? reference? cost?
	
	public enum EnumSpellType{
		chest,
		harvest,
		firebolt,
		frostbolt,
		ghost,
		jump,
		lightningbolt,
		pearl,
		phase,
		slowfall,
		waterwalk 
	};
	
	public static void cast(EnumSpellType spell, World world, EntityPlayer player,BlockPos pos)
	{
		switch(spell)
		{
		case chest:
			cast_chest(world,player,pos);
			break;
		case harvest:
			cast_harvest(world,player,pos);
			break;
		case firebolt:
			cast_firebolt(world,player,pos);
			break;
		case ghost:
			cast_ghost(world,player,pos);
			break;
		case jump:
			cast_jump(world,player,pos);
			break;
		case lightningbolt:
			cast_lightningbolt(world,player,pos);
			break;
		case pearl:
			cast_pearl(world,player,pos);
			break;
		case phase:
			cast_phase(world,player,pos);
			break;
		case slowfall:
			cast_slowfall(world,player,pos);
			break;
		case waterwalk:
			cast_waterwalk(world,player,pos);
			break;
		default:
			System.out.println("unknown spell");
			break;
		}
	}
	
	private static void cast_waterwalk(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_waterwalk spell");
		Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.waterwalk.id,fiveSeconds,0));
		 
	}
 
	private static void cast_slowfall(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_slowfall spell");	
		
		Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.slowfall.id,fiveSeconds,0));
		 
		
	}

	private static void cast_phase(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_phase spell");

	 
		EnumFacing facing = EnumFacing.getFacingFromVector(
				(float)player.getLookVec().xCoord
				, (float)player.getLookVec().yCoord
				, (float)player.getLookVec().zCoord);

		System.out.println("phase  "+facing.getName());
		
		//.getHorizontal(MathHelper.floor_double((double)(this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);

		ItemWallCompass.wallPhase(player.worldObj,player,pos,facing);
 
	}

	private static void cast_pearl(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_pearl spell");
		world.spawnEntityInWorld(new EntityEnderPearl(world,player 	 ));
	}

	private static void cast_lightningbolt(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_lightningbolt spell");
		world.spawnEntityInWorld(new EntityLightningballBolt(world,player 	 ));
		
		world.spawnEntityInWorld(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ()));
 	
 	
	}

	private static void cast_jump(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_jump spell");
		Util.addOrMergePotionEffect(player,new PotionEffect(Potion.jump.id,fiveSeconds,4));
		 
	}

	private static void cast_ghost(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_ghost spell");

		ItemFoodGhost.setPlayerGhostMode(player,player.worldObj);
	}

	private static void cast_firebolt(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_firebolt spell");
		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();

		world.spawnEntityInWorld(new EntitySmallFireball(world,up.getX(),up.getY(),up.getZ()
				 ,player.getLookVec().xCoord
				 ,player.getLookVec().yCoord
				 ,player.getLookVec().zCoord));

		Util.playSoundAt(player, Reference.sounds.bowtoss);
	}

	private static void cast_harvest(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("harvest spell");
		ItemMagicHarvester.replantField(world, player, null, pos);

	}

	private static void cast_chest(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_chest spell");
		ItemChestSackEmpty.convertChestToSack(player, null, (TileEntityChest)player.worldObj.getTileEntity(pos), pos);
		 
	}

	private static void cast_frostbolt(World world, EntityPlayer player,BlockPos pos)
	{
		System.out.println("cast_frostbolt spell");
		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();
		 
		EntitySnowballBolt snow = new EntitySnowballBolt(world,player);
		 
		world.spawnEntityInWorld(snow);
	 
		Util.playSoundAt(player, Reference.sounds.bowtoss); 
		
		Util.decrHeldStackSize(player); 
	}

	public static void shiftUp(EntityPlayer player)
	{
		EnumSpellType current = getPlayerCurrentSpell(player);
		EnumSpellType next;
		switch(current)
		{
		case chest:
			next = EnumSpellType.harvest;
			break;
		case harvest:
			next = EnumSpellType.firebolt;
			break;
		case firebolt:
			next = EnumSpellType.ghost;
			break;
		case ghost:
			next = EnumSpellType.jump;
			break;
		case jump:
			next = EnumSpellType.lightningbolt;
			break;
		case lightningbolt:
			next = EnumSpellType.pearl;
			break;
		case pearl:
			next = EnumSpellType.phase;
			break;
		case phase:
			next = EnumSpellType.slowfall;
			break;
		case slowfall:
			next = EnumSpellType.waterwalk;
			break;
		case waterwalk:
			next = EnumSpellType.chest;
			break;
		default:
			System.out.println("unknown spell");
			next = EnumSpellType.chest;//default
			break;
		}
		
		setPlayerCurrentSpell(player,next);
	}
	
	private static void setPlayerCurrentSpell(EntityPlayer player,	EnumSpellType current)
	{
		System.out.println("setPlayerCurrentSpell   "+current.name());
		player.getEntityData().setString(KEY_PLAYER, current.name());
	}
	public static EnumSpellType getPlayerCurrentSpell(EntityPlayer player)
	{
		if(player.getEntityData().getString(KEY_PLAYER) == "")
			setPlayerCurrentSpell(player, EnumSpellType.chest);//default
		return EnumSpellType.valueOf(player.getEntityData().getString(KEY_PLAYER));
	}
	public static void shiftDown(EntityPlayer player)
	{
		System.out.println("shiftDown");
		// TODO Auto-generated method stub
		
	}
	
}
