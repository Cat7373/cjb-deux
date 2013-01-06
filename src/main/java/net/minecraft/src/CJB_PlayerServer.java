package net.minecraft.src;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumGameType;

import org.lwjgl.input.Keyboard;

public class CJB_PlayerServer extends ServerPlayerBase{

	private Minecraft mc;
	private long hitWait;
	
	public CJB_PlayerServer(ServerPlayerAPI playerapi) {
		super(playerapi);
		mc = ModLoader.getMinecraftInstance();
	}
	
	public void moveEntity(double d, double d1, double d2)
    {
		if (!CJB.modcheats)
		{
			super.moveEntity(d, d1, d2);
			return;
		}
		boolean fly = CJB.flying;
		
		float steps = player.distanceWalkedModified;
		if (fly)
		{
			player.fallDistance = 0f;
			player.motionY = 0;
		}
		super.moveEntity(d, d1, d2);
    }
	
	@Override
	public boolean canHarvestBlock(Block block) {
		if (block == null)
			return false;
		
		if (CJB.nopickaxe){
			return true;
		}
		return super.canHarvestBlock(block);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
		if (CJB.nofalldamage && damagesource.damageType == DamageSource.fall.damageType)
			return false;
		
        if (CJB.infinitehealth)
            return false;
 
        return super.attackEntityFrom(damagesource, i);
    }
	
	@Override
	public void attackTargetEntityWithCurrentItem(Entity ent)
    {
		boolean flag = (ent instanceof EntityTameable && ((EntityTameable)ent).isTamed());
		
		if (CJB.onehitkill && !flag)
			ent.attackEntityFrom(DamageSource.causePlayerDamage(player), 9999);
		
		super.attackTargetEntityWithCurrentItem(ent);
    }
	
	@Override
	public void onLivingUpdate(){
		
		if (mc.isSingleplayer() && player != null && player.worldObj != null) {
			
			if (!(player.theItemInWorldManager instanceof CJB_ItemInWorldManager)) {
				EnumGameType type = player.theItemInWorldManager.getGameType();
				player.theItemInWorldManager = new CJB_ItemInWorldManager(player, player.worldObj);
				player.theItemInWorldManager.thisPlayerMP = player;
				player.theItemInWorldManager.setGameType(type);
			}
		}
		
		if (CJB.infinitexp) {
			player.experienceLevel = 999;
			player.experience = 1f;
		}
		
		if (CJB.infinitehealth)
            player.setHealthField(20);
		
		if (CJB.autoJump) {
			CJB.plr.stepHeight = 1.0f;
		} else
			player.stepHeight = 0.5f;
		
		for (ItemStack itemstack : player.inventory.mainInventory)
		{
			if (itemstack == null)
				continue;
			
			if (CJB.unbreakableitems && !(itemstack.getItem() instanceof ItemArmor) && itemstack.isItemStackDamageable()){
					itemstack.setItemDamage(0);
			}

			if (CJB.infinitearrows && itemstack.itemID == Item.arrow.shiftedIndex){
				itemstack.stackSize = 9;
			}
			
			if (CJB.unbreakablearmor && itemstack.getItem() instanceof ItemArmor){
				itemstack.setItemDamage(0);
			}
		}
		
		for (ItemStack itemstack : player.inventory.armorInventory)
		{
			if (itemstack == null)
				continue;
			
			if (CJB.unbreakablearmor && itemstack.getItem() instanceof ItemArmor){
				itemstack.setItemDamage(0);
			}
		}		
		
		for (int i = 0 ; i < player.worldObj.loadedEntityList.size() ; i++)
		{
			Entity ent = (Entity)player.worldObj.loadedEntityList.get(i);
			
			if (CJB.pickupallarrows && ent instanceof EntityArrow && ((EntityArrow)ent).arrowShake == 0)
			{
				((EntityArrow)ent).canBePickedUp = 1;
			}
			
			if (CJB.onehitkill && ent instanceof EntityArrow && ((EntityArrow)ent).shootingEntity instanceof EntityPlayer)
			{
				((EntityArrow)ent).setDamage(99999D);
			}              
		}
		
		if (CJB.instantfurnace) {
			for (int i = 0 ; i < player.worldObj.loadedTileEntityList.size() ; i++)
			{
				TileEntity tileentity = (TileEntity)player.worldObj.loadedTileEntityList.get(i);
				if (tileentity != null && tileentity instanceof TileEntityFurnace){
					TileEntityFurnace tile = (TileEntityFurnace) tileentity;
					tile.furnaceBurnTime = 300;
					
					if(tile.furnaceCookTime < 199)
						tile.furnaceCookTime = 199;
				}
			}
		}
		
		if (!mc.isGamePaused && mc.isSingleplayer())
		{
			List list = player.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(player.posX, player.posY, player.posZ, player.posX + 1.0D, player.posY + 1.0D, player.posZ + 1.0D).expand(60D, 60D, 60D));
			if (!list.isEmpty()) 
			{
				for (int i = 0 ; i < list.size(); i++)
				{
					EntityItem item = (EntityItem) list.get(i);
					// getItemStack() ??
					ItemStack itemstack = item.func_92014_d();

					if (player.isDead || player.getHealth() <= 0 || !mod_cjb_cheats.canStoreItemStack(itemstack, player) || item.isDead && (CJB.attractiveitems && CJB.pickupdistance > 1))
						continue;
	
					double d = 8D;
					double d1 = (player.posX - item.posX);
		            double d2 = ((player.posY + (double)player.getEyeHeight()) - item.posY);
		            double d3 = (player.posZ - item.posZ);
		            double d6 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
		            
		            if (item.delayBeforeCanPickup <= 0 && CJB.attractiveitems) {
			            d1 /= d;
			            d2 /= d;
			            d3 /= d;
			            
			            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
			            double d5 = 1.2D - d4;
			            if(d5 > 0.0D)
			            {
			                d5 *= d5;
			                item.motionX += (d1 / d4) * d5 * 0.10000000000000001D;
			                item.motionY += (d2 / d4) * d5 * 0.10000000000000001D;
			                item.motionZ += (d3 / d4) * d5 * 0.10000000000000001D;
			            }
		            }
		            if (!Keyboard.isKeyDown(mc.gameSettings.keyBindSneak.keyCode) && CJB.pickupdistance > 1 && d6 < CJB.pickupdistance)
		            {
		            	int j = itemstack.stackSize;
		            	
			            if (item != null && !item.isDead && j > 0 && player.inventory.addItemStackToInventory(itemstack))
			            {
			                if (itemstack.itemID == Block.wood.blockID)
			                {
			                	player.triggerAchievement(AchievementList.mineWood);
			                }
			                if (itemstack.itemID == Item.leather.shiftedIndex)
			                {
			                	player.triggerAchievement(AchievementList.killCow);
			                }
			                if (itemstack.itemID == Item.diamond.shiftedIndex)
			                {
			                	player.triggerAchievement(AchievementList.diamonds);
			                }
			                if (itemstack.itemID == Item.blazeRod.shiftedIndex)
			                {
			                	player.triggerAchievement(AchievementList.blazeRod);
			                }
			                ModLoader.onItemPickup(player, itemstack);
			                player.worldObj.playSoundAtEntity(item, "random.pop", 0.2F, ((player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
			                player.onItemPickup(item, j);
			                if (itemstack.stackSize <= 0)
			                {
			                    item.setDead();
			                }
			            }
		            }
				}
			}
		}
		super.onLivingUpdate();
	}

	
	@Override
	public boolean isEntityInsideOpaqueBlock()
    {
        return !player.noClip && !player.isPlayerSleeping() && super.isEntityInsideOpaqueBlock();
    }
}
