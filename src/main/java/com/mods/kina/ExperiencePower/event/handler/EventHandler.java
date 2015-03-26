package com.mods.kina.ExperiencePower.event.handler;

/**
 ここから各メソッドに分ける。
 分量少なければベタ書きでもいいかもしれない。
 */
public class EventHandler{
    public static final NormalEventHandler normal=new NormalEventHandler();
    public static final ClientEventHandler client = new ClientEventHandler();
    public static final TerrainGenEventHandler terrainGen=new TerrainGenEventHandler();
    public static final OreGenEventHandler oreGen=new OreGenEventHandler();
    public static final FMLEventHandler fml=new FMLEventHandler();
}
