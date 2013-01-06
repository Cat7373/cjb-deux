package net.minecraft.src;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class mod_cjb_chat extends BaseMod {
	
	public void load() {
		CJB.modchat = true;
		CJB.pingdelayed = false;
		CJB.enableping = CJB_Settings.getBoolean("chat.enableping", true);
		CJB.pingvolume = CJB_Settings.getInteger("chat.pingvolume", 5);
		CJB.pingSystemTime = System.currentTimeMillis();
		LoadPingWords();
		LoadPingBlockWords();
		
		ModLoader.setInGameHook(this, true, false);
	}
	
	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		GuiScreen g = mc.currentScreen;
		
		if (g instanceof GuiChat) {
			CJB.pingdelayed = true;
			CJB.pingSystemTime = System.currentTimeMillis();
		}
		
		if (CJB.pingdelayed && System.currentTimeMillis() - CJB.pingSystemTime > 1000) {
			CJB.pingdelayed = false;
		}
		return true;
	}
	
	@Override
	public void clientChat(String s)
    {
		if (CJB.enableping && !CJB.pingdelayed)
			shouldPing(CJB_GuiMain.removeColors(s));
    }
	
	public static void LoadPingWords(){
		CJB.pingwords = new ArrayList<CJB_Data>();
		String s[] = CJB_Settings.getString("chat.pingwords", "CJB").split(";");
		
		for (int i = 0 ; i < s.length ; i++)
		{
			CJB_Data word = new CJB_Data();
			word.Name = s[i];
			CJB.pingwords.add(word);
		}
	}
	
	public static void SavePingWords()
	{
		if (CJB.pingwords == null)
			return;
		
		String s = "";
		for (int i = 0 ; i < CJB.pingwords.size() ; i++)
		{
			s += ((CJB_Data)CJB.pingwords.get(i)).Name + ";";
		}
		
		CJB_Settings.setString("chat.pingwords", s);
	}
	
	public static void LoadPingBlockWords(){
		CJB.pingblockwords = new ArrayList<CJB_Data>();
		String s[] = CJB_Settings.getString("chat.pingblockwords", "[auto]").split(";");
		
		for (int i = 0 ; i < s.length ; i++)
		{
			CJB_Data word = new CJB_Data();
			word.Name = s[i];
			CJB.pingblockwords.add(word);
		}
	}
	
	public static void SavePingBlockWords()
	{
		if (CJB.pingblockwords == null)
			return;
		
		String s = "";
		for (int i = 0 ; i < CJB.pingblockwords.size() ; i++)
		{
			s += ((CJB_Data)CJB.pingblockwords.get(i)).Name + ";";
		}
		
		CJB_Settings.setString("chat.pingblockwords", s);
	}
	
	private void shouldPing(String s)
    {
    	for (CJB_Data word : CJB.pingblockwords)
    	{
    		if (s.toLowerCase().contains(word.Name.toLowerCase()))
        	{
        		return;
        	}
    	}
    	
    	if (ModLoader.getMinecraftInstance().thePlayer != null && s.contains(ModLoader.getMinecraftInstance().thePlayer.username))
    	{
    		pingPlayer();
    	}
    	
    	for (CJB_Data word : CJB.pingwords)
    	{
    		if (s.toLowerCase().contains(word.Name.toLowerCase()))
        	{
        		pingPlayer();
        	}
    	}
    }
    
    private void pingPlayer()
    {
    	for (int i = 0 ; i < CJB.pingvolume * 2 ; i++)
    	{
    		ModLoader.getMinecraftInstance().sndManager.playSoundFX("note.pling", 1F, 1);
    	}
    }

	public String getVersion() {
		return CJB.VERSION;
	}
	
	@Override
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}