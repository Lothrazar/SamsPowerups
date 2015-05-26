package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemWallCompass extends Item
{
	public static int DURABILITY;
	
	public ItemWallCompass()
	{
		this.setCreativeTab(ModMain.tabSamsContent);
		this.setMaxDamage(DURABILITY);
		this.setMaxStackSize(1);
	}
/*
	public static void addRecipe()
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.wall_compass)
			, new ItemStack(Items.compass)
			, new ItemStack(Items.ender_eye)
			, new ItemStack(Items.diamond)
			, new ItemStack(Items.emerald));
	}*/

	public static void onRightClick(PlayerInteractEvent event)
	{
		if(event.face == null){return;}//means we clicked into air, not on a block
		
		//TODO: we could detect block clicked, what type it is,and check some blacklist from config
		//ex, disable bedrock or something
		BlockPos pos = event.pos;
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		
		wallPhase(world,player,pos,event.face);

		if(world.isRemote == false)
			Util.damageOrBreakHeld(player);
	}
	public static void wallPhase(World world, EntityPlayer player,BlockPos pos ,EnumFacing face)
	{
		int dist = 1;
		if(face.getOpposite() == EnumFacing.DOWN)
		{
			 dist = 2;//only move two when going down - player is 2 tall
		}
		
		BlockPos offs = pos.offset(face.getOpposite(), dist);
		
		//not 2, depends on block pos?
		if(world.isAirBlock(offs) && world.isAirBlock(offs.up()))
		{
			player.setPositionAndUpdate(offs.getX(), offs.getY(), offs.getZ()); 

			world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);  
			Util.spawnParticle(world, EnumParticleTypes.PORTAL, offs);
		}
	}
}
