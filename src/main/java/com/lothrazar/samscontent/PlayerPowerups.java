package com.lothrazar.samscontent;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerPowerups implements IExtendedEntityProperties
{
	public final static String EXT_PROP_NAME = "PlayerPowerups";
	private final EntityPlayer player;//we get one of these powerup classes for each player
	
	//custom variables are all listed here
//	private int currentFlyTicks = 0;
	//private int currentHeartApplesEaten = 0;
	public static final int FLY_WATCHER = 20;
	private static final String NBT_FLY_CURRENT = "currentFlyTicks";

	public PlayerPowerups(EntityPlayer player)
	{
		this.player = player; 
		int startFlyTicks = 0;
		this.player.getDataWatcher().addObject(FLY_WATCHER, startFlyTicks);
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
		properties.setInteger(NBT_FLY_CURRENT, this.player.getDataWatcher().getWatchableObjectInt(FLY_WATCHER)); 
 
		compound.setTag(EXT_PROP_NAME, properties); 
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{ 
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		// Get our data from the custom tag compound

		this.player.getDataWatcher().updateObject(FLY_WATCHER, properties.getInteger(NBT_FLY_CURRENT));

		// Just so you know it's working, add this line: 
	}

	@Override
	public void init(Entity entity, World world) 
	{ 
	}
	/*
	public final int getCurrentFly()
	{
		return this.player.getDataWatcher().getWatchableObjectInt(FLY_WATCHER);
	}
	public final void incrementCurrentFly(int amount)
	{ 
		setCurrentFly(getCurrentFly() + amount);
	}
	public final void setCurrentFly(int amount)
	{
		this.player.getDataWatcher().updateObject(FLY_WATCHER, amount);
	}
	//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571567-forge-1-6-4-1-8-eventhandler-and
*/
}
