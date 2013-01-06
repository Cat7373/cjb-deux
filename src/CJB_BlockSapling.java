// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockFlower, World, WorldGenTaiga2, WorldGenForest, 
//            WorldGenTrees, WorldGenBigTree, WorldGenerator

public class CJB_BlockSapling extends BlockSapling
{

    protected CJB_BlockSapling(int i, int j)
    {
        super(i, j);
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
    	if (par1World.isRemote)
        {
            return;
        }

        super.updateTick(par1World, par2, par3, par4, par5Random);

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
        	growTree(par1World, par2, par3, par4, par5Random);
        }
    }
}
