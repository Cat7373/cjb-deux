package net.minecraft.src;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.ImageObserver;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.minecraft.client.renderer.GLAllocation;

import org.lwjgl.opengl.GL11;

public class CJB_GLTextureBufferedImage extends BufferedImage
{
    private static final ByteBuffer buffer = GLAllocation.createDirectByteBuffer(262144);
    private static final HashMap registerImage = new HashMap();
    private static final Lock lock = new ReentrantLock();
    public byte[] data;
    private int register;
    private boolean magFiltering;
    private boolean minFiltering;
    private boolean clampTexture;

    private CJB_GLTextureBufferedImage(ColorModel var1, WritableRaster var2, boolean var3, Hashtable var4)
    {
        super(var1, var2, var3, var4);
        this.data = ((DataBufferByte)var2.getDataBuffer()).getData();
    }

    public static CJB_GLTextureBufferedImage create(int var0, int var1)
    {
        ColorSpace var2 = ColorSpace.getInstance(1000);
        int[] var3 = new int[] {8, 8, 8, 8};
        int[] var4 = new int[] {0, 1, 2, 3};
        ComponentColorModel var5 = new ComponentColorModel(var2, var3, true, false, 3, 0);
        WritableRaster var6 = Raster.createInterleavedRaster(0, var0, var1, var0 * 4, 4, var4, (Point)null);
        return new CJB_GLTextureBufferedImage(var5, var6, false, (Hashtable)null);
    }

    public static CJB_GLTextureBufferedImage create(BufferedImage var0)
    {
    	CJB_GLTextureBufferedImage var1 = create(var0.getWidth(), var0.getHeight());
        Graphics var2 = var1.getGraphics();
        var2.drawImage(var0, 0, 0, (ImageObserver)null);
        var2.dispose();
        return var1;
    }

    public int register()
    {
        lock.lock();
        int var2;

        try
        {
            int var1;

            if (this.register == 0)
            {
                this.register = GL11.glGenTextures();
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.register);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, this.minFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, this.magFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
                var1 = this.clampTexture ? 10496 : 10497;
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, var1);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, var1);
                buffer.clear();
                buffer.put(this.data);
                buffer.flip();
                GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, this.getWidth(), this.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
                registerImage.put(Integer.valueOf(this.register), this);
                var2 = this.register;
                return var2;
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.register);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, this.minFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, this.magFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
            var1 = this.clampTexture ? 10496 : 10497;
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, var1);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, var1);
            buffer.clear();
            buffer.put(this.data);
            buffer.flip();
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, this.getWidth(), this.getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            var2 = this.register;
        }
        finally
        {
            lock.unlock();
        }

        return var2;
    }

    public boolean bind()
    {
        lock.lock();
        boolean var1;

        try
        {
            if (this.register == 0)
            {
                var1 = false;
                return var1;
            }

            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.register);
            var1 = true;
        }
        finally
        {
            lock.unlock();
        }

        return var1;
    }

    public void unregister()
    {
        lock.lock();

        try
        {
            if (this.register == 0)
            {
                return;
            }

            GL11.glDeleteTextures(this.register);
            this.register = 0;
            registerImage.remove(Integer.valueOf(this.register));
        }
        finally
        {
            lock.unlock();
        }
    }

    public static void unregister(int var0)
    {
        lock.lock();

        try
        {
        	CJB_GLTextureBufferedImage var1 = (CJB_GLTextureBufferedImage)registerImage.get(Integer.valueOf(var0));

            if (var1 != null)
            {
                var1.unregister();
            }
        }
        finally
        {
            lock.unlock();
        }
    }

    public void setMagFilter(boolean var1)
    {
        this.magFiltering = var1;
    }

    public void setMinFilter(boolean var1)
    {
        this.minFiltering = var1;
    }

    public int getId()
    {
        return this.register;
    }

    public boolean getMagFilter()
    {
        return this.magFiltering;
    }

    public boolean getMinFilter()
    {
        return this.minFiltering;
    }

    public void setClampTexture(boolean var1)
    {
        this.clampTexture = var1;
    }

    public boolean isClampTexture()
    {
        return this.clampTexture;
    }

    public void setRGBA(int var1, int var2, byte var3, byte var4, byte var5, byte var6)
    {
        int var7 = (var2 * this.getWidth() + var1) * 4;
        this.data[var7++] = var3;
        this.data[var7++] = var4;
        this.data[var7++] = var5;
        this.data[var7] = var6;
    }

    public void setRGB(int var1, int var2, byte var3, byte var4, byte var5)
    {
        int var6 = (var2 * this.getWidth() + var1) * 4;
        this.data[var6++] = var3;
        this.data[var6++] = var4;
        this.data[var6++] = var5;
        this.data[var6] = -1;
    }

    public void setRGB(int var1, int var2, int var3)
    {
        int var4 = (var2 * this.getWidth() + var1) * 4;
        this.data[var4++] = (byte)(var3 >> 16);
        this.data[var4++] = (byte)(var3 >> 8);
        this.data[var4++] = (byte)(var3 >> 0);
        this.data[var4] = (byte)(var3 >> 24);
    }

    public static void createTexture(int[] var0, int var1, int var2, int var3, boolean var4, boolean var5)
    {
        byte[] var6 = new byte[var1 * var2 * 4];
        int var7 = 0;
        int var8 = var0.length;

        for (int var9 = 0; var7 < var8; ++var7)
        {
            int var10 = var0[var7];
            var6[var9++] = (byte)(var10 >> 16);
            var6[var9++] = (byte)(var10 >> 8);
            var6[var9++] = (byte)(var10 >> 0);
            var6[var9++] = (byte)(var10 >> 24);
        }

        createTexture(var6, var1, var2, var3, var4, var5);
    }

    public static void createTexture(byte[] var0, int var1, int var2, int var3, boolean var4, boolean var5)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, var3);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var4 ? GL11.GL_LINEAR : GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var4 ? GL11.GL_LINEAR : GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, var5 ? GL11.GL_CLAMP : GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, var5 ? GL11.GL_CLAMP : GL11.GL_REPEAT);
        buffer.clear();
        buffer.put(var0);
        buffer.flip();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, var1, var2, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    }
}
