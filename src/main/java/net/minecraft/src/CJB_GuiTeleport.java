package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.GuiPlayerInfo;
import net.minecraft.client.multiplayer.NetClientHandler;

public class CJB_GuiTeleport extends CJB_GuiMain
{
	private static int menuid = 1010;
    private static CJB_Data location = null;
    private List<CJB_Button> tpmenubuttons;

    public void initGui()
    {
    	selectedgui = 1022;
    	super.initGui();
    	selectedmenu = menuid;
    	previousScreen = this;
    	
    	if (mc.isSingleplayer())
    	{
    		selectedmenu = 1010;
    	}
    	
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
        int i = 0;
        
        tpmenubuttons = new ArrayList<CJB_Button>();
        
        if (selectedmenu == 1010) {
        	rows = 11;
	    	tpmenubuttons.add(new CJB_ButtonAuto(3001, "add", k+10, l+132));
	    	tpmenubuttons.add(new CJB_ButtonAuto(3002, "delete", k+10, l+132));
	    	tpmenubuttons.add(new CJB_ButtonAuto(3003, "teleport", k+10, l+132));
        }

    	menubuttons.add(new CJB_Button(1010, "Locations", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1010? 0 : 32), 1f, 0x40FFFFFF));
    	if (!mc.isSingleplayer())
    		menubuttons.add(new CJB_Button(1011, "Players", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1011? 0 : 32), 1f, 0x40FFFFFF));
    	
    	RefreshLocations();
    }
    
    private void RefreshLocations()
    {
    	buttonslist = new ArrayList<CJB_Button>();
    	CJB_Settings.LoadLocations();
    	int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	
    	if (selectedmenu == 1010)
    	{
    		if (mc.isSingleplayer())
    			buttonslist.add(new CJB_ButtonLabel(-1, "command: " + mod_cjb_teleport.tpcommand, k + 8, l + 22, false));
    			    	
	    	for (CJB_Data loc : CJB.locations)
	    	{
	    		if (loc.data == mc.thePlayer.dimension)
	    		{
		    		CJB_Button btn = new CJB_ButtonLabel(loc.id, loc.Name + ": " + (int)loc.posx + ", " + (int)loc.posy + ", " + (int)loc.posz, k + 8, l + 22, true);
		    		
		    		if (location != null && location.id == loc.id)
		    			btn.selected = true;
		    		else
		    			btn.selected = false;
		    			
		    		buttonslist.add(btn);
	    		}
	    	}
    	
    	}
    	if (selectedmenu == 1011)
    	{
    		NetClientHandler netclienthandler = ((EntityClientPlayerMP)mc.thePlayer).sendQueue;
            java.util.List list = netclienthandler.playerInfoList;
            int i4 = netclienthandler.currentServerMaxPlayers;
    		
            for( int j = 0 ; j < i4 ; j++)
            {
            	if(j >= list.size())
                {
                    continue;
                }
            	
            	GuiPlayerInfo guiplayerinfo = (GuiPlayerInfo)list.get(j);
            	buttonslist.add(new CJB_ButtonLabel(-1, CJB_GuiMain.removeColors(guiplayerinfo.name), k + 8, l + 22, true));
            }
    	}
    }
    
    public void mouseClicked(int i, int j, int k)
    {
    	if(k == 0)
        {
    		super.mouseClicked(i, j, k);
            for(int l = 0; l < buttonslist.size(); l++)
            {
            	CJB_Button cjbbutton = (CJB_Button)buttonslist.get(l);
            	if(cjbbutton.MouseClick())
                {
            		mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
	            	if (selectedmenu == 1010)
	            	{
	                    for (CJB_Data loc : CJB.locations)
	                    {
	                    	if (cjbbutton.id == loc.id)
                    			location = loc;
	                    }
	                }
	            	if (selectedmenu == 1011)
	            	{
		                mc.thePlayer.sendChatMessage(mod_cjb_teleport.tpplayercommand.replace("#u", mc.thePlayer.username).replace("#d", cjbbutton.text));
		                chatmessage = "";
	            	}
	            	mc.displayGuiScreen(this);
                }
            }
            for(int l = 0; l < tpmenubuttons.size(); l++)
            {
            	CJB_Button cjbbutton = (CJB_Button)tpmenubuttons.get(l);
                if(cjbbutton.MouseClick())
                {                	
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformed(cjbbutton);
                }
            }
        }   
    }
    
    public void actionPerformed(CJB_Button cjbbutton)
    {
    	if (selectedmenu == 1010)
    	{
	    	if ( cjbbutton.id == 3001) {
	    		String s[] = chatmessage.split(" ");
	    		CJB_Data wploc = null;
	    		
	    		if (s.length == 1 )
	    		{
	    			if (s[0].equalsIgnoreCase("")) {
	    				msg(CJB_Colors.Rose + "Error: Incorrect input. Usage: <name> or <name x y z>");
	        			chatmessage = "";
	        			return;
	    			}
	    			for( CJB_Data loc : CJB.locations )
	        			if (loc.Name.equalsIgnoreCase(s[0]))wploc = loc;
	        		
	        		if (wploc != null) CJB.locations.remove(wploc);
	        		
	        		CJB_Data loc = new CJB_Data();
	        		loc.Name = s[0];
	        		loc.posx = mc.thePlayer.posX;
	        		loc.posy = mc.thePlayer.posY;
	        		loc.posz = mc.thePlayer.posZ;
	        		loc.data = mc.thePlayer.dimension;
	        		CJB.locations.add(loc);
	        		CJB_Settings.saveData(CJB.locations, "locations", true);
	        		RefreshLocations();
	        		return;
	    		}
	    		if (s.length == 4 )
	    		{
	    			if (s[0].equalsIgnoreCase("")) {
	    				msg(CJB_Colors.Rose + "Error: Incorrect input. Usage: <name> or <name x y z>");
	        			chatmessage = "";
	        			return;
	    			}
	    			for( CJB_Data loc : CJB.locations )
	        			if (loc.Name.equalsIgnoreCase(s[0]))wploc = loc;
	        		
	        		if (wploc != null) CJB.locations.remove(wploc);
	        		
	        		CJB_Data loc = new CJB_Data();
	        		loc.Name = s[0];
	        		loc.data = mc.thePlayer.dimension;
	        		try
	        		{
	        			loc.posx = Float.parseFloat(s[1]);
	        			loc.posy = Float.parseFloat(s[2]);
	        			loc.posz = Float.parseFloat(s[3]);
	        		} 
	        		catch(NumberFormatException e)
	        		{
	        			msg(CJB_Colors.Rose + "Error: Incorrect input. Usage: <name> or <name x y z>");
	        			chatmessage = "";
	        			return;
	        		}
	        		
	        		CJB.locations.add(loc);
	        		CJB_Settings.saveData(CJB.locations, "locations", true);
	        		RefreshLocations();
	        		chatmessage = "";
	        		return;
	    		}
	    		msg(CJB_Colors.Rose + "Error: Incorrect input. Usage: <name> or <name x y z>");
	    		chatmessage = "";
	    		return;
	    	}
	    	
	    	if ( cjbbutton.id == 3002) {
	    		if(location != null)
	    		{
	    			for (int i = 0 ; i < CJB.locations.size() ; i++)
	    			{
	    				CJB_Data loc = CJB.locations.get(i);
	    				if (loc.id == location.id){
	    					CJB.locations.remove(loc);
	    					break;
	    				}
	    			}
		    		location = null;
		    		CJB_Settings.saveData(CJB.locations, "locations", true);
		    		RefreshLocations();
	    		}
	    	}
	    	
	    	if ( cjbbutton.id == 3003) {
	    		if(location != null)
	    		{
	    			if (mc.isSingleplayer())
    				{
	    				CJB.plr.setPosition(location.posx, location.posy+0.5d, location.posz);
	    				mc.thePlayer.setPosition(location.posx, location.posy+0.5d, location.posz);
    				} else {
    					
    					String cmd = mod_cjb_teleport.tpcommand;
    					
    					cmd = cmd.replace("#u", mc.thePlayer.username);
    					cmd = cmd.replace("#x", String.valueOf((int)location.posx));
    					cmd = cmd.replace("#y", String.valueOf((int)location.posy));
    					cmd = cmd.replace("#z", String.valueOf((int)location.posz));
    					
    					mc.thePlayer.sendChatMessage(cmd);
    				}
	    			mc.displayGuiScreen(null);
	    			return;
	    		}
	    	}
    	}
    	super.actionPerformed(cjbbutton);
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    	if ( cjbbutton.id >= 1000 && cjbbutton.id < 1020)
    		menuid = cjbbutton.id;
    	
        super.actionPerformedMenus(cjbbutton);
    }

    public void drawBackground(int i, int j, float f)
    {
    	super.drawBackground(i, j, f);
    	int i2 = (buttonslist.size() / colums - rows);
        int j2 = (int)((double)(currentScroll * (float)i2) + 0.5D);
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
    }

	public String name() {
		return "Teleport";
	}

	public boolean multiplayer() {
		return true;
	}
	
	public int id() {
		return 1022;
	}
	
	public int textureid() {
		return 1;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiTeleport();
	}
}
