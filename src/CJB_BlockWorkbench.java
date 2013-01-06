package net.minecraft.src;

public class CJB_BlockWorkbench extends BlockWorkbench
{
    protected CJB_BlockWorkbench(int par1)
    {
        super(par1);
    }

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        mod_cjb_quickcraft.posX = par2;
        mod_cjb_quickcraft.posY = par3;
        mod_cjb_quickcraft.posZ = par4;
        return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
    }
}
