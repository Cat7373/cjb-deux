package net.minecraft.src;


import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

public class CJB_AnvilBlock extends BlockContainer
{
	private int id = 0;
	private Random rand = new Random();
    protected CJB_AnvilBlock(int i)
    {
        super(i, Material.iron);
        blockIndexInTexture = 20;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.disableStats();
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int l, float f, float f1, float f2)
    {
    	if (!world.isRemote) {
    		
    		TileEntity tile = world.getBlockTileEntity(i, j, k);
    		
    		if (tile instanceof CJB_AnvilTileEntity) {
	    		CJB_AnvilContainer con = new CJB_AnvilContainer(entityplayer.inventory, (CJB_AnvilTileEntity) tile);
	    		ModLoader.serverOpenWindow((EntityPlayerMP) entityplayer, con, 39205, i, j, k);
    		}
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
        return mod_cjb_items.AnvilModelID;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public TileEntity createNewTileEntity(World par1World)
    {
        return new CJB_AnvilTileEntity();
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return mod_cjb_items.Anvil.blockID;
    }
    
    @Override
    public void breakBlock(World var1, int var2, int var3, int var4, int var5, int var6)
    {
        ModLoader.genericContainerRemoval(var1, var2, var3, var4);
        super.breakBlock(var1, var2, var3, var4, var5, var6);
    }
    
    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving)
    {
        int var6 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        int var7 = par1World.getBlockMetadata(par2, par3, par4) & 4;

        if (var6 == 0)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | var7);
        }

        if (var6 == 1)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | var7);
        }

        if (var6 == 2)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | var7);
        }

        if (var6 == 3)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | var7);
        }
    }
    
    public void RenderInInv(RenderBlocks renderblocks, Block block, int i)
    {
        Tessellator tessellator = Tessellator.instance;
        for(int j = 0; j < 11; j++)
        {
        	int tex = 78;
        	
        	if(j == 10) {
        		tex = Block.stoneSingleSlab.blockIndexInTexture;
            	renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);
        	}
        	
        	if(j == 9) {
        		tex = Block.blockDiamond.blockIndexInTexture;
        		renderblocks.setRenderBounds(0.8F, 0.4F, 0.2F, 0.9F, 0.5F, 0.4F);
        	}
        	if(j == 8) {
        		tex = Block.planks.blockIndexInTexture;
        		renderblocks.setRenderBounds(0.825F, 0.5F, 0.275F, 0.875F, 0.9F, 0.325F);
        	}
            if(j == 7)
            	renderblocks.setRenderBounds(0.2F, 0.8F, 0.10F, 0.8F, 1.0F, 0.85F);
            if(j == 6)
            	renderblocks.setRenderBounds(0.25F, 0.8F, 0.85F, 0.75F, 1.0F, 0.90F);
            if(j == 5)
            	renderblocks.setRenderBounds(0.30F, 0.8F, 0.90F, 0.70F, 1.0F, 0.95F);
            if(j == 4)
            	renderblocks.setRenderBounds(0.35F, 0.9F, 0.95F, 0.65F, 1.0F, 1.00F);
            if(j == 3)
            	renderblocks.setRenderBounds(0.25F, 0.7F, 0.1F, 0.75F, 0.8F, 0.90F);
            if(j == 2)
            	renderblocks.setRenderBounds(0.30F, 0.6F, 0.10F, 0.70F, 0.7F, 0.80F);
            if(j == 1)
            	renderblocks.setRenderBounds(0.35F, 0.5F, 0.15F, 0.65F, 0.6F, 0.70F);
            if(j == 0)
            	renderblocks.setRenderBounds(0.30F, 0.4F, 0.1F, 0.70F, 0.5F, 0.80F);
            
           GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, -1F, 0.0F);
            renderblocks.renderBottomFace(block, 0.0D, 0.0D, 0.0D, tex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 1.0F, 0.0F);
            renderblocks.renderTopFace(block, 0.0D, 0.0D, 0.0D, tex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, -1F);
            renderblocks.renderEastFace(block, 0.0D, 0.0D, 0.0D, tex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(0.0F, 0.0F, 1.0F);
            renderblocks.renderWestFace(block, 0.0D, 0.0D, 0.0D, tex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(-1F, 0.0F, 0.0F);
            renderblocks.renderNorthFace(block, 0.0D, 0.0D, 0.0D, tex);
            tessellator.draw();
            tessellator.startDrawingQuads();
            tessellator.setNormal(1.0F, 0.0F, 0.0F);
            renderblocks.renderSouthFace(block, 0.0D, 0.0D, 0.0D, tex);
            tessellator.draw();
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        }
        
        
        //block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public boolean RenderInWorld(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block)
    {
    	int meta = iblockaccess.getBlockMetadata(i, j, k);
    	if (meta == 0) {
	    	id = 0;
	    	renderblocks.setRenderBounds(0.2F, 0.8F, 0.15F, 0.8F, 1.0F, 0.9F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.25F, 0.8F, 0.10F, 0.75F, 1.0F, 0.15F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.30F, 0.8F, 0.05F, 0.70F, 1.0F, 0.10F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.35F, 0.9F, 0.00F, 0.65F, 1.0F, 0.05F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.25F, 0.7F, 0.1F, 0.75F, 0.8F, 0.90F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.30F, 0.6F, 0.2F, 0.70F, 0.7F, 0.90F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.35F, 0.5F, 0.3F, 0.65F, 0.6F, 0.85F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.30F, 0.4F, 0.2F, 0.70F, 0.5F, 0.90F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 1;        
	        renderblocks.setRenderBounds(0.125F, 0.5F, 0.675F, 0.175F, 0.8F, 0.725F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 2;
	        renderblocks.setRenderBounds(0.1F, 0.4F, 0.6F, 0.2F, 0.5F, 0.8F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 3;
	        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);
	        renderblocks.renderStandardBlock(block, i, j, k);
    	}
    	if (meta == 1) {
	    	id = 0;
	    	renderblocks.setRenderBounds(0.2F, 0.8F, 0.10F, 0.8F, 1.0F, 0.85F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.25F, 0.8F, 0.85F, 0.75F, 1.0F, 0.90F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.30F, 0.8F, 0.90F, 0.70F, 1.0F, 0.95F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.35F, 0.9F, 0.95F, 0.65F, 1.0F, 1.00F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.25F, 0.7F, 0.1F, 0.75F, 0.8F, 0.90F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.30F, 0.6F, 0.10F, 0.70F, 0.7F, 0.80F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.35F, 0.5F, 0.15F, 0.65F, 0.6F, 0.70F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.30F, 0.4F, 0.1F, 0.70F, 0.5F, 0.80F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 1;        
	        renderblocks.setRenderBounds(0.825F, 0.5F, 0.275F, 0.875F, 0.8F, 0.325F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 2;
	        renderblocks.setRenderBounds(0.8F, 0.4F, 0.2F, 0.9F, 0.5F, 0.4F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 3;
	        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);
	        renderblocks.renderStandardBlock(block, i, j, k);
    	}
    	if (meta == 2) {
	    	id = 0;
	    	renderblocks.setRenderBounds(0.10F, 0.8F, 0.20F, 0.85F, 1.0F, 0.80F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.85F, 0.8F, 0.25F, 0.90F, 1.0F, 0.75F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.90F, 0.8F, 0.30F, 0.95F, 1.0F, 0.70F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.95F, 0.9F, 0.35F, 1.00F, 1.0F, 0.65F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.10F, 0.7F, 0.25F, 0.90F, 0.8F, 0.75F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.10F, 0.6F, 0.30F, 0.80F, 0.7F, 0.70F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.15F, 0.5F, 0.35F, 0.70F, 0.6F, 0.65F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.10F, 0.4F, 0.30F, 0.80F, 0.5F, 0.70F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 1;        
	        renderblocks.setRenderBounds(0.275F, 0.5F, 0.125F, 0.325F, 0.8F, 0.175F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 2;
	        renderblocks.setRenderBounds(0.20F, 0.4F, 0.10F, 0.40F, 0.5F, 0.20F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 3;
	        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);
	        renderblocks.renderStandardBlock(block, i, j, k);
    	}
    	if (meta == 3) {
	    	id = 0;
	    	renderblocks.setRenderBounds(0.15F, 0.8F, 0.20F, 0.90F, 1.0F, 0.80F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.10F, 0.8F, 0.25F, 0.15F, 1.0F, 0.75F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.05F, 0.8F, 0.30F, 0.10F, 1.0F, 0.70F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.00F, 0.9F, 0.35F, 0.05F, 1.0F, 0.65F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.10F, 0.7F, 0.25F, 0.90F, 0.8F, 0.75F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.20F, 0.6F, 0.30F, 0.90F, 0.7F, 0.70F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.30F, 0.5F, 0.35F, 0.85F, 0.6F, 0.65F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        renderblocks.setRenderBounds(0.20F, 0.4F, 0.30F, 0.90F, 0.5F, 0.70F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 1;        
	        renderblocks.setRenderBounds(0.675F, 0.5F, 0.825F, 0.725F, 0.8F, 0.875F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 2;
	        renderblocks.setRenderBounds(0.60F, 0.4F, 0.80F, 0.80F, 0.5F, 0.90F);
	        renderblocks.renderStandardBlock(block, i, j, k);
	        
	        id = 3;
	        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.4F, 1.0F);
	        renderblocks.renderStandardBlock(block, i, j, k);
    	}
    	block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return false;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
    	if(id == 1)
    		return Block.planks.blockIndexInTexture;
    	if(id == 2)
    		return Block.blockDiamond.blockIndexInTexture;
    	if(id == 3)
    		return Block.stoneSingleSlab.blockIndexInTexture;
    	
        return 78;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return true;
    }
}
