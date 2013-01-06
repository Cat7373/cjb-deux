package net.minecraft.src;

public class CJB_FoodStats extends FoodStats
{
	EntityPlayer plr;
    public CJB_FoodStats()
    {
        super();
    }

    public void addStats(ItemFood itemfood)
    {
        if (plr != null)
        	plr.heal(itemfood.getHealAmount());
        
        super.addStats(itemfood);
    }

    public void onUpdate(EntityPlayer entityplayer)
    {
    	setFoodLevel(10);
    	plr = entityplayer;
    }

    public boolean needFood()
    {
    	return true;
    }
}
