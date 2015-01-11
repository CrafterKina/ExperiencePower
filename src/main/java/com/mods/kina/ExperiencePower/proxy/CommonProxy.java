package com.mods.kina.ExperiencePower.proxy;

import com.mods.kina.ExperiencePower.base.ContainerMachineBase;
import com.mods.kina.ExperiencePower.base.TileEntityMachineBase;
import com.mods.kina.ExperiencePower.collection.EnumEPGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.lang.reflect.InvocationTargetException;

public class CommonProxy implements IGuiHandler{
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        try{
            return EnumEPGui.getGuiContainer(ID).getContainer().getConstructor(IInventory.class, TileEntityMachineBase.class).newInstance(player.inventory, world.getTileEntity(new BlockPos(x, y, z)));
        } catch(NoSuchMethodException e){
            e.printStackTrace();
        } catch(InvocationTargetException e){
            e.printStackTrace();
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        try{
            return EnumEPGui.getGuiContainer(ID).getGui().getConstructor(ContainerMachineBase.class).newInstance(getServerGuiElement(ID, player, world, x, y, z));
        } catch(NoSuchMethodException e){
            e.printStackTrace();
        } catch(InvocationTargetException e){
            e.printStackTrace();
        } catch(InstantiationException e){
            e.printStackTrace();
        } catch(IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }

    public void registerRender(){

    }
}
