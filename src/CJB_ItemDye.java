package net.minecraft.src;

public class CJB_ItemDye extends ItemDye
{

    public CJB_ItemDye(int i)
    {
        super(i);
    }
    
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
    	if(par1ItemStack.getItemDamage() == 15 && !par3World.isRemote)
        {
            int i1 = par3World.getBlockId(par4, par5, par6);
            if(i1 == Block.dirt.blockID)
            {
            	par3World.setBlockWithNotify(par4, par5, par6, Block.grass.blockID);
            	par1ItemStack.stackSize--;
                return true;
            }
        }
    	return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
    }
}
