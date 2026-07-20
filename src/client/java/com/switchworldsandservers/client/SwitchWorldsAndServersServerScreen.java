package com.switchworldsandservers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;

public final class SwitchWorldsAndServersServerScreen extends JoinMultiplayerScreen {
    private final Screen parent;

    public SwitchWorldsAndServersServerScreen(Screen parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("switch_worlds_and_servers.server_list.title");
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

    public void switchWorldsAndServers$refresh() {
        this.minecraft.setScreenAndShow(new SwitchWorldsAndServersServerScreen(this.parent));
    }

    public void switchWorldsAndServers$saveAndConnect(ServerData serverData) {
        Minecraft.getInstance().disconnect(new SwitchWorldsAndServersHandoffScreen(serverData), false);
    }
}
