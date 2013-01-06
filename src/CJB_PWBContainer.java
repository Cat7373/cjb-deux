package net.minecraft.src;

public class CJB_PWBContainer extends ContainerWorkbench
{
    public CJB_PWBContainer(InventoryPlayer inventoryplayer, World world)
    {
    	super(inventoryplayer, world, 0, 0, 0);
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return true;
    }
}
