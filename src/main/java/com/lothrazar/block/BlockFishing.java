package com.lothrazar.block;

import java.util.Random;  

import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.util.SamsRegistry;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class BlockFishing extends Block
{
	private static boolean isEnabled = true;
	  
	public BlockFishing()
	{
		super(Material.wood);  
		this.setCreativeTab(ModLoader.tabSamsContent);
		this.setHardness(3F);
		this.setResistance(5F); 
		this.setTickRandomly(true);
    }
	    
	@Override
	public void updateTick(World worldObj,  BlockPos pos, IBlockState state,  Random rand)
    {  
		int xCoord = pos.getX();
		int yCoord = pos.getY();
		int zCoord = pos.getZ();
		 
		 if(worldObj.getBlockState(new BlockPos(xCoord+1, yCoord, zCoord)).equals(Blocks.water) == false
		 || worldObj.getBlockState(new BlockPos(xCoord-1, yCoord, zCoord)).equals(Blocks.water) == false 
		 || worldObj.getBlockState(new BlockPos(xCoord, yCoord, zCoord+1)).equals(Blocks.water) == false
		 || worldObj.getBlockState(new BlockPos(xCoord, yCoord, zCoord-1)).equals(Blocks.water) == false //these 4 lines cover the four direct sides horiz
		 || worldObj.getBlockState(new BlockPos(xCoord, yCoord-1, zCoord)).equals(Blocks.water) == false 
		 || worldObj.getBlockState(new BlockPos(xCoord, yCoord-2, zCoord)).equals(Blocks.water) == false //also go 2 deep
		 
		)
		 {
			 //all   MUST be   water. so if any one of them is not water, dont fish
			  return; 
		 }
		
		 //reference for chances 
		 // http://minecraft.gamepedia.com/Fishing_Rod#Junk_and_treasures
	 
		 //i know junk can do stuff like leather, stick, string, etc
		 //but for this, junk gives us NADA
		 //and treasure is nada as well
 
		 ItemStack plain =  new ItemStack(Items.fish,1,0);
		 double plainChance = 60;

		 ItemStack salmon =  new ItemStack(Items.fish,1,1);
		 double salmonChance = 25 + plainChance;//so it is between 60 and 85

		 ItemStack clownfish =  new ItemStack(Items.fish,1,2);
		 double clownfishChance = 2 + salmonChance;//so between 85 and 87
		  
		 ItemStack pufferfish =  new ItemStack(Items.fish,1,3);
 
		 double diceRoll = rand.nextDouble() * 100; 
			
		 ItemStack fishSpawned;
		 
		 if(diceRoll < plainChance)
		 {
			 fishSpawned = plain;
		 }
		 else if(diceRoll < salmonChance )
		 {
			 fishSpawned = salmon;
		 }
		 else if(diceRoll < clownfishChance )
		 {
			 fishSpawned = clownfish;
		 }
		 else
		 {
			 fishSpawned = pufferfish;
		 }
		 
        EntityItem entityItem = new EntityItem(worldObj, xCoord, yCoord, zCoord, fishSpawned);
    	 
    	worldObj.spawnEntityInWorld(entityItem);
    	
    	worldObj.playSoundAtEntity(entityItem,"game.neutral.swim.splash",1F,1F); 
    }

	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency stuff
	}
 
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }
	
	public static final String name = "block_fishing";

	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.block_fishing), 
				"pwp", 
				"wfw", 
				"pwp", 
				'w', Blocks.web, 
				'f', new ItemStack(Items.fishing_rod, 1, 0), 
				'p', Blocks.planks);

		if(ModLoader.configSettings.uncraftGeneral)
		{
			GameRegistry.addSmelting(new ItemStack(BlockRegistry.block_fishing)
			, new ItemStack(Blocks.web, 4), 0); 
		}
	} 
}
