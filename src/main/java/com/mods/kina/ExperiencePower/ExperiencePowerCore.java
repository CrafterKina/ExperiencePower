package com.mods.kina.ExperiencePower;

import com.mods.kina.ExperiencePower.annotation.processor.EPPropProcessor;
import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import com.mods.kina.ExperiencePower.config.ConfigMaker;
import com.mods.kina.ExperiencePower.event.handler.EventHandler;
import com.mods.kina.ExperiencePower.loader.EPModelLoader;
import com.mods.kina.ExperiencePower.plugin.PluginLoader;
import com.mods.kina.ExperiencePower.proxy.CommonProxy;
import com.mods.kina.ExperiencePower.register.*;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 根幹クラス。大したことはしないはず。
 */
@Mod(modid = StaticFieldCollection.MODID, dependencies = "required-after:kina_core", useMetadata = true)
public class ExperiencePowerCore{

    @Mod.Instance(StaticFieldCollection.MODID)
    public static ExperiencePowerCore core;

    /**
     蔵鯖での処理分け。
     */
    @SidedProxy(modId = StaticFieldCollection.MODID,clientSide = "com.mods.kina.ExperiencePower.proxy.ClientProxy",serverSide = "com.mods.kina.ExperiencePower.proxy.CommonProxy")
    public static CommonProxy proxy;

    /**
     初期化前処理
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        EPPropProcessor.instance.process();
        ConfigMaker.createConfig(e);
        MinecraftForge.EVENT_BUS.register(EventHandler.normal);
        MinecraftForge.TERRAIN_GEN_BUS.register(EventHandler.terrainGen);
        MinecraftForge.ORE_GEN_BUS.register(EventHandler.oreGen);
        FMLCommonHandler.instance().bus().register(EventHandler.fml);
        BlockRegistrar.registerBlock();
        ItemRegistrar.registerItem();
        BlockRegistrar.registerModel();
        ItemRegistrar.registerModel();
        proxy.registerRender();
        ModelLoaderRegistry.registerLoader(EPModelLoader.instance);
        OreDictionaryRegistrar.registerOres();
        AchievementRegistrar.registerAchievement();
        InventionRegistrar.registerInvention();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }

    /**
     初期化中処理
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        EntityRegistrar.registerEntity();
    }

    /**
     初期化後処理
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        SmeltRecipeRegistrar.registerRecipes();
        CraftRecipeRegistrar.registerRecipes();
        PluginLoader.load();
    }

}
