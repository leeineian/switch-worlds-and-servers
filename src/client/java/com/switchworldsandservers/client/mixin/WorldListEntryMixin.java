package com.switchworldsandservers.client.mixin;

import com.switchworldsandservers.client.SwitchWorldsAndServersWorldScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldSelectionList.WorldListEntry.class)
abstract class WorldListEntryMixin {
    @Shadow
    @Final
    private Screen screen;

    @Redirect(method = "joinWorld()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/worldselection/WorldOpenFlows;openWorld(Ljava/lang/String;Ljava/lang/Runnable;)V"))
    private void switchWorldsAndServers$leaveServerBeforeOpeningWorld(WorldOpenFlows flows, String levelId, Runnable onFailure) {
        if (this.screen instanceof SwitchWorldsAndServersWorldScreen switchScreen) {
            switchScreen.switchWorldsAndServers$leaveServerAndOpenWorld(levelId);
        } else {
            flows.openWorld(levelId, onFailure);
        }
    }
}
