package com.lothrazar.samscontent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpellRegistry
{
	private static final String KEY_PLAYER = "SPELL";
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
	
	
	public static void cast(EnumSpellType spell, World world, EntityPlayer player)
	{
		switch(spell)
		{
		case chest:
			cast_chest(world,player);
			break;
		case harvest:
			cast_harvest(world,player);
			break;
		case firebolt:
			cast_firebolt(world,player);
			break;
		case ghost:
			cast_ghost(world,player);
			break;
		case jump:
			cast_jump(world,player);
			break;
		case lightningbolt:
			cast_lightningbolt(world,player);
			break;
		case pearl:
			cast_pearl(world,player);
			break;
		case phase:
			cast_phase(world,player);
			break;
		case slowfall:
			cast_slowfall(world,player);
			break;
		case waterwalk:
			cast_waterwalk(world,player);
			break;
		default:
			System.out.println("unknown spell");
			break;
		}
	}
	
	private static void cast_waterwalk(World world, EntityPlayer player)
	{
		System.out.println("cast_waterwalk spell");
		
	}

	private static void cast_slowfall(World world, EntityPlayer player)
	{
		System.out.println("cast_slowfall spell");
		
	}

	private static void cast_phase(World world, EntityPlayer player)
	{
		System.out.println("cast_phase spell");
		
	}

	private static void cast_pearl(World world, EntityPlayer player)
	{
		System.out.println("cast_pearl spell");
		
	}

	private static void cast_lightningbolt(World world, EntityPlayer player)
	{
		System.out.println("cast_lightningbolt spell");
		
	}

	private static void cast_jump(World world, EntityPlayer player)
	{
		System.out.println("cast_jump spell");
		
	}

	private static void cast_ghost(World world, EntityPlayer player)
	{
		System.out.println("cast_ghost spell");
		
	}

	private static void cast_firebolt(World world, EntityPlayer player)
	{
		System.out.println("cast_firebolt spell");
		
	}

	private static void cast_harvest(World world, EntityPlayer player)
	{
		System.out.println("harvest spell");
		
	}

	private static void cast_chest(World world, EntityPlayer player)
	{
		System.out.println("cast_chest spell");
		
	}

	private static void cast_frostbolt(World world, EntityPlayer player)
	{

		System.out.println("cast_frostbolt spell");
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
