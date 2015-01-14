package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import com.mods.kina.ExperiencePower.tileentity.TileEntitySimpleExpInjector;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockSimpleExpInjector extends BlockMachineBase{
    public BlockSimpleExpInjector(){
        super(Material.rock);
        setUnlocalizedName("simple_exp_injector");
        setLightLevel(0.1f);
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.

     @param world
     Worldのインスタンス
     @param meta
     設置されるBlockのメタデータ値。
     */
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntitySimpleExpInjector();
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        //TODO プレイヤーのアイテムを減らす
        if(playerIn.getCurrentEquippedItem() != null){
            if(((IInventory) worldIn.getTileEntity(pos)).getStackInSlot(0) != null)
                Block.spawnAsEntity(worldIn, pos, ((IInventory) worldIn.getTileEntity(pos)).getStackInSlot(0));
            ((IInventory) worldIn.getTileEntity(pos)).setInventorySlotContents(0, playerIn.getCurrentEquippedItem());
        }else if(((IInventory) worldIn.getTileEntity(pos)).getStackInSlot(0) != null){
            Block.spawnAsEntity(worldIn, pos, ((IInventory) worldIn.getTileEntity(pos)).getStackInSlot(0));
            ((IInventory) worldIn.getTileEntity(pos)).setInventorySlotContents(0, null);
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
        if(((TileEntitySimpleExpInjector) worldIn.getTileEntity(pos)).progressTime > 0) for(int i = 0; i < 10; i++){
            Blocks.enchanting_table.randomDisplayTick(worldIn, pos, state, rand);
        }
    }
}
