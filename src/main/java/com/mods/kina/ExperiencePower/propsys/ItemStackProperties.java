package com.mods.kina.ExperiencePower.propsys;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public class ItemStackProperties{
    public static final ItemStackProperties instance = new ItemStackProperties();
    private final Map<NBTTagCompound,PropertyContainer> propMap;

    private ItemStackProperties(){
        propMap = Maps.newHashMap();
        //Todo        addItemProperty(Items.nether_star,new PropertyContainer("Light","Holdable","Hand","Non-Blastable"));
    }

    public void addItemProperty(Item item, PropertyContainer prop){
        if(item.getHasSubtypes()){
            for(int i = 0; i < item.getMaxDamage(); i++){
                addItemStackProperty(new ItemStack(item, 1, i), prop);
            }
            return;
        }
        addItemStackProperty(new ItemStack(item), prop);
    }

    public void addBlockProperty(Block block, PropertyContainer prop){
        for(int i = 0; i < 16; i++){
            addItemStackProperty(new ItemStack(block, 1, i), prop);
        }
    }

    public void addItemStackProperty(ItemStack stack, PropertyContainer prop){
        ItemStack copy = stack.copy().splitStack(1);
        propMap.put(copy.writeToNBT(copy.getTagCompound() == null ? new NBTTagCompound() : copy.getTagCompound()), prop);
    }

    public PropertyContainer getPropertyContainer(ItemStack stack){
        return null;//todo
        //todo        ItemStack copy=stack.copy().splitStack(1);
        //todo        copy.writeToNBT(copy.getTagCompound()==null?new NBTTagCompound():copy.getTagCompound());
        //todo        for(Map.Entry<NBTTagCompound,PropertyContainer> entry : propMap.entrySet()){
        //todo            if(KinaLib.lib.areItemStackEqual(ItemStack.loadItemStackFromNBT(entry.getKey()), copy)){
        //todo                return entry.getValue();
        //todo            }
        //todo        }
        //todo        if(!(stack.getItem() instanceof ItemBlock))return new PropertyContainer("Light","Holdable","Hand","Brastable");
        //todo        return new PropertyContainer("Normal", "Box", "Stone", "Stone");
    }

    public static class PropertyContainer{
        private final float weight;
        private final float size;
        private final float hardness;
        private final float brittleness;
        private final float viscosity;
        private final float transparency;
        private final float elasticity;
        private final float luminosity;

        public PropertyContainer(float weight, float size, float hardness, float brittleness, float viscosity, float transparency, float elasticity, float luminosity){
            this.weight = weight;
            this.size = size;
            this.hardness = hardness;
            this.brittleness = brittleness;
            this.viscosity = viscosity;
            this.transparency = transparency;
            this.elasticity = elasticity;
            this.luminosity = luminosity;
        }

        public float getWeight(){
            return weight;
        }

        public float getSize(){
            return size;
        }

        public float getHardness(){
            return hardness;
        }

        public float getBrittleness(){
            return brittleness;
        }

        public float getViscosity(){
            return viscosity;
        }

        public float getTransparency(){
            return transparency;
        }

        public float getElasticity(){
            return elasticity;
        }

        public float getLuminosity(){
            return luminosity;
        }
    }
}
