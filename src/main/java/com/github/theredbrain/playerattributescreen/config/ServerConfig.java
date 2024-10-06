package com.github.theredbrain.playerattributescreen.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(
		name = "playerattributescreen"
)
public class ServerConfig extends PartitioningSerializer.GlobalData {
	@ConfigEntry.Category("server")
	@ConfigEntry.Gui.Excluded
	public GeneralServerConfig generalServerConfig = new GeneralServerConfig();

	public ServerConfig() {
	}

	@Config(
			name = "server"
	)
	public static class GeneralServerConfig implements ConfigData {
		public boolean disable_player_attribute_screen = false;

		public boolean use_attribute_screen_configuration_from_server = false;

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

		public GeneralServerConfig() {

		}
	}
}
