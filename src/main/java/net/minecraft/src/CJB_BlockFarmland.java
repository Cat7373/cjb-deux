package net.minecraft.src;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class CJB_BlockFarmland extends BlockFarmland
{
    protected CJB_BlockFarmland(int i)
    {
        super(i);
    }
    
    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if (isWaterNearby() || world.canLightningStrikeAt(i, j + 1, k))
        {
            world.setBlockMetadataWithNotify(i, j, k, 7);
        }
        else
        {
            int l = world.getBlockMetadata(i, j, k);
            if (l > 0)
            {
                world.setBlockMetadataWithNotify(i, j, k, l - 1);
            }
            else if (!isCropsNearby())
            {
                world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
            }
        }
    }

    @Override
    public void onFallenUpon(World world, int i, int j, int k, Entity entity, float f)
    {
        
    }

    private boolean isCropsNearby()
    {
        return true;
    }

    private boolean isWaterNearby()
    {
        return true;
    }
}