package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

public class CJB_PFData extends WorldSavedData implements IInventory
{   
    public ItemStack furnaceItemStacks[];
    public int furnaceBurnTime;
    public int currentItemBurnTime;
    public int furnaceCookTime;

    public CJB_PFData(String s)
    {
        super(s);
        furnaceItemStacks = new ItemStack[3];
        furnaceBurnTime = 0;
        currentItemBurnTime = 0;
        furnaceCookTime = 0;
    }
    
    public int getSizeInventory()
    {
        return furnaceItemStacks.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        return furnaceItemStacks[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if (furnaceItemStacks[i] != null)
        {
            if (furnaceItemStacks[i].stackSize <= j)
            {
                ItemStack itemstack = furnaceItemStacks[i];
                furnaceItemStacks[i] = null;
                return itemstack;
            }
            ItemStack itemstack1 = furnaceItemStacks[i].splitStack(j);
            if (furnaceItemStacks[i].stackSize == 0)
            {
                furnaceItemStacks[i] = null;
            }
            return itemstack1;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        furnaceItemStacks[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
    }

    public String getInvName()
    {
        return "Furnace";
    }
    
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        furnaceItemStacks = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte byte0 = nbttagcompound1.getByte("Slot");
            if (byte0 >= 0 && byte0 < furnaceItemStacks.length)
            {
                furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        furnaceBurnTime = nbttagcompound.getShort("BurnTime");
        furnaceCookTime = nbttagcompound.getShort("CookTime");
        currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setShort("BurnTime", (short)furnaceBurnTime);
        nbttagcompound.setShort("CookTime", (short)furnaceCookTime);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < furnaceItemStacks.length; i++)
        {
            if (furnaceItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                furnaceItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }
    
    public int getInventoryStackLimit()
    {
        return 64;
    }

    public int getCookProgressScaled(int i)
    {
        return (furnaceCookTime * i) / 200;
    }

    public int getBurnTimeRemainingScaled(int i)
    {
        if (currentItemBurnTime == 0)
        {
            currentItemBurnTime = 200;
        }
        return (furnaceBurnTime * i) / currentItemBurnTime;
    }

    public boolean isBurning()
    {
        return furnaceBurnTime > 0;
    }

    public boolean canSmelt()
    {
        if (furnaceItemStacks[0] == null)
        {
            return false;
        }
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);
        if (itemstack == null)
        {
            return false;
        }
        if (furnaceItemStacks[2] == null)
        {
            return true;
        }
        if (!furnaceItemStacks[2].isItemEqual(itemstack))
        {
            return false;
        }
        if (furnaceItemStacks[2].stackSize < getInventoryStackLimit() && furnaceItemStacks[2].stackSize < furnaceItemStacks[2].getMaxStackSize())
        {
            return true;
        }
        return furnaceItemStacks[2].stackSize < itemstack.getMaxStackSize();
    }

    public void smeltItem()
    {
        if (!canSmelt())
        {
            return;
        }
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);
        if (furnaceItemStacks[2] == null)
        {
            furnaceItemStacks[2] = itemstack.copy();
        }
        else if (furnaceItemStacks[2].itemID == itemstack.itemID)
        {
            furnaceItemStacks[2].stackSize++;
        }
        if (furnaceItemStacks[0].stackSize == 0)
        {
            Item item = furnaceItemStacks[0].getItem().getContainerItem();
            furnaceItemStacks[0] = item != null ? new ItemStack(item) : null;
        }
    }

    public int getItemBurnTime(ItemStack itemstack)
    {
        if (itemstack == null)
        {
            return 0;
        }
        int i = itemstack.getItem().shiftedIndex;
        if (i < 256 && Block.blocksList[i].blockMaterial == Material.wood)
        {
            return 300;
        }
        if (i == Item.stick.shiftedIndex)
        {
            return 100;
        }
        if (i == Item.coal.shiftedIndex)
        {
            return 1600;
        }
        if (i == Item.bucketLava.shiftedIndex)
        {
            return 20000;
        }
        if (i == Block.sapling.blockID)
        {
            return 100;
        }
        if (i == Item.blazeRod.shiftedIndex)
        {
            return 2400;
        }
        else
        {
            return ModLoader.addAllFuel(i, itemstack.getItemDamage());
        }
    }

	@Override
	public void onInventoryChanged() {
		markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}
}
