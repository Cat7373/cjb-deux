package net.minecraft.src;

import net.minecraft.world.storage.WorldInfo;

public class CJB_GuiCheats extends CJB_GuiMain
{
	private static int menuid = 1001;
    
    public void initGui()
    {
    	super.initGui();
    	previousScreen = this;
    	selectedmenu = menuid;
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
        int i = 0;
        
        menubuttons.add(new CJB_Button(1001, "Player", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1001? 0 : 32), 1f, 0x40FFFFFF));
        menubuttons.add(new CJB_Button(1002, "Items/Blocks", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1002? 0 : 32), 1f, 0x40FFFFFF));
        menubuttons.add(new CJB_Button(1003, "Weather", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1003? 0 : 32), 1f, 0x40FFFFFF));
        menubuttons.add(new CJB_Button(1004, "Fly/Move", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1004? 0 : 32), 1f, 0x40FFFFFF));
        
        k += 8;
        l += 19;
        
        if (selectedmenu == 1001) {
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(10, "Infinite Health = " + Boolean.toString(CJB.infinitehealth), k, l, true, "Player can't die"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(11, "Unbreakable Armor = " + Boolean.toString(CJB.unbreakablearmor), k, l, true, "Armor never breaks"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(12, "Infinite Breath = " + Boolean.toString(CJB.infinitebreath), k, l, true, "Never run out of air"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(13, "One Hit Kill = " + Boolean.toString(CJB.onehitkill), k, l, true, "Kill entities with one hit"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(14, "Infinite Arrows = " + Boolean.toString(CJB.infinitearrows), k, l, true, "Arrows never decrease"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(15, "No Fall Damage = " + Boolean.toString(CJB.nofalldamage), k, l, true, "Can't get hurt from a high distance"));
        	//if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(16, "Minecart Control = " + Boolean.toString(CJB.cartcontrol), k, l, true, "Control minecarts with movement keys"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(17, "Extended Reach = " + Boolean.toString(CJB.extendedreach), k, l, true, "Extends distance to place and break blocks"));
        	//if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(18, "Infinite Items = " + Boolean.toString(CJB.infiniteitems), k, l, true, "Place/use unlimited items"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(19, "Infinite XP = " + Boolean.toString(CJB.infinitexp), k, l, true, "XP level will always be 999"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(20, "GameMode Key = " + getKey("cheat.gamemodekey"), k, l, true));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(21, "Disable Hunger = " + Boolean.toString(CJB.disablehunger), k, l, true, "Player will never be hungry"));
        }
        if (selectedmenu == 1002) {
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(30, "Instant Furnace = " + Boolean.toString(CJB.instantfurnace), k, l, true, "Smelt materials instantly"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(31, "Super Soil = " + Boolean.toString(CJB.walkoversoil), k, l, true, "Soil don't need water"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(32, "Fast Growth = " + Boolean.toString(CJB.fastgrowth), k, l, true, "Vines, mushrooms, trees etc grow much faster"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(33, "Harvest All = " + Boolean.toString(CJB.nopickaxe), k, l, true, "Harvest any kind of stone with pickaxe"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(34, "Attractive Items = " + Boolean.toString(CJB.attractiveitems), k, l, true, "Items will be attracted towards you"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(35, "Editable Mob Spawner = " + Boolean.toString(CJB.mobspawner), k, l, true, "Change mob to spawn with rightclick"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(36, "One Hit Block = " + Boolean.toString(CJB.onehitblock), k, l, true, "Break a block in one hit"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(37, "Unbreakable Items = " + Boolean.toString(CJB.unbreakableitems), k, l, true, "Items never break"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(38, "Pick Up All Arrows = " + Boolean.toString(CJB.pickupallarrows), k, l, true, "Pick up arrows shot by others"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(39, "Pickup Distance = " + (CJB.pickupdistance == 1 ?  "default" : CJB.pickupdistance), k, l, true, "Pick up items from longer distance"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(40, "No Fire Spread = " + Boolean.toString(CJB.nofirespread), k, l, true, "Disables fire from spreading"));
        	buttonslist.add(new CJB_ButtonLabel(41, "Disable Black fog = " + Boolean.toString(CJB.blackfog), k, l, true, "Disables the black fog near bedrock"));
        	//if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(42, "Infinite Lava = " + Boolean.toString(CJB.infinitelava), k, l, true, "Enable lava to create infinite sources"));
        	buttonslist.add(new CJB_ButtonLabel(43, "Fast Break Speed = " + Boolean.toString(CJB.breakspeed), k, l, true, "Increasing block break speed, for creative or one hit block"));
        }
        if (selectedmenu == 1003) {
        	WorldInfo worldInfo = CJB.w.getWorldInfo();
        	
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(50, "Always Day = " + Boolean.toString(CJB.alwaysday), k, l, true, "It will always be day"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(51, "Always Night = " + Boolean.toString(CJB.alwaysnight), k, l, true, "It will always be night"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(52, "Toggle Rain = " + Boolean.toString(worldInfo.isRaining()), k, l, true, "Set rain on or off"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(53, "Toggle Thunderstorm = " + Boolean.toString(worldInfo.isThundering()), k, l, true, "Set thunder on or off"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(54, "Disable Rain = " + Boolean.toString(CJB.rain), k, l, true, "Make it never rain"));
        	if (mc.isSingleplayer()) buttonslist.add(new CJB_ButtonLabel(55, "Disable Thunder = " + Boolean.toString(CJB.thunder), k, l, true, "Make it never thunder"));
        }
        if (selectedmenu == 1004) {
        	buttonslist.add(new CJB_ButtonLabel(70, "Toggle Flying Key = " + getKey("cheat.flykey"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(71, "Fly Up Key = " + getKey("cheat.flyupkey"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(72, "Fly Down Key = " + getKey("cheat.flydownkey"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(73, "Speed Key = " + getKey("cheat.flyspeedkey"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(74, "Mouse Control = " + Boolean.toString(CJB.mouseControl), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(75, "Speed Mode = " + (CJB.speedMode ? "Toggle" : "Hold"), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(76, "Fly Speed = " + Integer.toString(CJB.flyspeed), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(77, "Run Speed = " + Integer.toString(CJB.runspeed), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(78, "Auto Jump = " + Boolean.toString(CJB.autoJump), k, l, true));
        	buttonslist.add(new CJB_ButtonLabel(79, "Always Speed FLY = " + Boolean.toString(CJB.alwaysspeed), k, l, true));
        	//buttonslist.add(new CJB_ButtonLabel(80, "No Clip = " + Boolean.toString(CJB.noclip), k, l, true));
        }
    }

    public void onGuiClosed()
    {
        super.onGuiClosed();
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
        if (CJB.modcheats)
        {
        	if (cjbbutton.id == 10) CJB_Settings.setBooleanW("cheat.infinitehealth",CJB.infinitehealth = !CJB.infinitehealth);
        	if (cjbbutton.id == 11) CJB_Settings.setBooleanW("cheat.unbreakablearmor",CJB.unbreakablearmor = !CJB.unbreakablearmor);
        	if (cjbbutton.id == 12) CJB_Settings.setBooleanW("cheat.infinitebreath",CJB.infinitebreath = !CJB.infinitebreath);
        	if (cjbbutton.id == 13) CJB_Settings.setBooleanW("cheat.onehitkill",CJB.onehitkill = !CJB.onehitkill);
        	if (cjbbutton.id == 14) CJB_Settings.setBooleanW("cheat.infinitearrows",CJB.infinitearrows = !CJB.infinitearrows);
        	if (cjbbutton.id == 15) CJB_Settings.setBooleanW("cheat.nofalldamage",CJB.nofalldamage = !CJB.nofalldamage);
        	if (cjbbutton.id == 16) CJB_Settings.setBooleanW("cheat.cartcontrol",CJB.cartcontrol = !CJB.cartcontrol);
        	if (cjbbutton.id == 17) CJB_Settings.setBooleanW("cheat.extendedreach",CJB.extendedreach = !CJB.extendedreach);
        	if (cjbbutton.id == 18) CJB_Settings.setBooleanW("cheat.infiniteitems",CJB.infiniteitems = !CJB.infiniteitems);
        	if (cjbbutton.id == 19) CJB_Settings.setBooleanW("cheat.infinitexp",CJB.infinitexp = !CJB.infinitexp);
        	if (cjbbutton.id == 20) changingkey = "cheat.gamemodekey";
        	if (cjbbutton.id == 21) CJB.disablehunger = CJB.toggleBW("cheat.disablehunger");
        	
        	if (cjbbutton.id == 30) CJB_Settings.setBooleanW("cheat.instantfurnace",CJB.instantfurnace = !CJB.instantfurnace);
        	if (cjbbutton.id == 31) CJB_Settings.setBooleanW("cheat.walkoversoil",CJB.walkoversoil = !CJB.walkoversoil);
        	if (cjbbutton.id == 32) CJB_Settings.setBooleanW("cheat.fastgrowth",CJB.fastgrowth = !CJB.fastgrowth);
        	if (cjbbutton.id == 33) CJB_Settings.setBooleanW("cheat.nopickaxe",CJB.nopickaxe = !CJB.nopickaxe);
        	if (cjbbutton.id == 34) CJB_Settings.setBooleanW("cheat.attractiveitems",CJB.attractiveitems = !CJB.attractiveitems);
        	if (cjbbutton.id == 35) CJB_Settings.setBooleanW("cheat.mobspawner",CJB.mobspawner = !CJB.mobspawner);
        	if (cjbbutton.id == 36) CJB_Settings.setBooleanW("cheat.onehitblock",CJB.onehitblock = !CJB.onehitblock);
        	if (cjbbutton.id == 37) CJB_Settings.setBooleanW("cheat.unbreakableitems",CJB.unbreakableitems = !CJB.unbreakableitems);
        	if (cjbbutton.id == 38) CJB_Settings.setBooleanW("cheat.pickupallarrows",CJB.pickupallarrows = !CJB.pickupallarrows);
        	if (cjbbutton.id == 39) CJB_Settings.setIntegerW("cheat.pickupdistance", CJB.pickupdistance = (CJB.pickupdistance < 8 ? ++CJB.pickupdistance : 1));
        	if (cjbbutton.id == 40) CJB_Settings.setBooleanW("cheat.nofirespread",CJB.nofirespread = !CJB.nofirespread);
        	if (cjbbutton.id == 41) CJB_Settings.setBoolean("cheat.blackfog",CJB.blackfog = !CJB.blackfog);
        	if (cjbbutton.id == 42) CJB_Settings.setBooleanW("cheat.infinitelava",CJB.infinitelava = !CJB.infinitelava);
        	if (cjbbutton.id == 43) CJB_Settings.setBooleanW("cheat.breakspeed", CJB.breakspeed = !CJB.breakspeed);
        	
        	WorldInfo worldInfo = CJB.w.getWorldInfo();
        	
        	if (cjbbutton.id == 50) {
        		CJB_Settings.setBooleanW("cheat.alwaysday",CJB.alwaysday = !CJB.alwaysday);
        		CJB_Settings.setBooleanW("cheat.alwaysnight", CJB.alwaysnight = false);}
        	if (cjbbutton.id == 51) {
        		CJB_Settings.setBooleanW("cheat.alwaysnight",CJB.alwaysnight = !CJB.alwaysnight);
        		CJB_Settings.setBooleanW("cheat.alwaysday", CJB.alwaysday = false);}
        	if (cjbbutton.id == 52 || (cjbbutton.id == 53 && !worldInfo.isRaining() && !worldInfo.isThundering())) {
        		worldInfo.setRaining(!worldInfo.isRaining()); 
        		worldInfo.setRainTime(CJB.w.rand.nextInt(worldInfo.isRaining() ? 12000 : 0x29040) + 12000);
        		if (!worldInfo.isRaining()) worldInfo.setThundering(false);}
        	if (cjbbutton.id == 53) {
        		worldInfo.setThundering(!worldInfo.isThundering());
        		if (worldInfo.isThundering())
        			worldInfo.setRainTime(CJB.w.rand.nextInt(worldInfo.isRaining() ? 12000 : 0x29040) + 12000);
        		worldInfo.setThunderTime(CJB.w.rand.nextInt(worldInfo.isThundering() ? 12000 : 0x29040) + 3600);}
        	if (cjbbutton.id == 54)
        	{
        		CJB_Settings.setBooleanW("cheat.rain",CJB.rain = !CJB.rain);
        		if (CJB.rain) CJB_Settings.setBooleanW("cheat.thunder",CJB.thunder = true);
        	}
        	if (cjbbutton.id == 55) CJB_Settings.setBooleanW("cheat.thunder",CJB.thunder = !CJB.thunder);
        	
        	if (cjbbutton.id == 70) changingkey = "cheat.flykey";
        	if (cjbbutton.id == 71) changingkey = "cheat.flyupkey";
        	if (cjbbutton.id == 72) changingkey = "cheat.flydownkey";
        	if (cjbbutton.id == 73) changingkey = "cheat.flyspeedkey";
        	if (cjbbutton.id == 74) CJB_Settings.setBoolean("cheat.flymousecontrol",CJB.mouseControl = !CJB.mouseControl);
        	if (cjbbutton.id == 75) CJB_Settings.setBoolean("cheat.speedmode",CJB.speedMode = !CJB.speedMode);
        	if (cjbbutton.id == 76) CJB_Settings.setInteger("cheat.flyspeed", CJB.flyspeed = CJB.flyspeed > 9 ? 1 : ++CJB.flyspeed);
        	if (cjbbutton.id == 77) CJB_Settings.setInteger("cheat.runspeed", CJB.runspeed = CJB.runspeed > 9 ? 1 : ++CJB.runspeed);
        	if (cjbbutton.id == 78) CJB_Settings.setBoolean("cheat.autojump",CJB.autoJump = !CJB.autoJump);
        	if (cjbbutton.id == 79) CJB_Settings.setBoolean("cheat.alwaysspeed",CJB.alwaysspeed = !CJB.alwaysspeed);
        	if (cjbbutton.id == 80) CJB_Settings.setBoolean("cheat.noclip",CJB.noclip = !CJB.noclip);
        }
        
        super.actionPerformed(cjbbutton);
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
    }

	public String name() {
		return "Cheats";
	}

	public boolean multiplayer() {
		return true;
	}
	
	public int id() {
		return 1027;
	}
	
	public int textureid() {
		return 6;
	}
	
	public CJB_GuiMain getGui(){
		return new CJB_GuiCheats();
	}
}
