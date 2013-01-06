package net.minecraft.src;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class CJB_GuiEntityColor extends GuiScreen
{
	final private GuiScreen parentScreen;
	final private CJB_Data ec;
	final private List<CJB_Data> ecs;
	final private int mw = 176;
	final private int mh = 166;
	final private String title;
	private int mx = 0;
	private int my = 0;
	private int col;
	
	private CJB_TextField name;
	private GuiButton ok;
	private CJB_ColorChart chart;
	
	
    public CJB_GuiEntityColor(GuiScreen guiscreen, CJB_Data ec)
    {
    	this.parentScreen = guiscreen;
    	this.ec = ec;
    	this.ecs = CJB_EntityColor.entities;
    	this.title = "Edit Waypoint";
    }
    
    public CJB_GuiEntityColor(GuiScreen guiscreen)
    {
    	this.parentScreen = guiscreen;
    	this.ecs = CJB_EntityColor.entities;
    	this.title = "Add Waypoint";
    	
    	CJB_Data data = new CJB_Data();
    	data.Name = "New Entity";
    	data.color = 0x00ff00;
    	
    	this.ec = data;
    	
    	addWaypoint(ec);
    }

    @Override
    public void initGui()
    {
    	mx = (width - mw) / 2;
    	my = (height - mh) / 2;
    	
    	Keyboard.enableRepeatEvents(true);
    	
    	col = ec.color;
    	
    	name = new CJB_TextField(fontRenderer, mx+50, my+20, 116, 12);
    	name.setText(ec.Name);
    	name.setMaxStringLength(20);
    	ok = new GuiButton(0, mx + 10, my + 140, 156, 20, "OK");
    	chart = new CJB_ColorChart(mc, mx+123, my+87);
    }
    
    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    public void mouseClicked(int i, int j, int k) {
    	name.mouseClicked(i, j, k);
    	if (chart.isMouseOver)
    		ec.color = chart.color;
    	
    	if (k == 0 && ok.mousePressed(mc, i, j)) {
    		CJB_EntityColor.saveEntityColors();
    		mc.displayGuiScreen(parentScreen);
    	}
    }
        
    public void actionPerformed(CJB_Button cjbbutton)
    {
    }
    
    public void actionPerformedMainMenus(CJB_Button cjbbutton)
    {
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
    	if (CJB.darkenbg)
    		drawDefaultBackground();
    	
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, mc.renderEngine.getTexture("/cjb/menuwaypoint.png"));
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(mx,my,0,0, mw, mh);
    	
    	this.drawCenteredString(fontRenderer, this.title, mx + mw/2, my+7, 0xffffff);
    	
    	this.drawString(fontRenderer, "Name:", mx + 10, my+22, 0xffffff);
    	this.drawString(fontRenderer, "Color:", mx + 10, my+104, 0xffffff);
    	
    	if (chart.isMouseOver)
    		col = chart.color;
    	
    	int k = mx + 49;
    	int l = my + 87;
    	this.drawRect(k, l, k+60, l+20, 0xff444444);
    	this.drawRect(k+1, l+1, k+60-1, l+20-1, 0xff000000 + ec.color);
    	
    	k = mx + 49;
    	l = my + 110;
    	this.drawRect(k, l, k+60, l+20, 0xff444444);
    	this.drawRect(k+1, l+1, k+60-1, l+20-1, 0xff000000 + col);
    	
    	name.drawTextBox();
    	ok.drawButton(mc, i, j);
    	chart.drawScreen(i, j);
    }
    
    @Override
    public void handleMouseInput()
    {
    	super.handleMouseInput();
    }
    
    @Override
    public void updateScreen()
    {
    	name.updateCursorCounter();
    }

    @Override
    public void keyTyped(char c, int i)
    {
    	if(i == 1)
        {
    		CJB_EntityColor.loadEntityColors();
            mc.displayGuiScreen(parentScreen);
            return;
        }
    	if (name.getIsFocused()) {
    		name.textboxKeyTyped(c, i);
    		ec.Name = name.getText();
    		return;
    	}
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return true;
    }
    
    private void addWaypoint(CJB_Data ec) {
    	for (int i = 0 ; i < ecs.size() ; i++) {
    		CJB_Data ec1 = ecs.get(i);
    		if (ec1.equals(ec))
    			ecs.remove(i);
    	}
    	ecs.add(ec);
    }
}
