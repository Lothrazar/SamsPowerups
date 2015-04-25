package com.lothrazar.samscontent.common;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerPowerups implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "PlayerPowerups";
	private final EntityPlayer player;//we get one of these powerup classes for each player
	/*
	public static final int SLEEP_WATCHER = 20;
	private static final String NBT_SLEEP_CURRENT = "samNightBed";

	public static final int AWAKE_WATCHER = 21;
	private static final String NBT_AWAKE_CURRENT = "samNightUnslept"; 
*/
	public static final int WAYPOINT_WATCHER = 20;
	private static final String NBT_WAYPOINT = "samWaypoints"; 

	public static final int TODO_WATCHER = 21;
	private static final String NBT_TODO = "samTodo"; 
	
	public PlayerPowerups(EntityPlayer player)
	{
		this.player = player;  
		this.player.getDataWatcher().addObject(WAYPOINT_WATCHER, 0);
		this.player.getDataWatcher().addObject(TODO_WATCHER, 0);
		/*
		this.player.getDataWatcher().addObject(SLEEP_WATCHER, 0);
		this.player.getDataWatcher().addObject(AWAKE_WATCHER, 0);
		*/
	}
	
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(PlayerPowerups.EXT_PROP_NAME, new PlayerPowerups(player));
	}

	public static final PlayerPowerups get(EntityPlayer player)
	{
		return (PlayerPowerups) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		// We need to create a new tag compound that will save everything for our Extended Properties
		NBTTagCompound properties = new NBTTagCompound(); 
		//properties.setInteger(NBT_SLEEP_CURRENT, this.player.getDataWatcher().getWatchableObjectInt(SLEEP_WATCHER)); 
		//properties.setInteger(NBT_AWAKE_CURRENT, this.player.getDataWatcher().getWatchableObjectInt(AWAKE_WATCHER)); 
		
		properties.setString(NBT_WAYPOINT, this.player.getDataWatcher().getWatchableObjectString(WAYPOINT_WATCHER)); 
		properties.setString(NBT_TODO,     this.player.getDataWatcher().getWatchableObjectString(TODO_WATCHER)); 
		
		compound.setTag(EXT_PROP_NAME, properties); 
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{ 
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		
		this.player.getDataWatcher().updateObject(WAYPOINT_WATCHER, properties.getString(NBT_WAYPOINT)); 
		this.player.getDataWatcher().updateObject(TODO_WATCHER,     properties.getString(NBT_TODO)); 
		
		//this.player.getDataWatcher().updateObject(SLEEP_WATCHER, properties.getInteger(NBT_SLEEP_CURRENT)); 
		//this.player.getDataWatcher().updateObject(AWAKE_WATCHER, properties.getInteger(NBT_AWAKE_CURRENT));
 	}
	public final String getStringTodo() 
	{
		return this.getStringSafe(TODO_WATCHER);
		/*
		try
		{
			//why is this giving  java.lang.Integer cannot be cast to java.lang.String
			return this.player.getDataWatcher().getWatchableObjectString(TODO_WATCHER); 
		}
		catch(ClassCastException e)
		{
			int test = this.player.getDataWatcher().getWatchableObjectInt(TODO_WATCHER);
			
			System.out.println("why is it an integer "+test);
			return "";
		}
		*/
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
			//int test = this.player.getDataWatcher().getWatchableObjectInt(WATCHER);
			
			//System.out.println("why is it an integer "+test);
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
		//return this.player.getDataWatcher().getWatchableObjectString(WAYPOINT_WATCHER); 
	}
	public final void setStringWaypoints(String waypointsCSV) 
	{
		this.player.getDataWatcher().updateObject(WAYPOINT_WATCHER, waypointsCSV);
	}
	@Override
	public void init(Entity entity, World world) 
	{ 
	}
	/*
	public final int getInt(int WATCHER) 
	{
		return this.player.getDataWatcher().getWatchableObjectInt(WATCHER);
	}
	public final void increment(int WATCHER)
	{ 
		setInt(WATCHER, getInt(WATCHER) + 1);
	}
	public final void setInt(int WATCHER, int amount)
	{
		this.player.getDataWatcher().updateObject(WATCHER, amount);
	}
	*/
	//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571567-forge-1-6-4-1-8-eventhandler-and

	public void copy(PlayerPowerups props) 
	{
		//thanks for the help https://github.com/coolAlias/Tutorial-Demo/blob/master/src/main/java/tutorial/entity/ExtendedPlayer.java

		//set in the player
		player.getDataWatcher().updateObject(WAYPOINT_WATCHER, props.getStringWaypoints());
		player.getDataWatcher().updateObject(TODO_WATCHER, props.getStringTodo());
		//set here
		this.setStringWaypoints(props.getStringWaypoints());
		this.setStringTodo(props.getStringTodo());  
	}

}