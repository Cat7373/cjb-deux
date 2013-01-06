package net.minecraft.src;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.world.World;

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
