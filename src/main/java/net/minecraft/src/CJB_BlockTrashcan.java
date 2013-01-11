package net.minecraft.src;


import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class CJB_BlockTrashcan extends BlockContainer
{
    protected CJB_BlockTrashcan(int i)
    {
        super(i, Material.wood);
        blockIndexInTexture = 3;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public String getTextureFile()
    {
    	return "/cjb/blocks.png";
    }
    
    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
    	if(i == 0)
        {
            return 116;
        }
        if(i == 1)
        {
            return 3;
        }
        return 2;
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2)
    {
    	if (!world.isRemote) {
    		ModLoader.serverOpenWindow((EntityPlayerMP) entityplayer, new ContainerChest(entityplayer.inventory, new InventoryBasic("Trashcan",27)), 39201, i, j, k);
    	}
    	return true;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public int getRenderType()
    {
        return mod_cjb_items.TrashcanModelID;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k)
    {
    	
    	if (world.isAirBlock(i, j+1, k))
    		return true;
    	
    	return false;
    }
    
    public void RenderInInv(RenderBlocks renderblocks, Block block, int i)
    {
        Tessellator tessellator = Tessellator.instance;
        for(int j = 0; j < 5; j++)
        {
            if(j == 1)
            {
            	renderblocks.setRenderBounds(0.18F, 0.0F, 0.15F, 0.82F, 0.8F, 0.18F);
            }
            if(j == 2)
            {
            	renderblocks.setRenderBounds(0.15F, 0.0F, 0.15F, 0.18F, 0.8F, 0.85F);
            } 
            if(j == 3)
            {
            	renderblocks.setRenderBounds(0.18F, 0.0F, 0.82F, 0.82F, 0.8F, 0.85F);
            }
            if(j == 4)
            {
            	renderblocks.setRenderBounds(0.82F, 0.0F, 0.15F, 0.85F, 0.8F, 0.85F);
            }
            if(j == 0)
            {
            	renderblocks.setRenderBounds(0.18F, 0.0F, 0.18F, 0.82F, 0.6F, 0.82F);
            }
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1F);
            renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0.0F, 0.0F);
            renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, i));
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, i));
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
        block.setBlockBounds(0.15F, 0.0F, 0.15F, 0.85F, 0.8F, 0.85F);
    }
    
    public boolean RenderInWorld(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
    {
        renderblocks.setRenderBounds(0.18F, 0.0F, 0.15F, 0.82F, 0.8F, 0.19F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.18F, 0.0F, 0.81F, 0.82F, 0.8F, 0.85F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.15F, 0.0F, 0.15F, 0.19F, 0.8F, 0.85F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.81F, 0.0F, 0.15F, 0.85F, 0.8F, 0.85F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.19F, 0.0F, 0.19F, 0.81F, 0.6F, 0.81F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.15F, 0.0F, 0.15F, 0.85F, 0.8F, 0.85F);
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return null;
	}
}
