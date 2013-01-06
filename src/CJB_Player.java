package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class CJB_Player extends PlayerBase{

	private Minecraft mc;
	private long hitWait;
	
	public CJB_Player(PlayerAPI playerapi) {
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
		CJB.entityplayer = true;
		boolean fly = CJB.flying;
		int keyup = CJB.KeyFlyUp;
		int keydown = CJB.KeyFlyDown;
		
		float steps = player.distanceWalkedModified;
		if (fly)
		{
			mc.gameSettings.keyBindSneak.pressed = false;
			
			d1 = 0d;
			if (!CJB.guiopen)
			{
				if (Keyboard.isKeyDown(keyup)) { d1 += 0.2F; }
				if (Keyboard.isKeyDown(keydown)) { d1 -= 0.2F; }
				
				boolean speedkey = Keyboard.isKeyDown(CJB.KeyFlySpeed);
				
				double mul = (speedkey || CJB.toggleSpeed || CJB.alwaysspeed) ? (1.0F * CJB.flyspeed) : 1D;
				
				float flyVertical = 0F;
				float pitch = Math.abs((0.0050F * (speedkey || CJB.toggleSpeed || CJB.alwaysspeed ? CJB.flyspeed : 1)) * player.rotationPitch);
				
				if (player.moveForward > 0.01F && CJB.mouseControl)
				{
					if (player.rotationPitch > 0)
						flyVertical -= pitch;
					if (player.rotationPitch < 0)
						flyVertical += pitch;
				}
				else if (player.moveForward < -0.01F && CJB.mouseControl)
				{
					if (player.rotationPitch > 0)
						flyVertical += pitch;
					if (player.rotationPitch < 0)
						flyVertical -= pitch;
				}
								
				d *= mul; 
				d1 *= mul;
				d2 *= mul;

				player.fallDistance = 0f;
				player.motionY = 0;
				if (CJB.mouseControl && d1 == 0D) 
					d1 = flyVertical; 
				
				CJB.flew = false;
			}
			
		} else {
			if (!CJB.guiopen)
			{
				boolean speedkey = Keyboard.isKeyDown(CJB.KeyFlySpeed);
				double mul = (speedkey || CJB.toggleSpeed) ? (1.0F * CJB.runspeed) : 1D;
				d *= mul; 
				d2 *= mul;
			}
		}
		super.moveEntity(d, d1, d2);
		
		if (fly)
		{
			player.movementInput.sneak = false;
			player.fallDistance = 0f;
			player.onGround = true;
			player.distanceWalkedModified = steps;
			player.capabilities.isFlying = true;
			player.sendPlayerAbilities();
		}  else if (!CJB.flew && !player.onGround && player.capabilities.isCreativeMode) {
			player.fallDistance = 0f;
			player.onGround = true;
		} else if (!CJB.flew && !player.onGround && !player.capabilities.isCreativeMode) {
			player.fallDistance = 0f;
			player.onGround = true;
			player.sendPlayerAbilities();
		}
		else
		{
			CJB.flew = true;
		}
    }
	
	public static void dropBlockAsItem(World world, int i, int j, int k, ItemStack itemstack)
    {
        if(world.isRemote)
        {
            return;
        } else
        {
            float f = 0.7F;
            double d = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)i + d, (double)j + d1, (double)k + d2, itemstack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
            return;
        }
    }
	
	@Override
	public boolean canBreatheUnderwater()
	{
		if (CJB.infinitebreath)
			return true;
		
		return super.canBreatheUnderwater();
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
		if (CJB.infinitexp) {
			player.experienceLevel = 999;
			player.experience = 1f;
		}
		
		if (CJB.autoJump) {
			player.stepHeight = 1.0f;
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
		if (!CJB.guiopen && CJB.cartcontrol)
		{
			//updateRiding(player, player.ridingEntity);
		}
		if (!mc.isGamePaused && mc.isSingleplayer())
		{
			List list = player.worldObj.getEntitiesWithinAABB(net.minecraft.src.EntityItem.class, AxisAlignedBB.getBoundingBox(player.posX, player.posY, player.posZ, player.posX + 1.0D, player.posY + 1.0D, player.posZ + 1.0D).expand(60D, 60D, 60D));
			if (!list.isEmpty()) 
			{
				for (int i = 0 ; i < list.size(); i++)
				{
					EntityItem item = (EntityItem) list.get(i);
					ItemStack itemstack = item.item;

					if (player.isDead || player.getHealth() <= 0 || !mod_cjb_cheats.canStoreItemStack(itemstack, player) || item.isDead && (CJB.attractiveitems && CJB.pickupdistance > 1))
						continue;
	
					double d = 8D;
					double d1 = (player.posX - item.posX);
		            double d2 = ((player.posY + (double)player.getEyeHeight()) - item.posY);
		            double d3 = (player.posZ - item.posZ);
		            double d6 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
		            
		            EntityItem item1 = null;
		            
		            List elist = new ArrayList();
		            
		            elist.addAll(CJB.w.loadedEntityList);
		            
		            for (Object obj : elist) {
		            	if (item.entityId == ((Entity)obj).entityId) {
		            		item1 = (EntityItem) obj;
		            		break;
		            	}
		            }
		
		            if (item1 == null) continue;
		            
		            if (item1.delayBeforeCanPickup <= 0 && CJB.attractiveitems) {
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
				}
			}
		}
		super.onLivingUpdate();
	}
	
	private void updateRiding(EntityPlayer plr, Entity entity)
	{
		if (entity == null || !(entity instanceof EntityMinecart))
			return;
		
		EntityMinecart ent = (EntityMinecart) entity;
		
		boolean keystop = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
		double px = plr.motionX, pz = plr.motionZ;
		double mx = ent.motionX + px * 2f, mz = ent.motionZ + pz * 2f;
		double speed = Math.sqrt(mx*mx*+mz*mz), rate;
		
		if (speed > 1F)
		{
			rate = 1f / speed;
			mx *= rate; mz *= rate;
		}
		
		if (!CJB.guiopen && keystop){ mx = mz = 0.0D; }
		
		ent.motionX = mx;
		ent.motionZ = mz;
	}
	
	@Override
	public int getBrightnessForRender(float f)
    {
		if (player.posY < 32D && CJB.blackfog)
			return 0xffffff;
		
		return super.getBrightnessForRender(f);
    }
	
	@Override
	public boolean isEntityInsideOpaqueBlock()
    {
        return !player.noClip && !player.sleeping && super.isEntityInsideOpaqueBlock();
    }
	
	@Override
	public MovingObjectPosition rayTrace(double var1, float var3) {
		return player.superRayTrace(mc.playerController.getBlockReachDistance(), var3);
	}
}
