package net.minecraft.src;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class CJB_BlockMesh extends Block
{

    protected CJB_BlockMesh(int i, Material material)
    {
        super(i, material);
        blockIndexInTexture = Block.blockSteel.blockIndexInTexture;
        float f = 0.1875F;
        setBlockBounds(0.0F, 0.8F - f / 2.0F, 0.0F, 1.0F, 0.8F + f / 2.0F, 1.0F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.disableStats();
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
    public int getRenderType()
    {
        return mod_cjb_items.MeshModelID;
    }
    
    /**
     * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
     */
    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
    {
    	if (entity instanceof EntityItem) {
    		entity.setPosition(entity.posX, entity.posY - 0.005d, entity.posZ);
    	}
    }
    
    @Override
    public void addCollidingBlockToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity){
    	setBlockBounds(0.0F, 0.8F, 0.0F, 1F, 0.9F, 1F);
        super.addCollidingBlockToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
    	setBlockBounds(0.0F, 0.5F, 0.0F, 1F, 1.0F, 1F);
        return super.getSelectedBoundingBoxFromPool(world, i, j, k);
    }
    
    public void RenderInInv(RenderBlocks renderblocks, Block block, int i)
    {
        Tessellator tessellator = Tessellator.instance;
        for(int j = 0; j < 4; j++)
        {
            if(j == 0)
            {
            	renderblocks.setRenderBounds(0.0F, 0.8F, 0.2F, 1F, 0.9F, 0.3F);
            }
            if(j == 1)
            {
            	renderblocks.setRenderBounds(0.0F, 0.8F, 0.7F, 1F, 0.9F, 0.8F);
            } 
            if(j == 2)
            {
            	renderblocks.setRenderBounds(0.2F, 0.8F, 0.0F, 0.3F, 0.9F, 1F);
            }
            if(j == 3)
            {
            	renderblocks.setRenderBounds(0.7F, 0.8F, 0.0F, 0.8F, 0.9F, 1F);
            }
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            GL11.glScalef(1f, 1f, 1f);
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
    }
    
    public boolean RenderInWorld(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
    {
       
    	renderblocks.setRenderBounds(0.0F, 0.8F, 0.2F, 1F, 0.9F, 0.3F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.0F, 0.8F, 0.7F, 1F, 0.9F, 0.8F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.2F, 0.8F, 0.0F, 0.3F, 0.9F, 1F);
        renderblocks.renderStandardBlock(block, i, j, k);
        
        renderblocks.setRenderBounds(0.7F, 0.8F, 0.0F, 0.8F, 0.9F, 1F);
        renderblocks.renderStandardBlock(block, i, j, k);
        return false;
    }
}
