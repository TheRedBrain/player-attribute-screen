package com.github.theredbrain.playerattributescreen.mixin.client.gui.screen.ingame;

import com.github.theredbrain.playerattributescreen.PlayerAttributeScreen;
import com.github.theredbrain.playerattributescreen.PlayerAttributeScreenClient;
import com.github.theredbrain.playerattributescreen.config.ClientConfig;
import com.github.theredbrain.playerattributescreen.gui.screen.ingame.DuckAbstractInventoryScreenMixin;
import com.github.theredbrain.playerattributescreen.gui.widget.ToggleInventoryScreenWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.tuple.MutablePair;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider, DuckAbstractInventoryScreenMixin {
	@Unique
	private static final Identifier INVENTORY_SIDES_BACKGROUND_TEXTURE = PlayerAttributeScreen.identifier("textures/gui/container/inventory_sides_background.png");
	@Unique
	private static final Identifier SCROLL_BAR_BACKGROUND_8_206_TEXTURE = PlayerAttributeScreen.identifier("textures/gui/sprites/scroll_bar/scroll_bar_background_8_152.png");
	@Unique
	private static final Identifier SCROLLER_VERTICAL_6_15_TEXTURE = PlayerAttributeScreen.identifier("textures/gui/sprites/scroll_bar/scroller_vertical_6_15.png");
	@Unique
	private static final Text TOGGLE_SHOW_ATTRIBUTES_BUTTON_TOOLTIP_TEXT_OFF = Text.translatable("gui.inventory.toggleShowAttributeScreenButton.off.tooltip");
	@Unique
	private static final Text TOGGLE_SHOW_ATTRIBUTES_BUTTON_TOOLTIP_TEXT_ON = Text.translatable("gui.inventory.toggleShowAttributeScreenButton.on.tooltip");
	@Unique
	private static final int MAX_ATTRIBUTE_SCREEN_LINES = 12;
	@Shadow
	@Final
	private RecipeBookWidget recipeBook;
	@Unique
	private ButtonWidget toggleShowAttributeScreenButton;
	@Unique
	private final int sidesBackgroundWidth = 130;
	@Unique
	private boolean showAttributeScreen;
	@Unique
	private int attributeListSize = 0;
	@Unique
	private int attributeScrollPosition = 0;
	@Unique
	private float attributeScrollAmount = 0.0f;
	@Unique
	private boolean attributeMouseClicked = false;

	public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Inject(method = "init", at = @At("TAIL"))
	protected void playerattributescreen$init(CallbackInfo ci) {
		ClientConfig generalClientConfig = PlayerAttributeScreenClient.CLIENT_CONFIG;
		this.showAttributeScreen = generalClientConfig.show_attribute_screen_when_opening_inventory_screen.get() && !PlayerAttributeScreen.SERVER_CONFIG.disable_player_attribute_screen.get();
		this.toggleShowAttributeScreenButton = this.addDrawableChild(new ToggleInventoryScreenWidget(this.x + 146, this.y + 7, this.showAttributeScreen, true, button -> {
			if (!this.recipeBook.isOpen()) {
				this.playerattributescreen$toggleShowAttributeScreen();
			}
		}));
		this.toggleShowAttributeScreenButton.setTooltip(Tooltip.of(this.showAttributeScreen ? TOGGLE_SHOW_ATTRIBUTES_BUTTON_TOOLTIP_TEXT_ON : TOGGLE_SHOW_ATTRIBUTES_BUTTON_TOOLTIP_TEXT_OFF));
		this.attributeScrollPosition = 0;
		this.attributeScrollAmount = 0.0f;
		this.attributeMouseClicked = false;
	}

	@Inject(method = "render", at = @At("TAIL"))
	public void playerattributescreen$render_head(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		this.playerattributescreen$calculateRenderState();
	}

	@Inject(method = "render", at = @At("TAIL"))
	public void playerattributescreen$render_tail(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		this.playerattributescreen$drawAttributeScreen(context, mouseX, mouseY);
	}

	@Inject(method = "drawBackground", at = @At("TAIL"))
	protected void playerattributescreen$drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
		if (this.showAttributeScreen) {
			context.drawTexture(INVENTORY_SIDES_BACKGROUND_TEXTURE, this.x + this.backgroundWidth, this.y, 0, 0, this.sidesBackgroundWidth, this.backgroundHeight, this.sidesBackgroundWidth, this.backgroundHeight);
		}
	}

	@Override
	public void resize(MinecraftClient client, int width, int height) {
		int number = this.attributeScrollPosition;
		float number1 = this.attributeScrollAmount;
		boolean bool = this.attributeMouseClicked;
		boolean bool1 = this.showAttributeScreen;
		this.init(client, width, height);
		this.attributeScrollPosition = number;
		this.attributeScrollAmount = number1;
		this.attributeMouseClicked = bool;
		this.showAttributeScreen = bool1;
	}

	@Inject(method = "mouseClicked", at = @At("HEAD"))
	public void playerattributescreen$mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
		if (this.attributeListSize > MAX_ATTRIBUTE_SCREEN_LINES && this.showAttributeScreen) {
			int i = this.x + this.backgroundWidth + this.sidesBackgroundWidth - 7;
			int j = this.y + 7;
			if (mouseX >= (double) i && mouseX < (double) (i + 6) && mouseY >= (double) j && mouseY < (double) (j + 152)) {
				this.attributeMouseClicked = true;
			}
		}
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (this.attributeListSize > MAX_ATTRIBUTE_SCREEN_LINES && this.attributeMouseClicked && this.showAttributeScreen) {
			int i = this.attributeListSize - MAX_ATTRIBUTE_SCREEN_LINES;
			float f = (float) deltaY / (float) i;
			this.attributeScrollAmount = MathHelper.clamp(this.attributeScrollAmount + f, 0.0f, 1.0f);
			this.attributeScrollPosition = (int) ((double) (this.attributeScrollAmount * (float) i));
		}
		return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		int scrollAreaStartX = this.x + this.backgroundWidth + 7;
		int scrollAreaWidth = 116;
		int scrollAreaStartY = this.y + 7;
		int scrollAreaHeight = 152;
		if (this.showAttributeScreen && this.attributeListSize > MAX_ATTRIBUTE_SCREEN_LINES && mouseX >= scrollAreaStartX && mouseX <= scrollAreaStartX + scrollAreaWidth && mouseY >= scrollAreaStartY && mouseY <= scrollAreaStartY + scrollAreaHeight) {
			int i = this.attributeListSize - MAX_ATTRIBUTE_SCREEN_LINES;
			float f = (float) verticalAmount / (float) i;
			this.attributeScrollAmount = MathHelper.clamp(this.attributeScrollAmount - f, 0.0f, 1.0f);
			this.attributeScrollPosition = (int) ((double) (this.attributeScrollAmount * (float) i));
		}
		return true;
	}

	@Unique
	private void playerattributescreen$calculateRenderState() {
		if (this.recipeBook.isOpen() || PlayerAttributeScreen.SERVER_CONFIG.disable_player_attribute_screen.get()) {
			if (this.showAttributeScreen) {
				this.playerattributescreen$toggleShowAttributeScreen();
			}
			if (this.toggleShowAttributeScreenButton.visible) {
				this.toggleShowAttributeScreenButton.visible = false;
			}

		} else if (!this.toggleShowAttributeScreenButton.visible) {
			this.toggleShowAttributeScreenButton.visible = true;
		}
		this.toggleShowAttributeScreenButton.setPosition(this.x + 146, this.y + 7);
	}

	@Unique
	private void playerattributescreen$toggleShowAttributeScreen() {
		this.showAttributeScreen = !this.showAttributeScreen;
		((ToggleInventoryScreenWidget) this.toggleShowAttributeScreenButton).setIsPressed(this.showAttributeScreen);
		this.toggleShowAttributeScreenButton.setTooltip(this.showAttributeScreen ? Tooltip.of(TOGGLE_SHOW_ATTRIBUTES_BUTTON_TOOLTIP_TEXT_ON) : Tooltip.of(TOGGLE_SHOW_ATTRIBUTES_BUTTON_TOOLTIP_TEXT_OFF));
		this.attributeScrollPosition = 0;
		this.attributeScrollAmount = 0.0f;
		this.attributeMouseClicked = false;

	}

	@Unique
	private void playerattributescreen$drawAttributeScreen(DrawContext context, int mouseX, int mouseY) {
		if (!this.showAttributeScreen) {
			return;
		}
		int x = this.x + this.backgroundWidth + 7;
		int y = this.y + 7;
		int currentY;
		List<MutablePair<Text, List<Text>>> list = PlayerAttributeScreenClient.getPlayerAttributeScreenData(this.client);
		this.attributeListSize = list.size();
		for (int i = this.attributeScrollPosition; i < Math.min(this.attributeListSize, this.attributeScrollPosition + MAX_ATTRIBUTE_SCREEN_LINES); i++) {
			currentY = y + ((i - this.attributeScrollPosition) * 13);
			context.drawText(this.textRenderer, list.get(i).left, x, currentY, 0x404040, false);
			if (mouseX >= x && mouseX <= x + this.sidesBackgroundWidth - 7 && mouseY >= currentY && mouseY <= currentY + 13) {
				List<Text> tooltipList = list.get(i).right;
				if (!tooltipList.isEmpty()) {
					context.drawTooltip(this.textRenderer, tooltipList, Optional.empty(), mouseX, mouseY);
				}
			}
		}
		if (list.size() > MAX_ATTRIBUTE_SCREEN_LINES) {
			context.drawTexture(SCROLL_BAR_BACKGROUND_8_206_TEXTURE, x + 108, y, 0, 0, 8, 152, 8, 152);
			int k = (int) (135.0f * this.attributeScrollAmount);
			context.drawTexture(SCROLLER_VERTICAL_6_15_TEXTURE, x + 109, y + 1 + k, 0, 0, 6, 15, 6, 15);
		}
	}

	@Override
	public boolean playerattributescreen$hideStatusEffects() {
		return this.showAttributeScreen;
	}
}