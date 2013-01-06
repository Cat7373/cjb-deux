package net.minecraft.src;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;

public class mod_cjb_measure extends BaseMod {
	
	public static boolean mousepressed;
	
	public void load()
	{
		CJB.modmeasures = true;
		ModLoader.setInGameHook(this, true, false);
		CJB.mesitem = CJB_Settings.getInteger("measures.item", Item.stick.shiftedIndex);
	}
	
	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		if (mc.currentScreen == null) {
			
			if (mc.gameSettings.keyBindUseItem.pressed) {
				if (!mousepressed) {
					handleMouseInput(mc, mc.objectMouseOver);
					mousepressed = true;
				}
			} else 
				mousepressed = false;
		}
		return true;
	}
	
	
	private void handleMouseInput(Minecraft mc, MovingObjectPosition mov) {
		
		int id = 0;
		
		if (mc.thePlayer.inventory.getCurrentItem() != null)
			id = mc.thePlayer.inventory.getCurrentItem().itemID;
		
		if (id != CJB.mesitem) return;
		
		if (Keyboard.isKeyDown(CJB.KeyMeasurePointsUndo)) {
			if (!CJB.measures.isEmpty())
				CJB.measures.pop();
		} else
		if (Keyboard.isKeyDown(CJB.KeyMeasurePointsClear)) {
				CJB.measures.clear();
		} else {
			if(mov != null && mov.typeOfHit == EnumMovingObjectType.TILE) {
				CJB_Data p = new CJB_Data();
				p.posx = mov.blockX;
				p.posy = mov.blockY;
				p.posz = mov.blockZ;
				
				CJB.measures.add(p);
			}
		}
	}
	
	public String getVersion() {
		return CJB.VERSION;
	}
	
	@Override
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}