package net.earthcomputer.clientcommands.mixin.compat;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Restores {@code Gui#getChat}, which Minecraft 26.2 moved to {@code Gui#hud}, but which the
 * Forgified Fabric API command module still calls.
 */
@Mixin(Gui.class)
public abstract class GuiGetChatCompatMixin {
    @Shadow
    public Hud hud;

    public ChatComponent getChat() {
        return this.hud.getChat();
    }
}
