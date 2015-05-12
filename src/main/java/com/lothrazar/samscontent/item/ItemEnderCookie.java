package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemEnderCookie  extends ItemFood
{
	public ItemEnderCookie()
	{ 
		super(1,false); 
		this.setCreativeTab(ModMain.tabSamsContent);
		this.setPotionEffect(Potion.confusion.id, 30, 0, 0.5F);
	}

	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true; //give it shimmer, depending on if this was set in constructor
    }
	 
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
		Util.teleportWallSafe(player, world, world.getSpawnPoint()); 
		Util.playSoundAt(player,  "mob.endermen.portal");
    }
	public static void addRecipe() 
	{
		GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.ender_cookie)
			, Items.ender_pearl
			, Items.cookie);
	}
}
