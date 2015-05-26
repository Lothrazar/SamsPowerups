package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.samscontent.spell.SpellGhost;
import com.lothrazar.util.Reference;
import com.lothrazar.util.Util; 
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemFoodGhost  
{ 
 
	public static void onPlayerUpdate(LivingUpdateEvent event) 
	{
		SpellGhost.onPlayerUpdate(event);
	} 
	
}
