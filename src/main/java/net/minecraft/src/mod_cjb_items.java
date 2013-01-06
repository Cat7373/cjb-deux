package net.minecraft.src;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

public class mod_cjb_items extends BaseMod {
	
	Minecraft m = null;
	World w = null;
	public static boolean hasGoggles = false;
	
	public static Item WoN;
	public static Item WoNe;
	public static Item Goggles;
	public static Item PWB;
	public static Item RW;
	public static Item MS;
	public static Item PF;
	public static Item Sack;
	public static Item mobplacer;
	public static Item HammerWood;
	public static Item HammerStone;
	public static Item HammerIron;
	public static Item HammerDiamond;
	
	public static Block pressurePlatePlankRev;
	public static Block pressurePlateStoneRev;
	public static Block pressurePlateIron;
	public static Block pressurePlateGold;
	public static Block pressurePlateIronRev;
	public static Block pressurePlateGoldRev;
	
	public static CJB_BlockTrashcan Trashcan;
	public static CJB_RecyclerBlock Recycler;
	public static CJB_BlockMesh Mesh;
	public static CJB_AnvilBlock Anvil;
	
	public static boolean gogglesEquiped;
	
	public static boolean wonb = CJB_Settings.getBoolean("item.WrathofNotch", true);
	public static boolean gogglesb = CJB_Settings.getBoolean("item.Goggles", true);
	public static boolean pwbb = CJB_Settings.getBoolean("item.PortableWorkbench", true);
	public static boolean rwb = CJB_Settings.getBoolean("item.RazorWind", true);
	public static boolean msb = CJB_Settings.getBoolean("item.MobSpawner", true);
	public static boolean pfb = CJB_Settings.getBoolean("item.PortableFurnace", true);
	public static boolean sackb = CJB_Settings.getBoolean("item.Sack", true);
	public static boolean mobplacerb = CJB_Settings.getBoolean("item.MobPlacer", true);
	public static boolean hammer = CJB_Settings.getBoolean("item.RepairHammer", true);
	
	public static int pppr = CJB_Settings.getInteger("block.PressurePlatePlanksReversedID", 207);
	public static int ppsr = CJB_Settings.getInteger("block.PressurePlateStoneReversedID", 208);
	public static int ppi = CJB_Settings.getInteger("block.PressurePlateIronID", 200);
	public static int ppg = CJB_Settings.getInteger("block.PressurePlateGoldID", 201);
	public static int ppir = CJB_Settings.getInteger("block.PressurePlateIronReversedID", 202);
	public static int ppgr = CJB_Settings.getInteger("block.PressurePlateGoldReversedID", 203);
	public static int trash = CJB_Settings.getInteger("block.TrashcanID", 204);
	public static int recyc = CJB_Settings.getInteger("block.RecyclerID", 205);
	public static int mesh = CJB_Settings.getInteger("block.MeshID", 206);
	public static int anvil = CJB_Settings.getInteger("block.AnvilID", 209);
	
	public static int TrashcanModelID;
	public static int MeshModelID;
	public static int AnvilModelID;
	public static int recyclertex1;
	public static int recyclertex2;
	public static int trashtex1;
	public static int trashtex2;
	
	public static int sacktex1;
	public static int sacktex2;
	public static int sacktex3;
	
	public static int armorgogglestex = ModLoader.addArmor("goggles");
	
	@SuppressWarnings("unchecked")
	public void AddRenderer(@SuppressWarnings("rawtypes") Map map)
    {
		map.put(net.minecraft.src.CJB_ForceEntity.class, new CJB_ForceRender());
    }
	
	@Override
	public void load() 
	{	
		ModLoader.registerContainerID(this, 39201);
		//ModLoader.registerContainerID(this, 39202);
		//ModLoader.registerContainerID(this, 39203);
		//ModLoader.registerContainerID(this, 39204);
		ModLoader.registerContainerID(this, 39205);
		
		MinecraftForgeClient.preloadTexture("/cjb/blocks.png");
		
		pfb = false;
		sackb = false;
		mobplacerb = false;
		
		if (wonb){
			WoN = (new CJB_ItemWoN(3920-256, 9)).setIconIndex(0).setItemName("WoN");
			WoNe = (new CJB_ItemWoN(3921-256, 0)).setIconIndex(1).setItemName("WoNe");
			ModLoader.addName(WoN, "Wrath of Notch");
			ModLoader.addName(WoNe, "Wrath of Notch");
			ModLoader.addRecipe(new ItemStack(WoN, 1), new Object[]{"#", "X", "X", Character.valueOf('X'), Block.blockDiamond, Character.valueOf('#'), Block.glowStone });
			ModLoader.addRecipe(new ItemStack(WoN, 1), new Object[]{"#", "X", Character.valueOf('X'), WoNe, Character.valueOf('#'), Block.glowStone });
		}
		
		if (gogglesb) {
			Goggles = (new CJB_ItemGoggles(3922-256, EnumArmorMaterial.GOLD, armorgogglestex, 0)).setIconIndex(10).setItemName("itemGoggles");
			ModLoader.addName(Goggles, "Nightvision Goggles");
			ModLoader.addRecipe(new ItemStack(Goggles, 1), new Object[]{"L L","L#L", "#X#", Character.valueOf('L'), Item.leather, Character.valueOf('X'), Item.ingotIron, Character.valueOf('#'), Block.glowStone });
			Goggles.setMaxDamage(0);
		}
		
		if (pwbb) {
			PWB = (new CJB_PWBItem(3923-256, 0)).setIconIndex(3).setItemName("PWB");
			ModLoader.addName(PWB, "Portable Workbench");
			ModLoader.addRecipe(new ItemStack(PWB, 1), new Object[]{"##","##", Character.valueOf('#'), Item.stick});
		}
		
		if (rwb) {
			RW = (new CJB_ItemRazorWind(3924-256)).setIconIndex(4).setItemName("razorwind");
			ModLoader.addName(RW, "Razor Wind");
			ModLoader.addRecipe(new ItemStack(RW, 1), new Object[]{"LPP","LPP","LLL", Character.valueOf('L'), Item.stick, Character.valueOf('P'), Item.paper});
			ModLoader.registerEntityID(net.minecraft.src.CJB_ForceEntity.class, "Force", ModLoader.getUniqueEntityId());
		}
		
		if (msb){
			MS = (new CJB_ItemMobSpawner(3925-256)).setIconIndex(15).setItemName("MobSpawner");
			ModLoader.addName(MS, "Mobster Wand");
			ModLoader.addRecipe(new ItemStack(MS, 1), new Object[]{" L "," P "," P ", Character.valueOf('P'), Item.blazeRod, Character.valueOf('L'), Item.eyeOfEnder});
		}
		
		if (pfb) {
			PF = (new CJB_PFItem(3926-256)).setIconIndex(2).setItemName("PF");
			ModLoader.addName(PF, "Portable Furnace");
			ModLoader.addRecipe(new ItemStack(PF, 1), new Object[]{"###","#X#", "# #", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.bucketLava});
		}
		
		if (sackb) {
			Sack = (new CJB_SackItem(3927-256)).setItemName("Sack");
			ModLoader.addRecipe(new ItemStack(Sack, 1, 0), new Object[]{" # ","XXX", "X$X", Character.valueOf('#'), Item.silk, Character.valueOf('X'), Item.leather, Character.valueOf('$'), Item.ingotIron});
			ModLoader.addRecipe(new ItemStack(Sack, 1, 1), new Object[]{" # ","XXX", "X$X", Character.valueOf('#'), Item.silk, Character.valueOf('X'), Item.leather, Character.valueOf('$'), Item.ingotGold});
			ModLoader.addRecipe(new ItemStack(Sack, 1, 2), new Object[]{" # ","XXX", "X$X", Character.valueOf('#'), Item.silk, Character.valueOf('X'), Item.leather, Character.valueOf('$'), Item.diamond});
		}
		
		if (mobplacerb) {
			mobplacer = (new CJB_ItemMonsterPlacer(3928-256)).setIconCoord(9, 9).setItemName("monsterPlacer");
		}
		
		if (hammer) {
			HammerWood = (new CJB_ItemHammer(3928-256,EnumToolMaterial.WOOD)).setIconIndex(14).setItemName("Wooden.Hammer");
			HammerStone = (new CJB_ItemHammer(3929-256,EnumToolMaterial.STONE)).setIconIndex(13).setItemName("Stone Repair Hammer");
			HammerIron = (new CJB_ItemHammer(3930-256,EnumToolMaterial.IRON)).setIconIndex(12).setItemName("Iron Repair Hammer");
			HammerDiamond = (new CJB_ItemHammer(3931-256,EnumToolMaterial.EMERALD)).setIconIndex(11).setItemName("Diamond Repair Hammer");
			ModLoader.addRecipe(new ItemStack(HammerWood, 5, 0), new Object[]{"###","#X#", " X ", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Item.stick});
			ModLoader.addRecipe(new ItemStack(HammerStone, 5, 0), new Object[]{"###","#X#", " X ", Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), Item.stick});
			ModLoader.addRecipe(new ItemStack(HammerIron, 5, 0), new Object[]{"###","#X#", " X ", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.stick});
			ModLoader.addRecipe(new ItemStack(HammerDiamond, 5, 0), new Object[]{"###","#X#", " X ", Character.valueOf('#'), Item.diamond, Character.valueOf('X'), Item.stick});
			ModLoader.addName(HammerWood, "Wooden Repair Hammer");
			ModLoader.addName(HammerStone, "Stone Repair Hammer");
			ModLoader.addName(HammerIron, "Iron Repair Hammer");
			ModLoader.addName(HammerDiamond, "Diamond Repair Hammer");
		}

		if (pppr > 0) {
			pressurePlatePlankRev = (new CJB_BlockPressurePlate(pppr, Block.planks.blockIndexInTexture, "everything", Material.wood, true)).setHardness(0.5F).setStepSound(Block.soundWoodFootstep).setBlockName("pressurePlatePlanksRev").setRequiresSelfNotify();
			ModLoader.registerBlock(pressurePlatePlankRev);
			ModLoader.addName(pressurePlatePlankRev, "Pressure Plate Plank Reversed");
			ModLoader.addRecipe(new ItemStack(pressurePlatePlankRev, 1), new Object[]{"#", "$", Character.valueOf('#'), Block.pressurePlatePlanks, Character.valueOf('$'), Block.torchRedstoneActive});
		}
		
		if (ppsr > 0) {
			pressurePlateStoneRev = (new CJB_BlockPressurePlate(ppsr, Block.stone.blockIndexInTexture, "living", Material.rock, true)).setHardness(0.5F).setStepSound(Block.soundStoneFootstep).setBlockName("pressurePlateStoneRev").setRequiresSelfNotify();
			ModLoader.registerBlock(pressurePlateStoneRev);
			ModLoader.addName(pressurePlateStoneRev, "Pressure Plate Stone Reversed");
			ModLoader.addRecipe(new ItemStack(pressurePlateStoneRev, 1), new Object[]{"#", "$", Character.valueOf('#'), Block.pressurePlateStone, Character.valueOf('$'), Block.torchRedstoneActive});
		}
		
		if (ppi > 0) {
			pressurePlateIron = (new CJB_BlockPressurePlate(ppi, Block.blockSteel.blockIndexInTexture, "players", Material.iron, false)).setHardness(0.5F).setStepSound(Block.soundMetalFootstep).setBlockName("pressurePlateIron").setRequiresSelfNotify();
			ModLoader.registerBlock(pressurePlateIron);
			ModLoader.addName(pressurePlateIron, "Pressure Plate Iron");
			ModLoader.addRecipe(new ItemStack(pressurePlateIron, 1), new Object[]{"##", Character.valueOf('#'), Item.ingotIron});
		}
		
		if (ppg > 0) {
			pressurePlateGold = (new CJB_BlockPressurePlate(ppg, Block.blockGold.blockIndexInTexture, "creatures", Material.iron, false)).setHardness(0.5F).setStepSound(Block.soundMetalFootstep).setBlockName("pressurePlateGold").setRequiresSelfNotify();
			ModLoader.registerBlock(pressurePlateGold);
			ModLoader.addName(pressurePlateGold, "Pressure Plate Gold");
			ModLoader.addRecipe(new ItemStack(pressurePlateGold, 1), new Object[]{"##", Character.valueOf('#'), Item.ingotGold});
		}
		
		if (ppir > 0) {
			pressurePlateIronRev = (new CJB_BlockPressurePlate(ppir, Block.blockSteel.blockIndexInTexture, "players", Material.iron, true)).setHardness(0.5F).setStepSound(Block.soundMetalFootstep).setBlockName("pressurePlateIronRev").setRequiresSelfNotify();
			ModLoader.registerBlock(pressurePlateIronRev);
			ModLoader.addName(pressurePlateIronRev, "Pressure Plate Iron Reversed");
			ModLoader.addRecipe(new ItemStack(pressurePlateIronRev, 1), new Object[]{"#", "$", Character.valueOf('#'), pressurePlateIron, Character.valueOf('$'), Block.torchRedstoneActive});
		}
		
		if (ppgr > 0) {
			pressurePlateGoldRev = (new CJB_BlockPressurePlate(ppgr, Block.blockGold.blockIndexInTexture, "creatures", Material.iron, true)).setHardness(0.5F).setStepSound(Block.soundMetalFootstep).setBlockName("pressurePlateGoldRev").setRequiresSelfNotify();
			ModLoader.registerBlock(pressurePlateGoldRev);
			ModLoader.addName(pressurePlateGoldRev, "Pressure Plate Gold Reversed");
			ModLoader.addRecipe(new ItemStack(pressurePlateGoldRev, 1), new Object[]{"#", "$", Character.valueOf('#'), pressurePlateGold, Character.valueOf('$'), Block.torchRedstoneActive});
		}
		
		if (trash > 0) {
			Trashcan = (CJB_BlockTrashcan)new CJB_BlockTrashcan(trash).setHardness(2.5F).setStepSound(Block.soundWoodFootstep).setBlockName("trashcan").setRequiresSelfNotify();
			ModLoader.registerBlock(Trashcan);
			ModLoader.addName(Trashcan, "Trashcan");
			ModLoader.addRecipe(new ItemStack(Trashcan, 1), new Object[]{"# #", "# #", "###", Character.valueOf('#'), Block.wood});
			TrashcanModelID = ModLoader.getUniqueBlockModelID(this, true);
		}
		
		if (recyc > 0) {
			Recycler = (CJB_RecyclerBlock)new CJB_RecyclerBlock(recyc).setHardness(3.5F).setStepSound(Block.soundStoneFootstep).setBlockName("recycler").setRequiresSelfNotify();
			ModLoader.registerBlock(Recycler);
			ModLoader.addName(Recycler, "Recycler");
			ModLoader.addRecipe(new ItemStack(Recycler, 1), new Object[]{" # ", "#$#", " # ", Character.valueOf('#'), Block.blockLapis, Character.valueOf('$'), Item.bow});
			ModLoader.registerTileEntity(net.minecraft.src.CJB_RecyclerTileEntity.class, "Recycler");
		}
		
		if (mesh > 0) {
			Mesh = (CJB_BlockMesh) new CJB_BlockMesh(mesh, Material.wood).setHardness(3F).setStepSound(Block.soundWoodFootstep).setBlockName("mesh").setRequiresSelfNotify();
			ModLoader.registerBlock(Mesh);
			ModLoader.addName(Mesh, "Mesh");
			ModLoader.addRecipe(new ItemStack(Mesh, 16), new Object[]{"# #", " # ", "# #", Character.valueOf('#'), Item.ingotIron});
			MeshModelID = ModLoader.getUniqueBlockModelID(this, true);
		}
		if (anvil > 0) {
			Anvil = (CJB_AnvilBlock) new CJB_AnvilBlock(anvil).setHardness(3F).setStepSound(Block.soundMetalFootstep).setBlockName("anvil").setRequiresSelfNotify();
			ModLoader.registerBlock(Anvil);
			ModLoader.addName(Anvil, "Anvil");
			ModLoader.addRecipe(new ItemStack(Anvil, 1), new Object[]{"###", " X ", Character.valueOf('#'), Block.blockSteel, Character.valueOf('X'), Block.stoneSingleSlab});
			ModLoader.registerTileEntity(net.minecraft.src.CJB_AnvilTileEntity.class, "Anvil");
			AnvilModelID = ModLoader.getUniqueBlockModelID(this, true);
		}

		ModLoader.setInGameHook(this, true, false);

		File file = new File(Minecraft.getMinecraftDir(), "resources/newsound/cjb/goggles.ogg");
		ModLoader.getMinecraftInstance().installResource("newsound/cjb/goggles.ogg", file);
		file = new File(Minecraft.getMinecraftDir(), "resources/newsound/cjb/swoosh.ogg");
		ModLoader.getMinecraftInstance().installResource("newsound/cjb/swoosh.ogg", file);
		file = new File(Minecraft.getMinecraftDir(), "resources/newsound/cjb/anvil.ogg");
		ModLoader.getMinecraftInstance().installResource("newsound/cjb/anvil.ogg", file);
		
		ModLoader.registerPacketChannel(this, "CJB|Recycler");
	}
	
	@Override
	public boolean onTickInGame(float f, Minecraft mc)
    {
		CJB.moditems = true;	
		renderMod(mc);
		return true;
    }
	
	@Override
	public void addRenderer(@SuppressWarnings("rawtypes") Map map)
    {
		map.put(net.minecraft.src.CJB_EntityEgg.class, new RenderSnowball(Item.egg.getIconFromDamage(0)));
		map.put(CJB_ForceEntity.class,  new CJB_ForceRender());
    }
	
	public void renderMod(Minecraft mc)
	{
		m = mc;
		EntityPlayer plr = CJB.plr;
		
		if (mobplacerb && !(Item.egg instanceof CJB_ItemEgg) ) {
			Item.itemsList[Item.egg.shiftedIndex] = null;
			Item.egg = (new CJB_ItemEgg(88)).setIconCoord(12, 0).setItemName("egg");
		}
		
    	if(m.theWorld == null || m.theWorld != w || plr == null){
    		w = m.theWorld;
    		hasGoggles = false;
    		return;
    	}
    	
		if (gogglesb && plr != null)
		{
			hasGoggles = false;
			for(ItemStack itemstack : plr.inventory.armorInventory)
			{
				if (itemstack != null && itemstack.getItem() == Goggles)
					hasGoggles = true;
			}
			
			if (hasGoggles && !gogglesEquiped) {
				gogglesEquiped = true;
				w.playSoundAtEntity(m.thePlayer, "cjb.goggles", 1F, 1F);
			}
			if (!hasGoggles && gogglesEquiped) {
				gogglesEquiped = false;
			}
			
			if (hasGoggles)
			{
				GL11.glPushMatrix();
				ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
		        int i = scaledresolution.getScaledWidth();
		        int j = scaledresolution.getScaledHeight();
		        GL11.glDisable(2896 /*GL_LIGHTING*/);
		        GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		        m.ingameGUI.drawRect(0, 0, i, j, 0x3300FF00);
		        GL11.glPopMatrix();
			}
		}
	}
    
    public boolean renderWorldBlock(RenderBlocks renderblocks, IBlockAccess iblockaccess, int i, int j, int k, Block block, int l)
    {
        if(l == TrashcanModelID)
            return Trashcan.RenderInWorld(renderblocks, iblockaccess, i, j, k, block);
        else if(l == MeshModelID)
            return Mesh.RenderInWorld(renderblocks, iblockaccess, i, j, k, block);
        else if(l == AnvilModelID)
        	return Anvil.RenderInWorld(renderblocks, iblockaccess, i, j, k, block);
        else
            return false;
    }
    
    public void renderInvBlock(RenderBlocks renderblocks, Block block, int i, int j)
    {
        if(j == TrashcanModelID)
            Trashcan.RenderInInv(renderblocks, block, i);
        if(j == MeshModelID) 
        	Mesh.RenderInInv(renderblocks, block, i);
        if(j == AnvilModelID) 
        	Anvil.RenderInInv(renderblocks, block, i);
    }
    
    @Override
    public GuiContainer getContainerGUI(EntityClientPlayerMP plr, int gid, int i, int j, int k)
    {
    	System.out.println("test1");
        if (gid == 39201){
        	return new GuiChest(plr.inventory, new InventoryBasic("Trashcan",27));
        }
        if (gid == 39202) {
        	TileEntity tile = plr.worldObj.getBlockTileEntity(i, j, k);
        	
        	if (tile instanceof CJB_RecyclerTileEntity) {
        		return new CJB_RecyclerGui(plr.inventory, (CJB_RecyclerTileEntity) tile);
        	}
        }
        if (gid == 39203){
        	return new CJB_PWBGuiCrafting(plr.inventory, plr.worldObj);
        }
        if (gid == 39204){
        	return new CJB_SackGui(plr.inventory, CJB_SackItem.getSackData(plr.getCurrentEquippedItem(),  plr.worldObj));
        }
        if (gid == 39205){
        	TileEntity tile = plr.worldObj.getBlockTileEntity(i, j, k);
        	
        	if (tile instanceof CJB_AnvilTileEntity) {
        		return new CJB_AnvilGui(plr.inventory, (CJB_AnvilTileEntity) tile);
        	}
        }
        return null;
    }
    
    public void serverCustomPayload(NetServerHandler var1, Packet250CustomPayload var2)
    {
        if (var2.channel.equals("CJB|Recycler"))
        {
            EntityPlayerMP var3 = var1.getPlayer();

            try
            {
                DataInputStream var4 = new DataInputStream(new ByteArrayInputStream(var2.data));
                int var5 = var4.readInt();
                int var6 = var4.readInt();
                int var7 = var4.readInt();
                byte var8 = (byte)var4.read();
                int var9 = var4.readInt();
                MinecraftServer var11 = MinecraftServer.getServer();
                WorldServer var12 = var11.worldServerForDimension(var8);

                if (var12 == null || var12.getBlockId(var5, var6, var7) != Recycler.blockID)
                {
                    return;
                }

                TileEntity var13 = var11.worldServerForDimension(var8).getBlockTileEntity(var5, var6, var7);

                if (var13 instanceof CJB_RecyclerTileEntity)
                {
                	CJB_RecyclerTileEntity var14 = (CJB_RecyclerTileEntity)var13;
                    var14.recycletime = var9;
                    var11.getConfigurationManager().sendPacketToAllPlayersInDimension(var2, var8);
                }
            }
            catch (IOException var15)
            {
                var15.printStackTrace();
            }
        }
    }

    public void clientCustomPayload(NetClientHandler var1, Packet250CustomPayload var2)
    {
        if (var2.channel.equals("CJB|Recycler"))
        {
            try
            {
                DataInputStream var3 = new DataInputStream(new ByteArrayInputStream(var2.data));
                int var4 = var3.readInt();
                int var5 = var3.readInt();
                int var6 = var3.readInt();
                byte var7 = (byte)var3.read();
                int var8 = var3.readInt();
                int var9 = var3.read();
                Minecraft mc = Minecraft.getMinecraft();
                EntityClientPlayerMP plr = mc.thePlayer;

                if (plr.dimension != var7)
                {
                    return;
                }

                TileEntity var12 = plr.worldObj.getBlockTileEntity(var4, var5, var6);

                if (var12 instanceof CJB_RecyclerTileEntity)
                {
                	CJB_RecyclerTileEntity var13 = (CJB_RecyclerTileEntity)var12;
                    var13.recycletime = var8;
                }
            }
            catch (IOException var14)
            {
                var14.printStackTrace();
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