package com.mods.kina.ExperiencePower.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;

public abstract class EntityAIBreakBlock extends EntityAIMoveToBlock{
    private int breakingTime;
    private int previousBreakProgress = -1;
    private EntityCreature theEntity;

    public EntityAIBreakBlock(EntityCreature creature, double speedIn, int length){
        super(creature, speedIn, length);
        theEntity = creature;
    }

    /**
     Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute(){
        return super.shouldExecute();
    }

    /**
     Execute a one shot task or start executing a continuous task
     */
    public void startExecuting(){
        super.startExecuting();
        this.breakingTime = 0;
    }

    /**
     Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting(){

        return this.breakingTime <= 240 && theEntity.getDistanceSq(destinationBlock) < 4.0D;

    }

    /**
     Resets the task
     */
    public void resetTask(){
        super.resetTask();
        this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), destinationBlock, -1);
    }

    /**
     Updates the task
     */
    public void updateTask(){
        super.updateTask();
        if(!theEntity.getPosition().down().equals(destinationBlock)) return;
        Block block = theEntity.worldObj.getBlockState(destinationBlock).getBlock();
        if(breakingTime % 4 == 0){
            theEntity.worldObj.playSound((float) destinationBlock.getX() + 0.5F, (float) destinationBlock.getY() + 0.5F, (float) destinationBlock.getZ() + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 8.0F, block.stepSound.getFrequency() * 0.5F, false);
        }
        //8.0/1.5/30
        ++this.breakingTime;
        int i = (int) ((float) this.breakingTime / 240.0F * 10.0F);
        if(i != this.previousBreakProgress){
            this.theEntity.worldObj.sendBlockBreakProgress(this.theEntity.getEntityId(), destinationBlock, i);
            this.previousBreakProgress = i;
        }

        if(this.breakingTime == 240){
            this.theEntity.worldObj.setBlockToAir(destinationBlock);
            //            this.theEntity.worldObj.playAuxSFX(1012, this.targetPosition, 0);
            this.theEntity.worldObj.playAuxSFX(2001, destinationBlock, Block.getIdFromBlock(block));
        }
    }
}
