package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class CJB_EntityColor {
	public static List<CJB_Data> entities= new ArrayList<CJB_Data>();
	public String name = "";
	public int color = 0x00ff00;
	
	public CJB_EntityColor(String name, int color) {
		this.name = name;
		this.color = color;
	}
	
	public static String defaultcolors = "humanoid,ff00ff;" +
										 "Item,ffffff;" +
			
										 "Cow,00ff00;" +
										 "Chicken,00ff00;" +
										 "Pig,00ff00;" +
										 "Sheep,00ff00;" +
										 "Wolf,00ff00;" +
										 "Ozelot,00ff00;" +
										 "SnowMan,00ff00;" +
										 "MushroomCow,00ff00;" +
										 "Squid,00ff00;" +
										 
										 "Villager,FF6E2B;" +
										 "VillagerGolem,C0C0C0;" +
										 
										 "Creeper,ff0000;" +
										 "Skeleton,ff0000;" +
										 "Spider,ff0000;" +
										 "Giant,ff0000;" +
										 "Zombie,ff0000;" +
										 "Slime,ffff00;" +
										 "Ghast,ff0000;" +
										 "PigZombie,ff0000;" +
										 "Enderman,010000;" +
										 "CaveSpider,ff0000;" +
										 "Silverfish,ff0000;" +
										 "Blaze,ff0000;" +
										 "LavaSlime,ff0000;" +
										 "";
	
	public static void loadEntityColors() {
		entities.clear();
		String list[] = CJB_Settings.getString("minimap.entitycolors", defaultcolors).split(";");
		
		for (int i = 0 ; i < list.length ; i++) {
			String ent[] = list[i].split(",");
			
			if (ent.length < 2) continue;
			
			CJB_Data data = new CJB_Data();
			data.Name = ent[0];
			data.color = Integer.parseInt(ent[1], 16);
			entities.add(data);
		}
	}
	
	public static void saveEntityColors() {
		String s = "";
		for (CJB_Data data : entities) {
			s += data.Name;
			s += ",";
			s += Integer.toHexString(data.color);
			s += ";";
		}
		CJB_Settings.setString("minimap.entitycolors", s);
	}
}
