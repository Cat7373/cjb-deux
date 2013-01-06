package net.minecraft.src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.TcpConnection;
import net.minecraft.world.storage.SaveHandler;

public class CJB_Settings 
{
	private static Properties p = new CJB_Properties();
	public static Properties pw = new CJB_Properties();
	private static File cjbdirs = new File(Minecraft.getMinecraftDir(), "mods/cjb/");
	private static String settingsFile = "settings.txt";
	
	public static Minecraft mc;
	
	public CJB_Settings()
	{
	}
	
	public static void setBoolean(String s, boolean flag)
	{
		p.setProperty(s, Boolean.toString(flag));
		saveSettings();
	}
	
	public static boolean getBoolean(String s, boolean flag)
	{
		loadSettings();
		if (p.containsKey(s))
			return Boolean.parseBoolean(p.getProperty(s));
		
		setBoolean(s, flag);
		return flag;
	}
	
	public static void setBooleanW(String s, boolean flag)
	{
		pw.setProperty(s, Boolean.toString(flag));
		saveSettingsW();
	}
	
	public static boolean getBooleanW(String s, boolean flag)
	{
		loadSettingsW();
		if (pw.containsKey(s))
			return Boolean.parseBoolean(pw.getProperty(s));
		
		setBooleanW(s, flag);
		return flag;
	}
	
	public static void setInteger(String s, int i)
	{
		p.setProperty(s, Integer.toString(i));
		saveSettings();
	}
	
	public static int getInteger(String s, int i)
	{
		loadSettings();
		if (p.containsKey(s))
				return Integer.parseInt(p.getProperty(s));
		
		setInteger(s,i);
		return i;
	}
	
	public static void setIntegerW(String s, int i)
	{
		pw.setProperty(s, Integer.toString(i));
		saveSettingsW();
	}
	
	public static int getIntegerW(String s, int i)
	{
		loadSettingsW();
		if (pw.containsKey(s))
				return Integer.parseInt(pw.getProperty(s));
		
		setIntegerW(s,i);
		return i;
	}
	
	public static void setString(String s, String s1)
	{
		p.setProperty(s, s1);
		saveSettings();
	}
	
	public static String getString(String s, String s1)
	{
		loadSettings();
		if (p.containsKey(s))
			return p.getProperty(s);
		
		setString(s, s1);
		return s1;
	}
	
	public static void setStringW(String s, String s1)
	{
		pw.setProperty(s, s1);
		saveSettingsW();
	}
	
	public static String getStringW(String s, String s1)
	{
		loadSettingsW();
		if (pw.containsKey(s))
			return pw.getProperty(s);
		setStringW(s, s1);
		return s1;
	}
	
	private static void saveSettings()
	{
		fileExist();
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(cjbdirs, settingsFile));
			p.store(outputStream,null);
			outputStream.close();
		} catch (Throwable e) {}
	}
	
	private static void loadSettings()
	{
		mc = ModLoader.getMinecraftInstance();
		fileExist();
		try {
			FileInputStream inputstream = new FileInputStream(new File(cjbdirs, settingsFile));
			p.load(inputstream);
			
			if (p.containsKey("menukey"))
				p = new CJB_Properties();
			
			inputstream.close();
		} catch (Throwable e) {}
	}
	
	private static void saveSettingsW()
	{
		mc = ModLoader.getMinecraftInstance();
		
		if (mc.theWorld == null) {
			return;
		}
		try {
			FileOutputStream outputStream = new FileOutputStream(new File(((SaveHandler) mc.getIntegratedServer().worldServerForDimension(0).getSaveHandler()).getSaveDirectory(),"cjb_settings.txt"));
			pw.store(outputStream,null);
			outputStream.close();
		} catch (Throwable e) {}
	}
	
	private static void loadSettingsW()
	{
		mc = ModLoader.getMinecraftInstance();
		
		if (mc.theWorld == null) {
			return;
		}
		
		try {
			FileInputStream inputstream = new FileInputStream(new File(((SaveHandler) mc.getIntegratedServer().worldServerForDimension(0).getSaveHandler()).getSaveDirectory(),"cjb_settings.txt"));
			pw.load(inputstream);
			
			if (pw.containsKey("menukey"))
				pw = new CJB_Properties();
			
			inputstream.close();
		} catch (Throwable e) {}
	}
	
	private static void fileExist()
	{
		if (!new File(Minecraft.getMinecraftDir(), "mods/cjb").exists())
			new File(Minecraft.getMinecraftDir(), "mods/cjb").mkdirs();
	}
	
	public static CJB_Data getLocationByName(String name)
    {
	    if (CJB.locations == null)
	    	return null;
	    
	    for (CJB_Data loc : CJB.locations)
	    	if (loc.Name.equalsIgnoreCase(name))
	    		return loc;
	    	
	    return null;
    }
	
	public synchronized static void saveData(List<CJB_Data> data, String filename, boolean worldsave)
    {
		if (data == null)
			return;
			
		File locfile = null;
		
		if (worldsave) {
			if (mc.isSingleplayer())
				locfile = new File(((SaveHandler) mc.getIntegratedServer().worldServerForDimension(0).getSaveHandler()).getSaveDirectory(),"cjb_" + filename + ".txt");
			else
				locfile = new File(Minecraft.getMinecraftDir(), "saves/cjb_" + getServerName() + filename + ".txt");
		} else {
			locfile = new File(cjbdirs, filename + ".txt");
		}
		try {
			
			FileWriter outFile = new FileWriter(locfile, false);
			PrintWriter out = new PrintWriter(outFile);
			
			int i = 0;
			for (CJB_Data d : data) {
				
				String s = "";
				s += i++;
				s += "|";
				s += d.Name.replace("|", "");
				s += "|";
				s += (d.posx);
				s += "|";
				s += (d.posy);
				s += "|";
				s += (d.posz);
				s += "|";
				s += d.data;
				s += "|";
				s += Integer.toHexString(d.color);
				
				out.println(s);
			}
			
			out.close();
			outFile.close();
			
		} catch (Throwable e) {e.printStackTrace();}
   	}
	
	public synchronized static void loadData(List<CJB_Data> data, String filename, boolean worldsave)
    {	
		
		data.clear();
		
		File locfile = null;
		
		if (worldsave) {
			if (mc.isSingleplayer())
				locfile = new File(((SaveHandler) mc.getIntegratedServer().worldServerForDimension(0).getSaveHandler()).getSaveDirectory(),"cjb_" + filename + ".txt");
			else
				locfile = new File(Minecraft.getMinecraftDir(), "saves/cjb_" + getServerName() + filename + ".txt");
		} else {
			locfile = new File(cjbdirs, filename + ".txt");
		}
		try {
			
			if (!locfile.exists()) return;
			
			BufferedReader in = new BufferedReader(new FileReader(locfile));
			
			String s;
			while ((s = in.readLine()) != null) {
				CJB_Data d = new CJB_Data();
				String sa[] = s.split("\\|");
				// line: id, name, posx, posy, posz, data
				if (sa.length == 6) {
					d.id = Integer.parseInt(sa[0]);
					d.Name = sa[1];
					d.posx = Double.parseDouble(sa[2]);
					d.posy = Double.parseDouble(sa[3]);
					d.posz = Double.parseDouble(sa[4]);
					d.data = Integer.parseInt(sa[5]);
					d.color = 0xffff00;
				}
				
				if (sa.length == 7) {
					d.id = Integer.parseInt(sa[0]);
					d.Name = sa[1];
					d.posx = Double.parseDouble(sa[2]);
					d.posy = Double.parseDouble(sa[3]);
					d.posz = Double.parseDouble(sa[4]);
					d.data = Integer.parseInt(sa[5]);
					d.color = (int) Long.parseLong(sa[6],16);
				}
				data.add(d);
			}
			
			in.close();
		} catch (Throwable e) {e.printStackTrace();}
   	}
    
    @SuppressWarnings("unchecked")
	public static void LoadLocations()
    {
    	
    	File locfile = null;
    	if (mc.isSingleplayer())
			locfile = new File(((SaveHandler) CJB.w.getSaveHandler()).getSaveDirectory(),"cjb_locations.dat");
		else
			locfile = new File(Minecraft.getMinecraftDir(), "saves/cjb_" + getServerName() + "_locations.dat");
    	
    	if(!locfile.exists())
    	{
    		CJB.locations = new ArrayList<CJB_Data>();
    		loadData(CJB.locations, "locations", true);
    	} else {
    		
    		ObjectInputStream inStream = null;
    		
    		try {
				inStream = new ObjectInputStream(new FileInputStream(locfile));
				CJB.locations = (List<CJB_Data>) inStream.readObject();
				int i = 0;
				if (CJB.locations != null)
				{
			  		for (CJB_Data loc : CJB.locations)
			  		{
			  			loc.id = i++;
			  		}
		  		} else {
		  			CJB.locations = new ArrayList<CJB_Data>();
		  		}
				inStream.close();
				
				saveData(CJB.locations, "locations", true);
				locfile.delete();
			} catch (Throwable e) {
				CJB.locations = new ArrayList<CJB_Data>();
				e.printStackTrace();
			}
    	}
   	}
    
    public static CJB_Data getWaypointByName(String name)
    {
	    if (CJB.mmwaypoints == null)
	    	return null;
	    
	    for (CJB_Data wp : CJB.mmwaypoints)
	    	if (wp.Name.equalsIgnoreCase(name))
	    		return wp;
	    	
	    return null;
    }
    
    @SuppressWarnings("unchecked")
	public static void LoadWaypoints()
    {
    	File locfile;
    	if (mc.isSingleplayer())
    		locfile = new File(((SaveHandler) CJB.w.getSaveHandler()).getSaveDirectory(),"cjb_waypoints.dat");
    	else {
    		locfile = new File(Minecraft.getMinecraftDir(), "saves/cjb_" + getServerName() + "_waypoints.dat");
    	}
	    if(!locfile.exists())
	    {
	    	CJB.mmwaypoints = new ArrayList<CJB_Data>();
	    	loadData(CJB.mmwaypoints, "waypoints", true);
	    } else {
	    	
	    	ObjectInputStream inStream = null;
	    	
	    	try {
				inStream = new ObjectInputStream(new FileInputStream(locfile));
				CJB.mmwaypoints = (List<CJB_Data>) inStream.readObject();
				int i = 0;
				if (CJB.mmwaypoints != null)
				{
			  		for (CJB_Data wp : CJB.mmwaypoints)
			  		{
			  			wp.id = i++;
			  		}
		  		} else {
		  			CJB.mmwaypoints = new ArrayList<CJB_Data>();
		  		}
					
				inStream.close();
				
				saveData(CJB.mmwaypoints, "waypoints", true);
				locfile.delete();
			} catch (Throwable e) {
				CJB.mmwaypoints = new ArrayList<CJB_Data>();
				e.printStackTrace();
			}
	    }
    }
    
    public static String getServerName()
	{
		try
		{
			NetClientHandler handler = mc.getSendQueue();
			if (handler == null)
				return "No handler";
			
			TcpConnection nm = (TcpConnection) ModLoader.getPrivateValue(NetClientHandler.class, handler, 1);
			if (nm == null)
				return "No NetworkManager";
			
			Socket socket = (Socket) ModLoader.getPrivateValue(TcpConnection.class, nm, 3);
			if (socket == null)
				return "No socket";
			
			return socket.getInetAddress().getHostName() + (socket.getPort() == 25565 ? "" : " " + socket.getPort());
			
		} catch(Throwable e){return "Error: " + e;}
	}
}
