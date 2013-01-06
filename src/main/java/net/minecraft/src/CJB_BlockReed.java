// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

import net.minecraft.block.BlockReed;
import net.minecraft.world.World;

// Referenced classes of package net.minecraft.src:
//            Block, Material, World, BlockGrass, 
//            Item, AxisAlignedBB

public class CJB_BlockReed extends BlockReed
{

    protected CJB_BlockReed(int i, int j)
    {
        super(i, j);
        this.disableStats();
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	for (int i3 = 0 ; i3 < 10 ; i3++)
    	{
            super.updateTick(world, i, j, k, random);
        }
    }
}
