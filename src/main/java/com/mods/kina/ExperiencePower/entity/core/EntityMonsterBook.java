package com.mods.kina.ExperiencePower.entity.core;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityMonsterBook extends EntityCreature{
    public EntityMonsterBook(World worldIn){
        super(worldIn);
        this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(2, new EntityAILookIdle(this));
    }

    public EntityMonsterBook(World world, BlockPos pos){
        this(world);
        setPosition(pos.getX(), pos.getY(), pos.getZ());
    }
}
