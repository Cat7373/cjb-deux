package net.minecraft.src;

public class CJB_ItemHammer extends Item
{
    protected EnumToolMaterial toolMaterial;

    public CJB_ItemHammer(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        this.toolMaterial = par2EnumToolMaterial;
        this.maxStackSize = 5;
        this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public String getTextureFile()
    {
    	return "/cjb/items.png";
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */

    @Override
    public boolean isFull3D()
    {
        return true;
    }
}
