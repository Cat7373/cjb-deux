package net.minecraft.src;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

public class CJB_RecyclerGui extends GuiContainer
{

    public CJB_RecyclerGui(InventoryPlayer inventoryplayer, CJB_RecyclerTileEntity tileentityrecycler)
    {
        super(new CJB_RecyclerContainer(inventoryplayer, tileentityrecycler));
        recycler = tileentityrecycler;
    }

    protected void drawGuiContainerForegroundLayer()
    {
        fontRenderer.drawString("Recycler", 67, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
        drawRect(120, (int) (69 - (recycler.recycletime * 1.03F)), 130, 69, 0xFF0000FF);
    }
    
    CJB_RecyclerTileEntity recycler;

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		int k = mc.renderEngine.getTexture("/cjb/recyclergui.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(k);
        drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
	}
}
