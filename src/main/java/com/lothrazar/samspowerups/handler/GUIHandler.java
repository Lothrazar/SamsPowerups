package com.lothrazar.samspowerups.handler;

import com.lothrazar.samspowerups.ModCore;
 

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler
{
	//C:\Users\Samson\Desktop\Minecraft\BACKUPS\146 src
//package net.minecraft..inventory.ContainerPlayer
	//  .............client.gui


	//existing ones
	public static final int craftingGui = 1;
	public static final int furnaceGui = 2;
 
	
	 @Override
	 public Object getClientGuiElement (int id, EntityPlayer p, World world, int x, int y, int z)
	 {
		 System.out.println(id);
		 ModCore.logger.info("loth1111 "+id);

		 GuiInventory g;
		// return new MyGuiInventory(p.inventory,world);
		 /*
		 if (id == craftingGui)
		 {
			 //can we overwrite the existing ui and use my own!?!?!
		 }*/
		 
		 return null;
		 
	 }
	 @Override
	 public Object getServerGuiElement (int id, EntityPlayer p, World world, int x, int y, int z)
	 {
		 System.out.println(id);

		// return new MyContainerPlayer(p.inventory,world.isRemote,p);
		 
		  return null;
	 }
	
}
