package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;

public class mod_cjb_quickcraft extends BaseMod {
	
	public static int posX = 0;
	public static int posY = 0;
	public static int posZ = 0;
	
	public void load()
	{
		ModLoader.setInGameHook(this, true, false);
		ModLoader.registerContainerID(this, 39210);
		ModLoader.registerContainerID(this, 39211);
	}

	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		CJB.modquickcraft = true;
		
		if (!(Block.blocksList[58] instanceof CJB_BlockWorkbench)) {
			Block.blocksList[58] = null;
			Block.blocksList[58] = (new CJB_BlockWorkbench(58)).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("workbench");
		}
		
		if (mc.currentScreen instanceof GuiCrafting && CJB.quickcraft && CJB.modquickcraft) {
			ModLoader.openGUI(mc.thePlayer, new CJB_GuiCrafting(mc.thePlayer, posX, posY, posZ, mc.currentScreen instanceof CJB_PWBGuiCrafting));
		}
		
		if (mc.currentScreen == null)
			CJB.quickcraft = true;
		
		return true;
	}
	
	@Override
    public GuiContainer getContainerGUI(EntityClientPlayerMP plr, int gid, int i, int j, int k)
    {
        if (gid == 39210){
        	return new GuiCrafting(plr.inventory,plr.worldObj,i,j,k);
        }
        if (gid == 39211){
        	return new CJB_PWBGuiCrafting(plr.inventory,plr.worldObj);
        }
        return null;
    }
	
	@Override
	public String getVersion() {
		return CJB.VERSION;
	}
	
	@Override
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}