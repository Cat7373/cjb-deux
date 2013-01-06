package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

public class CJB_GuiXray extends CJB_GuiMain
{
    private List<Integer> invisible;

    public void initGui()
    {   	
    	super.initGui();
    	colums = 8;
    	rows = 7;
    	previousScreen = this;
        invisible = new ArrayList<Integer>();
        
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
    	
    	for(int i = 0; i < CJB.blacklist.length; i++)
        {
            invisible.add(CJB.blacklist[i]);
        }
        
        for(int i = 0; i < Block.blocksList.length; i++)
        {
            if(Block.blocksList[i] != null)
            {
            	CJB_Button button = new CJB_ButtonItem(i, k + 14, l + 19, new ItemStack(Block.blocksList[i]),!invisible.contains(i));
            	//button.used = invisible.contains(i);
            	buttonslist.add(button);
            }
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
    	if(k == 0)
        {
            for(int l = 0; l < buttonslist.size(); l++)
            {
            	CJB_Button cjbbutton = (CJB_Button)buttonslist.get(l);
                if(cjbbutton.MouseClick() && cjbbutton.id != -1)
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    
                    if(!invisible.remove(new Integer(cjbbutton.id)))
                        invisible.add(new Integer(cjbbutton.id));
                    
                    updateBlockList();
                    mc.displayGuiScreen(this);
                }
            }
            super.mouseClicked(i, j, k);
        }   
    }
    
    private void updateBlockList() {
    	CJB.blacklist = new int[invisible.size()];
        for(int i = 0; i < invisible.size(); i++)
        {
        	CJB.blacklist[i] = ((Integer)invisible.get(i)).intValue();
        }

        mod_cjb_x_ray.save();
    }

    public void drawBackground(int i, int j, float f)
    {
	    super.drawBackground(i, j, f);

    	int i2 = (buttonslist.size() / colums - rows) + 1;
        int j2 = (int)((double)(currentScroll * (float)i2) + 0.5D);
        if(j2 < 0)
        {
            j2 = 0;
        }
        
        ItemStack stacktext = null;
        int cross = 0;
        
        for(int k = 0; k < rows; k++)
        {
            for(int l = 0; l < colums; l++)
            {
                int i1 = l + (k + j2) * colums;
                if(i1 >= 0 && i1 < buttonslist.size())
                {
        	        buttonslist.get(i1).drawScreen(mc, i, j, l * 18, k * 18);
        	        //blockselections.get(i1).drawScreen(mc, i, j, l * 18, k * 18);
        	        
        	        if (buttonslist.get(i1).isMouseOver)
        	        {
        	        	stacktext = buttonslist.get(i1).itemstack;
        	        	cross = i1;
        	        }
                }
            }
            
        }
        
        i2 = 0;
        if (stacktext != null && stacktext.getItem() != null)
        {
        	GL11.glDisable(2896 /*GL_LIGHTING*/);
            GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
            List list = stacktext.getTooltip(mc.thePlayer, false);

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
                	s = stacktext.itemID + " | " + s + (buttonslist.get(cross).selected ? " | X" : "");
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
        
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }

	public String name() {
		return "X-Ray";
	}

	public boolean multiplayer() {
		return true;
	}
	
	public int id() {
		return 1024;
	}
	
	public int textureid() {
		return 3;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiXray();
	}
}
