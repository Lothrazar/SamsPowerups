package com.lothrazar.samscontent.spell;

import java.util.ArrayList;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Util;

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

public class UtilPistonSpell 
{
	public static int DURABILITY;
	public static ArrayList<Block> ignoreList = new ArrayList<Block>();
	public static String ignoreListFromConfig = "";
	 
	private static void translateCSV()
	{
		//do this on the fly, could be items not around yet during config change
		ignoreList = Util.getBlockListFromCSV(ignoreListFromConfig); 
	}
	public static void seIgnoreBlocksFromString(String csv)
	{ 
		ignoreListFromConfig = csv;
	} 
 
 
	public static void moveBlockTo(World world, EntityPlayer player,BlockPos pos, BlockPos posMoveToHere,boolean useItem)
	{
		IBlockState hit = world.getBlockState(pos);
		translateCSV();
		if(hit == null || ignoreList.contains(hit.getBlock()))
		{
			return;
		}
		
		if(world.isAirBlock(posMoveToHere) && world.isBlockModifiable(player, pos)) 
		{
			if(world.isRemote) 
			{
				Util.spawnParticle(world, EnumParticleTypes.CRIT_MAGIC, pos); 
			}
			else
			{  
				Util.playSoundAt(player, "random.wood_click");

				//they swap places
				//world.destroyBlock(posMoveToHere, false);
				world.destroyBlock(pos, false);
				world.setBlockState(posMoveToHere, hit);//pulls the block towards the player
				
				if(useItem)
					Util.damageOrBreakHeld(player);
				
				player.swingItem();
			} 
		} 
	}
	public static void cast(PlayerInteractEvent event) 
	{
		BlockPos pos = event.pos;
		World world = event.world;
		IBlockState hit = world.getBlockState(pos);
		
		if(event.face != null)
		{
			EntityPlayer player = event.entityPlayer;
			
			BlockPos posTowardsPlayer = pos.offset(event.face,1);
			
			BlockPos posAwayPlayer = pos.offset(event.face.getOpposite(),1);
			 
			BlockPos posMoveToHere = player.isSneaking() ? posTowardsPlayer : posAwayPlayer;
			
			moveBlockTo(world,player,pos,posMoveToHere,true);
			
		}
	}
}