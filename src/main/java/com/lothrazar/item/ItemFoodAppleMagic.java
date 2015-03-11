package com.lothrazar.item;

import java.util.ArrayList; 

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.PlayerPowerups;
import com.lothrazar.util.Reference;
import com.lothrazar.util.SamsRegistry;
import com.lothrazar.util.SamsUtilities;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
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
	public static enum MagicType
	{
		Potion, Flying, Hearts 
	}
	private boolean hasEffect = false;
	private static int FLYING_COUNT_PER_EAT = 1;//num of ticks
	private ArrayList<Integer> potionIds;
	private ArrayList<Integer> potionDurations;
	private ArrayList<Integer> potionAmplifiers;;
	private MagicType type;
	
	public ItemFoodAppleMagic(MagicType ptype, int fillsHunger,boolean has_effect)
	{  
		super(fillsHunger,false);// fills 1 hunger (very small i know), and is not edible by wolf
		type = ptype;
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
  		if(MagicType.Potion == this.type)
  		{ 
			SamsUtilities.incrementPlayerIntegerNBT(par3EntityPlayer, par1ItemStack.getItem().getUnlocalizedName(),1); 
			
	  		for(int i = 0; i < potionIds.size(); i++)  
	  		{ 
	  			par3EntityPlayer.addPotionEffect(new PotionEffect(potionIds.get(i) ,potionDurations.get(i),potionAmplifiers.get(i)));
	  		} 
  		} 
  		else if(MagicType.Flying == this.type) 
  		{  

  			PlayerPowerups props = PlayerPowerups.get(par3EntityPlayer);

  			props.incrementCurrentFly(FLYING_COUNT_PER_EAT);
  		 
  			//SamsUtilities.incrementPlayerIntegerNBT(par3EntityPlayer, Reference.MODID + MagicType.Flying.toString(),FLYING_COUNT_PER_EAT);
  		}
  		else if(MagicType.Hearts == this.type)
  		{
  			par3EntityPlayer.removePotionEffectClient(Reference.potion_HEALTH_BOOST);//reset it so it gets reapplied 
  			SamsUtilities.incrementPlayerIntegerNBT(par3EntityPlayer, Reference.MODID + MagicType.Hearts.toString(),1);
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
	
	static int I = 0;//TODO: REFERENCE POTION
	static int II = 1;
	static int III = 2;
	static int IV = 3;
	static int V = 4;
	
	static int timeShort = 90; // 1:30
	static int timeLong = 8 * 60;// 8:00

	static int hungerSmall = 1;
	static int hungerLarge = 4; //how much it fills us up
	 
	//static int id_horse = -1;
	 
	public static void initChocolate()
	{
		if(!ModLoader.configSettings.appleChocolate){return;}

		ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(MagicType.Potion,hungerLarge, false); // 4 is the hunger 
		ItemRegistry.apple_chocolate.addEffect(Reference.potion_HASTE, timeShort, II); 
		SamsRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_chocolate)
				, "eee", "eae",	"eee"
				, 'e', new ItemStack(Items.dye, 1, Reference.dye_cocoa)  
				, 'a', Items.apple);
		
		ItemRegistry.apple_chocolate_rich = new ItemFoodAppleMagic(MagicType.Potion,hungerLarge, true); // 4 is the hunger 
		ItemRegistry.apple_chocolate_rich.addEffect(Reference.potion_HASTE, timeLong, I); 
		SamsRegistry.registerItem(ItemRegistry.apple_chocolate_rich, "apple_chocolate_rich");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_chocolate_rich)
				, "eee", "eae",	"eee"
				, 'e', new ItemStack(Items.cookie)  
				, 'a', Items.apple);
	}

	public static void initLapis()
	{  
		if(!ModLoader.configSettings.appleLapis){return;}
		ItemRegistry.apple_lapis = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, false);
		ItemRegistry.apple_lapis.addEffect(Reference.potion_RESISTANCE, timeShort, II); 
		SamsRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_lapis)
				, "lll","lal","lll"  
				,'l', new ItemStack(Items.dye, 1, Reference.dye_lapis)  
				,'a', Items.apple); 
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_lapis, new ItemStack(Items.dye, 8, Reference.dye_lapis), 0);// uncraft
	
		ItemRegistry.apple_lapis_rich = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, true);
		ItemRegistry.apple_lapis_rich.addEffect(Reference.potion_RESISTANCE, timeLong, I); 
		SamsRegistry.registerItem(ItemRegistry.apple_lapis_rich, "apple_lapis_rich");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_lapis_rich)
				, "lll","lal","lll"  
				,'l', Blocks.lapis_block
				,'a', Items.apple); 
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_lapis_rich, new ItemStack(Blocks.lapis_block), 0);// uncraft 
	}
 
	public static void initEmerald()
	{   
		if(!ModLoader.configSettings.appleEmerald) {return;}
		ItemRegistry.apple_emerald = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, false);
		ItemRegistry.apple_emerald.addEffect(Potion.absorption.id, timeShort, V); 
		ItemRegistry.apple_emerald.addEffect(ModLoader.customPotion.id, timeShort, I); 
		SamsRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_emerald)
				, "lll","lal","lll"  
				,'l', Items.emerald
				,'a', Items.apple);
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_emerald, new ItemStack(Items.emerald, 8),	0);
		 
		ItemRegistry.apple_emerald_rich = new ItemFoodAppleMagic(MagicType.Potion,hungerSmall, true);
		ItemRegistry.apple_emerald_rich.addEffect(Reference.potion_absorption, timeLong, V); 
		SamsRegistry.registerItem(ItemRegistry.apple_emerald_rich, "apple_emerald_rich");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_emerald_rich)
				, "lll","lal","lll"  
				,'l', Blocks.emerald_block
				,'a', Items.apple);
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_emerald_rich, new ItemStack(Blocks.emerald_block, 8),	0);
	} 

	public static void initDiamond()
	{ 
		if(!ModLoader.configSettings.appleDiamond) {return;}
		ItemRegistry.apple_diamond = new ItemFoodAppleMagic(MagicType.Hearts,hungerSmall, true);  
		//no potion effect, this just does heath boost + 1
		SamsRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_diamond)
				, "lll","lal","lll"  
				,'l', Items.diamond
				,'a', Items.apple); 
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_diamond, new ItemStack(Items.diamond, 8),	0); 
	}

	public static void initNether()
	{  
		if(!ModLoader.configSettings.appleNetherStar) {return;}
		ItemRegistry.apple_nether_star = new ItemFoodAppleMagic(MagicType.Flying,hungerSmall, true);  
		//no potion effect, this just gives flying
		SamsRegistry.registerItem(ItemRegistry.apple_nether_star, "apple_nether_star");
		
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_nether_star),
			"lll",
			"lnl",
			"lll", 
			'l', Items.apple,
			'n', Items.nether_star); 
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_nether_star, new ItemStack(Items.nether_star, 1),	0); 
	} 
}
