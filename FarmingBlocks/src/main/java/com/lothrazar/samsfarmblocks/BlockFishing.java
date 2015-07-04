package com.lothrazar.samsfarmblocks;

import java.util.ArrayList;
import java.util.Random;  
 


import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockFishing extends Block
{   
	public BlockFishing()
	{
		super(Material.wood);  
		this.setCreativeTab(ModFarmingBlocks.tabSFarming);
		this.setHardness(3F);
		this.setResistance(5F); 
		this.setStepSound(soundTypeWood);
		this.setTickRandomly(true);
		waterBoth.add(Blocks.flowing_water);
		waterBoth.add(Blocks.water);
    }

	public static ArrayList<Block> waterBoth = new ArrayList<Block>();
	@Override
	public void updateTick(World worldObj,  BlockPos pos, IBlockState state,  Random rand)
    {  
		///if(worldObj.rand.nextInt(2) != 0){return;}//possible values are 0,1
		//meaning we have a 1/2 chance of getting past this check
		//System.out.println(worldObj.getBlockState(pos.down()).getBlock().getUnlocalizedName());
		
		if( waterBoth.contains(worldObj.getBlockState(pos.down()).getBlock()  ) &&
			waterBoth.contains(worldObj.getBlockState(pos.down(2)).getBlock()   ) &&
			waterBoth.contains(worldObj.getBlockState(pos.down(3)).getBlock()   ) &&
			waterBoth.contains(worldObj.getBlockState(pos.north()).getBlock()  ) &&
			waterBoth.contains(worldObj.getBlockState(pos.east()).getBlock()  ) &&
			waterBoth.contains(worldObj.getBlockState(pos.west()).getBlock()  ) &&
			waterBoth.contains(worldObj.getBlockState(pos.south()).getBlock()  ) 
		 
		)
		{
			//System.out.println("pass water test");
	 
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
			 
			 EntityItem ei = ModFarmingBlocks.dropItemStackInWorld(worldObj, pos.up(), fishSpawned);
	 
			 worldObj.playSoundAtEntity(ei, "game.neutral.swim.splash", 1.0F, 1.0F);
		}
	//	else 
			//System.out.println("fail water test");
    }

	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency 
	}
 
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    } 

	public static void addRecipe() 
	{
		GameRegistry.addRecipe(new ItemStack(BlockRegistry.block_fishing), 
				"pwp", 
				"wfw", 
				"pwp", 
				'w', Blocks.web, 
				'f', new ItemStack(Items.fishing_rod, 1, 0), 
				'p', Blocks.planks);

	 
			GameRegistry.addSmelting(new ItemStack(BlockRegistry.block_fishing)
			, new ItemStack(Blocks.web, 4), 0); 
		 
	} 
}
