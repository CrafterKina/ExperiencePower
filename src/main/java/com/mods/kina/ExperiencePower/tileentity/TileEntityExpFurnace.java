package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.ContainerMachineBase;
import com.mods.kina.ExperiencePower.base.TileEntityMachineBase;
import net.minecraft.block.BlockFurnace;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class TileEntityExpFurnace extends TileEntityMachineBase implements ISidedInventory, IUpdatePlayerListBox{
    private static final int[] slotsTop = new int[]{0};
    private static final int[] slotsBottom = new int[]{2, 1};
    private static final int[] slotsSides = new int[]{1};
    /** The number of ticks that the furnace will keep burning */
    private int furnaceBurnTime;
    private int field_174906_k;
    private int field_174905_l;

    public TileEntityExpFurnace(){
        super("exp_furnace", false, 3);
    }

    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.furnaceBurnTime = compound.getShort("BurnTime");
        this.field_174906_k = compound.getShort("CookTime");
        this.field_174905_l = compound.getShort("CookTimeTotal");
    }

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setShort("BurnTime", (short) this.furnaceBurnTime);
        compound.setShort("CookTime", (short) this.field_174906_k);
        compound.setShort("CookTimeTotal", (short) this.field_174905_l);
    }

    /**
     Containerにスロットを追加したりする。 やろうと思えばなんでもできるが多分スロット追加だけ。

     @param container
     */
    public void initContainer(ContainerMachineBase container){

    }

    /**
     Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt(){
        if(getStackInSlot(0) == null){
            return false;
        }else{
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(0));
            if(itemstack == null) return false;
            if(getStackInSlot(2) == null) return true;
            if(!getStackInSlot(2).isItemEqual(itemstack)) return false;
            int result = getStackInSlot(2).stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= getStackInSlot(2).getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
        }
    }

    /**
     Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem(){
        if(this.canSmelt()){
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(getStackInSlot(0));

            if(getStackInSlot(2) == null){
                setInventorySlotContents(2, itemstack.copy());
            }else if(getStackInSlot(2).getItem() == itemstack.getItem()){
                getStackInSlot(2).stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
            }

            --getStackInSlot(0).stackSize;

            if(getStackInSlot(0).stackSize <= 0){
                setInventorySlotContents(0, null);
            }
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return index != 2 && (index != 1 || stack.getItemDamage() == 0);
    }

    public int[] getSlotsForFace(EnumFacing side){
        return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
    }

    /**
     * Returns true if automation can insert the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
        return this.isItemValidForSlot(index, itemStackIn);
    }

    /**
     * Returns true if automation can extract the given item in the given slot from the given side. Args: slot, item,
     * side
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        if(direction == EnumFacing.DOWN && index == 1){
            Item item = stack.getItem();

            if(item != Items.water_bucket && item != Items.bucket){
                return false;
            }
        }

        return true;
    }

    /**
     Furnace isBurning
     */
    public boolean isBurning(){
        return this.furnaceBurnTime > 0;
    }

    /**
     Updates the JList with a new model.
     */
    public void update(){
        boolean flag = this.isBurning();
        boolean flag1 = false;

        if(this.isBurning()){
            --this.furnaceBurnTime;
        }

        if(!this.worldObj.isRemote){
            if(!this.isBurning() && (getStackInSlot(1) == null || getStackInSlot(0) == null)){
                if(!this.isBurning() && this.field_174906_k > 0){
                    this.field_174906_k = MathHelper.clamp_int(this.field_174906_k - 2, 0, this.field_174905_l);
                }
            }else{
                if(!this.isBurning() && this.canSmelt()){
                    this.furnaceBurnTime = getStackInSlot(1).getItemDamage() * 1000;

                    if(this.isBurning()){
                        flag1 = true;

                        if(getStackInSlot(1) != null){
                            --getStackInSlot(1).stackSize;

                            if(getStackInSlot(1).stackSize == 0){
                                setInventorySlotContents(1, getStackInSlot(1).getItem().getContainerItem(getStackInSlot(1)));
                            }
                        }
                    }
                }

                if(this.isBurning() && this.canSmelt()){
                    ++this.field_174906_k;

                    if(this.field_174906_k == this.field_174905_l){
                        this.field_174906_k = 0;
                        this.field_174905_l = 200;
                        this.smeltItem();
                        flag1 = true;
                    }
                }else{
                    this.field_174906_k = 0;
                }
            }

            if(flag != this.isBurning()){
                flag1 = true;
                BlockFurnace.func_176446_a(this.isBurning(), this.worldObj, this.pos);
            }
        }

        if(flag1){
            this.markDirty();
        }
    }
}
