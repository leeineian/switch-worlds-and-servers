package com.switchworldsandservers.client;

import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.Component;

public final class SwitchWorldsAndServersWorldHandoffScreen extends GenericMessageScreen {
    private final String levelId;
    private int cleanTicks;
    private boolean loadStarted;

    public SwitchWorldsAndServersWorldHandoffScreen(String levelId) {
        super(Component.translatable("switch_worlds_and_servers.leaving_server"));
        this.levelId = levelId;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.loadStarted) {
            return;
        }
        if (this.minecraft.level != null || this.minecraft.getConnection() != null || this.minecraft.hasSingleplayerServer()) {
            this.cleanTicks = 0;
            return;
        }
        this.cleanTicks++;
        if (this.cleanTicks < 10) {
            return;
        }
        this.loadStarted = true;
        this.minecraft.createWorldOpenFlows().openWorld(this.levelId, () -> this.minecraft.setScreenAndShow(new SelectWorldScreen(new TitleScreen())));
    }
}
