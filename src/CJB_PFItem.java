package net.minecraft.src;

public class CJB_PFItem extends Item
{
    protected CJB_PFItem(int i)
    {
        super(i);
        setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public String getTextureFile()
    {
    	return "/cjb/items.png";
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	ModLoader.getMinecraftInstance().displayGuiScreen(new CJB_PFGui(entityplayer.inventory, getPFData(itemstack, world)));
    	return itemstack;
    }

    public CJB_PFData getPFData(ItemStack itemstack, World world)
    {
        CJB_PFData data = (CJB_PFData)world.loadItemData(net.minecraft.src.CJB_PFData.class,"CJB_PF_" + itemstack.getItemDamage());
        if (data == null)
        {
            itemstack.setItemDamage(world.getUniqueDataId("CJB_PF"));
            String s1 = "CJB_PF_" + itemstack.getItemDamage();
            data = new CJB_PFData(s1);
            world.setItemData(s1, data);
            data.markDirty();
        }
        return data;
    }

    public void updatePFData(CJB_PFData data)
    {
        boolean flag1 = false;
        boolean flag2 = CJB.modcheats && CJB.instantfurnace;
        if (flag2)
        	data.furnaceBurnTime = 1500;
        
        if (data.furnaceBurnTime > 0)
        {
        	data.furnaceBurnTime--;
        	data.markDirty();
        }
            if (data.furnaceBurnTime == 0 && data.canSmelt())
            {
            	data.currentItemBurnTime = data.furnaceBurnTime = data.getItemBurnTime(data.furnaceItemStacks[1]);
                if (data.furnaceBurnTime > 0)
                {
                	flag1 = true;

                    if (data.furnaceItemStacks[1] != null)
                    {
                    	data.furnaceItemStacks[1].stackSize--;

                        if (data.furnaceItemStacks[1].stackSize == 0)
                        {
                            Item item = data.furnaceItemStacks[1].getItem().getContainerItem();
                            data.furnaceItemStacks[1] = item != null ? new ItemStack(item) : null;
                        }
                    }
                }
            }
            if (data.isBurning() && data.canSmelt())
            {
            	data.furnaceCookTime++;
                if (data.furnaceCookTime == 200 || flag2)
                {
                	data.furnaceCookTime = 0;
                	data.smeltItem();
                    flag1 = true;
                }
            }
            else
            {
            	data.furnaceCookTime = 0;
            }
        if (flag1)
        {
            data.onInventoryChanged();
        }
        
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
    	if (world.isRemote) {
	        CJB_PFData data = getPFData(itemstack, world);
	        updatePFData(data);
    	}
    }
    
    @Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.setItemDamage(world.getUniqueDataId("CJB_PF"));
        String s = "CJB_PF_" + itemstack.getItemDamage();
        CJB_PFData data = new CJB_PFData(s);
        world.setItemData(s, data);
        data.markDirty();
    }
}
