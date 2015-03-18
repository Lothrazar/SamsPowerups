package com.lothrazar.samscontent.command;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random; 
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
//import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CommandTodoList implements ICommand
{   
	public static boolean REQUIRES_OP = false; 

	private ArrayList<String> aliases = new ArrayList<String>(); 
	
	public static String KEY_LIST = "todo_list";
	  
	public CommandTodoList()
	{  
	    this.aliases.add("todo");   
	}
  
	@Override
	public String getCommandUsage(ICommandSender s) 
	{ 
		return "/" + getName()+" <+|-> <text>";
	}
   
	public static String GetTodoForPlayerName(String playerName)
	{
		//String fileName = "todo_"+ p.getDisplayName() +".dat";
		String fileName = "todo_"+playerName +".dat";
   
		String todoCurrent = "" ; 
		try 
	 	{
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName); 
			if(!myFile.exists()) myFile.createNewFile(); 
			FileInputStream  fis = new FileInputStream(myFile);
			DataInputStream instream = new DataInputStream(fis);
			
			String val;
			while((val = instream.readLine()) != null)todoCurrent += val; 
			instream.close(); 
			fis.close();
	 	} catch (FileNotFoundException e) { 
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) {
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		}
	 
		return todoCurrent;
	}
	
	public static void SetTodoForPlayerName(String playerName, String todoCurrent)
	{
		String fileName = "todo_"+playerName +".dat";
		try{
			 
			File myFile = new File(DimensionManager.getCurrentSaveRootDirectory(), fileName); 
			if(!myFile.exists()) myFile.createNewFile(); 
			FileOutputStream fos = new FileOutputStream(myFile);
			
			DataOutputStream stream = new DataOutputStream(fos);
			stream.writeBytes(todoCurrent);
			stream.close();
			fos.close();
			  
		} catch (FileNotFoundException e) { 
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		} //this makes it per-world
		catch (IOException e) {
		//	Relay.addChatMessage(p, "Error with "+fileName);
			e.printStackTrace();
		} 
	}
	
	@Override
	public void execute(ICommandSender icommandsender, String[] args)
	{ 
		EntityPlayer p = (EntityPlayer)icommandsender;//

		String fileName = "todo_"+ p.getDisplayName() +".dat";
 
		String todoCurrent = GetTodoForPlayerName(p.getDisplayName().getUnformattedText() );
 
		 //is the first argument empty
		 if(args == null || args.length == 0 || args[0] == null || args[0].isEmpty())
		 {
			 //they only typed "/todo"
			 
			 if(todoCurrent != null && todoCurrent.isEmpty() == false)
			 {
				//Chat.addMessage(p,todoCurrent); //just tell them the message if it exists
			 }
			  
			 return; 
		 }
		 

		 String message = "";
	   
		 if(args[0].equals("-"))
		 { 
			 todoCurrent = "";
			 	//message does nothing
			 args[0] = "";//remove the plus sign 
		 } 
		 else if(args[0].equals("+"))
		 {
			// message = todoCurrent; //append to existing messages
			// args[0] = "";//remove the plus sign 
		
			 for(int i = 1; i < args.length; i++)
			 {
				 message += " " + args[i];
			 } 
			 todoCurrent += " " + message;//so append
		 }
		 else 
		 {
			 //they just did /todo blah blah
			 for(int i = 0; i < args.length; i++)
			 {
				 message += " " + args[i];
			 } 
			 todoCurrent = message;//so replace
		 }
 
		 SetTodoForPlayerName(p.getDisplayName().getUnformattedText(),todoCurrent); 
	}
	 
 
	@Override
	public List getAliases() 
	{ 
		return aliases;
	}
	 

	@Override
	public String getName() 
	{ 
		return "todo";
	}

	@Override
	public int compareTo(Object arg0)
	{ 
		return 0;
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

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{ 
		return false;
	}
}



