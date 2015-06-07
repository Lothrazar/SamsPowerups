package com.lothrazar.samsmagic;

import java.util.ArrayList;

import com.lothrazar.samsmagic.PlayerPowerups; 
import com.lothrazar.samsmagic.spell.*; 

import net.minecraft.entity.player.EntityPlayer; 
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class SpellRegistry
{ 
	public static void setup()
	{
		spellbook = new ArrayList<ISpell>();
		
		deposit = new SpellChestDeposit(); 
		chesttransp = new SpellChestTransport(); 
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
		endereye = new SpellEnderEye();
		haste = new SpellHaste();

		spellbook.add(deposit); 
		spellbook.add(chesttransp); 
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
		spellbook.add(endereye);
		spellbook.add(haste);
		
		//TODO:https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
	}

	public static ArrayList<ISpell> spellbook;
	public static ISpell deposit;
	public static ISpell chesttransp;
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
	 
	public static ISpell getDefaultSpell(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		if(props.getSpellToggle() == SPELL_TOGGLE_SHOWMAIN)
			return getSpellFromType("chest");
		else
			return getSpellFromType("torch");
	}
	public static final int SPELL_TOGGLE_HIDE = 0;
	public static final int SPELL_TOGGLE_SHOWMAIN = 1;
	public static final int SPELL_TOGGLE_SHOWOTHER = 2;
	public static final int SPELL_TIMER_MAX = 20;

	public static boolean canPlayerCastAnything(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		return props.getSpellTimer() == 0;
	}
	
	//enderinv, //TODO: delete this spell, we can aleady do it with /bind n /enderchest. 
	public static void cast(ISpell spell, World world, EntityPlayer player,BlockPos pos)
	{
		if(spell == null)
		{
			return;
		}
		if(canPlayerCastAnything(player) == false)
		{
			return;
		}
	
		if(spell.canPlayerCast(world, player, pos))
		{
			spell.cast(world, player, pos);
			spell.onCastSuccess(world, player, pos);
			startSpellTimer(player);
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
	
	public static void shiftLeft(EntityPlayer player)
	{
		ISpell current = getPlayerCurrentISpell(player);

		if(current.left() != null)
			setPlayerCurrentSpell(player,current.left().getSpellID());
	}

	public static void shiftRight(EntityPlayer player)
	{ 
		ISpell current = getPlayerCurrentISpell(player);

		if(current.right() != null)
			setPlayerCurrentSpell(player,current.right().getSpellID()); 
	}
	
	private static void setPlayerCurrentSpell(EntityPlayer player,	String current)
	{
		PlayerPowerups props = PlayerPowerups.get(player);

		props.setSpellCurrent(current);
	}
	public static String getPlayerCurrentSpell(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
	 
		return props.getSpellCurrent();
	}
	public static int getSpellTimer(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		return props.getSpellTimer();
	}
	public static void startSpellTimer(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		props.setSpellTimer(SPELL_TIMER_MAX);
	}
	public static void tickSpellTimer(EntityPlayer player)
	{
		PlayerPowerups props = PlayerPowerups.get(player);
		if(props.getSpellTimer() < 0)
			props.setSpellTimer(0);
		else if(props.getSpellTimer() > 0)
			props.setSpellTimer(props.getSpellTimer() - 1);
	}

	public static ISpell getPlayerCurrentISpell(EntityPlayer player)
	{
		String s = getPlayerCurrentSpell(player);
 
		for(ISpell sp : spellbook)
		{ 
			if(sp.getSpellID().equalsIgnoreCase(s))
			{
				return sp;
			}
		} 
		//if current spell is null,default to the first one
 
		return SpellRegistry.getDefaultSpell(player);
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
