package com.mods.kina.ExperiencePower.entity.core;

import com.mods.kina.ExperiencePower.entity.ai.EntityAIBreakBlock;
import com.mods.kina.KinaCore.misclib.KinaLib;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityOrefish extends EntityTameable{
    public EntityOrefish(World worldIn){
        super(worldIn);
        this.setSize(0.4F, 0.3F);
        tasks.addTask(1, new EntityAISwimming(this));
        //tasks.addTask(2,aiSit);
        tasks.addTask(3, new EntityAIBreakBlock(this, 0.8d, 16){
            protected boolean shouldMoveTo(World worldIn, BlockPos pos){
                return KinaLib.lib.isOre(worldIn.getBlockState(pos));
            }
        });
        tasks.addTask(4, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
        tasks.addTask(5, new EntityAIWander(this, 0.8D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        field_175506_bl = Blocks.stone;
    }

    public EntityAgeable createChild(EntityAgeable ageable){
        return null;
    }

    public float getEyeHeight(){
        return 0.1F;
    }

    protected void applyEntityAttributes(){
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        //this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
    }

    /**
     Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount){
        return !this.isEntityInvulnerable(source) && super.attackEntityFrom(source, amount);
    }

    /**
     returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     prevent them from trampling crops
     */
    protected boolean canTriggerWalking(){
        return false;
    }

    /**
     Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound(){
        return "mob.silverfish.say";
    }

    /**
     Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound(){
        return "mob.silverfish.hit";
    }

    /**
     Returns the sound this mob makes on death.
     */
    protected String getDeathSound(){
        return "mob.silverfish.kill";
    }

    /**
     Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn(){
        return !isTamed() && ticksExisted > 2400;
    }

    protected void playStepSound(BlockPos pos, Block blockIn){
        this.playSound("mob.silverfish.step", 0.15F, 1.0F);
    }

    protected Item getDropItem(){
        return Item.getItemFromBlock(Blocks.stone_button);
    }

    /**
     Called to update the entity's position/logic.
     */
    public void onUpdate(){
        //        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }

    public float getBlockPathWeight(BlockPos pos){
        return this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.stone || KinaLib.lib.isOre(worldObj.getBlockState(pos.down())) ? 10.0F : super.getBlockPathWeight(pos);
    }

    /**
     Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute(){
        return EnumCreatureAttribute.ARTHROPOD;
    }

    /**
     Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     the animal type)
     */
    public boolean isBreedingItem(ItemStack stack){
        return ItemStack.areItemsEqual(stack, new ItemStack(Blocks.stone_button));
    }

    /**
     Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer player){
        ItemStack itemstack = player.inventory.getCurrentItem();

        if(!isTamed() && itemstack != null && player.getDistanceSqToEntity(this) < 9.0D){
            if(!player.capabilities.isCreativeMode){
                --itemstack.stackSize;
            }

            if(itemstack.stackSize <= 0){
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }

            if(!this.worldObj.isRemote){
                if(this.rand.nextInt(3) == 0){
                    this.setTamed(true);
                    this.setOwnerId(player.getUniqueID().toString());
                    this.playTameEffect(true);
                    this.worldObj.setEntityState(this, (byte) 7);
                }else{
                    this.playTameEffect(false);
                    this.worldObj.setEntityState(this, (byte) 6);
                }
            }

            return true;
        }

        return super.interact(player);
    }

}
