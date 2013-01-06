package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

public class mod_cjb_minimap extends BaseMod{

	private boolean zoomkeypressed;
	
	public void load()
	{
		ModLoader.setInGameHook(this, true, false);
		CJB.modminimap = true;

		CJB.mmenabled = CJB_Settings.getBoolean("minimap.enabled", true);
		CJB.mmzoom = CJB_Settings.getInteger("minimap.zoom", 0);
		CJB.mmmobs = CJB_Settings.getBoolean("minimap.mobs", true);
		CJB.mmplayers = CJB_Settings.getBoolean("minimap.players", true);
		CJB.mmitems = CJB_Settings.getBoolean("minimap.items", true);
		CJB.mmcoords = CJB_Settings.getBoolean("minimap.coords", true);
		CJB.mmshowsnow = CJB_Settings.getBoolean("minimap.snow", true);
		CJB.mmside = CJB_Settings.getBoolean("minimap.side", true);
		CJB.mmtrans = CJB_Settings.getInteger("minimap.transparency", 0);
		CJB.mmsquare = CJB_Settings.getBoolean("minimap.square", false);
		CJB.mmslimechunks = CJB_Settings.getBoolean("minimap.slimechunks", false);
		CJB.mmsize = CJB_Settings.getInteger("minimap.minimapsize", 0);
		CJB.mmskylight = CJB_Settings.getBoolean("minimap.skylight", true);
		CJB.mmnoborder = CJB_Settings.getBoolean("minimap.noborder", false);
		CJB.mmaltindicator = CJB_Settings.getBoolean("minimap.altindicator", false);
		CJB.mmshadow = CJB_Settings.getBoolean("minimap.shadow", true);
		CJB.mmthreading = CJB_Settings.getBoolean("minimap.threading", true);
		CJB.mmpriority = CJB_Settings.getInteger("minimap.priority", 2);
		CJB.mmfrequency = CJB_Settings.getInteger("minimap.frequency", 2);
		CJB.mmshowallwp = CJB_Settings.getBoolean("minimap.showallwp", true);
	}
	
	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		if (mc.currentScreen == null)
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == CJB.KeyWPMenu && !CJB.disablehotkeys && !CJB.keypressed)
					mc.displayGuiScreen(new CJB_GuiMinimap());
				
				if (Keyboard.getEventKey() == CJB.KeyZoom && !zoomkeypressed) {
					CJB_Settings.setInteger("minimap.zoom", CJB.mmzoom = (CJB.mmzoom < 4 ? ++CJB.mmzoom : 0));
					zoomkeypressed = true;
				}
				
				if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0) && Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1) && !zoomkeypressed) {
					CJB_BlockColors.calcBlockColorD();
					zoomkeypressed = true;
				}
			}
			if (!Keyboard.getEventKeyState()) {
				zoomkeypressed = false;
			}
		}
		
		CJB_Minimap.instance.onTickInGame(mc);
		
		return true;
	}
	
	public String getVersion() {
		return CJB.VERSION;
	}
	
	@Override
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}
