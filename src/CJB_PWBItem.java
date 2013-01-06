package net.minecraft.src;

public class CJB_PWBItem extends Item
{

    public CJB_PWBItem(int i, int j)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(j);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public String getTextureFile()
    {
    	return "/cjb/items.png";
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	if (!world.isRemote) {
        	ModLoader.serverOpenWindow((EntityPlayerMP)CJB.plr, new CJB_PWBContainer(CJB.plr.inventory, CJB.w), 39211, 0, 0, 0);
        }
    	return itemstack;
    }

    public boolean isFull3D()
    {
        return true;
    }
}
