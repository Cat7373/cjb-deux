package net.minecraft.src;

import java.util.Iterator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CJB_AnvilContainer extends Container
{
    private CJB_AnvilTileEntity anvil;
    private int lastRepairTime = 0;

    public CJB_AnvilContainer(InventoryPlayer par1InventoryPlayer, CJB_AnvilTileEntity par2TileEntityAnvil)
    {
        this.anvil = par2TileEntityAnvil;
        this.addSlotToContainer(new Slot(par2TileEntityAnvil, 0, 44, 58));
        this.addSlotToContainer(new Slot(par2TileEntityAnvil, 1, 115, 58));
        int var3;

        for (var3 = 0; var3 < 3; ++var3)
        {
            for (int var4 = 0; var4 < 9; ++var4)
            {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3)
        {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, var3, 8 + var3 * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.anvil.repairTime);
    }

    /**
     * Updates crafting matrix; called from onCraftMatrixChanged. Args: none
     */
    public void updateCraftingResults()
    {
        super.detectAndSendChanges();
        Iterator var1 = this.crafters.iterator();

        while (var1.hasNext())
        {
            ICrafting var2 = (ICrafting)var1.next();

            if (this.lastRepairTime != this.anvil.repairTime)
            {
                var2.sendProgressBarUpdate(this, 0, this.anvil.repairTime);
            }
        }

        this.lastRepairTime = this.anvil.repairTime;
    }

    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.anvil.repairTime = par2;
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.anvil.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer plr, int par1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 == 2)
            {
                if (!this.mergeItemStack(var4, 2, 38, true))
                {
                    return null;
                }

                var3.onSlotChange(var4, var2);
            }
            else if (par1 != 1 && par1 != 0)
            {
                if (var4.isItemDamaged())
                {
                    if (!this.mergeItemStack(var4, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (this.anvil.isRepairItem(var4))
                {
                    if (!this.mergeItemStack(var4, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 2 && par1 < 29)
                {
                    if (!this.mergeItemStack(var4, 29, 38, false))
                    {
                        return null;
                    }
                }
                else if (par1 >= 29 && par1 < 38 && !this.mergeItemStack(var4, 2, 29, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 2, 38, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }

            if (var4.stackSize == var2.stackSize)
            {
                return null;
            }

            var3.onPickupFromSlot(plr, var4);
        }

        return var2;
    }
}
