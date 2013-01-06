// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockFlower, Block, World, IBlockAccess, 
//            Item, EntityItem, ItemStack

public class CJB_BlockStem extends BlockStem
{

    protected CJB_BlockStem(int i, Block block)
    {
        super(i, block);
    }
    
    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	for (int i1 = 0 ; i1 < 10 ; i1++)
    	{
    		super.updateTick(world, i, j, k, random);
    	}
    }
}
