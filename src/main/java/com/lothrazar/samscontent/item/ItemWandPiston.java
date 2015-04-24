package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWandPiston extends Item
{
	public static int DURABILITY;
	public static ArrayList<Block> ignoreList = new ArrayList<Block>();
	
	public ItemWandPiston()
	{
		super();
		this.setCreativeTab(ModMain.tabSamsContent);
		this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
	}
	
	public static void seIgnoreBlocksFromString(String csv)
	{ 
		ignoreList = SamsUtilities.getBlockListFromCSV(csv); 
	} 
	
	public void addRecipe()
	{
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.wand_piston),
			"  p",
			" i ",
			"b  ",
			'p',Blocks.piston,
			'i',Blocks.iron_block,
			'b',Items.blaze_rod); 
	}
 
	public static void cast(PlayerInteractEvent event) 
	{
		BlockPos pos = event.pos;
		World world = event.world;
		IBlockState hit = world.getBlockState(pos);

		if(hit == null || ignoreList.contains(hit.getBlock()))
		{
			return;
		}
		
		/*
		TileEntity here = world.getTileEntity(pos);
		if(here != null)
		{
			System.out.println("tile entity is not null");
		}
		 */
		 
		if(event.face != null)
		{
			EntityPlayer player = event.entityPlayer;
			
			BlockPos posTowardsPlayer = pos.offset(event.face,1);
			
			BlockPos posAwayPlayer = pos.offset(event.face.getOpposite(),1);
			 
			BlockPos posMoveToHere = player.isSneaking() ? posTowardsPlayer : posAwayPlayer;
			
			if(world.isAirBlock(posMoveToHere) && world.isBlockModifiable(player, pos)) 
			{
				if(world.isRemote) 
				{
					SamsUtilities.spawnParticle(world, EnumParticleTypes.CRIT_MAGIC, pos); 
				}
				else
				{ 

					SamsUtilities.playSoundAt(player, "random.wood_click");

					//they swap places
					world.setBlockState(posMoveToHere, hit);//pulls the block towards the player
					world.setBlockToAir(pos);
					 
					SamsUtilities.damageOrBreakHeld(player);
					
					player.swingItem();
				} 
			} 
		}
	}
	
	
}