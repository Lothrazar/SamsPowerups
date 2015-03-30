package com.lothrazar.samscontent.item;

import com.google.common.collect.Sets;  
import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.*;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper; 
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandHarvest extends Item
{
	public static int RADIUS;
	public static int DURABILITY; 
	public ItemWandHarvest( )
	{   
		super(); 
    	this.setMaxDamage(DURABILITY); 
		this.setMaxStackSize(1);
		this.setCreativeTab(ModSamsContent.tabSamsContent);
	}
	 
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wandHarvest),
			ItemRegistry.baseWand, 
			Blocks.hay_block  );
	}
	   
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer
    }
	 
	public void replantField(World world, EntityPlayer entityPlayer, ItemStack heldWand, BlockPos pos)
	{ 
		int isFullyGrown = 7; //certain this is full for wheat. applies to other plants as well 
		//http://www.minecraftforge.net/wiki/Plants
 
		int x = (int)entityPlayer.posX;
		int y = (int)entityPlayer.posY;
		int z = (int)entityPlayer.posZ;
		
		//search in a cube
		int xMin = x - RADIUS;
		int xMax = x + RADIUS; 
		int zMin = z - RADIUS;
		int zMax = z + RADIUS;
		
		int eventy = pos.getY();

		if(world.isRemote == false)  //only drop items in serverside
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				IBlockState bs = entityPlayer.worldObj.getBlockState(new BlockPos(xLoop, eventy, zLoop));
				Block blockCheck = bs.getBlock(); 

				if(blockCheck instanceof IGrowable)
				{
					//TODO: instead of droppin item on the plaeyer
					//just world.destropy block and replace it with the .getSeed90
					//??
				}
				//anything here should implemetn IGrowable, and also
				//extend BlockBush...
				//but those two dont give access to the seed/crop inside
				
				//BlockBush.age
				//int i = ((Integer)state.getValue(AGE)).intValue();

				//BlockCrops c = (BlockCrops)blockCheck;
				//BlockBush b =  (BlockBush)blockCheck;
				 
				//everything always drops 1 thing. which in a way is 2 things
				//because we replant for free, so a full grown carrot becomes a fresh planted carrot but also drops one
				if(blockCheck == Blocks.wheat && Blocks.wheat.getMetaFromState(bs) == isFullyGrown)
				{ 
				//	blockDamage = ;
					entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop),Blocks.wheat.getDefaultState());//this plants a seed. it is not 'hay_block'
					  
					//entityPlayer.dropItem(Items.wheat, 1); //no seeds, they got replanted
					

					entityPlayer.dropItem(blockCheck.getItemDropped(bs, world.rand, 0), 1); //no seeds, they got replanted
				}
				if( blockCheck == Blocks.carrots && Blocks.carrots.getMetaFromState(bs) == isFullyGrown)
				{
					entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), Blocks.carrots.getDefaultState());
					 
					entityPlayer.dropItem(Items.carrot, 1); 
				}
				if( blockCheck == Blocks.potatoes && Blocks.potatoes.getMetaFromState(bs) == isFullyGrown)
				{
					entityPlayer.worldObj.setBlockState(new BlockPos(xLoop,eventy,zLoop), Blocks.potatoes.getDefaultState());
					 
					//TODO: poison 
			       // if (((Integer)state.getValue(AGE)) >= 7 && rand.nextInt(50) == 0)
			        //    ret.add(new ItemStack(Items.poisonous_potato));
					entityPlayer.dropItem(Items.potato, 1); 
				} 
			}  
		} //end of the outer loop
 
		entityPlayer.swingItem();

		SamsUtilities.playSoundAt(entityPlayer, "mob.zombie.remedy");
		 
		if(world.isRemote) //client side 
			SamsUtilities.spawnParticle(world, EnumParticleTypes.VILLAGER_HAPPY, pos);//cant find the Bonemeal particles 
		else 
			SamsUtilities.damageOrBreakHeld(entityPlayer); 
	}
	
	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{      
		ItemStack held = event.entityPlayer.getCurrentEquippedItem();  
		 
		Block blockClicked = event.entityPlayer.worldObj.getBlockState(event.pos).getBlock();
		
		if(held != null && held.getItem() == ItemRegistry.wandHarvest && 
				event.action.RIGHT_CLICK_BLOCK == event.action && //TODO: IBush or IPlantable or something?
		    (blockClicked == Blocks.wheat || blockClicked == Blocks.carrots || blockClicked == Blocks.potatoes))
		{ 
			ItemRegistry.wandHarvest.replantField(event.world,event.entityPlayer,held,event.pos); 
		}
  	}
}
