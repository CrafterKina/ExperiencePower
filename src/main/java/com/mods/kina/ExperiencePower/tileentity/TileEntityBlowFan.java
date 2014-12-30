package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.block.BlockBlowFan;
import net.minecraft.block.Block;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

import java.util.List;

public class TileEntityBlowFan extends TileEntity implements IUpdatePlayerListBox{

    /**
     Updates the JList with a new model.
     */
    public void update(){
        //レッドストーン動力が働いている時のみ
        if(worldObj.isBlockPowered(pos) || worldObj.isBlockPowered(pos.offsetUp())){
            //ブロックの向き
            EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
            //相対的位置
            Vec3i vec3i = facing.getDirectionVec();
            //Entity取得
            List<Entity> entities = getEntityWithinRange();
            for(Entity entity : entities){
                //motionXYZに加算して動かす
                entity.motionX += vec3i.getX() * 0.075;
                entity.motionY += vec3i.getY() * 0.1;
                entity.motionZ += vec3i.getZ() * 0.075;
                //fallDistanceを変えているがうまく働いているかは不明
                if(facing == EnumFacing.UP && entity.fallDistance < entity.posY - pos.getY()){
                    entity.fallDistance = (float) entity.posY - pos.getY();
                }
            }
        }
    }

    /**
     正面にいるEntityどもを取得。
     */
    @SuppressWarnings("unchecked")
    public List<Entity> getEntityWithinRange(){
        //向きを取得
        EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
        //向いてるとこの相対的位置を取得
        Vec3i vec3i = facing.getDirectionVec();
        //初期化
        AxisAlignedBB aabb;
        //相対的位置が+方向か-方向かで場合分け
        //範囲指定
        if(facing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE){
            aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1 + vec3i.getX() * getAvailableDistance(), pos.getY() + 1 + vec3i.getY() * getAvailableDistance(), pos.getZ() + 1 + vec3i.getZ() * getAvailableDistance());
        }else{
            aabb = new AxisAlignedBB(pos.getX() + vec3i.getX() * getAvailableDistance(), pos.getY() + vec3i.getY() * getAvailableDistance(), pos.getZ() + vec3i.getZ() * getAvailableDistance(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        }
        //Entity取得
        return worldObj.func_175647_a(Entity.class, aabb, IEntitySelector.selectAnything);
    }

    public int getAvailableDistance(){
        //向きを取得
        EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
        //向いてるとこの相対的位置を取得
        Vec3i vec3i = facing.getDirectionVec();
        //初期化
        int i = 1;
        //forで最大15回回す
        for(; i <= worldObj.getStrongPower(pos); i++){
            //Fanからの相対的位置を元に調査ブロックを徐々に遠ざける
            BlockPos blockPos = pos.add(vec3i.getX() * i, vec3i.getY() * i, vec3i.getZ() * i);
            //BlockPosの位置にあるBlock
            Block block = worldObj.getBlockState(blockPos).getBlock();
            //空気とかじゃなければ
            if(block.getMaterial().isSolid()){
                return i - 1;
            }
        }
        //全部空気とかならめでたく最大値
        return i;
    }
}