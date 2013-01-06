package net.minecraft.src;

import java.awt.image.BufferedImage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderEngine;

import org.lwjgl.opengl.GL11;

public class CJB_ColorChart extends GuiScreen
{
    private final int xPos;
    private final int yPos;

    private BufferedImage chart;
    private static int textureid;
    private final Minecraft mc;
    private final RenderEngine re;
    
    public int color;
    public boolean isMouseOver;

    public CJB_ColorChart(Minecraft minecraft, int x, int y)
    {
        mc = minecraft;
        xPos = x;
        yPos = y;
        
        re = mc.renderEngine;

    	chart = CJB_GLTexture.read("colorwheel.png");
    	
        if (textureid != 0) {
        	re.deleteTexture(textureid);
        }
        
        textureid = re.allocateAndSetupTexture(chart);
    }
    
    public void handleMouseInput()
    {
    	
    }

    /**
     * Args: x, y, buttonClicked
     */
    public void mouseClicked(int mousex, int mousey, int button)
    {
    	
    }

    /**
     * Draws the textbox
     */
    public void drawScreen(int mousex, int mousey)
    {    	
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureid);
    	drawTexturedModalRect(xPos, yPos, 0, 0, 43, 43);
    	
    	int x = mousex - xPos;
    	int y = mousey - yPos;
    	try {
    		isMouseOver = getColor(x,y) != 0;
    	} catch(Throwable e) {
    		e.printStackTrace();
    	}
    	
    }
    
    public int getColor(int x, int y) {
    	if (x < 0 || y < 0 || x > 43 || y > 43 )
    		return 0;
    	
    	int col = chart.getRGB(x, y);
    	
    	color = 0;
    	color += (col >> 16 & 0xff) * 0x10000;
    	color += (col >> 8 & 0xff) * 0x100;
    	color += (col & 0xff) * 0x1;

    	return color;
    }
    
}
