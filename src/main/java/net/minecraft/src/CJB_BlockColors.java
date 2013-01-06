package net.minecraft.src;

import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.src.Block;

public final class CJB_BlockColors
{
    private static final ArrayList list = new ArrayList();
    public static final CJB_BlockColors AIR_BLOCK = instance(0xff00ff);
    private static final int BLOCK_NUM = Block.blocksList.length;
    private static final CJB_BlockColors[] defaultColor = new CJB_BlockColors[BLOCK_NUM * 16 + 1];
    private static final CJB_BlockColors[] blockColor = new CJB_BlockColors[BLOCK_NUM * 16 + 1];
    private static final boolean[][] useMetadata = new boolean[BLOCK_NUM][16];
    public final int argb;
    public final CJB_TintType tintType;
    public final float alpha;
    public final float red;
    public final float green;
    public final float blue;

    private static void calcBlockColor(CJB_BlockColors[] ... blockcolorslist)
    {
    	loadBlockColors();
        Arrays.fill(blockColor, AIR_BLOCK);
        //Arrays.fill(useMetadata, false);
        
        for (int i = 0 ; i < BLOCK_NUM ; i++)
        	for (int j = 0 ; j < 16 ; j++)
        		useMetadata[i][j] = false;
        
        CJB.mmblockcolors = new ArrayList<CJB_Data>();
        CJB_Settings.loadData(CJB.mmblockcolors, "blockcolors", false);
        
        try {
	        for (CJB_Data data : CJB.mmblockcolors) {
	        	setDefaultColor(data.data, (int) data.posx, Integer.parseInt(data.Name, 16));
	        }
        } catch (Throwable e){}
        
        
        int id = 0;
        
        while (id < BLOCK_NUM)
        {
        	CJB_BlockColors[] var2 = null;
        	CJB_BlockColors var3 = null;
        	CJB_BlockColors[][] var4 = blockcolorslist;
            int blockcolorlistlength = blockcolorslist.length;
            int blockcolorlistindex = 0;

            while (true)
            {
                if (blockcolorlistindex < blockcolorlistlength)
                {
                	CJB_BlockColors[] var7 = var4[blockcolorlistindex];

                    if (var7[id << 4] == null)
                    {
                        ++blockcolorlistindex;
                        continue;
                    }

                    var2 = var7;
                    var3 = var7[id << 4];
                    blockColor[id << 4] = var3;
                }

                if (var2 != null)
                {
                    for (int var8 = 1; var8 < 16; ++var8)
                    {
                    	blockcolorlistlength = pointer(id, var8);

                        if (var2[blockcolorlistlength] != AIR_BLOCK)
                        {
                            blockColor[blockcolorlistlength] = var2[blockcolorlistlength];
                            useMetadata[id][0] = true;
                            useMetadata[id][var8] = true;
                        }
                        else
                        {
                            blockColor[blockcolorlistlength] = var3;
                        }
                    }
                }

                ++id;
                break;
            }
        }
    }

    public static void calcBlockColorD()
    {
        calcBlockColor(new CJB_BlockColors[][] {defaultColor});
    }

    public static boolean useMetadata(int id, int meta)
    {
        return useMetadata[id][meta];
    }

    public static CJB_BlockColors getBlockColor(int id, int meta)
    {
        return blockColor[pointer(id, meta)];
    }

    private static CJB_BlockColors instance(int var0)
    {
        return instance(var0, CJB_TintType.NONE);
    }

    private static CJB_BlockColors instance(int color, CJB_TintType tint)
    {
    	CJB_BlockColors var2 = new CJB_BlockColors(color, tint);
        int var3 = list.indexOf(var2);

        if (var3 == -1)
        {
            list.add(var2);
            return var2;
        }
		return (CJB_BlockColors)list.get(var3);
    }

    private static void setDefaultColor(int id, int meta, int color)
    {
        CJB_TintType tint = CJB_TintType.NONE;

        switch (id)
        {
            case 2:
            case 106:
            	tint = CJB_TintType.GRASS;
                break;

            case 8:
            case 9:
            case 79:
            	tint = CJB_TintType.WATER;
                break;

            case 18:
                int var4 = meta & 3;

                if (var4 == 0)
                {
                	tint = CJB_TintType.FOLIAGE;
                }

                if (var4 == 1)
                {
                	tint = CJB_TintType.PINE;
                }

                if (var4 == 2)
                {
                	tint = CJB_TintType.BIRCH;
                }

                if (var4 == 3)
                {
                	tint = CJB_TintType.FOLIAGE;
                }

                break;

            case 20:
            	tint = CJB_TintType.GLASS;
                break;

            case 31:
                if (meta == 1 || meta == 2)
                {
                	tint = CJB_TintType.TALL_GRASS;
                }
        }

        defaultColor[pointer(id, meta)] = instance(color, tint);
    }

    private static int pointer(int id, int meta)
    {
        return id << 4 | meta;
    }

    private CJB_BlockColors(int color, CJB_TintType tint)
    {
        if (tint == null)
        {
        	tint = CJB_TintType.NONE;
        }

        float a = (255) * 0.003921569F;
        float r = (color >> 16 & 255) * 0.003921569F;
        float g = (color >> 8 & 255) * 0.003921569F;
        float b = (color >> 0 & 255) * 0.003921569F;
        this.alpha = a;
        this.red = r;
        this.green = g;
        this.blue = b;
        this.argb = color;
        this.tintType = tint;
    }

    public String toString()
    {
        return String.format("%08X:%s", new Object[] {Integer.valueOf(this.argb), this.tintType});
    }

    public int hashCode()
    {
        return this.argb;
    }

    public boolean equals(Object var1)
    {
        return var1 instanceof CJB_BlockColors && this.equals((CJB_BlockColors)var1);
    }

    boolean equals(CJB_BlockColors var1)
    {
        return this.argb == var1.argb && this.tintType == var1.tintType;
    }
    
    private static void loadBlockColors() {
        Arrays.fill(defaultColor, AIR_BLOCK);
        setDefaultColor(1, 0, 0x686868);
        setDefaultColor(2, 0, 0xbbbbbb);
        setDefaultColor(3, 0, 0x79553a);
        setDefaultColor(4, 0, 0x959595);
        setDefaultColor(5, 0, 0xbc9862);
        setDefaultColor(6, 0, 0x436d12);
        setDefaultColor(6, 1, 0x323921);
        setDefaultColor(6, 2, 0x759553);
        setDefaultColor(7, 0, 0x333333);
        setDefaultColor(8, 0, 0x2a5eff);
        setDefaultColor(9, 0, 0x2a5eff);
        setDefaultColor(10, 0, 0xd96514);
        setDefaultColor(11, 0, 0xd96514);
        setDefaultColor(12, 0, 0xddd7a0);
        setDefaultColor(13, 0, 0x897f7e);
        setDefaultColor(14, 0, 0x908b7c);
        setDefaultColor(15, 0, 0x88827f);
        setDefaultColor(16, 0, 0x747373);
        setDefaultColor(17, 0, 0x675132);
        setDefaultColor(17, 1, 0x342919);
        setDefaultColor(17, 2, 0xc8c29f);
        setDefaultColor(17, 2, 0x795823);
        setDefaultColor(18, 0, 0x305a25);
        setDefaultColor(18, 1, 0x3a4c26);
        setDefaultColor(18, 2, 0x335133);
        setDefaultColor(18, 3, 0x197403);
        setDefaultColor(19, 0, 0xe5e54e);
        setDefaultColor(20, 0, 0xffffff);
        setDefaultColor(21, 0, 0x677087);
        setDefaultColor(22, 0, 0x1d47a6);
        setDefaultColor(23, 0, 0x585858);
        setDefaultColor(24, 0, 0xdbd29f);
        setDefaultColor(25, 0, 0x644432);
        setDefaultColor(26, 0, 0x9f4545);
        setDefaultColor(26, 1, 0x9f4545);
        setDefaultColor(26, 2, 0x9f4545);
        setDefaultColor(26, 3, 0x9f4545);
        setDefaultColor(26, 4, 0x9f4545);
        setDefaultColor(26, 5, 0x9f4545);
        setDefaultColor(26, 6, 0x9f4545);
        setDefaultColor(26, 7, 0x9f4545);
        setDefaultColor(26, 8, 0x9e6161);
        setDefaultColor(26, 9, 0x9e6161);
        setDefaultColor(26, 10, 0x9e6161);
        setDefaultColor(26, 11, 0x9e6161);
        setDefaultColor(26, 12, 0x9e6161);
        setDefaultColor(26, 13, 0x9e6161);
        setDefaultColor(26, 14, 0x9e6161);
        setDefaultColor(26, 15, 0x9e6161);
        setDefaultColor(27, 0, 0x806060);
        setDefaultColor(27, 1, 0x806060);
        setDefaultColor(27, 2, 0x806060);
        setDefaultColor(27, 3, 0x806060);
        setDefaultColor(27, 4, 0x806060);
        setDefaultColor(27, 5, 0x806060);
        setDefaultColor(27, 6, 0x806060);
        setDefaultColor(27, 7, 0x806060);
        setDefaultColor(27, 8, 0xd06060);
        setDefaultColor(27, 9, 0xd06060);
        setDefaultColor(27, 10, 0xd06060);
        setDefaultColor(27, 11, 0xd06060);
        setDefaultColor(27, 12, 0xd06060);
        setDefaultColor(27, 13, 0xd06060);
        setDefaultColor(27, 14, 0xd06060);
        setDefaultColor(27, 15, 0xd06060);
        setDefaultColor(28, 0, 0x776458);
        setDefaultColor(29, 0, 0x6d6d6d);
        setDefaultColor(29, 1, 0x8d9163);
        setDefaultColor(29, 2, 0x6a665e);
        setDefaultColor(29, 3, 0x6a665e);
        setDefaultColor(29, 4, 0x6a665e);
        setDefaultColor(29, 5, 0x6a665e);
        setDefaultColor(29, 8, 0x6d6d6d);
        setDefaultColor(29, 9, 0x8d9163);
        setDefaultColor(29, 10, 0x6a665e);
        setDefaultColor(29, 11, 0x6a665e);
        setDefaultColor(29, 12, 0x6a665e);
        setDefaultColor(29, 13, 0x6a665e);
        setDefaultColor(30, 0, 0xd9d9d9);
        setDefaultColor(31, 0, 0x7a4e19);
        setDefaultColor(31, 1, 0x508032);
        setDefaultColor(31, 2, 0x508032);
        setDefaultColor(32, 0, 0x7a4e19);
        setDefaultColor(33, 0, 0x6d6d6d);
        setDefaultColor(33, 1, 0x99815a);
        setDefaultColor(33, 2, 0x6a665e);
        setDefaultColor(33, 3, 0x6a665e);
        setDefaultColor(33, 4, 0x6a665e);
        setDefaultColor(33, 5, 0x6a665e);
        setDefaultColor(33, 8, 0x6d6d6d);
        setDefaultColor(33, 9, 0x99815a);
        setDefaultColor(33, 10, 0x6a665e);
        setDefaultColor(33, 11, 0x6a665e);
        setDefaultColor(33, 12, 0x6a665e);
        setDefaultColor(33, 13, 0x6a665e);
        setDefaultColor(34, 0, 0x99815a);
        setDefaultColor(34, 1, 0x99815a);
        setDefaultColor(34, 2, 0x99815a);
        setDefaultColor(34, 3, 0x99815a);
        setDefaultColor(34, 4, 0x99815a);
        setDefaultColor(34, 5, 0x99815a);
        setDefaultColor(34, 8, 0x99815a);
        setDefaultColor(34, 9, 0x8d9163);
        setDefaultColor(34, 10, 0x99815a);
        setDefaultColor(34, 11, 0x99815a);
        setDefaultColor(34, 12, 0x99815a);
        setDefaultColor(34, 13, 0x99815a);
        setDefaultColor(35, 0, 0xdddddd);
        setDefaultColor(35, 1, 0xe97e36);
        setDefaultColor(35, 2, 0xbd4ec8);
        setDefaultColor(35, 3, 0x678ad3);
        setDefaultColor(35, 4, 0xc1b41c);
        setDefaultColor(35, 5, 0x3abb2f);
        setDefaultColor(35, 6, 0xd8829a);
        setDefaultColor(35, 7, 0x424242);
        setDefaultColor(35, 8, 0x9da4a4);
        setDefaultColor(35, 9, 0x277494);
        setDefaultColor(35, 10, 0x8035c2);
        setDefaultColor(35, 11, 0x263299);
        setDefaultColor(35, 12, 0x55331b);
        setDefaultColor(35, 13, 0x374c18);
        setDefaultColor(35, 14, 0xa32c28);
        setDefaultColor(35, 15, 0x1a1717);
        setDefaultColor(37, 0, 0xf1f902);
        setDefaultColor(38, 0, 0xf7070f);
        setDefaultColor(39, 0, 0x916d55);
        setDefaultColor(40, 0, 0x9a171c);
        setDefaultColor(41, 0, 0xfefb5d);
        setDefaultColor(42, 0, 0xe9e9e9);
        setDefaultColor(43, 0, 0xa8a8a8);
        setDefaultColor(43, 1, 0xe5ddaf);
        setDefaultColor(43, 2, 0x94794a);
        setDefaultColor(43, 3, 0x828282);
        setDefaultColor(43, 4, 0x9b6d61);
        setDefaultColor(43, 5, 0x7a7a7a);
        setDefaultColor(44, 0, 0xa8a8a8);
        setDefaultColor(44, 1, 0xe5ddaf);
        setDefaultColor(44, 2, 0x94794a);
        setDefaultColor(44, 3, 0x828282);
        setDefaultColor(44, 4, 0x9b6d61);
        setDefaultColor(44, 5, 0x7a7a7a);
        setDefaultColor(45, 0, 0x9b6d61);
        setDefaultColor(46, 0, 0xdb441a);
        setDefaultColor(47, 0, 0xb4905a);
        setDefaultColor(48, 0, 0x1f471f);
        setDefaultColor(49, 0, 0x13121d);
        setDefaultColor(50, 0, 0xffd800);
        setDefaultColor(51, 0, 0xc05a01);
        setDefaultColor(52, 0, 0x265f87);
        setDefaultColor(53, 0, 0xbc9862);
        setDefaultColor(54, 0, 0x8f691d);
        setDefaultColor(55, 0, 0x800000);
        setDefaultColor(55, 1, 0xc80000);
        setDefaultColor(55, 2, 0xcc0000);
        setDefaultColor(55, 3, 0xd00000);
        setDefaultColor(55, 4, 0xd40000);
        setDefaultColor(55, 5, 0xd80000);
        setDefaultColor(55, 6, 0xdc0000);
        setDefaultColor(55, 7, 0xe00000);
        setDefaultColor(55, 8, 0xe40000);
        setDefaultColor(55, 9, 0xe80000);
        setDefaultColor(55, 10, 0xec0000);
        setDefaultColor(55, 11, 0xf00000);
        setDefaultColor(55, 12, 0xf40000);
        setDefaultColor(55, 13, 0xf80000);
        setDefaultColor(55, 14, 0xfc0000);
        setDefaultColor(55, 15, 0xff0000);
        setDefaultColor(56, 0, 0x818c8f);
        setDefaultColor(57, 0, 0x60e0e0);
        setDefaultColor(58, 0, 0x855935);
        setDefaultColor(59, 0, 0x00990f);
        setDefaultColor(59, 1, 0x12ab0f);
        setDefaultColor(59, 2, 0x1c890b);
        setDefaultColor(59, 3, 0x258b08);
        setDefaultColor(59, 4, 0x2e8007);
        setDefaultColor(59, 5, 0x417a07);
        setDefaultColor(59, 6, 0x4f7607);
        setDefaultColor(59, 7, 0x566507);
        setDefaultColor(60, 0, 0x734b2d);
        setDefaultColor(60, 1, 0x6e4629);
        setDefaultColor(60, 2, 0x694225);
        setDefaultColor(60, 3, 0x643d21);
        setDefaultColor(60, 4, 0x5f391d);
        setDefaultColor(60, 5, 0x5a3519);
        setDefaultColor(60, 6, 0x553015);
        setDefaultColor(60, 7, 0x502c11);
        setDefaultColor(60, 8, 0x4c280e);
        setDefaultColor(61, 0, 0x747474);
        setDefaultColor(62, 0, 0x808080);
        setDefaultColor(63, 0, 0xb49055);
        setDefaultColor(64, 0, 0x866532);
        setDefaultColor(65, 0, 0x785e34);
        setDefaultColor(66, 0, 0x776a55);
        setDefaultColor(67, 0, 0x9e9e9e);
        setDefaultColor(68, 0, 0xb49055);
        setDefaultColor(69, 0, 0x695433);
        setDefaultColor(70, 0, 0x8f8f8f);
        setDefaultColor(71, 0, 0xc1c1c1);
        setDefaultColor(72, 0, 0xbc9862);
        setDefaultColor(73, 0, 0x957861);
        setDefaultColor(74, 0, 0x957861);
        setDefaultColor(75, 0, 0x581d12);
        setDefaultColor(76, 0, 0xa12413);
        setDefaultColor(77, 0, 0x747474);
        setDefaultColor(78, 0, 0xebefef);
        setDefaultColor(79, 0, 0x7cacfd);
        setDefaultColor(80, 0, 0xf0f0f0);
        setDefaultColor(81, 0, 0x108020);
        setDefaultColor(82, 0, 0x9ea3b0);
        setDefaultColor(83, 0, 0x93bf64);
        setDefaultColor(84, 0, 0x6a4936);
        setDefaultColor(85, 0, 0xbc9862);
        setDefaultColor(86, 0, 0xbf7515);
        setDefaultColor(87, 0, 0x6b3433);
        setDefaultColor(88, 0, 0x544033);
        setDefaultColor(89, 0, 0xc08f46);
        setDefaultColor(90, 0, 0x732486);
        setDefaultColor(91, 0, 0xc4971e);
        setDefaultColor(92, 0, 0xe3cccd);
        setDefaultColor(93, 0, 0x979393);
        setDefaultColor(94, 0, 0xc09393);
        setDefaultColor(95, 0, 0x8f691d);
        setDefaultColor(96, 0, 0x7e5d2d);
        setDefaultColor(96, 1, 0x7e5d2d);
        setDefaultColor(96, 2, 0x7e5d2d);
        setDefaultColor(96, 3, 0x7e5d2d);
        setDefaultColor(96, 4, 0x7e5d2d);
        setDefaultColor(96, 5, 0x7e5d2d);
        setDefaultColor(96, 6, 0x7e5d2d);
        setDefaultColor(96, 7, 0x7e5d2d);
        setDefaultColor(97, 0, 0x686868);
        setDefaultColor(97, 1, 0x959595);
        setDefaultColor(97, 2, 0x7a7a7a);
        setDefaultColor(98, 0, 0x7a7a7a);
        setDefaultColor(98, 1, 0x72776a);
        setDefaultColor(98, 2, 0x767676);
        setDefaultColor(99, 0, 0xcaab78);
        setDefaultColor(99, 1, 0x8d6a53);
        setDefaultColor(99, 2, 0x8d6a53);
        setDefaultColor(99, 3, 0x8d6a53);
        setDefaultColor(99, 4, 0x8d6a53);
        setDefaultColor(99, 5, 0x8d6a53);
        setDefaultColor(99, 6, 0x8d6a53);
        setDefaultColor(99, 7, 0x8d6a53);
        setDefaultColor(99, 8, 0x8d6a53);
        setDefaultColor(99, 9, 0x8d6a53);
        setDefaultColor(99, 10, 0xcaab78);
        setDefaultColor(100, 0, 0xcaab78);
        setDefaultColor(100, 1, 0xb62524);
        setDefaultColor(100, 2, 0xb62524);
        setDefaultColor(100, 3, 0xb62524);
        setDefaultColor(100, 4, 0xb62524);
        setDefaultColor(100, 5, 0xb62524);
        setDefaultColor(100, 6, 0xb62524);
        setDefaultColor(100, 7, 0xb62524);
        setDefaultColor(100, 8, 0xb62524);
        setDefaultColor(100, 9, 0xb62524);
        setDefaultColor(100, 10, 0xcaab78);
        setDefaultColor(101, 0, 0x6d6c6a);
        setDefaultColor(102, 0, 0xffffff);
        setDefaultColor(103, 0, 0x979924);
        setDefaultColor(104, 0, 0x009900);
        setDefaultColor(104, 1, 0x139402);
        setDefaultColor(104, 2, 0x269004);
        setDefaultColor(104, 3, 0x398b07);
        setDefaultColor(104, 4, 0x4d8609);
        setDefaultColor(104, 5, 0x60810c);
        setDefaultColor(104, 6, 0x737c0e);
        setDefaultColor(104, 7, 0x877810);
        setDefaultColor(105, 0, 0x009900);
        setDefaultColor(105, 1, 0x139402);
        setDefaultColor(105, 2, 0x269004);
        setDefaultColor(105, 3, 0x398b07);
        setDefaultColor(105, 4, 0x4d8609);
        setDefaultColor(105, 5, 0x60810c);
        setDefaultColor(105, 6, 0x737c0e);
        setDefaultColor(105, 7, 0x877810);
        setDefaultColor(106, 0, 0x1b7b03);
        setDefaultColor(107, 0, 0xbc9862);
        setDefaultColor(108, 0, 0x9b6d61);
        setDefaultColor(109, 0, 0x7a7a7a);
        setDefaultColor(110, 0, 0x666666);
        setDefaultColor(111, 0, 0x208030);
        setDefaultColor(112, 0, 0x30181c);
        setDefaultColor(113, 0, 0x30181c);
        setDefaultColor(114, 0, 0x30181c);
        setDefaultColor(115, 0, 0x70081c);
        setDefaultColor(115, 1, 0x6a0001);
        setDefaultColor(115, 2, 0x650000);
        setDefaultColor(116, 0, 0xa22b29);
        setDefaultColor(117, 0, 0x5a5a5a);
        setDefaultColor(118, 0, 0x2d2d2d);
        setDefaultColor(119, 0, 0x000000);
        setDefaultColor(120, 0, 0xb3ae78);
        setDefaultColor(121, 0, 0xf4f9bd);
        setDefaultColor(122, 0, 0x2d0133);
        setDefaultColor(123, 0, 0x50413a);
        setDefaultColor(124, 0, 0xf1d1af);
        
        setDefaultColor(200, 0, 0xe9e9e9);
        setDefaultColor(201, 0, 0xfefb5d);
        setDefaultColor(202, 0, 0xe9e9e9);
        setDefaultColor(203, 0, 0xfefb5d);
        setDefaultColor(204, 0, 0x897f7e);
        setDefaultColor(205, 0, 0x12269b);
        setDefaultColor(206, 0, 0xe9e9e9);
        setDefaultColor(207, 0, 0xbc9862);
        setDefaultColor(208, 0, 0x959595);
    }
}