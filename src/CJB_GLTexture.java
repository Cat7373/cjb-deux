package net.minecraft.src;


import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class CJB_GLTexture
{
    private static String DEFAULT_PACK = "/cjb/minimap/";
    private static String pack = DEFAULT_PACK;
    private static ArrayList list = new ArrayList();
    private static CJB_GLTexture missing = new CJB_GLTexture("missing.png", true, false);
    static final CJB_GLTexture ROUND_MAP = new CJB_GLTexture("minimapcircle.png", true, true);
    static final CJB_GLTexture ROUND_MAP_MASK = new CJB_GLTexture("circle.png", false, true);
    static final CJB_GLTexture SQUARE_MAP = new CJB_GLTexture("minimapsquare.png", true, true);
    static final CJB_GLTexture SQUARE_MAP_MASK = new CJB_GLTexture("minimapsquaremask.png", false, true);
    static final CJB_GLTexture ENTITY = new CJB_GLTexture("entity.png", true, true);
    static final CJB_GLTexture N = new CJB_GLTexture("n.png", true, true);
    static final CJB_GLTexture MMARROW = new CJB_GLTexture("arrow.png", true, true);
    static final CJB_GLTexture WAYPOINT = new CJB_GLTexture("waypoint.png", true, true);
    static final CJB_GLTexture WAYPOINTMARKER = new CJB_GLTexture("waypointmarker.png", true, true);
    static final CJB_GLTexture COLORWHEEL = new CJB_GLTexture("colorwheel.png", true, true);
    private final String fileName;
    private final boolean blur;
    private final boolean clamp;
    private int textureId;

    static void setPack(String var0)
    {
        if (!var0.equals(pack))
        {
            Iterator var1 = list.iterator();

            while (var1.hasNext())
            {
            	CJB_GLTexture var2 = (CJB_GLTexture)var1.next();
                var2.release();
            }

            pack = var0;
        }
    }

    private CJB_GLTexture(String var1, boolean var2, boolean var3)
    {
        this.fileName = var1;
        this.blur = var2;
        this.clamp = var3;
        list.add(this);
    }

    int[] getData()
    {
        BufferedImage var1 = read(this.fileName);
        int var2 = var1.getWidth();
        int var3 = var1.getHeight();
        int[] var4 = new int[var2 * var3];
        var1.getRGB(0, 0, var2, var3, var4, 0, var2);
        return var4;
    }

    void bind()
    {
        if (this.textureId == 0)
        {
            BufferedImage var1 = read(this.fileName);

            if (var1 == null)
            {
                this.textureId = this == missing ? -2 : -1;
            }
            else
            {
                this.textureId = GL11.glGenTextures();
                int var2 = var1.getWidth();
                int var3 = var1.getHeight();
                int[] var4 = new int[var2 * var3];
                var1.getRGB(0, 0, var2, var3, var4, 0, var2);
                CJB_GLTextureBufferedImage.createTexture(var4, var2, var3, this.textureId, this.blur, this.clamp);
            }
        }

        if (this.textureId == -2)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        }
        else
        {
            if (this.textureId == -1)
            {
                missing.bind();
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureId);
        }
    }

    void release()
    {
        if (this.textureId > 0)
        {
            GL11.glDeleteTextures(this.textureId);
        }

        this.textureId = 0;
    }

    public static BufferedImage read(String var0)
    {
        BufferedImage var1 = readImage(pack + var0);
        return var1 == null ? readImage(DEFAULT_PACK + var0) : var1;
    }

    public static BufferedImage readImage(String var0)
    {
        InputStream var1 = CJB_GLTexture.class.getResourceAsStream(var0);

        if (var1 == null)
        {
            return null;
        }

        Object var3;

        try
        {
            BufferedImage var2 = ImageIO.read(var1);
            return var2;
      	}
        catch (Exception var13)
       	{
        	var3 = null;
     	}
       	finally
       	{
           	try
          	{
              	var1.close();
           	}
          	catch (Exception var12){}
        }
        return (BufferedImage)var3;
    }
}
