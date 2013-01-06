package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class CJB_Button extends Gui{
	public String text;
	public String text2 = "";
	public int id;
	public int posx;
	public int posy;
	public int bwidth;
	public int bheight;
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
	public ItemStack itemstack;
	public boolean available = false;
	public boolean used = false;
	public int blockcolor = -1;
	public int data1 = 0;
	
	public String tooltip = "";
	public long hovertime = 0;
	
	public CJB_Button(int i, int j, int k) {
		id = i;
		posx = j;
		posy = k;
		visible = true;
		enabled = true;
	}
	
	public CJB_Button(int i, String s, int j, int k, int l, int i1, boolean flag, int j1, int k1, int l1)
	{
		text = s;
		id = i;
		posx = j;
		posy = k;
		bwidth = l;
		bheight = i1;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = "";
		imagex = 0;
		imagey = 0;
		imagescale = 0;
		color = j1;
		colorhover = k1;
		colorselected = l1; 
		visible = true;
	}
	
	public CJB_Button(int i, String s, int j, int k, int l, int i1, boolean flag, String s1, int j1, int k1, float f, int i2)
	{
		text = s;
		id = i;
		posx = j;
		posy = k;
		bwidth = l;
		bheight = i1;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = s1;
		imagex = j1;
		imagey = k1;
		imagescale = f;
		colorhover = i2;
		visible = true;
	}
	
	public CJB_Button(int i, String s, int j, int k, int l, int i1, boolean flag, String s1, int j1, int k1, float f, int i2, String s2)
	{
		text = s;
		id = i;
		posx = j;
		posy = k;
		bwidth = l;
		bheight = i1;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = s1;
		imagex = j1;
		imagey = k1;
		imagescale = f;
		colorhover = i2;
		visible = true;
		tooltip = s2;
	}
	
	public CJB_Button(int i, String s, int j, int k, int l, int i1, boolean flag, String s1, int j1, int k1, float f, int i2, boolean flag1)
	{
		text = s;
		id = i;
		posx = j;
		posy = k;
		bwidth = l;
		bheight = i1;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = s1;
		imagex = j1;
		imagey = k1;
		imagescale = f;
		colorhover = i2;
		visible = flag1;
	}
	
	public CJB_Button(int i, String s, int j, int k, int l, int i1, boolean flag, String s1, int j1, int k1, float f)
	{
		text = s;
		id = i;
		posx = j;
		posy = k;
		bwidth = l;
		bheight = i1;
		enabled = flag;
		isMouseOver = false;
		selected = false;
		image = s1;
		imagex = j1;
		imagey = k1;
		imagescale = f;
		visible = true;
	}
	
	public void drawScreen(Minecraft mc, int i, int j, int offsetx, int offsety)
	{
        int k = i;
        int i1 = j;
        if (visible) {
			isMouseOver = k > posx+offsetx && k < posx+offsetx + bwidth && i1 > posy+offsety && i1 < posy+offsety + bheight;
			if (!image.equalsIgnoreCase("")) {
				GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture(image));
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPushMatrix();
				GL11.glTranslatef(posx+offsetx + (bwidth/2),(posy+offsety + (bheight/2)),0);
		        GL11.glScalef(imagescale, imagescale, imagescale);
		        GL11.glTranslatef(-(posx+offsetx + (bwidth/2)),-(posy+offsety + (bheight/2)),0);
				drawTexturedModalRect(posx+offsetx, posy+offsety, imagex, imagey, bwidth, bheight);
				GL11.glPopMatrix();
				
				int k1 = (bheight - 10) / 2;
				
				if (!text.equalsIgnoreCase(""))
				{
					if(enabled) color = 0xFFFFFF; else color = 0xAAAAAA;
					drawCenteredString(mc.fontRenderer, text,  posx+offsetx + (bwidth / 2), posy+offsety+k1+1, color);
				}
				
				if (isMouseOver && colorhover != 0)
	    			drawRect(posx+offsetx, posy+offsety, posx+offsetx + bwidth, posy+offsety + bheight, colorhover);
				return;
			}
		
			if (enabled)
			{
				if (isMouseOver)
	    			drawRect(posx+offsetx, posy+offsety, posx+offsetx + bwidth, posy+offsety + bheight, colorhover);
				else {
					if(!selected)
						drawRect(posx+offsetx, posy+offsety, posx+offsetx + bwidth, posy+offsety + bheight, color);
					else
						drawRect(posx+offsetx, posy+offsety, posx+offsetx + bwidth, posy+offsety + bheight, colorselected);
				}
				if (!noshadow)
					drawString(mc.fontRenderer, text, posx+offsetx+4, posy+offsety+(bheight/2)-4, 0xffffff);
				else
					mc.fontRenderer.drawString(text, posx+offsetx+4, posy+offsety+(bheight/2)-4, 0xffffff);
			} else {
				drawRect(posx+offsetx, posy+offsety, posx+offsetx + bwidth, posy+offsety + bheight, 0x30000000);
				drawCenteredString(mc.fontRenderer, text,  posx+offsetx + (bwidth / 2), posy+offsety+(bheight/2)-4, 0xe0e0e0);
			}
		}
	} 
	
	public void drawToolTip(Minecraft mc, int i, int j)
	{
		ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		int k = sr.getScaledWidth();
		if (!isMouseOver) hovertime = System.currentTimeMillis();
		if (tooltip != null && !tooltip.equalsIgnoreCase("") && System.currentTimeMillis() - hovertime > 500L)
		{
			int l = k - i;
			String[] as = tooltip.split(" ");
			String[] as1 = new String[50];
			for (int i1 = 0 ; i1 < as1.length ; i1++)
			{
				as1[i1] = "";
			}
			int i1 = 10;
			int j1 = 0;
			int tw = 0;
			for (int k1 = 0 ; k1 < as.length ; k1++)
			{
				int l1 = mc.fontRenderer.getStringWidth(as[k1] + " ");
				if (i1 + l1  < l)
				{
					i1 += l1;
					as1[j1] += as[k1] + " ";
					if (tw < mc.fontRenderer.getStringWidth(as1[j1]))
						tw = mc.fontRenderer.getStringWidth(as1[j1]);
				} else {
					
					k1--;
					j1++;
					i1 = 0;
				}
				
			}
			i += 10;
			drawRect(i, j, i+tw+4 ,j+10 + (j1*10), 0xb0000000);
			for (int i2 = 0 ; i2 <= j1 ; i2++)
			{
				int j2 = j + (10 * i2);
				drawRect(i, j2, i+tw+4 ,j2, 0xff000000);
				drawString(mc.fontRenderer, as1[i2],  i+4, j2+1, 0xFFFFFF);
			}
		}
	}
	
	public boolean MouseClick()
	{
		return isMouseOver && enabled && visible;
	}
	
	public void onTick() {
		isMouseOver = false;
	}
	
	public CJB_Button setData1(int i) {
		data1 = i;
		return this;
	}
}
