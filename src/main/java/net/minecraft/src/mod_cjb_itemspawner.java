package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class mod_cjb_itemspawner extends BaseMod {
	
	public static String itemcommand = "/give";
	private boolean keypressed = false;
	
	public void load()
	{
		ModLoader.setInGameHook(this, true, false);
		CJB.moditemspawner = true;
		itemcommand = CJB_Settings.getString("itemspawner.command", "/give #u #i #a #d");
	}

	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		if (mc.currentScreen == null)
		{
			if (Keyboard.getEventKeyState())
			{
				if (Keyboard.isKeyDown(CJB.KeyItemSpawnerMenu) && !CJB.disablehotkeys && !CJB.keypressed)
					mc.displayGuiScreen(new CJB_GuiItemSpawner());
				
				if (Keyboard.isKeyDown(CJB.KeyItemStack) && !keypressed)
				{
					keypressed = true;
					dropStack(mc);
				}
			} 
			else
			{
				keypressed = false;
			}
		}
		
		if (mc.thePlayer == null && !(mc.currentScreen instanceof CJB_GuiItemSpawner))
			return true;
		
		for (ItemStack itemstack : mc.thePlayer.inventory.mainInventory) {
			if (itemstack != null && itemstack.animationsToGo > 0)
				itemstack.animationsToGo--;
		}
		
		return true;
	}
	
	private void dropStack(Minecraft mc)
	{
		EntityPlayer plr = CJB.plr;
		World worldObj = CJB.w;
		Random rand = new Random();
		
		if (plr.getCurrentEquippedItem() == null)
			return;
			
		ItemStack itemstack = new ItemStack(plr.getCurrentEquippedItem().getItem(), plr.getCurrentEquippedItem().getMaxStackSize(), plr.getCurrentEquippedItem().getItemDamageForDisplay());
	
        EntityItem entityitem = new EntityItem(worldObj, plr.posX, (plr.posY - 0.30000001192092896D) + (double)plr.getEyeHeight(), plr.posZ, itemstack);
        entityitem.delayBeforeCanPickup = 10;
        
        float f1 = 0.3F;
        entityitem.motionX = -MathHelper.sin((plr.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((plr.rotationPitch / 180F) * 3.141593F) * f1;
        entityitem.motionZ = MathHelper.cos((plr.rotationYaw / 180F) * 3.141593F) * MathHelper.cos((plr.rotationPitch / 180F) * 3.141593F) * f1;
        entityitem.motionY = -MathHelper.sin((plr.rotationPitch / 180F) * 3.141593F) * f1 + 0.1F;
        f1 = 0.02F;
        float f3 = rand.nextFloat() * 3.141593F * 2.0F;
        f1 *= rand.nextFloat();
        entityitem.motionX += Math.cos(f3) * (double)f1;
        entityitem.motionY += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
        entityitem.motionZ += Math.sin(f3) * (double)f1;
        
        plr.worldObj.spawnEntityInWorld(entityitem);
	}
	
	public String getVersion() {
		return CJB.VERSION;
	}
	
	@Override
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}