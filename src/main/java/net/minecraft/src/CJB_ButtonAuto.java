package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class CJB_ButtonAuto extends CJB_Button{
		
	public CJB_ButtonAuto(int i, String s, int j, int k)
	{
		super(i, j, k);
		text = s;
		bheight = 10;
		color = 0x50000000;
		colorhover = 0x80000000;
	}
	
	public void drawScreen(Minecraft mc, int i, int j, int offsetx, int offsety)
	{
        int k = i;
        int i1 = j;
        int x = posx + offsetx;
        int y = posy + offsety;
        
        bwidth = mc.fontRenderer.getStringWidth(text) + 8;

		isMouseOver = k > posx+offsetx && k < posx+offsetx + bwidth && i1 > posy+offsety && i1 < posy+offsety + bheight;

		if (isMouseOver)
	    	drawRect(x, y, x + bwidth, y + bheight, colorhover);
		else
			drawRect(x, y, x + bwidth, y + bheight, color);
			
		drawString(mc.fontRenderer, text, x+4, y+(bheight/2)-4, 0xffffff);
	} 
	
	public boolean MouseClick()
	{
		return isMouseOver && enabled && visible;
	}
}
