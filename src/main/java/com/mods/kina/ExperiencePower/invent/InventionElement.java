package com.mods.kina.ExperiencePower.invent;

import com.mods.kina.ExperiencePower.collection.EnumEPInvention;
import com.mods.kina.ExperiencePower.collection.EnumEPInventionPage;
import net.minecraft.block.Block;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.IStatStringFormat;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class InventionElement{
    /** The Stat ID */
    public final String statId;
    /** Is the column (related to center of achievement gui, in 24 pixels unit) that the achievement will be displayed. */
    public final int displayColumn;
    /** Is the row (related to center of achievement gui, in 24 pixels unit) that the achievement will be displayed. */
    public final int displayRow;
    /** Holds the parent achievement, that must be taken before this achievement is avaiable. */
    public final InventionElement[] parentAchievements;
    /** Holds the ItemStack that will be used to draw the achievement into the GUI. */
    public final ItemStack theItemStack;
    /** The Stat name */
    private final IChatComponent statName;
    /** Holds the description of the achievement, ready to be formatted and/or displayed. */
    private final String achievementDescription;
    public boolean isIndependent;
    public EnumEPInventionPage parentPage;
    /**
     Holds a string formatter for the achievement, some of then needs extra dynamic info - like the key used to open the
     inventory.
     */
    @SideOnly(Side.CLIENT)
    private IStatStringFormat statStringFormatter;
    /**
     Special achievements have a 'spiked' (on normal texture pack) frame, special achievements are the hardest ones to
     achieve.
     */
    private boolean isSpecial;

    public InventionElement(String p_i46327_1_, String p_i46327_2_, int p_i46327_3_, int p_i46327_4_, Item p_i46327_5_, InventionElement... p_i46327_6_){
        this(p_i46327_1_, p_i46327_2_, p_i46327_3_, p_i46327_4_, new ItemStack(p_i46327_5_), p_i46327_6_);
    }

    public InventionElement(String p_i45301_1_, String p_i45301_2_, int p_i45301_3_, int p_i45301_4_, Block p_i45301_5_, InventionElement... p_i45301_6_){
        this(p_i45301_1_, p_i45301_2_, p_i45301_3_, p_i45301_4_, new ItemStack(p_i45301_5_), p_i45301_6_);
    }

    public InventionElement(String p_i45302_1_, String p_i45302_2_, int p_i45302_3_, int p_i45302_4_, ItemStack p_i45302_5_, InventionElement... p_i45302_6_){
        statId = p_i45302_1_;
        statName = new ChatComponentTranslation("invention." + p_i45302_2_);
        theItemStack = p_i45302_5_;
        achievementDescription = "invention." + p_i45302_2_ + ".desc";
        displayColumn = p_i45302_3_;
        displayRow = p_i45302_4_;

        if(p_i45302_3_ < EnumEPInvention.minDisplayColumn){
            EnumEPInvention.minDisplayColumn = p_i45302_3_;
        }

        if(p_i45302_4_ < EnumEPInvention.minDisplayRow){
            EnumEPInvention.minDisplayRow = p_i45302_4_;
        }

        if(p_i45302_3_ > EnumEPInvention.maxDisplayColumn){
            EnumEPInvention.maxDisplayColumn = p_i45302_3_;
        }

        if(p_i45302_4_ > EnumEPInvention.maxDisplayRow){
            EnumEPInvention.maxDisplayRow = p_i45302_4_;
        }

        parentAchievements = p_i45302_6_;
    }

    public InventionElement func_180789_a(){
        isIndependent = true;
        return this;
    }

    /**
     Special achievements have a 'spiked' (on normal texture pack) frame, special achievements are the hardest ones to
     achieve.
     */
    public InventionElement setSpecial(){
        isSpecial = true;
        return this;
    }

    public IChatComponent getStatName(){
        IChatComponent ichatcomponent = statName.createCopy();
        ichatcomponent.getChatStyle().setColor(EnumChatFormatting.GRAY);
        ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(statId)));
        ichatcomponent.getChatStyle().setColor(getSpecial() ? EnumChatFormatting.DARK_PURPLE : EnumChatFormatting.GREEN);
        return ichatcomponent;
    }

    /**
     Returns the fully description of the achievement - ready to be displayed on screen.
     */
    @SideOnly(Side.CLIENT)
    public String getDescription(){
        return statStringFormatter != null ? statStringFormatter.formatString(StatCollector.translateToLocal(achievementDescription)) : StatCollector.translateToLocal(achievementDescription);
    }

    /**
     Defines a string formatter for the achievement.
     */
    @SideOnly(Side.CLIENT)
    public InventionElement setStatStringFormatter(IStatStringFormat p_75988_1_){
        statStringFormatter = p_75988_1_;
        return this;
    }

    /**
     Special achievements have a 'spiked' (on normal texture pack) frame, special achievements are the hardest ones to
     achieve.
     */
    public boolean getSpecial(){
        return isSpecial;
    }

    public EnumEPInventionPage getPage(){
        return parentPage;
    }

    public InventionElement setPage(EnumEPInventionPage page){
        parentPage = page;
        return this;
    }
}
