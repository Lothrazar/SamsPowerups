package com.lothrazar.samscontent.common;


import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerPowerups implements IExtendedEntityProperties
{
	private final static String EXT_PROP_NAME = "PlayerPowerups";
	private final EntityPlayer player;//we get one of these powerup classes for each player

	private static final int WAYPOINT_WATCHER = 20;
	private static final String NBT_WAYPOINT = "samWaypoints"; 

	private static final int TODO_WATCHER = 21;
	private static final String NBT_TODO = "samTodo"; 
	
	private static final int SPELLMAIN_WATCHER = 22;
	private static final String NBT_SPELLMAIN = "samSpell"; 
	
	private static final int SPELLOTHER_WATCHER = 23;
	private static final String NBT_SPELLOTHER = "samSpellOther"; 
 
	private static final int SPELLTOG_WATCHER = 24;
	private static final String NBT_SPELLTOG = "samSpellToggle"; 
 
	public PlayerPowerups(EntityPlayer player)
	{
		this.player = player;  
		this.player.getDataWatcher().addObject(WAYPOINT_WATCHER, 0);
		this.player.getDataWatcher().addObject(TODO_WATCHER, 0); 
		this.player.getDataWatcher().addObject(SPELLMAIN_WATCHER, 0);  
		this.player.getDataWatcher().addObject(SPELLOTHER_WATCHER, 0);  
		this.player.getDataWatcher().addObject(SPELLTOG_WATCHER, 0); 
	}
	
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(PlayerPowerups.EXT_PROP_NAME, new PlayerPowerups(player));
	}

	public static final PlayerPowerups get(EntityPlayer player)
	{
		return (PlayerPowerups) player.getExtendedProperties(EXT_PROP_NAME);
	}

	public static final int SPELL_TOGGLE_HIDE = 0;
	public static final int SPELL_TOGGLE_SHOWMAIN = 1;
	public static final int SPELL_TOGGLE_SHOWOTHER = 2;

	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		// We need to create a new tag compound that will save everything for our Extended Properties
		NBTTagCompound properties = new NBTTagCompound(); 
	
		properties.setString(NBT_WAYPOINT,   this.getStringSafe(WAYPOINT_WATCHER)); 
		properties.setString(NBT_TODO,       this.getStringSafe(TODO_WATCHER)); 
		properties.setString(NBT_SPELLMAIN,  this.getStringSafe(SPELLMAIN_WATCHER)); 
		properties.setString(NBT_SPELLOTHER, this.getStringSafe(SPELLOTHER_WATCHER));  
		properties.setInteger(NBT_SPELLTOG,  this.player.getDataWatcher().getWatchableObjectInt(SPELLTOG_WATCHER) ); 
 	 
		compound.setTag(EXT_PROP_NAME, properties); 
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{ 
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		if(properties == null){ properties = new NBTTagCompound(); }
		
		this.player.getDataWatcher().updateObject(WAYPOINT_WATCHER, properties.getString(NBT_WAYPOINT)); 
		this.player.getDataWatcher().updateObject(TODO_WATCHER,     properties.getString(NBT_TODO)); 
		this.player.getDataWatcher().updateObject(SPELLMAIN_WATCHER,    properties.getString(NBT_SPELLMAIN));  
		this.player.getDataWatcher().updateObject(SPELLTOG_WATCHER,    properties.getInteger(NBT_SPELLTOG));   
 	}

	private final String getSpellMain() 
	{
		return this.getStringSafe(SPELLMAIN_WATCHER);
	}
	private final void setSpellMain(String s) 
	{
		this.player.getDataWatcher().updateObject(SPELLMAIN_WATCHER, s);
	}
	private final String getSpellOther() 
	{
		return this.getStringSafe(SPELLOTHER_WATCHER);
	}
	private final void setSpellOther(String s) 
	{
		this.player.getDataWatcher().updateObject(SPELLOTHER_WATCHER, s);
	}
	public final String getSpellCurrent()
	{
		int current = this.getSpellToggle();
		String spell = getSpellMain();
		switch(current)
		{
		case SPELL_TOGGLE_SHOWMAIN:
			System.out.println("getSpellCurrent "+current+" "+getSpellMain()); 
			spell = getSpellMain();
			
			if(spell == null || spell.isEmpty())
				setSpellOther(SpellRegistry.chest.getSpellID());
			
			return getSpellMain();
			//break;
		case SPELL_TOGGLE_SHOWOTHER:
			spell = getSpellOther();
			
			if(spell == null || spell.isEmpty())
				setSpellOther(SpellRegistry.torch.getSpellID());

			System.out.println("getSpellCurrent "+current+" "+getSpellOther());
			return getSpellOther();
			//break;
		}
		return null;
	}
	public final void setSpellCurrent(String spell)
	{
		int current = this.getSpellToggle();
		switch(current)
		{
		case SPELL_TOGGLE_SHOWMAIN:
			this.setSpellMain(spell);
			break;
		case SPELL_TOGGLE_SHOWOTHER:
			this.setSpellOther(spell);
			break;
		}
	}
	public final String getStringTodo() 
	{
		return this.getStringSafe(TODO_WATCHER);
	}
	public String getStringSafe(int WATCHER)
	{
		//we used to get these exceptions when our "copy" function wanst in here saving us to persist data on player death.
		//doesnt seem to happen anymore, but keeing the try catch because it couldn't hurt.
		try
		{
			//why is this giving  java.lang.Integer cannot be cast to java.lang.String
			return this.player.getDataWatcher().getWatchableObjectString(WATCHER); 
		}
		catch(ClassCastException e)
		{
			return "";
		}
	}
 
	public final void setStringTodo(String todo) 
	{
		this.player.getDataWatcher().updateObject(TODO_WATCHER, todo);
	}
	public final String getStringWaypoints() 
	{
		return this.getStringSafe(WAYPOINT_WATCHER);
	}
	public final void setWaypoints(String waypointsCSV) 
	{
		this.player.getDataWatcher().updateObject(WAYPOINT_WATCHER, waypointsCSV);
	}
	@Override
	public void init(Entity entity, World world) 
	{ 
	}
	
	public final void setSpellToggle(int current) 
	{
		int old = getSpellToggle();
		this.player.getDataWatcher().updateObject(SPELLTOG_WATCHER, current);
	}
	public final int getSpellToggle() 
	{
		return this.player.getDataWatcher().getWatchableObjectInt(SPELLTOG_WATCHER);
	}
	public final int getSpellToggleNext() 
	{
		int current = getSpellToggle() ;

		switch(current)
		{
		case SPELL_TOGGLE_HIDE:
			return SPELL_TOGGLE_SHOWMAIN;
		case SPELL_TOGGLE_SHOWMAIN:
			return SPELL_TOGGLE_SHOWOTHER;
		default:
		case SPELL_TOGGLE_SHOWOTHER:
			return SPELL_TOGGLE_HIDE;
		}
	} 
	//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571567-forge-1-6-4-1-8-eventhandler-and

	public void copy(PlayerPowerups props) 
	{
		//thanks for the help https://github.com/coolAlias/Tutorial-Demo/blob/master/src/main/java/tutorial/entity/ExtendedPlayer.java

		//set in the player
		player.getDataWatcher().updateObject(WAYPOINT_WATCHER, props.getStringWaypoints());
		player.getDataWatcher().updateObject(TODO_WATCHER, props.getStringTodo());
		player.getDataWatcher().updateObject(SPELLMAIN_WATCHER, props.getSpellMain()); 
		player.getDataWatcher().updateObject(SPELLOTHER_WATCHER, props.getSpellOther()); 
		player.getDataWatcher().updateObject(SPELLTOG_WATCHER, props.getSpellToggle()); 
		//set here
		this.setWaypoints(props.getStringWaypoints());
		this.setStringTodo(props.getStringTodo());  
		this.setSpellMain(props.getSpellMain());   
		this.setSpellOther(props.getSpellOther());   
		this.setSpellToggle(props.getSpellToggle());   
	}

}