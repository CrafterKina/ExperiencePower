package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import static com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection.*;

public class BlockOre extends Block{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", OreType.class);

    public BlockOre(){
        super(Material.rock);
        setUnlocalizedName("ore");
        setDefaultState(blockState.getBaseState().withProperty(TYPE, OreType.COPPER));
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        setResistance(5);
        ModelLoader.setCustomStateMapper(this, (new StateMap.Builder()).addPropertiesToIgnore(TYPE).build());
    }

    /**
     StackTraceElementを使ってパーティクル生成時にメソッドが呼ばれているかチェック
     */
    public static boolean checkStackRoot(){
        StackTraceElement[] es = new Exception().getStackTrace();
        String particle = EntityDiggingFX.class.getCanonicalName()/*"net.minecraft.client.particle.EntityDiggingFX"*/;
        return es[2].getClassName().equals(particle) || es[4].getClassName().equals(particle);
    }

    /**
     Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName(){
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    /**
     Get the Item that this Block should drop when harvested.

     @param fortune
     the level of the Fortune enchantment on the player's tool
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        if(state.getValue(TYPE) == OreType.WISE) return null;
        return Item.getItemFromBlock(this);
    }

    /**
     Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random){
        if(fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState) this.getBlockState().getValidStates().iterator().next(), random, fortune)){
            int j = random.nextInt(fortune + 2) - 1;

            if(j < 0){
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1);
        }else{
            return this.quantityDropped(random);
        }
    }

    /**
     特に変えない
     */
    @SideOnly(Side.CLIENT)
    public int getBlockColor(){
        return 0xffffff;
    }

    /**
     BlockStateで色を変えている。 パーティクルは論外
     */
    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state){
        if(checkStackRoot()) return 0xffffff;
        switch((OreType) state.getValue(TYPE)){
            case COPPER:
                return copperOreColor;
            case TIN:
                return tinOreColor;
            case SILVER:
                return silverOreColor;
            case WISE:
                return wiseOreColor;
            default:
                return 0xffffff;
        }
    }

    /**
     RenderPassを利用しないのでgetRenderColorと同じで良い
     @see #getRenderColor
     */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass){
        return getRenderColor(worldIn.getBlockState(pos));
    }

    /**
     Called when a user uses the creative pick block button on this block

     @param target
     The full target the player is looking at
     @return A ItemStack to add to the player's inventory, Null if nothing should be added.
     */
    public ItemStack getPickBlock(MovingObjectPosition target, World world, BlockPos pos){
        return new ItemStack(Item.getItemFromBlock(this), 1, ((OreType) world.getBlockState(pos).getValue(TYPE)).ordinal());
    }

    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune){
        IBlockState state = world.getBlockState(pos);
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if(this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)){
            int j;

            if(world.getBlockState(pos).getValue(TYPE) == OreType.WISE){
                j = MathHelper.getRandomIntegerInRange(rand, 10, 20);
            }else{
                j = MathHelper.getRandomIntegerInRange(rand, 0, 2);
            }

            return j;
        }
        return 0;
    }

    /**
     Queries the class of tool required to harvest this block, if null is returned we assume that anything can harvest
     this block.
     */
    public String getHarvestTool(IBlockState state){
        return "pickaxe";
    }

    /**
     Queries the harvest level of this item stack for the specified tool class, Returns -1 if this tool is not of the
     specified type

     @return Harvest level, or -1 if not the specified tool type.
     */
    public int getHarvestLevel(IBlockState state){
        return 1;
    }

    /**
     Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(TYPE, OreType.values()[meta]);
    }

    /**
     Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state){
        return ((OreType) state.getValue(TYPE)).ordinal();
    }

    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list){
        for(int i = 0; i < OreType.values().length; i++){
            list.add(new ItemStack(itemIn, 1, i));
        }
    }

    public float getBlockHardness(World worldIn, BlockPos pos){
        return 3;
    }

    protected BlockState createBlockState(){
        return new BlockState(this, TYPE);
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer(){
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public enum OreType implements IStringSerializable{
        COPPER,
        TIN,
        SILVER,
        WISE;

        public String getName(){
            return name().toLowerCase();
        }
    }

}
