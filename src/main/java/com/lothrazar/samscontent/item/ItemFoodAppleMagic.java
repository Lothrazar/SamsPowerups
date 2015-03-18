package com.lothrazar.samscontent.item;

import java.util.ArrayList; 

import com.lothrazar.samscontent.potion.PotionRegistry;
import com.lothrazar.samscontent.ModLoader; 
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ItemFoodAppleMagic extends ItemFood
{  
	private boolean hasEffect = false;
	private static int FLYING_COUNT_PER_EAT = 1;//num of ticks
	private ArrayList<Integer> potionIds;
	private ArrayList<Integer> potionDurations;
	private ArrayList<Integer> potionAmplifiers;
	
	public ItemFoodAppleMagic(int fillsHunger,boolean has_effect)
	{  
		super(fillsHunger,false);//is not edible by wolf
		hasEffect = has_effect;//true gives it enchantment shine
 
		this.setAlwaysEdible(); //can eat even if full hunger
		potionIds = new ArrayList<Integer>();
		potionDurations = new ArrayList<Integer>();
		potionAmplifiers = new ArrayList<Integer>();
		this.setCreativeTab(ModLoader.tabSamsContent);
	}
	 
	public ItemFoodAppleMagic addEffect(int potionId,int potionDuration,int potionAmplifier)
	{
		int TICKS_PER_SEC = 20;
		
		potionIds.add(potionId);
		potionDurations.add(potionDuration * TICKS_PER_SEC);
		potionAmplifiers.add(potionAmplifier);
		 
		return this;//to chain together
	}
	
	@Override
	protected void onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {     
		if(par2World.isRemote == false)///false means serverside
	  		for(int i = 0; i < potionIds.size(); i++)  
	  		{ 
	  			par3EntityPlayer.addPotionEffect(new PotionEffect(potionIds.get(i) ,potionDurations.get(i),potionAmplifiers.get(i)));
	  		}  
    }
	
	@Override
    public boolean hasEffect(ItemStack par1ItemStack)
    {
    	return hasEffect; //give it shimmer, depending on if this was set in constructor
    }
	 
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack)
	{
		 if(hasEffect)
			 return EnumRarity.EPIC; //dynamic text to match the two apple colours
		 else 
			 return EnumRarity.RARE;
	} 
	 
	public static int timeShort = 90; // 1:30
	public static int timeLong = 8 * 60;// 8:00

	public static int hungerSmall = 1;
	public static int hungerLarge = 4; //how much it fills us up
  
	public static int chocolatePotion = Potion.digSpeed.id;//Haste
	public static int chocolateLevel = com.lothrazar.samscontent.potion.PotionRegistry.I;
	
	public static int lapisPotion = PotionRegistry.waterwalk.id;
	public static int lapisLevel = PotionRegistry.I;
	
	public static int emeraldPotion = PotionRegistry.slowfall.id;  
	public static int emeraldLevel = PotionRegistry.I;
 
	public static int diamondPotion = PotionRegistry.flying.id;
	public static int diamondPotion2 = Potion.resistance.id; 
	public static int dimondLevel = PotionRegistry.I;
	public static int dimond2Level = PotionRegistry.I;
	
	public static int netherwartApplePotion = PotionRegistry.lavawalk.id;
	public static int netherwartAppleLevel = PotionRegistry.I;

	public static int enderPotion = PotionRegistry.ender.id;
	public static int enderLevel = PotionRegistry.I;

	public static void addRecipe(ItemFoodAppleMagic apple, ItemStack ingredient) 
	{
		GameRegistry.addRecipe(new ItemStack(apple)
			,"lll","lal","lll"  
			,'l', ingredient
			,'a', Items.apple);
		
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(apple, new ItemStack(ingredient.getItem(), 8),	0);
	} 
}
