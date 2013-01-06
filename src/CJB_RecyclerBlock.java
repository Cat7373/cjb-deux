// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, Block, World, 
//            IBlockAccess, TileEntityDispenser, EntityPlayer, ItemStack, 
//            ModLoader, Item, EntityArrow, EntityEgg, 
//            EntitySnowball, EntityItem, Entity, EntityLiving, 
//            MathHelper, TileEntity

public class CJB_RecyclerBlock extends BlockContainer
{

    protected CJB_RecyclerBlock(int i)
    {
        super(i, Material.rock);
        blockIndexInTexture = 0;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public String getTextureFile()
    {
    	return "/cjb/blocks.png";
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        setDispenserDefaultDirection(world, i, j, k);
    }

    private void setDispenserDefaultDirection(World world, int i, int j, int k)
    {
        int l = world.getBlockId(i, j, k - 1);
        int i1 = world.getBlockId(i, j, k + 1);
        int j1 = world.getBlockId(i - 1, j, k);
        int k1 = world.getBlockId(i + 1, j, k);
        byte byte0 = 3;
        if(Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
        {
            byte0 = 3;
        }
        if(Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
        {
            byte0 = 2;
        }
        if(Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
        {
            byte0 = 5;
        }
        if(Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
        {
            byte0 = 4;
        }
        world.setBlockMetadataWithNotify(i, j, k, byte0);
    }

    @Override
    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        int i1 = iblockaccess.getBlockMetadata(i, j, k);
        if(l != i1)
        {
            return 0;
        } else
        {
            return 1;
        }
    }

    @Override
    public int getBlockTextureFromSide(int i)
    {
        if(i == 3)
        {
            return 1;
        } else
        {
            return 0;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2)
    {
    	if (!world.isRemote) {
    		CJB_RecyclerTileEntity tile = (CJB_RecyclerTileEntity) world.getBlockTileEntity(i, j, k);
    		if (tile != null) {
    			ModLoader.serverOpenWindow((EntityPlayerMP) entityplayer, new CJB_RecyclerContainer(entityplayer.inventory, tile), 39202, i, j, k);
    		}
    	}
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
    {
        int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if(l == 0)
        {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        }
        if(l == 1)
        {
            world.setBlockMetadataWithNotify(i, j, k, 5);
        }
        if(l == 2)
        {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        }
        if(l == 3)
        {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        }
    }
    
    @Override
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        ModLoader.genericContainerRemoval(var1, var2, var3, var4);
        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new CJB_RecyclerTileEntity();
	}
}
