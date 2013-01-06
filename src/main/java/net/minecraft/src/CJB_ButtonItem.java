package net.minecraft.src;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class CJB_ButtonItem extends CJB_Button{
	
	public CJB_ButtonItem(int i, int j, int k, ItemStack item)
	{
		super(i,j,k);
		itemstack = item;
		bwidth = 18;
		bheight = 18;
		
		color = 0;
		colorhover = 0x80FFFFFF;
		colorselected = 0x80FF0000; 
		available = true;
	}
	
	public CJB_ButtonItem(int i, int j, int k, ItemStack item, boolean available)
	{
		super(i,j,k);
		itemstack = item;
		bwidth = 18;
		bheight = 18;
		
		color = 0;
		colorhover = 0x80FFFFFF;
		colorselected = 0x80FF0000;  
		
		this.available = available;
	}
	
	public CJB_ButtonItem(int i, int j, int k, ItemStack item, boolean available, boolean used)
	{
		super(i,j,k);
		itemstack = item;
		bwidth = 18;
		bheight = 18;
		
		color = 0;
		colorhover = 0x80FFFFFF;
		colorselected = 0x80FF0000;  
		
		this.available = available;
		this.used = used;
	}

	public void drawScreen(Minecraft mc, int i, int j, int offsetx, int offsety)
	{
		isMouseOver = itemstack != null && i > posx+offsetx && i < posx+offsetx + bwidth && j > posy+offsety && j < posy+offsety + bheight;
		
		GL11.glBindTexture(3553, mc.renderEngine.getTexture("/gui/container.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(posx+offsetx, posy+offsety, 7, 17, 18, 18);
		if (!available)
			drawRect(posx+offsetx+1, posy+offsety+1, posx+offsetx + bwidth-1, posy+offsety + bheight-1, 0xffff0000);
		else if (used)
			drawRect(posx+offsetx+1, posy+offsety+1, posx+offsetx + bwidth-1, posy+offsety + bheight-1, 0xff00ff00);
		
		if (itemstack == null || itemstack.getItem() == null) 
			return;
		
		RenderItem itemRenderer = new RenderItem();
		FontRenderer fontRenderer = mc.fontRenderer;
	    RenderHelper.enableGUIStandardItemLighting();
	    GL11.glPushMatrix();
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
	    int u1 = 240;
	    int u2 = 240;
	    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)u1 / 1.0F, (float)u2 / 1.0F);
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    
	    GL11.glTranslatef(0.0F, 0.0F, 32F);
	    zLevel = 200F;
        itemRenderer.zLevel = 200F;
        GL11.glEnable(GL11.GL_DEPTH_TEST);
	    itemRenderer.renderItemIntoGUI(fontRenderer, mc.renderEngine, itemstack, posx + offsetx+1, posy + offsety+1);
	    itemRenderer.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, itemstack, posx + offsetx+1, posy + offsety+1);
	    zLevel = 0.0F;
        itemRenderer.zLevel = 0.0F;
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();	
        
        if (!available) {
        	
        	GL11.glBindTexture(3553, mc.renderEngine.getTexture("/cjb/crafting.png"));
    		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    		drawTexturedModalRect(posx+offsetx+1, posy+offsety+1, 208, 166, 7, 7);
        	//drawRect(posx+offsetx+1, posy+offsety+1, posx+offsetx + bwidth-1, posy+offsety + bheight-1, 0x60ff0000);
        }
        
        if (!selected)
        	drawRect(posx+offsetx+1, posy+offsety+1, posx+offsetx + bwidth-1, posy+offsety + bheight-1, isMouseOver ? colorhover : color);
        else
        	drawRect(posx+offsetx+1, posy+offsety+1, posx+offsetx + bwidth-1, posy+offsety + bheight-1, isMouseOver ? color : colorselected);
	} 
}
