package com.lothrazar.samsfancysigns;

import java.util.Random;

import net.minecraft.block.BlockSign;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity; 
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomSign extends BlockSign 
{
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityCustomSign();
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.sign;//TODO: my custom item /block/type
	}    
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            return tileentity instanceof TileEntityCustomSign ? ((TileEntityCustomSign)tileentity).func_174882_b(playerIn) : false;
        }
    }

    @SideOnly(Side.CLIENT)
	@Override
    public Item getItem(World worldIn, BlockPos pos)
    {
        return Items.sign;//TODO: my custom item /block/type
    }
}
