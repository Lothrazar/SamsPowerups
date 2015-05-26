package com.lothrazar.samscontent;

import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.common.PlayerPowerups;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samscontent.item.ItemChestSackEmpty;
import com.lothrazar.samscontent.item.ItemFoodGhost;
import com.lothrazar.samscontent.item.SpellHarvest;
import com.lothrazar.samscontent.item.ItemWallCompass;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class SpellRegistry
{ 
	private static int fiveSeconds = Reference.TICKS_PER_SEC * 5;//TODO : config? reference? cost?
	
	public static void setup()
	{
		harvest = new SpellHarvest();
	}
	public ISpell chest;
	public static ISpell harvest;
	
	public enum EnumSpellType{
		chest,
		harvest,
		hud,
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
	
	
	
	public enum EnumHudType{
		none,
		clock,
		compass,
		both
	};
	
	public static void cast(EnumSpellType spell, World world, EntityPlayer player,BlockPos pos)
	{
		switch(spell)
		{
		case chest:
			cast_chest(world,player,pos);
			break;
		case harvest:

System.out.println("SpellRegistry.harvest.cast");
			SpellRegistry.harvest.cast(world, player,  pos);
			
			break;
		case hud:
			cast_hud(world,player,pos);
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
			break;
		}
	}
	
	private static void cast_hud(World world, EntityPlayer player, BlockPos pos)
	{
		// TODO Auto-generated method stub
		

		PlayerPowerups props = PlayerPowerups.get(player);
		
		String hudCurr = props.getStringHUD();
		if(hudCurr == null || hudCurr=="") hudCurr = EnumHudType.none.name();
		EnumHudType hudNew;
		
		switch(EnumHudType.valueOf(hudCurr))
		{
		case none: 
			hudNew = EnumHudType.clock;
		break;

		case clock: 
			hudNew = EnumHudType.compass;
		break;

		case compass: 
			hudNew = EnumHudType.both;
		break;

		case both: 
			hudNew = EnumHudType.none;
		break;
		default:
			hudNew = EnumHudType.none;
			break;
		}
		
		props.setStringHUD(hudNew.name());
		
	}

	private static void cast_waterwalk(World world, EntityPlayer player,BlockPos pos)
	{ 
		Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.waterwalk.id,fiveSeconds,0));
		 
	}
 
	private static void cast_slowfall(World world, EntityPlayer player,BlockPos pos)
	{ 
		Util.addOrMergePotionEffect(player,new PotionEffect(PotionRegistry.slowfall.id,fiveSeconds,0));
		 
		
	}

	private static void cast_phase(World world, EntityPlayer player,BlockPos pos)
	{
		EnumFacing facing = EnumFacing.getFacingFromVector(
				(float)player.getLookVec().xCoord
				, (float)player.getLookVec().yCoord
				, (float)player.getLookVec().zCoord);

		System.out.println("TODO: bugfix phase  "+facing.getName()+"  "+Util.posToString(pos));
		
		//.getHorizontal(MathHelper.floor_double((double)(this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3);

		ItemWallCompass.wallPhase(player.worldObj,player,pos,facing);
 
	}

	private static void cast_pearl(World world, EntityPlayer player,BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityEnderPearl(world,player 	 ));
	}

	private static void cast_lightningbolt(World world, EntityPlayer player,BlockPos pos)
	{ 
		world.spawnEntityInWorld(new EntityLightningballBolt(world,player 	 ));
		
		world.spawnEntityInWorld(new EntityLightningBolt(world, pos.getX(), pos.getY(), pos.getZ()));
 	
 	
	}

	private static void cast_jump(World world, EntityPlayer player,BlockPos pos)
	{ 
		Util.addOrMergePotionEffect(player,new PotionEffect(Potion.jump.id,fiveSeconds,4));
		 
	}

	private static void cast_ghost(World world, EntityPlayer player,BlockPos pos)
	{ 

		ItemFoodGhost.setPlayerGhostMode(player,player.worldObj);
	}

	private static void cast_firebolt(World world, EntityPlayer player,BlockPos pos)
	{ 
		BlockPos up = player.getPosition().offset(player.getHorizontalFacing(), 1).up();

		world.spawnEntityInWorld(new EntitySmallFireball(world,up.getX(),up.getY(),up.getZ()
				 ,player.getLookVec().xCoord
				 ,player.getLookVec().yCoord
				 ,player.getLookVec().zCoord));

		Util.playSoundAt(player, Reference.sounds.bowtoss);
	}
 

	private static void cast_chest(World world, EntityPlayer player,BlockPos pos)
	{
		ItemChestSackEmpty.convertChestToSack(player, null, (TileEntityChest)player.worldObj.getTileEntity(pos), pos);
	}

	private static void cast_frostbolt(World world, EntityPlayer player,BlockPos pos)
	{ 
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
			next = EnumSpellType.hud;
			break;
		case hud:
			next = EnumSpellType.harvest;
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
			next = EnumSpellType.chest;//default
			break;
		}
		
		setPlayerCurrentSpell(player,next);
	}
	public static void shiftDown(EntityPlayer player)
	{ 
		EnumSpellType current = getPlayerCurrentSpell(player);
		EnumSpellType next;
		switch(current)
		{
		case chest:
			next = EnumSpellType.waterwalk;
			break;
		case hud:
			next = EnumSpellType.chest;
			break;
		case harvest:
			next = EnumSpellType.hud;
			break;
		case firebolt:
			next = EnumSpellType.harvest;
			break;
		case ghost:
			next = EnumSpellType.firebolt;
			break;
		case jump:
			next = EnumSpellType.ghost;
			break;
		case lightningbolt:
			next = EnumSpellType.jump;
			break;
		case pearl:
			next = EnumSpellType.lightningbolt;
			break;
		case phase:
			next = EnumSpellType.pearl;
			break;
		case slowfall:
			next = EnumSpellType.phase;
			break;
		case waterwalk:
			next = EnumSpellType.slowfall;
			break;
		default:
			next = EnumSpellType.chest;//default
			break;
		}
		
		setPlayerCurrentSpell(player,next);
	}
	
	private static void setPlayerCurrentSpell(EntityPlayer player,	EnumSpellType current)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		
		props.setStringSpell(current.name());
	}
	public static EnumSpellType getPlayerCurrentSpell(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		 
		if(props.getStringSpell() == "")
			setPlayerCurrentSpell(player, EnumSpellType.chest);//default

		return EnumSpellType.valueOf(props.getStringSpell());
	}
}
