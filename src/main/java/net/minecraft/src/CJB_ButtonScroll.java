package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import org.lwjgl.opengl.GL11;

public class CJB_ButtonScroll extends Gui{
	public String text;
	public int id;
	public int posx;
	public int posy;
	public int width;
	public int height;
	public int color;
	public int colorhover;
	public int colorselected;
	public String image;
	public int imagex;
	public int imagey;
	public float imagescale;
	public boolean noshadow;
	public boolean enabled;
	public boolean isMouseOver;
	public boolean selected;
	public boolean visible;
	
	
	public CJB_ButtonScroll(int i, String s, int j, int k, int l, int i1, boolean flag)
	{
		text = s;
		id = i;
		posx = j;
		posy = k;
		width = l;
		height = i1;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = "";
		imagescale = 1F;
		color =  0x00000000;
		colorhover = 0x80000000;
		colorselected = 0x30000000;
		visible = true;
	}
	
	public void drawScreen(Minecraft mc, int i, int j, int offsetx, int offsety)
	{
        int k = i;
        int i1 = j;
        if (visible) {
			isMouseOver = k > posx+offsetx && k < posx+offsetx + width && i1 > posy+offsety && i1 < posy+offsety + height;
			if (!image.equalsIgnoreCase("")) {
				GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture(image));
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPushMatrix();
				GL11.glTranslatef(posx+offsetx + (width/2),(posy+offsety + (height/2)),0);
		        GL11.glScalef(imagescale, imagescale, imagescale);
		        GL11.glTranslatef(-(posx+offsetx + (width/2)),-(posy+offsety + (height/2)),0);
				drawTexturedModalRect(posx+offsetx, posy+offsety, imagex, imagey, width, height);
				GL11.glPopMatrix();
				
				if (!text.equalsIgnoreCase(""))
				{
					if(enabled) color = 0xFFFFFF; else color = 0xAAAAAA;
					drawCenteredString(mc.fontRenderer, text,  posx+offsetx + (width / 2), posy+offsety+4, color);
				}
				
				if (isMouseOver && colorhover != 0)
	    			drawRect(posx+offsetx, posy+offsety, posx+offsetx + width, posy+offsety + height, colorhover);
				return;
			}
		
			if (enabled)
			{
				if (isMouseOver)
	    			drawRect(posx+offsetx, posy+offsety, posx+offsetx + width, posy+offsety + height, colorhover);
				else {
					if(!selected)
						drawRect(posx+offsetx, posy+offsety, posx+offsetx + width, posy+offsety + height, color);
					else
						drawRect(posx+offsetx, posy+offsety, posx+offsetx + width, posy+offsety + height, colorselected);
				}
				if (!noshadow)
					drawString(mc.fontRenderer, text, posx+offsetx+4, posy+offsety+(height/2)-4, 0xffffff);
				else
					mc.fontRenderer.drawString(text, posx+offsetx+4, posy+offsety+(height/2)-4, 0xffffff);
			} else {
				drawRect(posx+offsetx, posy+offsety, posx+offsetx + width, posy+offsety + height, 0x30000000);
				drawCenteredString(mc.fontRenderer, text,  posx+offsetx + (width / 2), posy+offsety+(height/2)-4, 0xe0e0e0);
			}
		}
	} 
	
	public boolean MouseClick()
	{
		return isMouseOver && enabled && visible;
	}
}
