package com.lothrazar.backport;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFenceGateSimple extends BlockFenceGate
{
	private IIcon icon;

	public BlockFenceGateSimple()
	{
		super();
		this.setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood);
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return this.icon;//Blocks.planks.getBlockTextureFromSide(side);
    }


	@Override
    public boolean canPlaceBlockAt(World w, int x, int y, int z)
    {
       return true;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        //this.icon = new IIcon[field_150096_a.length];

        //"planks_" + spruce
        this.icon = p_149651_1_.registerIcon( this.getTextureName());
   
    }
}