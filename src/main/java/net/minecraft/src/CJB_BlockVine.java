package net.minecraft.src;

import java.util.Random;

import net.minecraft.block.BlockVine;
import net.minecraft.world.World;

public class CJB_BlockVine extends BlockVine
{
    public CJB_BlockVine(int i)
    {
        super(i);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	for (int i1 = 0 ; i1 < 250 ; i1++)
    	{
    		super.updateTick(world, i, j, k, random);
    	}
    }
}
