package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CJB_PlayerController extends PlayerControllerMP{

	private Minecraft mc;
	
	public CJB_PlayerController(Minecraft par1Minecraft, NetClientHandler par2NetClientHandler) {
		super(par1Minecraft, par2NetClientHandler);
		mc = par1Minecraft;
	}

	@Override
	public boolean onPlayerRightClick(EntityPlayer entityplayer, World world, ItemStack itemstack, int i, int j, int k, int l, Vec3 par8Vec3)
    {
        float var9 = (float)par8Vec3.xCoord - (float)i;
        float var10 = (float)par8Vec3.yCoord - (float)j;
        float var11 = (float)par8Vec3.zCoord - (float)k;
        boolean var12 = false;
        
        int i1 = world.getBlockId(i, j, k);
        if (CJB.mobspawner) {
			if (i1 == Block.mobSpawner.blockID){
				TileEntityMobSpawner tile = (TileEntityMobSpawner) CJB.w.getBlockTileEntity(i, j, k);
				
				if (tile != null) {
					mc.displayGuiScreen(new CJB_GuiMobSpawner(tile, (TileEntityMobSpawner) mc.theWorld.getBlockTileEntity(i, j, k)));
					return true;
				}
			}
		}

        return super.onPlayerRightClick(entityplayer, world, itemstack, i, j, k, l, par8Vec3);
    }
	
	@Override
	public boolean extendedReach()
    {
        return CJB.extendedreach || super.extendedReach();
    }
	
	@Override
	public float getBlockReachDistance()
    {
		//return super.getBlockReachDistance();
        return CJB.extendedreach ? 200F : super.getBlockReachDistance();
    }
}