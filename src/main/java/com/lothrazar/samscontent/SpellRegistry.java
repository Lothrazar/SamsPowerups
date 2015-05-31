package com.lothrazar.samscontent;

import java.util.ArrayList;
import com.lothrazar.samscontent.common.PlayerPowerups; 
import com.lothrazar.samscontent.spell.ISpell;
import com.lothrazar.samscontent.spell.SpellHarvest; 
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.spell.*;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer; 
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
		spellbook = new ArrayList<ISpell>();
		
		chest = new SpellChest();
		//enderinv = new SpellEnderInventory();
		firebolt = new SpellFirebolt();
		frostbolt = new SpellFrostbolt();
		ghost = new SpellGhost();
		harvest = new SpellHarvest(); 
		jump = new SpellJump();
		pearl = new SpellEnderPearl();
		phase = new SpellPhasing();
		lightningbolt = new SpellLightningbolt();
		slowfall = new SpellSlowfall();
		waterwalk = new SpellWaterwalk();
		waterbolt = new SpellWaterBolt();
		soulstone = new SpellSoulstone();
		

		torch = new SpellTorchBolt();
		//heart = new SpellHeart();
		endereye = new SpellEnderEye();
		haste = new SpellHaste();
		 
		spellbook.add(chest);
		//spellbook.add(enderinv);
		spellbook.add(firebolt);
		spellbook.add(frostbolt);
		spellbook.add(ghost);
		spellbook.add(harvest); 
		spellbook.add(jump );
		spellbook.add(pearl );
		spellbook.add(phase );
		spellbook.add(lightningbolt);
		spellbook.add(slowfall );
		spellbook.add(waterwalk );
		spellbook.add(waterbolt );
		spellbook.add(soulstone);
		spellbook.add(torch);
		//spellbook.add(heart);
		spellbook.add(endereye);
		spellbook.add(haste);
		//TODO:https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
	}

	public static ArrayList<ISpell> spellbook;
	public static ISpell chest;
	public static ISpell firebolt;
	public static ISpell frostbolt;
	public static ISpell ghost;
	public static ISpell harvest; 
	public static ISpell jump;
	public static ISpell lightningbolt;
	public static ISpell pearl;
	public static ISpell phase;
	public static ISpell slowfall;//10
	public static ISpell waterwalk;
	public static ISpell waterbolt;
	public static ISpell soulstone;
	public static ISpell torch;
	public static ISpell endereye;
	public static ISpell haste;//16
	 
	public static ISpell getDefaultSpell()
	{
		return getSpellFromType("chest");
	}

	//enderinv, //TODO: delete this spell, we can aleady do it with /bind n /enderchest. 
	public static void cast(ISpell spell, World world, EntityPlayer player,BlockPos pos)
	{
		if(spell == null){return;}
		if(spell.canPlayerCast(player))
		{
			spell.cast(world, player, pos);
			spell.onCastSuccess(world, player, pos);
		}
		else
		{
			spell.onCastFailure(world, player, pos);
		}
	}
	public static void cast(String spell_id, World world, EntityPlayer player,BlockPos pos)
	{
		ISpell sp = SpellRegistry.getSpellFromType(spell_id); 
		cast(sp,world,player,pos);
	}
	
	public static void shiftUp(EntityPlayer player)
	{
		ISpell current = getPlayerCurrentISpell(player);
		
		if(current.left() != null)
			setPlayerCurrentSpell(player,current.left().getSpellID());
	}

	public static void shiftDown(EntityPlayer player)
	{ 
		ISpell current = getPlayerCurrentISpell(player);

		if(current.right() != null)
			setPlayerCurrentSpell(player,current.right().getSpellID());
	}
	
	private static void setPlayerCurrentSpell(EntityPlayer player,	String current)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		
		props.setStringSpell(current);
	}
	public static String getPlayerCurrentSpell(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
	 
		return props.getStringSpell();
	}

	public static ISpell getPlayerCurrentISpell(EntityPlayer player)
	{
		String s = getPlayerCurrentSpell(player);

		for(ISpell sp : spellbook)
		{ 
			if(sp.getSpellID() == s)
			{
				return sp;
			}
		} 
		//if current spell is null,default to the first one
		return SpellRegistry.getDefaultSpell();
	}
 
	public static ISpell getSpellFromType(String next)
	{
		if(next == null){return null;}
		for(ISpell sp : spellbook)
		{ 
			if(sp.getSpellID() == next)
			{
				return sp;
			}
		} 
		
		return null;
	}
}
