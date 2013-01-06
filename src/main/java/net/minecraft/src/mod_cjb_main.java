package net.minecraft.src;


import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class mod_cjb_main extends BaseMod {
	
	public static Minecraft mc;
	private static boolean lightenabled;
	private World world;
	
	
	public void load()
	{
		ModLoader.setInGameHook(this, true, false);
		//ModLoader.setInGUIHook(this, true, false);
		CJB.disableAch = CJB_Settings.getBoolean("main.disableachievements", false);
		loadkeys();
	}
	
	@Override
	public boolean onTickInGUI(float f, Minecraft mc, GuiScreen guiscreen)
	{
		if (guiscreen instanceof GuiMainMenu) {
			CJB.pmxray = CJB.pmentity = CJB.pmfly = true;
			CJB.pmnoclip = CJB.pmquickcraft = false;
		}
		
		/*if (!(mc.ingameGUI instanceof CJB_GuiIngame))
			mc.ingameGUI = new CJB_GuiIngame(mc);*/
		
		if (mc.theWorld == null) {
			if (CJB.modcheats) mod_cjb_cheats.cheatsloaded = false;
			if (CJB.modmobfilter) mod_cjb_mobfilter.filtersloaded = false;
			CJB_Settings.pw = new CJB_Properties();
		}
		
		if (CJB.renderer == null)
			CJB.renderer = new CJB_RenderGlobal(mc);
		
		if (!(mc.effectRenderer instanceof CJB_EffectRenderer))
			mc.effectRenderer = new CJB_EffectRenderer(mc.theWorld, mc.renderEngine);
		
		return true;
	}
	
	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		this.mc = mc;
		
		if (world != mc.theWorld) {
			if (CJB.modcheats) mod_cjb_cheats.cheatsloaded = false;
			if (CJB.modmobfilter) mod_cjb_mobfilter.filtersloaded = false;
			CJB_Settings.pw = new CJB_Properties();
			world = mc.theWorld;
		}
		
		if (mc.isSingleplayer()) {
			EntityPlayer p = mc.getIntegratedServer().getConfigurationManager().getPlayerForUsername(mc.thePlayer.username);
			if (p != null) {
				CJB.plr = (EntityPlayerMP) p;
				CJB.w = mc.getIntegratedServer().worldServerForDimension(p.dimension);
			}
			CJB.pmfly = CJB.pmxray = CJB.pmentity = true;
		} else {
			CJB.plr = mc.thePlayer;
			CJB.w = mc.theWorld;
		}
		
		if (mc.currentScreen == null)
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.getEventKey() == CJB.KeyMainMenu && !CJB.keypressed)
					mc.displayGuiScreen(CJB_GuiMain.previousScreen);
			} 
			else
			{
				CJB.keypressed = false;
			}
		}
		
		if (mc.thePlayer != null && mc.thePlayer.username != null && mc.thePlayer.username.equalsIgnoreCase("cjbok")) {
			CJB.pmfly = CJB.pmxray = CJB.pmentity = true;
		}
		
		if (CJB.disableAch && !(mc.guiAchievement instanceof CJB_GuiAchievement))
			mc.guiAchievement = new CJB_GuiAchievement(mc);
		if (!CJB.disableAch && (mc.guiAchievement instanceof CJB_GuiAchievement))
			mc.guiAchievement = new GuiAchievement(mc);
		
		lightenabled = ((CJB.moditems && mod_cjb_items.gogglesEquiped) || (CJB.modxray && (mod_cjb_x_ray.xray || mod_cjb_x_ray.cave || mod_cjb_x_ray.light)));
		
		if (CJB.renderer == null)
			CJB.renderer = new CJB_RenderGlobal(mc);
		
		if (!(mc.effectRenderer instanceof CJB_EffectRenderer))
			mc.effectRenderer = new CJB_EffectRenderer(mc.theWorld, mc.renderEngine);
		
        if (lightenabled)
        	mod_cjb_main.enablelight();
        else
        	mod_cjb_main.disablelight();
              	
		return true;
	}
	
	@Override
	public void clientChat(String s)
    {
		if (s.contains("§3 §9 §2 §0 §0 §0")) {
			CJB.pmfly = CJB.pmxray = CJB.pmentity = false;
		}
		if (s.contains("§3 §9 §2 §0 §0 §1")) {
			CJB.pmfly = false;
		}
		if (s.contains("§3 §9 §2 §0 §0 §2")) {
			CJB.pmxray = false;
		}
		if (s.contains("§3 §9 §2 §0 §0 §3")) {
			CJB.pmentity = false;
		}
		if (s.contains("§f §f §4 §0 §9 §6")) {
			CJB.pmnoclip = true;
		}
    }
	
	
	public static void enablelight(){
		if (CJB.nvlight)
			return;
		CJB.nvlight = true;
    	for(int i = 0; i <= 15; i++)
        {
            mc.theWorld.provider.lightBrightnessTable[i] = 1F;
        }
	    mc.renderGlobal.loadRenderers();
    }
    
    public static void disablelight(){
    	if (!CJB.nvlight)
			return;
    	CJB.nvlight = false;
    	mc.theWorld.provider.generateLightBrightnessTable();
    	mc.renderGlobal.loadRenderers();
    }
	
	public String getVersion() {
		return CJB.VERSION;
	}
	
	public static void loadkeys() {
		CJB.KeyMainMenu = CJB_Settings.getInteger("main.menukey", Keyboard.KEY_Y);
        CJB.KeyTPMenu = CJB_Settings.getInteger("teleport.key", Keyboard.KEY_G);
        CJB.KeyWPMenu = CJB_Settings.getInteger("minimap.key", Keyboard.KEY_U);
        CJB.KeyXRay = CJB_Settings.getInteger("xray.xraykey", Keyboard.KEY_X);
        CJB.KeyItemSpawnerMenu = CJB_Settings.getInteger("itemspawner.key", Keyboard.KEY_J);
        CJB.KeyCave = CJB_Settings.getInteger("xray.cavekey", Keyboard.KEY_Z);
        CJB.KeyLight = CJB_Settings.getInteger("xray.nightvisionkey", Keyboard.KEY_N);
        CJB.KeyFly = CJB_Settings.getInteger("cheat.flykey", Keyboard.KEY_B);
        CJB.KeyFlyUp = CJB_Settings.getInteger("cheat.flyupkey", Keyboard.KEY_R);
        CJB.KeyFlyDown = CJB_Settings.getInteger("cheat.flydownkey", Keyboard.KEY_F);
        CJB.KeyFlySpeed = CJB_Settings.getInteger("cheat.flyspeedkey", Keyboard.KEY_LSHIFT);
        CJB.KeyItemStack = CJB_Settings.getInteger("itemspawner.dropstackkey", Keyboard.KEY_T);
        CJB.KeyZoom = CJB_Settings.getInteger("minimap.zoomkey", Keyboard.KEY_ADD);
        CJB.KeySpawnArea = CJB_Settings.getInteger("moreinfo.showspawnareakey", Keyboard.KEY_L);
        CJB.KeyShowOres = CJB_Settings.getInteger("moreinfo.showoreskey", Keyboard.KEY_K);
        CJB.KeyGameMode = CJB_Settings.getInteger("cheat.gamemodekey", Keyboard.KEY_M);
        CJB.KeyMeasurePointsUndo = CJB_Settings.getInteger("measures.keyundo", Keyboard.KEY_LSHIFT);
        CJB.KeyMeasurePointsClear = CJB_Settings.getInteger("measures.keyclear", Keyboard.KEY_LCONTROL);
	}
}