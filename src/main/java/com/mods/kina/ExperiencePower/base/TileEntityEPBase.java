package com.mods.kina.ExperiencePower.base;

import com.mods.kina.KinaCore.toExtends.IInventoryImpl;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IChatComponent;

public abstract class TileEntityEPBase extends IInventoryImpl implements IInventory{
    /**
     ここでコンテナー内のアイテムを全部空にする処理を詰め込むっぽい。
     */
    public abstract void clear();

    /**
     ローカライズ後の名前。
     */
    public abstract IChatComponent getDisplayName();
}
