package net.minecraft.src;

import java.util.Iterator;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class CJB_ItemMobSpawner extends Item
{
	
	private NBTTagCompound spawnerTags = null;

    public CJB_ItemMobSpawner(int i)
    {
        super(i);
        maxStackSize = 1;
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
    	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) 
    	{
    		ModLoader.getMinecraftInstance().displayGuiScreen(new CJB_GuiMobSpawner());
    		return itemstack;
    	}
    	
    	
    	MovingObjectPosition mov = ModLoader.getMinecraftInstance().objectMouseOver;
    	
    	if (mov != null && mov.entityHit != null)
    	{
    		if (mov.entityHit instanceof EntityVillager)
    		{
    			EntityVillager ent = (EntityVillager)mov.entityHit;
    			try {
					int i1 = (Integer) ModLoader.getPrivateValue(EntityVillager.class, ent, 0);
					i1 = i1 > 3 ? 0 : ++i1;
					String texture = "/mob/villager/villager.png";
					
					if(i1 == 0)
			        {
			            texture = "/mob/villager/farmer.png";
			        }
			        if(i1 == 1)
			        {
			            texture = "/mob/villager/librarian.png";
			        }
			        if(i1 == 2)
			        {
			            texture = "/mob/villager/priest.png";
			        }
			        if(i1 == 3)
			        {
			            texture = "/mob/villager/smith.png";
			        }
			        if(i1 == 4)
			        {
			            texture = "/mob/villager/butcher.png";
			        }
			        ModLoader.setPrivateValue(EntityVillager.class, ent, "texture", texture);
					ModLoader.setPrivateValue(EntityVillager.class, ent, 0, i1);
				} catch (Throwable e) {
					e.printStackTrace();
				}
    		}
    			
    		return itemstack;
    	}
    	
    	return itemstack;
    }
    
    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f, float f1, float f2)
    {   	
    	
    	if (world.isRemote) return true;
    	
    	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) 
    		return false;
    	
    	i += Facing.offsetsXForSide[l];
        j += Facing.offsetsYForSide[l];
        k += Facing.offsetsZForSide[l];
        Entity entity = (EntityLiving)EntityList.createEntityByName(CJB_GuiMobSpawner.mob, world);
        if (entity != null)
        {
        	this.writeNBTTagsTo(entity);
        	
            entity.setLocationAndAngles((double)i + 0.5D, j, (double)k + 0.5D, 0.0F, 0.0F);
            world.spawnEntityInWorld(entity);
        }
    	return true;
    }
    
    public void writeNBTTagsTo(Entity par1Entity)
    {
        if (this.spawnerTags != null)
        {
            NBTTagCompound var2 = new NBTTagCompound();
            par1Entity.addEntityID(var2);
            Iterator var3 = this.spawnerTags.getTags().iterator();

            while (var3.hasNext())
            {
                NBTBase var4 = (NBTBase)var3.next();
                var2.setTag(var4.getName(), var4.copy());
            }

            par1Entity.readFromNBT(var2);
        }
    }

    @Override
    public boolean isFull3D()
    {
        return true;
    }
}
