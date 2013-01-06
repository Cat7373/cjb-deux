package net.minecraft.src;

public class CJB_EntityEgg extends EntityThrowable
{
    public CJB_EntityEgg(World par1World)
    {
        super(par1World);
    }

    public CJB_EntityEgg(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World, par2EntityLiving);
    }

    public CJB_EntityEgg(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when the throwable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
        if (par1MovingObjectPosition.entityHit != null && par1MovingObjectPosition.entityHit instanceof EntityAnimal)
        {
        	EntityAnimal ent = (EntityAnimal) par1MovingObjectPosition.entityHit;
        	
        	if (ent instanceof EntityTameable && ((EntityTameable) ent).isTamed()) return;
        	
        	CJB_ItemMonsterPlacer item = (CJB_ItemMonsterPlacer) mod_cjb_items.mobplacer;
        	ItemStack itemstack = new ItemStack(item, 1);
        	
        	byte b = 0;
        	
        	try {
        		b = ent.dataWatcher.getWatchableObjectByte(16);
        	} catch (Throwable e) {}
        	
        	item.onCatch(itemstack, worldObj, ent, b);
        	
        	EntityItem itementity = new EntityItem(ent.worldObj, ent.posX, ent.posY, ent.posZ, itemstack);
        	ent.worldObj.spawnEntityInWorld(itementity);
        	
        	ent.setDead();
        	setDead();
        }

        if (par1MovingObjectPosition.typeOfHit == EnumMovingObjectType.TILE && !worldObj.isRemote && rand.nextInt(8) == 0)
        {
            byte byte0 = 1;

            if (rand.nextInt(32) == 0)
            {
                byte0 = 4;
            }

            for (int j = 0; j < byte0; j++)
            {
                EntityChicken entitychicken = new EntityChicken(worldObj);
                entitychicken.setGrowingAge(-24000);
                entitychicken.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
                worldObj.spawnEntityInWorld(entitychicken);
            }
        }

        for (int i = 0; i < 8; i++)
        {
            worldObj.spawnParticle("snowballpoof", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!worldObj.isRemote)
        {
            setDead();
        }
    }
}
