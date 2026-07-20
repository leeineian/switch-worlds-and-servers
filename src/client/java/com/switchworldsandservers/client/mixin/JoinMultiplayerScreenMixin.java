package com.switchworldsandservers.client.mixin;

import com.switchworldsandservers.client.SwitchWorldsAndServersServerScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JoinMultiplayerScreen.class)
abstract class JoinMultiplayerScreenMixin {
    @Inject(method = "join(Lnet/minecraft/client/multiplayer/ServerData;)V", at = @At("HEAD"), cancellable = true)
    private void switchWorldsAndServers$saveThenJoin(ServerData serverData, CallbackInfo callback) {
        if ((Object) this instanceof SwitchWorldsAndServersServerScreen switchScreen) {
            callback.cancel();
            switchScreen.switchWorldsAndServers$saveAndConnect(serverData);
        }
    }

    @Inject(method = "refreshServerList()V", at = @At("HEAD"), cancellable = true)
    private void switchWorldsAndServers$keepCustomScreen(CallbackInfo callback) {
        if ((Object) this instanceof SwitchWorldsAndServersServerScreen switchScreen) {
            callback.cancel();
            switchScreen.switchWorldsAndServers$refresh();
        }
    }
}
