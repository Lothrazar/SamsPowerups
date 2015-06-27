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
		//TODO:https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
		spellbook = new ArrayList<ISpell>();
		
		deposit = new SpellChestDeposit(); 
		chesttransp = new SpellChestTransport(); 
		ghost = new SpellGhost();
		jump = new SpellJump();
		pearl = new SpellEnderPearl();
		phase = new SpellPhasing();
		slowfall = new SpellSlowfall();
		waterwalk = new SpellWaterwalk();
		endereye = new SpellEnderEye();
		haste = new SpellHaste();

		if(deposit.getExpCost() >= 0) spellbook.add(deposit); 
		if(chesttransp.getExpCost() >= 0)spellbook.add(chesttransp); 
		if(phase.getExpCost() >= 0)spellbook.add(phase );
		if(pearl.getExpCost() >= 0)spellbook.add(pearl );
		if(haste.getExpCost() >= 0)spellbook.add(haste);
		if(slowfall.getExpCost() >= 0)spellbook.add(slowfall );
		if(jump.getExpCost() >= 0)spellbook.add(jump );
		if(ghost.getExpCost() >= 0)spellbook.add(ghost);
		if(waterwalk.getExpCost() >= 0)spellbook.add(waterwalk );
		if(endereye.getExpCost() >= 0)spellbook.add(endereye);
	}

	public static ArrayList<ISpell> spellbook;
	public static ISpell deposit;
	public static ISpell chesttransp;
	public static ISpell ghost;
	public static ISpell jump;
	public static ISpell pearl;
	public static ISpell phase;
	public static ISpell slowfall;
	public static ISpell waterwalk;
	public static ISpell endereye;
	public static ISpell haste;
	 
	public static ISpell getDefaultSpell(EntityPlayer player)
	{
		return spellbook.get(0);
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
