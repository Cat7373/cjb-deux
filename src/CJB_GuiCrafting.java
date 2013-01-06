package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class CJB_GuiCrafting extends GuiContainer
{
	private List<CJB_Button> menubuttons = new ArrayList<CJB_Button>();
    private List<CJB_ButtonItem> inventorybuttons = new ArrayList<CJB_ButtonItem>();
    private List<CJB_ButtonItem> recipebuttons = new ArrayList<CJB_ButtonItem>();
    
    private List<CJB_Button> categorybuttons = new ArrayList<CJB_Button>();
    private static int selectedcat = 4;
    
    private ItemStack selectedrecipe;
    private ItemStack[] recipestack;
    private int menuw = 256;
    private int menuh = 166;
    private EntityClientPlayerMP player;
    private ItemStack selecteditem = null;
    
    private boolean wasClicking = false;
    private boolean isScrolling = false;
    private float currentScroll = 0.0F;
    
    private int defaultrecipessize;
    private int posx;
    private int posy;
    private int posz;
    boolean isPortable;
    
    public CJB_GuiCrafting(EntityClientPlayerMP player, int x, int y, int z, boolean flag)
    {
    	super(null);
    	this.player = player;
    	this.posx = x;
    	this.posy = y;
    	this.posz = z;
    	isPortable = flag;
    }

    public void initGui()
    {
    	CJB_Recipe.load();
    	defaultrecipessize = 206;
    	
    	CJB.showuncraftablerecipes = CJB_Settings.getBoolean("quickcraft.showuncraftablerecipes", true);
    	
    	inventorybuttons = new ArrayList<CJB_ButtonItem>();
        recipebuttons = new ArrayList<CJB_ButtonItem>();
        categorybuttons = new ArrayList<CJB_Button>();
        menubuttons = new ArrayList<CJB_Button>();
        
        menubuttons.add(new CJB_Button(9999, "", 0, 0, 26, 23, true, "/cjb/crafting.png", 215, 166, 1f, 0x40FFFFFF, "Classic"));
        menubuttons.add(new CJB_Button(9998, "", 0, 0, 26, 23, true, "/cjb/crafting.png", 215, 189 + (!CJB.showuncraftablerecipes ? 23 : 0), 1f, 0x40FFFFFF, "Show uncraftable recipes"));
        
        categorybuttons.add(new CJB_Button(4, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 4 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Building Blocks").setData1(45));
        categorybuttons.add(new CJB_Button(5, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 5 ? 0 : 26), 166 , 1f, 0x40FFFFFF, "Decoration Blocks").setData1(38));
        categorybuttons.add(new CJB_Button(8, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 8 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Redstone").setData1(331));
        categorybuttons.add(new CJB_Button(7, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 7 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Transportation").setData1(27));
        categorybuttons.add(new CJB_Button(6, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 6 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Miscellaneous").setData1(280));
        categorybuttons.add(new CJB_Button(1, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 1 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Foodstuff").setData1(260));
        categorybuttons.add(new CJB_Button(2, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 2 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Tools").setData1(258));
        categorybuttons.add(new CJB_Button(3, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 3 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Combat").setData1(283));
        categorybuttons.add(new CJB_Button(9, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 9 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Materials").setData1(327));
        
	    categorybuttons.add(new CJB_Button(104, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 104 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Building Blocks").setData1(45));
	    categorybuttons.add(new CJB_Button(105, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 105 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Decoration Blocks").setData1(38));
	    categorybuttons.add(new CJB_Button(108, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 108 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Redstone").setData1(331));
	    categorybuttons.add(new CJB_Button(107, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 107 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Transportation").setData1(27));
	    categorybuttons.add(new CJB_Button(106, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 106 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Miscellaneous").setData1(280));
	    categorybuttons.add(new CJB_Button(101, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 101 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Foodstuff").setData1(260));
	    categorybuttons.add(new CJB_Button(102, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 102 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Tools").setData1(258));
	    categorybuttons.add(new CJB_Button(103, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 103 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Combat").setData1(283));
	    categorybuttons.add(new CJB_Button(109, "", 0, 0, 26, 26, true, "/cjb/crafting.png", 0 + (selectedcat == 109 ? 0 : 26), 166, 1f, 0x40FFFFFF, "Custom Materials").setData1(327));
        
        for (int i = 0 ; i < player.inventory.mainInventory.length; i++)
    	{
    		int id = -1;
    		if (player.inventory.mainInventory[i] != null) id = player.inventory.mainInventory[i].itemID;
    		inventorybuttons.add(new CJB_ButtonItem(id,0,0,player.inventory.mainInventory[i]));
    	}
    	
        if (selecteditem != null) {
        	for (int i = 0 ; i < (mc.isSingleplayer() ? CJB_Recipe.recipes.size() : this.defaultrecipessize) ; i++)
	    	{
        		CJB_Recipe rec = CJB_Recipe.recipes.get(i);
        		ItemStack item = rec.result;
	    		if (item == null || item.getItem() == null) continue;
        		if (rec.containsItem(selecteditem) && (CJB.showuncraftablerecipes || CJB_Recipe.recipes.get(i).isCraftable(player, true)))
        			recipebuttons.add(new CJB_ButtonItem(rec.id,0,0,item, CJB_Recipe.recipes.get(i).isCraftable(player, true)));
	    	}
		} else
        if (selectedcat < 100)
        {
	    	for (int i = 0 ; i < defaultrecipessize ; i++)
	    	{
	    		CJB_Recipe rec = CJB_Recipe.recipes.get(i);
	    		ItemStack item = rec.result;
	    		
	    		if (item == null || item.getItem() == null) continue;
	    		
	    		
	    		else if (rec.getCategory(item.getItem()) == selectedcat && (CJB.showuncraftablerecipes || CJB_Recipe.recipes.get(i).isCraftable(player, true)))
	    			recipebuttons.add(new CJB_ButtonItem(rec.id,0,0,item, CJB_Recipe.recipes.get(i).isCraftable(player, true)));
	    		
			} 
        } else {
        	for (int i = defaultrecipessize ; i < CJB_Recipe.recipes.size() ; i++)
	    	{
	    		CJB_Recipe rec = CJB_Recipe.recipes.get(i);
	    		ItemStack item = rec.result;
	    		
	    		if (item == null || item.getItem() == null) continue;
	    		
	    		if (rec.getCategory(item.getItem()) + 100 == selectedcat && (CJB.showuncraftablerecipes || CJB_Recipe.recipes.get(i).isCraftable(player, true)))
	    			recipebuttons.add(new CJB_ButtonItem(rec.id,0,0,item, CJB_Recipe.recipes.get(i).isCraftable(player, true)));
			} 
        }
    }

    @Override
    public void onGuiClosed()
    {
    }
    
    @Override
    public void mouseClicked(int i, int j, int k)
    {
    	if(k == 0)
        {
            for(int l = 0; l < inventorybuttons.size(); l++)
            {
            	CJB_ButtonItem cjbbutton = inventorybuttons.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformed(cjbbutton);
                }
            }
            for(int l = 0; l < categorybuttons.size(); l++)
            {
            	CJB_Button cjbbutton = (CJB_Button)categorybuttons.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformedCategory(cjbbutton);
                }
            }
            for(int l = 0; l < menubuttons.size(); l++)
            {
            	CJB_Button cjbbutton = (CJB_Button)menubuttons.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformedCategory(cjbbutton);
                }
            }
            for(int l = 0; l < recipebuttons.size(); l++)
            {
            	CJB_ButtonItem cjbbutton = recipebuttons.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformedCraft(cjbbutton);
                }
            }
        }
    }
    
    public void actionPerformed(CJB_ButtonItem cjbbutton)
    {
    	selecteditem = cjbbutton.itemstack;
    	selectedcat = 0;
    	mc.displayGuiScreen(this);
    }
    
    public void actionPerformedCategory(CJB_Button cjbbutton)
    {
    	if (cjbbutton.id == 9999)
    	{
    		CJB.quickcraft = false;
    		if (mc.isSingleplayer() && isPortable) {
	    		ModLoader.serverOpenWindow((EntityPlayerMP)CJB.plr, new CJB_PWBContainer(CJB.plr.inventory, CJB.w), 39211, 0, 0, 0);
    		} else {
    			mc.thePlayer.displayGUIWorkbench(posx, posy, posz);
    		}
    		return;
    	}
    	
    	if (cjbbutton.id == 9998)
    	{
    		CJB.showuncraftablerecipes = CJB.toggleB("quickcraft.showuncraftablerecipes");
    		mc.displayGuiScreen(this);
    		return;
    	}
    	
    	selecteditem = null;
    	selectedcat = cjbbutton.id;
    	mc.displayGuiScreen(this);
    }
    
    public void actionPerformedCraft(CJB_ButtonItem cjbbutton)
    {
    	CJB_Recipe rec = CJB_Recipe.getRecipe(cjbbutton.id);
    	
    	System.out.println(rec.id + " " + rec.result.getItemName() + " " + rec.result.itemID);
    	
    	if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
    		boolean flag = true;
    		int i = 0;
    		while (flag && i < rec.result.getMaxStackSize())
    		{
    			rec.setFakeInventory(player);
    	    	if (rec.isCraftable(player, true)) {
    	    		rec.setRealInventory(player);
    	    		rec.craftRecipe(player);
    	    		i += rec.result.stackSize;
    	    	} else 
    	    		flag = false;
    		}
    	} else if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
    		boolean flag = true;
    		int i = 0;
    		while (flag)
    		{
    			rec.setFakeInventory(player);
    	    	if (rec.isCraftable(player, true)) {
    	    		rec.setRealInventory(player);
    	    		rec.craftRecipe(player);
    	    		i += rec.result.stackSize;
    	    	} else 
    	    		flag = false;
    		}
    	} else {
	    	rec.setFakeInventory(player);
	    	if (rec.isCraftable(player, true)) {
	    		rec.setRealInventory(player);
	    		rec.craftRecipe(player);
	    	}
    	}
    	mc.displayGuiScreen(this);
    }
    
    @Override
    public void handleMouseInput()
    {
    	super.handleMouseInput();
    	if (recipebuttons.size() < 36) return;
    	int i = Mouse.getEventDWheel();
        if(i != 0)
        {
            float j = recipebuttons.size() / 12F - 3F;
            if (j <= 0 )
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

    public void drawScreen(int mousex, int mousey, float f)
    {  	
    	for (CJB_Button btn : menubuttons) {
    		btn.onTick();
    	}
    	for (CJB_Button btn : categorybuttons) {
    		btn.onTick();
    	}
    	for (CJB_Button btn : recipebuttons) {
    		btn.onTick();
    	}
    	for (CJB_Button btn : inventorybuttons) {
    		btn.onTick();
    	}
    	
    	this.guiLeft = (this.width - this.menuw) / 2;
        this.guiTop = (this.height - this.menuh) / 2;
        
    	int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	drawDefaultBackground();
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    	GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/cjb/crafting.png"));
    	drawTexturedModalRect(k,l,0,0, menuw, menuh);
    	drawCenteredString(mc.fontRenderer, "inventory", k+193, l+103, 0xffffff);
    	drawScrolling(mousex,mousey,f);
    	int custom = 0;
    	for (int i = 0 ; i < categorybuttons.size(); i++) {
	    	CJB_Button btn = categorybuttons.get(i);
	    	
	    	int j = k + 11 + i * 26;
	    	int i1 = l+6;
	    	
	    	if (btn.id >= 100) {
	    		j = k + 11 + custom++ * 26;
	    		i1 = l + 165;
	    	}
	    	
	    	btn.drawScreen(mc, mousex, mousey, j, i1);
	    	RenderHelper.enableGUIStandardItemLighting();
		    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		    GL11.glEnable(GL11.GL_DEPTH_TEST);
		    GL11.glEnable(GL11.GL_LIGHTING);
		    ItemStack var10 = new ItemStack(Item.itemsList[btn.data1]);
		    itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, btn.posx + j + 5, btn.posy + i1 + 5);
		    itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, var10, btn.posx + j, btn.posy + i1);
		    RenderHelper.disableStandardItemLighting();
		    GL11.glDisable(GL11.GL_DEPTH_TEST);
		  	GL11.glDisable(GL11.GL_LIGHTING);
		  	GL11.glDisable(GL12.GL_RESCALE_NORMAL);
	    	
	    	if (btn.id >= 100)
	    		drawString(mc.fontRenderer, "C", j+15, i1+15, 0xffffffff);
	    }
    	
    	for (int i = 0 ; i < menubuttons.size(); i++) {
	    	CJB_Button btn = menubuttons.get(i);
	    	
	    	int j = k+5;
	    	int i1 = l-22;
	    	
	    	if (btn.id == 9998)
	    		j += 220;
	    	
	    	btn.drawScreen(mc, mousex, mousey, j, i1);
	    }
	    
	    selectedrecipe = null;
	    recipestack = null;
	    List<CJB_ButtonItem> recipeitems = new ArrayList<CJB_ButtonItem>();
	    drawResultButtons(mousex,mousey,f);
	    
	    for (int i = 0 ; i < CJB.useditems.length ; i++) {
	    	CJB.useditems[i] = false;
	    }
	    
	    if (selectedrecipe != null && recipestack != null)
	    {
	    	int x = k+11;
	    	int y = l+96;
	    	
	    	CJB_Recipe rec = new CJB_Recipe();
	    	rec.setFakeInventory(player);
	    	for (ItemStack item : recipestack)
	    	{
	    		if (item != null ) {
	    			item.stackSize = 1;
	    			recipeitems.add(new CJB_ButtonItem(-1,0,0,item,rec.consumeItem(item)));
	    			continue;
	    		}
	    		recipeitems.add(new CJB_ButtonItem(-1,0,0,item));
	    	}
	    	
	    	for (int i = 0 ; i < recipeitems.size() ; i++)
	    	{
	    		CJB_ButtonItem btn = recipeitems.get(i);
	    		btn.drawScreen(mc, 0, 0, i % 3 * 18 + x + 5, i / 3 * 18 + y + 5);
	    	}
	    	new CJB_ButtonItem(-1,0,0,selectedrecipe).drawScreen(mc, 0, 0, x+99, y + 23);
	    }
	    
	    GL11.glPushMatrix();
	    GL11.glScalef(0.5f, 0.5f, 1f);
	    GL11.glTranslatef(k+153, l+100+18, 0);
	    
	    for (int i = 0 ; i < inventorybuttons.size(); i++) {
	    	CJB_Button btn = inventorybuttons.get(i);
	    	
	    	int j = 153;
	    	int i1 = 100;
	    	
	    	if (i < 9)
	    		i1 += 76;
	    	
	    	int j1 = (int)(k + j + ((mousex - j - k) / 0.5));
	    	int k1 = (int)(l + i1 - (i < 9 ? 76 : 0 ) + ((mousey - i1 - l + (i < 9 ? 76 : 0 )) / 0.5f));
	    	
	    	btn.used = CJB.useditems[i];
	    	
	    	btn.drawScreen(mc, j1, k1-18, k + j + i % 9 * 18, l + i1 + i / 9 * 18);
	    }
	    GL11.glPopMatrix();
	    
	    for (CJB_ButtonItem btn : inventorybuttons) {
	    	if (btn.isMouseOver)
	    		drawItemName(btn.itemstack, mousex, mousey);
	    }
	    
	    for (CJB_ButtonItem btn : recipebuttons) {
	    	if (btn.isMouseOver)
	    		drawItemName(btn.itemstack, mousex, mousey);
	    }
	    
	    for (CJB_Button btn : categorybuttons) {
	    	if (btn.isMouseOver)
	    		btn.drawToolTip(mc, mousex, mousey);
	    }
	    
	    for (CJB_Button btn : menubuttons) {
	    	if (btn.isMouseOver)
	    		btn.drawToolTip(mc, mousex, mousey);
	    }
    }
    
    public void drawResultButtons(int i, int j, float f)
    {
    	float i2 = (recipebuttons.size() / 12F - 3F);
        int j2 = (int)((double)(currentScroll * (float)i2) + 0.5D);
        
        if(j2 < 0)
        {
            j2 = 0;
        }
        int recipeid = -1;
        for(int k = 0; k < 3; k++)
        {
            for(int l = 0; l < 12; l++)
            {
                int i1 = l + (k + j2) * 12;
                if(i1 >= 0 && i1 < recipebuttons.size())
                {
                	recipebuttons.get(i1).drawScreen(mc, i, j, guiLeft + 11 + l * 18, guiTop + 36 + k * 18);
                	if (recipebuttons.get(i1).isMouseOver)
                		recipeid = recipebuttons.get(i1).id;
                }
            }
        }
        
        if (recipeid > -1)
	    {
	    	for (CJB_Recipe rec : CJB_Recipe.recipes)
	    	{
	    		if (rec.id == recipeid)
	    		{
	    			selectedrecipe = rec.result;
	    			recipestack = rec.items;
	    		}
	    	}
	    }
    }
    
    private void drawScrolling(int i, int j, float f){
    	if (recipebuttons.size() < 36) {
    		this.currentScroll = 0f;
    		return;
    	}
    	
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    int tex = mc.renderEngine.getTexture("/cjb/crafting.png");
	    mc.renderEngine.bindTexture(tex);
	    boolean flag = Mouse.isButtonDown(0);
	    int k5 = guiLeft;
	    int l5 = guiTop;
	    int i5 = k5 + 229;
	    int j4 = l5 + 37;
	    int k4 = i5 + 15;
	    int l4 = j4 + 52;
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
            currentScroll = (j - (j4 + 7)) / ((l4 - j4) - 15F);
            if(currentScroll < 0.0F)
            {
                currentScroll = 0.0F;
            }
            if(currentScroll > 1.0F)
            {
                currentScroll = 1.0F;
            }
        }
        
	    drawTexturedModalRect(i5, j4 + (int)((l4 - j4 - 15) * currentScroll), 241, 166, 15, 15);
    }
    
    protected void keyTyped(char par1, int par2)
    {
        if (par2 == 1 || par2 == mc.gameSettings.keyBindInventory.keyCode)
        {
            mc.thePlayer.closeScreen();
        }
    }
    
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    private void drawItemName(ItemStack stacktext, int i, int j) {
    	try
        {
	        if (stacktext != null && stacktext.getItem() != null)
	        {
	        	GL11.glDisable(2896 /*GL_LIGHTING*/);
	            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
	            List list = stacktext.getTooltip(player, false);
	            int i6 = 0;
	            for (int i4 = 0 ; i4 < list.size() ; i4++)
	            {
	            	RenderItem itemRenderer = new RenderItem();
	            	zLevel = 200F;
	                itemRenderer.zLevel = 100F;
	            	int k1 = i + 12;
	                int i3 = j - 12;
	                
	                String s = "";
	                
	                if (i4 == 0)
	                	s =  stacktext.itemID + " : ";
	                s += (String)list.get(i4);
	                
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

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		
	}
}
