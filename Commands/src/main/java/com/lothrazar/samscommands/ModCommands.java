package com.lothrazar.samscommands;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import com.lothrazar.samscommands.command.*; 

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance; 
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModCommands.MODID, useMetadata=true)
public class ModCommands
{
    public static final String MODID = "samscommands"; 
	public static final String TEXTURE_LOCATION = MODID + ":";  
	@Instance(value = MODID)
	public static ModCommands instance;
	public static Logger logger; 
	public static ConfigCommands cfg;
	
	public static SimpleNetworkWrapper network;  
	
    @EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
    {
    	cfg = new ConfigCommands(new Configuration(event.getSuggestedConfigurationFile()));

    	logger = event.getModLog();  
    	network = NetworkRegistry.INSTANCE.newSimpleChannel( MODID );     
    	network.registerMessage(MessagePotion.class, MessagePotion.class, MessagePotion.ID, Side.CLIENT);
    	  
		FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance); 
    } 
/*
    @SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{ 
		AddWaypointInfo(event); 
		AddTodoInfo(event); 
	}
    
    @SideOnly(Side.CLIENT)
	public static void AddWaypointInfo(RenderGameOverlayEvent.Text event) 
	{
		EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
	 
    	ArrayList<String> saved = CommandSimpleWaypoints.getForPlayer(Minecraft.getMinecraft().thePlayer);//.getDisplayName().getUnformattedText()

    	if(saved.size() > 0 && saved.get(0) != null)
    	{ 
    		//find what index is selected currently
    		int index = 0;
    		try
    		{
	    		index = Integer.parseInt( saved.get(0) );
    		}
    		catch(NumberFormatException e) 
    		{ 
    			return;
    		}// do nothing, its allowed to be a string
    		
    		 
    		Location loc = null;
 
        	loc = CommandSimpleWaypoints.getSingleForPlayer(p,index);
        	 
    		if(loc != null)
    		{ 
    			if(p.dimension != loc.dimension)
    			{ 
    				return;//hide it, we are in wrong dimension to display this
    			}
    			
    			double dX = p.posX - loc.X;
    			double dZ = p.posZ - loc.Z;
    			
    			int dist = MathHelper.floor_double(Math.sqrt( dX*dX + dZ*dZ));
    			int dY = MathHelper.floor_double(loc.Y - p.posY);
    			  
    			String height = " [" + dY + "]";
    			
    			String showName = dist + "m to <"+index+"> " + loc.name + height;	
			 
				event.right.add(showName); 
    		} 
    	}
	}

    @SideOnly(Side.CLIENT)
	public static void AddTodoInfo(RenderGameOverlayEvent.Text event) 
	{
		String todoCurrent = CommandTodoList.getTodoForPlayer(Minecraft.getMinecraft().thePlayer);
		
		if(todoCurrent != null && todoCurrent.trim().isEmpty() == false)
		{ 
			event.right.add(todoCurrent.trim());
		}
	}
	*/
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event)
	{
		if(cfg.cmd_searchtrade) 
			event.registerServerCommand(new CommandSearchTrades()); 
		
		if(cfg.cmd_searchitem) 
			event.registerServerCommand(new CommandSearchItem()); 
		
		if(cfg.cmd_searchspawner) 
			event.registerServerCommand(new CommandSearchSpawner()); 
		 
		if(cfg.cmd_simplewaypoint) 
			event.registerServerCommand(new CommandSimpleWaypoints()); 
		
		if(cfg.cmd_enderchest) 
			event.registerServerCommand(new CommandEnderChest()); 
		
		if(cfg.cmd_todo) 
			event.registerServerCommand(new CommandTodoList());  
		 
		if(cfg.cmd_kit)  
			event.registerServerCommand(new CommandKit()); 
  
		if(cfg.cmd_home) 
			event.registerServerCommand(new CommandWorldHome()); 
		
		if(cfg.worldhome) 
			event.registerServerCommand(new CommandHome());

		if(cfg.cmd_place_blocks) 
			event.registerServerCommand(new CommandPlaceBlocks());
	 
		if(cfg.cmd_recipe) 
			event.registerServerCommand(new CommandRecipe());

		if(cfg.cmd_uses) 
			event.registerServerCommand(new CommandUses());
  
		if(cfg.cmd_ping) 
			event.registerServerCommand(new CommandPing());
		
		//these ones are always here. no reason to disable.
	 
		event.registerServerCommand(new CommandHearts());

	}

	public static void playSoundAt(World world,BlockPos pos, String sound)
	{ 
		world.playSound(pos.getX(), pos.getY(), pos.getZ(), sound, 1.0F, 1.0F, false);
	}
	public static String getDirectionsString(EntityPlayer player, BlockPos pos  )
	{     
		int xDist,yDist,zDist;
		
		xDist = (int) player.posX - pos.getX();
		yDist = (int) player.posY - pos.getY();
		zDist = (int) player.posZ - pos.getZ();
		
		//in terms of directon copmass:
		//North is -z;  south is +z		
		//east is +x, west is -x
		
		//so for Distances: 
		
		boolean isNorth = (zDist > 0);
		boolean isSouth = (zDist < 0);
		
		boolean isWest = (xDist > 0);
		boolean isEast = (xDist < 0);

		boolean isUp   = (yDist < 0);
		boolean isDown = (zDist > 0);
		
		String xStr = "";
		String yStr = "";
		String zStr = "";

		if(isWest) xStr = Math.abs(xDist) + " west ";
		if(isEast) xStr = Math.abs(xDist) + " east ";
		
		if(isNorth) zStr = Math.abs(zDist) + " north ";
		if(isSouth) zStr = Math.abs(zDist) + " south ";

		if(isUp)   yStr = Math.abs(yDist) + " up ";
		if(isDown) yStr = Math.abs(yDist) + " down ";
		 
		return xStr +  yStr +  zStr ;
	}
	
	public static ArrayList<Item> getItemListFromCSV(String csv)
	{
		ArrayList<Item> items = new ArrayList<Item>();
		String[] ids = csv.split(","); 
		Item isItNull = null;
		Block b;
		
		for(int i = 0; i < ids.length; i++)
		{
			isItNull = Item.getByNameOrId(ids[i]);
			if(isItNull == null)  //try to get block version  
			{ 
				b = Block.getBlockFromName(ids[i]);
				if(b != null)	isItNull = Item.getItemFromBlock(b); 
			} 
			 
			if(isItNull == null)
			{
				ModCommands.logger.log(Level.WARN, "Item not found : "+ ids[i]);
			}
			else
			{
				items.add(isItNull);
			} 
		}  
		
		return items;
	}
	public static ArrayList<BlockPos> findBlocks(EntityPlayer player, Block blockHunt, int RADIUS ) 
	{        
		ArrayList<BlockPos> found = new ArrayList<BlockPos>();
		int xMin = (int) player.posX - RADIUS;
		int xMax = (int) player.posX + RADIUS;

		int yMin = (int) player.posY - RADIUS;
		int yMax = (int) player.posY + RADIUS;

		int zMin = (int) player.posZ - RADIUS;
		int zMax = (int) player.posZ + RADIUS;
		 
		int xDistance, zDistance, distance , distanceClosest = RADIUS * RADIUS;
		
		BlockPos posCurrent = null; 
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int yLoop = yMin; yLoop <= yMax; yLoop++)
			{
				for (int zLoop = zMin; zLoop <= zMax; zLoop++)
				{  
					posCurrent = new BlockPos(xLoop, yLoop, zLoop);
					if(player.worldObj.getBlockState(posCurrent).getBlock().equals(blockHunt))
					{ 
						xDistance = (int) Math.abs(xLoop - player.posX );
						zDistance = (int) Math.abs(zLoop - player.posZ );
						
						distance = (int) distanceBetweenHorizontal(player.getPosition(), posCurrent);
						
						found.add(posCurrent);
					} 
				}
			}
		}
		
		return found; 
	}
	public static void spawnParticlePacketByID(BlockPos position, int particleID)
	{
		//this. fires only on server side. so send packet for client to spawn particles and so on
		ModCommands.network.sendToAll(new MessagePotion(position, particleID));
    	
		
	}
	public static ArrayList<Block> getBlockListFromCSV(String csv)
	{
		 ArrayList<Block> blocks = new ArrayList<Block>();
		 String[] ids = csv.split(",");  
		 Block b; 
		 
		 for(String id : ids)
		 {
			 b = Block.getBlockFromName(id);
			 
			 if(b == null)
			 {
				 ModCommands.logger.log(Level.WARN, "getBlockListFromCSV : Block not found : "+id);
			 }
			 else 
			 {
				 blocks.add(b);
			 }
		 } 
		 
		 return blocks;
	}
	public static void playSoundAt(Entity player, String sound)
	{ 
		player.worldObj.playSoundAtEntity(player, sound, 1.0F, 1.0F);
	}
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) 
	{ 
		//false means switched dimensions
		System.out.println("clone player: wasdeath : "+event.wasDeath);
		
		if(event.wasDeath)
		{
			
			System.out.println(CommandTodoList.getTodoForPlayer(event.original));
			System.out.println(CommandSimpleWaypoints.getForPlayer(event.original));
			//PlayerPowerups.get(event.entityPlayer).copy(PlayerPowerups.get(event.original));
			
			
			CommandSimpleWaypoints.overwriteForPlayer(event.entityPlayer, CommandSimpleWaypoints.getForPlayer(event.original));
			CommandTodoList.setTodoForPlayer(event.entityPlayer, CommandTodoList.getTodoForPlayer(event.original));
			
			
	
			System.out.println("NEW CLONE");
			System.out.println(CommandTodoList.getTodoForPlayer(event.entityPlayer));
			System.out.println(CommandSimpleWaypoints.getForPlayer(event.entityPlayer));
		}
	}
	public static String lang(String name)
	{
		return StatCollector.translateToLocal(name);
	}
	@SubscribeEvent
 	public void onEntityConstructing(EntityConstructing event)
 	{ 
		/*
 		if (event.entity instanceof EntityPlayer && PlayerPowerups.get((EntityPlayer) event.entity) == null)
 		{ 
 			PlayerPowerups.register((EntityPlayer) event.entity);
 		} */
 	}
	public static void incrementPlayerIntegerNBT(EntityPlayer player, String prop, int inc)
	{
		int prev = player.getEntityData().getInteger(prop);
		prev += inc; 
		player.getEntityData().setInteger(prop, prev);
	}
	public static void decrHeldStackSize(EntityPlayer entityPlayer) 
	{
		decrHeldStackSize(entityPlayer,1);
	}
	public static void decrHeldStackSize(EntityPlayer entityPlayer, int by) 
	{
		if (entityPlayer.capabilities.isCreativeMode == false)
        {
			entityPlayer.inventory.decrStackSize(entityPlayer.inventory.currentItem, by);
        }
	}
	public static void setMaxHealth(EntityLivingBase living,int max)
	{	
		living.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(max);
	}
	public static void addChatMessage(String string) 
	{ 
		addChatMessage(new ChatComponentTranslation(string)); 
	}
	public static void addChatMessage(IChatComponent string) 
	{ 
		 Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(string); 
	}
	public static void addChatMessage(EntityPlayer player,String string) 
	{ 
		player.addChatMessage(new ChatComponentTranslation(string));
	}
	public static EnumFacing getPlayerFacing(EntityPlayer player) 
	{
    	int yaw = (int)player.rotationYaw;

        if (yaw<0)              //due to the yaw running a -360 to positive 360
           yaw+=360;    //not sure why it's that way

        yaw += 22;     //centers coordinates you may want to drop this line
        yaw %= 360;  //and this one if you want a strict interpretation of the zones

        int facing = yaw/45;   //  360degrees divided by 45 == 8 zones
       
		return EnumFacing.getHorizontal( facing/2 );
	}
	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
		if(pos != null)
			spawnParticle(world,type,pos.getX(),pos.getY(),pos.getZ());
    }	
 
	public static String posToString(BlockPos position) 
	{ 
		return "["+ position.getX() + ", "+position.getY()+", "+position.getZ()+"]";
	} 
	public static void teleportWallSafe(EntityLivingBase player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		moveEntityWallSafe(player, world);
	}
	public static void moveEntityWallSafe(EntityLivingBase entity, World world) 
	{
		while (entity.getEntityBoundingBox() != null && //gm 3 must have a null box because of the ghost
				!world.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox()).isEmpty())
		{
			entity.setPositionAndUpdate(entity.posX, entity.posY + 1.0D, entity.posZ);
		}
	}
	
	public static double distanceBetweenHorizontal(BlockPos start, BlockPos end)
	{
		int xDistance =  Math.abs(start.getX() - end.getX() );
		int zDistance =  Math.abs(start.getZ() - end.getZ() );
		//ye olde pythagoras
		return Math.sqrt(xDistance * xDistance + zDistance * zDistance);
	}

	public static String getCoordsOrReduced(EntityPlayer player, BlockPos pos)
	{
		boolean showCoords = !player.worldObj.getGameRules().getGameRuleBooleanValue("reducedDebugInfo");
		
		if(showCoords)
			return pos.getX() + ", " + pos.getY() + ", " + pos.getZ();
		else
			return getDirectionsString(player, pos); 
	}
	public static BlockPos getBedLocationSafe(World world, EntityPlayer player) 
	{
		 BlockPos realBedPos = null;
		
		 BlockPos coords = player.getBedLocation(0);
		  
		 if(coords != null)
		 { 
			 Block block = world.getBlockState(coords).getBlock();
			 
			 if (block.equals(Blocks.bed) || block.isBed(world, coords, player))
			 {
				 //then move over according to how/where the bed wants me to spawn
				 realBedPos = block.getBedSpawnPosition(world, coords, player); 
			 }
		 }
		 
		 return realBedPos;
	}
	public static void spawnParticle(World world, EnumParticleTypes type, double x, double y, double z)
	{ 
		//http://www.minecraftforge.net/forum/index.php?topic=9744.0
		for(int countparticles = 0; countparticles <= 10; ++countparticles)
		{
			world.spawnParticle(type, x + (world.rand.nextDouble() - 0.5D) * (double)0.8, y + world.rand.nextDouble() * (double)1.5 - (double)0.1, z + (world.rand.nextDouble() - 0.5D) * (double)0.8, 0.0D, 0.0D, 0.0D);
		} 
    }
	public static void execute(EntityPlayer player, String cmd)
	{
		MinecraftServer.getServer().getCommandManager().executeCommand(player, cmd);
	}

}
