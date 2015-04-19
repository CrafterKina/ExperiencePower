package com.mods.kina.ExperiencePower.tileentity;

import com.google.common.collect.Lists;
import com.mods.kina.ExperiencePower.block.BlockBlowFan;
import com.mods.kina.KinaCore.misclib.KinaLib;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TileEntityBlowFan extends TileEntity implements IUpdatePlayerListBox{

    /**
     Updates the JList with a new model.
     */
    @SuppressWarnings("unchecked")
    public void update(){
        //レッドストーン動力が働いている時のみ
        if(worldObj.isBlockIndirectlyGettingPowered(pos) > 0){
            //ブロックの向き
            EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
            //Entity取得
            List<Entity> entities = getAvailableRange() == null ? Lists.newArrayList() : worldObj.getEntitiesWithinAABB(Entity.class, getAvailableRange(), IEntitySelector.selectAnything);
            for(Entity entity : entities){
                //motionXYZに加算して動かす
                entity.motionX += facing.getFrontOffsetX() * 0.075;
                entity.motionY += facing.getFrontOffsetY() * 0.1;
                entity.motionZ += facing.getFrontOffsetZ() * 0.075;
                //fallDistanceを変えて痛くないように
                if(facing == EnumFacing.UP){
                    entity.fallDistance = 0;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public AxisAlignedBB getAvailableRange(){
        //向きを取得
        EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
        //範囲指定
        BlockPos facingPos = KinaLib.lib.getFrontPos(facing, pos);
        //BlockPosの方は順番が気に喰わないので独自のを用意。
        Iterator<BlockPos> maxPoses = KinaLib.lib.getAllInBox(facingPos, KinaLib.lib.getFrontPosWithDistance(facing, pos, worldObj.isBlockIndirectlyGettingPowered(pos))).iterator();
        ArrayList<BlockPos> result = Lists.newArrayList();
        while(maxPoses.hasNext()){
            BlockPos scanning = maxPoses.next();
            if(worldObj.getBlockState(scanning).getBlock().getMaterial().isSolid()) break;
            result.add(scanning);
        }
        if(result.isEmpty()) return null;
        return KinaLib.lib.getBlocklizedAABB(KinaLib.lib.getFixedAABB(result.get(0), result.get(result.size() - 1)), facing);
    }
}