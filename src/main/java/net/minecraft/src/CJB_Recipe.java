package net.minecraft.src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class CJB_Recipe {

	public static List<CJB_Recipe> recipes = new ArrayList<CJB_Recipe>();

	public int id;
	public ItemStack result;
	public ItemStack[] items;
	public HashMap<Integer, Integer> ids = new HashMap();
	public String category;
	public InventoryPlayer inv = new InventoryPlayer(null);

	public static void load() {
			recipes = new ArrayList<CJB_Recipe>();
			for (int i = 0; i < CraftingManager.getInstance().getRecipeList().size(); i++) {
				try {
					CJB_Recipe rec = new CJB_Recipe();
					rec.id = i;
					IRecipe irecipe = (IRecipe) CraftingManager.getInstance().getRecipeList().get(i);
					rec.result = irecipe.getRecipeOutput();
					if (rec.result == null) continue;
					
					rec.ids.put(rec.result.itemID, 0);
					
					if (irecipe instanceof ShapedRecipes) {
						rec.items = new ItemStack[9];
						
						int w = ((ShapedRecipes)irecipe).recipeWidth;
						int h = ((ShapedRecipes)irecipe).recipeHeight;
						if (w*h > 9) continue;
						
						ItemStack temp[] = ((ShapedRecipes)irecipe).recipeItems;
						for (int j = 0 ; j < temp.length ; j++) {
							int x = j % w;
							int y = j / w;
							rec.items[x + y * 3] = temp[j];
						}
						
						
					} else if (irecipe instanceof ShapelessRecipes) {
						List list = ((ShapelessRecipes)irecipe).recipeItems;
						rec.items = new ItemStack[list.size()];
						for (int j = 0; j < list.size(); j++) {
							rec.items[j] = (ItemStack) list.get(j);
						}
					} else if (irecipe instanceof ShapelessOreRecipe) {
						List list = (List) ModLoader.getPrivateValue(ShapelessOreRecipe.class, (ShapelessOreRecipe) irecipe, 1);
						rec.items = new ItemStack[list.size()];
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j) instanceof ArrayList) {
								List reqitems = (List) list.get(j);
								for (int k = 0 ; k < reqitems.size(); k++) {
									rec.items[j + k] = (ItemStack) reqitems.get(k);
								}
							} else {
								rec.items[j] = (ItemStack) list.get(j);
							}
						}
					}else if (irecipe instanceof ShapedOreRecipe) {
						rec.items = new ItemStack[9];
						int w = (Integer) ModLoader.getPrivateValue(ShapedOreRecipe.class, (ShapedOreRecipe) irecipe, 4);
						int h = (Integer) ModLoader.getPrivateValue(ShapedOreRecipe.class, (ShapedOreRecipe) irecipe, 5);
						if (w*h > 9) continue;
						
						Object temp[] = (Object[]) ModLoader.getPrivateValue(ShapedOreRecipe.class, (ShapedOreRecipe) irecipe, 3);
						
						for (int j = 0 ; j < temp.length ; j++) {
							if (temp[j] instanceof ArrayList) {
								List reqitems = (List) temp[j];
								for (int k = 0 ; k < reqitems.size(); k++) {
									int x = k;
									int y = j;
									if (temp.length <= 3)
										y = j * 3;
									rec.items[y+k] = (ItemStack) reqitems.get(k);
								}
							} else {
								int x = j % w;
								int y = j / w;
								rec.items[(x+y*3)] = (ItemStack) temp[j];
							}
						}
					}
	
					for (int j = 0; j < rec.items.length; j++) {
						if (rec.items[j] == null)
							continue;
	
						if (!rec.ids.containsKey(rec.items[j].itemID)) {
							rec.ids.put(rec.items[j].itemID, 0);
						}
					}
	
					recipes.add(rec);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
	}
	
	public void setFakeInventory(EntityPlayer player) {
		inv = new InventoryPlayer(player);
		inv.copyInventory(player.inventory);
	}
	
	public void setRealInventory(EntityPlayer player) {
		inv = player.inventory;
	}
	
	public boolean isCraftable(EntityPlayer player, boolean flag)
	{
		if (flag) {
			inv = new InventoryPlayer(player);
			inv.copyInventory(player.inventory);
		}
		for (ItemStack item : items)
		{
			if (item == null) continue;
			if (!consumeItem(item)) {
				return false; 
			} else {
				
				if (item.getItem().hasContainerItem()) {
					if (!inv.addItemStackToInventory(new ItemStack(item.getItem().getContainerItem(), 1, 0))) return false;
				}
			}		
		}
		
		if (!inv.addItemStackToInventory(new ItemStack(result.getItem(), result.stackSize, result.getItemDamageForDisplay()))) return false;
		
		return true;
	}
	
	public void craftRecipe(EntityPlayer plr) {
		
		if (true) {
			inv = plr.inventory;
			for (int i = 0 ; i < items.length ; i++)
			{
				if (items[i] == null) continue;
				if (!consumeItemMP(items[i], (EntityClientPlayerMP) plr, items, i)) {
					return; 
				}
			}
			
			Minecraft mc = ModLoader.getMinecraftInstance();
			mc.playerController.windowClick(plr.openContainer.windowId, 0, 0, 0, plr);
			
			int i = 0;
			int size = result.stackSize;
			do {		
				i = storeItemStack(result);
				
				if (i < 0)
					i = inv.getFirstEmptyStack();
				
				if (i >= 0 ) {
					if (i < 9)
						i += 27;
					else
						i -= 9;
					mc.playerController.windowClick(plr.openContainer.windowId, i + 10, 1, 0, plr);
					size--;
					continue;
				}
				break;
			} while (size > 0);
		}
	}
	
	private int storeItemStack(ItemStack par1ItemStack)
    {
        for (int var2 = 0; var2 < inv.mainInventory.length; ++var2)
        {
            if (inv.mainInventory[var2] != null && inv.mainInventory[var2].itemID == par1ItemStack.itemID && inv.mainInventory[var2].isStackable() && inv.mainInventory[var2].stackSize < inv.mainInventory[var2].getMaxStackSize() && inv.mainInventory[var2].stackSize < inv.getInventoryStackLimit() && (!inv.mainInventory[var2].getHasSubtypes() || inv.mainInventory[var2].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(inv.mainInventory[var2], par1ItemStack))
            {
                return var2;
            }
        }

        return -1;
    }
	
	private int getInventorySlotContainItemAndDamage(int par1, int par2)
    {
        for (int var3 = 0; var3 < inv.mainInventory.length; ++var3)
        {
            if (inv.mainInventory[var3] != null && inv.mainInventory[var3].itemID == par1 && inv.mainInventory[var3].getItemDamage() == par2)
            {
                return var3;
            }
        }

        return -1;
    }
	
	public boolean containsItem(ItemStack item){
		
		if (item == null) return false;
		
		for (ItemStack is : items)
		{
			if (is == null) continue;
			if (is.itemID != item.itemID) continue;
			
			if (is.getItemDamage() != -1 && is.getItemDamage() != item.getItemDamage()) continue;
			
			return true;
		}
		
		if (result != null) {
			if (item.itemID != result.itemID) return false;
			
			if (result.getItemDamage() != -1 && result.getItemDamage() != item.getItemDamage()) return false;
			
			return true;
		}
		
		return false;
	}
	
	public boolean containsItemResult(ItemStack item){
		if (item != null && result != null) {
			if (item.itemID != result.itemID) return false;
			
			if (result.getItemDamage() != -1 && result.getItemDamage() != item.getItemDamage()) return false;
			
			return true;
		}
		return false;
	}
	
	public CJB_Recipe getRecipeResult(ItemStack item)
	{
		for (CJB_Recipe rec : recipes)
		{
			if (rec.containsItemResult(item))
				return rec;
		}
		
		return null;
	}
	
	public boolean consumeItem(ItemStack is) {
		
		for (int i = 0 ; i < inv.mainInventory.length ; i++)
		{
			if (inv.mainInventory[i] == null || is == null) continue;
			if (is.itemID != inv.mainInventory[i].itemID) continue;
			
			if (is.getItemDamage() != -1 && is.getItemDamage() != inv.mainInventory[i].getItemDamage()) continue;
			
			if (--inv.mainInventory[i].stackSize <= 0)
				inv.mainInventory[i] = null;
			
			CJB.useditems[i] = true;
			return true;
		}
		
		return false;
	}
	
	public boolean consumeItemMP(ItemStack is, EntityClientPlayerMP plr, ItemStack[] isr, int j) {
		
		for (int i = 0 ; i < inv.mainInventory.length ; i++)
		{
			if (inv.mainInventory[i] == null || is == null) continue;
			if (is.itemID != inv.mainInventory[i].itemID) continue;
			
			if (is.getItemDamage() != -1 && is.getItemDamage() != inv.mainInventory[i].getItemDamage()) continue;
			
			int k = i;
			if (k < 9)
				k += 27;
			else
				k -= 9;
			
			
			Minecraft mc = ModLoader.getMinecraftInstance();
			mc.playerController.windowClick(plr.openContainer.windowId, k+10, 0, 0, plr);
			mc.playerController.windowClick(plr.openContainer.windowId, j+1, 1, 0, plr);
			mc.playerController.windowClick(plr.openContainer.windowId, k+10, 0, 0, plr);
			
			/*if (--inv.mainInventory[i].stackSize <= 0)
				inv.mainInventory[i] = null;*/
			
			CJB.useditems[i] = true;
			return true;
		}
		
		return false;
	}
	
	public static CJB_Recipe getRecipe(int i) {
		for (CJB_Recipe rec : recipes) {
			if (rec.id == i)
				return rec;
		}
		
		return null;
	}
	
	public int getCategory(Item item) {
		
		if (item == null) return -1;
		
		CreativeTabs t = item.getCreativeTab();
		
		if (t == null) return 9;

		if (t.equals(CreativeTabs.tabFood))
			return 1;
		
		if (t.equals(CreativeTabs.tabTools))
			return 2;
		
		if (t.equals(CreativeTabs.tabBlock))
			return 4;
		
		if (t.equals(CreativeTabs.tabCombat))
			return 3;
		
		if (t.equals(CreativeTabs.tabTransport))
			return 7;
		
		if (t.equals(CreativeTabs.tabRedstone))
			return 8;
		
		if (t.equals(CreativeTabs.tabMaterials))
			return 6;
		
		if (t.equals(CreativeTabs.tabDecorations))
			return 5;
		
		return 9;
	}
}
