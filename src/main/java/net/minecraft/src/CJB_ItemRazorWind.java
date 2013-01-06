package net.minecraft.src;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CJB_ItemRazorWind extends Item
{

    public CJB_ItemRazorWind(int i)
    {
        super(i);
        maxStackSize = 1;
        setMaxDamage(50);
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
    	
    	if (itemstack.getItemDamage() > 0)
    		return itemstack;
    	
    	if (world.spawnEntityInWorld(new CJB_ForceEntity(world, entityplayer)))
    	{
    		entityplayer.swingItem();
    		world.playSoundAtEntity(entityplayer, "cjb.swoosh", 1F, 1F);
    		itemstack.setItemDamage(100);
    	}
    	
    	return itemstack;
    }
    
    @Override
    public void onUpdate(ItemStack itemstack, World world, Entity entity, int i, boolean flag)
    {
    	if (itemstack.getItemDamage() > itemstack.getMaxDamage())
    		itemstack.setItemDamage(itemstack.getMaxDamage());
    	
    	if (itemstack.getItemDamage() > 0)
    		itemstack.damageItem(-1, null);
    }
    
    @Override
    public boolean isFull3D()
    {
        return false;
    }
}
