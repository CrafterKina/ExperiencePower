package com.mods.kina.ExperiencePower.gui;

import com.mods.kina.ExperiencePower.collection.EnumEPInvention;
import com.mods.kina.ExperiencePower.collection.EnumEPInventionPage;
import com.mods.kina.ExperiencePower.invent.InventionElement;
import com.mods.kina.ExperiencePower.item.ItemInventionNote;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GuiInventionNote extends GuiScreen{
    private static final int field_146572_y = EnumEPInvention.minDisplayColumn * 24 - 112;
    private static final int field_146571_z = EnumEPInvention.minDisplayRow * 24 - 112;
    private static final int field_146559_A = EnumEPInvention.maxDisplayColumn * 24 - 77;
    private static final int field_146560_B = EnumEPInvention.maxDisplayRow * 24 - 77;
    private static final ResourceLocation field_146561_C = new ResourceLocation("textures/gui/achievement/achievement_background.png");
    public List<List<Byte>> unlockedList = new ArrayList<List<Byte>>(EnumEPInventionPage.values().length);
    protected GuiScreen parentScreen;
    protected int field_146555_f = 256;
    protected int field_146557_g = 202;
    protected int field_146563_h;
    protected int field_146564_i;
    protected float field_146570_r = 1.0F;
    protected double field_146569_s;
    protected double field_146568_t;
    protected double field_146567_u;
    protected double field_146566_v;
    protected double field_146565_w;
    protected double field_146573_x;
    private ItemStack itemStack;
    private ItemInventionNote item;
    private int field_146554_D;
    private boolean loadingAchievements = true;

    private int currentPage = 0;
    private GuiButton button;
    //private LinkedList<InventionElement> minecraftAchievements = new LinkedList<InventionElement>();

    public GuiInventionNote(GuiScreen p_i45026_1_, ItemStack stack){
        this.parentScreen = p_i45026_1_;
        itemStack = stack;
        item = (ItemInventionNote) stack.getItem();
        short short1 = 141;
        short short2 = 141;
        this.field_146569_s = this.field_146567_u = this.field_146565_w = (double) (EnumEPInvention.Sword.getInventionElement().displayColumn * 24 - short1 / 2 - 12);
        this.field_146568_t = this.field_146566_v = this.field_146573_x = (double) (EnumEPInvention.Sword.getInventionElement().displayRow * 24 - short2 / 2);
        for(EnumEPInventionPage page : EnumEPInventionPage.values())
            for(InventionElement element : page.getPage().elementList){
                ArrayList<Byte> byteList = new ArrayList<Byte>(page.getPage().elementList.size());
                for(int i = 0; i < page.getPage().elementList.size(); i++){
                    byteList.add(i, (byte) 0);
                }
                unlockedList.add(byteList);
            }
        onOpenGui();
    }

    /**
     Adds the buttons (and other controls) to the screen in question.
     */
    @SuppressWarnings("unchecked")
    public void initGui(){
        this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
        this.buttonList.clear();
        this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 24, this.height / 2 + 74, 80, 20, I18n.format("gui.done")));
        this.buttonList.add(button = new GuiButton(2, (width - field_146555_f) / 2 + 24, height / 2 + 74, 125, 20, EnumEPInventionPage.values()[currentPage].name()));
    }

    protected void actionPerformed(GuiButton button) throws IOException{
        if(!this.loadingAchievements){
            if(button.id == 1){
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                onCloseGui();
            }

            if(button.id == 2){
                currentPage++;
                if(currentPage >= EnumEPInventionPage.values().length){
                    currentPage = 0;
                }
                this.button.displayString = EnumEPInventionPage.values()[currentPage].name();
            }

            if(button.id > 2){
                int bid = button.id;
                int id = bid - 2;
                if(item.canUnlockInvention(unlockedList, currentPage, id))
                    unlockedList.get(currentPage).add(id, (byte) 1);
            }
        }
    }

    /**
     Fired when a key is typed (except F11 who toggle full screen). This is the equivalent of
     KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
        if(keyCode == this.mc.gameSettings.keyBindInventory.getKeyCode()){
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
            onCloseGui();
        }else{
            super.keyTyped(typedChar, keyCode);
        }
    }

    /**
     Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        if(!loadingAchievements){
            int k;

            if(Mouse.isButtonDown(0)){
                k = (this.width - this.field_146555_f) / 2;
                int l = (this.height - this.field_146557_g) / 2;
                int i1 = k + 8;
                int j1 = l + 17;

                if((this.field_146554_D == 0 || this.field_146554_D == 1) && mouseX >= i1 && mouseX < i1 + 224 && mouseY >= j1 && mouseY < j1 + 155){
                    if(this.field_146554_D == 0){
                        this.field_146554_D = 1;
                    }else{
                        this.field_146567_u -= (double) ((float) (mouseX - this.field_146563_h) * this.field_146570_r);
                        this.field_146566_v -= (double) ((float) (mouseY - this.field_146564_i) * this.field_146570_r);
                        this.field_146565_w = this.field_146569_s = this.field_146567_u;
                        this.field_146573_x = this.field_146568_t = this.field_146566_v;
                    }

                    this.field_146563_h = mouseX;
                    this.field_146564_i = mouseY;
                }
            }else{
                this.field_146554_D = 0;
            }

            k = Mouse.getDWheel();
            float f4 = this.field_146570_r;

            if(k < 0){
                this.field_146570_r += 0.25F;
            }else if(k > 0){
                this.field_146570_r -= 0.25F;
            }

            this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);

            if(this.field_146570_r != f4){
                float f6 = f4 - this.field_146570_r;
                float f5 = f4 * (float) this.field_146555_f;
                float f1 = f4 * (float) this.field_146557_g;
                float f2 = this.field_146570_r * (float) this.field_146555_f;
                float f3 = this.field_146570_r * (float) this.field_146557_g;
                this.field_146567_u -= (double) ((f2 - f5) * 0.5F);
                this.field_146566_v -= (double) ((f3 - f1) * 0.5F);
                this.field_146565_w = this.field_146569_s = this.field_146567_u;
                this.field_146573_x = this.field_146568_t = this.field_146566_v;
            }

            if(this.field_146565_w < (double) field_146572_y){
                this.field_146565_w = (double) field_146572_y;
            }

            if(this.field_146573_x < (double) field_146571_z){
                this.field_146573_x = (double) field_146571_z;
            }

            if(this.field_146565_w >= (double) field_146559_A){
                this.field_146565_w = (double) (field_146559_A - 1);
            }

            if(this.field_146573_x >= (double) field_146560_B){
                this.field_146573_x = (double) (field_146560_B - 1);
            }

            this.drawDefaultBackground();
            this.drawAchievementScreen(mouseX, mouseY, partialTicks);
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            this.drawTitle();
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
    }

    /**
     Called from the main game loop to update the screen.
     */
    public void updateScreen(){
        if(!this.loadingAchievements){
            this.field_146569_s = this.field_146567_u;
            this.field_146568_t = this.field_146566_v;
            double d0 = this.field_146565_w - this.field_146567_u;
            double d1 = this.field_146573_x - this.field_146566_v;

            if(d0 * d0 + d1 * d1 < 4.0D){
                this.field_146567_u += d0;
                this.field_146566_v += d1;
            }else{
                this.field_146567_u += d0 * 0.85D;
                this.field_146566_v += d1 * 0.85D;
            }
        }
    }

    protected void drawTitle(){
        int i = (this.width - this.field_146555_f) / 2;
        int j = (this.height - this.field_146557_g) / 2;
        this.fontRendererObj.drawString(I18n.format("gui.achievements"), i + 15, j + 5, 4210752);
    }

    protected void drawAchievementScreen(int p_146552_1_, int p_146552_2_, float p_146552_3_){
        int k = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * (double) p_146552_3_);
        int l = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * (double) p_146552_3_);

        if(k < field_146572_y){
            k = field_146572_y;
        }

        if(l < field_146571_z){
            l = field_146571_z;
        }

        if(k >= field_146559_A){
            k = field_146559_A - 1;
        }

        if(l >= field_146560_B){
            l = field_146560_B - 1;
        }

        int i1 = (this.width - this.field_146555_f) / 2;
        int j1 = (this.height - this.field_146557_g) / 2;
        int k1 = i1 + 16;
        int l1 = j1 + 17;
        this.zLevel = 0.0F;
        GlStateManager.depthFunc(518);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) k1, (float) l1, -200.0F);
        GlStateManager.scale(1.0F / this.field_146570_r, 1.0F / this.field_146570_r, 0.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        int i2 = k + 288 >> 4;
        int j2 = l + 288 >> 4;
        int k2 = (k + 288) % 16;
        int l2 = (l + 288) % 16;
        Random random = new Random();
        float f1 = 16.0F / this.field_146570_r;
        float f2 = 16.0F / this.field_146570_r;
        int i3;
        float f3;
        int j3;
        int k3;

        for(i3 = 0; (float) i3 * f1 - (float) l2 < 155.0F; ++i3){
            f3 = 0.6F - (float) (j2 + i3) / 25.0F * 0.3F;
            GlStateManager.color(f3, f3, f3, 1.0F);

            for(j3 = 0; (float) j3 * f2 - (float) k2 < 224.0F; ++j3){
                random.setSeed((long) (this.mc.getSession().getPlayerID().hashCode() + i2 + j3 + (j2 + i3) * 16));
                k3 = random.nextInt(1 + j2 + i3) + (j2 + i3) / 2;
                TextureAtlasSprite textureatlassprite = this.func_175371_a(Blocks.sand);

                if(k3 <= 37 && j2 + i3 != 35){
                    if(k3 == 22){
                        if(random.nextInt(2) == 0){
                            textureatlassprite = this.func_175371_a(Blocks.diamond_ore);
                        }else{
                            textureatlassprite = this.func_175371_a(Blocks.redstone_ore);
                        }
                    }else if(k3 == 10){
                        textureatlassprite = this.func_175371_a(Blocks.iron_ore);
                    }else if(k3 == 8){
                        textureatlassprite = this.func_175371_a(Blocks.coal_ore);
                    }else if(k3 > 4){
                        textureatlassprite = this.func_175371_a(Blocks.stone);
                    }else if(k3 > 0){
                        textureatlassprite = this.func_175371_a(Blocks.dirt);
                    }
                }else{
                    Block block = Blocks.bedrock;
                    textureatlassprite = this.func_175371_a(block);
                }

                this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
                this.drawTexturedModalRect(j3 * 16 - k2, i3 * 16 - l2, textureatlassprite, 16, 16);
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.depthFunc(515);
        this.mc.getTextureManager().bindTexture(field_146561_C);
        int l3;
        int i4;
        int l4;

        List<InventionElement> inventionList = EnumEPInventionPage.values()[currentPage].getPage().getElements();
        for(i3 = 0; i3 < inventionList.size(); ++i3){
            InventionElement achievement1 = inventionList.get(i3);

            if(achievement1.parentAchievements != null && inventionList.contains(achievement1.parentAchievements)){
                j3 = achievement1.displayColumn * 24 - k + 11;
                k3 = achievement1.displayRow * 24 - l + 11;
                int k4 = achievement1.parentAchievements[0].displayColumn * 24 - k + 11;
                l4 = achievement1.parentAchievements[0].displayRow * 24 - l + 11;
                boolean flag5 = item.hasInventionUnlocked(unlockedList, currentPage, i3);
                boolean flag6 = item.canUnlockInvention(unlockedList, currentPage, i3);
                l3 = item.getDistanceFromOpened(unlockedList, currentPage, i3);

                if(l3 <= 4){
                    i4 = -16777216;

                    if(flag5){
                        i4 = -6250336;
                    }else if(flag6){
                        i4 = -16711936;
                    }

                    this.drawHorizontalLine(j3, k4, k3, i4);
                    this.drawVerticalLine(k4, k3, l4, i4);

                    if(j3 > k4){
                        this.drawTexturedModalRect(j3 - 11 - 7, k3 - 5, 114, 234, 7, 11);
                    }else if(j3 < k4){
                        this.drawTexturedModalRect(j3 + 11, k3 - 5, 107, 234, 7, 11);
                    }else if(k3 > l4){
                        this.drawTexturedModalRect(j3 - 5, k3 - 11 - 7, 96, 234, 11, 7);
                    }else if(k3 < l4){
                        this.drawTexturedModalRect(j3 - 5, k3 + 11, 96, 241, 11, 7);
                    }
                }
            }
        }

        InventionElement achievement = null;
        f3 = (float) (p_146552_1_ - k1) * this.field_146570_r;
        float f4 = (float) (p_146552_2_ - l1) * this.field_146570_r;
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        int i5;
        int j5;

        for(k3 = 0; k3 < inventionList.size(); ++k3){
            InventionElement inventionElement2 = inventionList.get(k3);
            l4 = inventionElement2.displayColumn * 24 - k;
            i5 = inventionElement2.displayRow * 24 - l;

            if(l4 >= -24 && i5 >= -24 && (float) l4 <= 224.0F * this.field_146570_r && (float) i5 <= 155.0F * this.field_146570_r){
                j5 = item.getDistanceFromOpened(unlockedList, currentPage, k3);
                float f5;

                if(item.hasInventionUnlocked(unlockedList, currentPage, k3)){
                    f5 = 0.75F;
                    GlStateManager.color(f5, f5, f5, 1.0F);
                }else if(item.canUnlockInvention(unlockedList, currentPage, k3)){
                    f5 = 1.0F;
                    GlStateManager.color(f5, f5, f5, 1.0F);
                }else if(j5 < 3){
                    f5 = 0.3F;
                    GlStateManager.color(f5, f5, f5, 1.0F);
                }else if(j5 == 3){
                    f5 = 0.2F;
                    GlStateManager.color(f5, f5, f5, 1.0F);
                }else{
                    if(j5 != 4){
                        continue;
                    }

                    f5 = 0.1F;
                    GlStateManager.color(f5, f5, f5, 1.0F);
                }

                this.mc.getTextureManager().bindTexture(field_146561_C);

                GlStateManager.enableBlend(); // Forge: Specifically enable blend because it is needed here. And we fix Generic RenderItem's leakage of it.
                if(inventionElement2.getSpecial()){
                    this.drawTexturedModalRect(l4 - 2, i5 - 2, 26, 202, 26, 26);
                }else{
                    this.drawTexturedModalRect(l4 - 2, i5 - 2, 0, 202, 26, 26);
                }
                GlStateManager.disableBlend(); //Forge: Cleanup states we set.

                if(!item.canUnlockInvention(unlockedList, currentPage, k3)){
                    f5 = 0.1F;
                    GlStateManager.color(f5, f5, f5, 1.0F);
                    this.itemRender.func_175039_a(false);
                }

                GlStateManager.disableLighting(); //Forge: Make sure Lighting is disabled. Fixes MC-33065
                GlStateManager.enableCull();
                this.itemRender.renderItemAndEffectIntoGUI(inventionElement2.theItemStack, l4 + 3, i5 + 3);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.disableLighting();

                if(!item.canUnlockInvention(unlockedList, currentPage, k3)){
                    this.itemRender.func_175039_a(true);
                }

                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

                if(f3 >= (float) l4 && f3 <= (float) (l4 + 22) && f4 >= (float) i5 && f4 <= (float) (i5 + 22)){
                    achievement = inventionElement2;
                }
            }
        }

        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.popMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_146561_C);
        this.drawTexturedModalRect(i1, j1, 0, 0, this.field_146555_f, this.field_146557_g);
        this.zLevel = 0.0F;
        GlStateManager.depthFunc(515);
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        super.drawScreen(p_146552_1_, p_146552_2_, p_146552_3_);

        if(achievement != null){
            int id = item.getIDFromPage(currentPage, achievement);
            String s = achievement.getStatName().getUnformattedText();
            String s1 = achievement.getDescription();
            l4 = p_146552_1_ + 12;
            i5 = p_146552_2_ - 4;
            j5 = item.getDistanceFromOpened(unlockedList, currentPage, id);

            if(item.canUnlockInvention(unlockedList, currentPage, id)){
                l3 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                i4 = this.fontRendererObj.splitStringWidth(s1, l3);

                if(item.hasInventionUnlocked(unlockedList, currentPage, id)){
                    i4 += 12;
                }

                this.drawGradientRect(l4 - 3, i5 - 3, l4 + l3 + 3, i5 + i4 + 3 + 12, -1073741824, -1073741824);
                this.fontRendererObj.drawSplitString(s1, l4, i5 + 12, l3, -6250336);

                if(item.hasInventionUnlocked(unlockedList, currentPage, id)){
                    this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken"), (float) l4, (float) (i5 + i4 + 4), -7302913);
                }
            }else{
                int j4;
                String s2;

                if(j5 == 3){
                    s = I18n.format("achievement.unknown");
                    l3 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                    s2 = getStatNames(achievement.parentAchievements);
                    j4 = this.fontRendererObj.splitStringWidth(s2, l3);
                    this.drawGradientRect(l4 - 3, i5 - 3, l4 + l3 + 3, i5 + j4 + 12 + 3, -1073741824, -1073741824);
                    this.fontRendererObj.drawSplitString(s2, l4, i5 + 12, l3, -9416624);
                }else if(j5 < 3){
                    l3 = Math.max(this.fontRendererObj.getStringWidth(s), 120);
                    s2 = (new ChatComponentTranslation("achievement.requires", getStatNames(achievement.parentAchievements))).getUnformattedText();
                    j4 = this.fontRendererObj.splitStringWidth(s2, l3);
                    this.drawGradientRect(l4 - 3, i5 - 3, l4 + l3 + 3, i5 + j4 + 12 + 3, -1073741824, -1073741824);
                    this.fontRendererObj.drawSplitString(s2, l4, i5 + 12, l3, -9416624);
                }else{
                    s = null;
                }
            }

            if(s != null){
                this.fontRendererObj.drawStringWithShadow(s, (float) l4, (float) i5, item.canUnlockInvention(unlockedList, currentPage, id) ? (achievement.getSpecial() ? -128 : -1) : (achievement.getSpecial() ? -8355776 : -8355712));
            }
        }

        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        RenderHelper.disableStandardItemLighting();
    }

    public void onOpenGui(){
        readFromNBT(item.getNBT(itemStack));
        loadingAchievements = false;
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound){
        if(!nbtTagCompound.getTagList("page", 10).hasNoTags()){
            for(int i = 0; i < nbtTagCompound.getTagList("page", 10).tagCount(); i++){
                ArrayList<Byte> unlock = new ArrayList<Byte>();
                for(int j = 0; j < ((NBTTagList) nbtTagCompound.getTagList("page", 10).get(i)).tagCount(); j++){
                    unlock.add(((NBTTagByte) ((NBTTagList) nbtTagCompound.getTagList("page", 10).get(i)).get(j)).getByte());
                }
                unlockedList.add(unlock);
            }
        }else{
            item.initNBT(nbtTagCompound);
        }
    }

    public void onCloseGui(){
        writeToNBT(item.getNBT(itemStack));
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound){
        NBTTagList pageList = new NBTTagList();
        for(int i = 0; i < EnumEPInventionPage.values().length; i++){
            NBTTagList inventionList = new NBTTagList();
            for(int j = 0; j < EnumEPInventionPage.values()[i].getPage().getElements().size(); j++){
                NBTTagByte unlocked = new NBTTagByte(unlockedList.get(i).get(j));
                inventionList.appendTag(unlocked);
            }
            pageList.appendTag(inventionList);
        }
        nbtTagCompound.setTag("page", pageList);
    }

    private String getStatNames(InventionElement... elements){
        String s = "";
        for(InventionElement element : elements)
            for(InventionElement parent : element.parentAchievements){
                s += "\n" + (new ChatComponentTranslation("achievement.requires", parent.getStatName())).getUnformattedText();
            }
        return s;
    }

    private TextureAtlasSprite func_175371_a(Block p_175371_1_){
        return Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(p_175371_1_.getDefaultState());
    }

    /**
     Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame(){
        return false;
    }
}
