package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class CJB_GuiChatOptions extends CJB_GuiMain
{
	private static int menuid = 1001;
    private static CJB_Data location = null;
    private List<CJB_Button> tpmenubuttons;

    public void initGui()
    {
    	selectedgui = id();
    	super.initGui();
    	selectedgui = menuid;
    	rows = 11;
    	previousScreen = this;
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
        int i = 0;
        
        tpmenubuttons = new ArrayList<CJB_Button>();
        
        
        if(selectedmenu == 1001 || selectedmenu == 1002)
        {
		    tpmenubuttons.add(new CJB_ButtonAuto(3001, "add", k+10, l+132));
		    tpmenubuttons.add(new CJB_ButtonAuto(3002, "remove", k+10, l+132));
        }
        
        menubuttons.add(new CJB_Button(1001, "Ping Words", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1001? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1002, "Ping Block", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1002? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1003, "Options", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1003? 0 : 32), 1f, 0x40FFFFFF));
        
    	if (selectedmenu == 1001)
    		LoadPingWords();
    	if (selectedmenu == 1002)
    		LoadPingBlockWords();
    	
    	if (selectedmenu == 1003)
    	{
    		buttonslist = new ArrayList<CJB_Button>();
    		buttonslist.add(new CJB_ButtonLabel(1, "Enable Ping = " + Boolean.toString(CJB.enableping), k + 8, l + 22, true));
    		buttonslist.add(new CJB_ButtonLabel(2, "Ping Volume = " + Integer.toString(CJB.pingvolume), k + 8, l + 22, true));
    	}
    }
    
    private void LoadPingWords()
    {
    	buttonslist = new ArrayList<CJB_Button>();
    	int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	
    	if (selectedmenu == 1001) {    		
	    	List list = CJB.pingwords;
	    	
	    	for (int i = 0 ; i < list.size(); i++)
	    	{
	    		CJB_Data word = (CJB_Data) list.get(i);
	    		CJB_Button btn = new CJB_ButtonLabel(i, word.Name, k + 8, l + 22, true);
	    		
	    		if (location != null && location.Name.equalsIgnoreCase(word.Name))
	    			btn.enabled = true;
	    			
	    		buttonslist.add(btn);
	    	}
    	}
    }
    
    private void LoadPingBlockWords()
    {
    	buttonslist = new ArrayList<CJB_Button>();
    	int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	 			    	
	    List list = CJB.pingblockwords;
	    
	    for (int i = 0 ; i < list.size(); i++)
	    {
	    	CJB_Data word = (CJB_Data) list.get(i);
	    	CJB_Button btn = new CJB_ButtonLabel(i, word.Name, k + 8, l + 22, true);
	    	
	    	if (location != null && location.Name.equalsIgnoreCase(word.Name))
	    		btn.enabled = true;
	    		
	    	buttonslist.add(btn);
	    }
    }
    
    public void mouseClicked(int i, int j, int k)
    {
    	super.mouseClicked(i, j, k);
    	if(k == 0)
        {
    		for(int l = 0; l < tpmenubuttons.size(); l++)
            {
            	CJB_Button cjbbutton = tpmenubuttons.get(l);
                if(cjbbutton.MouseClick())
                {                	
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformedTPButton(cjbbutton);
                }
            }
    		
            for(int l = 0; l < buttonslist.size(); l++)
            {
            	CJB_Button cjbbutton = buttonslist.get(l);
	            if(cjbbutton.MouseClick())
	            {
	            	mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	            	actionPerformed(cjbbutton);
	            	
	            	if (selectedmenu == 1001 || selectedmenu == 1002)
	            	{
		              	for (CJB_Button btn : buttonslist)
		              	{
		              		btn.selected = false;
		               		if (btn.id == cjbbutton.id)
		               			btn.selected = true;
		               	}
		                
		                if (selectedmenu == 1001)
		                   	location = CJB.pingwords.get(cjbbutton.id);
		                if (selectedmenu == 1002)
		                   	location = CJB.pingblockwords.get(cjbbutton.id);
		                chatmessage = "";
	            	}
	            }
            }
        }   
    }
    
    public void actionPerformed(CJB_Button cjbbutton)
    {   	
    	if (cjbbutton.id == 1) CJB_Settings.setBoolean("chat.enableping", CJB.enableping = !CJB.enableping);
    	if (cjbbutton.id == 2) CJB_Settings.setInteger("chat.pingvolume", CJB.pingvolume = CJB.pingvolume > 9 ? 1 : ++CJB.pingvolume);
    	
    	super.actionPerformed(cjbbutton);
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    	if ( cjbbutton.id >= 1000 && cjbbutton.id < 1020)
    		menuid = cjbbutton.id;
    	
        super.actionPerformedMenus(cjbbutton);
    }
    
    public void actionPerformedTPButton(CJB_Button cjbbutton)
    {
    	if (selectedmenu == 1001)
    	{
	    	if ( cjbbutton.id == 3001) {
	    		String s = chatmessage;

	    		if (s.length() < 1) {
	    			msg(CJB_Colors.Rose + "Error: Incorrect input. Usage: <word>");
	        		chatmessage = "";
	        		return;
	    		}
	        	
	        	CJB_Data loc = new CJB_Data();
	        	loc.Name = s;
	        	CJB.pingwords.add(loc);
	        	mod_cjb_chat.SavePingWords();
	        	LoadPingWords();
	    		chatmessage = "";
	    		return;
	    	}
	    	
	    	if ( cjbbutton.id == 3002) {
	    		if(location != null)
	    		{
	    			CJB.pingwords.remove(location);
		    		location = null;
		    		mod_cjb_chat.SavePingWords();
		    		LoadPingWords();
	    		}
	    	}
	    	LoadPingWords();
    	}
    	if (selectedmenu == 1002)
    	{
	    	if ( cjbbutton.id == 3001) {
	    		String s = chatmessage;

	    		if (s.length() < 1) {
	    			msg(CJB_Colors.Rose + "Error: Incorrect input. Usage: <word>");
	        		chatmessage = "";
	        		return;
	    		}
	        	
	        	CJB_Data loc = new CJB_Data();
	        	loc.Name = s;
	        	CJB.pingblockwords.add(loc);
	        	mod_cjb_chat.SavePingBlockWords();
	        	LoadPingBlockWords();
	    		chatmessage = "";
	    		return;
	    	}
	    	
	    	if ( cjbbutton.id == 3002) {
	    		if(location != null)
	    		{
	    			CJB.pingblockwords.remove(location);
		    		location = null;
		    		mod_cjb_chat.SavePingBlockWords();
		    		LoadPingBlockWords();
	    		}
	    	}
	    	LoadPingBlockWords();
    	}
    }

    public void drawScreen(int i, int j, float f)
    {
    	
    	super.drawScreen(i, j, f);
    	
    	int i2 = (buttonslist.size() / colums - rows) + 1;
        int j2 = (int)((currentScroll * i2) + 0.5D);
        if(j2 < 0)
        {
            j2 = 0;
        }
        for(int k = 0; k < rows; k++)
        { 
            for(int l = 0; l < colums; l++)
            {
                int i1 = l + (k + j2) * colums;
                if(i1 >= 0 && i1 < buttonslist.size())
                {
        	        buttonslist.get(i1).drawScreen(mc, i, j, 0, k * 10);
                }
            }
            
        }
	    
        int width = 0;
	    for (CJB_Button btn : tpmenubuttons) {
	    	btn.drawScreen(mc, i, j, width, 0);
	    	width += btn.bwidth + 2;
	    }

    }
    
    public void keyTyped(char c, int i)
    {
    	super.keyTyped(c, i);
        if(field_20082_i.indexOf(c) >= 0 && chatmessage.length() < 24)
        {
        	location = null;
            for (CJB_Button btn : buttonslist)
            {
                btn.selected = false;
            }
        }
    }

	public String name() {
		return "Chat";
	}

	public boolean multiplayer() {
		return true;
	}

	public int id() {
		return 1029;
	}
	
	public int textureid() {
		return 7;
	}

	@Override
	public CJB_GuiMain getGui() {
		return new CJB_GuiChatOptions();
	}
}
