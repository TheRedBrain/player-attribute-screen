package com.github.theredbrain.playerattributescreen.gui.widget;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundEvents;

@Environment(EnvType.CLIENT)
public class ToggleInventoryScreenWidget extends ButtonWidget {
	private boolean isPressed;
	private final boolean opensToRight;

	public ToggleInventoryScreenWidget(int x, int y, boolean isPressed, boolean opensToRight, PressAction action) {
		super(x, y, 23, 13, ScreenTexts.EMPTY, action, DEFAULT_NARRATION_SUPPLIER);
		this.isPressed = isPressed;
		this.opensToRight = opensToRight;
	}

	public boolean getIsPressed() {
		return this.isPressed;
	}

	public void setIsPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	@Override
	public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
		int i = 0;
		int j = 192;
		if (this.opensToRight) {
			if (this.isPressed) {
				i += 23;
				j += 13;
			}
		} else {
			if (this.isPressed) {
				i += 23;
			} else {
				j += 13;
			}
		}
		context.drawTexture(BookScreen.BOOK_TEXTURE, this.getX(), this.getY(), i, j, 23, 13);
	}

	@Override
	public void playDownSound(SoundManager soundManager) {
		soundManager.play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0F));
	}
}
