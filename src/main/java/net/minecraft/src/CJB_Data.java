package net.minecraft.src;

import java.util.List;

public class CJB_Data {	
	public String Name = "";
    public double posx = 0;
    public double posy = 0;
    public double posz = 0;
    public int id = 0;
    public int data = 0;
    public int color = 0;
    
    public boolean equals(CJB_Data dat) {
    	
    	if (dat == null)
    		return false;
    	
    	if (!Name.equalsIgnoreCase(dat.Name))
    		return false;
    	
    	if (posx != dat.posx)
    		return false;
    	
    	if (posy != dat.posy)
    		return false;
    	
    	if (posz != dat.posz)
    		return false;
    	
    	if (data != dat.data)
    		return false;
    	
    	if (color != dat.color)
    		return false;
    	
    	return true;
    }
    
    public CJB_Data getDataFromList(List<CJB_Data> list) {
    	
    	for (CJB_Data dat : list) {
    		if (dat.equals(this))
    			return dat;
    	}
    	
    	return null;
    }
}