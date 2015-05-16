package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class ItemWallCompass extends Item
{
	public static int DURABILITY=5;//TODO CONFIG
	
	public ItemWallCompass()
	{
		this.setCreativeTab(ModMain.tabSamsContent);
		this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
	}

	public static void addRecipe()
	{

		
	}

	public static void onRightClick(PlayerInteractEvent event)
	{
		if(event.face == null){return;}//means we clicked into air, not on a block
		
		BlockPos pos = event.pos;
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		
		int dist = 1;
		if(event.face.getOpposite() == EnumFacing.DOWN)
		{
			 dist = 2;//only move two when going down - player is 2 tall
		}
		
		BlockPos offs = pos.offset(event.face.getOpposite(), dist);
		
		//not 2, depends on block pos?
		if(event.world.isAirBlock(offs) && event.world.isAirBlock(offs.up()))
		{
			player.setPositionAndUpdate(offs.getX(), offs.getY(), offs.getZ()); 

			event.world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);  
			Util.spawnParticle(world, EnumParticleTypes.PORTAL, offs);

			if(event.world.isRemote == false)
				Util.damageOrBreakHeld(player);
		}
	}
}
