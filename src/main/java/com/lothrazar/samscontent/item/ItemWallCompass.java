package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Util;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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
		// TODO Auto-generated method stub
		
	}

	public static void onRightClick(PlayerInteractEvent event)
	{
		System.out.println("rightclick wc");
		if(event.face == null){return;}
		
		BlockPos pos = event.pos;
		
		BlockPos offs = pos.offset(event.face.getOpposite(),1);
		
	 
		//not 2, depends on block pos?
		if(event.world.isAirBlock(offs) && event.world.isAirBlock(offs.up()))
		{
			
			//Util.teleportWallSafe(event.entityPlayer, event.world, offs);
			event.entityPlayer.setPositionAndUpdate(offs.getX(), offs.getY(), offs.getZ()); 

			
			if(event.world.isRemote == false)
				Util.damageOrBreakHeld(event.entityPlayer);
			
			

			event.world.playSoundAtEntity(event.entityPlayer, "mob.endermen.portal", 1.0F, 1.0F);  
			Util.spawnParticle(event.world, EnumParticleTypes.PORTAL, event.entityPlayer.getPosition());
		}
	}
}
