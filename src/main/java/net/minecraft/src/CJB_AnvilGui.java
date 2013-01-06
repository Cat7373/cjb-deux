package net.minecraft.src;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.storage.WorldInfo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class CJB_AnvilGui extends GuiContainer
{
    private CJB_AnvilTileEntity anvil;

    public CJB_AnvilGui(InventoryPlayer par1InventoryPlayer, CJB_AnvilTileEntity anvil)
    {
        super(new CJB_AnvilContainer(par1InventoryPlayer, anvil));
        this.anvil = anvil;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal("Anvil"), 76, 6, 4210752);
        //this.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        int var4 = this.mc.renderEngine.getTexture("/cjb/anvil.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        
        if (this.anvil.isRepairing())
        {
            int var7 = this.anvil.repairTime * 21 / anvil.getRequiredRepairTime();
            this.drawTexturedModalRect(var5 + 74, var6 + 38 - var7, 176, 36 - var7, 30, 20);
        }
        
        ItemStack item = anvil.getSuggestedItem();
        if (item != null)
        {
        	GL11.glPushMatrix();
        	RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            itemRenderer.zLevel = 100.0F;
            itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, item, var5 + 80, var6 + 58);
            itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, item, var5 + 80, var6 + 58);
            itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.disableStandardItemLighting();
        }
        
        this.drawCenteredString(mc.fontRenderer, getTimeFinished(anvil.getRequiredRepairTime() - anvil.repairTime), this.guiLeft+this.xSize/2, this.guiTop+43, 0xffffffff);
    }
    
    public static String getTimeFinished(int i) {
    	
    	if (i == 0)
    		return "";
    	
    	WorldInfo worldinfo = CJB.w.getWorldInfo();
		//long time = CJB.w.worldInfo.getWorldTime() + i;
    	long time = i;
        int hours = (int) (time / 1000);
        float mins = (float) (time % 24000);
        mins %= 1000;
        mins = mins / 1000 * 60;
        
    	return hours + ":" + (Integer.toString((int)mins).length() == 1 ? "0" : "") + (int)mins;
    }
}