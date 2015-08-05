package com.mods.kina.ExperiencePower.item;

import com.google.common.base.Objects;
import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.KinaCore.misclib.KinaLib;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.oredict.OreDictionary;

public class ItemBlockMemoryBook extends ItemEPBase{
    public ItemBlockMemoryBook(){
        setCreativeTab(EnumEPCreativeTab.ITEM);
        setUnlocalizedName("memory_book");
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        switch(getMode(stack)){
            case 0:
                storeBlock(stack, worldIn.getBlockState(pos), 9);
                break;
            case 1:
                storeBlockFuzzy(stack, worldIn.getBlockState(pos).getBlock(), 9);
            case 2:
                storeBlocksByOreDict(stack, worldIn.getBlockState(pos), 9);
                break;
        }
        return false;
    }

    public boolean hasMemory(ItemStack stack){
        return stack.getSubCompound("memory", false) != null;
    }

    public void onItemRightClick(ItemStack stack, EntityPlayer player){
        if(!hasMemory(stack)) return;
        if(player.isSneaking()){
            changeMode(stack);
            player.addChatComponentMessage(new ChatComponentText("Mode Changed To" + modeString(getMode(stack))));
            return;
        }
        if(getMode(stack) == 3){
            //Change range.
            return;
        }
        NBTTagCompound memory = stack.getSubCompound("memory", false);
        NBTTagList list = memory.getTagList("blockList", 10);
        for(int i = 0; i < list.tagCount(); i++){
            NBTTagCompound entry = list.getCompoundTagAt(i);
            player.addChatComponentMessage(new ChatComponentText(Objects.toStringHelper(this).addValue(Block.getStateById(entry.getInteger("stateID"))).toString()));
        }
    }

    /****** STORE *******************************************************************************************************/

    public ItemStack storeBlocksByBook(ItemStack stack, ItemStack book){
        if(!ItemEditableBook.validBookTagContents(book.getTagCompound())) return stack;
        NBTTagList pages = book.getTagCompound().getTagList("pages", 8);
        for(int i = 0; i < pages.tagCount(); i++)
            for(String s : pages.getStringTagAt(i).split("\\n")){
                s = s.replaceAll("\\s", "");
                String[] prop = s.split(",");
                if(prop.length < 2){
                    storeBlocksByOreDict(stack, prop[0], 9);
                    storeBlock(stack, Block.getBlockFromName(prop[0]).getDefaultState(), 9);
                }else storeBlock(stack, Block.getBlockFromName(prop[0]).getStateFromMeta(Integer.parseInt(prop[1])), 9);
            }
        return stack;
    }

    public ItemStack storeBlocksByOreDict(ItemStack stack, IBlockState state, int range){
        int meta = state.getBlock().getMetaFromState(state);
        return storeBlocksByOreDict(stack, new ItemStack(state.getBlock(), 1, meta), range);
    }

    private ItemStack storeBlocksByOreDict(ItemStack stack, ItemStack ore, int range){
        for(int i : OreDictionary.getOreIDs(ore)){
            storeBlocksByOreDict(stack, OreDictionary.getOreName(i), range);
        }
        return stack;
    }

    private ItemStack storeBlocksByOreDict(ItemStack stack, String oreID, int range){
        for(ItemStack ore : OreDictionary.getOres(oreID)){
            storeBlock(stack, Block.getBlockFromItem(ore.getItem()).getStateFromMeta(ore.getItemDamage()), range);
        }
        return stack;
    }

    public ItemStack storeBlock(ItemStack stack, IBlockState state, int range){
        return storeBlock(stack, Block.getStateId(state), range);
    }

    public ItemStack storeBlockFuzzy(ItemStack stack, Block block, int range){
        for(Object raw : block.getBlockState().getValidStates()){
            IBlockState state = (IBlockState) raw;
            storeBlock(stack, Block.getStateId(state), range);
        }
        return stack;
    }

    private ItemStack storeBlock(ItemStack stack, int stateID, int range){
        NBTTagCompound memory = stack.getSubCompound("memory", true);
        NBTTagList list = memory.hasKey("blockList") ? memory.getTagList("blockList", Constants.NBT.TAG_COMPOUND) : new NBTTagList();
        storeInfo(list, stateID, range);
        memory.setTag("blockList", list);
        return stack;
    }

    private void storeInfo(NBTTagList list, int stateID, int range){
        NBTTagCompound tag;
        for(int i = 0; i < list.tagCount(); i++){
            tag = list.getCompoundTagAt(i);
            if(!tag.hasKey("stateID") || tag.getInteger("stateID") != stateID) continue;
            if(tag.hasKey("range") && tag.getInteger("range") == range){
                tag.setInteger("range", range);
                return;
            }
        }
        tag = new NBTTagCompound();
        tag.setInteger("stateID", stateID);
        tag.setInteger("range", range);
        list.appendTag(tag);
    }

    /************************************************MODE**************************************************************/

    /**
     0:normal 1:fuzzy 2:ore_dict 3:setting
     */
    public ItemStack changeMode(ItemStack stack, int mode){
        NBTTagCompound memory = stack.getSubCompound("memory", true);
        memory.setInteger("mode", mode);
        return stack;
    }

    public ItemStack changeMode(ItemStack stack){
        return changeMode(stack, KinaLib.lib.cycleNumber(getMode(stack), 3));
    }

    public int getMode(ItemStack stack){
        boolean has = stack.getSubCompound("memory", true).hasKey("mode");
        if(!has){
            changeMode(stack, 0);
        }
        return stack.getSubCompound("memory", false).getInteger("mode");
    }

    public String modeString(int mode){
        switch(mode){
            case 0:
                return "Normal";
            case 1:
                return "Fuzzy";
            case 2:
                return "OreDict";
            case 3:
                return "Setting";
            default:
                return "Unknown";
        }
    }

    /******************************************* MISC *******************************************************************/


    public boolean hasEffect(ItemStack stack){
        return hasMemory(stack);
    }
}
