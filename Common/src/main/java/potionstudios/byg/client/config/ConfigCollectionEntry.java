package potionstudios.byg.client.config;

import blue.endless.jankson.JsonElement;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.List;

public class ConfigCollectionEntry extends ConfigEditEntry<JsonElement> {

    private final Button editButton;

    public ConfigCollectionEntry(Screen parent, String key, JsonElement val, String parentFilePath) {
        super(key, val);
        this.editButton = new Button(0, 0, 100, 20, new TranslatableComponent("Edit"), (button) -> {
            Minecraft.getInstance().setScreen(new ConfigEditScreen(parent, val, parentFilePath + "." + key));
            button.active = false;
        }) {
            protected MutableComponent createNarrationMessage() {
                return new TranslatableComponent("narrator.controls.reset", key);
            }
        };
    }

    @Override
    public void render(PoseStack pPoseStack, int pIndex, int pTop, int pLeft, int pWidth, int pHeight, int pMouseX, int pMouseY, boolean pIsMouseOver, float pPartialTick) {
        super.render(pPoseStack, pIndex, pTop, pLeft, pWidth, pHeight, pMouseX, pMouseY, pIsMouseOver, pPartialTick);
        this.editButton.x = (int) (this.keyScreenPosition.x + this.maxKeyWidth + 20);
        this.editButton.y = ((pTop + pHeight / 2 - 9 / 2));
        this.editButton.setWidth(pWidth - 20);
        this.editButton.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    public List<? extends GuiEventListener> children() {
        return ImmutableList.of(this.editButton);
    }

    public List<? extends NarratableEntry> narratables() {
        return ImmutableList.of(this.editButton);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            return this.editButton.mouseClicked(mouseX, mouseY, button);
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
