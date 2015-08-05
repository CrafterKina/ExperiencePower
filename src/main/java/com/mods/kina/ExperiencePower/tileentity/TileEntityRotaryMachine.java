package com.mods.kina.ExperiencePower.tileentity;

import com.google.common.collect.Maps;
import com.mods.kina.ExperiencePower.base.TileEntityEPBase;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;
import com.mods.kina.KinaCore.misclib.KinaLib;
import com.mods.kina.KinaCore.toExtends.IHasInfo;
import net.minecraft.block.BlockSourceImpl;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;

import java.util.Map;

public class TileEntityRotaryMachine extends TileEntityEPBase implements ISidedInventory{
    public int operations = 0;
    public int currentNeedOperations = 0;

    public TileEntityRotaryMachine(){
        super("rotary_machine", false, 6);
    }

    /**
     レッドストーンパルス時だけ動く。独自エネルギーとか作るかは謎。
     */
    @SuppressWarnings("unchecked")
    public void power(){
        ItemStack parts = getStackInSlot(0);
        if(!(parts.getItem() instanceof IHasInfo)) return;
        Map<String,Object> args = Maps.newHashMap();
        args.put("times", operations);
        args.put("inventory", this);
        args.put("tileInfo", new BlockSourceImpl(worldObj, pos));
        args.put("itemstack", parts);
        Map<String,Object> info = (Map<String,Object>) ((IHasInfo) parts.getItem()).info(args);//infoと言いつつ処理を投げる。
        if((Boolean) KinaLib.lib.getOrDefault(info, "rotary.match", false)){
            currentNeedOperations = (Integer) KinaLib.lib.getOrDefault(info, "times.needed", currentNeedOperations);
            inventoryContents = (ItemStack[]) KinaLib.lib.getOrDefault(info, "result", inventoryContents);
            markDirty();
            operations++;
            worldObj.playAuxSFX(1000, pos, 0);
        }else{
            operations = 0;
            worldObj.playAuxSFX(1001, pos, 0);
        }
    }

    protected void readFromNBTExtended(NBTTagCompound compound){
        operations = compound.getInteger("operations");
    }

    protected void writeToNBTExtended(NBTTagCompound compound){
        compound.setInteger("operations", operations);
    }

    public Packet getDescriptionPacket(){
        return UtilTileEntity.instance.getSimpleDescriptionPacket(this);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        readFromNBT(pkt.getNbtCompound());
    }

    public int[] getSlotsForFace(EnumFacing side){
        switch(side){
            case UP:
                return new int[]{1, 2};
            case DOWN:
                return new int[]{3, 4, 5};
            default:
                return new int[0];
        }
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
        return isItemValidForSlot(index, itemStackIn);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return true;
    }

    //蔵鯖同期
    public int getField(int id){
        switch(id){
            case 0:
                return operations;
            case 1:
                return currentNeedOperations;
            default:
                return 0;
        }
    }

    //蔵鯖同期
    public void setField(int id, int value){
        switch(id){
            case 0:
                operations = value;
                break;
            case 1:
                currentNeedOperations = value;
                break;
        }
    }
}