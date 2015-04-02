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
	public static final int SLEEP_WATCHER = 20;
	private static final String NBT_SLEEP_CURRENT = "samSleeps";

	public PlayerPowerups(EntityPlayer player)
	{
		this.player = player; 
		int startFlyTicks = 0;
		this.player.getDataWatcher().addObject(SLEEP_WATCHER, startFlyTicks);
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
		properties.setInteger(NBT_SLEEP_CURRENT, this.player.getDataWatcher().getWatchableObjectInt(SLEEP_WATCHER)); 
 
		compound.setTag(EXT_PROP_NAME, properties); 
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{ 
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		// Get our data from the custom tag compound

		this.player.getDataWatcher().updateObject(SLEEP_WATCHER, properties.getInteger(NBT_SLEEP_CURRENT));

		// Just so you know it's working, uncomment/ add this line:
		//System.out.println("IEXTENDED T:currentFlyTicks " + this.player.getDataWatcher().getWatchableObjectInt(SLEEP_WATCHER)  );
	}

	@Override
	public void init(Entity entity, World world) 
	{ 
	}
	public final int getCurrentSleep()
	{
		return this.player.getDataWatcher().getWatchableObjectInt(SLEEP_WATCHER);
	}
	public final void incrementCurrentFly(int amount)
	{ 
		setCurrentSleep(getCurrentSleep() + amount);
	}
	public final void setCurrentSleep(int amount)
	{
		this.player.getDataWatcher().updateObject(SLEEP_WATCHER, amount);
	}
	//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571567-forge-1-6-4-1-8-eventhandler-and
}