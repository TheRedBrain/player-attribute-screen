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
				"STRING:gui.inventory.attributes",
				"EMPTY_LINE",
				"ATTRIBUTE_VALUE:minecraft:generic.max_health",
				"ATTRIBUTE_VALUE:healthregenerationoverhaul:generic.health_regeneration",
				"ATTRIBUTE_VALUE:manaattributes:generic.max_mana",
				"ATTRIBUTE_VALUE:manaattributes:generic.mana_regeneration",
				"ATTRIBUTE_VALUE:staminaattributes:generic.max_stamina",
				"ATTRIBUTE_VALUE:staminaattributes:generic.stamina_regeneration",
				"ATTRIBUTE_VALUE:minecraft:generic.armor",
				"ATTRIBUTE_VALUE:minecraft:generic.armor_toughness",
				"ATTRIBUTE_VALUE_PERCENT:overhauleddamage:generic.increased_piercing_damage",
				"ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE_PERCENT:overhauleddamage:generic.increased_slashing_damage",
				"ATTRIBUTE_VALUE:minecraft:generic.luck"
		};

		public AttributeScreenClientConfig() {
		}

	}

}
