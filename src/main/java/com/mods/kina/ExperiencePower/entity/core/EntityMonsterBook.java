package com.mods.kina.ExperiencePower.entity.core;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMonsterBook extends EntityZombie{
    public EntityMonsterBook(World worldIn){
        super(worldIn);
    }

    public EntityMonsterBook(World world, BlockPos pos){
        this(world);
        setPosition(pos.getX(), pos.getY(), pos.getZ());
    }
}
