package com.mods.kina.ExperiencePower.proxy;

import com.mods.kina.ExperiencePower.collection.EnumEPGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.lang.reflect.InvocationTargetException;

public class CommonProxy implements IGuiHandler{
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        try{
            return EnumEPGuiContainer.getGuiContainer(ID).getContainer().getConstructor(World.class, EntityPlayer.class, BlockPos.class).newInstance(world, player, new BlockPos(x, y, z));
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
            return EnumEPGuiContainer.getGuiContainer(ID).getGui().getConstructor(World.class, EntityPlayer.class, BlockPos.class).newInstance(world, player, new BlockPos(x, y, z));
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
