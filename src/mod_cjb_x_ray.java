package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;

public class mod_cjb_x_ray extends BaseMod {

	private boolean ctrlpressed = false;
	private boolean raypressed = false;
	private boolean cavepressed = false;
	private boolean lightpressed = false;
	
    public static boolean xray = false;
    public static boolean cave = false;
    public static boolean light = false;
    
    private World w = null;
    private Minecraft m;
	
    @Override
	public void load()
    {
		ModLoader.setInGameHook(this, true, false);
		CJB.modxray = true;
    }

	@Override
    public boolean onTickInGame(float f, Minecraft mc)
    {
    	m = mc;
    	if (m.theWorld == null) return true;
    	if(w == null || m.theWorld != w){
    		w = m.theWorld;
    		xray = false;
    		loadmod();
    		disableray();
    	}
    	
    	if (mc.currentScreen != null)
    		return true;
    	
        if (Keyboard.getEventKeyState())
		{
        	
        	if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
				ctrlpressed = true;
        	
        	
			if (Keyboard.isKeyDown(CJB.KeyXRay) && !CJB.keypressed)
			{
				if (!ctrlpressed && !raypressed) {
					if(xray)
						disableray();
			        else
			        	enableray();
			        
				} else if (!raypressed){
					m.displayGuiScreen(new CJB_GuiXray());
					ctrlpressed = false;
				}
				
				raypressed = true;
			}
			
			if (Keyboard.isKeyDown(CJB.KeyCave) && !cavepressed)
			{
				if(cave)
					disablecave();
			    else
			       	enablecave();
				
				cavepressed = true;
			}
			
			if (Keyboard.isKeyDown(CJB.KeyLight) && !lightpressed)
			{
				light = !light;
				
				lightpressed = true;
			}
			
		} else {
			ctrlpressed = false;
			raypressed = false;
			cavepressed = false;
			lightpressed = false;
			CJB.keypressed = false;
		}
        return true;
    }
    
    public static void loadmod() //loadXray
	{
		String s[] = CJB_Settings.getString("xray.blacklistblocks", "").split(",");
		if (s.length <= 1)
			return;
		
		CJB.blacklist = new int[s.length];
		for (int i = 0 ; i < s.length ; i++)
		{
			CJB.blacklist[i] = Integer.parseInt(s[i]);
		}
	}

    public static void save() //saveXRay
    {
    	String s = "";
    	for (int i = 0 ; i < CJB.blacklist.length ; i++)
    	{
    		s += CJB.blacklist[i] + ",";
    	}
    	CJB_Settings.setString("xray.blacklistblocks", s);
    }
    
    private void enableray()
    {
    	loadmod();
    	
    	if (!m.isSingleplayer() && !CJB.pmxray) {
    		disableray();
    		return;
    	}
    	
    	xray = true;
    	cave = false;
    	m.renderGlobal.loadRenderers();
    }
        
    private void disableray()
    {
    	xray = false;
    	cave = false;
    	m.renderGlobal.loadRenderers();
    }
    
    private void enablecave()
    {
    	loadmod();
    	if (!m.isSingleplayer() && !CJB.pmxray) {
    		disableray();
    		return;
    	}
    	xray = false;
    	cave = true;
    	m.renderGlobal.loadRenderers();
    }
        
    private void disablecave()
    {
    	xray = false;
    	cave = false;
    	m.renderGlobal.loadRenderers();
    }

    public String getVersion() {
		return CJB.VERSION;
	}
    
    @Override
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}