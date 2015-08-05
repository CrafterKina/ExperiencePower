package com.mods.kina.ExperiencePower.event.handler;

import com.mods.kina.ExperiencePower.block.BlockOre;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 MinecraftForge.ORE_GEN_BUSのEvent。 TerrainGenの中でも特に鉱石生成のEvent。
 */
public class OreGenEventHandler{
    private static final WorldGenerator copperGen = new WorldGenMinable(EnumEPBlock.Ore.getBlock().getDefaultState(), 9);
    private static final WorldGenerator tinGen = new WorldGenMinable(EnumEPBlock.Ore.getBlock().getStateFromMeta(BlockOre.OreType.TIN.ordinal()), 9);
    private static final WorldGenerator silverGen = new WorldGenMinable(EnumEPBlock.Ore.getBlock().getStateFromMeta(BlockOre.OreType.SILVER.ordinal()), 9);
    private static final WorldGenerator wiseGen = new WorldGenMinable(EnumEPBlock.Ore.getBlock().getStateFromMeta(BlockOre.OreType.WISE.ordinal()), 1);

    //鉱石を生成する
    @SubscribeEvent
    public void generateOrePre(OreGenEvent.Pre event){
        if(TerrainGen.generateOre(event.world, event.rand, copperGen, event.pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            genStandardOre1(event.world, event.pos, 20, copperGen, 0, 96, event.rand);
        if(TerrainGen.generateOre(event.world, event.rand, tinGen, event.pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            genStandardOre1(event.world, event.pos, 20, tinGen, 0, 64, event.rand);
        if(TerrainGen.generateOre(event.world, event.rand, silverGen, event.pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            genStandardOre1(event.world, event.pos, 2, silverGen, 0, 32, event.rand);
        if(TerrainGen.generateOre(event.world, event.rand, wiseGen, event.pos, OreGenEvent.GenerateMinable.EventType.CUSTOM))
            genStandardOre1(event.world, event.pos, 10, wiseGen, 0, 48, event.rand);
    }

    /**
     Standard ore generation helper. Generates most ores.
     */
    protected void genStandardOre1(World world, BlockPos pos, int size, WorldGenerator generator, int minY, int maxY, Random rnd){
        int l;

        if(maxY < minY){
            l = minY;
            minY = maxY;
            maxY = l;
        }else if(maxY == minY){
            if(minY < 255){
                ++maxY;
            }else{
                --minY;
            }
        }

        for(l = 0; l < size; ++l){
            BlockPos blockpos = pos.add(rnd.nextInt(16), rnd.nextInt(maxY - minY) + minY, rnd.nextInt(16));
            generator.generate(world, rnd, blockpos);
        }
    }

    /**
     Standard ore generation helper. Generates Lapis Lazuli.
     */
    protected void genStandardOre2(World world, BlockPos pos, int size, WorldGenerator generator, int minY, int maxY, Random rnd){
        for(int l = 0; l < size; ++l){
            BlockPos blockpos = pos.add(rnd.nextInt(16), rnd.nextInt(maxY) + rnd.nextInt(maxY) + minY - maxY, rnd.nextInt(16));
            generator.generate(world, rnd, blockpos);
        }
    }
}
