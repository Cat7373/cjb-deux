package net.minecraft.src;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockVine;
import net.minecraft.block.StepSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumGameType;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

public class mod_cjb_cheats extends BaseMod {
	
	private boolean keystop;
	Minecraft m;
	
	private static boolean flypressed;
	public static boolean cheatsloaded;
	
	public void load()
	{
		ModLoader.setInGameHook(this, true, false);
		CJB.modcheats = true;
		
		PlayerAPI.register("CJB Mods", CJB_Player.class);
		ServerPlayerAPI.register("CJB Mods", CJB_PlayerServer.class);
	}
	
	
	public static void loadCheats()
	{
		cheatsloaded = true;
		CJB.infinitehealth = CJB_Settings.getBooleanW("cheat.infinitehealth", false);
		CJB.infinitebreath = CJB_Settings.getBooleanW("cheat.infinitebreath", false);
		CJB.unbreakableitems = CJB_Settings.getBooleanW("cheat.unbreakableitems", false);
		CJB.onehitblock = CJB_Settings.getBooleanW("cheat.onehitblock", false);
		CJB.onehitkill = CJB_Settings.getBooleanW("cheat.onehitkill", false);
		CJB.walkoversoil = CJB_Settings.getBooleanW("cheat.walkoversoil", false);
		CJB.instantfurnace = CJB_Settings.getBooleanW("cheat.instantfurnace", false);
		CJB.infinitearrows = CJB_Settings.getBooleanW("cheat.infinitearrows", false);
		CJB.unbreakablearmor = CJB_Settings.getBooleanW("cheat.unbreakablearmor", false);
		CJB.alwaysday = CJB_Settings.getBooleanW("cheat.alwaysday", false);
		CJB.alwaysnight = CJB_Settings.getBooleanW("cheat.alwaysnight", false);
		CJB.nofalldamage = CJB_Settings.getBooleanW("cheat.nofalldamage", false);
		CJB.cartcontrol = CJB_Settings.getBooleanW("cheat.cartcontrol", false);
		CJB.extendedreach = CJB_Settings.getBooleanW("cheat.extendedreach", false);
		CJB.fastgrowth = CJB_Settings.getBooleanW("cheat.fastgrowth", false);
		CJB.nopickaxe = CJB_Settings.getBooleanW("cheat.nopickaxe", false);
		CJB.pickupallarrows = CJB_Settings.getBooleanW("cheat.pickupallarrows", false);
		CJB.attractiveitems = CJB_Settings.getBooleanW("cheat.attractiveitems", false);
		CJB.mobspawner = CJB_Settings.getBooleanW("cheat.mobspawner", false);
		CJB.infiniteitems = CJB_Settings.getBooleanW("cheat.infiniteitems", false);
		CJB.rain = CJB_Settings.getBooleanW("cheat.rain", false);
		CJB.thunder = CJB_Settings.getBooleanW("cheat.thunder", false);
		CJB.infinitexp = CJB_Settings.getBooleanW("cheat.infinitexp", false);
		CJB.nofirespread = CJB_Settings.getBooleanW("cheat.nofirespread", false);
		CJB.pickupdistance = CJB_Settings.getIntegerW("cheat.pickupdistance", 1);
		CJB.disablehunger = CJB_Settings.getBooleanW("cheat.disablehunger", false);
		CJB.infinitelava = CJB_Settings.getBooleanW("cheat.infinitelava", false);
		CJB.breakspeed = CJB_Settings.getBooleanW("cheat.breakspeed", false);
		
		CJB.mouseControl = CJB_Settings.getBoolean("cheat.flymousecontrol", true);
		CJB.flyspeed = CJB_Settings.getInteger("cheat.flyspeed", 3);
		CJB.runspeed = CJB_Settings.getInteger("cheat.runspeed", 3);
		CJB.speedMode = CJB_Settings.getBoolean("cheat.speedmode", false);
		CJB.autoJump = CJB_Settings.getBoolean("cheat.autojump", false);
		CJB.alwaysspeed = CJB_Settings.getBoolean("cheat.alwaysspeed", false);
		CJB.noclip = CJB_Settings.getBoolean("cheat.noclip", false);
		
		CJB.blackfog = CJB_Settings.getBoolean("cheat.blackfog", false);
	}
	
	public boolean onTickInGame(float f, Minecraft mc)
	{		
		m = mc;
		World world = CJB.w;
		EntityPlayer plr = CJB.plr;
		
		if (plr == null || world == null) {
			cheatsloaded = false;
			CJB.flying = false;
			return true;
		}
		
		plr.noClip = CJB.noclip && CJB.flying;
		
		if (!cheatsloaded)
			loadCheats();

		if (mc.currentScreen == null)
		{
			if (Keyboard.getEventKeyState())
			{				
				if (Keyboard.isKeyDown(CJB.KeyFly) && !flypressed)
				{
					flypressed = true;
					CJB.flying = !CJB.flying;
					
					if (CJB.pmnoclip && CJB.noclip && !mc.isSingleplayer()) {
						mc.thePlayer.sendChatMessage("/noclip " + (CJB.flying ? "enabled" : "disabled"));
					}
				}
				if (CJB.speedMode && Keyboard.isKeyDown(CJB.KeyFlySpeed) && !flypressed)
				{
					flypressed = true;
					CJB.toggleSpeed = !CJB.toggleSpeed;
				}
				if (mc.isSingleplayer() && Keyboard.isKeyDown(CJB.KeyGameMode) && !flypressed)
				{
					if (mc.playerController.isNotCreative()) {
						CJB.plr.sendGameTypeToPlayer(EnumGameType.CREATIVE);
					} else {
						CJB.plr.sendGameTypeToPlayer(EnumGameType.SURVIVAL);
					}
					flypressed = true;
				}
			}
			else {
				flypressed = false;
			}
		}
		
		if (!CJB.speedMode)
			CJB.toggleSpeed = false;
		
		if (mc.isSingleplayer() && !(mc.playerController instanceof CJB_PlayerController)) {
			EnumGameType gtype = mc.playerController.isInCreativeMode() ? EnumGameType.CREATIVE : EnumGameType.SURVIVAL;
			mc.playerController = new CJB_PlayerController(mc, mc.getSendQueue());
			mc.playerController.setGameType(gtype);
		}
			
		CJB.guiopen = mc.currentScreen != null;
		
		if (!CJB.pmfly)
			CJB.flying = false;
		
		if (CJB.breakspeed) {
			try {
				int i = (Integer) ModLoader.getPrivateValue(PlayerControllerMP.class, mc.playerController, 8);
				if (i > 2)
					ModLoader.setPrivateValue(PlayerControllerMP.class, mc.playerController, 8, 2);
			} catch (Throwable e) {
			}
		}
		
		if(!mc.isSingleplayer())
			return true;
		
		if (mc.isSingleplayer() && CJB.plr != null && CJB.plr.worldObj != null) {
			
			EntityPlayerMP player = (EntityPlayerMP) CJB.plr;
			
			if (!(player.theItemInWorldManager instanceof CJB_ItemInWorldManager)) {
				EnumGameType type = player.theItemInWorldManager.getGameType();
				player.theItemInWorldManager = new CJB_ItemInWorldManager(player, player.worldObj);
				player.theItemInWorldManager.thisPlayerMP = player;
				player.theItemInWorldManager.setGameType(type);
			}
		}
		
		if (CJB.disablehunger && !(plr.getFoodStats() instanceof CJB_FoodStats))
			ModLoader.setPrivateValue(EntityPlayer.class, plr, "foodStats", new CJB_FoodStats());
		
		if (!CJB.disablehunger && plr.getFoodStats() instanceof CJB_FoodStats)
			ModLoader.setPrivateValue(EntityPlayer.class, plr, "foodStats", new FoodStats());
		
		if (CJB.walkoversoil && !(Block.blocksList[60] instanceof CJB_BlockFarmland)) {
			Block.blocksList[60] = null;
			Block.blocksList[60] = new CJB_BlockFarmland(60).setHardness(0.6F).setStepSound(new StepSound("gravel", 1.0F, 1.0F)).setBlockName("farmland");
		}
		if ((!CJB.walkoversoil || !mc.isSingleplayer()) && Block.blocksList[60] instanceof CJB_BlockFarmland)
		{
			Block.blocksList[60] = null;
			Block.blocksList[60] = Block.tilledField; //new BlockFarmland(60).setHardness(0.6F).setStepSound(new StepSound("gravel", 1.0F, 1.0F)).setBlockName("farmland");
		}
		
		if (CJB.nofirespread && !(Block.blocksList[60] instanceof CJB_BlockFire)) {
			Block.blocksList[51] = null;
			Block.blocksList[51] = new CJB_BlockFire(51, 31).setHardness(0.0F).setLightValue(1.0F).setStepSound(Block.soundWoodFootstep).setBlockName("fire");
		}
		if ((!CJB.nofirespread || !mc.isSingleplayer()) && Block.blocksList[60] instanceof BlockFire)
		{
			Block.blocksList[51] = null;
			Block.blocksList[51] = Block.fire;
		}
		/*if (CJB.infinitelava && !(Block.blocksList[10] instanceof CJB_BlockLava) && mc.isSingleplayer()) {
			Block.blocksList[10] = null;
			Block.blocksList[10] = (new CJB_BlockLava(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().setRequiresSelfNotify();
		} else if (!CJB.infinitelava || ((Block.blocksList[10] instanceof CJB_BlockLava) && !mc.isSingleplayer())) {
			Block.blocksList[10] = null;
			Block.blocksList[10] = (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().setRequiresSelfNotify();
		}*/
	
		if (CJB.fastgrowth && !(Block.blocksList[59] instanceof CJB_BlockCrops)) {
			Block.blocksList[59] = null;
			Block.blocksList[59] = (new CJB_BlockCrops(59, 88)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("crops").setRequiresSelfNotify();
			Block.blocksList[83] = null;
			Block.blocksList[83] = (new CJB_BlockReed(83, 73)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("reeds");
			Block.blocksList[6] = null;
			Block.blocksList[6] = (new CJB_BlockSapling(6, 15)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("sapling").setRequiresSelfNotify();
			Block.blocksList[81] = null;
			Block.blocksList[81] = (new CJB_BlockCactus(81, 70)).setHardness(0.4F).setStepSound(Block.soundClothFootstep).setBlockName("cactus");
			Block.blocksList[104] = null;
			Block.blocksList[104] = (new CJB_BlockStem(104, Block.pumpkin)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
			Block.blocksList[105] = null;
			Block.blocksList[105] = (new CJB_BlockStem(105, Block.melon)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
			Block.blocksList[39] = null;
			Block.blocksList[39] = (BlockFlower)(new CJB_BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setLightValue(0.125F).setBlockName("mushroom");
			Block.blocksList[40] = null;
			Block.blocksList[40] = (BlockFlower)(new CJB_BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("mushroom");
			Block.blocksList[106] = null;
			Block.blocksList[106] = (new CJB_BlockVine(106)).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setBlockName("vine").setRequiresSelfNotify();
			
			Item.itemsList[256+95] = null;
			Item.dyePowder = (new CJB_ItemDye(95)).setIconCoord(14, 4).setItemName("dyePowder");
		}
		else if (!CJB.fastgrowth && (Block.blocksList[59] instanceof CJB_BlockCrops)) {
			Block.blocksList[59] = null;
			Block.blocksList[59] = Block.crops;//(new BlockCrops(59, 88)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("crops").setRequiresSelfNotify();
			Block.blocksList[83] = null;
			Block.blocksList[83] = Block.reed;//(new BlockReed(83, 73)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("reeds");
			Block.blocksList[6] = null;
			Block.blocksList[6] = Block.sapling;//(new BlockSapling(6, 15)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("sapling").setRequiresSelfNotify();
			Block.blocksList[81] = null;
			Block.blocksList[81] = Block.cactus;//(new BlockCactus(81, 70)).setHardness(0.4F).setStepSound(Block.soundClothFootstep).setBlockName("cactus");
			Block.blocksList[104] = null;
			Block.blocksList[104] = Block.pumpkinStem;//(new BlockStem(104, Block.pumpkin)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
			Block.blocksList[105] = null;
			Block.blocksList[105] = Block.melonStem;//(new BlockStem(105, Block.melon)).setHardness(0.0F).setStepSound(Block.soundWoodFootstep).setBlockName("pumpkinStem").setRequiresSelfNotify();
			Block.blocksList[39] = null;
			Block.blocksList[39] = Block.mushroomBrown;//(BlockFlower)(new BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setLightValue(0.125F).setBlockName("mushroom");
			Block.blocksList[40] = null;
			Block.blocksList[40] = Block.mushroomRed;//(BlockFlower)(new BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setBlockName("mushroom");
			Block.blocksList[106] = null;
			Block.blocksList[106] = Block.vine;//(new BlockVine(106)).setHardness(0.2F).setStepSound(Block.soundGrassFootstep).setBlockName("vine").setRequiresSelfNotify();
			
			Item.itemsList[256+95] = null;
			Item.dyePowder = (new ItemDye(95)).setIconCoord(14, 4).setItemName("dyePowder");
		}
		
		long time = world.getWorldTime() % 24000;
		
		if (CJB.alwaysday && time > 12000)
		{
			world.setWorldTime(world.getWorldTime() + 24000 - time);
		}
		
		if (CJB.alwaysnight && (time < 14000 || time > 22000))
		{
			world.setWorldTime(world.getWorldTime() + 15000 - time);
		}		
		
		if (CJB.rain && world.getWorldInfo().isRaining())
		{
			world.getWorldInfo().setRaining(false);
			world.getWorldInfo().setThundering(false);
		}
		
		if (CJB.thunder && world.getWorldInfo().isThundering())
		{
			world.getWorldInfo().setThundering(false);
		}
		
		return true;
	}
		
	public static boolean canStoreItemStack(ItemStack itemstack, EntityPlayer plr)
    {
        for(int i = 0; i < plr.inventory.mainInventory.length; i++)
        {
        	if (plr.inventory.mainInventory[i] == null)
        		return true;
        	
            if(plr.inventory.mainInventory[i] != null && plr.inventory.mainInventory[i].itemID == itemstack.itemID && plr.inventory.mainInventory[i].isStackable() && plr.inventory.mainInventory[i].stackSize < plr.inventory.mainInventory[i].getMaxStackSize() && plr.inventory.mainInventory[i].stackSize < plr.inventory.getInventoryStackLimit() && (!plr.inventory.mainInventory[i].getHasSubtypes() || plr.inventory.mainInventory[i].getItemDamage() == itemstack.getItemDamage()))
            {
                return true;
            }
        }

        return false;
    }

	public String getVersion() {
		return CJB.VERSION;
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
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}