package com.lothrazar.samscontent.item;

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModMain;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsUtilities; 
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

public class ItemFoodGhost extends ItemFood
{ 
	public ItemFoodGhost()
	{  
		super(4,false);
		this.setAlwaysEdible(); //can eat even if full hunger
		this.setCreativeTab(ModMain.tabSamsContent);
	}
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return true;  
    }
	private static final String KEY_BOOLEAN = "ghost_on";
	private static final String KEY_TIMER = "ghost_timer";
	private static final String KEY_EATLOC = "ghost_location";
	private static final String KEY_EATDIM = "ghost_dim";
	private static final int GHOST_SECONDS = 10;//so 30 seconds
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer player)
    {     
		if(par2World.isRemote == false)  //false means serverside
		{ 
			player.setGameType(GameType.SPECTATOR);
			 
			SamsUtilities.incrementPlayerIntegerNBT(player, KEY_TIMER, GHOST_SECONDS * Reference.TICKS_PER_SEC);
			player.getEntityData().setBoolean(KEY_BOOLEAN,true);
			player.getEntityData().setString(KEY_EATLOC, SamsUtilities.posToStringCSV(player.getPosition()));
			player.getEntityData().setInteger(KEY_EATDIM, player.dimension);
		}
    }

	public void addRecipe() 
	{

//TODO: is there something powerful enough for a ghast tear apple? 
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_ghost)
			,"lll","lal","lll"  
			,'l', Items.bone
			,'a', Items.apple);
	
		if(ModMain.cfg.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_ghost, new ItemStack(Items.bone, 8),	0);
	}

	public static void onPlayerUpdate(LivingUpdateEvent event) 
	{
		EntityPlayer player = (EntityPlayer)event.entityLiving;
		
		if(player.getEntityData().getBoolean(KEY_BOOLEAN))
		{ 
			int playerGhost = player.getEntityData().getInteger(KEY_TIMER);
			
			if(playerGhost > 0)
			{
				System.out.println("ghost timer = "+playerGhost);
				
				SamsUtilities.incrementPlayerIntegerNBT(player, KEY_TIMER,-1);
			}
			else  
			{
				
				if(player.getEntityData().getInteger(KEY_EATDIM) != player.dimension)
				{
					//if the player changed dimension while a ghost, thats not allowed
	
					player.setGameType(GameType.SURVIVAL);
					player.attackEntityFrom(DamageSource.magic, 50); 
				}
				else
				{
					// : teleport back to source
					String posCSV = player.getEntityData().getString(KEY_EATLOC); 
					String[] p = posCSV.split(",");  
					// new BlockPos(Integer.parseInt(p[0]),Integer.parseInt(p[1]),Integer.parseInt(p[2]));
				  
					player.fallDistance = 0.0F;
					player.setPositionAndUpdate(Double.parseDouble(p[0]),Double.parseDouble(p[1]),Double.parseDouble(p[2]));
					player.setGameType(GameType.SURVIVAL);
				} 
				 
				player.getEntityData().setBoolean(KEY_BOOLEAN, false);//then we are done
			}  
		}
	} 
}
