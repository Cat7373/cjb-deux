// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.List;
import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, World, EnumMobType, AxisAlignedBB, 
//            EntityLiving, EntityPlayer, IBlockAccess, Material, 
//            Entity

public class CJB_BlockPressurePlate extends Block
{

    protected CJB_BlockPressurePlate(int i, int j, String type, Material material, boolean reversed)
    {
        super(i, j, material);
        triggerMobType = type;
        setTickRandomly(true);
        float f = 0.0625F;
        setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
        isReversed = reversed;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public int tickRate()
    {
        return 20;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
        return world.isBlockNormalCube(i, j - 1, k);
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
    	world.notifyBlocksOfNeighborChange(i, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        boolean flag = false;
        if(!world.isBlockNormalCube(i, j - 1, k))
        {
            flag = true;
        }
        if(flag)
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.isRemote)
        {
            return;
        }
        if(world.getBlockMetadata(i, j, k) == 0)
        {
            return;
        } else
        {
            setStateIfMobInteractsWithPlate(world, i, j, k);
            return;
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
        if(world.isRemote)
        {
            return;
        }
        if(world.getBlockMetadata(i, j, k) == 1)
        {
            return;
        } else
        {
            setStateIfMobInteractsWithPlate(world, i, j, k);
            return;
        }
    }

    private void setStateIfMobInteractsWithPlate(World world, int i, int j, int k)
    {
        boolean flag = world.getBlockMetadata(i, j, k) == 1;
        boolean flag1 = false;
        float f = 0.125F;
        List list = null;
        if(triggerMobType.equalsIgnoreCase("everything"))
        {
            list = world.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f, (float)(i + 1) - f, (double)j + 0.25D, (float)(k + 1) - f));
        }
        if(triggerMobType.equalsIgnoreCase("living"))
        {
            list = world.getEntitiesWithinAABB(net.minecraft.src.EntityLiving.class, AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f, (float)(i + 1) - f, (double)j + 0.25D, (float)(k + 1) - f));
        }
        if(triggerMobType.equalsIgnoreCase("creatures"))
        {
            list = world.getEntitiesWithinAABB(net.minecraft.src.EntityCreature.class, AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f, (float)(i + 1) - f, (double)j + 0.25D, (float)(k + 1) - f));
        }
        if(triggerMobType.equalsIgnoreCase("players"))
        {
            list = world.getEntitiesWithinAABB(net.minecraft.src.EntityPlayer.class, AxisAlignedBB.getBoundingBox((float)i + f, j, (float)k + f, (float)(i + 1) - f, (double)j + 0.25D, (float)(k + 1) - f));
        }
        if(list.size() > 0)
        {
            flag1 = true;
        }
        if(flag1 && !flag)
        {
            world.setBlockMetadataWithNotify(i, j, k, 1);
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.markBlockRangeForRenderUpdate(i, j, k, i, j, k);
            world.playSoundEffect((double)i + 0.5D, (double)j + 0.10000000000000001D, (double)k + 0.5D, "random.click", 0.3F, 0.6F);
        }
        if(!flag1 && flag)
        {
            world.setBlockMetadataWithNotify(i, j, k, 0);
            world.notifyBlocksOfNeighborChange(i, j, k, blockID);
            world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
            world.markBlockRangeForRenderUpdate(i, j, k, i, j, k);
            world.playSoundEffect((double)i + 0.5D, (double)j + 0.10000000000000001D, (double)k + 0.5D, "random.click", 0.3F, 0.5F);
        }
        if(flag1)
        {
            world.scheduleBlockUpdate(i, j, k, blockID, tickRate());
        }
    }

    @Override
    public void breakBlock(World world, int i, int j, int k, int l, int i1)
    {
        world.setBlockMetadataWithNotify(i, j, k, 0);
        world.notifyBlocksOfNeighborChange(i, j, k, blockID);
        world.notifyBlocksOfNeighborChange(i, j - 1, k, blockID);
        super.breakBlock(world, i, j, k, l, i1);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
    {
        boolean flag = iblockaccess.getBlockMetadata(i, j, k) == 1;
        float f = 0.0625F;
        if(flag)
        {
            setBlockBounds(f, 0.0F, f, 1.0F - f, 0.03125F, 1.0F - f);
        } else
        {
            setBlockBounds(f, 0.0F, f, 1.0F - f, 0.0625F, 1.0F - f);
        }
    }

    @Override
    public boolean isProvidingWeakPower(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        return iblockaccess.getBlockMetadata(i, j, k) == (isReversed ? 0 : 1);
    }

    @Override
    public boolean isProvidingStrongPower(IBlockAccess par1IBlockAccess, int i, int j, int k, int l)
    {
        if(par1IBlockAccess.getBlockMetadata(i, j, k) == (isReversed ? 1 : 0))
        {
            return false;
        } else
        {
            return l == 1;
        }
    }

    @Override
    public boolean canProvidePower()
    {
        return true;
    }

    @Override
    public void setBlockBoundsForItemRender()
    {
        float f = 0.5F;
        float f1 = 0.125F;
        float f2 = 0.5F;
        setBlockBounds(0.5F - f, 0.5F - f1, 0.5F - f2, 0.5F + f, 0.5F + f1, 0.5F + f2);
    }

    @Override
    public int getMobilityFlag()
    {
        return 1;
    }

    private String triggerMobType;
    private boolean isReversed;
}
