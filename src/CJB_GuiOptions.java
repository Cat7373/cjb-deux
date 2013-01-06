package net.minecraft.src;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import org.lwjgl.input.Keyboard;

public class CJB_GuiOptions extends CJB_GuiMain
{
	private static int menuid = 1001;
    private String[] isPos = {"Upper Left", "Upper Right", "Down Left", "Down Right"};
    private CJB_Button versionbtn;
    
    public void initGui()
    {
    	super.initGui();
    	
    	selectedmenu = menuid;
    	previousScreen = this;
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
        int i = 0;
        int i2 = 0;
        
        
    	
        menubuttons.add(new CJB_Button(1001, "CJB Mods", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1001? 0 : 32), 1f, 0x40FFFFFF));
    	if (CJB.modmoreinfo) menubuttons.add(new CJB_Button(1003, "More Info", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1003? 0 : 32), 1f, 0x40FFFFFF));
    	if (CJB.modteleport) menubuttons.add(new CJB_Button(1004, "Teleport", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1004? 0 : 32), 1f, 0x40FFFFFF));
    	if (CJB.modxray)menubuttons.add(new CJB_Button(1006, "X-Ray", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1006? 0 : 32), 1f, 0x40FFFFFF));
    	if (CJB.moditemspawner) menubuttons.add(new CJB_Button(1007, "Item Spawner", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1007? 0 : 32), 1f, 0x40FFFFFF));
    	if (CJB.moditems) menubuttons.add(new CJB_Button(1008, "Items/Blocks", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1008? 0 : 32), 1f, 0x40FFFFFF));
    	if (CJB.modmobs) menubuttons.add(new CJB_Button(1009, "Mobs", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1009? 0 : 32), 1f, 0x40FFFFFF));
    	if (CJB.modmeasures) menubuttons.add(new CJB_Button(1010, "Measures", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1010? 0 : 32), 1f, 0x40FFFFFF));
    	
    	if (selectedmenu == 1003 || selectedmenu == 1011) menubuttons.add(new CJB_Button(1011, "Show Ores", k+170+3, l+3+(i2++ * 16), 80, 16, true, "/cjb/menu.png", 176, 25 + (selectedmenu == 1011? 0 : 32), 1f, 0x40FFFFFF));
    	
    	
    	k += 8;
    	l += 19;
    	
    	versionbtn = new CJB_ButtonLabel(-111, "Up to Date: " + CJB.versionstring, k, l, false);
    	
    	if (selectedmenu == 1001)
    	{
    		buttonslist.add(versionbtn);
    		buttonslist.add(new CJB_ButtonLabel(219, "Check Version", k, l, true, "Check if current version of CJB Mods is up-to-date"));
    		buttonslist.add(new CJB_ButtonLabel(220, "Auto Version Check = " + Boolean.toString(CJB.autocheck), k, l, true, "Enable or disable if CJB Mods is up-to-date"));
	    	buttonslist.add(new CJB_ButtonLabel(0, "Main Menu Key = " + getKey("main.menukey"), k, l, true, "Change key to open CJB Mods menu"));
	    	buttonslist.add(new CJB_ButtonLabel(1, "Disable Hotkeys = " + Boolean.toString(CJB.disablehotkeys), k, l, true, "Disable hotkeys for menu's except Main Menu key"));
	    	buttonslist.add(new CJB_ButtonLabel(3, "Disable Achiev Popup = " + Boolean.toString(CJB.disableAch), k, l, true, "Disables Achievement Popups"));
	    	buttonslist.add(new CJB_ButtonLabel(4, "@CJBMods Latest Tweet", k, l, true, "Read Latest tweet of @CJBMods"));
	    	buttonslist.add(new CJB_ButtonLabel(5, "Darken Background = " + Boolean.toString(CJB.darkenbg), k, l, true, "Disables the dark background"));
    	}
        if (selectedmenu == 1003 && CJB.modmoreinfo) {
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(23, "Seed " + Long.toString(CJB.w.getSeed()), k, l, true, "Click to copy world seed to clipboard"));
        	buttonslist.add(new CJB_ButtonLabel(10, "12h Clock = " + Boolean.toString(CJB.clock12h), k, l, true, "Toggle between 12h or 24h system"));
        	buttonslist.add(new CJB_ButtonLabel(17, "Big Icons/text = " + Boolean.toString(CJB.bigicons), k, l, true, "Changes the size of the icons or text"));
        	buttonslist.add(new CJB_ButtonLabel(18, "Hide Icons = " + Boolean.toString(CJB.hideicons), k, l, true, "Toggle between text or icons"));
        	buttonslist.add(new CJB_ButtonLabel(19, "Position = " + isPos[CJB.position], k, l, true, "Change the position where the information should be displayed"));
        	buttonslist.add(new CJB_ButtonLabel(11, "Show Day = " + Boolean.toString(CJB.showday), k, l, true, "Show how many minecraft days are past in current world"));
        	buttonslist.add(new CJB_ButtonLabel(12, "Show Time = " + Boolean.toString(CJB.showtime), k, l, true, "show current minecraft time"));
        	buttonslist.add(new CJB_ButtonLabel(13, "Show FPS = " + Boolean.toString(CJB.showfps), k, l, true, "Show current FPS"));
        	buttonslist.add(new CJB_ButtonLabel(14, "Show LightLevel = " + Boolean.toString(CJB.showlightlevel), k, l, true, "Show the lightleven of your current position"));
        	buttonslist.add(new CJB_ButtonLabel(15, "Show Biome = " + Boolean.toString(CJB.showbiome), k, l, true, "Show the biome your in"));
        	buttonslist.add(new CJB_ButtonLabel(16, "Show Arrow Count = " + Boolean.toString(CJB.showarrowcount), k, l, true, "Shows how much arrows you have in total"));
        	buttonslist.add(new CJB_ButtonLabel(20, "Show Item Info = " + Boolean.toString(CJB.showitemdamage), k, l, true, "Show the current equiped item info"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(21, "Show Rain Time = " + Boolean.toString(CJB.showrain), k, l, true, "Show how long it will be raining"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(22, "Show Thunder Time = " + Boolean.toString(CJB.showthunder), k, l, true, "Show how long it will be thundering"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(23, "Show Slimes = " + Boolean.toString(CJB.showslimes), k, l, true, "Show when you are in a slimes chunk"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(24, "Show Mob Info = " + Boolean.toString(CJB.showmobinfo), k, l, true, "Show mob name and health"));
        	buttonslist.add(new CJB_ButtonLabel(25, "Show Coordinates = " + Boolean.toString(CJB.showcoords), k, l, true, "Show current coordinates"));
        	buttonslist.add(new CJB_ButtonLabel(26, "Spawn Area Key = " + getKey("moreinfo.showspawnareakey"), k, l, true, "Change key to show spawn areas"));
        	buttonslist.add(new CJB_ButtonLabel(27, "Spawn Area Skylight = " + Boolean.toString(CJB.useskylight), k, l, true, "Calculate spawn with skylight?"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(28, "Show Mob Health = " + Boolean.toString(CJB.showmobhealth), k, l, true, "Shows the health above every mobs head"));
        	buttonslist.add(new CJB_ButtonLabel(29, "Show Debuff Effect = " + Boolean.toString(CJB.showdebuffs), k, l, true, "Shows current active debuff effects"));
        }
        if (selectedmenu == 1011 && CJB.modmoreinfo) {
        	buttonslist.add(new CJB_ButtonLabel(50, "Show Ores Key = " + getKey("moreinfo.showoreskey"), k, l, true, "Change key to show ores"));
        	buttonslist.add(new CJB_ButtonLabel(51, "Draw Lines = " + Boolean.toString(CJB.drawlines), k, l, true, "Draws a wireware around ore blocks"));
        	buttonslist.add(new CJB_ButtonLabel(52, "Show Coal Ore = " + Boolean.toString(CJB.showcoal), k, l, true, "Shows coal ores when Show Ores is enabled"));
        	buttonslist.add(new CJB_ButtonLabel(53, "Show Iron Ore = " + Boolean.toString(CJB.showiron), k, l, true, "Shows iron ores when Show Ores is enabled"));
        	buttonslist.add(new CJB_ButtonLabel(54, "Show Gold Ore = " + Boolean.toString(CJB.showgold), k, l, true, "Shows gold ores when Show Ores is enabled"));
        	buttonslist.add(new CJB_ButtonLabel(55, "Show Diamond Ore = " + Boolean.toString(CJB.showdiamond), k, l, true, "Shows diamond ores when Show Ores is enabled"));
        	buttonslist.add(new CJB_ButtonLabel(56, "Show Lapis Ore = " + Boolean.toString(CJB.showlapis), k, l, true, "Shows lapis block ores Show Ores is enabled"));
        	buttonslist.add(new CJB_ButtonLabel(57, "Show Redstone Ore = " + Boolean.toString(CJB.showredstone), k, l, true, "Shows redstone block ores Show Ores is enabled"));
        	buttonslist.add(new CJB_ButtonLabel(58, "Show Emerald Ore = " + Boolean.toString(CJB.showemerald), k, l, true, "Shows emerald block ores Show Ores is enabled"));
        	buttonslist.add(new CJB_ButtonLabel(60, "Scan Width Radius = " + (128 >> CJB.renderWidth), k, l, true, "Change the scan width radius of Spawn Areas and Ores (Higher values is FPS consuming"));
        	buttonslist.add(new CJB_ButtonLabel(61, "Scan Height Radius = " + (128 >> CJB.renderHeight), k, l, true, "Change the scan height radius of Spawn Areas and Ores (Higher values is FPS consuming"));
        }
        if (selectedmenu == 1004 && CJB.modteleport) {
        	buttonslist.add(new CJB_ButtonLabel(31, "Command = " + mod_cjb_teleport.tpcommand, k, l, true, "#x #y #z = coordinates"));
        	buttonslist.add(new CJB_ButtonLabel(32, "Set Default Command", k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(33, "TP to Player = " + mod_cjb_teleport.tpplayercommand, k, l, true, "#u = Your Username, #d = Selected username"));
        	if(!CJB.disablehotkeys) buttonslist.add(new CJB_ButtonLabel(30, "Menu Key = " + getKey("teleport.key"), k, l, true));
        }
        if (selectedmenu == 1006 && CJB.modxray) {
        	buttonslist.add(new CJB_ButtonLabel(40, "Toggle X-Ray = " + getKey("xray.xraykey"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(41, "Toggle Caves = " + getKey("xray.cavekey"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(42, "Toggle Nightvision = " + getKey("xray.nightvisionkey"), k, l, true));
        	if(!CJB.disablehotkeys) buttonslist.add(new CJB_ButtonLabel(-1, "Menu Key = " + "CTLR + " + Keyboard.getKeyName(CJB.KeyXRay), k, l, true));
        }
        if (selectedmenu == 1007 && CJB.moditemspawner) {
        	if(!CJB.disablehotkeys) buttonslist.add(new CJB_ButtonLabel(70, "Menu Key = " + getKey("itemspawner.key"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(-1, "#u Username, #i Item ID", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(-1, "#a Item Amount, #d Item Damage", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(73, "Vanilla: /give #u #i #a #d", k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(74, "Essentials: /give #u #i:#d #a", k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(71, "Command = " + mod_cjb_itemspawner.itemcommand, k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(72, "Drop Stack Key = " + getKey("itemspawner.dropstackkey"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(-1, "SHIFT + Click = 10 Items", k, l, true, "Makes you spawn a stack with 10 items"));
        	buttonslist.add(new CJB_ButtonLabel(-1, "CTRL + Click = 64 Items", k, l, true, "Makes you spawn a stack with 64 items"));
        }
        if (selectedmenu == 1008 && CJB.moditems) {
        	buttonslist.add(new CJB_ButtonLabel(-1, "Blocks: ID 0 = Disable", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(-1, "Restart after changes", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(120, "PresPlate Plank Rev ID = " + CJB_Settings.getInteger("block.PressurePlatePlanksReversedID", 207), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(121, "PresPlate Stone Rev ID = " + CJB_Settings.getInteger("block.PressurePlateStoneReversedID", 208), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(122, "PresPlate Iron ID = " + CJB_Settings.getInteger("block.PressurePlateIronID", 200), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(123, "PresPlate Gold ID = " + CJB_Settings.getInteger("block.PressurePlateGoldID", 201), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(124, "PresPlate Iron Rev ID = " + CJB_Settings.getInteger("block.PressurePlateIronReversedID", 202), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(125, "PresPlate Gold Rev ID = " + CJB_Settings.getInteger("block.PressurePlateGoldReversedID", 203), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(126, "Trashcan ID = " + CJB_Settings.getInteger("block.TrashcanID", 204), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(127, "Recycler ID = " + CJB_Settings.getInteger("block.RecyclerID", 205), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(128, "Mesh ID = " + CJB_Settings.getInteger("block.MeshID", 206), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(129, "Anvil ID = " + CJB_Settings.getInteger("block.AnvilID", 209), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(-1, "Items: False = Disable", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(-1, "Restart after changes", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(140, "Wrath of Notch = " + CJB_Settings.getBoolean("item.WrathofNotch", true), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(141, "Nightvision Goggles = " + CJB_Settings.getBoolean("item.Goggles", true), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(142, "Portable Workbench = " + CJB_Settings.getBoolean("item.PortableWorkbench", true), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(143, "Razor Wind = " + CJB_Settings.getBoolean("item.RazorWind", true), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(144, "Portable Furnace = " + CJB_Settings.getBoolean("item.PortableFurnace", true), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(145, "Sack = " + CJB_Settings.getBoolean("item.Sack", true), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(146, "Repair Hammer = " + CJB_Settings.getBoolean("item.RepairHammer", true), k, l, true));
        }
        if (selectedmenu == 1009 && CJB.modmobs) {
        	buttonslist.add(new CJB_ButtonLabel(-1, "Mobs: False = Disable", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(-1, "Restart after changes", k, l, false));
        	buttonslist.add(new CJB_ButtonLabel(160, "Bunny = " + CJB_Settings.getBoolean("mobs.bunny", true), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(161, "Dog = " + CJB_Settings.getBoolean("mobs.dog", true), k, l, true));
        }
        if (selectedmenu == 1010 && CJB.modmeasures) {
        	buttonslist.add(new CJB_ButtonLabel(200, "Used Item = " + CJB.mesitem, k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(201, "Hold Undo Key = " + getKey("measures.keyundo"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(202, "Hold Clear Key = " + getKey("measures.keyclear"), k, l, true));
        }
    }
    
    public void mouseClicked(int i, int j, int k)
    {
    	super.mouseClicked(i, j, k);
    	if(k == 0)
        {            
            for(int l = 0; l < buttonslist.size(); l++)
            {
            	CJB_Button cjbbutton = (CJB_Button)buttonslist.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformed(cjbbutton);
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
    
    public void actionPerformed(CJB_Button cjbbutton)
    {   	
    	if ( cjbbutton.id >= 1000 && cjbbutton.id < 1020)
    		selectedmenu = cjbbutton.id;

    	if ( cjbbutton.id == 0) changingkey = "main.menukey";
    	if ( cjbbutton.id == 1) CJB.disablehotkeys = CJB.toggleB("main.disablehotkeys");
    	if ( cjbbutton.id == 3) CJB.disableAch = CJB.toggleB("main.disableachievements");
    	if ( cjbbutton.id == 4) readTweet();
    	if ( cjbbutton.id == 5) CJB.darkenbg = CJB.toggleB("main.darkenbg");
    	
    	if ( cjbbutton.id == 220) {
    		CJB_Settings.setBoolean("main.autoversioncheck", CJB.autocheck = !CJB.autocheck);
    		if (CJB.autocheck) {isVersionChecked = false; checkVersion();}
    	}
    	if ( cjbbutton.id == 219)  {isVersionChecked = false; checkVersion();}
        
        if(CJB.modmoreinfo) {
        	if ( cjbbutton.id == 23) setClipboard(Long.toString(CJB.w.getSeed()));
        	if ( cjbbutton.id == 10) CJB.clock12h = CJB.toggleB("moreinfo.clock12h");
        	if ( cjbbutton.id == 17) CJB.bigicons = CJB.toggleB("moreinfo.bigicons");
        	if ( cjbbutton.id == 18) CJB.hideicons = CJB.toggleB("moreinfo.hideicons");
        	if ( cjbbutton.id == 19) CJB_Settings.setInteger("moreinfo.position", CJB.position = CJB.position >= 3 ? 0 : ++CJB.position);
        	if ( cjbbutton.id == 11) CJB.showday = CJB.toggleB("moreinfo.showday");
        	if ( cjbbutton.id == 12) CJB.showtime = CJB.toggleB("moreinfo.showtime");
        	if ( cjbbutton.id == 13) CJB.showfps = CJB.toggleB("moreinfo.showfps");
        	if ( cjbbutton.id == 14) CJB.showlightlevel = CJB.toggleB("moreinfo.showlightlevel");
        	if ( cjbbutton.id == 15) CJB.showbiome = CJB.toggleB("moreinfo.showbiome");
        	if ( cjbbutton.id == 16) CJB.showarrowcount = CJB.toggleB("moreinfo.showarrowcount");
        	if ( cjbbutton.id == 20) CJB.showitemdamage = CJB.toggleB("moreinfo.showitemdamage");
        	if ( cjbbutton.id == 21) CJB.showrain = CJB.toggleB("moreinfo.showrain");
        	if ( cjbbutton.id == 22) CJB.showthunder = CJB.toggleB("moreinfo.showthunder");
        	if ( cjbbutton.id == 23) CJB.showslimes = CJB.toggleB("moreinfo.showslimes");
        	if ( cjbbutton.id == 24) CJB.showmobinfo = CJB.toggleB("moreinfo.showmobinfo");
        	if ( cjbbutton.id == 25) CJB.showcoords = CJB.toggleB("moreinfo.showcoords");
        	if ( cjbbutton.id == 26) changingkey = "moreinfo.showspawnareakey";
        	if ( cjbbutton.id == 27) CJB.useskylight = CJB.toggleB("moreinfo.useskylight");
        	if ( cjbbutton.id == 28) CJB.showmobhealth = CJB.toggleB("moreinfo.showmobhealth");
        	if ( cjbbutton.id == 29) CJB.showdebuffs = CJB.toggleB("moreinfo.showdebuffs");
        	
        	if ( cjbbutton.id == 50) changingkey = "moreinfo.showoreskey";
        	if ( cjbbutton.id == 51) CJB.drawlines = CJB.toggleB("moreinfo.drawlines");
        	if ( cjbbutton.id == 52) CJB.showcoal = CJB.toggleB("moreinfo.showcoal");
        	if ( cjbbutton.id == 53) CJB.showiron = CJB.toggleB("moreinfo.showiron");
        	if ( cjbbutton.id == 54) CJB.showgold = CJB.toggleB("moreinfo.showgold");
        	if ( cjbbutton.id == 55) CJB.showdiamond = CJB.toggleB("moreinfo.showdiamond");
        	if ( cjbbutton.id == 56) CJB.showlapis = CJB.toggleB("moreinfo.showlapis");
        	if ( cjbbutton.id == 57) CJB.showredstone = CJB.toggleB("moreinfo.showredstone");
        	if ( cjbbutton.id == 58) CJB.showemerald = CJB.toggleB("moreinfo.showemerald");
        	if ( cjbbutton.id == 60) CJB.renderWidth = CJB.toggleInt("moreinfo.renderwidth", 3);
        	if ( cjbbutton.id == 61) CJB.renderHeight = CJB.toggleInt("moreinfo.renderheight", 3);
        }
        if(CJB.modteleport) {
        	if (cjbbutton.id == 30) changingkey = "teleport.key";
        	if (cjbbutton.id == 31) CJB_Settings.setString("teleport.command", mod_cjb_teleport.tpcommand = chatmessage);
        	if (cjbbutton.id == 32) CJB_Settings.setString("teleport.command", mod_cjb_teleport.tpcommand = "/tppos #x #y #z");
        	if (cjbbutton.id == 33) CJB_Settings.setString("teleport.playercommand", mod_cjb_teleport.tpplayercommand = chatmessage);
        }
        if (CJB.modxray)
        {
        	if (cjbbutton.id == 40) changingkey = "xray.xraykey";
        	if (cjbbutton.id == 41) changingkey = "xray.cavekey";
        	if (cjbbutton.id == 42) changingkey = "xray.nightvisionkey";
        }
        if (CJB.moditemspawner)
        {
        	if (cjbbutton.id == 70) changingkey = "itemspawner.key";
        	if (cjbbutton.id == 71) CJB_Settings.setString("itemspawner.command", mod_cjb_itemspawner.itemcommand = chatmessage);
        	if (cjbbutton.id == 72) changingkey = "itemspawner.dropstackkey";
        	if (cjbbutton.id == 73) CJB_Settings.setString("itemspawner.command", mod_cjb_itemspawner.itemcommand = "/give #u #i #a #d");
        	if (cjbbutton.id == 74) CJB_Settings.setString("itemspawner.command", mod_cjb_itemspawner.itemcommand = "/give #u #i:#d #a");
        }
        if (CJB.moditems)
        {
        	if (cjbbutton.id == 120) CJB_Settings.setInteger("block.PressurePlatePlanksReversedID", changeBlockID("block.PressurePlatePlanksReversedID", 207));
        	if (cjbbutton.id == 121) CJB_Settings.setInteger("block.PressurePlateStoneReversedID", changeBlockID("block.PressurePlateStoneReversedID", 208));
        	if (cjbbutton.id == 122) CJB_Settings.setInteger("block.PressurePlateIronID", changeBlockID("block.PressurePlateIronID", 200));
        	if (cjbbutton.id == 123) CJB_Settings.setInteger("block.PressurePlateGoldID", changeBlockID("block.PressurePlateGoldID", 201));
        	if (cjbbutton.id == 124) CJB_Settings.setInteger("block.PressurePlateIronReversedID", changeBlockID("block.PressurePlateIronReversedID", 202));
        	if (cjbbutton.id == 125) CJB_Settings.setInteger("block.PressurePlateGoldReversedID", changeBlockID("block.PressurePlateGoldReversedID", 203));
        	if (cjbbutton.id == 126) CJB_Settings.setInteger("block.TrashcanID", changeBlockID("block.TrashcanID", 204));
        	if (cjbbutton.id == 127) CJB_Settings.setInteger("block.RecyclerID", changeBlockID("block.RecyclerID", 205));
        	if (cjbbutton.id == 128) CJB_Settings.setInteger("block.MeshID", changeBlockID("block.MeshID", 206));
        	if (cjbbutton.id == 129) CJB_Settings.setInteger("block.AnvilID", changeBlockID("block.AnvilID", 209));
        	if (cjbbutton.id == 140) CJB_Settings.setBoolean("item.WrathofNotch",!CJB_Settings.getBoolean("item.WrathofNotch", true));
        	if (cjbbutton.id == 141) CJB_Settings.setBoolean("item.Goggles",!CJB_Settings.getBoolean("item.Goggles", true));
        	if (cjbbutton.id == 142) CJB_Settings.setBoolean("item.PortableWorkbench",!CJB_Settings.getBoolean("item.PortableWorkbench", true));
        	if (cjbbutton.id == 143) CJB_Settings.setBoolean("item.RazorWind",!CJB_Settings.getBoolean("item.RazorWind", true));
        	if (cjbbutton.id == 144) CJB_Settings.setBoolean("item.PortableFurnace",!CJB_Settings.getBoolean("item.PortableFurnace", true));
        	if (cjbbutton.id == 145) CJB_Settings.setBoolean("item.Sack",!CJB_Settings.getBoolean("item.Sack", true));
        	if (cjbbutton.id == 146) CJB_Settings.setBoolean("item.RepairHammer",!CJB_Settings.getBoolean("item.RepairHammer", true));
        }
        if (CJB.modmobs)
        {
        	if (cjbbutton.id == 160) CJB_Settings.setBoolean("mobs.bunny",!CJB_Settings.getBoolean("mobs.bunny", true));
        	if (cjbbutton.id == 161) CJB_Settings.setBoolean("mobs.dog",!CJB_Settings.getBoolean("mobs.dog", true));
        }
        if (CJB.modmeasures)
        {
        	if (cjbbutton.id == 200) CJB.mesitem = setIntegerFromChat("measures.item", "type a positive number");
        	if (cjbbutton.id == 201) changingkey = "measures.keyundo";
        	if (cjbbutton.id == 202) changingkey = "measures.keyclear";
        }
        
        super.actionPerformed(cjbbutton);
    }
    
    public int changeBlockID(String s, int defaultid) {
    	
    	int tempid = CJB_Settings.getInteger(s, defaultid);
    	int newid = 0;
    	
    	String s1[] = chatmessage.split(" ");
    	
    	if (s1.length == 1) {
	    	try
			{
				newid = Integer.parseInt(s1[0]);
				if (newid < 0 || newid > 255) {
					msg(CJB_Colors.Rose + "Error: Block ID's can not be lower then 0 or higher then 255");
					return tempid;
				}
					
				return newid;
			} 
			catch(NumberFormatException e)
			{
				msg(CJB_Colors.Rose + "Error: Incorrect input. Usage: <ID number>");
				chatmessage = "";
				return tempid;
			}
    	}
    	return tempid;
    }
    
    public void setClipboard(String str) {
        StringSelection ss = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }

    public void drawBackground(int i, int j, float f)
    {
    	super.drawBackground(i, j, f);
    	
    	versionbtn.text = "Up to Date: " + CJB.versionstring;
    	
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
    }

	@Override
	public String name() {
		return "Options";
	}

	@Override
	public boolean multiplayer() {
		return true;
	}
	
	public int id() {
		return 1021;
	}
	
	public int textureid() {
		return 0;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiOptions();
	}
	
	public int setIntegerFromChat(String s, String error){
		
		int i = CJB_Settings.getInteger(s, 0);
		
		if (chatmessage.length() < 1) {
			msg(CJB_Colors.Rose + "Error: " + error + ">");
			chatmessage = "";
			return i;
		}

		try{
			i = Integer.parseInt(chatmessage);
		} catch(NumberFormatException e) {
			msg(CJB_Colors.Rose + "Error: " + error + ">");
			chatmessage = "";
			return i;
		}
		
		CJB_Settings.setInteger(s, i);
		
		return i;
	}
}
