package com.switchworldsandservers.client.mixin;

import com.switchworldsandservers.client.SwitchWorldsAndServersServerScreen;
import com.switchworldsandservers.client.SwitchWorldsAndServersWorldScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {
    protected PauseScreenMixin(Component title) {
        super(title);
    }

    @Inject(method = "init()V", at = @At("RETURN"))
    private void switchWorldsAndServers$addButtons(CallbackInfo ci) {
        Button optionsButton = null;
        Button lanButton = null;
        for (GuiEventListener child : this.children()) {
            if (child instanceof Button button) {
                Component msg = button.getMessage();
                if (msg != null && msg.getContents() instanceof TranslatableContents translatable) {
                    String key = translatable.getKey();
                    if ("menu.options".equals(key)) {
                        optionsButton = button;
                        break;
                    }
                }
            }
        }

        if (optionsButton == null) {
            return;
        }

        for (GuiEventListener child : this.children()) {
            if (child instanceof Button button && button != optionsButton) {
                if (button.getY() == optionsButton.getY() && button.getX() > optionsButton.getX()) {
                    lanButton = button;
                    break;
                }
            }
        }

        int btnY = optionsButton.getY() - 24;
        int btnHeight = optionsButton.getHeight();

        for (GuiEventListener child : this.children()) {
            if (child instanceof AbstractWidget widget) {
                if (widget.getY() <= btnY) {
                    widget.setY(widget.getY() - 24);
                }
            }
        }

        if (lanButton != null) {
            Button switchWorld = Button.builder(Component.translatable("switch_worlds_and_servers.switch_world"), btn -> {
                this.minecraft.setScreenAndShow(new SwitchWorldsAndServersWorldScreen(this));
            }).bounds(optionsButton.getX(), btnY, optionsButton.getWidth(), btnHeight).build();

            Button switchServer = Button.builder(Component.translatable("switch_worlds_and_servers.switch_server"), btn -> {
                this.minecraft.setScreenAndShow(new SwitchWorldsAndServersServerScreen(this));
            }).bounds(lanButton.getX(), btnY, lanButton.getWidth(), btnHeight).build();

            this.addRenderableWidget(switchWorld);
            this.addRenderableWidget(switchServer);
        } else {
            int optionsX = optionsButton.getX();
            int optionsWidth = optionsButton.getWidth();
            int halfWidth = (optionsWidth - 8) / 2;

            Button switchWorld = Button.builder(Component.translatable("switch_worlds_and_servers.switch_world"), btn -> {
                this.minecraft.setScreenAndShow(new SwitchWorldsAndServersWorldScreen(this));
            }).bounds(optionsX, btnY, halfWidth, btnHeight).build();

            Button switchServer = Button.builder(Component.translatable("switch_worlds_and_servers.switch_server"), btn -> {
                this.minecraft.setScreenAndShow(new SwitchWorldsAndServersServerScreen(this));
            }).bounds(optionsX + halfWidth + 8, btnY, halfWidth, btnHeight).build();

            this.addRenderableWidget(switchWorld);
            this.addRenderableWidget(switchServer);
        }
    }
}
