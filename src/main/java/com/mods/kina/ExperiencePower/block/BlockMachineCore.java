package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.tileentity.TileEntityMachineCore;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection.defaultDyeColor;

public class BlockMachineCore extends BlockMachineBase{
    public BlockMachineCore(){
        super(Material.rock);
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        setUnlocalizedName("machine_core");
        UtilTileEntity.instance.registerTileEntity(TileEntityMachineCore.class, "machine_core");
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        if(playerIn.getCurrentEquippedItem().getItem() instanceof ItemDye){
            ((TileEntityMachineCore) worldIn.getTileEntity(pos)).colors[side.getIndex()] = EnumDyeColor.byDyeDamage(playerIn.getCurrentEquippedItem().getMetadata());
            worldIn.markAndNotifyBlock(pos, null, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass){
        TileEntityMachineCore machineCore = (TileEntityMachineCore) worldIn.getTileEntity(pos);
        return machineCore != null ? defaultDyeColor[machineCore.colors[renderPass].getDyeDamage()] : 0xFFFFFF;
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer(){
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityMachineCore();
    }
}
