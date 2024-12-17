package com.github.theredbrain.playerattributescreen.mixin.client.gui.screen.ingame;

import com.github.theredbrain.playerattributescreen.gui.screen.ingame.DuckAbstractInventoryScreenMixin;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractInventoryScreen.class)
public abstract class AbstractInventoryScreenMixin implements DuckAbstractInventoryScreenMixin {

	@Inject(method = "drawStatusEffects", at = @At("HEAD"), cancellable = true)
	private void playerattributescreen$drawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
		if (this.playerattributescreen$hideStatusEffects()) {
			ci.cancel();
		}
	}

	@Override
	public boolean playerattributescreen$hideStatusEffects() {
		return false;
	}
}