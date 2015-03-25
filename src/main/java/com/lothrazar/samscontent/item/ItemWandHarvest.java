package com.lothrazar.samscontent.item;

import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.*;

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

public class ItemWandHarvest extends ItemTool
{
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandHarvest),
			ItemRegistry.baseWand, 
			Blocks.hay_block  );
	}
	public static int RADIUS = 128;
	public static int DURABILITY = 80;
	//public static boolean drainsHunger = true;
	//public static boolean drainsDurability = true;
  
	public ItemWandHarvest( )
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
	 
	public void replantField(EntityPlayer entityPlayer, ItemStack heldWand, BlockPos pos)
	{
		int isFullyGrown = 7; //certain this is full for wheat. applies to other plants as well
		 
		//http://www.minecraftforge.net/wiki/Plants

		int radius = 32;
		
		int x = (int)entityPlayer.posX;
		int y = (int)entityPlayer.posY;
		int z = (int)entityPlayer.posZ;
		
		//search in a cube
		int xMin = x - radius;
		int xMax = x + radius; 
		int zMin = z - radius;
		int zMax = z + radius;
		
		int eventy = pos.getY();
		
		for (int xLoop = xMin; xLoop <= x + radius; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				IBlockState bs = entityPlayer.worldObj.getBlockState(new BlockPos(xLoop, eventy, zLoop));
				Block blockCheck = bs.getBlock(); 
		 
				//everything always drops 1 thing. which in a way is 2 things
				//because we replant for free, so a full grown carrot becomes a fresh planted carrot but also drops one
				if(blockCheck == Blocks.wheat && Blocks.wheat.getMetaFromState(bs) == isFullyGrown)
				{ 
				//	blockDamage = ;
					entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop),Blocks.wheat.getDefaultState());//this plants a seed. it is not 'hay_block'
					 
					entityPlayer.dropItem(Items.wheat, 1); //no seeds, they got replanted
				}
				if( blockCheck == Blocks.carrots && Blocks.carrots.getMetaFromState(bs) == isFullyGrown)
				{
					entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), Blocks.carrots.getDefaultState());
					 
					entityPlayer.dropItem(Items.carrot, 1); 
				}
				if( blockCheck == Blocks.potatoes && Blocks.potatoes.getMetaFromState(bs) == isFullyGrown)
				{
					entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), Blocks.potatoes.getDefaultState());
					 
					entityPlayer.dropItem(Items.potato, 1); 
				} 
			}  
		} //end of the outer loop
		
		entityPlayer.swingItem();
		/* 
		if(drainsHunger)
		{
			SamsUtilities.drainHunger(entityPlayer);
		}
		*/
		SamsUtilities.damageOrBreakHeld(entityPlayer);
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		if(event.world.isRemote){ return; }//server side only!
		
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		if(held == null) { return; }//empty hand so do nothing
		  
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(held.getItem() == ItemRegistry.wandHarvest && 
				event.action.RIGHT_CLICK_BLOCK == event.action && 
		    (blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes))
		{ 
			ItemRegistry.wandHarvest.replantField(event.entityPlayer,held,event.pos); 
		}
  	}
}
