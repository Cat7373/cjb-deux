package net.minecraft.src;

import java.awt.Point;

class CJB_StripCounter
{
    private int count;
    private Point[] points;

    CJB_StripCounter(int var1)
    {
        this.points = new Point[var1];
        int var2 = 0;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        this.points[0] = new Point(var2, var3);

        for (int var7 = 1; var7 < var1; ++var7)
        {
            switch (var4)
            {
                case 0:
                    --var3;
                    break;

                case 1:
                    ++var2;
                    break;

                case 2:
                    ++var3;
                    break;

                case 3:
                    --var2;
            }

            ++var5;

            if (var5 > var6)
            {
                var4 = var4 + 1 & 3;
                var5 = 0;

                if (var4 == 0 || var4 == 2)
                {
                    ++var6;
                }
            }

            this.points[var7] = new Point(var2, var3);
        }
    }

    Point next()
    {
        return this.points[this.count++];
    }

    int count()
    {
        return this.count;
    }

    void reset()
    {
        this.count = 0;
    }
}