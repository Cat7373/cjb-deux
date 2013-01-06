package net.minecraft.src;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatAllowedCharacters;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class CJB_GuiMain extends GuiScreen
{
	public static CJB_NumField num;
	public static CJB_TextField txt;
	public static boolean txtFocused = false;
	public static List<CJB_GuiMain> menus;
	public static int selectedgui = 1021;
	public static int selectedmenu = 1001;
	
	public static GuiScreen previousScreen = new CJB_GuiOptions();
	public String chatmessage;
	public static boolean isVersionChecked;
    public int chatupdateCounter;
    public static final String field_20082_i;
    public int menuw = 176;
    public int menuh = 166;
    public int left;
    public int top; 
    
    public String changingkey = "";
    
    protected float currentScroll;
    protected boolean isScrolling;
    protected boolean wasClicking;
    protected int colums;
    protected int rows;
    
    public List<CJB_Button> buttonslist;
    public List<CJB_Button> menubuttons;
    public List<CJB_Button> mainmenubuttons;
    public String bstr[] = new String[] {"Spawn", "Filter"};
    
    
    public CJB_GuiMain()
    {
    	chatmessage = "";
        chatupdateCounter = 0;
        CJB.disablehotkeys = CJB_Settings.getBoolean("main.disablehotkeys", false);
        CJB.darkenbg = CJB_Settings.getBoolean("main.darkenbg", true);
        currentScroll = 0.0F;
        isScrolling = false;
        
    }

    public void initGui()
    {
    	
    	mod_cjb_main.loadkeys();
    	chatupdateCounter = 0;
    	selectedgui = id();
    	colums = 1;
    	rows = 12;
    	left = (width - menuw) / 2;
    	top = (height - menuh) / 2;;
    	
    	CJB.autocheck = CJB_Settings.getBoolean("main.autoversioncheck", true);
    	
    	if(CJB.autocheck) {
    		checkVersion();
    	}
    	
    	CJB_Settings.mc = mc;
        Keyboard.enableRepeatEvents(true);
        buttonslist = new ArrayList<CJB_Button>();
        menubuttons = new ArrayList<CJB_Button>();
        mainmenubuttons = new ArrayList<CJB_Button>();
        
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2 + 3;
        
    	menubuttons.add(new CJB_Button(2000, "", k+161, l+2, 8, 8, true, "/cjb/menu.png", 176, 0, 1f, 0x40FFFFFF));
    	
    	txt = new CJB_TextField(fontRenderer, k+8, l+147, 160, 10);
    	txt.setText(chatmessage);
    	txt.setFocused(txtFocused);
    	
    	num = new CJB_NumField(fontRenderer, 0,0,50, 12);
    	num.setIsHexField(false);
    	num.setMaxStringLength(3);
    	num.setMax(0xff);
    	if(selectedgui == 0) return;
    	
    	menus = new ArrayList<CJB_GuiMain>();
    	menus.add(new CJB_GuiOptions());
    	if (CJB.modteleport) menus.add(new CJB_GuiTeleport());
    	if (CJB.modminimap) menus.add(new CJB_GuiMinimap());
    	if (CJB.modxray) menus.add(new CJB_GuiXray());
    	if (CJB.moditemspawner) menus.add(new CJB_GuiItemSpawner());
    	if (CJB.modmobfilter) menus.add(new CJB_GuiMobFilter());
    	if (CJB.modcheats) menus.add(new CJB_GuiCheats());
    	if (CJB.modchat) menus.add(new CJB_GuiChatOptions());
    	menus.add(new CJB_GuiModPackInfo());
    	//menus.add(new CJB_GuiErrors());
    	
    	for (CJB_GuiMain menu : menus)
    	{
    		if (mc.isSingleplayer() || (!mc.isSingleplayer() && menu.multiplayer()))
    			mainmenubuttons.add(new CJB_Button(menu.id(), "", 0, 0, 22, 22, true, "/cjb/menubuttons.png", menu.textureid() % 11 * 22, (menu.textureid() / 11) * 44 + (selectedgui == menu.id()? 22 : 0), 1f, 0x40FFFFFF, menu.name()));
    	}
    }

    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }
    
    public void mouseClicked(int i, int j, int k)
    {
    	txt.mouseClicked(i, j, k);
    	txtFocused = txt.getIsFocused();
    	if(k == 0)
        {
            for(int l = 0; l < menubuttons.size(); l++)
            {
            	CJB_Button cjbbutton = menubuttons.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformedMenus(cjbbutton);
                }
            }
            for(int l = 0; l < mainmenubuttons.size(); l++)
            {
            	CJB_Button cjbbutton = mainmenubuttons.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformedMainMenus(cjbbutton);
                }
            }
        }
    }
    
    public void actionPerformed(CJB_Button cjbbutton)
    {
    	if ( cjbbutton.id >= 1020 && cjbbutton.id < 1030)
    		selectedgui = cjbbutton.id;
    	
    	mc.displayGuiScreen(this);
    }
    
    public void actionPerformedMainMenus(CJB_Button cjbbutton)
    {
    	for (CJB_GuiMain menu : menus)
    	{
    		if ( menu.id() == cjbbutton.id) {
    			mc.displayGuiScreen(menu.getGui());
        		return;
    		}
    	}
    	mc.displayGuiScreen(this);
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    	if ( cjbbutton.id >= 1000 && cjbbutton.id < 1020)
    		selectedmenu = cjbbutton.id;
    	
    	
    	if ( cjbbutton.id == 2000) {
    		mc.displayGuiScreen(null);
    		return;
    	}
    	
        mc.displayGuiScreen(this);
    }

    public void drawScreen(int i, int j, float f)
    {  	
    	if (menubuttons == null || buttonslist == null || mainmenubuttons == null)
    	
    	for (CJB_Button btn : menubuttons) {
    		if (btn != null) btn.onTick();
    	}
    	
    	for (CJB_Button btn : buttonslist) {
    		if (btn != null) btn.onTick();
    	}
    	
    	for (CJB_Button btn : mainmenubuttons) {
    		if (btn != null) btn.onTick();
    	}
    	
    	int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	if (CJB.darkenbg)
    		drawDefaultBackground();
    	
    	GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/cjb/menu.png"));
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	drawTexturedModalRect(k,l,0,0, menuw, menuh);
    	
    	String version = CJB.VERSION.contains("-") ? CJB.VERSION.split(" - ")[1] : CJB.VERSION;
    	drawString(fontRenderer, "CJB Mods v" + version, k + 8, l + 6, 0xffffff);
    	
	    for (CJB_Button btn : menubuttons) {
	    	btn.drawScreen(mc, i, j, 0, 0);
	    }
	    int i1 = 0;
	    int j1 = (width - mainmenubuttons.size() * 22) / 2;
	    for (CJB_Button btn : mainmenubuttons) {
	    	btn.drawScreen(mc, i, j, j1 + 22 * i1++, (height - menuh - 44) / 2);
	    }
	    
	    txt.drawTextBox();
	    drawBackground(i, j, f);
	    drawForeground(i, j, f);
	    
	    
    }
    
    public void drawForeground(int i, int j, float f)
    {
    	if (buttonslist != null && !buttonslist.isEmpty())
    	for (CJB_Button btn : buttonslist)
    	{
    		btn.drawToolTip(mc, i, j);
    	}
    	
    	if (mainmenubuttons != null && !mainmenubuttons.isEmpty())
        	for (CJB_Button btn : mainmenubuttons)
        	{
        		btn.drawToolTip(mc, i, j);
        	}
    }
    
    public void drawBackground(int i, int j, float f)
    {
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    int tex = mc.renderEngine.getTexture("/cjb/menu.png");
	    mc.renderEngine.bindTexture(tex);
	    boolean flag = Mouse.isButtonDown(0);
	    int k5 = left;
	    int l5 = top;
	    int i5 = k5 + 166;
	    int j4 = l5 + 19;
	    int k4 = i5 + 5;
	    int l4 = j4 + 125;
	    if(!wasClicking && flag && i >= i5 && j >= j4 && i < k4 && j < l4)
        {
            isScrolling = true;
        }
        if(!flag)
        {
            isScrolling = false;
        }
        wasClicking = flag;
        if(isScrolling)
        {
            currentScroll = (j - (j4 + 8)) / ((l4 - j4) - 15F);
            if(currentScroll < 0.0F)
            {
                currentScroll = 0.0F;
            }
            if(currentScroll > 1.0F)
            {
                currentScroll = 1.0F;
            }
        }
        
	    drawTexturedModalRect(i5, j4 + (int)((l4 - j4 - 15) * currentScroll), 208, 150, 5, 15);
    }
    
    public void handleMouseInput()
    {
    	super.handleMouseInput();
    	int i = Mouse.getEventDWheel();
        if(i != 0)
        {
            int j = (buttonslist.size() / colums - rows);
            if (j  <= 0)
            {
            	currentScroll = 0;
            	return;
            }
            if(i > 0)
            {
                i = 1;
            }
            if(i < 0)
            {
                i = -1;
            }
            if (j < 0) j = 0;
            currentScroll -= (float)i / (float)j;
            if(currentScroll < 0.0F)
            {
                currentScroll = 0.0F;
            }
            if(currentScroll > 1.0F)
            {
                currentScroll = 1.0F;
            }
        }
    }
    
    public boolean ChatMessage(String s)
    {
    	EntityPlayerSP plr = mc.thePlayer;
    	String m[] = s.split(" ");
    	
    	if (m[0].equalsIgnoreCase("addtime")) {
    		mc.theWorld.setWorldTime(mc.theWorld.getWorldTime()+ 12000);
    		return true;
    	}
    	
    	if (m[0].equalsIgnoreCase("killme")) {
    		plr.setHealth(-9999);
    		return true;
    	}
    	
    	if (m[0].equalsIgnoreCase("damage")) {
    		int dmg = 0;
    		
    		if (m.length == 2)
    			dmg = Integer.parseInt(m[1]);
    		
    		for (ItemStack i : CJB.plr.inventory.mainInventory) {
    			if (i != null && i.isItemStackDamageable() && !i.getHasSubtypes()) {
    				i.setItemDamage(i.getMaxDamage() - 1);
    				
    				if (dmg > 0)
    					i.setItemDamage(dmg);
    			}
    		}
    		
    		return true;
    	}

    	return false;
    }

    public void msg(String s)
    {
    	mc.ingameGUI.getChatGUI().printChatMessage(s);
    }
    
    public void updateScreen()
    {
    	chatupdateCounter++;
    	txt.updateCursorCounter();
    }

    public void keyTyped(char c, int i)
    {
        if (!changingkey.equalsIgnoreCase("")) {
        	int j = i;
        	if (j == 1) j = 0;
        	CJB_Settings.setInteger(changingkey, j);
        	changingkey = "";
        	mc.displayGuiScreen(this);
        	return;
        }
    	if(i == 1)
        {
            mc.displayGuiScreen(null);
            return;
        }
    	if(i == 28)
        {
            String s = chatmessage.trim();
            if(s.length() > 0)
            {
                if (!ChatMessage(chatmessage.trim()))
                	msg(CJB_Colors.Rose + "Unknown Command");
            }
            chatmessage = "";
            mc.displayGuiScreen(null);
            return;
        }
    	if (txt.getIsFocused()) {
    		txt.textboxKeyTyped(c, i);
    		chatmessage = txt.getText();
    		return;
    	}
    	if(!txt.getIsFocused() && (i == CJB.KeyMainMenu || i == mc.gameSettings.keyBindInventory.keyCode))
        {
    		CJB.keypressed = true;
            mc.displayGuiScreen(null);
            return;
        }
        
        if (c == 22) {
        	getClipboard();
        }
    }

    static 
    {
        field_20082_i = ChatAllowedCharacters.allowedCharacters;
    }
    
    public boolean doesGuiPauseGame()
    {
        return true;
    }
    
    public static boolean isMinimapEnabled()
    {
    	return CJB.modminimap && CJB_Settings.getBoolean("minimap.enabled", false);
    }
    
    public void checkVersion()
    {
    	if (isVersionChecked)
    		return;
    	
    	isVersionChecked = true;
    	CJB.versionstring = CJB_Colors.Green + "Checking Version...";
    	
		Thread thread = new Thread(){
			public void run(){
				try {
					URL version;
			    	String inputLine = "";
			    	
					version = new URL("http://dl-web.dropbox.com:80/u/24354188/version");
					version.openConnection().setReadTimeout(3000);
					BufferedReader in;
					InputStreamReader inputStream = new InputStreamReader(version.openStream());
					in = new BufferedReader(inputStream);
					while ((inputLine = in.readLine()) != null)
					{
						if (inputLine.contains("-") && CJB.VERSION.equalsIgnoreCase(inputLine.split(" - ")[1])) {
							CJB.versionstring = CJB_Colors.Green + "YES";
							in.close();
						    inputStream.close();
							return;
						} else {
							if (inputLine.equalsIgnoreCase(CJB.VERSION)) {
								CJB.versionstring = CJB_Colors.Green + "YES";
								in.close();
							    inputStream.close();
								return;
							}
						}
					}
					in.close();
				    inputStream.close();
					CJB.versionstring = CJB_Colors.Rose + "NO";
				    
				} catch(Throwable e) {
					CJB.versionstring = CJB_Colors.Rose + "CHECK FAILED";
				}
			}
		};
		thread.start();
    }
    
    public void readTweet()
    {
    	Thread thread = new Thread() {
    		public void run() {
    			msg(CJB_Colors.Green + "Retreiving tweet...");
    			URL version;
    	    	String inputLine = "";
    			try {
    				version = new URL("http://api.twitter.com/1/statuses/user_timeline.xml?id=CJBMods&count=1&page=1");
    				version.openConnection().setReadTimeout(3000);
    				BufferedReader in;
    				InputStreamReader inputStream = new InputStreamReader(version.openStream());
    				in = new BufferedReader(inputStream);
    				int i = 0;
    				String tweet = CJB_Colors.Gold;
    				
    				while ((inputLine = in.readLine()) != null) {
    					if (i == 3 && inputLine.contains("<created_at>")) {
    						tweet += inputLine.split("<created_at>")[1].split("</created_at>")[0].replace("+0000 ", "") + ": ";
    					}
    					
    					if (i == 5 && inputLine.contains("<text>")) {
    						tweet += CJB_Colors.Green;
    						tweet += inputLine.split("<text>")[1].split("</text>")[0];
    					}
    					i++;
    				}
    				msg(tweet);
    			    in.close();
    			    inputStream.close();
    			} catch (Throwable e) {
    				msg(CJB_Colors.Rose + "Failed to retrieve tweet!");
    			}
    		}
    	};
    	thread.start();
    }
    
    public static String removeColors(String s)
    {
    	for (int i = 0 ; i < 16 ;i++)
    	{
    		String s1 = "\u00A00" + Integer.toHexString(i);
        	s = s.replace(s1, "");
    	}
    	
    	return s;
    }
    
    public String getKey(String s) {
    	
    	String s1 = "";
    	boolean flag = isChangingKey(s);
    	
    	if (flag)
    		s1 += "> ";
    	
    	s1 += Keyboard.getKeyName(CJB_Settings.getInteger(s, 0));
    	
    	if (flag)
    		s1 += " <";
    	
    	return s1;
    }
    
    public boolean isChangingKey(String s) {
    	
    	if (s.equalsIgnoreCase(changingkey))
    		return true;
   
    	return false;
    }
    
    public void getClipboard() {
    	String s = ChatAllowedCharacters.filerAllowedCharacters(getClipboardString());
    	for (int i = 0 ; i < s.length(); i++) {
    		char c = s.charAt(i);
    		//keyTyped(c,0);
    	}
    }

	public abstract String name();
	public abstract boolean multiplayer();
	public abstract int id();
	public abstract int textureid();
	public abstract CJB_GuiMain getGui();
}
