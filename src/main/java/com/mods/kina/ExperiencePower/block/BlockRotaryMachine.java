package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import com.mods.kina.ExperiencePower.base.IMachineSettable;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.collection.EnumEPGuiContainer;
import com.mods.kina.ExperiencePower.tileentity.TileEntityRotaryMachine;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;
import com.mods.kina.KinaCore.misclib.BlockFieldHelper;
import com.mods.kina.KinaCore.misclib.KinaLib;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockRotaryMachine extends BlockMachineBase{
    public BlockRotaryMachine(){
        super(Material.rock);
        setUnlocalizedName("rotary_machine");
        setCreativeTab(EnumEPCreativeTab.BLOCK);
        UtilTileEntity.instance.registerTileEntity(TileEntityRotaryMachine.class, "rotary_machine");
    }

    public static boolean isIndirectlyPowered(World world, BlockPos pos){
        return world.isBlockIndirectlyGettingPowered(pos) > 0;
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World world, int p_149915_2_){
        return new TileEntityRotaryMachine();
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ){
        if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof IMachineSettable){
            ((TileEntityRotaryMachine) world.getTileEntity(pos)).setInventorySlotContents(0, new ItemStack(player.getHeldItem().getItem()));
            player.inventory.getCurrentItem().stackSize--;
        }else if(!world.isRemote){
            EnumEPGuiContainer.RotaryMachine.openGui(world, pos, player);
        }
        return true;
    }

    /**
     Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileEntityRotaryMachine){
            TileEntityRotaryMachine rotaryMachine = (TileEntityRotaryMachine) te;
            boolean isPowering = (Boolean) KinaLib.lib.putIfAbsent(BlockFieldHelper.instance.getFieldMap(pos), "isPowering", false);
            if(isPowering && !isIndirectlyPowered(world, pos)){
                isPowering = false;
            }else if(!isPowering && isIndirectlyPowered(world, pos)){
                isPowering = true;
                rotaryMachine.power();
            }
            BlockFieldHelper.instance.putField(pos, "isPowering", isPowering);
        }
    }
}
