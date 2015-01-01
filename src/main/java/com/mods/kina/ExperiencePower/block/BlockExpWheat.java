package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockExpWheat extends BlockCrops{
    public BlockExpWheat(){
        setUnlocalizedName("exp_crop");
    }

    protected Item getSeed(){
        return EnumEPItem.ExperienceCropSeed.getItem();
    }

    protected Item getCrop(){
        return null;
    }

    /**
     右クリックされた時に成長段階をひとつ戻し経験値をドロップ
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        int age = (Integer) worldIn.getBlockState(pos).getValue(AGE);
        if(age == 7){
            worldIn.setBlockState(pos, state.withProperty(AGE, 4));
            worldIn.spawnEntityInWorld(new EntityXPOrb(worldIn, pos.getX(), pos.getY(), pos.getZ(), (worldIn.rand.nextInt(12) + 1)));
            return true;
        }else{
            return false;
        }
    }
}
