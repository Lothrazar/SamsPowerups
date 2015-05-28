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
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.SpellRegistry;
import com.lothrazar.samscontent.SpellRegistry.EnumHudType;
import com.lothrazar.samscontent.SpellRegistry.EnumSpellType;
import com.lothrazar.samscontent.command.CommandSimpleWaypoints;
import com.lothrazar.samscontent.command.CommandTodoList;
import com.lothrazar.samscontent.common.PlayerPowerups;
import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.spell.ISpell;
import com.lothrazar.util.Location;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util; 
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft; 
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
 
public class DebugScreenText
{   
	 public Date addDays(Date baseDate, int daysToAdd) 
	 {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseDate);
        calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        return calendar.getTime();
	}
	 
	private static void renderItemAt(ItemStack stack, int x, int y)
	{
		int height = 16, width = 16;

		IBakedModel iBakedModel = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
		TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(iBakedModel.getTexture().getIconName());
		
		renderTexture( textureAtlasSprite, x, y);
	}
	public static void renderBlockAt(Block block, int x, int y)
	{ 
	     TextureAtlasSprite textureAtlasSprite = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(block.getDefaultState());
		    
	     renderTexture( textureAtlasSprite, x, y);
	}
	private static void renderTexture( TextureAtlasSprite textureAtlasSprite , int x, int y)
	{	
		//special thanks to http://www.minecraftforge.net/forum/index.php?topic=26613.0
		
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		Tessellator tessellator = Tessellator.getInstance();

		int height = 16, width = 16;
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV((double)(x),          (double)(y + height),  0.0, (double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMaxV());
		worldrenderer.addVertexWithUV((double)(x + width),  (double)(y + height),  0.0, (double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMaxV());
		worldrenderer.addVertexWithUV((double)(x + width),  (double)(y),           0.0, (double)textureAtlasSprite.getMaxU(), (double)textureAtlasSprite.getMinV());
		worldrenderer.addVertexWithUV((double)(x),          (double)(y),           0.0, (double)textureAtlasSprite.getMinU(), (double)textureAtlasSprite.getMinV());
		tessellator.draw();
		
	}
	@SubscribeEvent
	public void onRenderTextOverlay(RenderGameOverlayEvent.Text event)
	{  
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer; 
		
		if(Minecraft.getMinecraft().gameSettings.showDebugInfo == false)
		{
			drawSpell(event); 

		 	drawHud(player);
			return;
		}
		
		World world = Minecraft.getMinecraft().getIntegratedServer().getEntityWorld();
		
	 
		if(ModMain.cfg.reducedDebugImproved && 
				world.getGameRules().getGameRuleBooleanValue(Reference.gamerule.reducedDebugInfo) )
		{ 
			//then replace all existing text with just this
			event.right.clear();
			event.left.clear();
			
			
			
			int blockLight = world.getLightFromNeighbors(player.getPosition()) + 1;
			
			String firstLine = Util.lang("debug.biome")+"  "+world.getBiomeGenForCoords(player.getPosition()).biomeName;

			//EnumChatFormatting.GREEN + 
			String light = Util.lang("debug.light")+"  "+blockLight;
				//	+"  "+world.getLight(player.getPosition(), true)
				//	+"  "+world.getLightBrightness(player.getPosition())
					
			
			
			if(player.isSneaking()) // L for light
				firstLine = firstLine +"  "+light;
			
			//Minecraft.getMinecraft().getDebugFPS()
			
			event.left.add(firstLine);
			
			

		}
		
 
		addDateTimeInfo(event, world);
		
		  
	 	if(ModMain.cfg.debugSlime && player.dimension == Reference.Dimension.overworld)
	 	{ 
	 		addSlimeChunkInfo(event, player, world); 
	 	}
	 	
	 	if(ModMain.cfg.debugVillageInfo && world.villageCollectionObj != null)
	 	{   
	 		addVillageInfo(event, player, world);	 
		}
	 	
	 	if(ModMain.cfg.debugHorseInfo && player.ridingEntity != null && player.ridingEntity instanceof EntityHorse)
	 	{ 
	 		addHorseInfo(event, player);   
	 	} 

	
		CommandSimpleWaypoints.AddWaypointInfo(event); 
		
		addTodoCommandInfo(event, player);  

	 	if(Util.isShiftKeyDown() && ModMain.cfg.debugGameruleInfo)  
	 	{ 
			addGameruleInfo(event, world); 
		} 
	 	 
	}

	private void drawSpell(RenderGameOverlayEvent.Text event)
	{ 
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer; 
		 
		ISpell spell = SpellRegistry.getPlayerCurrentISpell(player);
 
		int x = 8, y = 2;

		boolean canAfford = (Util.getExpTotal(player) <= spell.getExpCost());
		
		if(canAfford)
			renderItemAt(new ItemStack(ItemRegistry.exp_cost_dummy),x,y);
		else
			renderItemAt(new ItemStack(ItemRegistry.exp_cost_empty_dummy),x,y);
			
		//Util.lang("key.spell.cost")
		//event.left.add("");
		//event.left.add(spell.getExpCost()+"" );
		
		
		if(spell.getIconDisplay() != null)
		{
			x = 25; 
			y = 2;
			renderItemAt(spell.getIconDisplay(),x,y);
		}
	 
	}

	private void drawHud(EntityPlayerSP player)
	{
		//TESTING OUT PLAYER COMPASS CLOCKS PELLS
	 	//System.out.println("width"+ Minecraft.getMinecraft().displayWidth);
		int xMiddle = Minecraft.getMinecraft().displayWidth/4;
		int yMiddle = Minecraft.getMinecraft().displayHeight/4;
		int yBottom = Minecraft.getMinecraft().displayHeight/2 - 32;
		int xRight = Minecraft.getMinecraft().displayWidth/2 - 32;
	 
		
		PlayerPowerups props = PlayerPowerups.get(player);
		
		String hudCurr = props.getStringHUD();
		if(hudCurr == null || hudCurr=="") hudCurr = EnumHudType.none.name();
	 
		switch(EnumHudType.valueOf(hudCurr))
		{
		case clock: 
			renderItemAt(new ItemStack(Items.clock),20,yBottom);//works at mid left
		break;

		case compass: 
			renderItemAt(new ItemStack(Items.compass),xRight,yBottom);//works at mid top//was ,16
		break;

		case both: 
			renderItemAt(new ItemStack(Items.clock),20,yBottom);//works at mid left
			renderItemAt(new ItemStack(Items.compass),xRight,yBottom);
		break;
		case none: 
		default:
			//neither
			break;
		}
	}

	private void addTodoCommandInfo(RenderGameOverlayEvent.Text event,	EntityPlayerSP player) 
	{
		String todoCurrent = CommandTodoList.GetTodoForPlayer(player);
		
		if(todoCurrent != null && todoCurrent.isEmpty() == false)
		{ 
			event.right.add(todoCurrent);
		}
	}

	private void addGameruleInfo(RenderGameOverlayEvent.Text event, World world) 
	{
		event.right.add(""); 
		
		GameRules rules = world.getWorldInfo().getGameRulesInstance();
		
		ArrayList<String> ruleNames = new ArrayList<String>();
		ruleNames.add(Reference.gamerule.commandBlockOutput);
		ruleNames.add(Reference.gamerule.doDaylightCycle);
		ruleNames.add(Reference.gamerule.doEntityDrops);
		ruleNames.add(Reference.gamerule.doFireTick);
		ruleNames.add(Reference.gamerule.doMobLoot);
		ruleNames.add(Reference.gamerule.doMobSpawning);
		ruleNames.add(Reference.gamerule.doTileDrops);
		ruleNames.add(Reference.gamerule.keepInventory);
		ruleNames.add(Reference.gamerule.mobGriefing);
		ruleNames.add(Reference.gamerule.naturalRegeneration);
		ruleNames.add(Reference.gamerule.reducedDebugInfo);
		ruleNames.add(Reference.gamerule.sendCommandFeedback);
		ruleNames.add(Reference.gamerule.showDeathMessages); 
 
		String name;
		for(int i = 0; i < ruleNames.size(); i++)
		{
			name = ruleNames.get(i);
			if(rules.getGameRuleBooleanValue(name))
			{ 
				event.right.add(EnumChatFormatting.GREEN + name); 
			}
			else
			{ 
				event.right.add(EnumChatFormatting.RED + name); 
			}
		}
	}

	private void addHorseInfo(RenderGameOverlayEvent.Text event,	EntityPlayerSP player) 
	{
		EntityHorse horse = (EntityHorse)player.ridingEntity;
		   
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
			
			case Reference.horse.variant_white: variant = "White";break; 
			case Reference.horse.variant_creamy: variant = "Creamy";break;
			case Reference.horse.variant_chestnut: variant = "Chestnut";break;
			case Reference.horse.variant_brown: variant = "Brown";break;
			case Reference.horse.variant_black: variant = "Black";break;
			case Reference.horse.variant_gray: variant = "Gray";break;
			case Reference.horse.variant_brown_dark: variant = "Dark Brown";break; 
		}
		
		//if its not a horse, variant wont matter
		String type = "";
		switch( horse.getHorseType())
		{
			case Reference.horse.type_standard: type = variant + " Horse";break;
			case Reference.horse.type_donkey: type = "Donkey";break;
			case Reference.horse.type_mule: type = "Mule";break;
			case Reference.horse.type_zombie: type = "Zombie Horse";break;
			case Reference.horse.type_skeleton: type = "Skeleton Horse";break;
		}

		if(spots != null) type += " ("+spots+")";

		//event.left.add("");
		event.left.add(Util.lang("debug.horsetype")+"  "+type); 

		DecimalFormat df = new DecimalFormat("0.0000");
		
		event.left.add(Util.lang("debug.horsespeed")+"  "+ df.format(speed)   ); 
		
		df = new DecimalFormat("0.0");
		
		event.left.add(Util.lang("debug.horsejump") +"  "+ df.format(jumpHeight) );
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
  
		    event.left.add(Util.lang("debug.villagepop")+"  "+String.format("%d",villagers));
		    event.left.add(Util.lang("debug.villagerep")+"  "+String.format("%d",rep));
		    event.left.add(Util.lang("debug.villagedoors")+"  "+String.format("%d",doors));
 
		    dX = playerX - closest.getCenter().getX();
		    dZ = playerZ - closest.getCenter().getZ();
		    
		    int dist = MathHelper.floor_double(Math.sqrt( dX*dX + dZ*dZ));

		    event.left.add(Util.lang("debug.villagedistcenter")+"  "+String.format("%d", dist)); 
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
			event.left.add(Util.lang("debug.slimechunk")); 
		}
	}
 
	private void addDateTimeInfo(RenderGameOverlayEvent.Text event, World world) 
	{
		long time = world.getWorldTime(); 
	 
		int days = MathHelper.floor_double( time / Reference.ticksPerDay);
		 
		long remainder = time % Reference.ticksPerDay;
		
		String detail = "";
	
		if(remainder < 5000) detail = Util.lang("debug.morning");
		else if(remainder < 7000) detail = Util.lang("debug.midday");//midd ay is exactly 6k, so go one on each side
		else if(remainder < 12000) detail = Util.lang("debug.afternoon");
		else detail = Util.lang("debug.moonphase") + " " + world.getMoonPhase();

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
 
		event.left.add(Util.lang("debug.days") + days +", "+detail);
		event.left.add(sdf.format(curr));
	}
}
