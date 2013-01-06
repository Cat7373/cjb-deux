package net.minecraft.src;

public class CJB_GuiErrors extends CJB_GuiMain
{
    public static boolean renderblocksloaded = false;
    public static boolean blockloaded = false;
    public void initGui()
    {
    	super.initGui();
    	previousScreen = this;
    	rows = 12;
    }
    
    public void mouseClicked(int i, int j, int k)
    {
    	if(k == 0)
        {
    		super.mouseClicked(i, j, k);
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
    
    
    public void actionPerformed(CJB_Button cjbbutton)
    {   	
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
		return "Errors";
	}

	public boolean multiplayer() {
		return true;
	}
	
	public int id() {
		return 1030;
	}
	
	public int textureid() {
		return 9;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiErrors();
	}
}
