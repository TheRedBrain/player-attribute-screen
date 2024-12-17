package com.github.theredbrain.playerattributescreen.config;

import com.github.theredbrain.playerattributescreen.PlayerAttributeScreen;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;

import java.util.ArrayList;

@ConvertFrom(fileName = "server.json5", folder = "playerattributescreen")
public class ServerConfig extends Config {

	public ServerConfig() {
		super(PlayerAttributeScreen.identifier("server"));
	}

	public ValidatedBoolean disable_player_attribute_screen = new ValidatedBoolean(false);

	public ValidatedBoolean use_attribute_screen_configuration_from_server = new ValidatedBoolean(false);

	public ValidatedList<String> attribute_screen_configuration = new ValidatedList<>(getList(), new ValidatedString());

	private ArrayList<String> getList() {
		ArrayList<String> list = new ArrayList<>();
		list.add("STRING:gui.attribute_screen.attributes");
		list.add("EMPTY_LINE");
		list.add("ATTRIBUTE_VALUE:minecraft:generic.max_health");
		list.add("STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.health_regeneration_line_1:healthregenerationoverhaul:generic.health_regeneration");
		list.add("CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.health_regeneration_line_2:healthregenerationoverhaul:generic.health_regeneration");
		list.add("ATTRIBUTE_VALUE:manaattributes:generic.max_mana");
		list.add("STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.mana_regeneration_line_1:manaattributes:generic.mana_regeneration");
		list.add("CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.mana_regeneration_line_2:manaattributes:generic.mana_regeneration");
		list.add("ATTRIBUTE_VALUE:staminaattributes:generic.max_stamina");
		list.add("STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.stamina_regeneration_line_1:staminaattributes:generic.stamina_regeneration");
		list.add("CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.stamina_regeneration_line_2:staminaattributes:generic.stamina_regeneration");
		list.add("ATTRIBUTE_VALUE:minecraft:generic.armor");
		list.add("STRING_REQUIRES_ATTRIBUTE:gui.attribute_screen.armor_toughness_line_1:minecraft:generic.armor_toughness");
		list.add("CUSTOM_ATTRIBUTE_VALUE:gui.attribute_screen.armor_toughness_line_2:minecraft:generic.armor_toughness");
		list.add("ATTRIBUTE_VALUE:minecraft:generic.luck");
		return list;
	}
}
