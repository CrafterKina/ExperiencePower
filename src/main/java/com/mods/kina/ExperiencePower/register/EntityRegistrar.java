package com.mods.kina.ExperiencePower.register;

public class EntityRegistrar{
    @SuppressWarnings("unchecked")
    public static void registerEntity(){
        /*for(EnumEPEntity epEntity : EnumEPEntity.values()){
            EntityRegistry.registerModEntity(epEntity.getEntityClass(), epEntity.name(), epEntity.ordinal(), ExperiencePowerCore.core, epEntity.getTrackingRange(), epEntity.getUpdateFrequency(), epEntity.isSendsVelocityUpdates());
            if(epEntity.isLiving()){
                EntityRegistry.addSpawn((Class<? extends EntityLiving>) epEntity.getEntityClass(), epEntity.getWeightedProb(), epEntity.getGroupMin(), epEntity.getGroupMax(), epEntity.getTypeOfCreature(), epEntity.getSpawnBiomes());
            }
            if(FMLCommonHandler.instance().getSide() == Side.CLIENT){
                RenderingRegistry.registerEntityRenderingHandler(epEntity.getEntityClass(), epEntity.getRender());
            }
        }*/
    }
}
