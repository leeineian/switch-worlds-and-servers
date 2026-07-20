package com.switchworldsandservers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;

public final class SwitchWorldsAndServersHandoffScreen extends GenericMessageScreen {
    private final ServerData server;
    private int ticks;
    private boolean connectionStarted;

    public SwitchWorldsAndServersHandoffScreen(ServerData server) {
        super(Component.translatable("switch_worlds_and_servers.saving"));
        this.server = server;
    }

    @Override
    public void tick() {
        super.tick();
        this.ticks++;
        if (this.connectionStarted || this.ticks < 2) {
            return;
        }
        if (this.minecraft.level != null || this.minecraft.getConnection() != null || this.minecraft.hasSingleplayerServer()) {
            return;
        }
        this.connectionStarted = true;
        JoinMultiplayerScreen failureParent = new JoinMultiplayerScreen(new TitleScreen());
        ConnectScreen.startConnecting(failureParent, this.minecraft, ServerAddress.parseString(this.server.ip), this.server, false, null);
    }
}
