package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModSamsContent;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
	protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player)
    {     
		if(world.isRemote == false) //isRemote = false means server only
		{ 
			//TODO: telep wand that does home/worldhome? 
		    //single use items like edible ender pearls?
			//but is it home or world home, we need to set types
			
			boolean playSound = false;
			 

			switch(((ItemFoodTeleport)itemStack.getItem()).getType())
			{ 
			case  BEDHOME:
				
				//TODO: refactor those TP commands for reuse of fns here
				//SamsUtilities.teleportWallSafe(player, world, coords);
				BlockPos realBedPos = SamsUtilities.getBedLocationSafe(world, player);
				 
				if(realBedPos != null)
				{ 
					SamsUtilities.teleportWallSafe(player, world, realBedPos); 
					playSound = true;
				}
				else
				{
					//spawn point was set, so the coords were not null, but player broke the bed (probably recently)
					player.addChatMessage(new ChatComponentTranslation("Your home bed was missing or obstructed.")); 
				} 
				
				
				break;
			case  WORLDSPAWN:

				SamsUtilities.teleportWallSafe(player, world, world.getSpawnPoint()); 
				playSound = true;
				
				break;
			case  WORLDHEIGHT:
				
				int max = 256;//send them up to worldheight
				
				int diff = max - player.getPosition().getY() ;
				
				SamsUtilities.teleportWallSafe(player, world, player.getPosition().up(diff));
				playSound = true;
				break;
			}//end switch
			
			if(playSound)
			{ 
				world.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
			}
			
		}
    }

	public static void addRecipe(ItemFoodTeleport item, ItemStack ingredient) 
	{
		GameRegistry.addRecipe(new ItemStack(item)
			,"lll","lal","lll"  
			,'l', ingredient
			,'a', ItemRegistry.beetrootItem);
		
		if(ModSamsContent.cfg.uncraftGeneral) 
			GameRegistry.addSmelting(item, new ItemStack(ingredient.getItem(), 8),	0);
	}
}
