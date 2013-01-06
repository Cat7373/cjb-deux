// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            Container, Slot, TileEntityDispenser, IInventory, 
//            EntityPlayer

public class CJB_RecyclerContainer extends Container
{

    public CJB_RecyclerContainer(IInventory iinventory, CJB_RecyclerTileEntity tileentityrecycler)
    {
        for(int i = 0; i < 3; i++)
        {
            for(int l = 0; l < 3; l++)
            {
                addSlotToContainer(new Slot(tileentityrecycler, l + i * 3, 62 + l * 18, 17 + i * 18));
            }

        }

        for(int j = 0; j < 3; j++)
        {
            for(int i1 = 0; i1 < 9; i1++)
            {
            	addSlotToContainer(new Slot(iinventory, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
            }

        }

        for(int k = 0; k < 9; k++)
        {
        	addSlotToContainer(new Slot(iinventory, k, 8 + k * 18, 142));
        }

    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer plr, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(i);
        if(slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(i < 9)
            {
                if(!mergeItemStack(itemstack1, 9, 45, true))
                {
                    return null;
                }
            } else
            if(!mergeItemStack(itemstack1, 0, 9, false))
            {
                return null;
            }
            if(itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }
            if(itemstack1.stackSize != itemstack.stackSize)
            {
                slot.onPickupFromSlot(plr, itemstack1);
            } else
            {
                return null;
            }
        }
        return itemstack;
    }

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return true;
	}
}
