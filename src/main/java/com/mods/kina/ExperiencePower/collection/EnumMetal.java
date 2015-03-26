package com.mods.kina.ExperiencePower.collection;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection.*;

public enum EnumMetal{
    Iron(ironOreColor),
    Gold(goldOreColor),
    Copper(copperOreColor),
    Tin(tinOreColor),
    Silver(silverOreColor),
    Wise(wiseOreColor),
    Bronze(bronzeOreColor),
    Bismuth,
    Zinc,
    Brass,
    Lead,
    Platinum,
    Nickel,
    Steel,;

    private ItemStack[] ore;
    private ItemStack[] ingot;
    private ItemStack[] block;
    private ItemStack[] dust;
    private ItemStack[] nugget;
    private ItemStack[] wire;
    private ItemStack[] plate;
    private int color;

    EnumMetal(){this(0xffffff);}

    EnumMetal(int color){
        this("", "", "", "", "", "", "");
        this.color = color;
    }

    EnumMetal(String ore, String ingot, String block, String dust, String nugget, String wire, String plate){
        this.ore = !ore.isEmpty() ? getItemStackArray(ore) : getItemStackArray("ore" + name());
        this.ingot = !ingot.isEmpty() ? getItemStackArray(ingot) : getItemStackArray("ingot" + name());
        this.block = !block.isEmpty() ? getItemStackArray(block) : getItemStackArray("block" + name());
        this.dust = !dust.isEmpty() ? getItemStackArray(dust) : getItemStackArray("dust" + name());
        this.nugget = !nugget.isEmpty() ? getItemStackArray(nugget) : getItemStackArray("nugget" + name());
        this.wire = !wire.isEmpty() ? getItemStackArray(wire) : getItemStackArray("wire" + name());
        this.plate = !plate.isEmpty() ? getItemStackArray(plate) : getItemStackArray("plate" + name());
    }

    public static ItemStack[] getItemStackArray(String name){
        List<ItemStack> ores = OreDictionary.getOres(name);
        return ores.toArray(new ItemStack[ores.size()]);
    }

    public ItemStack[] getOre(){
        return ore;
    }

    public ItemStack[] getIngot(){
        return ingot;
    }

    public ItemStack[] getBlock(){
        return block;
    }

    public ItemStack[] getDust(){
        return dust;
    }

    public ItemStack[] getNugget(){
        return nugget;
    }

    public ItemStack[] getWire(){
        return wire;
    }

    public ItemStack[] getPlate(){
        return plate;
    }

    public int getColor(){
        return color;
    }
}
