package com.mods.kina.ExperiencePower.proxy;

import com.mods.kina.ExperiencePower.render.renderer.tileentity.TESRSimpleExpInjector;
import com.mods.kina.ExperiencePower.tileentity.TileEntitySimpleExpInjector;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy{
    public void registerRender(){
        ClientRegistry.registerTileEntity(TileEntitySimpleExpInjector.class, "TileEntitySimpleExpInjector", TESRSimpleExpInjector.render);
    }
}
