package com.lothrazar.powerinventory;
 
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class PlayerExtended implements IExtendedEntityProperties
{
	private final static String EXT_PROP_NAME = "PlayerExtended"+Const.MODID;
	private final EntityPlayer player;//we get one of these powerup classes for each player
  
	private static final int WATCHER_PAGE = 21;
	private static final String NBT_PAGE = "page"; 
 
 
	public PlayerExtended(EntityPlayer player)
	{
		this.player = player;   
		this.player.getDataWatcher().addObject(WATCHER_PAGE, 0);    
	}
	
	@Override
	public void init(Entity entity, World world) 
	{ 
	}	
	
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(PlayerExtended.EXT_PROP_NAME, new PlayerExtended(player));
	}

	public static final PlayerExtended get(EntityPlayer player)
	{
		return (PlayerExtended) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) 
	{
		// We need to create a new tag compound that will save everything for our Extended Properties
		NBTTagCompound properties = new NBTTagCompound(); 
	 
		properties.setInteger(NBT_PAGE,   this.player.getDataWatcher().getWatchableObjectInt(WATCHER_PAGE));  

		compound.setTag(EXT_PROP_NAME, properties); 
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) 
	{ 
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		if(properties == null){ properties = new NBTTagCompound(); }
		
		this.player.getDataWatcher().updateObject(WATCHER_PAGE, properties.getInteger(NBT_PAGE));    
 	}

	public final int getPageCurrent()
	{
		int page = 0;
		try{
			page = this.player.getDataWatcher().getWatchableObjectInt(WATCHER_PAGE);
		}
		catch(java.lang.ClassCastException e)
		{
			System.out.println(e.getMessage());//do not quit, leave it as zero
		}
		System.out.println("getPageCurrent "+page);
		return page;
	}
 
	public final void setPageCurrent(int current) 
	{
		this.player.getDataWatcher().updateObject(WATCHER_PAGE, current);
	}   
	//http://www.minecraftforum.net/forums/mapping-and-modding/mapping-and-modding-tutorials/1571567-forge-1-6-4-1-8-eventhandler-and

	public void copy(PlayerExtended props) 
	{
		//thanks for the help https://github.com/coolAlias/Tutorial-Demo/blob/master/src/main/java/tutorial/entity/ExtendedPlayer.java

		//set in the player 
		player.getDataWatcher().updateObject(WATCHER_PAGE, props.getPageCurrent());  
		//set here  
		this.setPageCurrent(props.getPageCurrent());   
	}
}