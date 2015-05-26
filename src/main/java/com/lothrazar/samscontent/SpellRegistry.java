package com.lothrazar.samscontent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class SpellRegistry
{
	public enum EnumSpellType{
		frostbolt,
		lightningbolt,
		firebolt,
		ghost,
		jump,
		phase,
		slowfall,
		waterwalk,
		chest,
		pearl,
		harvest
		
		
	};
	
	
	public static void cast(EnumSpellType spell, World world, EntityPlayer player)
	{
		switch(spell)
		{
		case frostbolt:
			cast_frostbolt(world,player);
			break;
		default:
			System.out.println("unknown spell");
			break;
		}
	}
	
	private static void cast_frostbolt(World world, EntityPlayer player)
	{
		
	}
	
}
