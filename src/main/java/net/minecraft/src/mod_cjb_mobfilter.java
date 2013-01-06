package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class mod_cjb_mobfilter extends BaseMod {
	
	private long systemTime = 0;
	
	public static boolean killmobs;
	
	public static boolean filtercow;
	public static boolean filterchicken;
	public static boolean filterpig;
	public static boolean filtersheep;
	public static boolean filterwolf;
	public static boolean filtersquid;
	public static boolean filtermooshroom;
	public static boolean filtervillager;
	public static boolean filtersnowman;
	public static boolean filterocelot;
	
	public static boolean filterslime;
	public static boolean filterspider;
	public static boolean filtercreeper;
	public static boolean filterzombie;
	public static boolean filterskeleton;
	public static boolean filterenderman;
	public static boolean filtersilverfish;
	public static boolean filterenderdragon;
	public static boolean filtercavespider;
	
	public static boolean filterghast;
	public static boolean filterzombiepigman;
	public static boolean filterblaze;
	public static boolean filtermagmacube;
	
	public static boolean filterdog;
	
	public static boolean filtersloaded;
	
	public void load()
	{
		ModLoader.setInGameHook(this, true, false);
		CJB.modmobfilter = true;
	}
	
	public static void loadFilters()
	{
		filtersloaded = true;
		killmobs = CJB_Settings.getBoolean("mobfilter.killmobs", false);
		
		filtercow = CJB_Settings.getBooleanW("mobfilter.cow", false);
		filterchicken = CJB_Settings.getBooleanW("mobfilter.chicken", false);
		filterpig = CJB_Settings.getBooleanW("mobfilter.pig", false);
		filtersheep = CJB_Settings.getBooleanW("mobfilter.sheep", false);
		filterwolf = CJB_Settings.getBooleanW("mobfilter.wolf", false);
		filtersquid = CJB_Settings.getBooleanW("mobfilter.squid", false);
		filtermooshroom = CJB_Settings.getBooleanW("mobfilter.mooshroom", false);
		filtervillager = CJB_Settings.getBooleanW("mobfilter.villager", false);
		filtersnowman = CJB_Settings.getBooleanW("mobfilter.snowman", false);
		filterocelot = CJB_Settings.getBooleanW("mobfilter.ocelot", false);
		
		filterslime = CJB_Settings.getBooleanW("mobfilter.slime", false);
		filterspider = CJB_Settings.getBooleanW("mobfilter.spider", false);
		filtercreeper = CJB_Settings.getBooleanW("mobfilter.creeper", false);
		filterzombie = CJB_Settings.getBooleanW("mobfilter.zombie", false);
		filterskeleton = CJB_Settings.getBooleanW("mobfilter.skeleton", false);
		filterenderman = CJB_Settings.getBooleanW("mobfilter.enderman", false);
		filtersilverfish = CJB_Settings.getBooleanW("mobfilter.silverfish", false);
		filterenderdragon = CJB_Settings.getBooleanW("mobfilter.enderdragon", false);
		filtercavespider = CJB_Settings.getBooleanW("mobfilter.cavespider", false);
		
		filterghast = CJB_Settings.getBooleanW("mobfilter.ghast", false);
		filterzombiepigman = CJB_Settings.getBooleanW("mobfilter.zombiepigman", false);
		filterblaze = CJB_Settings.getBooleanW("mobfilter.blaze", false);
		filtermagmacube = CJB_Settings.getBooleanW("mobfilter.magmacube", false);
		
		filterdog = CJB_Settings.getBooleanW("mobfilter.dog", false);
	}

	@Override
	public boolean onTickInGame(float f, Minecraft mc)
	{
		if((mc.currentScreen != null && mc.currentScreen.doesGuiPauseGame()))
			return true;
		
		if(!mc.isSingleplayer())
		{
			CJB.modmobfilter = false;
			return true;
		} else
			CJB.modmobfilter = true;

		World world = CJB.w;
		EntityPlayer plr = CJB.plr;
		
		if (world == null || plr == null) {
			filtersloaded = false;
			return true;
		}
		
		if (!filtersloaded)
			loadFilters();
		
		if (System.currentTimeMillis() - systemTime >= 500)
		{
			systemTime = System.currentTimeMillis();
			
			if (killmobs)
			{
				for (int i = 0 ; i < world.loadedEntityList.size() ; i++)
				{
					
					Entity ent = (Entity) world.loadedEntityList.get(i);
					
					if (ent == null)
						continue;
					
					if (ent instanceof EntityCow && filtercow) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityChicken && filterchicken) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityPig && filterpig) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntitySheep && filtersheep) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityWolf && filterwolf) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntitySquid && filtersquid) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityMooshroom && filtermooshroom) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityVillager && filtervillager) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntitySnowman && filtersnowman) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityOcelot && filterocelot) ((EntityLiving)ent).setDead(); else
						
					if (ent instanceof EntitySlime && filterslime) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntitySpider && filterspider) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityCreeper && filtercreeper) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityZombie && filterzombie) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntitySkeleton && filterskeleton) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityEnderman && filterenderman) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntitySilverfish && filtersilverfish) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityDragon && filterenderdragon) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityCaveSpider && filtercavespider) ((EntityLiving)ent).setDead(); else
					
					if (ent instanceof EntityGhast && filterghast) ((EntityLiving)ent).setDead(); else
					if (ent instanceof EntityPigZombie && filterzombiepigman) ((EntityLiving)ent).setDead();else
					if (ent instanceof EntityBlaze && filterblaze) ((EntityLiving)ent).setDead();else
					if (ent instanceof EntityMagmaCube && filtermagmacube) ((EntityLiving)ent).setDead();
				}
			}
			
			if (filtercow) ModLoader.removeSpawn(EntityCow.class, EnumCreatureType.creature);
	       	if (filterchicken) ModLoader.removeSpawn(EntityChicken.class, EnumCreatureType.creature);
	       	if (filterpig) ModLoader.removeSpawn(EntityPig.class, EnumCreatureType.creature);
	       	if (filtersheep) ModLoader.removeSpawn(EntitySheep.class, EnumCreatureType.creature);
	       	if (filtersquid) ModLoader.removeSpawn(EntitySquid.class, EnumCreatureType.waterCreature);
	       	if (filterwolf) ModLoader.removeSpawn(EntityWolf.class, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.taiga, BiomeGenBase.forest});
	       	if (filtermooshroom) ModLoader.removeSpawn(EntityMooshroom.class, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore});
	       	if (filterocelot) ModLoader.removeSpawn(EntityOcelot.class, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.jungle, BiomeGenBase.jungleHills});
	       	
	       	if (filterslime) ModLoader.removeSpawn(EntitySlime.class, EnumCreatureType.monster);
	       	if (filterspider) ModLoader.removeSpawn(EntitySpider.class, EnumCreatureType.monster);
	       	if (filtercreeper) ModLoader.removeSpawn(EntityCreeper.class, EnumCreatureType.monster);
	       	if (filterzombie) ModLoader.removeSpawn(EntityZombie.class, EnumCreatureType.monster);
	       	if (filterskeleton) ModLoader.removeSpawn(EntitySkeleton.class, EnumCreatureType.monster);
	       	if (filterenderman) ModLoader.removeSpawn(EntityEnderman.class, EnumCreatureType.monster);
	       	
	       	if (filterghast) ModLoader.removeSpawn(EntityGhast.class, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.hell});
	       	if (filterzombiepigman) ModLoader.removeSpawn(EntityPigZombie.class, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.hell});
	       	if (filtermagmacube) ModLoader.removeSpawn(EntityMagmaCube.class, EnumCreatureType.monster, new BiomeGenBase[]{BiomeGenBase.hell});
		}
		
		return true;
	}
	
	public String getVersion() {
		return CJB.VERSION;
	}
	
	@Override
    public String getPriorities() {
    	return "required-after:mod_cjb_main";
    }
}