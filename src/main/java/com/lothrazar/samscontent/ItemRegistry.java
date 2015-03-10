package com.lothrazar.samscontent;

import com.lothrazar.item.*; 

public class ItemRegistry 
{
	public static ItemBucketStorage itemWater;
	public static ItemBucketStorage itemLava;
	public static ItemEnderBook itemEnderBook = null;
	public static ItemWandBuilding wandBuilding;
	public static ItemWandChest wandChest; 
	public static ItemChestSack itemChestSack;
	public static ItemWandHarvest wandHarvest;
	public static ItemWandTransform wandTransform; 
	public static ItemWandLivestock wandLivestock;
	public static ItemWandProspect wandProspect; 
	 
	public static ItemFoodAppleMagic apple_emerald;
	public static ItemFoodAppleMagic apple_emerald_rich;
	public static ItemFoodAppleMagic apple_diamond; 
	public static ItemFoodAppleMagic apple_lapis;
	public static ItemFoodAppleMagic apple_lapis_rich;
	public static ItemFoodAppleMagic apple_chocolate;
	public static ItemFoodAppleMagic apple_chocolate_rich;
	public static ItemFoodAppleMagic apple_nether_star;
	public static ItemWandFire wandFire;
	public static ItemWandCopyPaste wandCopy;
	public static ItemBaseWand baseWand;
	 
	public static void registerItems()
	{
		ItemBaseWand.Init();
		ItemWandFire.init();
		ItemWandCopyPaste.init();
		
		ItemBucketStorage.initLava();
		
		ItemBucketStorage.initWater();
		//ItemBucketStorage.initMilk();
		ItemWandBuilding.Init();
		 
		ItemWandChest.onInit();

		ItemWandTransform.onInit();

		ItemWandHarvest.onInit();
		
		ItemWandLivestock.onInit();

		ItemWandProspect.onInit();
		
		ItemEnderBook.initEnderbook();
		
		ItemFoodAppleMagic.initEmerald();

		ItemFoodAppleMagic.initDiamond();

		ItemFoodAppleMagic.initLapis();

		ItemFoodAppleMagic.initChocolate();

		ItemFoodAppleMagic.initNether();
	}
}
