package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.TileEntityEPBase;
import com.mods.kina.ExperiencePower.block.BlockBasicPipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import static com.mods.kina.ExperiencePower.block.BlockBasicPipe.optionalFacing;
import static com.mods.kina.ExperiencePower.block.BlockBasicPipe.optionalFacing.NONE;
import static com.mods.kina.ExperiencePower.block.BlockBasicPipe.optionalFacing.values;

public class TileEntityBasicPipe extends TileEntityEPBase implements IUpdatePlayerListBox, IHopper, ISidedInventory{
    public optionalFacing in = NONE;
    public optionalFacing out = NONE;
    public int cooldownTime = -1;

    public TileEntityBasicPipe(){
        super("basic_pipe", false, 1);
    }

    protected void readFromNBTExtended(NBTTagCompound compound){
        in = values()[compound.getByte("in")];
        out = values()[compound.getByte("out")];
        cooldownTime = compound.getInteger("cooldownTime");
    }

    protected void writeToNBTExtended(NBTTagCompound compound){
        compound.setByte("in", (byte) in.ordinal());
        compound.setByte("out", (byte) out.ordinal());
        compound.setInteger("cooldownTime", cooldownTime);
    }

    public void update(){
        if(this.worldObj == null || this.worldObj.isRemote) return;
        --cooldownTime;
        if(isOnCooldown()) return;
        cooldownTime = 0;
        updatePipe();
    }

    private void updatePipe(){
        boolean flag = getStackInSlot(0) != null && transferItemStack();
        //flag = hasUnmetSlot() ? carryInItemStack() || flag : flag;
        if(!flag) return;
        cooldownTime = 8;
        markDirty();
    }

    private boolean transferItemStack(){
        IInventory iinventory = getInventoryConnectingWithOutput();
        if(iinventory == null) return false;
        EnumFacing enumfacing = BlockBasicPipe.convertToNormal(out).getOpposite();
        if(!canInsertItem(iinventory, enumfacing)) return false;
        if(this.getStackInSlot(0) == null) return false;
        ItemStack itemstack = this.getStackInSlot(0).copy();
        ItemStack itemstack1 = TileEntityHopper.func_174918_a(iinventory, decrStackSize(0, 1), enumfacing);
        if(itemstack1 == null || itemstack1.stackSize == 0){
            iinventory.markDirty();
            return true;
        }
        this.setInventorySlotContents(0, itemstack);
        return false;
    }

    /*private boolean carryInItemStack(){
        IInventory iinventory = getInventoryConnectingWithInput();

        if(iinventory == null) return false;
        EnumFacing enumfacing = EnumFacing.DOWN;

        if(!hasEmptySlot(iinventory, enumfacing))return false;

        if(iinventory instanceof ISidedInventory){
            ISidedInventory isidedinventory = (ISidedInventory) iinventory;
            int[] aint = isidedinventory.getSlotsForFace(enumfacing);

            for(int anAint : aint){
                if(func_174915_a(this, iinventory, anAint, enumfacing)){
                    return true;
                }
            }
        }else{
            int j = iinventory.getSizeInventory();

            for(int k = 0; k < j; ++k){
                if(func_174915_a(this, iinventory, k, enumfacing)){
                    return true;
                }
            }
        }
        return false;
    }*/

    private boolean hasEmptySlot(IInventory iinventory, EnumFacing enumfacing){
        int j = iinventory instanceof ISidedInventory ? ((ISidedInventory) iinventory).getSlotsForFace(enumfacing).length : iinventory.getSizeInventory();
        for(int k = 0; k < j; ++k){
            ItemStack itemstack1 = iinventory.getStackInSlot(k);

            if(itemstack1 == null){
                return true;
            }
        }
        return false;
    }

    private boolean canInsertItem(IInventory iinventory, EnumFacing enumfacing){
        int j = iinventory instanceof ISidedInventory ? ((ISidedInventory) iinventory).getSlotsForFace(enumfacing).length : iinventory.getSizeInventory();
        for(int k = 0; k < j; ++k){
            ItemStack itemstack1 = iinventory.getStackInSlot(k);

            if(itemstack1 == null || itemstack1.stackSize != itemstack1.getMaxStackSize()){
                return true;
            }
        }
        return false;
    }

    private boolean func_174915_a(IHopper p_174915_0_, IInventory p_174915_1_, int p_174915_2_, EnumFacing p_174915_3_){
        ItemStack itemstack = p_174915_1_.getStackInSlot(p_174915_2_);

        if(itemstack != null && (!(p_174915_1_ instanceof ISidedInventory) || ((ISidedInventory) p_174915_1_).canExtractItem(p_174915_2_, itemstack, p_174915_3_))){
            ItemStack itemstack1 = itemstack.copy();
            ItemStack itemstack2 = TileEntityHopper.func_174918_a(p_174915_0_, p_174915_1_.decrStackSize(p_174915_2_, 1), null);
            if(cooldownTime <= 1){
                cooldownTime = 8;
            }

            if(itemstack2 == null || itemstack2.stackSize == 0){
                p_174915_1_.markDirty();
                return true;
            }

            p_174915_1_.setInventorySlotContents(p_174915_2_, itemstack1);
        }

        return false;
    }

    private boolean hasUnmetSlot(){
        return getStackInSlot(0) == null || getStackInSlot(0).stackSize < getStackInSlot(0).getMaxStackSize();
    }

    private IInventory getInventoryConnectingWithOutput(){
        EnumFacing facing = BlockBasicPipe.convertToNormal(out);
        return TileEntityHopper.func_145893_b(worldObj, getXPos() + facing.getDirectionVec().getX(), getYPos() + facing.getDirectionVec().getY(), getZPos() + facing.getDirectionVec().getZ());
    }
    
    /*private IInventory getInventoryConnectingWithInput(){
        EnumFacing facing = BlockBasicPipe.convertToNormal(in);
        return TileEntityHopper.func_145893_b(worldObj, getXPos() + facing.getDirectionVec().getX(), getYPos() + facing.getDirectionVec().getY(), getZPos() + facing.getDirectionVec().getZ());
    }*/

    private boolean isOnCooldown(){
        return cooldownTime > 0;
    }

    public double getXPos(){
        return pos.getX();
    }

    public double getYPos(){
        return pos.getY();
    }

    public double getZPos(){
        return pos.getZ();
    }

    public int[] getSlotsForFace(EnumFacing side){
        return new int[]{0};
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
        return in != NONE && direction == BlockBasicPipe.convertToNormal(in);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return out != NONE && direction == BlockBasicPipe.convertToNormal(out);
    }

    public boolean wrench(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!playerIn.isSneaking()){
            if(in == NONE || BlockBasicPipe.convertToNormal(in) != side) in = BlockBasicPipe.convertToOptional(side);
            else in = NONE;
        }else{
            if(out == NONE || BlockBasicPipe.convertToNormal(out) != side) out = BlockBasicPipe.convertToOptional(side);
            else out = NONE;
        }
        worldIn.markAndNotifyBlock(pos, null, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
        return true;
    }
}
