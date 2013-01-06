package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class CJB_ButtonLabel extends CJB_Button{
	
	public CJB_ButtonLabel(int i, String s, int j, int k, boolean flag)
	{
		super(i, j, k);
		text = s;
		bwidth = 156;
		bheight = 10;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = "";
		imagescale = 1F;
		color =  0x00000000;
		colorhover = 0x80000000;
		colorselected = 0xFFFF0000;
		visible = true;
	}
	
	public CJB_ButtonLabel(int i, String s, int j, int k, boolean flag, String tip)
	{
		super(i, j, k);
		text = s;
		bwidth = 156;
		bheight = 10;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = "";
		imagescale = 1F;
		color =  0x00000000;
		colorhover = 0x80000000;
		colorselected = 0xFFFF0000;
		visible = true;
		tooltip = tip;
	}
	
	public void drawScreen(Minecraft mc, int i, int j, int offsetx, int offsety)
	{
		int x = posx+offsetx;
		int y = posy+offsety;
		
		
		
		if (text2.isEmpty()) {
			isMouseOver = i > x && i < x + bwidth && j > y && j < y + bheight;
			if (enabled)
			{
				if (isMouseOver)
		    		drawRect(x, y, x + bwidth, y + bheight, colorhover);
				else {
					if(!selected)
						drawRect(x, y, x + bwidth, y + bheight, color);
					else
						drawRect(x, y, x + bwidth, y + bheight, colorselected);
				}
				drawString(mc.fontRenderer, text, x+4, y+(bheight/2)-4, 0xffffff);
			} else {
				drawRect(x, y, posx+offsetx + bwidth, y + bheight, 0x30000000);
				drawCenteredString(mc.fontRenderer, text,  x + (bwidth / 2), y+(bheight/2)-4, 0xe0e0e0);
			}
		} else {
			isMouseOver = i > x && i < x + bwidth && j > y && j < y + bheight*2;
			if (enabled)
			{
				if (isMouseOver)
		    		drawRect(x, y, x + bwidth, y + (bheight*2), colorhover);
				else {
					if(!selected)
						drawRect(x, y, x + bwidth, y + (bheight*2), color);
					else
						drawRect(x, y, x + bwidth, y + (bheight*2), colorselected);
				}
				drawString(mc.fontRenderer, text, x+4, y+(bheight/2)-4, 0xffffff);
				drawString(mc.fontRenderer, text2, x+4, y+(bheight), 0xffffff);
			} else {
				drawRect(x, y, posx+offsetx + bwidth, y + bheight, 0x30000000);
				drawCenteredString(mc.fontRenderer, text,  x + (bwidth / 2), y+(bheight/2)-4, 0xe0e0e0);
			}
		}
		
		int alpha = blockcolor < 0 ? 0 : 0xff000000;
		
		if (blockcolor != -1) {
			drawRect(x + bwidth - bheight + 1, y + 1, posx+offsetx + bwidth -1, y + bheight - 1, 0xFF444444);
			drawRect(x + bwidth - bheight + 2, y + 2, posx+offsetx + bwidth - 2, y + bheight - 2, alpha + blockcolor);
		}
		
	}
}
