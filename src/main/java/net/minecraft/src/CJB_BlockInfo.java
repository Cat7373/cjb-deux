package net.minecraft.src;

public class CJB_BlockInfo {
	public float x,y,z;
	public int lightlevel;
	public byte r,g,b;
	public CJB_BlockInfo(int bx, int by, int bz, int lightlevel){
		x = bx;
		y = by;
		z = bz;
		this.lightlevel = lightlevel;
	}
	
	public CJB_BlockInfo(int bx, int by, int bz, byte cr, byte cg, byte cb){
		x = bx;
		y = by;
		z = bz;
		r = cr;
		g = cg;
		b = cb;
	}
}
