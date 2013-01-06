package net.minecraft.src;

public class CJB_SackItem extends Item
{
    protected CJB_SackItem(int i)
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
    public int getIconFromDamage(int i)
    {
    	World world = ModLoader.getMinecraftInstance().theWorld;
    	if (world != null) {
    		CJB_SackData sack = getSackData(new ItemStack(this, 1, i), world);
        	if (sack.sackSize == 1)
        		return 6;
        	else if (sack.sackSize == 2)
        		return 7;
    	}
    	
        return 5;
    }
    
    @Override
    public String getItemDisplayName(ItemStack itemstack)
    {
    	World world = ModLoader.getMinecraftInstance().theWorld;
    	
    	if (world == null) return "Iron Sack";
    	
    	CJB_SackData sack = getSackData(itemstack, world);
    	
    	if (sack.sackSize == 0)
    		return "Iron Sack";
    	else if (sack.sackSize == 1)
    		return "Gold Sack";
    	else if (sack.sackSize == 2)
    		return "Diamond Sack";
    	
        return "Iron Sack";
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	if (world.isRemote) return itemstack;
    	
    	ModLoader.serverOpenWindow((EntityPlayerMP) entityplayer, new CJB_SackContainer(entityplayer.inventory, getSackData(itemstack, world)), 39204, 0, 0, 0);
    	
    	//ModLoader.getMinecraftInstance().displayGuiScreen(new CJB_SackGui(entityplayer.inventory, getSackData(itemstack, world)));
    	return itemstack;
    }

    public static CJB_SackData getSackData(ItemStack itemstack, World world)
    {   	
        String s = "CJB_SACK_" + itemstack.getItemDamage();
        CJB_SackData data = (CJB_SackData)world.loadItemData(CJB_SackData.class, s);
        
        if (data == null)
        {
        	int i = itemstack.getItemDamage();
            itemstack.setItemDamage(world.getUniqueDataId("CJB_SACK"));
            String s1 = "CJB_SACK_" + itemstack.getItemDamage();
            data = new CJB_SackData(s1);
            data.sackSize = i;
            world.setItemData(s1, data);
            data.markDirty();
        } 
        
        return data;
    }

    @Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	if (world.isRemote) return;
    	
    	int i = itemstack.getItemDamage();
        itemstack.setItemDamage(world.getUniqueDataId("CJB_SACK"));
        String s = "CJB_SACK_" + itemstack.getItemDamage();
        CJB_SackData data = new CJB_SackData(s);
        data.sackSize = i;
        world.setItemData(s, data);
        data.markDirty();
    }
}
