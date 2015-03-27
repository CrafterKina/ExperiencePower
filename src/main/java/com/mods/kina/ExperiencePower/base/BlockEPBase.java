package com.mods.kina.ExperiencePower.base;

import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

public class BlockEPBase extends Block{
    public BlockEPBase(Material materialIn){
        super(materialIn);
    }

    public BlockEPBase(){
        this(Material.rock);
    }

    @Override
    public String getUnlocalizedName(){
        return super.getUnlocalizedName().replaceFirst("tile\\.", "kina.tile.");
    }

    public ItemMeshDefinition getMeshDef(){
        return new ItemMeshDefinition(){
            public ModelResourceLocation getModelLocation(ItemStack stack){
                return new ModelResourceLocation(StaticFieldCollection.MODID + ":" + getUnlocalizedName().substring(5).replaceFirst("tile\\.", "").replaceFirst("kina\\.", ""), "inventory");
            }
        };
    }
}
