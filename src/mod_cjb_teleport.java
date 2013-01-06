package net.minecraft.src;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class mod_cjb_teleport extends BaseMod {
	
	public static String tpcommand;
	public static String tpplayercommand;
	
	public void load() {

		ModLoader.setInGameHook(this, true, false);
		CJB.modteleport = true;
		tpcommand = CJB_Settings.getString("teleport.command", "/tppos #x #y #z");
		tpplayercommand = CJB_Settings.getString("teleport.playercommand", "/tp #u #d");
	}
	
	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		if (mc.currentScreen == null)
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.isKeyDown(CJB.KeyTPMenu) && !CJB.disablehotkeys && !CJB.keypressed)
					mc.displayGuiScreen(new CJB_GuiTeleport());
			}
			if (!Keyboard.getEventKeyState())
				CJB.keypressed = false;
		}
		
		return true;
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