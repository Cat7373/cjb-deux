package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class CJB_GuiMinimap extends CJB_GuiMain
{
    private List<CJB_Button> wpmenubuttons;
    public static CJB_Data waypoint;
    public static CJB_Data entitycolor;
    private static int menuid = 1001;
    
    private String[] zoom_str = {"0.5", "1", "2", "4", "8"};

    public void initGui()
    {
    	super.initGui();
    	previousScreen = this;
    	selectedmenu = menuid;
    	rows = 12;
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	
    	wpmenubuttons = new ArrayList<CJB_Button>();
    	if (selectedmenu == 1001)
    	{
    		rows = 5;
		    wpmenubuttons.add(new CJB_ButtonAuto(1001, "add", k+10, l+132));
		    if (waypoint != null) {
		    	wpmenubuttons.add(new CJB_ButtonAuto(1004, "Edit", k+10, l+132));
		    	wpmenubuttons.add(new CJB_ButtonAuto(1002, "delete", k+10, l+132));
			    wpmenubuttons.add(new CJB_ButtonAuto(1003, "Unselect", k+10, l+132));
		    }
    	} 
    	if (selectedmenu == 1002 || selectedmenu == 1003) {
    		rows = 11;
    		wpmenubuttons.add(new CJB_ButtonAuto(1003, "clear", k+10, l+132));
    	}
    	if (selectedmenu == 1006) {
    		rows = 11;
    		wpmenubuttons.add(new CJB_ButtonAuto(1010, "Add", k+10, l+132));
    		if (entitycolor != null) {
    			wpmenubuttons.add(new CJB_ButtonAuto(1011, "Edit", k+10, l+132));
    			wpmenubuttons.add(new CJB_ButtonAuto(1012, "Delete", k+10, l+132));
    		}
    	}
    	
    	int i = 0;
    	menubuttons.add(new CJB_Button(1001, "Waypoints", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1001? 0 : 32), 1f, 0x40FFFFFF));
    	if(mc.isSingleplayer())menubuttons.add(new CJB_Button(1002, "Structures", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1002? 0 : 32), 1f, 0x40FFFFFF));
    	if(!mc.isSingleplayer())menubuttons.add(new CJB_Button(1003, "Players", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1003? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1011, "Options", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1011? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1012, "HUD Options", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1012? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1013, "Threading Opt.", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1013? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1005, "Block Colors", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1005? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1006, "Entity Colors", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1006? 0 : 32), 1f, 0x40FFFFFF));
    	
        RefreshWaypoints();
    }
    
    private void RefreshWaypoints()
    {
    	buttonslist = new ArrayList<CJB_Button>();
    	
    	int k = (width - menuw) / 2 + 8;
    	int l = (height - menuh) / 2 + 19;
    	
    	if (selectedmenu == 1001)
        {
    		CJB_Settings.LoadWaypoints();
	    	for (CJB_Data wp : CJB.mmwaypoints)
	        {
	    		if (wp.data == mc.thePlayer.dimension)
	    		{
	    			CJB_Button btn = new CJB_ButtonLabel(wp.id, wp.Name , k, l, true);
	    			btn.text2 = (int)wp.posx + ", " + (int)wp.posy + ", " + (int)wp.posz;
	    			if(waypoint != null && wp.equals(waypoint))
	    				btn.selected = true;
	    			else
	    				btn.selected = false;
	    			
	    			btn.blockcolor = wp.color;
	    			
	    			buttonslist.add(btn);
	    		}
	    	}
        }
    	if (selectedmenu == 1002)
    	{
    		buttonslist.add(new CJB_ButtonLabel(1, "Stronghold", k, l, true));
    	}
    	if (selectedmenu == 1003)
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
            	buttonslist.add(new CJB_ButtonLabel(-1, CJB_GuiMain.removeColors(guiplayerinfo.name), k, l, true));
            }
    	}
    	if (selectedmenu == 1011)
    	{
        	buttonslist.add(new CJB_ButtonLabel(100, "Show Mobs = " + Boolean.toString(CJB.mmmobs), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(101, "Show Players = " + Boolean.toString(CJB.mmplayers), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(102, "Show Items = " + Boolean.toString(CJB.mmitems), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(103, "Show Coordinates = " + Boolean.toString(CJB.mmcoords), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(104, "Show Snow = " + Boolean.toString(CJB.mmshowsnow), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(105, "Show Slime Chunks = " + Boolean.toString(CJB.mmslimechunks), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(106, "Sky Light = " + Boolean.toString(CJB.mmskylight), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(107, "Shadow = " + Boolean.toString(CJB.mmshadow), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(108, "Zoom = " + zoom_str[CJB.mmzoom], k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(109, "Zoom Key = " + getKey("minimap.zoomkey"), k, l, true));
        	if(!CJB.disablehotkeys) buttonslist.add(new CJB_ButtonLabel(110, "Menu Key = " + getKey("minimap.key"), k, l, true));
    	}
    	if (selectedmenu == 1012)
    	{
    		float fa[] = {1f, 0.75f, 0.5f, 0.25f};
    		buttonslist.add(new CJB_ButtonLabel(200, "Enabled = " + Boolean.toString(CJB.mmenabled), k, l, true));
    		buttonslist.add(new CJB_ButtonLabel(201, "Transparency = " + fa[CJB.mmtrans], k, l, true));
    		buttonslist.add(new CJB_ButtonLabel(202, "Side = " + (CJB.mmside?"Right":"Left"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(203, "No Border = " + Boolean.toString(CJB.mmnoborder), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(204, "Square = " + Boolean.toString(CJB.mmsquare), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(205, "Minimap Size = " + Integer.toString(CJB.mmsize), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(206, "Show All Waypoints = " + Boolean.toString(CJB.mmshowallwp), k, l, true));
    	}
    	if (selectedmenu == 1013)
    	{
    		String sa[] = {"Very Low", "Low", "Normal","High","Very High"};
    		buttonslist.add(new CJB_ButtonLabel(300, "Threading = " + Boolean.toString(CJB.mmthreading), k, l, true));
    		buttonslist.add(new CJB_ButtonLabel(301, "Priority = " + sa[CJB.mmpriority], k, l, true));
    		buttonslist.add(new CJB_ButtonLabel(302, "Update Freq. = " + sa[CJB.mmfrequency], k, l, true));
    	}
    	
    	if (selectedmenu == 1005) {
    		CJB_BlockColors.calcBlockColorD();
    		for (int id = 0 ; id < Block.blocksList.length ; id++) {
    			
    			Block b = Block.blocksList[id];
    			
    			if (b != null) {
    				
    				String s = "";
    				
    				try {
    					
    					s = (String) new ItemStack(b).getTooltip(mc.thePlayer, false).get(0);
    					
    				} catch (Throwable e){}
    				
    				CJB_BlockColors bc = CJB_BlockColors.getBlockColor(id, 0);
    				String color = Integer.toHexString(bc.argb);
    				while(color.length() < 6) color = "0" + color;
    				
    				CJB_ButtonLabel btn = new CJB_ButtonLabel(id, id + " | 0 | " + color, k, l, true, s);
    				btn.blockcolor = bc.argb;
    				buttonslist.add(btn);
    				
    				for (int meta = 1 ; meta < 16 ; meta++) {
    					
    					if (!bc.useMetadata(id, meta)) continue;
    					bc = CJB_BlockColors.getBlockColor(id, meta);
    	    			color = Integer.toHexString(bc.argb);
    	    			while(color.length() < 6) color = "0" + color;
    	    			
    	    			btn = new CJB_ButtonLabel(id, id + " | " + meta + " | " + color, k, l, true, s);
    	    			btn.blockcolor = bc.argb;
    	    			buttonslist.add(btn);
    				}
    			}
    		}
    	}
    	
    	if (selectedmenu == 1006) {
    		CJB_EntityColor.loadEntityColors();
    		int i = 0;
    		for (CJB_Data ec : CJB_EntityColor.entities) {
    			CJB_ButtonLabel btn = new CJB_ButtonLabel(i++, ec.Name, k, l, true);
    			btn.blockcolor = ec.color;
    			
    			if (entitycolor != null)
    				btn.selected = btn.text.equalsIgnoreCase(entitycolor.Name);
    			
    			buttonslist.add(btn);
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
            	CJB_Button cjbbutton = buttonslist.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    if (selectedmenu == 1001)
                    {
                    	for (CJB_Data wp : CJB.mmwaypoints)
                    	{
                    		if (cjbbutton.id == wp.id)
                    			waypoint = wp;
                    	}
                    } else if (selectedmenu == 1002) {
                    	try 
                    	{
	                    	EntityPlayer entityplayer = CJB.plr;
	                    	ChunkPosition chunkposition = null;
	                    	
	                    	/*if (cjbbutton.id == 1) {
	                    		MapGenStronghold strongholdGenerator = (MapGenStronghold) ModLoader.getPrivateValue(ChunkProviderGenerate.class, CJB.w.provider.getChunkProvider(), 13);
	                    		chunkposition = strongholdGenerator.getNearestInstance(CJB.w, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
	                    	}
	                    	if (cjbbutton.id == 2) {
	                    		MapGenVillage villageGenerator = (MapGenVillage) ModLoader.getPrivateValue(ChunkProviderGenerate.class, CJB.w.provider.getChunkProvider(), 14);
	                    		chunkposition = villageGenerator.getNearestInstance(CJB.w, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
	                    	}
	                    	if (cjbbutton.id == 3) {
	                    		MapGenMineshaft mineshaftGenerator = (MapGenMineshaft) ModLoader.getPrivateValue(ChunkProviderGenerate.class, CJB.w.provider.getChunkProvider(), 15);
	                    		chunkposition = mineshaftGenerator.getNearestInstance(CJB.w, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
	                    	}*/
	                    	
	                    	if (chunkposition != null) {
	                    		CJB_Data wp = new CJB_Data();
	                    		wp.Name = cjbbutton.text;
	                    		wp.posx = chunkposition.x;
	                    		wp.posy = chunkposition.y;
	                    		wp.posz = chunkposition.z;
	                    		wp.data = 0;
	                    		wp.color = 0xffffff;
	                    		waypoint = wp;
	                    	}
                    	} catch (Throwable e){
                    	}
                    } else if (selectedmenu == 1006)
                    {
                    	for (CJB_Data ec : CJB_EntityColor.entities)
                    	{
                    		if (cjbbutton.text.equalsIgnoreCase(ec.Name))
                    			entitycolor = ec;
                    	}
                    } else {
                    	actionPerformed(cjbbutton);
                    }
                    
                    mc.displayGuiScreen(this);
                }
            }
            for(int l = 0; l < wpmenubuttons.size(); l++)
            {
            	CJB_Button cjbbutton = wpmenubuttons.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformedWPButton(cjbbutton);
                }
            }
        }   
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    	if ( cjbbutton.id >= 1000 && cjbbutton.id < 1020)
    		menuid = cjbbutton.id;
    	
        super.actionPerformedMenus(cjbbutton);
    }
    
    protected void actionPerformedWPButton(CJB_Button cjbbutton)
    {  	
    	if (selectedmenu == 1001 || selectedmenu == 1002)
    	{
	    	if ( cjbbutton.id == 1001) {
	    		waypoint = null;
	    		mc.displayGuiScreen(new CJB_GuiWaypoint(this, mc.thePlayer));
	    		return;
	    	}
	    	
	    	if ( cjbbutton.id == 1002) {
	    		if(waypoint != null)
	    		{	    			
	    			for (int i = 0 ; i < CJB.mmwaypoints.size() ; i++)
	    			{
	    				CJB_Data wp = CJB.mmwaypoints.get(i);
	    				if (wp.equals(waypoint)){
	    					CJB.mmwaypoints.remove(wp);
	    					break;
	    				}
	    			}
		    		waypoint = null;
		    		CJB_Settings.saveData(CJB.mmwaypoints, "waypoints", true);
		    		RefreshWaypoints();
	    		}
	    	}
    	}
    	if ( cjbbutton.id == 1003) {
    		waypoint = null;
    		RefreshWaypoints();
    	}
    	if ( cjbbutton.id == 1004) {
    		for (CJB_Data wp : CJB.mmwaypoints) {
    			if (wp.equals(waypoint)) {
    				waypoint = null;
    				mc.displayGuiScreen(new CJB_GuiWaypoint(this, wp));
    			}
    		}
    		return;
    	}
    	if ( cjbbutton.id == 1010 ) {
    		waypoint = null;
    		mc.displayGuiScreen(new CJB_GuiEntityColor(this));
    		return;
    	} 
    	if ( cjbbutton.id == 1010 ) {
    		entitycolor = null;
    		mc.displayGuiScreen(new CJB_GuiEntityColor(this));
    		return;
    	}
    	if ( cjbbutton.id == 1011) {
    		for (CJB_Data data :  CJB_EntityColor.entities) {
    			if (data.equals(entitycolor)) {
    				entitycolor = null;
    				mc.displayGuiScreen(new CJB_GuiEntityColor(this, data));
    			}
    		}
    		return;
    	}
    	if ( cjbbutton.id == 1012) {
    		if(entitycolor != null)
    		{	    			
    			for (int i = 0 ; i < CJB_EntityColor.entities.size() ; i++)
    			{
    				CJB_Data data = CJB_EntityColor.entities.get(i);
    				if (data.equals(entitycolor)){
    					CJB_EntityColor.entities.remove(data);
    					break;
    				}
    			}
	    		entitycolor = null;
	    		CJB_EntityColor.saveEntityColors();
	    		RefreshWaypoints();
    		}
    	}
    	mc.displayGuiScreen(this);
    }
    
    public void actionPerformed(CJB_Button cjbbutton)
    {   	
    	if (selectedmenu == 1011)
    	{
	    	if (cjbbutton.id == 100) CJB_Settings.setBoolean("minimap.mobs",CJB.mmmobs = !CJB.mmmobs);
	    	if (cjbbutton.id == 101) CJB_Settings.setBoolean("minimap.players",CJB.mmplayers = !CJB.mmplayers);
	    	if (cjbbutton.id == 102) CJB_Settings.setBoolean("minimap.items",CJB.mmitems = !CJB.mmitems);
	    	if (cjbbutton.id == 103) CJB_Settings.setBoolean("minimap.coords",CJB.mmcoords = !CJB.mmcoords);
	    	if (cjbbutton.id == 104) CJB_Settings.setBoolean("minimap.snow",CJB.mmshowsnow = !CJB.mmshowsnow);
	    	if (cjbbutton.id == 105) CJB_Settings.setBoolean("minimap.slimechunks",CJB.mmslimechunks = !CJB.mmslimechunks);
	    	if (cjbbutton.id == 106) CJB_Settings.setBoolean("minimap.skylight",CJB.mmskylight = !CJB.mmskylight);
	    	if (cjbbutton.id == 107) CJB.mmshadow = CJB.toggleB("minimap.shadow");
	    	if (cjbbutton.id == 108) CJB_Settings.setInteger("minimap.zoom",CJB.mmzoom = (CJB.mmzoom < 4 ? ++CJB.mmzoom : 0));
	    	if (cjbbutton.id == 109) changingkey = "minimap.zoomkey";
	    	if (cjbbutton.id == 110) changingkey = "minimap.key";
    	}
    	if (selectedmenu == 1012)
    	{
	    	if (cjbbutton.id == 200) CJB.mmenabled = CJB.toggleB("minimap.enabled");
	    	if (cjbbutton.id == 201) CJB.mmtrans = CJB.toggleInt("minimap.transparency", 3);
	    	if (cjbbutton.id == 202) CJB_Settings.setBoolean("minimap.side",CJB.mmside = !CJB.mmside);
	    	if (cjbbutton.id == 203) CJB_Settings.setBoolean("minimap.noborder",CJB.mmnoborder = !CJB.mmnoborder);
	    	if (cjbbutton.id == 204) CJB_Settings.setBoolean("minimap.square",CJB.mmsquare = !CJB.mmsquare);
	    	if (cjbbutton.id == 205) CJB_Settings.setInteger("minimap.minimapsize",CJB.mmsize = CJB.mmsize < 5 ? ++CJB.mmsize : 0);
	    	if (cjbbutton.id == 206) CJB.mmshowallwp = CJB.toggleB("minimap.showallwp");
    	}
    	if (selectedmenu == 1013)
    	{
    		if (cjbbutton.id == 300) CJB_Settings.setBoolean("minimap.threading",CJB.mmthreading = !CJB.mmthreading);
    		if (cjbbutton.id == 301) CJB.mmpriority = CJB.toggleInt("minimap.priority", 4);
    		if (cjbbutton.id == 302) CJB.mmfrequency = CJB.toggleInt("minimap.frequency", 4);
    	}
        super.actionPerformed(cjbbutton);
    }

    @Override
    public void drawBackground(int i, int j, float f)
    {
    	super.drawBackground(i, j, f);
    	
    	int i2 = (buttonslist.size() / colums - rows);
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
        	        buttonslist.get(i1).drawScreen(mc, i, j, 0, k * (buttonslist.get(i1).text2.isEmpty() ? 10 :20));
                }
            }
        }
    	
    	int width = 0;
	    for (CJB_Button btn : wpmenubuttons) {
	    	btn.drawScreen(mc, i, j, width, 0);
	    	width += btn.bwidth + 2;
	    }
    }
    
    @Override
    public void drawForeground(int i, int j, float f)
    {
    	super.drawForeground(i, j, f);
    	try {
    		String sa[] =  chatmessage.trim().split(" ");
            
        	if (sa.length >= 2 && sa.length <= 3) {
        		int id = 0, meta = 0;
        		int color = 0;
        	   
        		if (sa.length == 2) {
        			id = Integer.parseInt(sa[0]);
        			color = Integer.parseInt(sa[1], 16);
        		}
        		
        		if (sa.length == 3) {
        			id = Integer.parseInt(sa[0]);
        			meta = Integer.parseInt(sa[1]);
        			color = Integer.parseInt(sa[2], 16);
        		}
        		
        		String scolor = Integer.toHexString(color);
        		
        		int k = (width - menuw) / 2 + 137;
            	int l = (height - menuh) / 2 + 151;
        		
        		this.drawRect(k, l, k + 30, l + 8, 0xff000000 + color);
        		
        	}
    	} catch (Throwable e) {}
    }
    
    public void keyTyped(char c, int i)
    {
        if(i == 28 && selectedmenu == 1005)
        {
        	try {
	            String s = chatmessage.trim();
	            if(s.length() > 0)
	            {
	            	String sa[] = chatmessage.split(" ");
	               
	            	if (sa.length >= 2 && sa.length <= 3) {
	            		int id = 0, meta = 0;
	            		int color = 0;
	            	   
	            		if (sa.length == 2) {
	            			id = Integer.parseInt(sa[0]);
	            			color = Integer.parseInt(sa[1], 16);
	            		}
	            		
	            		if (sa.length == 3) {
	            			id = Integer.parseInt(sa[0]);
	            			meta = Integer.parseInt(sa[1]);
	            			color = Integer.parseInt(sa[2], 16);
	            		}
	            		
	            		String scolor = Integer.toHexString(color);
	            		
	            		while(scolor.length() < 6) scolor = "0" + scolor;
	            		
	            		
		            	for (int j = 0 ; j < CJB.mmblockcolors.size() ; j++) {
		            		if (CJB.mmblockcolors.get(j).data == id && meta == (int) CJB.mmblockcolors.get(j).posx) {
		            			CJB.mmblockcolors.remove(j);
		            			break;
		            		}
		            	}
		            	
		            	if (color == 0) {
		            		CJB_Settings.saveData(CJB.mmblockcolors, "blockcolors", false);
		            		mc.displayGuiScreen(this);
		                    return;
		            	}
	            	   
	            		CJB_Data data = new CJB_Data();
	            		data.data = id;
	            		data.posx = meta;
	            		data.Name = scolor;
	            	   
	            		CJB.mmblockcolors.add(data);
	            		CJB_Settings.saveData(CJB.mmblockcolors, "blockcolors", false);
	            		mc.displayGuiScreen(this);
	                    return;
	            	}
	            }
        	} catch (Throwable e) {
        		msg(CJB_Colors.Rose + "Incorrect input: usage [blockid] [color] or [blockid] [metadata] [color]");
        		mc.displayGuiScreen(this);
                return;
        	}
        }
        super.keyTyped(c, i);
    }

	public String name() {
		return "Minimap";
	}

	public boolean multiplayer() {
		return true;
	}
	
	public int id() {
		return 1023;
	}
	
	public int textureid() {
		return 2;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiMinimap();
	}
}
