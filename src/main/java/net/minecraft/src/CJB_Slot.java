package net.minecraft.src;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CJB_Slot extends Slot
{


    public CJB_Slot(IInventory iinventory, int i, int j, int k)
    {
        super(iinventory, i, j, k);
    }

    public boolean isItemValid(ItemStack itemstack)
    {
    	if ( mod_cjb_items.pfb && itemstack.itemID == mod_cjb_items.PF.shiftedIndex)
    		return false;
    	
    	if (itemstack.itemID == mod_cjb_items.Sack.shiftedIndex)
    		return false;
    	
        return super.isItemValid(itemstack);
    }
}
