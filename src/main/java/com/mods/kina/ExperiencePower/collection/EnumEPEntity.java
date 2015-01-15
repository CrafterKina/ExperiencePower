package com.mods.kina.ExperiencePower.collection;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public enum EnumEPEntity{
    ;
    private final Class<? extends Entity> entityClass;
    private final int trackingRange;
    private final int updateFrequency;
    private final boolean sendsVelocityUpdates;
    private final Render render;
    private final boolean isLiving;
    private final int weightedProb;
    private final int groupMin;
    private final int groupMax;
    private final EnumCreatureType typeOfCreature;
    private final BiomeGenBase[] spawnBiomes;

    /**
     @param entityClass
     Entityのクラス
     @param trackingRange
     Mob探知の範囲
     @param updateFrequency
     tickあたりの更新頻度
     @param sendsVelocityUpdates
     速度情報の有無
     @param isLiving
     以降のパラメータを使用するかどうか
     @param weightedProb
     スポーン確率
     @param groupMin
     まとまりでの最小出現数
     @param groupMax
     まとまりでの最大出現数
     @param typeOfCreature
     Mobのタイプ
     @param spawnBiomes
     スポーンするバイオーム
     */
    EnumEPEntity(Class<? extends Entity> entityClass, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, Render render, boolean isLiving, int weightedProb, int groupMin, int groupMax, EnumCreatureType typeOfCreature, BiomeGenBase... spawnBiomes){
        this.entityClass = entityClass;
        this.trackingRange = trackingRange;
        this.updateFrequency = updateFrequency;
        this.sendsVelocityUpdates = sendsVelocityUpdates;
        this.render = render;
        this.isLiving = isLiving;
        this.weightedProb = weightedProb;
        this.groupMin = groupMin;
        this.groupMax = groupMax;
        this.typeOfCreature = typeOfCreature;
        this.spawnBiomes = spawnBiomes;
    }

    //Non-Living//
    EnumEPEntity(Class<? extends Entity> entityClass, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, Render render){
        this(entityClass, trackingRange, updateFrequency, sendsVelocityUpdates, render, false, 0, 0, 0, null);
    }

    public Class<? extends Entity> getEntityClass(){
        return entityClass;
    }

    public int getTrackingRange(){
        return trackingRange;
    }

    public int getUpdateFrequency(){
        return updateFrequency;
    }

    public boolean isSendsVelocityUpdates(){
        return sendsVelocityUpdates;
    }

    public boolean isLiving(){
        return isLiving;
    }

    public int getWeightedProb(){
        return weightedProb;
    }

    public int getGroupMin(){
        return groupMin;
    }

    public int getGroupMax(){
        return groupMax;
    }

    public EnumCreatureType getTypeOfCreature(){
        return typeOfCreature;
    }

    public BiomeGenBase[] getSpawnBiomes(){
        return spawnBiomes;
    }

    public Render getRender(){
        return render;
    }
}
