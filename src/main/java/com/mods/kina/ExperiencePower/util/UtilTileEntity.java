package com.mods.kina.ExperiencePower.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public class UtilTileEntity{
    public static final UtilTileEntity instance = new UtilTileEntity();

    /**
     TileEntityを同期する。 getDescriptionPacketとonDataPacketの実装が必要。
     */
    public void syncTileEntity(TileEntity entity){
        if(!entity.hasWorldObj()) return;
        @SuppressWarnings("unchecked") List<EntityPlayer> list = entity.getWorld().playerEntities;
        for(EntityPlayer player : list){
            if(player instanceof EntityPlayerMP){
                ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(entity.getDescriptionPacket());
            }
        }
    }

    public Packet getSimpleDescriptionPacket(TileEntity entity){
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        entity.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(entity.getPos(), 1, nbtTagCompound);
    }

    public void onSimpleDataPacket(TileEntity entity, NetworkManager net, S35PacketUpdateTileEntity pkt){
        entity.readFromNBT(pkt.getNbtCompound());
    }
}
