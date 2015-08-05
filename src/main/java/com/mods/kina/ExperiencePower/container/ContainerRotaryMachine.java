package com.mods.kina.ExperiencePower.container;

import com.mods.kina.ExperiencePower.base.IRecipeContainer;
import com.mods.kina.ExperiencePower.tileentity.TileEntityRotaryMachine;
import com.mods.kina.KinaCore.toExtends.IHasInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerRotaryMachine extends Container{
    static final int partSize = 1;
    static final int sourceSize = 2;
    //static final int fuelSize = 3;
    static final int productSize = 3;
    //    private int lastIsOperating;
    //    private int lastCurrentOperations;
    static final int inventorySize = 27;
    static final int hotbarSize = 9;
    static final int partIndex = 0;
    static final int sourceIndex = partIndex + partSize;
    static final int productIndex = sourceIndex + sourceSize;
    //static final int productIndex = fuelIndex + fuelSize;
    static final int inventoryIndex = productIndex + productSize;
    static final int hotbarIndex = inventoryIndex + inventorySize;
    /*
        private boolean isSourceItem(IRecipeContainer parts,ItemStack stack){
            for(IEPRecipe recipe : parts.getRecipes()){
                ItemStack compare;
                if((compare= (ItemStack) recipe.info(rotaryMachine).get("rotary.normal.item"))==null)continue;
                if(compare.getItem()!=stack.getItem())continue;
                return true;
            }
            return false;
        }*/
    private TileEntityRotaryMachine rotaryMachine = null;
    private int lastOperationTimes;
    private int lastCurrentOperations;

    public ContainerRotaryMachine(World world, EntityPlayer player, BlockPos pos){
        rotaryMachine = (TileEntityRotaryMachine) world.getTileEntity(pos);
        int i;
        int j;
        addSlotToContainer(new Slot(rotaryMachine, 0, 154, 6){
            public boolean isItemValid(ItemStack itemStack){
                return itemStack.getItem() instanceof IHasInfo;
            }
        });
        addSlotToContainer(new Slot(rotaryMachine, 1, 38, 35));
        addSlotToContainer(new Slot(rotaryMachine, 2, 56, 35));
        addSlotToContainer(makeOutputSlot(3, 112, 35));
        addSlotToContainer(makeOutputSlot(4, 130, 35));
        addSlotToContainer(makeOutputSlot(5, 130, 53));

        for(i = 0; i < 3; ++i){
            for(j = 0; j < 9; ++j){
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i){
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }

    private Slot makeOutputSlot(int index, int x, int y){
        return new Slot(rotaryMachine, index, x, y){
            public boolean isItemValid(ItemStack itemStack){
                return false;
            }
        };
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_){
        return rotaryMachine.isUseableByPlayer(p_75145_1_);
    }

    public void onCraftGuiOpened(ICrafting listener){
        super.onCraftGuiOpened(listener);
        listener.func_175173_a(this, rotaryMachine);
    }

    /**
     Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();

        for(Object crafter : this.crafters){
            ICrafting icrafting = (ICrafting) crafter;
            if(lastOperationTimes != rotaryMachine.getField(0)){
                icrafting.sendProgressBarUpdate(this, 0, rotaryMachine.operations);
            }
            /*if(lastIsOperating!=(!rotaryMachine.isOperating?0:1)){
                icrafting.sendProgressBarUpdate(this,1,!rotaryMachine.isOperating?0:1);
            }*/
            if(lastCurrentOperations != rotaryMachine.getField(1)){
                icrafting.sendProgressBarUpdate(this, 1, rotaryMachine.currentNeedOperations);
            }
        }

        this.lastOperationTimes = rotaryMachine.operations;
        lastCurrentOperations = rotaryMachine.currentNeedOperations;
        //        lastIsOperating=!rotaryMachine.isOperating?0:1;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        rotaryMachine.setField(id, data);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int clickedIndex){

        //クリックされたスロットを取得
        Slot slot = (Slot) this.inventorySlots.get(clickedIndex);
        if(slot == null){
            return null;
        }

        if(!slot.getHasStack()) return null;

        //クリックされたスロットのItemStackを取得
        ItemStack itemStack = slot.getStack();

        //書き換えるた後比較したいので変更前のItemStackの状態を保持しておく
        ItemStack itemStackOrg = slot.getStack().copy();

        //素材スロットがクリックされたらインベントリかホットバーの空いてるスロットに移動
        if(sourceIndex <= clickedIndex && clickedIndex <= sourceIndex + sourceSize){
            if(!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)){
                return null;
            }

            slot.onSlotChange(itemStack, itemStackOrg);
        }
        // 結果スロットがクリックされたらインベントリかホットバーの空いてるスロットに移動
        else if(productIndex <= clickedIndex && clickedIndex < productIndex + productSize){
            if(!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize + hotbarSize, false)){
                return null;
            }

            slot.onSlotChange(itemStack, itemStackOrg);
        }
        //インベントリがクリックされた
        else if(inventoryIndex <= clickedIndex && clickedIndex < inventoryIndex + inventorySize){
            if(itemStack.getItem() instanceof IRecipeContainer){
                //燃料アイテムなので燃料スロットへ移動
                if(!this.mergeItemStack(itemStack, partIndex, partIndex + partSize, false)){
                    return null;
                }
            }else if(rotaryMachine.getStackInSlot(0) != null/*&&isSourceItem((IRecipeContainer)rotaryMachine.getStackInSlot(0).getItem(),itemStack)*/){
                //素材アイテムなので素材スロットへ移動
                if(!this.mergeItemStack(itemStack, sourceIndex, sourceIndex + sourceSize, false)){
                    return null;
                }
            }else{
                //どちらでもないのでホットバーに移動
                if(!this.mergeItemStack(itemStack, hotbarIndex, hotbarIndex + hotbarSize, false)){
                    return null;
                }
            }
        }
        //ホットバーがクリックされた
        else if(hotbarIndex <= clickedIndex && clickedIndex < hotbarIndex + hotbarSize){
            if(itemStack.getItem() instanceof IRecipeContainer){
                //燃料アイテムなので燃料スロットへ移動
                if(!this.mergeItemStack(itemStack, partIndex, partIndex + partSize, false)){
                    return null;
                }
            }else if(rotaryMachine.getStackInSlot(0) != null/*&&isSourceItem((IRecipeContainer)rotaryMachine.getStackInSlot(0).getItem(),itemStack)*/){
                //素材アイテムなので素材スロットへ移動
                if(!this.mergeItemStack(itemStack, sourceIndex, sourceIndex + sourceSize, false)){
                    return null;
                }
            }else{
                //どちらでもないのでインベントリに移動
                if(!this.mergeItemStack(itemStack, inventoryIndex, inventoryIndex + inventorySize, false)){
                    return null;
                }
            }
        }

        //シフトクリックで移動先スロットが溢れなかった場合は移動元スロットを空にする
        if(itemStack.stackSize == 0){
            slot.putStack(null);
        }
        //移動先スロットが溢れた場合は数だけ変わって元スロットにアイテムが残るので更新通知
        else{
            slot.onSlotChanged();
        }

        //シフトクリック前後で数が変わらなかった＝移動失敗
        if(itemStack.stackSize == itemStackOrg.stackSize){
            return null;
        }

        slot.onPickupFromSlot(par1EntityPlayer, itemStack);

        return itemStackOrg;
    }
}
