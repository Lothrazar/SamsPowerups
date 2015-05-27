package com.lothrazar.samscontent;

import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.common.PlayerPowerups;
import com.lothrazar.samscontent.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samscontent.entity.projectile.EntitySnowballBolt; 
import com.lothrazar.samscontent.spell.SpellHarvest; 
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.spell.*;
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
	public static void setup()
	{
		chest = new SpellChest();
		firebolt = new SpellFirebolt();
		frostbolt = new SpellFrostbolt();
		ghost = new SpellGhost();
		harvest = new SpellHarvest();
		hud = new SpellHud();
		jump = new SpellJump();
		pearl = new SpellEnderpearl();
		phase = new SpellPhasing();
		lightningbolt = new SpellLightningbolt();
		slowfall = new SpellSlowfall();
		waterwalk = new SpellWaterwalk();
	}

	public static ISpell chest;
	public static ISpell firebolt;
	public static ISpell frostbolt;
	public static ISpell ghost;
	public static ISpell harvest;
	public static ISpell hud;
	public static ISpell jump;
	public static ISpell lightningbolt;
	public static ISpell pearl;
	public static ISpell phase;
	public static ISpell slowfall;
	public static ISpell waterwalk;
	
	public enum EnumSpellType {
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
	
	public enum EnumHudType {
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
			chest.cast(world, player, pos);
			break;
		case harvest:
			harvest.cast(world, player,  pos);
			break;
		case hud:
			hud.cast(world,player,pos);
			break;
		case firebolt:
			firebolt.cast(world,player,pos);
			break;
		case ghost:

			ghost.cast(player.worldObj,player,pos);
			
			break;
		case jump:
		  
			jump.cast(world,player,pos);
			
			break;
		case lightningbolt:
		 
			lightningbolt.cast(world, player, pos);
			 
			break;
		case pearl:

			pearl.cast(world, player, pos);
			 
			break;
		case phase:

			phase.cast(world, player, pos);
		 
			break;
		case slowfall:

			slowfall.cast(world, player, pos);
			 
			break;
		case waterwalk:

			waterwalk.cast(world, player, pos);
			 
			break;
		default:
			break;
		}
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
