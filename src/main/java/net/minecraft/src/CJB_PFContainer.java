package net.minecraft.src;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class CJB_PFContainer extends Container
{
    private CJB_PFData data;
    private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;

    public CJB_PFContainer(InventoryPlayer inventoryplayer, CJB_PFData pfdata)
    {
        lastCookTime = 0;
        lastBurnTime = 0;
        lastItemBurnTime = 0;
        data = pfdata;
        addSlotToContainer(new CJB_Slot(pfdata, 0, 56, 17));
        addSlotToContainer(new CJB_Slot(pfdata, 1, 56, 53));
        addSlotToContainer(new SlotFurnace(inventoryplayer.player, pfdata, 2, 116, 35));
        for (int i = 0; i < 3; i++)
        {
            for (int k = 0; k < 9; k++)
            {
            	addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
            }
        }

        for (int j = 0; j < 9; j++)
        {
        	addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
        }
    }

    public void updateCraftingResults()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting)crafters.get(i);
            if (lastCookTime != data.furnaceCookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, data.furnaceCookTime);
            }
            if (lastBurnTime != data.furnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, data.furnaceBurnTime);
            }
            if (lastItemBurnTime != data.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, data.currentItemBurnTime);
            }
        }

        lastCookTime = data.furnaceCookTime;
        lastBurnTime = data.furnaceBurnTime;
        lastItemBurnTime = data.currentItemBurnTime;
    }

    public void updateProgressBar(int i, int j)
    {
        if (i == 0)
        {
        	data.furnaceCookTime = j;
        }
        if (i == 1)
        {
        	data.furnaceBurnTime = j;
        }
        if (i == 2)
        {
        	data.currentItemBurnTime = j;
        }
    }

    public boolean canInteractWith(EntityPlayer entityplayer)
    {
        return data.isUseableByPlayer(entityplayer);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer plr, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)inventorySlots.get(i);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i == 2)
            {
                if (!mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }
            }
            else if (i >= 3 && i < 30)
            {
                if (!mergeItemStack(itemstack1, 30, 39, false))
                {
                    return null;
                }
            }
            else if (i >= 30 && i < 39)
            {
                if (!mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 3, 39, false))
            {
                return null;
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
            if (itemstack1.stackSize != itemstack.stackSize)
            {
                slot.onPickupFromSlot(plr, itemstack1);
            }
            else
            {
                return null;
            }
        }
        return itemstack;
    }
}
