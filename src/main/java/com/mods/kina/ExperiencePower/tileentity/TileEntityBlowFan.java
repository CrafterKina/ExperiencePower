package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.block.BlockBlowFan;
import net.minecraft.entity.Entity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;

import java.util.List;

public class TileEntityBlowFan extends TileEntity implements IUpdatePlayerListBox{
    /**
     Updates the JList with a new model.
     */
    public void update(){
        //System.out.println(getEntityWithinRange());
        if(worldObj.isBlockPowered(pos) || worldObj.isBlockPowered(pos.offsetUp())){
            EnumFacing facing = (EnumFacing) worldObj.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
            Vec3i vec3i = facing.getDirectionVec();
            for(Entity entity : getEntityWithinRange()){
                if(entity.canBeCollidedWith() || entity.canBePushed()){
                    entity.setPosition(entity.posX + vec3i.getX() * 0.1, entity.posY + vec3i.getY() * 0.1, entity.posZ + vec3i.getZ() * 0.1);
                }
            }
        }
    }

    /**
     正面にいるEntityどもを取得。
     */
    @SuppressWarnings("unchecked")
    public List<Entity> getEntityWithinRange(){
        EnumFacing facing = (EnumFacing)worldObj.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
        Vec3i vec3i = facing.getDirectionVec();
        AxisAlignedBB aabb;
        if(facing.getAxisDirection()==EnumFacing.AxisDirection.POSITIVE){
            aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1 + vec3i.getX()*worldObj.getStrongPower(pos), pos.getY()+ vec3i.getY()*worldObj.getStrongPower(pos), pos.getZ() + 1+vec3i.getZ()*worldObj.getStrongPower(pos));
        }else {
            aabb =new AxisAlignedBB(pos.getX() + vec3i.getX()*worldObj.getStrongPower(pos), pos.getY()+ vec3i.getY()*worldObj.getStrongPower(pos), pos.getZ() +vec3i.getZ()*worldObj.getStrongPower(pos), pos.getX() + 1 , pos.getY(), pos.getZ() + 1);
        }
        return worldObj.getEntitiesWithinAABB(Entity.class, aabb);
    }
}