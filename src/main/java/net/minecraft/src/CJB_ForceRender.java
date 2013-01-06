package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class CJB_ForceRender extends Render
{
    public void renderForce(CJB_ForceEntity forceentity, double d, double d1, double d2)
    {
    	float i = forceentity.ticksAlive;
    	GL11.glPushMatrix();
    	
    	GL11.glEnable(2977 /*GL_NORMALIZE*/);
    	GL11.glEnable(3042);
    	GL11.glDisable(2896 /*GL_LIGHTING*/);
    	GL11.glBlendFunc(770, 771);
    	GL11.glColor3f(1, 1, 1);
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        GL11.glEnable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        //GL11.glScalef(1f, 1f, 1f);
        loadTexture("/cjb/force.png");
        Tessellator tessellator = Tessellator.instance;
        float f2 = 0 / 10f;
        float f3 = 10 / 10f;
        float f4 = 0 / 10f;
        float f5 = 10 / 10f;
        float f6 = 0.5F + i / 2f;
        float f7 = 0.25F + i / 4f;
        GL11.glRotatef(180F - renderManager.playerViewY, 0.0F,0.5F, 0.0F);
        GL11.glRotatef(-renderManager.playerViewX, 0.5F, 0.0F, 0.0F);
        GL11.glRotatef(77*i, 0.0F, 0.0F, 1.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f7, 0.0F - f7, 0.0D, f2, f5);
        tessellator.addVertexWithUV(f6 - f7, 0.0F - f7, 0.0D, f3, f5);
        tessellator.addVertexWithUV(f6 - f7, f6 - f7, 0.0D, f3, f4);
        tessellator.addVertexWithUV(0.0F - f7, f6 - f7, 0.0D, f2, f4);
        tessellator.draw();
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glDisable(3042 /*GL_BLEND*/);
        GL11.glDisable(32826 /*GL_RESCALE_NORMAL_EXT*/);
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        renderForce((CJB_ForceEntity)entity, d, d1, d2);
    }
}
