package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandPiston extends Item
{
	public static int DURABILITY = 200;
	public static ArrayList<Block> ignoreList = new ArrayList<Block>();
	
	public ItemWandPiston()
	{
		super();
		this.setCreativeTab(ModMain.tabSamsContent);
		this.setMaxDamage(DURABILITY);
	}
	
	public static void seIgnoreBlocksFromString(String csv)
	{ 
		System.out.println("seIgnoreBlocksFromString");
		System.out.println(csv);
		ignoreList = SamsUtilities.getBlockListFromCSV(csv); 
		System.out.println("seIgnoreBlocksFromString  "+ignoreList.size());
	} 
	
	public void addRecipe()
	{
		
	}
 
	public static void cast(PlayerInteractEvent event) 
	{
		BlockPos pos = event.pos;
		World world = event.world;
		IBlockState hit = world.getBlockState(pos);
		EntityPlayer player = event.entityPlayer;
		 
		if(hit == null || ignoreList.contains(hit.getBlock()))
		{
			return;
		}
		 
		if(event.face != null)
		{
			BlockPos posTowardsPlayer = pos.offset(event.face,1);
			
			BlockPos posAwayPlayer = pos.offset(event.face.getOpposite(),1);
			
			System.out.println("towards is "+SamsUtilities.posToString(posTowardsPlayer));
			System.out.println("AWAY is "+SamsUtilities.posToString(posAwayPlayer));
			BlockPos posMoveToHere = player.isSneaking() ? posTowardsPlayer : posAwayPlayer;
			
			if(world.isAirBlock(posMoveToHere) && world.isBlockModifiable(player, pos)) 
			{
				if(world.isRemote) // clientside
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