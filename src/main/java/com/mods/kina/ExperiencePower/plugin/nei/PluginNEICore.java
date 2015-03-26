package com.mods.kina.ExperiencePower.plugin.nei;

import codechicken.nei.api.API;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.mods.kina.ExperiencePower.plugin.nei.handler.ShapedRecipeWithExpHandler;
import com.mods.kina.ExperiencePower.plugin.nei.handler.ShapelessRecipeWithExpHandler;

public class PluginNEICore{
    public static ShapedRecipeWithExpHandler shapedRecipeWithExp;
    public static ShapelessRecipeWithExpHandler shapelessRecipeWithExp;

    public static void load(){
        shapedRecipeWithExp = new ShapedRecipeWithExpHandler();
        shapelessRecipeWithExp = new ShapelessRecipeWithExpHandler();

        registerToAPI(shapedRecipeWithExp);
        registerToAPI(shapelessRecipeWithExp);
    }

    private static void registerToAPI(TemplateRecipeHandler handler){
        API.registerRecipeHandler(handler);
        API.registerUsageHandler(handler);
        API.registerGuiOverlay(handler.getGuiClass(), handler.getOverlayIdentifier(), 0, 0);
    }
}
