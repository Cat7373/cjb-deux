package net.minecraft.src;

import java.util.Random;

import net.minecraft.block.BlockMushroom;
import net.minecraft.world.World;

public class CJB_BlockMushroom extends BlockMushroom
{
    protected CJB_BlockMushroom(int i, int j)
    {
        super(i, j);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
    	for (int i3 = 0 ; i3 < 10 ; i3++)
    	{
            super.updateTick(world, i, j, k, random);
        }
    }
}
