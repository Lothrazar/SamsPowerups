package com.lothrazar.samscontent;

import java.util.ArrayList;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.common.PlayerPowerups; 
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
		enderinv = new SpellEnderInventory();
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
		waterbolt = new SpellWaterBolt();
		spellbook.add(chest);
		spellbook.add(enderinv);
		spellbook.add(firebolt);
		spellbook.add(frostbolt);
		spellbook.add(ghost);
		spellbook.add(harvest);
		spellbook.add(hud);
		spellbook.add(jump );
		spellbook.add(pearl );
		spellbook.add(phase );
		spellbook.add(lightningbolt);
		spellbook.add(slowfall );
		spellbook.add(waterwalk );
		spellbook.add(waterbolt );
		//TODO:https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
	}

	public static ArrayList<ISpell> spellbook;
	public static ISpell chest;
	public static ISpell enderinv;
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
	public static ISpell waterbolt;
	
	public enum EnumSpellType {
		chest,
		enderinv,
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
		waterwalk,
		waterbolt;
		//thanks to http://digitaljoel.nerd-herders.com/2011/04/05/get-the-next-value-in-a-java-enum/
		public EnumSpellType next() 
		{
		     return this.ordinal() < EnumSpellType.values().length - 1
		         ? EnumSpellType.values()[this.ordinal() + 1]
		         : EnumSpellType.values()[0];
		}
		public EnumSpellType prev() 
		{
		     return this.ordinal() > 0
		         ? EnumSpellType.values()[this.ordinal() - 1]
		         : EnumSpellType.values()[EnumSpellType.values().length - 1];
		}
	};
	
	public enum EnumHudType {
		none,
		clock,
		compass,
		both
	};
	
	public static void cast(EnumSpellType spell, World world, EntityPlayer player,BlockPos pos)
	{
		for(ISpell sp : spellbook)
		{ 
			if(sp.getSpellType() == spell)
			{
				sp.cast(world, player, pos);
				break;
			}
		} 
	}
	
	public static void shiftUp(EntityPlayer player)
	{
		EnumSpellType current = getPlayerCurrentSpell(player);
		EnumSpellType next = current.next();
		 
		setPlayerCurrentSpell(player,next);
	}
	public static void shiftDown(EntityPlayer player)
	{ 
		EnumSpellType current = getPlayerCurrentSpell(player);
		EnumSpellType next = current.prev();
		 
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

	public static ISpell getPlayerCurrentISpell(EntityPlayer player)
	{
		EnumSpellType s = getPlayerCurrentSpell(player);
		for(ISpell sp : spellbook)
		{ 
			if(sp.getSpellType() == s)
			{
				return sp;
			}
		} 
		
		return null;
	}
	/**/
}
