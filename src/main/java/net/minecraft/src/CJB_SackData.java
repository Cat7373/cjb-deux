package net.minecraft.src;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

public class CJB_SackData extends WorldSavedData
    implements IInventory
{
    private ItemStack chestContents[];
    public int sackSize;

    public CJB_SackData(String s)
    {
    	super(s);
        chestContents = new ItemStack[36];
    }

    public int getSizeInventory()
    {
        return 9 * (sackSize + 1);
    }

    public ItemStack getStackInSlot(int i)
    {
        return chestContents[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if (chestContents[i] != null)
        {
            if (chestContents[i].stackSize <= j)
            {
                ItemStack itemstack = chestContents[i];
                chestContents[i] = null;
                onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = chestContents[i].splitStack(j);
            if (chestContents[i].stackSize == 0)
            {
                chestContents[i] = null;
            }
            onInventoryChanged();
            return itemstack1;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        chestContents[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    public String getInvName()
    {
    	String s = "Iron Sack";
    	if (sackSize == 0)
    		s = "Iron Sack";
    	else if (sackSize == 1)
    		s = "Gold Sack";
    	else if (sackSize == 2)
    		s = "Diamond Sack";
        return s;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
    	sackSize = nbttagcompound.getInteger("sackSize");
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        chestContents = new ItemStack[getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if (j >= 0 && j < chestContents.length)
            {
                chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
    	nbttagcompound.setInteger("sackSize", sackSize);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < chestContents.length; i++)
        {
            if (chestContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }
        nbttagcompound.setTag("Items", nbttaglist);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }

	public void onInventoryChanged() {
		markDirty();
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	public void openChest() {
		
	}

	public void closeChest() {
		markDirty();
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}
}
