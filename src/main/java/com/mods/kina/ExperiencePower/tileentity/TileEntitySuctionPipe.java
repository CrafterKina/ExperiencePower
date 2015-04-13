package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.TileEntityEPBase;
import com.mods.kina.ExperiencePower.block.BlockBasicPipe;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import static com.mods.kina.ExperiencePower.block.BlockBasicPipe.optionalFacing.NONE;
import static com.mods.kina.ExperiencePower.block.BlockBasicPipe.optionalFacing.values;

public class TileEntitySuctionPipe extends TileEntityEPBase implements IUpdatePlayerListBox, IHopper, ISidedInventory{
    public BlockBasicPipe.optionalFacing in = NONE;
    public BlockBasicPipe.optionalFacing out = NONE;
    public int cooldownTime = -1;

    public TileEntitySuctionPipe(){
        super("suction_pipe", false, 1);
    }

    private static boolean canExtractItem(IInventory p_174921_0_, ItemStack p_174921_1_, int p_174921_2_, EnumFacing p_174921_3_){
        return !(p_174921_0_ instanceof ISidedInventory) || ((ISidedInventory) p_174921_0_).canExtractItem(p_174921_2_, p_174921_1_, p_174921_3_);
    }

    private static boolean isEmptyInventory(IInventory inventory, EnumFacing facing){
        if(inventory instanceof ISidedInventory){
            ISidedInventory isidedinventory = (ISidedInventory) inventory;
            int[] aint = isidedinventory.getSlotsForFace(facing);

            for(int anAint : aint){
                if(isidedinventory.getStackInSlot(anAint) != null){
                    return false;
                }
            }
        }else{
            int j = inventory.getSizeInventory();

            for(int k = 0; k < j; ++k){
                if(inventory.getStackInSlot(k) != null){
                    return false;
                }
            }
        }

        return true;
    }

    protected void readFromNBTExtended(NBTTagCompound compound){
        in = values()[compound.getByte("in")];
        out = values()[compound.getByte("out")];
        cooldownTime = compound.getInteger("cooldownTime");
        UtilTileEntity.instance.syncTileEntity(this);
        if(hasWorldObj())
            worldObj.markAndNotifyBlock(pos, worldObj.getChunkFromBlockCoords(pos), worldObj.getBlockState(pos), worldObj.getBlockState(pos), 3);
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

    public Packet getDescriptionPacket(){
        return UtilTileEntity.instance.getSimpleDescriptionPacket(this);
    }

    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        UtilTileEntity.instance.onSimpleDataPacket(this, net, pkt);
    }

    /**
     For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     hasn't changed and skip it.
     */
    public void markDirty(){
        super.markDirty();
        cooldownTime = 8;
    }

    /**
     Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. Isn't this
     more of a set than a get?
     */
    public int getInventoryStackLimit(){
        return 1;
    }

    private void updatePipe(){
        transferItemStack();
        obtainItemStack();
    }

    private void transferItemStack(){
        if(out == NONE) return;
        IInventory iinventory = getInventoryConnectingWithOutput();
        if(iinventory == null) return;
        EnumFacing enumfacing = BlockBasicPipe.convertToNormal(out).getOpposite();
        if(!canInsertItem(iinventory, enumfacing)) return;
        if(this.getStackInSlot(0) == null) return;
        ItemStack itemstack = this.getStackInSlot(0).copy();
        ItemStack itemstack1 = TileEntityHopper.func_174918_a(iinventory, decrStackSize(0, 1), enumfacing);
        if(itemstack1 == null || itemstack1.stackSize == 0){
            iinventory.markDirty();
            markDirty();
        }
        this.setInventorySlotContents(0, itemstack);
    }

    private void obtainItemStack(){
        IInventory iinventory = getInventoryConnectingWithInput();

        if(iinventory == null) return;
        EnumFacing enumfacing = BlockBasicPipe.convertToNormal(in).getOpposite();

        if(isEmptyInventory(iinventory, enumfacing)) return;

        if(iinventory instanceof ISidedInventory){
            ISidedInventory isidedinventory = (ISidedInventory) iinventory;
            int[] aint = isidedinventory.getSlotsForFace(enumfacing);

            for(int anAint : aint){
                if(extractItem(iinventory, anAint, enumfacing)){
                    markDirty();
                }
            }
            return;
        }

        int j = iinventory.getSizeInventory();

        for(int k = 0; k < j; ++k){
            if(extractItem(iinventory, k, enumfacing)){
                markDirty();
            }
        }
    }

    private boolean extractItem(IInventory inventory, int slotIndex, EnumFacing facing){
        ItemStack itemstack = inventory.getStackInSlot(slotIndex);

        if(itemstack != null && canExtractItem(inventory, itemstack, slotIndex, facing)){
            ItemStack itemstack2 = TileEntityHopper.func_174918_a(this, inventory.decrStackSize(slotIndex, 1), null);

            if(itemstack2 == null || itemstack2.stackSize == 0){
                inventory.markDirty();
                return true;
            }

            inventory.setInventorySlotContents(slotIndex, itemstack.copy());
        }

        return false;
    }

    private IInventory getInventoryConnectingWithInput(){
        if(in == NONE) return null;
        EnumFacing facing = BlockBasicPipe.convertToNormal(in);
        return TileEntityHopper.func_145893_b(worldObj, getXPos() + facing.getDirectionVec().getX(), getYPos() + facing.getDirectionVec().getY(), getZPos() + facing.getDirectionVec().getZ());
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

    private IInventory getInventoryConnectingWithOutput(){
        if(out == NONE) return null;
        EnumFacing facing = BlockBasicPipe.convertToNormal(out);
        return TileEntityHopper.func_145893_b(worldObj, getXPos() + facing.getDirectionVec().getX(), getYPos() + facing.getDirectionVec().getY(), getZPos() + facing.getDirectionVec().getZ());
    }

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
            if(out != NONE && BlockBasicPipe.convertToNormal(out) == side) return false;
            if(in == NONE || BlockBasicPipe.convertToNormal(in) != side) in = BlockBasicPipe.convertToOptional(side);
            else in = NONE;
        }else{
            if(in != NONE && BlockBasicPipe.convertToNormal(in) == side) return false;
            if(out == NONE || BlockBasicPipe.convertToNormal(out) != side) out = BlockBasicPipe.convertToOptional(side);
            else out = NONE;
        }
        worldIn.markAndNotifyBlock(pos, null, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
        return true;
    }
}
