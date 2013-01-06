package net.minecraft.src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.lwjgl.opengl.GL11;

public class CJB_GuiModPackInfo extends CJB_GuiMain
{    
	private static int menuid = 1001;
	private static String text[] = new String[10000];
	private static String changelog[] = new String[10000];
	private static String faq[] = new String[10000];
	private static String modsinfo[] = new String[10000];
	private static boolean isFilesLoaded;

	public void initGui()
    {
    	super.initGui();
    	previousScreen = this;
    	selectedmenu = menuid;
    	rows = 25;
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;

        if(!isFilesLoaded) {
        	changelog = loadChangelog("changelog");
        	faq = loadChangelog("faq");
        	modsinfo = loadChangelog("modsinfo");
        	msg(CJB_Colors.Green + "Refreshing ModPack Info Complete");
        }
        
        if (selectedmenu == 1001) text = changelog;
        if (selectedmenu == 1002) text = faq;
        if (selectedmenu == 1003) text = modsinfo;
        
        for (String s : text) {
        	if (s != null) {
        		int i;
        		boolean morelines = false;;
                for(; mc.fontRenderer.getStringWidth(s) > 310; s = s.substring(i))
                {
                    for(i = 1; i < s.length() && mc.fontRenderer.getStringWidth(s.substring(0, i + 1)) <= 310; i++) { }
                    buttonslist.add(new CJB_ButtonLabel(-1, s.substring(0, i), k + 8, l + 19, false));
                    morelines = true;
                }

        		buttonslist.add(new CJB_ButtonLabel(-1, (morelines? "  ":"") + s, k + 8, l + 19, false));
        	}
        }
        
        int i = 0;
        menubuttons.add(new CJB_Button(1001, "Changelog", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1001? 0 : 32), 1f));
        menubuttons.add(new CJB_Button(1002, "FAQ", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1002? 0 : 32), 1f));
        menubuttons.add(new CJB_Button(1003, "Info", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1003? 0 : 32), 1f));
        menubuttons.add(new CJB_Button(1, "Refresh", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + 32, 1f));
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

    public String[] loadChangelog(String s)
    {
    	String tempstring[] = new String[10000];
    	URL version;
    	String inputLine = "";
		try {
			version = new URL("http://dl.dropbox.com/u/24354188/" + s);
			BufferedReader in;
			try {
				InputStreamReader inputStream = new InputStreamReader(version.openStream());
				in = new BufferedReader(inputStream);
				
				try {
					int i = 0;
					while ((inputLine = in.readLine()) != null)
					{
						tempstring[i++] = inputLine;
					}
				} catch (IOException e) {
					msg(CJB_Colors.Rose + "Could not load " + s + " - Error: 0");
				}
		    	in.close();
		    	inputStream.close();
			} catch (IOException e) {
				msg(CJB_Colors.Rose + "Could not load " + s + " - Error: 1");
			}
		} catch (MalformedURLException e) {
			msg(CJB_Colors.Rose + "Could not load " + s + " - Error: 2");
		}
		isFilesLoaded = true;
		return tempstring;
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    	if ( cjbbutton.id == 1) isFilesLoaded = false;
    	
    	if ( cjbbutton.id >= 1000 && cjbbutton.id < 1020)
    		menuid = cjbbutton.id;
    	
        super.actionPerformedMenus(cjbbutton);
    }
    
    public void actionPerformed(CJB_Button cjbbutton)
    {
        super.actionPerformed(cjbbutton);
    }

    public void drawBackground(int i, int j, float f)
    {
    	super.drawBackground(i, j, f);
    	int l1 = (height - menuh) / 2 + 19;
	    
	    GL11.glPushMatrix();
		GL11.glTranslatef(width/2,l1,0);
        GL11.glScalef(0.5f, 0.5f, 0f);
        GL11.glTranslatef(-width/2,-l1,0);
	    
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
	    GL11.glPopMatrix();
    }

	public String name() {
		return "HELP";
	}

	public boolean multiplayer() {
		return false;
	}
	
	public int id() {
		return 1028;
	}
	
	public int textureid() {
		return 8;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiModPackInfo();
	}
}
