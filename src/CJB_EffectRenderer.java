package net.minecraft.src;

public class CJB_EffectRenderer extends EffectRenderer
{
    public CJB_EffectRenderer(World par1World, RenderEngine par2RenderEngine)
    {
    	super(par1World, par2RenderEngine);
    }

    public void renderParticles(Entity par1Entity, float par2)
    {
    	super.renderParticles(par1Entity, par2);
    	try {
    		if (CJB.renderer != null)
    			CJB.renderer.renderCJB(par2);
    	} catch (Throwable e) {}
    }
}
