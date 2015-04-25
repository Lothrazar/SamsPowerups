package com.lothrazar.samscontent.block;

import java.util.Random;

import com.lothrazar.samscontent.ModMain;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockFragile extends Block
{
	public BlockFragile() 
	{
		super(Material.wood);
		this.setCreativeTab(ModMain.tabSamsContent);
		this.setTickRandomly(true);
		this.setHardness(3F);
		this.setResistance(5F); 
		this.setStepSound(soundTypeWood);
	} 
	@Override
	public boolean isOpaqueCube() 
	{
		return false;//transparency 
	}
	@SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;//transparency 
    } 
	@Override
	public void updateTick(World worldObj,  BlockPos pos, IBlockState state,  Random rand)
    {  
		
		//if(worldObj.rand.nextInt(6) == 0)
		//{
			worldObj.destroyBlock(pos, true); 
		//}
    }
	
	//TODO: let player place it in midair, even if it is not against a block
}
