package net.minecraft.src;

import java.util.Arrays;
import net.minecraft.src.Chunk;
import net.minecraft.src.EmptyChunk;
import net.minecraft.src.World;

public class CJB_ChunkCache
{
    private final int shift;
    private final int size;
    private final int mask;
    private Chunk[] cache;
    private int[] count;
    private boolean[] slime;

    public CJB_ChunkCache(int var1)
    {
        this.shift = var1;
        this.size = 1 << this.shift;
        this.mask = this.size - 1;
        this.cache = new Chunk[this.size * this.size];
        this.count = new int[this.size * this.size];
        this.slime = new boolean[this.size * this.size];
    }

    public Chunk get(World var1, int var2, int var3)
    {
        int var4 = var2 & this.mask | (var3 & this.mask) << this.shift;
        Object var5 = this.cache[var4];

        if (var5 == null || ((Chunk)var5).worldObj != var1 || !((Chunk)var5).isAtLocation(var2, var3) || --this.count[var4] < 0)
        {
            if (var1.blockExists(var2 << 4, 0, var3 << 4))
            {
                this.cache[var4] = (Chunk) (var5 = var1.getChunkFromChunkCoords(var2, var3));
                this.count[var4] = 128;
            }
            else if (var5 instanceof EmptyChunk && ((Chunk)var5).isAtLocation(var2, var3))
            {
                this.count[var4] = 8;
            }
            else
            {
                this.cache[var4] = (Chunk) (var5 = new EmptyChunk(var1, var2, var3));
                this.count[var4] = 8;
            }

            this.slime[var4] = var1.getSeed() != 0L && ((Chunk)var5).getRandomWithSeed(987234911L).nextInt(10) == 0;
        }

        return (Chunk)var5;
    }

    public boolean isSlimeSpawn(int var1, int var2)
    {
        return this.slime[var1 & this.mask | (var2 & this.mask) << this.shift];
    }

    public void clear()
    {
        Arrays.fill(this.cache, (Object)null);
        Arrays.fill(this.count, 0);
    }
}
