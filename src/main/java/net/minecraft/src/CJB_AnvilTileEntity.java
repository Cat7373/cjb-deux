package net.minecraft.src;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class CJB_AnvilTileEntity extends TileEntity implements IInventory
{
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private ItemStack[] itemstacks = new ItemStack[2];
    public int repairTime = 0;
    private Random rand = new Random();

    /**
     * Returns the number of slots in the inventory.
     */
    public int getSizeInventory()
    {
        return this.itemstacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1)
    {
        return this.itemstacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int par1, int par2)
    {
        if (this.itemstacks[par1] != null)
        {
            ItemStack var3;

            if (this.itemstacks[par1].stackSize <= par2)
            {
                var3 = this.itemstacks[par1];
                this.itemstacks[par1] = null;
                return var3;
            }
            else
            {
                var3 = this.itemstacks[par1].splitStack(par2);

                if (this.itemstacks[par1].stackSize == 0)
                {
                    this.itemstacks[par1] = null;
                }

                return var3;
            }
        }
        else
        {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.itemstacks[par1] != null)
        {
            ItemStack var2 = this.itemstacks[par1];
            this.itemstacks[par1] = null;
            return var2;
        }
        else
        {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.itemstacks[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName()
    {
        return "Anvil";
    }

    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList var2 = par1NBTTagCompound.getTagList("Items");
        this.itemstacks = new ItemStack[this.getSizeInventory()];

        for (int var3 = 0; var3 < var2.tagCount(); ++var3)
        {
            NBTTagCompound var4 = (NBTTagCompound)var2.tagAt(var3);
            byte var5 = var4.getByte("Slot");

            if (var5 >= 0 && var5 < this.itemstacks.length)
            {
                this.itemstacks[var5] = ItemStack.loadItemStackFromNBT(var4);
            }
        }

        this.repairTime = par1NBTTagCompound.getShort("RepairTime");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("RepairTime", (short)this.repairTime);
        NBTTagList var2 = new NBTTagList();

        for (int var3 = 0; var3 < this.itemstacks.length; ++var3)
        {
            if (this.itemstacks[var3] != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte)var3);
                this.itemstacks[var3].writeToNBT(var4);
                var2.appendTag(var4);
            }
        }

        par1NBTTagCompound.setTag("Items", var2);
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Returns true if the furnace is currently burning
     */
    public boolean isRepairing()
    {
    	if (itemstacks[0] == null || itemstacks[1] == null)
    		return false;
    	
        return this.repairTime > 0;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void updateEntity()
    {
        boolean var1 = this.repairTime > 0;
        boolean var2 = false;
        
        if (var1 && this.rand.nextInt(50) == 0) {
        	this.worldObj.playSoundEffect(xCoord, yCoord, zCoord, "cjb.anvil", 1f, 1.5f + rand.nextFloat());
        }
        
        if (this.canRepair())
        {
            ++this.repairTime;

            if (this.repairTime == this.getRequiredRepairTime())
            {
                this.repairTime = 0;
                this.repairItem();
                var2 = true;
            }
        }
        else
        {
            this.repairTime = 0;
        }
        
        
        if (var2)
        {
            this.onInventoryChanged();
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canRepair()
    {
    	ItemStack item1 = this.itemstacks[0];
    	ItemStack item2 = this.itemstacks[1];
    	
        if (item1 == null || item2 == null)
        {
        	this.repairTime = 0;
            return false;
        }
        else
        {
        	if (item1.isItemDamaged()){
        		if (!item1.isItemEnchanted()) {
        			if (item2.getItem() instanceof CJB_ItemHammer)
        				return true;
        		} else {
        			if (!item2.isItemDamaged() && item1.itemID == item2.itemID) {
        				return true;
        			}
        		}
        	}
        	
        	return false;
        }
    }
    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void repairItem()
    {
        if (this.canRepair())
        {
        	if (!itemstacks[0].isItemEnchanted()) {
        		
        		int i = itemstacks[0].getItemDamage();
        		
        		i -= itemstacks[1].getMaxDamage();
        		
        		if (i < 0)
        			i = 0;
        		
        		itemstacks[0].setItemDamage(i);
        		--this.itemstacks[1].stackSize;
        		
        		if (this.itemstacks[1].stackSize == 0)
        			this.itemstacks[1] = null;
        	} else {
        		itemstacks[0].setItemDamage(0);
        		--this.itemstacks[1].stackSize;
        		
        		if (this.itemstacks[1].stackSize == 0)
        			this.itemstacks[1] = null;
        	}
        }
    }
    
    public ItemStack getSuggestedItem() {
    	
    	ItemStack item = itemstacks[0];
    	
    	if (item == null || !item.isItemDamaged()) return null;
    	
    	int i = item.getItemDamage();
    	
    	if (!item.isItemEnchanted()) {
	    	if (i < mod_cjb_items.HammerWood.getMaxDamage())
	    		return new ItemStack(mod_cjb_items.HammerWood);
	    	if (i < mod_cjb_items.HammerStone.getMaxDamage())
	    		return new ItemStack(mod_cjb_items.HammerStone);
	    	if (i < mod_cjb_items.HammerIron.getMaxDamage())
	    		return new ItemStack(mod_cjb_items.HammerIron);
	    	if (i < mod_cjb_items.HammerDiamond.getMaxDamage())
	    		return new ItemStack(mod_cjb_items.HammerDiamond);
    	} else {
    		return new ItemStack(item.getItem());
    	}
    	return null;
    }
    
    public int getRequiredRepairTime() {
    	
    	if (itemstacks[0] != null && itemstacks[0].isItemEnchanted()) {
    		NBTTagList var3 = itemstacks[0].getEnchantmentTagList();
    		int lvl = 1;
            if (var3 != null)
            {
                for (int var4 = 0; var4 < var3.tagCount(); ++var4)
                {
                    short var6 = ((NBTTagCompound)var3.tagAt(var4)).getShort("lvl");
                    lvl += var6;
                }
            }
            return 200 + (lvl * itemstacks[0].getItemDamage());
    	}
    	
    	if (itemstacks[0] != null)
    		return 200;
    	
    	return 0;
    }
    
    public boolean isRepairItem(ItemStack stack) {
    	if (itemstacks[0] != null && itemstacks[0].isItemDamaged()) {
    		if (itemstacks[0].isItemEnchanted() && !stack.isItemDamaged()) {
    			if (itemstacks[0].itemID == stack.itemID)
    				return true;
    			else
    				return false;
    		} else {
	    		if (stack.itemID == mod_cjb_items.HammerWood.shiftedIndex) return true;
	    		if (stack.itemID == mod_cjb_items.HammerStone.shiftedIndex) return true;
	    		if (stack.itemID == mod_cjb_items.HammerIron.shiftedIndex) return true;
	    		if (stack.itemID == mod_cjb_items.HammerDiamond.shiftedIndex) return true;
    		}
    	}
    	
    	return false;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer)
    {
        return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    public void openChest() {}

    public void closeChest() {}
}
