package com.lothrazar.samscontent.event;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;  
import java.util.Calendar;
import java.util.Date;
import java.util.Random; 

import org.apache.logging.log4j.Logger; 

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.command.CommandSimpleWaypoints;
import com.lothrazar.samscontent.command.CommandTodoList;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.util.Location;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.client.Minecraft;
//import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraftforge.common.DimensionManager;  
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;  
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
 
public class HandlerScreenText
{   
	 public Date addDays(Date baseDate, int daysToAdd) 
	 {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
	}
	 
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{ 
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo == false){return;}

		if(ModLoader.configSettings.debugClearRight )
		{ 
			event.right.clear();
		}
		 //ok add left content
		event.left.add("");
		
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer; 
		
		//PlayerPowerups props = PlayerPowerups.get(player); 
	  
		World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
  
	
		addDateTimeInfo(event, world);
		  
	 	if(ModLoader.configSettings.debugSlime && player.dimension == Reference.Dimension.overworld)
	 	{ 
	    	addSlimeChunkInfo(event, player, world); 
	 	}
	 	
	 	if(ModLoader.configSettings.debugVillageInfo && world.villageCollectionObj != null)
	 	{   
			 addVillageInfo(event, player, world);	 
		 }
	 	
	 	if(ModLoader.configSettings.debugHorseInfo && player.ridingEntity != null && player.ridingEntity instanceof EntityHorse)
	 	{ 
	 		addHorseInfo(event, player);   
	 	} 
	  
	 	if(SamsUtilities.isShiftKeyDown()) //if(showGameRules)//todo config
	 	{ 
			event.left.add("");
			addGameruleInfo(event, world); 
		} 

		event.left.add("");
		addTodoCommandInfo(event, player);  

		CommandSimpleWaypoints.AddWaypointInfo(event); 
	}

	private void addTodoCommandInfo(RenderGameOverlayEvent.Text event,	EntityPlayerSP player) 
	{
		String todoCurrent = CommandTodoList.GetTodoForPlayerName(player.getName());
		
		if(todoCurrent != null && todoCurrent.isEmpty() == false)
		{
			event.left.add("TODO : "+todoCurrent); 
		}
	}

	private void addGameruleInfo(RenderGameOverlayEvent.Text event, World world) 
	{
		event.right.add("Enabled Gamerules:");
		
		GameRules rules = world.getWorldInfo().getGameRulesInstance();
		
		ArrayList<String> ruleNames = new ArrayList<String>();
		ruleNames.add(Reference.gamerule.commandBlockOutput);
		ruleNames.add(Reference.gamerule.doDaylightCycle);
		ruleNames.add(Reference.gamerule.doFireTick);
		ruleNames.add(Reference.gamerule.doMobLoot);
		ruleNames.add(Reference.gamerule.doMobSpawning);
		ruleNames.add(Reference.gamerule.doTileDrops);
		ruleNames.add(Reference.gamerule.keepInventory);
		ruleNames.add(Reference.gamerule.mobGriefing);
		ruleNames.add(Reference.gamerule.naturalRegeneration);
		//TODO: more rules missing from 1.8 addition

		String T = "[T]", F = "[F]";
		
		String name;
		for(int i = 0; i < ruleNames.size(); i++)
		{
			name = ruleNames.get(i);
			if(rules.getGameRuleBooleanValue(name))
			{ 
				event.right.add(T + " " + name); 
			}
			else
			{ 
				event.right.add(F + " " +name); 
			}
		}
	}

	private void addHorseInfo(RenderGameOverlayEvent.Text event,	EntityPlayerSP player) 
	{
		EntityHorse horse = (EntityHorse)player.ridingEntity;
		 
		//int type = horse.getHorseType();
		//type 0 is horse, type 1 is donkey, type 2 is mule
		 
		//variant 0 is white, 1 is creamy
		//2 is chestnut 3 is brown
		//4 is black, 5 is grey, 6 is dark brown
		
		//int armor = horse.getTotalArmorValue();
		
		// nope not this:float speed = horse.getAIMoveSpeed();
		double speed =  horse.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue() ;
			 
		double jump = horse.getHorseJumpStrength() ;
		//convert from scale factor to blocks
		double jumpHeight = 0;
		double gravity = 0.98;
		while (jump > 0)
		{
			jumpHeight += jump;
			jump -= 0.08;
			jump *= gravity;
		}
	
		//http://minecraft.gamepedia.com/Item_id#Horse_Variants
		String variant = "";
		 
		int var = horse.getHorseVariant();

		String spots = null;
		
		if(var >= 1024) spots = "Black Dots ";
		else if(var >= 768) spots = "White Dots";
		else if(var >= 512) spots = "White Field";
		else if(var >= 256) spots = "White Patches";
		
		while(var - 256 > 0)
		{
			var -= 256;
		}
		
		switch( var )
		{
			case 0:  variant = "White";break; 
			case 1: variant = "Creamy";break;
			case 2: variant = "Chestnut";break;
			case 3: variant = "Brown";break;
			case 4: variant = "Black";break;
			case 5: variant = "Gray";break;
			case 6: variant = "Dark Brown";break; 
		}
		
		//if its not a horse, variant wont matter
		String type = "";
		switch( horse.getHorseType())
		{
			case 0: type = variant + " Horse";break;
			case 1: type = "Donkey";break;
			case 2: type = "Mule";break;
			case 3: type = "Undead Horse";break;
			case 4: type = "Skeleton Horse";break;
		}

		if(spots != null) type += " ("+spots+")";

		event.left.add("");
		event.left.add("Riding "+type); 

		DecimalFormat df = new DecimalFormat("0.0000");
		
		event.left.add("  "+ df.format(speed) + " Speed"  ); 
		
		df = new DecimalFormat("0.0");
		
		event.left.add("  "+ df.format(jumpHeight) + " Jump"  );
	}

	private void addVillageInfo(RenderGameOverlayEvent.Text event,	EntityPlayerSP player, World world) 
	{
		int playerX = MathHelper.floor_double(player.posX);
		 int playerY = MathHelper.floor_double(player.posY);
		 int playerZ = MathHelper.floor_double(player.posZ);
		 
		 int dX,dZ;
		 
		 int range = 10;

		 Village closest = world.villageCollectionObj.getNearestVillage(player.getPosition(), range);
		 
		 if(closest != null)
		 { 
		    int doors = closest.getNumVillageDoors();
		    int villagers = closest.getNumVillagers();
		     
		    int rep = closest.getReputationForPlayer(player.getName());
  
		    event.left.add("");
		    event.left.add("Village Data");
		    event.left.add(String.format("# of Villagers: %d",villagers));
		    event.left.add(String.format("Reputation: %d",rep));
		    event.left.add(String.format("Valid Doors: %d",doors));
 
		    dX = playerX - closest.getCenter().getX();
		    dZ = playerZ - closest.getCenter().getZ();
		    
		    int dist = MathHelper.floor_double(Math.sqrt( dX*dX + dZ*dZ));

		    event.left.add(String.format("Distance from Center:  %d", dist)); 
		 }
	}

	private void addSlimeChunkInfo(RenderGameOverlayEvent.Text event,	EntityPlayerSP player, World world)
	{
		long seed =  world.getSeed();
    
		Chunk in = world.getChunkFromBlockCoords(player.getPosition());

		//formula source : http://minecraft.gamepedia.com/Slime
		Random rnd = new Random(seed +
		        (long) (in.xPosition * in.xPosition * 0x4c1906) +
		        (long) (in.xPosition * 0x5ac0db) + 
		        (long) (in.zPosition * in.zPosition) * 0x4307a7L +
		        (long) (in.zPosition * 0x5f24f) ^ 0x3ad8025f);
		
		boolean isSlimeChunk = (rnd.nextInt(10) == 0);
    
		if(isSlimeChunk)
		{
			event.left.add("Slime Chunk"); 
		}
	}
 
	private void addDateTimeInfo(RenderGameOverlayEvent.Text event, World world) 
	{
		long time = world.getWorldTime(); 
	 
		int days = MathHelper.floor_double( time / Reference.ticksPerDay);
		 
		long remainder = time % Reference.ticksPerDay;
		
		String detail = "";
	
		if(remainder < 5000) detail = "Morning";
		else if(remainder < 7000) detail = "Mid-day";//midd ay is exactly 6k, so go one on each side
		else if(remainder < 12000) detail = "Afternoon";
		else detail = "Moon Phase " + world.getMoonPhase();
	   
		// a 365 day calendar. Day Zero is January 1st of year zero?``
		 //http://stackoverflow.com/questions/8263220/calendar-set-year-issue
		//event.left.add("Day "+days +" ("+detail+")");  
		 
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
		
		Calendar c1 = Calendar.getInstance(); // TODAY AD
		c1.set(Calendar.YEAR, 0);             // August  16th,    0 AD
		c1.set(Calendar.DAY_OF_YEAR, 1);      // January  1st,    0 AD
		c1.set(Calendar.YEAR, 1000);          // January  1st, 2001 AD
		Date start = c1.getTime();     // prints the expected date
		 
		Date curr = addDays(start,days);
 
		
		event.left.add(sdf.format(curr)  + " ("+detail+")" );
	}
}
