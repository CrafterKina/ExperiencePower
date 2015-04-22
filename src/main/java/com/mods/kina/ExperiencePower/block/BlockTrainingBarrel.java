package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockEPBase;
import com.mods.kina.ExperiencePower.base.ISendEnergy;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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
        if(worldIn.isRemote) return;
        if(playerIn.getCurrentEquippedItem().getItem() instanceof ItemSword){
            worldIn.spawnEntityInWorld(new EntityXPOrb(worldIn, pos.getX(), pos.getY(), pos.getZ(), worldIn.rand.nextInt(20) == 0 ? 1 : 0));
            playerIn.getCurrentEquippedItem().damageItem(1, playerIn);
        }
    }
}
