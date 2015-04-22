package com.mods.kina.ExperiencePower.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class IInventoryImpl extends TileEntity implements IInventory{
    private String inventoryTitle;
    private int slotsCount;
    private ItemStack[] inventoryContents;
    private boolean hasCustomName;

    public IInventoryImpl(String title, boolean hasCustomName, int slotsCount){
        this.inventoryTitle = title;
        this.hasCustomName = hasCustomName;
        this.slotsCount = slotsCount;
        this.inventoryContents = new ItemStack[slotsCount];
    }

    @SideOnly(Side.CLIENT)
    public IInventoryImpl(IChatComponent title, int slotsCount){
        this(title.getUnformattedText(), true, slotsCount);
    }

    public IInventoryImpl(){}

    /**
     Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int index){
        return index >= 0 && index < this.inventoryContents.length ? this.inventoryContents[index] : null;
    }

    /**
     Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a new
     stack.
     */
    public ItemStack decrStackSize(int index, int count){
        if(this.inventoryContents[index] != null){
            ItemStack itemstack;

            if(this.inventoryContents[index].stackSize <= count){
                itemstack = this.inventoryContents[index];
                this.inventoryContents[index] = null;
                this.markDirty();
                return itemstack;
            }else{
                itemstack = this.inventoryContents[index].splitStack(count);

                if(this.inventoryContents[index].stackSize == 0){
                    this.inventoryContents[index] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }else{
            return null;
        }
    }

    /**
     When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem - like
     when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int index){
        if(this.inventoryContents[index] != null){
            ItemStack itemstack = this.inventoryContents[index];
            this.inventoryContents[index] = null;
            return itemstack;
        }else{
            return null;
        }
    }

    /**
     Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setInventorySlotContents(int index, ItemStack stack){
        this.inventoryContents[index] = stack;

        if(stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    /**
     Returns the number of slots in the inventory.
     */
    public int getSizeInventory(){
        return this.slotsCount;
    }

    /**
     Gets the name of this command sender (usually username, but possibly "Rcon")
     */
    public String getCommandSenderName(){
        return this.inventoryTitle;
    }

    /**
     Returns true if this thing is named
     */
    public boolean hasCustomName(){
        return this.hasCustomName;
    }

    public void setCustomName(String p_110133_1_){
        this.hasCustomName = true;
        this.inventoryTitle = p_110133_1_;
    }

    public IChatComponent getDisplayName(){
        return this.hasCustomName() ? new ChatComponentText(this.getCommandSenderName()) : new ChatComponentTranslation(this.getCommandSenderName());
    }

    /**
     Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. Isn't this
     more of a set than a get?
     */
    public int getInventoryStackLimit(){
        return 64;
    }

    /**
     For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     hasn't changed and skip it.
     */
    public void markDirty(){
        super.markDirty();
    }

    /**
     Do not make give this method the name canInteractWith because it clashes with Container
     */
    public boolean isUseableByPlayer(EntityPlayer playerIn){
        return true;
    }

    public void openInventory(EntityPlayer playerIn){}

    public void closeInventory(EntityPlayer playerIn){}

    /**
     Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int index, ItemStack stack){
        return true;
    }

    public int getField(int id){
        return 0;
    }

    public void setField(int id, int value){}

    public int getFieldCount(){
        return 0;
    }

    public void clear(){
        for(int i = 0; i < this.inventoryContents.length; ++i){
            this.inventoryContents[i] = null;
        }
    }

    /*TileEntity NBT*/
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.inventoryContents = new ItemStack[this.getSizeInventory()];

        if(compound.hasKey("CustomName", 8)){
            setCustomName(compound.getString("CustomName"));
        }

        for(int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if(j >= 0 && j < this.inventoryContents.length){
                this.inventoryContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        readFromNBTExtended(compound.getCompoundTag("Extended"));
    }

    protected void readFromNBTExtended(NBTTagCompound compound){}

    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        NBTTagList nbttaglist = new NBTTagList();

        for(int i = 0; i < this.inventoryContents.length; ++i){
            if(this.inventoryContents[i] != null){
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventoryContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        compound.setTag("Items", nbttaglist);

        if(this.hasCustomName()){
            compound.setString("CustomName", this.getCommandSenderName());
        }

        NBTTagCompound extendedCompound = new NBTTagCompound();
        writeToNBTExtended(extendedCompound);
        compound.setTag("Extended", extendedCompound);
    }

    protected void writeToNBTExtended(NBTTagCompound compound){}
}
