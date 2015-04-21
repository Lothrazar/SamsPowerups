package com.lothrazar.samscontent.item;

import java.util.ArrayList;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWandPiston extends Item
{
	public static int DURABILITY=200;//TODO:CONFIG
	private static boolean ignoreTileEntities = false;
	private static boolean ingorePlantable = false;
	public static ArrayList<Block> ignoreList = new ArrayList<Block>();
	public ItemWandPiston()
	{
		super();
		this.setCreativeTab(ModMain.tabSamsContent);
		this.setMaxDamage(DURABILITY);
	}
	public static void seIgnoreBlocksFromString(String csv)
	{ 
		System.out.println(csv);
		ignoreList = SamsUtilities.getBlockListFromCSV(csv); 
	} 
	public void addRecipe()
	{
		
	}

	
	public static void cast(PlayerInteractEvent event) 
	{
		BlockPos pos = event.pos;
		World world = event.world;
		IBlockState hit = world.getBlockState(pos);
		 
		if(ignoreTileEntities && world.getTileEntity(pos) != null)
		{
			return;
		}
		
		if(hit == null || ignoreList.contains(hit.getBlock()))
		{
			return;
		}
		
		if(hit.getBlock() instanceof IGrowable)
		{
			return;
		}
		
		if(event.face != null)
		{
			BlockPos offsetpast = pos.offset(event.face,1);
			
			if(world.isAirBlock(offsetpast)) 
			{
				if(world.isRemote) // clientside
				{
					SamsUtilities.spawnParticle(world, EnumParticleTypes.CRIT_MAGIC, pos); 
				}
				else
				{
					EntityPlayer player = event.entityPlayer;
					
					SamsUtilities.playSoundAt(player, "random.wood_click");

					//they swap places
					world.setBlockState(offsetpast, hit);//pulls the block towards the player
					world.setBlockToAir(pos);
					 
					SamsUtilities.damageOrBreakHeld(player);
					
					player.swingItem();
				} 
			} 
		}
	}
	
	
}