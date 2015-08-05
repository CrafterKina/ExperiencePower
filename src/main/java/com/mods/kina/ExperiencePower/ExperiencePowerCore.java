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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 根幹クラス。大したことはしないはず。
 */
@Mod(modid = StaticFieldCollection.MODID, dependencies = "required-after:kina_core")
public class ExperiencePowerCore{

    @Mod.Instance(StaticFieldCollection.MODID)
    public static ExperiencePowerCore core;

    /**
     蔵鯖での処理分け。
     */
    @SidedProxy(modId = StaticFieldCollection.MODID, clientSide = "com.mods.kina.ExperiencePower.proxy.ClientProxy", serverSide = "com.mods.kina.ExperiencePower.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void fingerprintWarning(FMLFingerprintViolationEvent event){
        System.out.println("fingerprintStart");
        //MinecraftForge.EVENT_BUS.register(EventHandler.kina);
        System.out.println("fingerprintEnd");
    }

    /**
     初期化前処理
     */
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e){
        System.out.println("preInitStart");
        EPPropProcessor.instance.process();
        ConfigMaker.createConfig(e);
        EventHandler.register();
        BlockRegistrar.registerBlock();
        ItemRegistrar.registerItem();
        BlockRegistrar.registerModel();
        ItemRegistrar.registerModel();
        proxy.registerRender();
        ModelLoaderRegistry.registerLoader(EPModelLoader.instance);
        OreDictionaryRegistrar.registerOres();
        AchievementRegistrar.registerAchievement();
        InventionRegistrar.registerInvention();
        NetworkRegistry.INSTANCE.registerGuiHandler(core, proxy);
        System.out.println("preInitEnd");
    }

    /**
     初期化中処理
     */
    @Mod.EventHandler
    public void init(FMLInitializationEvent e){
        System.out.println("initStart");
        EntityRegistrar.registerEntity();
        System.out.println("initEnd");
    }

    /**
     初期化後処理
     */
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e){
        System.out.println("postInitStart");
        SmeltRecipeRegistrar.registerRecipes();
        CraftRecipeRegistrar.registerRecipes();
        PluginLoader.load();
        System.out.println("postInitEnd");
    }

    @Mod.EventHandler
    public void loadComplete(FMLLoadCompleteEvent e){
        System.out.println("loaded");
    }

}
