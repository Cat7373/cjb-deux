// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

// Referenced classes of package net.minecraft.src:
//            TileEntity, IInventory, ItemStack, NBTTagCompound, 
//            NBTTagList, World, EntityPlayer

public class CJB_RecyclerTileEntity extends TileEntity
    implements IInventory
{

    public CJB_RecyclerTileEntity()
    {
    	recyclerContents = new ItemStack[9];
        dispenserRandom = new Random();
        recycletime = 0;
    }

    public int getSizeInventory()
    {
        return recyclerContents.length;
    }

    public ItemStack getStackInSlot(int i)
    {
        return recyclerContents[i];
    }

    public ItemStack decrStackSize(int i, int j)
    {
        if(recyclerContents[i] != null)
        {
            if(recyclerContents[i].stackSize <= j)
            {
                ItemStack itemstack = recyclerContents[i];
                recyclerContents[i] = null;
                onInventoryChanged();
                return itemstack;
            }
            ItemStack itemstack1 = recyclerContents[i].splitStack(j);
            if(recyclerContents[i].stackSize == 0)
            {
            	recyclerContents[i] = null;
            }
            onInventoryChanged();
            return itemstack1;
        } else
        {
            return null;
        }
    }

    public ItemStack getRandomStackFromInventory()
    {
        int i = -1;
        for(int k = 0; k < recyclerContents.length; k++)
        {
            if(recyclerContents[k] != null)
            {
                i = k;
            }
        }

        if(i >= 0)
        {
            return decrStackSize(i, 1);
        } else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
    	recyclerContents[i] = itemstack;
        if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
        {
            itemstack.stackSize = getInventoryStackLimit();
        }
        onInventoryChanged();
    }

    public String getInvName()
    {
        return "Recycler";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readFromNBT(nbttagcompound);
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
        recyclerContents = new ItemStack[getSizeInventory()];
        for(int i = 0; i < nbttaglist.tagCount(); i++)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 0xff;
            if(j >= 0 && j < recyclerContents.length)
            {
            	recyclerContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        for(int i = 0; i < recyclerContents.length; i++)
        {
            if(recyclerContents[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                recyclerContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public int getInventoryStackLimit()
    {
        return 64;
    }
    
    public void recycleItem(IRecipe irecipe) {
    	
    	ItemStack aitemstack[];
    	
    	try {   		
    		if (irecipe instanceof ShapedRecipes) {
    			if(irecipe.getRecipeOutput().itemID == Item.cake.shiftedIndex)
    			{
    				dispenseItem(new ItemStack(Item.sugar, 2, 0));
    				dispenseItem(new ItemStack(Item.wheat, 3, 0));
    				dispenseItem(new ItemStack(Item.egg, 1, 0));
    				return;
    			}
    			
    			
    			aitemstack = (ItemStack[])ModLoader.getPrivateValue(ShapedRecipes.class, (ShapedRecipes)irecipe, 2);
    			for (int i = 0 ; i < aitemstack.length ; i++)
    			{
    				if (aitemstack[i] != null) {
    					int itemdamage = aitemstack[i].getItemDamageForDisplay();
    					if(itemdamage < 0) itemdamage = 0;
    					dispenseItem(new ItemStack(aitemstack[i].getItem(), 1, itemdamage));
    				}
    			}
    		} else
    		
    		if (irecipe instanceof ShapelessRecipes) {
    			List list = (List)ModLoader.getPrivateValue(ShapelessRecipes.class, (ShapelessRecipes)irecipe, 1);
    			for (int i = 0 ; i < list.size() ; i++)
    			{
    				int itemdamage = ((ItemStack)list.get(i)).getItemDamageForDisplay();
					if(itemdamage < 0) itemdamage = 0;
    				dispenseItem(new ItemStack(((ItemStack)list.get(i)).getItem(), 1, itemdamage));
    			}
    		}
		} catch (Throwable e) {
		}
		worldObj.playAuxSFX(1000, xCoord, yCoord, zCoord, 0);
    }
    
    public IRecipe canRecycle(ItemStack itemstack) 
    {
    	List recipes = CraftingManager.getInstance().getRecipeList();
    	for (int j = 0 ; j < recipes.size() ; j++) {
    		
    		ItemStack itemstack1 = ((IRecipe) recipes.get(j)).getRecipeOutput();
    		
    		if (itemstack1 == null)
    			continue;
    		
    		stacksize = itemstack1.stackSize;
    		
    		if (itemstack1.itemID == itemstack.itemID && itemstack1.stackSize <= itemstack.stackSize && (isItemUndamaged(itemstack) || itemstack.getItemDamage() == itemstack1.getItemDamage()))
    			return (IRecipe) recipes.get(j);
    	}
    	
    	return null;
    }
    
    public boolean isItemUndamaged (ItemStack itemstack)
    {
    	return Item.itemsList[itemstack.itemID].getMaxDamage() > 0 && itemstack.getItemDamage() <= 0;
    }
    private void dispenseItem(ItemStack itemstack)
    {
    	dispenserRandom = new Random();
        int l = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        int i1 = 0;
        int j1 = 0;
        if(l == 3)
        {
            j1 = 1;
        } else
        if(l == 2)
        {
            j1 = -1;
        } else
        if(l == 5)
        {
            i1 = 1;
        } else
        {
            i1 = -1;
        }
        double d = (double)xCoord + (double)i1 * 0.59999999999999998D + 0.5D;
        double d1 = (double)yCoord + 0.5D;
        double d2 = (double)zCoord + (double)j1 * 0.59999999999999998D + 0.5D;
        if(itemstack != null)
        {
            EntityItem entityitem = new EntityItem(worldObj, d, d1 - 0.29999999999999999D, d2, itemstack);
            entityitem.delayBeforeCanPickup = 10;
            double d3 = dispenserRandom.nextDouble() * 0.10000000000000001D + 0.20000000000000001D;
            entityitem.motionX = (double)i1 * d3;
            entityitem.motionY = 0.20000000298023221D;
            entityitem.motionZ = (double)j1 * d3;
            entityitem.motionX += dispenserRandom.nextGaussian() * 0.0074999998323619366D * 6D;
            entityitem.motionY += dispenserRandom.nextGaussian() * 0.0074999998323619366D * 6D;
            entityitem.motionZ += dispenserRandom.nextGaussian() * 0.0074999998323619366D * 6D;
            worldObj.spawnEntityInWorld(entityitem);
        }
    }
    
    public void updateEntity()
    {
    	if (worldObj.isRemote)
    		return;
    	
    	if (!containsItems()) {
    		recycletime = 0;
    		return;
    	}
    	
    	if(recycletime > 50)
    		recycletime = 50;
    	
    	IRecipe recipe = null;
    	int i = 0;
    	boolean canrecycle = false;
    	
    	for (i = 0 ; i < recyclerContents.length ; i++)
    	{
    		ItemStack itemstack = recyclerContents[i];
    		if (itemstack == null)
    			continue;

    		recipe = canRecycle(itemstack);
    		
    		if (recipe == null)
    			continue;
    		
    		canrecycle = true;
    		break;
    	}
    	
    	if (canrecycle && recycletime >= 50) {
    		decrStackSize(i, stacksize);
    		recycleItem(recipe);
    		recycletime = 0;
    	} else
    	if (canrecycle)
    		recycletime++;
    	else
    		recycletime = 0;
    	
    	try
        {
            ByteArrayOutputStream var13 = new ByteArrayOutputStream();
            DataOutputStream var11 = new DataOutputStream(var13);
            var11.writeInt(xCoord);
            var11.writeInt(yCoord);
            var11.writeInt(zCoord);
            var11.write(worldObj.provider.dimensionId);
            var11.writeInt(recycletime);
            ModLoader.clientSendPacket(new Packet250CustomPayload("CJB|Recycler", var13.toByteArray()));
        }
        catch (IOException var12)
        {
            var12.printStackTrace();
        }
    }
    
    private boolean containsItems() {
    	for (int i = 0 ; i < recyclerContents.length ; i++)
    	{
    		if (recyclerContents[i] != null)
    			return true;
    	}
    	return false;
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this)
        {
            return false;
        }
        return entityplayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
    }

    private ItemStack recyclerContents[];
    private Random dispenserRandom;
    public int recycletime;
    private static int stacksize;

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
