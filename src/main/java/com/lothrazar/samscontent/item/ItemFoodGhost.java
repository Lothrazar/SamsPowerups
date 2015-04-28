package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;

public class ItemFoodGhost extends ItemFood
{ 
	public ItemFoodGhost()
	{  
		super(2,false);
		this.setAlwaysEdible(); //can eat even if full hunger
		this.setCreativeTab(ModMain.tabSamsContent);
	}
	
	private static final String KEY_TIMER = "ghost_timer";
	private static final int GHOST_TICKS = 30 * Reference.TICKS_PER_SEC;
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {     
		if(par2World.isRemote == false)  //false means serverside
		{
			//TODO: only allowed in overworld
			player.setGameType(GameType.SPECTATOR);
			 
			SamsUtilities.incrementPlayerIntegerNBT(player, KEY_TIMER, GHOST_TICKS);
		}
    }

	public void addRecipe() 
	{

		//probably shapeless, apple + ghast tear
	} 
}
