package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class CJB_ItemMonsterPlacer extends Item
{
    public CJB_ItemMonsterPlacer(int par1)
    {
        super(par1);
        maxStackSize = 1;
    }
    
    @Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.setItemDamage(world.getUniqueDataId("CJB_MP"));
        String s = "CJB_MP_" + itemstack.getItemDamage();
        CJB_MPData data = new CJB_MPData(s);
        world.setItemData(s, data);
        data.markDirty();
    }
    
    public void onCatch(ItemStack itemstack, World world, EntityAnimal ent, byte datawatcher)
    {   	
        itemstack.setItemDamage(world.getUniqueDataId("CJB_MP"));
        String s = "CJB_MP_" + itemstack.getItemDamage();
        String entname = (String) CJB.invokePrivateMethod(EntityAnimal.class, ent, "getEntityString", null);
        CJB_MPData data = new CJB_MPData(s, entname, ent.getGrowingAge(), datawatcher, ent.getHealth());
        
        world.setItemData(s, data);
        data.markDirty();
    }
    
    public CJB_MPData getMPData(ItemStack itemstack, World world)
    {
        CJB_MPData data = (CJB_MPData)world.loadItemData(net.minecraft.src.CJB_MPData.class, "CJB_MP_" + itemstack.getItemDamage());
        if (data == null)
        {
        	itemstack.setItemDamage(world.getUniqueDataId("CJB_MP"));
            String s = "CJB_MP_" + itemstack.getItemDamage();
            data = new CJB_MPData(s);
            world.setItemData(s, data);
            data.markDirty();
        }
        return data;
    }

    public String getItemDisplayName(ItemStack par1ItemStack)
    {
    	World world = ModLoader.getMinecraftInstance().theWorld;
    	CJB_MPData data = getMPData(par1ItemStack, world);
    	
        String s = StatCollector.translateToLocal(getItemName() + ".name").trim();
        String s1 = data.id;

        if (s1 != null)
        {
            s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
        }

        return s;
    }

    public int getColorFromDamage(int par1, int par2)
    {
    	Map map = new HashMap();
    	EntityEggInfo entityegginfo = null;
    	
    	World world = ModLoader.getMinecraftInstance().theWorld;
    	CJB_MPData data = getMPData(new ItemStack(mod_cjb_items.mobplacer, 1, par1), world);
    	
    	try {
    		map = (Map) ModLoader.getPrivateValue(EntityList.class, null, 4);
    		entityegginfo = (EntityEggInfo)EntityList.entityEggs.get(map.get(data.id));
    	} catch (Throwable e) {
    		
    	}


        if (entityegginfo != null)
        {
            if (par2 == 0)
            {
                return entityegginfo.primaryColor;
            }
            else
            {
                return entityegginfo.secondaryColor;
            }
        }
        else
        {
            return 0xffffff;
        }
    }

    public boolean func_46058_c()
    {
        return true;
    }

    public int getIconFromDamageForRenderPass(int par1, int par2)
    {   	
        if (par2 > 0)
        {
            return super.getIconFromDamageForRenderPass(par1, par2) + 16;
        }
        else
        {
            return super.getIconFromDamageForRenderPass(par1, par2);
        }
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
     */
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float f, float f1, float f2)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        
        int i = par3World.getBlockId(par4, par5, par6);
        par4 += Facing.offsetsXForSide[par7];
        par5 += Facing.offsetsYForSide[par7];
        par6 += Facing.offsetsZForSide[par7];
        double d = 0.0D;

        if (par7 == 1 && i == Block.fence.blockID || i == Block.netherFence.blockID)
        {
            d = 0.5D;
        }

        if (getMob(par3World, par1ItemStack, (double)par4 + 0.5D, (double)par5 + d, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
        {
            par1ItemStack.stackSize--;
        }

        return true;
    }

    public boolean getMob(World world, ItemStack itemstack, double par2, double par4, double par6)
    {
    	CJB_MPData data = getMPData(itemstack, world);
    	
    	Map map = null;
    	
    	try {
    		map = (Map) ModLoader.getPrivateValue(EntityList.class, null, 4);
    	} catch (Throwable e) {
    		
    	}
    	
    	if (map == null)
    		return false;
    	
        if (!EntityList.entityEggs.containsKey((map.get(data.id))))
        {
            return false;
        }

        EntityAnimal entity = (EntityAnimal) EntityList.createEntityByName(data.id, world);
        entity.setGrowingAge(data.data1);
        entity.setEntityHealth(data.health);
        
		try {
			entity.getDataWatcher().updateObject(16, data.data2);
		} catch (Throwable e) {
		}
		
		if (entity != null)
        {
            entity.setLocationAndAngles(par2, par4, par6, world.rand.nextFloat() * 360F, 0.0F);
            world.spawnEntityInWorld(entity);
            ((EntityLiving)entity).playLivingSound();
        }

        return entity != null;
    }
    
    @Override
    public int getIconFromDamage(int i)
    {
        return iconIndex;
    }
}
