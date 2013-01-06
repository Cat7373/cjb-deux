package net.minecraft.src;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class CJB_PWBGuiCrafting extends GuiCrafting
{
    public CJB_PWBGuiCrafting(InventoryPlayer inventoryplayer, World world)
    {
    	super(inventoryplayer, world, 0, 0, 0);
    	inventorySlots = new CJB_PWBContainer(inventoryplayer, world);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        fontRenderer.drawString("Portable Workbench", 8, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }
}
