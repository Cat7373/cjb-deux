package net.minecraft.src;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class CJB_MPData extends WorldSavedData
{   
    public String id;
    public int health;
    public int data1;
    public byte data2;
    
    public CJB_MPData(String s)
    {
        super(s);
        id = "Pig";
        data1 = 0;
        data2 = 0;
        health = 10;
    }
    

    public CJB_MPData(String s, String s1, int j, byte k, int l)
    {
        super(s);
        id = s1;
        data1 = j;
        data2 = k;
        health = l;
    }
    
    public void readFromNBT(NBTTagCompound nbttagcompound)
    {
        id = nbttagcompound.getString("id");
        data1 = nbttagcompound.getShort("data1");
        data2 = nbttagcompound.getByte("data2");
        health = nbttagcompound.getShort("health");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setString("id", id);
        nbttagcompound.setShort("data1", (short)data1);
        nbttagcompound.setByte("data2", data2);
        nbttagcompound.setShort("health", (short)health);
    }
}
