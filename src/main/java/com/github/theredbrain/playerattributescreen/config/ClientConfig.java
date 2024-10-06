package com.github.theredbrain.playerattributescreen.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(
		name = "playerattributescreen"
)
public class ClientConfig extends PartitioningSerializer.GlobalData {
	@ConfigEntry.Category("generalClientConfig")
	@ConfigEntry.Gui.TransitiveObject
	public GeneralClientConfig generalClientConfig = new GeneralClientConfig();
	@ConfigEntry.Category("attributeScreenClientConfig")
	@ConfigEntry.Gui.TransitiveObject
	public AttributeScreenClientConfig attributeScreenClientConfig = new AttributeScreenClientConfig();

	public ClientConfig() {
	}

	@Config(
			name = "generalClientConfig"
	)
	public static class GeneralClientConfig implements ConfigData {
		@Comment("show_attribute_screen_when_opening_inventory_screen")
		public boolean show_attribute_screen_when_opening_inventory_screen = false;

		public GeneralClientConfig() {

		}

	}

	@Config(
			name = "attributeScreenClientConfig"
	)
	public static class AttributeScreenClientConfig implements ConfigData {
		@ConfigEntry.Gui.PrefixText
		public String[] attribute_screen_configuration = {
				"STRING:gui.attribute_screen.attributes",
				"EMPTY_LINE",
				"ATTRIBUTE_VALUE:minecraft:generic.max_health",
				"STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.health_regeneration_line_1:healthregenerationoverhaul:generic.health_regeneration",
				"CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.health_regeneration_line_2:healthregenerationoverhaul:generic.health_regeneration",
				"ATTRIBUTE_VALUE:manaattributes:generic.max_mana",
				"STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.mana_regeneration_line_1:manaattributes:generic.mana_regeneration",
				"CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.mana_regeneration_line_2:manaattributes:generic.mana_regeneration",
				"ATTRIBUTE_VALUE:staminaattributes:generic.max_stamina",
				"STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.stamina_regeneration_line_1:staminaattributes:generic.stamina_regeneration",
				"CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.stamina_regeneration_line_2:staminaattributes:generic.stamina_regeneration",
				"ATTRIBUTE_VALUE:minecraft:generic.armor",
				"STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.armor_toughness_line_1:minecraft:generic.armor_toughness",
				"CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.armor_toughness_line_2:minecraft:generic.armor_toughness",
				"ATTRIBUTE_VALUE:minecraft:generic.luck"
		};

		public AttributeScreenClientConfig() {
		}

	}

}
