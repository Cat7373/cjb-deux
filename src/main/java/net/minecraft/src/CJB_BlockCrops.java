// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

import net.minecraft.block.BlockCrops;
import net.minecraft.world.World;

// Referenced classes of package net.minecraft.src:
//            BlockFlower, Block, World, EntityItem, 
//            ItemStack, Item

public class CJB_BlockCrops extends BlockCrops
{

    protected CJB_BlockCrops(int i, int j)
    {
        super(i, j);
        this.disableStats();
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	for (int i1 = 0 ; i1 < 10 ; i1++) {
    		super.updateTick(world, i, j, k, random);
    	}
    }
}
