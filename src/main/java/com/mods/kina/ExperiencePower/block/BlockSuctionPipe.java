package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.tileentity.TileEntityBasicPipe;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;

public class BlockSuctionPipe extends BlockBasicPipe{
    public BlockSuctionPipe(){
        super();
        setUnlocalizedName("item_suction_pipe");
        UtilTileEntity.instance.registerTileEntity(TileEntityBasicPipe.class, "suction_pipe");
    }
}
