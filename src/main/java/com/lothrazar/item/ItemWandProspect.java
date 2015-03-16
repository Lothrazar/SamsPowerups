package com.lothrazar.item;

import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper; 
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandProspect extends ItemTool
{
	private static int RADIUS = 16;//TODO: CONFIG FILE
	private static int DURABILITY = 80;
	public static boolean drainsHunger = true;
	public static boolean drainsDurability = true;
  
	public ItemWandProspect( )
	{   
		super(1.0F,Item.ToolMaterial.WOOD, Sets.newHashSet()); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
		this.setCreativeTab(ModLoader.tabSamsContent);
	}
	   
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
	
	public void searchProspect(EntityPlayer entityPlayer, ItemStack heldWand, BlockPos pos)
	{  
		//0 bottom, 1 top
		//5 east 3 south
		//4 west 2 north
		
		//the x-axis indicates the player's distance east (positive) or west (negative) of the origin point—i.e., the longitude,
	  //	the z-axis indicates the player's distance south (positive) or north (negative) of the origin point—i.e., the latitude,

		int x = (int)entityPlayer.posX;
		int y = (int)entityPlayer.posY;
		int z = (int)entityPlayer.posZ;
		
		//if player hits the EAST side of the block, then the blocks east side is facing them
		//therefore, the player is facing west
		String foundMessage = "No diamond ore found within "+ItemWandProspect.RADIUS+" blocks";

		BlockPos found = SamsUtilities.findClosestBlock(entityPlayer, Blocks.diamond_ore, RADIUS);
		
		int distance = (int)SamsUtilities.distanceBetween(found, entityPlayer.getPosition());

		if(found != null)
		{
			foundMessage =  "Diamond ore found at distance "+distance ;
		} 
	  
		entityPlayer.addChatMessage(new ChatComponentTranslation( foundMessage));
	 
		entityPlayer.swingItem();
		 
		if(drainsHunger)
		{
			SamsUtilities.drainHunger(entityPlayer);
		}
		
		SamsUtilities.damageOrBreakHeld(entityPlayer);
	}
	  
	public static void onInit() 
	{  
		if(!ModLoader.configSettings.wandProspect){return;}
			
		ItemRegistry.wandProspect = new ItemWandProspect();
  
		SamsRegistry.registerItem(ItemRegistry.wandProspect, "wand_prospect");

		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandProspect),
			ItemRegistry.baseWand, 
			Items.redstone  );  
	}
 
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return ;}//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(held.getItem() == ItemRegistry.wandProspect && 
				event.action.RIGHT_CLICK_BLOCK == event.action)
		{ 
			ItemRegistry.wandProspect.searchProspect(event.entityPlayer,held,event.pos);   
		}
  	}
}
