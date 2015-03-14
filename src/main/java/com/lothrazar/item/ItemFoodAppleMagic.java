package com.lothrazar.item;

import java.util.ArrayList; 

import com.lothrazar.samscontent.ItemRegistry;
import com.lothrazar.samscontent.ModLoader;
import com.lothrazar.samscontent.PlayerPowerups;
import com.lothrazar.samscontent.PotionRegistry;
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

	public static int chocolatePotion = PotionRegistry.slowfall.id;
	public static int lapisPotion = PotionRegistry.waterwalk.id;
	public static int emeraldPotion = Potion.healthBoost.id; // V //replaces that MagicType.Hearts thing
	public static int diamondPotion = PotionRegistry.lavawalk.id;
	public static int diamondPotion2 = Potion.resistance.id; 
	//TODO: we removed absorop and resistance already, do we want them back?
	 
	public static void initChocolate()
	{
		if(!ModLoader.configSettings.appleChocolate){return;}

		ItemRegistry.apple_chocolate = new ItemFoodAppleMagic(hungerLarge, false); // 4 is the hunger 
		ItemRegistry.apple_chocolate.addEffect(chocolatePotion, timeShort, PotionRegistry.I); 
		SamsRegistry.registerItem(ItemRegistry.apple_chocolate, "apple_chocolate");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_chocolate)
				,"lll", "lal",	"lll"
				,'l', new ItemStack(Items.dye, 1, Reference.dye_cocoa)  
				,'a', Items.apple);
		
		ItemRegistry.apple_chocolate_rich = new ItemFoodAppleMagic(hungerLarge, true); // 4 is the hunger 
		ItemRegistry.apple_chocolate_rich.addEffect(chocolatePotion, timeLong, PotionRegistry.I); 
		SamsRegistry.registerItem(ItemRegistry.apple_chocolate_rich, "apple_chocolate_rich");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_chocolate_rich)
				,"lll", "lal",	"lll"
				,'l', new ItemStack(Items.cookie) //since no Chocolate Block exists. //TODO: does this make sense? use brown clay texture?  
				,'l', Items.apple);
	}
 
	public static void initLapis()
	{   
		if(!ModLoader.configSettings.appleLapis){return;}
		
		ItemRegistry.apple_lapis = new ItemFoodAppleMagic(hungerSmall, false);
		ItemRegistry.apple_lapis.addEffect(lapisPotion, timeShort, PotionRegistry.I); 
		SamsRegistry.registerItem(ItemRegistry.apple_lapis, "apple_lapis");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_lapis)
				,"lll","lal","lll"  
				,'l', new ItemStack(Items.dye, 1, Reference.dye_lapis)  
				,'a', Items.apple); 
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_lapis, new ItemStack(Items.dye, 8, Reference.dye_lapis), 0);// uncraft
	
		ItemRegistry.apple_lapis_rich = new ItemFoodAppleMagic(hungerSmall, true);
		ItemRegistry.apple_lapis_rich.addEffect(lapisPotion, timeLong, PotionRegistry.I); 
		SamsRegistry.registerItem(ItemRegistry.apple_lapis_rich, "apple_lapis_rich");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_lapis_rich) 
				,"lll","lal","lll"  
				,'l', Blocks.lapis_block
				,'a', Items.apple); 
		
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_lapis_rich, new ItemStack(Blocks.lapis_block), 0);// uncraft 
	}
 
	public static void initEmerald()
	{   
		if(!ModLoader.configSettings.appleEmerald) {return;}
		
		ItemRegistry.apple_emerald = new ItemFoodAppleMagic(hungerSmall, false);
		ItemRegistry.apple_emerald.addEffect(emeraldPotion, timeShort, PotionRegistry.V);  
		SamsRegistry.registerItem(ItemRegistry.apple_emerald, "apple_emerald");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_emerald)
				,"lll","lal","lll"  
				,'l', Items.emerald
				,'a', Items.apple);
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_emerald, new ItemStack(Items.emerald, 8),	0);
		 
		ItemRegistry.apple_emerald_rich = new ItemFoodAppleMagic(hungerSmall, true);
		ItemRegistry.apple_emerald_rich.addEffect(emeraldPotion, timeLong, PotionRegistry.V); 
		SamsRegistry.registerItem(ItemRegistry.apple_emerald_rich, "apple_emerald_rich");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_emerald_rich)
				,"lll","lal","lll"  
				,'l', Blocks.emerald_block
				,'a', Items.apple);
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_emerald_rich, new ItemStack(Blocks.emerald_block, 8),	0); 
	} 

	public static void initDiamond()
	{ 
		if(!ModLoader.configSettings.appleDiamond) {return;}
		 
		ItemRegistry.apple_diamond = new ItemFoodAppleMagic(hungerSmall, false);
		ItemRegistry.apple_diamond.addEffect(diamondPotion, timeShort, PotionRegistry.I);  
		SamsRegistry.registerItem(ItemRegistry.apple_diamond, "apple_diamond");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_diamond)
				,"lll","lal","lll"  
				,'l', Items.diamond
				,'a', Items.apple);
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_emerald, new ItemStack(Items.diamond, 8),	0);
		 
		ItemRegistry.apple_diamond_rich = new ItemFoodAppleMagic(hungerSmall, true);
		ItemRegistry.apple_diamond_rich.addEffect(diamondPotion, timeLong, PotionRegistry.I); 
		SamsRegistry.registerItem(ItemRegistry.apple_diamond_rich, "apple_diamond_rich");
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_diamond_rich)
				,"lll","lal","lll"  
				,'l', Blocks.diamond_block
				,'a', Items.apple);
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_diamond_rich, new ItemStack(Blocks.diamond_block, 8),	0); 
	}

	public static void initNether()
	{  
		if(!ModLoader.configSettings.appleNetherStar) {return;}
		
		ItemRegistry.apple_nether_star = new ItemFoodAppleMagic(hungerSmall, true);  
		
		if(PotionRegistry.flying != null)
			ItemRegistry.apple_emerald_rich.addEffect(PotionRegistry.flying.id, timeShort, PotionRegistry.I);  //TODO: why null??
		else System.out.println("FLYING NULL");
		
		SamsRegistry.registerItem(ItemRegistry.apple_nether_star, "apple_nether_star");
		
		GameRegistry.addRecipe(new ItemStack(ItemRegistry.apple_nether_star)
				,"lll","lal","lll"  
				,'l', Items.nether_wart
				,'a', Items.apple);//was nether star? TODO? investigate 
		if(ModLoader.configSettings.uncraftGeneral) 
			GameRegistry.addSmelting(ItemRegistry.apple_nether_star, new ItemStack(Items.nether_wart, 1),	0); 
	} 
	
	
}
