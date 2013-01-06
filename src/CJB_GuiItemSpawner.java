package net.minecraft.src;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class CJB_GuiItemSpawner extends CJB_GuiMain
{
	private static int menuid = 1001;
	private int amount = 1;
	private int blocklistlength = 256;
	private int defaultblockids[];
	
	private int defaultitemids[];
    
	public void initGui()
    {
		defaultblockids = new int[] {0,1,2,3,4,5,6,7,8,9,
				  10,11,12,13,14,15,16,17,18,19,
				  20,21,22,23,24,25,26,27,28,29,
				  30,31,32,33,34,35,36,37,38,39,
				  40,41,42,43,44,45,46,47,48,49,
				  50,51,52,53,54,55,56,57,58,59,
				  60,61,62,63,64,65,66,67,68,69,
				  70,71,72,73,74,75,76,77,78,79,
				  80,81,82,83,84,85,86,87,88,89,
				  90,91,92,93,94,95,96,97,98,99,
				  100,101,102,103,104,105,106,107,108,109,
				  110,111,112,113,114,115,116,117,118,119,
				  120,121,122,123,124,125,126,127,128,129,
				  130,131,132,133,134,135,136,137,138,139,
				  140,141,142,143,144,145,
				  2256,2257,2258,2259,2260,2261,2262,2263,2264,2265,2266,2267,
				  3928-256};
		
    	selectedgui = 1025;
    	super.initGui();
    	selectedmenu = menuid;
    	previousScreen = this;
    	colums = 8;
    	rows = 7;
    	
    	defaultitemids = new int[400];
    	for (int i = blocklistlength ; i <= defaultitemids.length; i++)
    	{
    		defaultitemids[i-blocklistlength] = i;
    	}
    	
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	int i1 = 0;
    	int i2 = 0;
    	menubuttons.add(new CJB_Button(1001, "All", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1001? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1002, "Blocks", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1002? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1003, "Tools", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1003? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1004, "Armor/Weapons", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1004? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1005, "Dyes/Wools", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1005? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1006, "Food", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1006? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1007, "Ores/Metals", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1007? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1008, "Mechanics", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1008? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1009, "Misc", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1009? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1010, "Custom", k-80+3, l+3+(i1++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1010? 0 : 32), 1f, 0x40FFFFFF));
    	
    	menubuttons.add(new CJB_Button(1011, "Potions", k+170+3, l+3+(i2++ * 16), 80, 16, true, "/cjb/menu.png", 176, 25 + (selectedmenu == 1011? 0 : 32), 1f, 0x40FFFFFF));
    	menubuttons.add(new CJB_Button(1019, "Search", k+170+3, l+3+(i2++ * 16), 80, 16, true, "/cjb/menu.png", 176, 25 + (selectedmenu == 1019? 0 : 32), 1f, 0x40FFFFFF));
    	
    	buttonslist.clear();
    	if (selectedmenu == 1001 || selectedmenu == 1002)
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[1], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[3], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[4], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[5], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[5], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[5], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[5], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[7], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[8], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[9], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[10], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[11], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[12], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[13], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[17], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[17], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[17], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[17], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[18], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[18], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[18], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[18], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[19], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[20], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[22], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[24], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[24], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[24], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[41], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[42], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[43], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[44], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[44], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[44], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[44], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[45], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[47], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[48], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[49], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[51], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[52], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[53], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[57], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[60], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[67], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[78], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[79], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[80], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[81], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[82], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[84], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[85], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[86], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[87], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[88], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[89], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[90], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[91], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[95], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[97], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[98], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[98], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[98], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[98], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[99], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[100], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[101], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[102], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[103], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[104], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[105], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[106], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[107], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[108], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[109], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[110], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[111], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[112], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[113], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[114], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[115], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[117], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[118], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[119], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[120], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[121], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[122], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[123], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[124], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[125], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[125], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[125], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[125], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[126], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[126], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[126], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[126], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[127], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[128], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[133], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[134], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[135], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[136], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[139], 1, 0)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[139], 1, 1)));
    	}
    	
    	if (selectedmenu == 1001 || selectedmenu == 1003)
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[271], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[290], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[270], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[269], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[275], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[291], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[274], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[273], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[258], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[292], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[257], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[256], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[286], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[294], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[285], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[284], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[279], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[293], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[278], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[277], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[346], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[259], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[345], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[347], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[50], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[325], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[58], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[61], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[355], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[323], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[358], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[359], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[379], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[380], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[116], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[130], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[386], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[137], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[138], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[145], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[389], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[386], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[387], 1)));
    	}
    	
    	if (selectedmenu == 1001 || selectedmenu == 1004)
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[268], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[272], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[267], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[283], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[276], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[261], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[262], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[301], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[299], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[298], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[300], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[309], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[307], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[306], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[308], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[317], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[315], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[314], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[316], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[313], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[311], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[310], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[312], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[305], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[303], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[302], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[304], 1)));
    	}
    	
    	if (selectedmenu == 1001 || selectedmenu == 1005) 
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 0)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 4)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 5)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 6)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 7)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 8)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 9)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 10)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 11)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 12)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 13)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 14)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[351], 1, 15)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 0)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 4)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 5)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 6)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 7)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 8)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 9)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 10)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 11)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 12)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 13)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 14)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[35], 1, 15)));
    	}
    	
    	if (selectedmenu == 1001 || selectedmenu == 1006) 
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[260], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[322], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[282], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[297], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[319], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[320], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[349], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[350], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[357], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[354], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[360], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[363], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[364], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[365], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[366], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[391], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[392], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[393], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[394], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[396], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[400], 1)));
    	}
    	
    	if (selectedmenu == 1001 || selectedmenu == 1007) 
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[16], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[15], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[14], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[56], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[21], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[73], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[129], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[263], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[263], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[265], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[266], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[264], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[331], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[388], 1)));
    	}
    	
    	if (selectedmenu == 1001 || selectedmenu == 1008) 
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[324], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[330], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[328], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[333], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[342], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[343], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[356], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[69], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[70], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[72], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[77], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[25], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[331], 1)));    
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[23], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[46], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[54], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[65], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[66], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[27], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[28], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[76], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[96], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[29], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[33], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[123], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[124], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[131], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[143], 1)));
    	}
    	
    	if (selectedmenu == 1001 || selectedmenu == 1009) 
    	{
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[6], 1, 0)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[6], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[6], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[6], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[280], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[281], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[287], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[288], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[289], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[295], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[296], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[318], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[321], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[325], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[326], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[327], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[329], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[332], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[334], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[335], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[336], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[337], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[338], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[339], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[340], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[341], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[344], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[348], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[352], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[353], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2256], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2257], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2258], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2259], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2260], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2261], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2262], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2263], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2264], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2265], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2266], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[2267], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[30], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[37], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[38], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[39], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[40], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[31], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[32], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[361], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[362], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[367], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[368], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[369], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[370], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[371], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[372], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[374], 1)));	
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[375], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[376], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[377], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[378], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[381], 1)));	
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[382], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[395], 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[397], 1, 0)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[397], 1, 1)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[397], 1, 2)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[397], 1, 3)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[397], 1, 4)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[398], 1, 4)));
    		buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[399], 1, 4)));
    		Integer integer;
            for (Iterator iterator = EntityList.entityEggs.keySet().iterator(); iterator.hasNext(); buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.monsterPlacer.shiftedIndex, 1, integer.intValue()))))
            {
                integer = (Integer)iterator.next();
            }
            buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[384], 1)));
            buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[385], 1)));
    	}
    	if (selectedmenu == 1001 || selectedmenu == 1010) 
    	{
    		for (int i = 0 ; i < Item.itemsList.length ; i++)
    		{
    			if(Item.itemsList[i] != null && !isDefaultItem(i)) {
    				HashSet hashset = new HashSet();
    				
    				for (int l1 = 0 ; l1 < 100 ; l1++){
    					
    					ItemStack itemstack = new ItemStack(Item.itemsList[i], 1, l1);
    					try {
    						int k1 = Item.itemsList[i].getIconIndex(itemstack);
    						String s = itemstack.getItem().getItemName() + k1;
    						itemstack.getItemDamageForDisplay();
    						List list = itemstack.getTooltip(mc.thePlayer, false);
    						s += (String)list.get(0);

    						if (!hashset.contains(s)){
    							buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, itemstack));
    							hashset.add(s);
    						}
    						
    					} catch (Throwable e) { }
    				}
    			}
    		}
    	}
    	if (selectedmenu == 1001 || selectedmenu == 1011) 
    	{
	    	buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 32)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 64)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8193)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8194)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8195)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8196)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8197)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8200)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8201)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8202)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8204)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8225)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8226)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8228)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8229)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8233)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8236)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8257)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8258)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8257)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8259)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8260)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8264)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8265)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 8266)));
			
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16378)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16385)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16386)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16388)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16389)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16392)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16393)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16394)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16396)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16418)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16420)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16421)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16425)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16428)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16449)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16450)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16451)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16452)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16456)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16457)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16458)));
			buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, new ItemStack(Item.itemsList[373], 1, 16471)));
    	}
    	
    	if (selectedmenu == 1019)
    	{
    		searchItems();
    	}
    	
    	for (int i = 0 ; i < buttonslist.size() ; i++)
    	{
    		CJB_Button btn = buttonslist.get(i);
    		
    		if (btn instanceof CJB_ButtonItem && (btn.itemstack == null || btn.itemstack.getItem() == null))
    			buttonslist.remove(btn);
    		
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
    	ItemStack stack = cjbbutton.itemstack;
    	amount = amount > stack.getMaxStackSize() ? stack.getMaxStackSize() : amount;
    	int damage = stack.getItemDamageForDisplay();
    	if(mc.isSingleplayer()) {
    		stack.stackSize = amount;
    		stack.onCrafting(mc.theWorld, mc.thePlayer,0);  		
    		
    		if (CJB.plr.inventory.addItemStackToInventory(stack))
    		{
    			if (stack.itemID == Block.wood.blockID)
                {
    				mc.thePlayer.triggerAchievement(AchievementList.mineWood);
                }
                if (stack.itemID == Item.leather.shiftedIndex)
                {
                	mc.thePlayer.triggerAchievement(AchievementList.killCow);
                }
                if (stack.itemID == Item.diamond.shiftedIndex)
                {
                	mc.thePlayer.triggerAchievement(AchievementList.diamonds);
                }
                if (stack.itemID == Item.blazeRod.shiftedIndex)
                {
                	mc.thePlayer.triggerAchievement(AchievementList.blazeRod);
                }
                ModLoader.onItemPickup(mc.thePlayer, stack);
                mc.theWorld.playSoundAtEntity(mc.thePlayer, "random.pop", 0.2F, ((mc.theWorld.rand.nextFloat() - mc.theWorld.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
    		}
    	}
    	else
    	{
    		String cmd = mod_cjb_itemspawner.itemcommand;
    		
    		cmd = cmd.replace("#u", mc.thePlayer.username);
    		cmd = cmd.replace("#i", Integer.toString(stack.itemID));
    		cmd = cmd.replace("#a", Integer.toString(amount));
    		cmd = cmd.replace("#d", Integer.toString(damage));
    		mc.thePlayer.sendChatMessage(cmd);
    	}
    }
    public void drawForeground(int i, int j, float f)
    {
	    super.drawForeground(i, j, f);
    }

    public void drawBackground(int i, int j, float f)
    {  	
    	super.drawBackground(i, j, f);
    	
    	amount = 1;
    	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) amount = 10;
    	if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) amount = 64;
    	
    	int i2 = (buttonslist.size() / colums - rows) + 1;
        int j2 = (int)((double)(currentScroll * (float)i2) + 0.5D);
        if(j2 < 0)
        {
            j2 = 0;
        }
        ItemStack stacktext = null;
        
        for(int k = 0; k < rows; k++)
        {
            for(int l = 0; l < colums; l++)
            {
                int i1 = l + (k + j2) * colums;
                if(i1 >= 0 && i1 < buttonslist.size())
                {
        	        buttonslist.get(i1).drawScreen(mc, i, j, l * 18, k * 18);
        	        
        	        if (buttonslist.get(i1).isMouseOver)
        	        {
        	        	stacktext = buttonslist.get(i1).itemstack;
        	        }
                }
            }
            
        }
        
        i2 = 0;
        try
        {
	        if (stacktext != null && stacktext.getItem() != null)
	        {
	        	GL11.glDisable(2896 /*GL_LIGHTING*/);
	            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
	            List list = stacktext.getTooltip(mc.thePlayer, false);
	        	int ItemDamage = 0;
		    	if (stacktext.getItemDamageForDisplay() > 0)
		    		ItemDamage = stacktext.getItemDamageForDisplay();
	            int i6 = 0;
	            for (int i4 = 0 ; i4 < list.size() ; i4++)
	            {
	            	RenderItem itemRenderer = new RenderItem();
	            	zLevel = 200F;
	                itemRenderer.zLevel = 100F;
	            	int k1 = i + 12;
	                int i3 = j - 12;
	                
	                String s = (String)list.get(i4);
	                
	                if (i4 == 0)
	                	s = stacktext.itemID + (ItemDamage != 0?":" + ItemDamage:"")  + " | " + s + " x " + Integer.toString(amount > stacktext.getMaxStackSize() ? stacktext.getMaxStackSize() : amount);
	                int j3 = fontRenderer.getStringWidth(s);
	                
	                if (k1+j3 > width)
	                	k1 = width - j3;
	                
	                if (i4 == 0)
	                {
	                	i6 = k1;
	                }
	
	                drawGradientRect(i6 - 3, i3 - 3 + (i4 * 14), i6 + j3 + 3, i3 + 8 + 3 + (i4 * 10), 0xc0000000, 0xc0000000);
	                
	                drawString(fontRenderer, s, i6, i3 + (10 * i4), -1);
	                zLevel = 0F;
	                itemRenderer.zLevel = 0F;
	            }
	        }
        } catch(Throwable e){}
    }
    
    private boolean isDefaultItem(int i) {
    	
    	for (int j = 0 ; j < defaultblockids.length ; j++)
    	{
    		if (defaultblockids[j] == i)
    			return true;
    	}
    	
    	for (int j = 0 ; j < defaultitemids.length ; j++)
    	{
    		if (defaultitemids[j] == i)
    			return true;
    	}
    	
    	return false;
    }
    
    public void keyTyped(char c, int i)
    {
    	 super.keyTyped(c, i);
    	 
    	 if (txt.getIsFocused()) {
    		 menuid = 1019;
    		 searchItems();
    		 mc.displayGuiScreen(this);
    		 return;
    	 }
    }
    
    public void searchItems(){
    	if (chatmessage.isEmpty())
    		return;
    	
    	int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	
    	for (int i = 0 ; i < Item.itemsList.length ; i++)
		{
			if(Item.itemsList[i] != null) {
				HashSet hashset = new HashSet();
				
				for (int l1 = 0 ; l1 < 100 ; l1++){
					
					ItemStack itemstack = new ItemStack(Item.itemsList[i], 1, l1);
					try {
						int k1 = Item.itemsList[i].getIconIndex(itemstack);
						String s = itemstack.getItem().getItemName() + k1;
						itemstack.getItemDamageForDisplay();
						List list = itemstack.getTooltip(mc.thePlayer, false);
					
						for ( int i1 = 0 ; i1 < list.size() ; i1 ++)
						{
							String s1 = (String) list.get(i1);
							if ((s.toLowerCase().contains(chatmessage.toLowerCase()) || s1.toLowerCase().contains(chatmessage.toLowerCase())) && !hashset.contains(s1))
							{
								buttonslist.add(new CJB_ButtonItem(-1, k + 14, l + 19, itemstack));
								hashset.add(s1);
							}
						}
						
					} catch (Throwable e) {};
				}
			}
		}
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }

	public String name() {
		return "Item Spawner";
	}

	public boolean multiplayer() {
		return true;
	}
	
	public int id() {
		return 1025;
	}
	
	public int textureid() {
		return 4;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiItemSpawner();
	}
}
