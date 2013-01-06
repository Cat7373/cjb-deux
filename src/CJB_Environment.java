package net.minecraft.src;

import java.util.concurrent.ConcurrentLinkedQueue;

class CJB_Environment
{
    private static ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
    private static final int foliageColorPine = ColorizerFoliage.getFoliageColorPine();
    private static final int foliageColorBirch = ColorizerFoliage.getFoliageColorBirch();
    private static CJB_Environment[] envCache = new CJB_Environment[262144];
    private static World world;
    private int x;
    private int z;
    private int grassColor;
    private int foliageColor;
    private BiomeGenBase biome;
    private float temperature;
    private float humidity;
    private boolean valid;
    
    public CJB_Environment (){}

    private boolean isLocation(int var1, int var2)
    {
        return this.x == var1 && this.z == var2;
    }

    private void set(int var1, int var2, float var3, float var4, BiomeGenBase var5)
    {
        this.x = var1;
        this.z = var2;
        this.grassColor = ColorizerGrass.getGrassColor(var3, var4);
        this.foliageColor = ColorizerFoliage.getFoliageColor(var3, var4);
        this.temperature = var3;
        this.humidity = var4;
        this.biome = var5;
    }

    public int getGrassColor()
    {
        return this.grassColor;
    }

    public int getFoliageColor()
    {
        return this.foliageColor;
    }

    public static int getFoliageColorPine()
    {
        return foliageColorPine;
    }

    public static int getFoliageColorBirch()
    {
        return foliageColorBirch;
    }

    public BiomeGenBase getBiome()
    {
        return this.biome;
    }

    public float getTemperature()
    {
        return this.temperature;
    }

    public float getHumidity()
    {
        return this.humidity;
    }

    public static int calcGrassColor(BiomeGenBase var0, int var1)
    {
        return var0 == BiomeGenBase.swampland ? (var1 & 16711422) + 5115470 >> 1 : var1;
    }

    public static int calcFoliageColor(BiomeGenBase var0, int var1)
    {
        return var0 == BiomeGenBase.swampland ? (var1 & 16711422) + 5115470 >> 1 : var1;
    }

    static void calcEnvironment()
    {
        if (Thread.currentThread() == CJB_Minimap.instance.mcThread)
        {
            while (true)
            {
            	CJB_Environment var0 = (CJB_Environment)queue.poll();

                if (var0 == null)
                {
                    return;
                }

                calcEnvironment(var0);
            }
        }
    }

    public static CJB_Environment getEnvironment(int var0, int var1, Thread var2)
    {
        int var3 = (var0 & 511) << 9 | var1 & 511;
        CJB_Environment var4 = envCache[var3];

        if (!var4.isLocation(var0, var1))
        {
            var4.valid = false;

            if (var2 == CJB_Minimap.instance.mcThread)
            {
                calcEnvironment(var0, var1, var4);
            }
            else
            {
                var4.set(var0, var1, 0.5F, 1.0F, BiomeGenBase.plains);
                queue.offer(var4);
            }
        }

        return var4;
    }

    public static CJB_Environment getEnvironment(Chunk var0, int var1, int var2, Thread var3)
    {
        return getEnvironment(var0.xPosition * 16 + var1, var0.zPosition * 16 + var2, var3);
    }

    private static void calcEnvironment(CJB_Environment var0)
    {
        calcEnvironment(var0.x, var0.z, var0);
    }

    private static void calcEnvironment(int var0, int var1, CJB_Environment var2)
    {
    	if (world == null) return;
        if (!var2.valid)
        {
            BiomeGenBase var3 = world.getBiomeGenForCoords(var0, var1);
            float var4 = var3.temperature < 0.0F ? 0.0F : (var3.temperature > 1.0F ? 1.0F : var3.temperature);
            float var5 = var3.rainfall < 0.0F ? 0.0F : (var3.rainfall > 1.0F ? 1.0F : var3.rainfall);
            var2.set(var0, var1, var4, var5, var3);
            var2.valid = true;
        }
    }

    public static void setWorld(World var0)
    {
        world = var0;
        queue.clear();

        for (int var1 = 0; var1 < envCache.length; ++var1)
        {
            envCache[var1].x = Integer.MIN_VALUE;
            envCache[var1].z = Integer.MIN_VALUE;
        }
    }

    static
    {
        for (int var0 = 0; var0 < envCache.length; ++var0)
        {
            envCache[var0] = new CJB_Environment();
        }
    }
}
