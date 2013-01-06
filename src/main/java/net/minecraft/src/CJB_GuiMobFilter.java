package net.minecraft.src;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;

public class CJB_GuiMobFilter extends CJB_GuiMain
{
	private static int menuid = 1001;
	
    public void initGui()
    {
    	super.initGui();
    	previousScreen = this;
    	selectedmenu = menuid;
        int k = (width - menuw) / 2;
    	int l = (height - menuh) / 2;
        int i = 0;
        
        menubuttons.add(new CJB_Button(1001, "Options", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1001? 0 : 32), 1f, 0x40FFFFFF));
        menubuttons.add(new CJB_Button(1002, "Neutral", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1002? 0 : 32), 1f, 0x40FFFFFF));
        menubuttons.add(new CJB_Button(1003, "Aggressive", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1003? 0 : 32), 1f, 0x40FFFFFF));
        menubuttons.add(new CJB_Button(1004, "Nether", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1004? 0 : 32), 1f, 0x40FFFFFF));
        if(CJB.modmobs) menubuttons.add(new CJB_Button(1005, "CJB Mobs", k-80+3, l+3+(i++ * 16), 80, 16, true, "/cjb/menu.png", 176, 9 + (selectedmenu == 1005? 0 : 32), 1f, 0x40FFFFFF));
         
        k += 8;
        l += 19;
        if (selectedmenu == 1001)
        {
        	buttonslist.add(new CJB_ButtonLabel(100, "Kill Remaining Mobs = " + mod_cjb_mobfilter.killmobs, k, l, true, "Kills already spawned mobs that are filtered"));
        }
        if (selectedmenu == 1002){
	    	buttonslist.add(new CJB_ButtonLabel(1, "Cows = " + bstr[(mod_cjb_mobfilter.filtercow)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(2, "Chickens = " + bstr[(mod_cjb_mobfilter.filterchicken)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(3, "Pigs = " + bstr[(mod_cjb_mobfilter.filterpig)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(4, "Sheep = " + bstr[(mod_cjb_mobfilter.filtersheep)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(5, "Wolfs = " + bstr[(mod_cjb_mobfilter.filterwolf)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(6, "Squid = " + bstr[(mod_cjb_mobfilter.filtersquid)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(7, "Mooshroom = " + bstr[(mod_cjb_mobfilter.filtermooshroom)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(8, "Villager = " + bstr[(mod_cjb_mobfilter.filtervillager)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(9, "Snowman = " + bstr[(mod_cjb_mobfilter.filtersnowman)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(10, "Ocelot = " + bstr[(mod_cjb_mobfilter.filterocelot)?1:0], k, l, true));
        }
    	
        if (selectedmenu == 1003){
	    	buttonslist.add(new CJB_ButtonLabel(11, "Slimes = " + bstr[(mod_cjb_mobfilter.filterslime)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(12, "Spiders = " + bstr[(mod_cjb_mobfilter.filterspider)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(13, "Creepers = " + bstr[(mod_cjb_mobfilter.filtercreeper)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(14, "Zombies = " + bstr[(mod_cjb_mobfilter.filterzombie)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(15, "Skeletons = " + bstr[(mod_cjb_mobfilter.filterskeleton)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(16, "Enderman = " + bstr[(mod_cjb_mobfilter.filterenderman)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(17, "Silverfish = " + bstr[(mod_cjb_mobfilter.filtersilverfish)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(18, "Ender Dragon = " + bstr[(mod_cjb_mobfilter.filterenderdragon)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(19, "Cave Spider = " + bstr[(mod_cjb_mobfilter.filtercavespider)?1:0], k, l, true));
        }
        
        if (selectedmenu == 1004){
	    	buttonslist.add(new CJB_ButtonLabel(21, "Ghasts = " + bstr[(mod_cjb_mobfilter.filterghast)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(22, "Zombie Pigman = " + bstr[(mod_cjb_mobfilter.filterzombiepigman)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(23, "Blaze = " + bstr[(mod_cjb_mobfilter.filterblaze)?1:0], k, l, true));
	    	buttonslist.add(new CJB_ButtonLabel(24, "Magma Cube = " + bstr[(mod_cjb_mobfilter.filtermagmacube)?1:0], k, l, true));
        }
        
        if (selectedmenu == 1005){
	    	buttonslist.add(new CJB_ButtonLabel(32, "Dog = " + bstr[(mod_cjb_mobfilter.filterdog)?1:0], k, l, true));
        }
    }
    
    public void actionPerformedMenus(CJB_Button cjbbutton)
    {   	
    	if ( cjbbutton.id >= 1000 && cjbbutton.id < 1020)
    		menuid = cjbbutton.id;
    	
        super.actionPerformedMenus(cjbbutton);
    }

    public void actionPerformed(CJB_Button cjbbutton)
    {   	
    	
    	if ( cjbbutton.id == 100) CJB_Settings.setBoolean("mobfilter.killmobs", mod_cjb_mobfilter.killmobs = !mod_cjb_mobfilter.killmobs);
       	if ( cjbbutton.id == 1) CJB_Settings.setBooleanW("mobfilter.cow", mod_cjb_mobfilter.filtercow = !mod_cjb_mobfilter.filtercow);
       	if ( cjbbutton.id == 2) CJB_Settings.setBooleanW("mobfilter.chicken", mod_cjb_mobfilter.filterchicken = !mod_cjb_mobfilter.filterchicken);
       	if ( cjbbutton.id == 3) CJB_Settings.setBooleanW("mobfilter.pig", mod_cjb_mobfilter.filterpig = !mod_cjb_mobfilter.filterpig);
       	if ( cjbbutton.id == 4) CJB_Settings.setBooleanW("mobfilter.sheep", mod_cjb_mobfilter.filtersheep = !mod_cjb_mobfilter.filtersheep);
       	if ( cjbbutton.id == 5) CJB_Settings.setBooleanW("mobfilter.wolf", mod_cjb_mobfilter.filterwolf = !mod_cjb_mobfilter.filterwolf);
       	if ( cjbbutton.id == 6) CJB_Settings.setBooleanW("mobfilter.squid", mod_cjb_mobfilter.filtersquid = !mod_cjb_mobfilter.filtersquid);
       	if ( cjbbutton.id == 7) CJB_Settings.setBooleanW("mobfilter.mooshroom", mod_cjb_mobfilter.filtermooshroom = !mod_cjb_mobfilter.filtermooshroom);
       	if ( cjbbutton.id == 8) CJB_Settings.setBooleanW("mobfilter.villager", mod_cjb_mobfilter.filtervillager = !mod_cjb_mobfilter.filtervillager);
       	if ( cjbbutton.id == 9) CJB_Settings.setBooleanW("mobfilter.snowman", mod_cjb_mobfilter.filtersnowman = !mod_cjb_mobfilter.filtersnowman);
       	if ( cjbbutton.id == 10) CJB_Settings.setBooleanW("mobfilter.ocelot", mod_cjb_mobfilter.filterocelot = !mod_cjb_mobfilter.filterocelot);
       	
       	if ( cjbbutton.id == 11) CJB_Settings.setBooleanW("mobfilter.slime", mod_cjb_mobfilter.filterslime = !mod_cjb_mobfilter.filterslime);
       	if ( cjbbutton.id == 12) CJB_Settings.setBooleanW("mobfilter.spider", mod_cjb_mobfilter.filterspider = !mod_cjb_mobfilter.filterspider);
       	if ( cjbbutton.id == 13) CJB_Settings.setBooleanW("mobfilter.creeper", mod_cjb_mobfilter.filtercreeper = !mod_cjb_mobfilter.filtercreeper);
       	if ( cjbbutton.id == 14) CJB_Settings.setBooleanW("mobfilter.zombie", mod_cjb_mobfilter.filterzombie = !mod_cjb_mobfilter.filterzombie);
       	if ( cjbbutton.id == 15) CJB_Settings.setBooleanW("mobfilter.skeleton", mod_cjb_mobfilter.filterskeleton = !mod_cjb_mobfilter.filterskeleton);
       	if ( cjbbutton.id == 16) CJB_Settings.setBooleanW("mobfilter.enderman", mod_cjb_mobfilter.filterenderman = !mod_cjb_mobfilter.filterenderman);
       	if ( cjbbutton.id == 17) CJB_Settings.setBooleanW("mobfilter.silverfish", mod_cjb_mobfilter.filtersilverfish = !mod_cjb_mobfilter.filtersilverfish);
       	if ( cjbbutton.id == 18) CJB_Settings.setBooleanW("mobfilter.enderdragon", mod_cjb_mobfilter.filterenderdragon = !mod_cjb_mobfilter.filterenderdragon);
       	if ( cjbbutton.id == 19) CJB_Settings.setBooleanW("mobfilter.cavespider", mod_cjb_mobfilter.filtercavespider = !mod_cjb_mobfilter.filtercavespider);
       	
       	if ( cjbbutton.id == 21) CJB_Settings.setBooleanW("mobfilter.ghast", mod_cjb_mobfilter.filterghast = !mod_cjb_mobfilter.filterghast);
       	if ( cjbbutton.id == 22) CJB_Settings.setBooleanW("mobfilter.zombiepigman", mod_cjb_mobfilter.filterzombiepigman = !mod_cjb_mobfilter.filterzombiepigman);
       	if ( cjbbutton.id == 23) CJB_Settings.setBooleanW("mobfilter.blaze", mod_cjb_mobfilter.filterblaze = !mod_cjb_mobfilter.filterblaze);
       	if ( cjbbutton.id == 24) CJB_Settings.setBooleanW("mobfilter.magmacube", mod_cjb_mobfilter.filtermagmacube = !mod_cjb_mobfilter.filtermagmacube);
       	
       	if ( cjbbutton.id == 32) CJB_Settings.setBooleanW("mobfilter.dog", mod_cjb_mobfilter.filterdog = !mod_cjb_mobfilter.filterdog);
       	
       	if (mod_cjb_mobfilter.filtercow) ModLoader.removeSpawn(EntityCow.class, EnumCreatureType.creature); else ModLoader.addSpawn(EntityCow.class, 8, 4, 4, EnumCreatureType.creature);
       	if (mod_cjb_mobfilter.filterchicken) ModLoader.removeSpawn(EntityChicken.class, EnumCreatureType.creature); else ModLoader.addSpawn(EntityChicken.class, 10, 4, 4, EnumCreatureType.creature);
       	if (mod_cjb_mobfilter.filterpig) ModLoader.removeSpawn(EntityPig.class, EnumCreatureType.creature); else ModLoader.addSpawn(EntityPig.class, 10, 4, 4, EnumCreatureType.creature);
       	if (mod_cjb_mobfilter.filtersheep) ModLoader.removeSpawn(EntitySheep.class, EnumCreatureType.creature); else ModLoader.addSpawn(EntitySheep.class, 12, 4, 4, EnumCreatureType.creature);
       	if (mod_cjb_mobfilter.filtersquid) ModLoader.removeSpawn(EntitySquid.class, EnumCreatureType.waterCreature); else ModLoader.addSpawn(EntitySquid.class, 10, 4, 4, EnumCreatureType.waterCreature);
       	if (mod_cjb_mobfilter.filterwolf) ModLoader.removeSpawn(EntityWolf.class, EnumCreatureType.creature); else ModLoader.addSpawn(EntityWolf.class, 2, 4, 4, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.taiga, BiomeGenBase.forest});
       	if (mod_cjb_mobfilter.filtermooshroom) ModLoader.removeSpawn(EntityMooshroom.class, EnumCreatureType.creature); else ModLoader.addSpawn(EntityMooshroom.class, 8, 4, 8, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore});
       	if (mod_cjb_mobfilter.filterocelot) ModLoader.removeSpawn(EntityOcelot.class, EnumCreatureType.creature); else ModLoader.addSpawn(EntityOcelot.class, 10, 4, 4, EnumCreatureType.creature, new BiomeGenBase[]{BiomeGenBase.jungle, BiomeGenBase.jungleHills});
       	
       	if (mod_cjb_mobfilter.filterslime) ModLoader.removeSpawn(EntitySlime.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntitySlime.class, 10, 4, 4, EnumCreatureType.monster);
       	if (mod_cjb_mobfilter.filterspider) ModLoader.removeSpawn(EntitySpider.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntitySpider.class, 10, 4, 4, EnumCreatureType.monster);
       	if (mod_cjb_mobfilter.filtercreeper) ModLoader.removeSpawn(EntityCreeper.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntityCreeper.class, 10, 4, 4, EnumCreatureType.monster);
       	if (mod_cjb_mobfilter.filterzombie) ModLoader.removeSpawn(EntityZombie.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntityZombie.class, 10, 4, 4, EnumCreatureType.monster);
       	if (mod_cjb_mobfilter.filterskeleton) ModLoader.removeSpawn(EntitySkeleton.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntitySkeleton.class, 10, 4, 4, EnumCreatureType.monster);
       	if (mod_cjb_mobfilter.filterenderman) ModLoader.removeSpawn(EntityEnderman.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntityEnderman.class, 1, 4, 4, EnumCreatureType.monster);

       	if (mod_cjb_mobfilter.filterghast) ModLoader.removeSpawn(EntityGhast.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntityGhast.class, 50, 4, 4, EnumCreatureType.monster,new BiomeGenBase[]{BiomeGenBase.hell});
       	if (mod_cjb_mobfilter.filterzombiepigman) ModLoader.removeSpawn(EntityPigZombie.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntityPigZombie.class, 100, 4, 4, EnumCreatureType.monster,new BiomeGenBase[]{BiomeGenBase.hell});
       	if (mod_cjb_mobfilter.filtermagmacube) ModLoader.removeSpawn(EntityMagmaCube.class, EnumCreatureType.monster); else ModLoader.addSpawn(EntityMagmaCube.class, 1, 4, 4, EnumCreatureType.monster,new BiomeGenBase[]{BiomeGenBase.hell});
       	
       	super.actionPerformed(cjbbutton);
    }
    
    public void mouseClicked(int i, int j, int k)
    {
    	super.mouseClicked(i, j, k);
    	if(k == 0)
        {            
            for(int l = 0; l < buttonslist.size(); l++)
            {
            	CJB_Button cjbbutton = (CJB_Button)buttonslist.get(l);
                if(cjbbutton.MouseClick())
                {
                    mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    actionPerformed(cjbbutton);
                }
            }
        }
    }

    public void drawBackground(int i, int j, float f)
    {
    	super.drawBackground(i, j, f);
    	
    	int i2 = (buttonslist.size() / colums - rows);
        int j2 = (int)((double)(currentScroll * (float)i2) + 0.5D);
        if(j2 < 0)
        {
            j2 = 0;
        }
        
        for(int k = 0; k < rows; k++)
        {
            for(int l = 0; l < colums; l++)
            {
                int i1 = l + (k + j2) * colums;
                if(i1 >= 0 && i1 < buttonslist.size())
                {
        	        buttonslist.get(i1).drawScreen(mc, i, j, 0, k * 10);
                }
            }
            
        }
    }

	public String name() {
		return "Mob Filter";
	}

	public boolean multiplayer() {
		return false;
	}
	
	public int id() {
		return 1026;
	}
	
	public int textureid() {
		return 5;
	}

	public CJB_GuiMain getGui(){
		return new CJB_GuiMobFilter();
	}
}
