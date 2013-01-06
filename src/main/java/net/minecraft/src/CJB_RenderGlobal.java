package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

public class CJB_RenderGlobal
{
	private Minecraft mc;
	private double posX, posY, posZ;
	private EntityPlayer player;
	private World world;
	private List<CJB_BlockInfo> blocks;
	private List<CJB_BlockInfo> ores;
	private RenderManager renderManager;
	private Long timer = System.currentTimeMillis();
	private Long timerore = System.currentTimeMillis();
	
	public CJB_RenderGlobal(Minecraft par1Minecraft)
    {
		mc = par1Minecraft;
		blocks = new ArrayList<CJB_BlockInfo>();
		renderManager = RenderManager.instance;
    }

	public void renderCJB(float par1)
    {
    	player = mc.thePlayer;
    	world = mc.theWorld;
    	OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 0f);
    	float f = par1;
    	
    	if (renderManager == null) {
    		renderManager = RenderManager.instance;
    		return;
    	}
    	
    	if(player == null || world == null || mc.entityRenderer == null) return;
        
    	try {
            // update player position
    		posX = player.posX; 
    		posY = player.posY; 
    		posZ = player.posZ;
            // draw in 3d
    		float px = (float)posX; 
    		float py = (float)posY; 
    		float pz = (float)posZ;
         	float mx = (float)player.prevPosX;
    		float my = (float)player.prevPosY;
    		float mz = (float)player.prevPosZ;
         	float x = mx + ( px - mx ) * f;
    		float y = my + ( py - my ) * f;
    		float z = mz + ( pz - mz ) * f;
        	// handle mods
    		if (CJB.mmwaypoints != null)
    			try { drawWaypoints(x,y,z); } catch(Exception error) { error.printStackTrace(); }
    		if (CJB.showspawnareas)
    			try { drawSpawnAreas(x,y,z); } catch(Exception error) { error.printStackTrace(); }
    		
    		if (CJB.showores && CJB.pmxray)
    			try { drawOreBlocks(x,y,z); } catch(Exception error) { error.printStackTrace(); }
    		
    		if (CJB.modmeasures)
    			drawMeasures(x,y,z);
    		
     	} catch(Exception error) { 
        	error.printStackTrace();
    	}
        
    	if (mc.isSingleplayer() && CJB.showmobhealth) {
    	
	        renderManager.livingPlayer = player;
			List list = mc.theWorld.getLoadedEntityList();
			
			for (int i = 0 ; i < list.size() ; i++)
			{
				if (list.get(i) instanceof EntityLiving)
				{
					EntityLiving ent = (EntityLiving) list.get(i);
					
					if (!ent.isEntityAlive() || ent == CJB.plr) continue;
			        List list1 = CJB.w.getLoadedEntityList();
					
					for (int j = 0 ; j < list1.size() ; j++)
					{
						if (list1.get(j) instanceof EntityLiving)
						{
							EntityLiving ent1 = (EntityLiving) list1.get(j);
							
							if (ent.entityId != ent1.entityId) continue;
							
							double d = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)f;
					        double d1 = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)f;
					        double d2 = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)f;
					        				
							renderLivingLabel(ent1, ent,"", d - RenderManager.renderPosX, d1 - RenderManager.renderPosY, d2 - RenderManager.renderPosZ, 24,false, 0, 0);
							break;
						}
					}
				}
			}
    	}
    }
    
    public void drawSpawnAreas(float x, float y, float z) {
    	float mx, my, mz;
        if(System.currentTimeMillis() - timer > 250)
        {
        	timer = System.currentTimeMillis();
        	getSpawnAreas(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
        }
        for(int i=0;i<blocks.size();i++) {
        	
        	CJB_BlockInfo bi= blocks.get(i);
        	if (bi.lightlevel > 7)
        		GL11.glColor4f(0, 1, 0, 0.2f);
        	else
        		GL11.glColor4f(1, 0, 0, 0.2f);
            mx = bi.x - x;
            my = bi.y - y;
            mz = bi.z - z;
            
            drawAreaPanel(mx, my, mz);
        }
    }
    
    public void drawOreBlocks(float x, float y, float z) {
    	float mx, my, mz;
    	if(System.currentTimeMillis() - timerore > 500)
        {
        	timerore = System.currentTimeMillis();
        	getOreBlocks(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ));
        }
        for(int i=0;i<ores.size();i++) {
        	
        	CJB_BlockInfo bi= ores.get(i);
            mx = bi.x - x;
            my = bi.y - y;
            mz = bi.z - z;
            
            drawBlock(mx, my, mz, bi.r, bi.g, bi.b);
        }
    }
    
    public void drawWaypoints(float x, float y, float z) {
    	float mx, my, mz;
    	
    	if (CJB.mmshowallwp) {
    		for (CJB_Data way : CJB.mmwaypoints) {
    			mx = (float) (way.posx - x);
                my = (float) (way.posy - y);
                mz = (float) (way.posz - z);
                
                double d = way.posx+0.5d;
    	        double d1 = way.posy-1.0d;
    	        double d2 = way.posz+0.5d;
                
                drawWaypoint(mx, my, mz, d-RenderManager.renderPosX, d1-RenderManager.renderPosY, d2-RenderManager.renderPosZ, way.color);
    			renderLivingLabel(null, null, way.Name, d - RenderManager.renderPosX, d1 - RenderManager.renderPosY, d2 - RenderManager.renderPosZ, 999, false, 0, 0);
    		}
    	} else {
    	CJB_Data way = CJB_GuiMinimap.waypoint;
    	
        if (way == null) return;
        	
            mx = (float) (way.posx - x);
            my = (float) (way.posy - y);
            mz = (float) (way.posz - z);
            
            double d = way.posx+0.5d;
	        double d1 = way.posy-1.0d;
	        double d2 = way.posz+0.5d;
            
            drawWaypoint(mx, my, mz, d-RenderManager.renderPosX, d1-RenderManager.renderPosY, d2-RenderManager.renderPosZ, way.color);
			renderLivingLabel(null, null, way.Name, d - RenderManager.renderPosX, d1 - RenderManager.renderPosY, d2 - RenderManager.renderPosZ, 999, false, 0, 0);
    	}
    }
    
    public void drawMeasures(float x, float y, float z) {
    	float mx, my, mz;  
    	
    	CJB_Data temp = null;
    	
    	for (int i = 0 ; i < CJB.measures.size() ; i++)
    	{
    		CJB_Data mes = CJB.measures.get(i);
    		mx = (float) (mes.posx - x);
            my = (float) (mes.posy - y);
            mz = (float) (mes.posz - z);
    		
            double d = mes.posx + 0.5f;
	        double d1 = mes.posy - 1.5f;
	        double d2 = mes.posz + 0.5f;
	        
	        drawSphere(mx+0.5f, my+0.5f, mz+0.5f);
	        
	        if (temp != null) {
	        	int tx, ty, tz = 0;
	        	
	        	tx = (int) Math.abs(temp.posx - mes.posx) + 1;
	        	ty = (int) Math.abs(temp.posy - mes.posy) + 1;
	        	tz = (int) Math.abs(temp.posz - mes.posz) + 1;
	        	
	        	renderLivingLabel(null, null, "x: " + tx, d - RenderManager.renderPosX, d1 - RenderManager.renderPosY, d2 - RenderManager.renderPosZ, 999, true, 20, -10);
	        	renderLivingLabel(null, null, "y: " + ty, d - RenderManager.renderPosX, d1 - RenderManager.renderPosY, d2 - RenderManager.renderPosZ, 999, true, 20, 0);
	        	renderLivingLabel(null, null, "z: " + tz, d - RenderManager.renderPosX, d1 - RenderManager.renderPosY, d2 - RenderManager.renderPosZ, 999, true, 20, 10);
	        }
	        temp = mes;
    	}
    	
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
    	GL11.glDepthMask(true);
        GL11.glDepthRange(1, -1);
    	GL11.glColor4f(0, 0, 1, 1f);
        GL11.glBegin(GL11.GL_LINES);
        for (int i = 0 ; i < CJB.measures.size() ; i++)
    	{
        	CJB_Data mes = CJB.measures.get(i);
        	
        	mx = (float) (mes.posx - x);
            my = (float) (mes.posy - y);
            mz = (float) (mes.posz - z);
            
        	
        	GL11.glVertex3f(mx+0.5f, my+0.5f, mz+0.5f);
        	if (i != 0)
        		GL11.glVertex3f(mx+0.5f, my+0.5f, mz+0.5f);
    	}
        GL11.glEnd();
        GL11.glDepthRange(0, 1);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }
    
    private void getOreBlocks(int pX, int pY, int pZ) {
    	ores = new ArrayList<CJB_BlockInfo>();
    	
    	int renderWidth = 128 >> CJB.renderWidth;
    	int renderHeight = 128 >> CJB.renderHeight;
    	
    	for(int x=pX-renderWidth;x<pX+renderWidth;x++) for(int y=pY-renderHeight;y<pY+renderHeight;y++) for(int z=pZ-renderWidth;z<pZ+renderWidth;z++) {
    		
    		int i = world.getBlockId(x, y, z);
    		if (i == 0) continue;
    		
    		if (CJB.showcoal && i == Block.oreCoal.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)20,(byte)20,(byte)20));
    		if (CJB.showiron && i == Block.oreIron.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)186,(byte)186,(byte)186));
    		if (CJB.showgold && i == Block.oreGold.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)255,(byte)241,(byte)68));
    		if (CJB.showdiamond && i == Block.oreDiamond.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)150,(byte)233,(byte)229));
    		if (CJB.showlapis && i == Block.oreLapis.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)15,(byte)38,(byte)184));
    		if (CJB.showredstone && i == Block.oreRedstone.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)255,(byte)0,(byte)0));
    		if (CJB.showredstone && i == Block.oreRedstoneGlowing.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)255,(byte)0,(byte)0));
    		if (CJB.showemerald && i == Block.oreEmerald.blockID)
    			ores.add(new CJB_BlockInfo(x,y,z, (byte)169,(byte)139,(byte)34));
    	}
    }
    
    private void getSpawnAreas(int pX, int pY, int pZ) {
    	blocks = new ArrayList<CJB_BlockInfo>();
    	
    	int renderWidth = 64 >> CJB.renderWidth;
    	int renderHeight = 16 >> CJB.renderHeight;
    	
    	for(int x=pX-renderWidth;x<pX+renderWidth;x++) for(int y=pY-renderHeight;y<pY+renderHeight;y++) for(int z=pZ-renderWidth;z<pZ+renderWidth;z++) {
    		
    		Block blk = Block.blocksList[world.getBlockId(x, y, z)];
    		if (blk == null || !blk.isOpaqueCube()) continue;
    		
    		double d = player.posX;
    		double d1 = player.posY;
    		double d2 = player.posZ;
    		player.setPosition(x+0.5d, y+2.8d, z+0.5d);
    		boolean flag = world.getCollidingBoundingBoxes(player, player.boundingBox).size() != 0 || world.isAnyLiquid(player.boundingBox);
    		player.setPosition(d, d1, d2);
    		
    		if (flag) continue;
    		
    		blocks.add(new CJB_BlockInfo(x,y+1,z, world.getChunkFromBlockCoords(x, z).getBlockLightValue(x & 0xf, y+1, z & 0xf, CJB.useskylight ? world.skylightSubtracted : 16)));
    	}
    }
    
    private void drawSphere(float x, float y, float z)
    {
    	Sphere sphere = new Sphere();
        RenderHelper.enableStandardItemLighting();
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        GL11.glDepthMask(true);
        GL11.glDepthRange(1, -1);
        
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
			GL11.glColor4f(0.0f, 0.0f, 1.0f, 1f);
			sphere.draw(0.2f, 20, 20);
		GL11.glPopMatrix();
		
		RenderHelper.disableStandardItemLighting();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthRange(0, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_TEXTURE_GEN_S);
		GL11.glDisable(GL11.GL_BLEND);
    }
    
    private void drawBlock(float x, float y, float z, byte r, byte g, byte b)
    {
    	//GL11.glEnable(GL11.GL_LIGHTING);
    	
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        
        float f1 = 0.05f;
        float f2 = 0.95f;
        
        GL11.glColor4ub(r, g, b, (byte)100);
        GL11.glBegin(GL11.GL_QUADS);
	        //FRONT
		        GL11.glVertex3f(x+f1,y+f1,z+f2);
		        GL11.glVertex3f(x+f2,y+f1,z+f2); 
		        GL11.glVertex3f(x+f2,y+f2,z+f2);
		        GL11.glVertex3f(x+f1,y+f2,z+f2);
	        
	        //BACK
		        GL11.glVertex3f(x+f1,y+f1,z+f1);
		        GL11.glVertex3f(x+f1,y+f2,z+f1); 
		        GL11.glVertex3f(x+f2,y+f2,z+f1);
		        GL11.glVertex3f(x+f2,y+f1,z+f1);
    		
	        //TOP
		        GL11.glVertex3f(x+f1,y+f2,z+f1);
		        GL11.glVertex3f(x+f1,y+f2,z+f2); 
		        GL11.glVertex3f(x+f2,y+f2,z+f2);
		        GL11.glVertex3f(x+f2,y+f2,z+f1);
	        
	        //BOTTON
		        GL11.glVertex3f(x+f1,y+f1,z+f1);
		        GL11.glVertex3f(x+f2,y+f1,z+f1); 
		        GL11.glVertex3f(x+f2,y+f1,z+f2);
		        GL11.glVertex3f(x+f1,y+f1,z+f2);
	        //RIGHT
	        GL11.glVertex3f(x+f2,y+f1,z+f1);
	        GL11.glVertex3f(x+f2,y+f2,z+f1); 
	        GL11.glVertex3f(x+f2,y+f2,z+f2);
	        GL11.glVertex3f(x+f2,y+f1,z+f2);
	        //LEFT
	        GL11.glVertex3f(x+f1,y+f1,z+f1);
	        GL11.glVertex3f(x+f1,y+f1,z+f2); 
	        GL11.glVertex3f(x+f1,y+f2,z+f2);
	        GL11.glVertex3f(x+f1,y+f2,z+f1);
        GL11.glEnd();
        
        f1 = 0.00f;
        f2 = 1.00f;
        
        if (CJB.drawlines) {
	        GL11.glColor4f(1, 1, 1, 0.2f);
	        GL11.glBegin(GL11.GL_LINES);
	        
		        GL11.glVertex3f(x+f1,y+f1,z+f1); GL11.glVertex3f(x+f1,y+f1,z+f2); 
		        GL11.glVertex3f(x+f1,y+f1,z+f2); GL11.glVertex3f(x+f2,y+f1,z+f2);
		        GL11.glVertex3f(x+f2,y+f1,z+f2); GL11.glVertex3f(x+f2,y+f1,z+f1);
		        GL11.glVertex3f(x+f2,y+f1,z+f1); GL11.glVertex3f(x+f1,y+f1,z+f1);

		        GL11.glVertex3f(x+f1,y+f2,z+f1); GL11.glVertex3f(x+f1,y+f2,z+f2); 
		        GL11.glVertex3f(x+f1,y+f2,z+f2); GL11.glVertex3f(x+f2,y+f2,z+f2);
		        GL11.glVertex3f(x+f2,y+f2,z+f2); GL11.glVertex3f(x+f2,y+f2,z+f1);
		        GL11.glVertex3f(x+f2,y+f2,z+f1); GL11.glVertex3f(x+f1,y+f2,z+f1);
	        
		        GL11.glVertex3f(x+f1,y+f1,z+f1); GL11.glVertex3f(x+f1,y+f2,z+f1);
		        GL11.glVertex3f(x+f1,y+f1,z+f2); GL11.glVertex3f(x+f1,y+f2,z+f2);
	        	GL11.glVertex3f(x+f2,y+f1,z+f2); GL11.glVertex3f(x+f2,y+f2,z+f2);
	        	GL11.glVertex3f(x+f2,y+f1,z+f1); GL11.glVertex3f(x+f2,y+f2,z+f1);
	        
	        GL11.glEnd();
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }
    
    private void drawWaypoint(float x, float y, float z, double d, double d1, double d2, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslated((float)d , (float)d1, (float)d2);
        GL11.glRotatef(0.2f*CJB.rotatecounter++, 0.0F, 1.0F, 0.0F);
        GL11.glTranslated((float)-d , (float)-d1, (float)-d2);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        GL11.glDepthRange(1, -1);
        y += 0f;
        float f1 = 0f;
        float f2 = 1f;
        float f3 = 0.5f;
        GLColor(0xff000000 + color);
        GL11.glBegin(GL11.GL_QUADS);
	        //FRONT
		        GL11.glVertex3f(x+f1,y+f1,z+f2);
		        GL11.glVertex3f(x+f2,y+f1,z+f2); 
		        GL11.glVertex3f(x+f3,y+f2,z+f3);
		        GL11.glVertex3f(x+f3,y+f2,z+f3);
	        
	        //BACK
		        GL11.glVertex3f(x+f1,y+f1,z+f1);
		        GL11.glVertex3f(x+f3,y+f2,z+f3); 
		        GL11.glVertex3f(x+f3,y+f2,z+f3);
		        GL11.glVertex3f(x+f2,y+f1,z+f1);

	        //RIGHT
		        GL11.glVertex3f(x+f2,y+f1,z+f1);
		        GL11.glVertex3f(x+f3,y+f2,z+f3); 
		        GL11.glVertex3f(x+f3,y+f2,z+f3);
		        GL11.glVertex3f(x+f2,y+f1,z+f2);
	        //LEFT
		        GL11.glVertex3f(x+f1,y+f1,z+f1);
		        GL11.glVertex3f(x+f1,y+f1,z+f2); 
		        GL11.glVertex3f(x+f3,y+f2,z+f3);
		        GL11.glVertex3f(x+f3,y+f2,z+f3);
        GL11.glEnd();
        
        y-= 1;
        
        GL11.glBegin(GL11.GL_QUADS);
        //FRONT
	        GL11.glVertex3f(x+f3,y+f1,z+f3);
	        GL11.glVertex3f(x+f3,y+f1,z+f3); 
	        GL11.glVertex3f(x+f2,y+f2,z+f2);
	        GL11.glVertex3f(x+f1,y+f2,z+f2);
        
        //BACK
	        GL11.glVertex3f(x+f3,y+f1,z+f3);
	        GL11.glVertex3f(x+f1,y+f2,z+f1); 
	        GL11.glVertex3f(x+f2,y+f2,z+f1);
	        GL11.glVertex3f(x+f3,y+f1,z+f3);

        //RIGHT
	        GL11.glVertex3f(x+f3,y+f1,z+f3);
	        GL11.glVertex3f(x+f2,y+f2,z+f1); 
	        GL11.glVertex3f(x+f2,y+f2,z+f2);
	        GL11.glVertex3f(x+f3,y+f1,z+f3);
        //LEFT
	        GL11.glVertex3f(x+f3,y+f1,z+f3);
	        GL11.glVertex3f(x+f3,y+f1,z+f3); 
	        GL11.glVertex3f(x+f1,y+f2,z+f2);
	        GL11.glVertex3f(x+f1,y+f2,z+f1);
	        GL11.glEnd();
	        
	        GL11.glDepthRange(0, 0);
	        GL11.glColor4f(1, 1, 1, 1f);
	        GL11.glBegin(GL11.GL_LINES);

		        GL11.glVertex3f(x+f1,y+f2,z+f1); GL11.glVertex3f(x+f1,y+f2,z+f2); 
		        GL11.glVertex3f(x+f1,y+f2,z+f2); GL11.glVertex3f(x+f2,y+f2,z+f2);
		        GL11.glVertex3f(x+f2,y+f2,z+f2); GL11.glVertex3f(x+f2,y+f2,z+f1);
		        GL11.glVertex3f(x+f2,y+f2,z+f1); GL11.glVertex3f(x+f1,y+f2,z+f1);
	        
		        GL11.glVertex3f(x+f3,y+f1,z+f3); GL11.glVertex3f(x+f1,y+f2,z+f1);
		        GL11.glVertex3f(x+f3,y+f1,z+f3); GL11.glVertex3f(x+f1,y+f2,z+f2);
	        	GL11.glVertex3f(x+f3,y+f1,z+f3); GL11.glVertex3f(x+f2,y+f2,z+f2);
	        	GL11.glVertex3f(x+f3,y+f1,z+f3); GL11.glVertex3f(x+f2,y+f2,z+f1);
	        	
	        	y+=1;
	        	GL11.glVertex3f(x+f1,y+f1,z+f1); GL11.glVertex3f(x+f3,y+f2,z+f3);
		        GL11.glVertex3f(x+f1,y+f1,z+f2); GL11.glVertex3f(x+f3,y+f2,z+f3);
	        	GL11.glVertex3f(x+f2,y+f1,z+f2); GL11.glVertex3f(x+f3,y+f2,z+f3);
	        	GL11.glVertex3f(x+f2,y+f1,z+f1); GL11.glVertex3f(x+f3,y+f2,z+f3);
	        
	        GL11.glEnd();
        
	    GL11.glDepthMask(true);
	    GL11.glDepthRange(0, 1);
	    GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }
    
    private void drawAreaPanel(float x, float y, float z)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        
        y += 0.03f;
        float f1 = 0.1f;
        float f2 = 0.9f;
        
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f(x+f1,y,z+f1);
        GL11.glVertex3f(x+f1,y,z+f2); 
        GL11.glVertex3f(x+f2,y,z+f2);
        GL11.glVertex3f(x+f2,y,z+f1);
        GL11.glEnd();
        
        
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex3f(x+f1,y,z+f1); GL11.glVertex3f(x+f1,y,z+f2); 
        GL11.glVertex3f(x+f1,y,z+f2); GL11.glVertex3f(x+f2,y,z+f2);
        GL11.glVertex3f(x+f2,y,z+f2); GL11.glVertex3f(x+f2,y,z+f1);
        GL11.glVertex3f(x+f2,y,z+f1); GL11.glVertex3f(x+f1,y,z+f1);
        GL11.glEnd();
        
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    private void renderLivingLabel(EntityLiving ent, EntityLiving ent1, String par2Str, double par3, double par5, double par7, int par9, boolean flag, int lx, int ly)
    {
    	float f = 1;
    	float fheight = 2.3F;
    	
    	if (ent1 != null) {
    		f = ent1.getDistanceToEntity(renderManager.livingPlayer);
    		fheight = ent1.height + 0.5f;
    	}

        if (f > (float)par9 || ent1 == renderManager.livingPlayer)
        {
            return;
        }
        
        float f1 = 1.6F;
        float f2 = 0.01666667F * f1;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)par3 + 0.0F, (float)par5 + fheight, (float)par7);
        GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f2, -f2, f2);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        if (flag)
        	GL11.glDisable(GL11.GL_DEPTH_TEST);
        
        GuiIngame g = mc.ingameGUI;
        
        
        if (ent != null) {
        	float health = (float)ent.getHealth() / (float)ent.getMaxHealth();
            int r = ((int)(255 * health) & 255) << 16;
            int gr = ((int)(255 * health) & 255) << 8;
            
	        GL11.glDepthMask(false);
	        g.drawRect(-21, 0, 21, 4, 0x80000000);
	        g.drawRect(-20, 1,-20 + (int)(40 * health), 3, 0x80000000 + gr - r);
	        GL11.glDepthMask(true);
	        
	        if (ent instanceof EntityAnimal) {
	        	if (((EntityAnimal)ent).getGrowingAge() == 0) {
	        		GL11.glColor4f(1, 1, 1, 0.8f);
	        		GL11.glScalef(0.5f, 0.5f, f2);
	        		
	        		GL11.glEnable(GL11.GL_BLEND);
	        		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        		
	        		GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, mc.renderEngine.getTexture("/gui/inventory.png"));
	        		g.drawTexturedModalRect(48, -4, 127, 200, 16, 16);
	        	}
	        }
        } else {
        	g.drawCenteredString(mc.fontRenderer, par2Str, lx, ly, 0xffffffff);
        }
        
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
    
    private void GLColor(int color) {
    	float f = (color >> 16 & 255) * 0.003921569F;
        float f1 = (color >> 8 & 255) * 0.003921569F;
        float f2 = (color & 255) * 0.003921569F;
        float f3 = (color >> 24 & 255) * 0.003921569F;
        GL11.glColor4f(f, f1, f2, f3);
    }
}
