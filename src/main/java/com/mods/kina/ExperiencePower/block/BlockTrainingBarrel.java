package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockConduitBase;
import com.mods.kina.ExperiencePower.base.BlockEPBase;
import com.mods.kina.ExperiencePower.base.ISendEnergy;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockTrainingBarrel extends BlockEPBase implements ISendEnergy{
    public BlockTrainingBarrel(){
        super(Material.wood);
        setStepSound(soundTypeGrass);
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        setUnlocalizedName("training_barrel");
        setHardness(1.5f);
        setResistance(10);
        setStepSound(soundTypeWood);
    }

    /**
     左クリック時に経験値を出す。
     */
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn){
        /*EnumFacing[] connectingFacings = getConnectingFacings(worldIn, pos);
        if(connectingFacings.length > 0){
            for(EnumFacing facing : connectingFacings){
                BlockConduitBase.addContents(worldIn.getBlockState(pos.offset(facing)), worldIn.rand.nextInt(100));
            }
        }else */
        if(worldIn.isRemote) return;
        if(playerIn.getCurrentEquippedItem().getItem() instanceof ItemSword){
            worldIn.spawnEntityInWorld(new EntityXPOrb(worldIn, pos.getX(), pos.getY(), pos.getZ(), worldIn.rand.nextInt(20) == 0 ? 1 : 0));
            playerIn.getCurrentEquippedItem().damageItem(1, playerIn);
        }
    }

    private EnumFacing[] getConnectingFacings(World world, BlockPos pos){
        ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
        for(EnumFacing facing : EnumFacing.values()){
            if(world.getBlockState(pos.offset(facing)).getBlock() instanceof BlockConduitBase) facings.add(facing);
        }
        return facings.toArray(new EnumFacing[facings.size()]);
    }
}
