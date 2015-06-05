package com.lothrazar.samsarmor;
 
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
 

public class ItemEmeraldArmor extends ItemArmor 
{
	public String textureName;
	public ItemEmeraldArmor(int armorType) 
	{
		//int renderIndex = 0;
		super(ArmorRegistry.MATERIAL_EMERALD, 0, armorType);
		textureName = "emerald_armor";
		this.setCreativeTab(ModArmor.tabSamsContent); 
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
	    return ModArmor.TEXTURE_LOCATION + "textures/models/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}
}
