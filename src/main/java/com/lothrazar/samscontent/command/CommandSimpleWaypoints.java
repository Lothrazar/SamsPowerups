package com.lothrazar.samscontent.command;

import java.io.*;
import java.util.ArrayList;
import java.util.List;    

import com.lothrazar.util.Location;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
//import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.DimensionManager;

public class CommandSimpleWaypoints  implements ICommand
{
	public static boolean REQUIRES_OP; 
	public static boolean showCoords;  
	//TODO: SHOW COORDS BUG: 
	//SIMPLE WAYPOINTS stop using [0] as dimension, use <1> [name] coords [d=0] -
	//LISTEN TO SHOW COORDS in list. if its false, just say <number>:name OR DELETE SHOW COORDS FLAG

	//in F3 use <1> home

	private ArrayList<String> aliases = new ArrayList<String>();

	public CommandSimpleWaypoints()
	{  
		this.aliases.add("wp");
		this.aliases.add("simplewp");
	}
	
	@Override
	public int compareTo(Object arg0) 
	{ 
		return 0;
	}

	@Override
	public String getName() 
	{ 
		return "simplewp";
	}

	@Override
	public String getCommandUsage(ICommandSender p_71518_1_) 
	{ 
		return "/" + getName()+" <"+MODE_LIST + "|" + MODE_SAVE + "|"+MODE_CLEAR + "|" + MODE_HIDEDISPLAY + "|" + MODE_DISPLAY+"> [savename | displayindex]";
	}

	@Override
	public List getAliases() 
	{ 
		return this.aliases;
	}

	private static String MODE_DISPLAY = "show"; 
	private static String MODE_HIDEDISPLAY = "hide";
	private static String MODE_LIST = "list";
	private static String MODE_SAVE = "save";
	private static String MODE_CLEAR = "delete"; 
	
	public static String KEY_CURRENT = "simplewp_current";
	
	@Override
	public void execute(ICommandSender icommandsender, String[] args) 
	{  
		EntityPlayer p = (EntityPlayer)icommandsender;
		
		if(args == null || args.length == 0 || args[0] == null || args[0].length() == 0)
		{ 
			p.addChatMessage(new ChatComponentTranslation(getCommandUsage(icommandsender))); 
	 
			return;//not enough args
		}
		
		 //so length at args is at least 1, so [0] exists
		//try
		//{
		if(args[0].equals(MODE_LIST))
		{
			executeList(p);
			return;
		} 
		
		if(args[0].equals(MODE_HIDEDISPLAY))
		{
			executeHide(p);
			return;
		}

		if(args[0].equals(MODE_SAVE))
		{
			String n = "";
			if(args.length > 1) n = args[1];
			executeSave(p, n);
			return;
		} 
		//clear current
		if(args[0].equals(MODE_CLEAR))
		{
			executeClear(p);
			return;
		} 
		
		 //so its outside the scope of commands that do not have a number
		int index = -1;
		
		index = Integer.parseInt(args[1]);
		
		if(index <= 0 ) //invalid number, or int parse failed
		{
			// ZERO NOT ALLOWED
			p.addChatMessage(new ChatComponentTranslation(getCommandUsage(icommandsender))); 
			return;
		}
		
		if(args[0].equals(MODE_DISPLAY))
		{ 
			executeDisplay(p,index);
			return;
		} 
		
		//}
		//catch(Exception e) //NumberFormat not anymore, could be IOOB
		//{ 
		//    addChatMessage(p,getCommandUsage(icommandsender));
		//	return;//not enough args
		//} 
		
//if nothing else
		p.addChatMessage(new ChatComponentTranslation(getCommandUsage(icommandsender))); 
	}
	
	private void executeSave(EntityPlayer p, String name) 
	{ 
		ArrayList<String> lines = GetForPlayerName(p.getDisplayName().getUnformattedText());
		
		if(name == null) name = "";
		
		//never putr a loc in index zero
		
		if(lines.size() == 0) lines.add("0");
		
		Location here = new Location(lines.size(), p , name);
		
		lines.add( here.toCSV());
		 
		OverwriteForPlayerName(p.getDisplayName().getUnformattedText(),lines);
	} 

	private void executeHide(EntityPlayer p) 
	{
		ArrayList<String> lines = GetForPlayerName(p.getDisplayName().getUnformattedText());
		
		if(lines.size() < 1){return;}
		lines.set(0,"0");
		OverwriteForPlayerName(p.getDisplayName().getUnformattedText(),lines); 
	}
	
	private void executeClear(EntityPlayer p) 
	{
		ArrayList<String> lines = GetForPlayerName(p.getDisplayName().getUnformattedText());
		
		if(lines.size() <= 1){return;}
		
		String sindex = lines.get(0);
		
		if(sindex == null || sindex.isEmpty()) {return;}
		
		int index = Integer.parseInt(sindex);
		
		ArrayList<String> newLines = new ArrayList<String>();
		
		int i = 0;
		for(String line : lines)
		{
			if(i == index)
			{
				i++;
				continue;//skip this line
			}
			i++;

			
			newLines.add(line);
		}
	 
		newLines.set(0,"0");
		OverwriteForPlayerName(p.getDisplayName().getUnformattedText(),newLines);
		
	}
	
	private void executeDisplay(EntityPlayer p, int index) 
	{  
		SetCurrentForPlayerName(p.getDisplayName().getUnformattedText(),index);
	}
	
	private void executeList(EntityPlayer p) 
	{ 
		ArrayList<String> lines = GetForPlayerName(p.getDisplayName().getUnformattedText());
		
		int i = 0;
		String d;
		for(String line : lines)
		{ 
			if(i == 0){i++;continue;}
			
			if(line == null || line.isEmpty()) {continue;}
			
			d = "" + i +" : " +(new Location(line).toDisplay());
	 
			p.addChatMessage(new ChatComponentTranslation(d)); 
			
			i++;
		}
	}
	 

	@Override
	public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) 
	{ 
		return false;
	} 
	 
	private void SetCurrentForPlayerName(String playerName, int current)
	{
		ArrayList<String> lines = GetForPlayerName(playerName);
		
		lines.set(0, current+"");//overwrite the current index
 
		OverwriteForPlayerName(playerName, lines);
	}
	
	private static String filenameForPlayer(String playerName)
	{
		return "swp_"+playerName +".dat";
	}
	
	private void OverwriteForPlayerName(String playerName, ArrayList<String> lines)
	{
		String fileName = filenameForPlayer(playerName);
		try
		{
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
			if(!myFile.exists()) myFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(myFile);
			DataOutputStream stream = new DataOutputStream(fos);
			 
			for(String line : lines) 
			{
				stream.writeBytes(line);
				stream.writeBytes(System.lineSeparator());
			}
			
			stream.close();
			fos.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> GetForPlayerName(String playerName)
	{ 
		if(playerName == null)
		{
			//logger.info("GetForPlayerName possible exception: <null>");
			return null;
		}
	//	logger.info("GetForPlayerName : "+ playerName);
		String fileName = filenameForPlayer(playerName);;
		ArrayList<String> lines = new ArrayList<String>();
	 
		try
		{
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName);
			if(!myFile.exists()) myFile.createNewFile();
			FileInputStream fis = new FileInputStream(myFile);
			DataInputStream instream = new DataInputStream(fis);
			String val;
			
			while((val = instream.readLine()) != null) lines.add(val);
			
			instream.close();
			fis.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return lines;
	} 
	
	public static void AddWaypointInfo(RenderGameOverlayEvent.Text event) 
	{
		EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
	 
    	ArrayList<String> saved = GetForPlayerName(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText());

    	if(saved.size() > 0 && saved.get(0) != null)
    	{ 
    		int index = 0;
    		try
    		{
	    		index = Integer.parseInt( saved.get(0) );
    		}
    		catch(NumberFormatException e) 
    		{ 
    			return;
    		}// do nothing, its allowed to be a string
    		
    		if(index <= 0){return;}
    		
    		Location loc = null;

    		if(saved.size() <= index) {return;}
    		
    		String sloc = saved.get(index);
    		
    		if(sloc == null || sloc.isEmpty()) {return;}
    	 
    		if( index < saved.size() && saved.get(index) != null) loc = new Location(sloc);
    		
    		if(loc != null)
    		{ 
    			if(p.dimension != loc.dimension){return;}
    			
    			double dX = p.posX - loc.X;
    			double dZ = p.posZ - loc.Z;
    			
    			int dist = MathHelper.floor_double(Math.sqrt( dX*dX + dZ*dZ));
    			 
    			String showName = "Distance "+dist+ " from waypoint ["+index+"] " + loc.name;	
    			
    			boolean sideRight=true;
    			if(sideRight)
    				event.right.add(showName);
    			else 
    				event.left.add(showName);
    		} 
    	}
	}

	@Override
	public boolean canCommandSenderUse(ICommandSender ic)
	{
		return (REQUIRES_OP) ? ic.canUseCommand(2, this.getName()) : true; 
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{ 
		return null;
	}
}
