package net.minecraft.src;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;

public class CJB_GuiIngame extends GuiIngame
{
	
	private Minecraft mc;

    public CJB_GuiIngame(Minecraft minecraft)
    {
    	super(minecraft);
    	mc = minecraft;
    }
    
    public void renderGameOverlay(float f, boolean flag, int i, int j)
    {
    	super.renderGameOverlay(f, flag, i, j);
    	
    	if (CJB.modmoreinfo)
    		mod_cjb_moreinfo.instance.renderMod(mc);
    	
    	if (CJB.modminimap)
    		CJB_Minimap.instance.onTickInGame(mc);
    }
}
