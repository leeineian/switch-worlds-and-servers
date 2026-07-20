package com.switchworldsandservers.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;

public final class SwitchWorldsAndServersWorldScreen extends SelectWorldScreen {
    public SwitchWorldsAndServersWorldScreen(Screen parent) {
        super(parent);
    }

    @Override
    public Component getTitle() {
        return Component.translatable("switch_worlds_and_servers.world_list.title");
    }

    @Override
    public boolean isPauseScreen() {
        return true;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.fill(0, 0, this.width, this.height, 0x55000000);
        graphics.fill(0, 0, this.width, this.height, 0x22000000);
    }

    public void switchWorldsAndServers$leaveServerAndOpenWorld(String levelId) {
        this.minecraft.disconnect(new SwitchWorldsAndServersWorldHandoffScreen(levelId), false);
    }
}
