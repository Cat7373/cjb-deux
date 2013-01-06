package net.minecraft.src;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

public class CJB_GuiMobSpawner extends CJB_GuiMain
{
	private static List mobs = new ArrayList();
	public static String mob = "Pig";
	public TileEntityMobSpawner tile;
	public TileEntityMobSpawner tilelocal;
	
	public CJB_GuiMobSpawner()
	{
	}
	
	public CJB_GuiMobSpawner(TileEntityMobSpawner tileentity, TileEntityMobSpawner tileentity1)
	{
		tile = tileentity;
		tilelocal = tileentity1;
	}
	
    public void initGui()
    {
    	super.initGui();
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
        
        if (mobs.size() == 0)
        {
        	Field idMapField = EntityList.class.getDeclaredFields()[1];
        	idMapField.setAccessible(true);
        	Map idMap = null;
        	try
        	{
        		idMap = (Map)idMapField.get(null);
        	} catch (Exception e)
        	{
        		e.printStackTrace();
        	}
        	if (idMap != null)
        	{
        		for (Iterator keyIter = idMap.keySet().iterator(); keyIter.hasNext();)
        		{
        			Class keyClass = (Class)keyIter.next();
        			try
        			{
        				if (EntityLiving.class.isAssignableFrom(keyClass) && keyClass.getConstructor(new Class[]{ World.class }) != null && !Modifier.isAbstract(keyClass.getModifiers()))
        				{
        					String entityName = (String)idMap.get(keyClass);
        					mobs.add(entityName);
        				}
        			}
        			catch(SecurityException e)
        			{
        				e.printStackTrace();
        			}
        			catch(NoSuchMethodException ee){}
        		}
        	}
        	Collections.sort(mobs);
        } 
        
        if (mobs.size() > 0) {
        	
        	for (int i = 0 ; i < mobs.size(); i++)
        	{
        		buttonslist.add(new CJB_ButtonLabel(-1, (String)mobs.get(i), k + 8, l + 22, true));
        	}
        }
    }

    public void actionPerformed(CJB_Button cjbbutton)
    {   	
       	if ( cjbbutton.id == 1) return;
       	
       	if (tile == null) {
	       	mob = cjbbutton.text;
	       	mc.displayGuiScreen(this);
       	} else {
       		tile.setMobID(cjbbutton.text);
       		tile.delay = 50;
       		tilelocal.setMobID(cjbbutton.text);
       		tilelocal.delay = 50;
       		mc.displayGuiScreen(null);
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
                	if (buttonslist.get(i1).text.equalsIgnoreCase(mob))
        	    		buttonslist.get(i1).selected = true;
        	    	else
        	    		buttonslist.get(i1).selected = false;
                	
        	        buttonslist.get(i1).drawScreen(mc, i, j, 0, k * 10);
        	        //buttonslist.get(p).drawScreen(mc, i, j, 0, i1++ * 10);
                }
            }
            
        }
    }

	public String name() {
		return "Mob Spawner";
	}

	public boolean multiplayer() {
		return false;
	}
	
	public int id() {
		return 0;
	}
	
	public int textureid() {
		return -1;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiMobSpawner();
	}
}
