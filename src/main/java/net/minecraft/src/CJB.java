package net.minecraft.src;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CJB {
	
	public static String VERSION = "6.5.1";
	public static float partialTicks = 0;
	public static CJB_RenderGlobal renderer;
	public static int rotatecounter = 0;
	public static EntityPlayer plr = null;
	public static World w = null;
	
	//MAIN
	public static int KeyMainMenu;
    public static int KeyTPMenu;
    public static int KeyWPMenu;
    public static int KeyXRay;
    public static int KeyCave;
    public static int KeyLight;
    public static int KeyItemSpawnerMenu;
    public static int KeyFly;
    public static int KeyFlyUp;
    public static int KeyFlyDown;
    public static int KeyFlySpeed;
    public static int KeyGameMode;
    public static int KeyItemStack;
    public static int KeyZoom;
    public static int KeySpawnArea;
    public static int KeyShowOres;
    public static int KeyMeasurePointsUndo;
    public static int KeyMeasurePointsClear;
    
    public static boolean keypressed = false;
    public static boolean autocheck = true;
    public static String versionstring = "";
    
    public static boolean disablehotkeys;
    
    public static boolean modmoreinfo = false;
    public static boolean modteleport = false;
    public static boolean modminimap = false;
    public static boolean modxray = false;
    public static boolean modcheats = false;
    public static boolean moditemspawner = false;
    public static boolean modmobfilter = false;
    public static boolean moditems = false;
    public static boolean modmobs = false;
    public static boolean modchat = false;
    public static boolean modmeasures = false;
    public static boolean modquickcraft = false;
    
    public static boolean nvlight = false;
    
    public static boolean pmfly = true;
    public static boolean pmxray = true;
    public static boolean pmentity = true;
    public static boolean pmnoclip = false;
    public static boolean pmquickcraft = false;
	
    public static boolean disableAch;
    public static boolean darkenbg = true;
	
	// MOREINFO
	public static boolean clock12h = true;
	public static boolean bigicons = false;
	public static boolean hideicons = false;
	public static boolean showday = false;
	public static boolean showtime = false;
	public static boolean showfps = false;
	public static boolean showlightlevel = false;
	public static boolean showbiome = false;
	public static boolean showarrowcount = true;
	public static boolean showitemdamage = true;
	public static boolean showrain = true;
	public static boolean showthunder = true;
	public static boolean showslimes = true;
	public static boolean showmobinfo = true;
	public static boolean showcoords = true;
	public static int position = 0;
	public static boolean showspawnareas = false;
	public static boolean useskylight = true;
	public static boolean showmobhealth = true;
	public static boolean showdebuffs = true;
	
	public static boolean showores;
	public static boolean showcoal;
	public static boolean showiron;
	public static boolean showgold;
	public static boolean showdiamond;
	public static boolean showlapis;
	public static boolean showredstone;
	public static boolean showemerald;
	public static boolean drawlines;
	public static int renderWidth;
	public static int renderHeight;
	
	//CHEATS
	public static boolean infinitehealth;
	public static boolean infinitebreath;
	public static boolean unbreakableitems;
	public static boolean onehitblock;
	public static boolean onehitkill;
	public static boolean walkoversoil;
	public static boolean instantfurnace;
	public static boolean infinitearrows;
	public static boolean unbreakablearmor;
	public static boolean alwaysday;
	public static boolean alwaysnight;
	public static boolean nofalldamage;
	public static boolean cartcontrol;
	public static boolean extendedreach;
	public static boolean fastgrowth;
	public static boolean nopickaxe;
	public static boolean pickupallarrows;
	public static boolean attractiveitems;
	public static boolean mobspawner;
	public static boolean infiniteitems;
	public static boolean rain;
	public static boolean thunder;
	public static boolean infinitexp;
	public static boolean nofirespread;
	public static int pickupdistance;
	public static boolean blackfog;
	public static boolean disablehunger;
	public static boolean infinitelava;
	public static boolean breakspeed = false;
	
	public static boolean entityplayer;
	public static boolean flying;
	public static boolean mouseControl;
	public static int flyspeed;
	public static int runspeed;
	public static boolean toggleSpeed;
	public static boolean speedMode;
	
	public static boolean flew;
	public static boolean guiopen;
	public static boolean autoJump;
	public static boolean alwaysspeed;
	public static boolean noclip;
	
	//CHAT
	public static boolean chatopen;
	public static List<CJB_Data> pingwords;
	public static List<CJB_Data> pingblockwords;
	public static boolean enableping;
	public static int pingvolume; 
	public static Boolean pingdelayed;
	public static long pingSystemTime = System.currentTimeMillis();
	
	//QUICKCRAFT
	public static boolean quickcraft;
	public static boolean useditems[] = new boolean[36];
	public static boolean showuncraftablerecipes = true;
	
	// Measures
	public static Stack<CJB_Data> measures = new Stack<CJB_Data>();
	public static int mesitem;
	
	// XRAY
	public static int blacklist[] = {1,2,3,4,7,12,13,17,18,24,31,32,78,87,88,106,121};
	
	// MINIMAP
	public static List<CJB_Data> mmwaypoints = new ArrayList<CJB_Data>();
	public static boolean mmenabled = true;
	public static int mmzoom = 0;
	public static boolean mmmobs = true;
	public static boolean mmplayers = true;
	public static boolean mmitems = true;
	public static boolean mmcoords = true;
	public static boolean mmshowsnow = true;
	public static boolean mmside = true;
	public static int mmtrans = 0;
	public static boolean mmsquare = false;
	public static boolean mmslimechunks = false;
	public static int mmsize = 0;
	public static boolean mmskylight = true;
	public static boolean mmnoborder = false;
	public static boolean mmshadow = true;
	public static boolean mmaltindicator = false;
	public static boolean mmthreading = true;
	public static int mmpriority = 2;
	public static int mmfrequency = 2;
	public static boolean mmshowallwp = true;
	public static List<CJB_Data> mmblockcolors = new ArrayList<CJB_Data>();
	public static List<CJB_Data> EntityColors = new ArrayList<CJB_Data>();
	
	// TELEPORT
	public static List<CJB_Data> locations = new ArrayList<CJB_Data>();
	
	public static boolean toggleB(String s){
		boolean flag = !CJB_Settings.getBoolean(s, false);
		CJB_Settings.setBoolean(s, flag);
		return flag;
	}
	
	public static boolean toggleBW(String s){
		boolean flag = !CJB_Settings.getBooleanW(s, false);
		CJB_Settings.setBooleanW(s, flag);
		return flag;
	}
	
	public static int toggleInt(String s, int maxint){
		int i = CJB_Settings.getInteger(s, 0) + 1;
		
		if (i > maxint) i = 0;
		
		CJB_Settings.setInteger(s, i);
		return i;
	}
	
	public static void invokePrivateMethod(Class c, Object obj, Object args[], String s)
	{
		try
		{
			Method methods[] = c.getDeclaredMethods();
			for (int i = 0 ; i < methods.length ; i++)
			{
				if ( methods[i].getName().equalsIgnoreCase(s)) 
				{
					methods[i].setAccessible(true);
					methods[i].invoke(obj, args);
				}
			}
		} catch (Throwable e){e.printStackTrace();}
	}
}
