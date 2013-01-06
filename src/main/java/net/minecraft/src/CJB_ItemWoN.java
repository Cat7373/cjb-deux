package net.minecraft.src;

public class CJB_ItemWoN extends Item
{

    public CJB_ItemWoN(int i, int j)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(j);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public String getTextureFile()
    {
    	return "/cjb/items.png";
    }
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	if (itemstack.itemID == mod_cjb_items.WoNe.shiftedIndex) return itemstack;
    	
        float pitch = entityplayer.rotationPitch;
        float yaw = entityplayer.rotationYaw;
        double x = entityplayer.posX;
        double y = entityplayer.posY + 1.62D - (double)entityplayer.yOffset;
        double z = entityplayer.posZ;
        
        Vec3 vec1 = Vec3.vec3dPool.getVecFromPool(x, y, z);
        
        float f1 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float f2 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float f3 = -MathHelper.cos(-pitch * 0.01745329F);
        float f4 = MathHelper.sin(-pitch * 0.01745329F);
        float f5 = f2 * f3;
        float f6 = f1 * f3;
        double distance = 30D;
        
        Vec3 vec2 = vec1.addVector(f5 * distance, f4 * distance, f6 * distance);
        MovingObjectPosition mo = world.rayTraceBlocks_do(vec1, vec2, false);
        if (mo == null)
        	return itemstack;
        
        if (mo.typeOfHit == EnumMovingObjectType.TILE)
        {
        	int bx = mo.blockX;
        	int by = mo.blockY;
        	int bz = mo.blockZ;
        	int bs = mo.sideHit;
        	
        	if (bs == 0) --by;
        	if (bs == 1) ++by;
        	if (bs == 2) --bz;
        	if (bs == 3) ++bz;
        	if (bs == 4) --bx;
        	if (bs == 5) ++bx;
        	world.spawnEntityInWorld(new EntityLightningBolt(world, (double)bx + 0.5D, (double)by + 0.5D, (double)bz + 0.5D));

        	entityplayer.swingItem();

        	if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) 
        		return new ItemStack(mod_cjb_items.WoNe,1);
        	
        	itemstack.damageItem(1, entityplayer);        	
        }
    	return itemstack;
    }

    public boolean isFull3D()
    {
        return true;
    }
}
