package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemFoodTeleport extends ItemFood
{
	public static enum TeleportType
	{
		BEDHOME, WORLDSPAWN, WORLDHEIGHT
	}
	
	private TeleportType type;

	public ItemFoodTeleport(int amount, TeleportType ptype) 
	{
		super(amount, false);
		this.setAlwaysEdible(); //can eat even if full hunger
		this.setCreativeTab(ModSamsContent.tabSamsContent);
		type = ptype;
	}
	
	public TeleportType getType()
	{
		return type;
	}
	@Override
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
    {     
		if(world.isRemote == false) //isRemote = false means server only
		{

			//TODO: telep wand that does home/worldhome? 
		    //single use items like edible ender pearls?
			//but is it home or world home, we need to set types
			 

			switch(((ItemFoodTeleport)itemStack.getItem()).getType())
			{ 
			case  BEDHOME:
				
				//TODO: refactor those TP commands for reuse of fns here
				//SamsUtilities.teleportWallSafe(player, world, coords);
				
				break;
			case  WORLDSPAWN:
				
				break;
			case  WORLDHEIGHT:
				
				int max = 256;//send them up to worldheight
				
				int diff = max - entityPlayer.getPosition().getY() ;
				
				SamsUtilities.teleportWallSafe(entityPlayer, world, entityPlayer.getPosition().up(diff));
				break;
			}
			
		}
    }
	

}
