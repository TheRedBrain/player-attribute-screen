package com.github.theredbrain.playerattributescreen;

import com.github.theredbrain.playerattributescreen.config.ClientConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerAttributeScreenClient implements ClientModInitializer {
	public static ClientConfig clientConfig;

	@Override
	public void onInitializeClient() {
		// Config
		AutoConfig.register(ClientConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
		clientConfig = AutoConfig.getConfigHolder(ClientConfig.class).getConfig();

		// Packets
		ClientPlayNetworking.registerGlobalReceiver(PlayerAttributeScreen.ServerConfigSyncPacket.PACKET_ID, (payload, context) -> {
			PlayerAttributeScreen.serverConfig = payload.serverConfig();
		});
	}

	public static List<MutablePair<Text, List<Text>>> getPlayerAttributeScreenData(MinecraftClient client) {
		String[] attribute_screen_configuration;
		if (PlayerAttributeScreen.serverConfig.generalServerConfig.use_attribute_screen_configuration_from_server) {
			attribute_screen_configuration = PlayerAttributeScreen.serverConfig.generalServerConfig.attribute_screen_configuration;
		} else {
			attribute_screen_configuration = PlayerAttributeScreenClient.clientConfig.attributeScreenClientConfig.attribute_screen_configuration;
		}
		List<MutablePair<Text, List<Text>>> newData = new ArrayList<>(List.of());

		PlayerEntity player = null;
		if (client != null) {
			player = client.player;
		}
		if (player == null) {
			return newData;
		}
		for (String string : attribute_screen_configuration) {
			Text newText = null;
			List<Text> newToolTipList = new ArrayList<>(List.of());
			String[] stringArray = string.split(":");
			if (stringArray.length == 1) {
				if (stringArray[0].equals("EMPTY_LINE")) {
					newText = Text.empty();
//					newToolTipList = new ArrayList<>(List.of());
				}
			} else if (stringArray.length == 2) {
				if (stringArray[0].equals("STRING")) {
					newText = Text.translatable(stringArray[1]);
//					newToolTipList = new ArrayList<>(List.of());
				}
			} else if (stringArray.length == 3) {
				switch (stringArray[0]) {
					case "ATTRIBUTE_VALUE" -> {
						Identifier attributeId = Identifier.of(stringArray[1], stringArray[2]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(attribute.get().value().getTranslationKey());
							mutableText.append(Text.of(": "));
							mutableText.append(Text.of(Double.toString(player.getAttributeValue(attribute.get()))));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE" -> {
						Identifier attributeId = Identifier.of(stringArray[1], stringArray[2]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(attribute.get().value().getTranslationKey());
							mutableText.append(Text.of(": "));
							double baseValue = player.getAttributes().getBaseValue(attribute.get());
							double currentValue = player.getAttributeValue(attribute.get());
							double differenceValue = currentValue - baseValue;
							if (differenceValue > 0) {
								mutableText.append(Text.of("+"));
							}
							mutableText.append(Text.of(Double.toString(differenceValue)));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "ATTRIBUTE_VALUE_PERCENT" -> {
						Identifier attributeId = Identifier.of(stringArray[1], stringArray[2]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(attribute.get().value().getTranslationKey());
							mutableText.append(Text.of(": "));
							mutableText.append(Text.of(Double.toString(player.getAttributeValue(attribute.get()) * 100)));
							mutableText.append(Text.of("%"));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE_PERCENT" -> {
						Identifier attributeId = Identifier.of(stringArray[1], stringArray[2]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(attribute.get().value().getTranslationKey());
							mutableText.append(Text.of(": "));
							double baseValue = player.getAttributes().getBaseValue(attribute.get());
							double currentValue = player.getAttributeValue(attribute.get());
							double differenceValue = currentValue - baseValue;
							if (differenceValue > 0) {
								mutableText.append(Text.of("+"));
							}
							mutableText.append(Text.of(Double.toString(differenceValue * 100)));
							mutableText.append(Text.of("%"));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
				}
			} else if (stringArray.length == 4) {
				switch (stringArray[0]) {
					case "CUSTOM_ATTRIBUTE_VALUE" -> {
						Identifier attributeId = Identifier.of(stringArray[2], stringArray[3]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(stringArray[1]);
							mutableText.append(Text.of(Double.toString(player.getAttributeValue(attribute.get()))));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "CUSTOM_ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE" -> {
						Identifier attributeId = Identifier.of(stringArray[2], stringArray[3]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(stringArray[1]);
							double baseValue = player.getAttributes().getBaseValue(attribute.get());
							double currentValue = player.getAttributeValue(attribute.get());
							double differenceValue = currentValue - baseValue;
							if (differenceValue > 0) {
								mutableText.append(Text.of("+"));
							}
							mutableText.append(Text.of(Double.toString(differenceValue)));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "CUSTOM_ATTRIBUTE_VALUE_PERCENT" -> {
						Identifier attributeId = Identifier.of(stringArray[2], stringArray[3]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(stringArray[1]);
							mutableText.append(Text.of(Double.toString(player.getAttributeValue(attribute.get()) / 100)));
							mutableText.append(Text.of("%"));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "CUSTOM_ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE_PERCENT" -> {
						Identifier attributeId = Identifier.of(stringArray[2], stringArray[3]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(stringArray[1]);
							double baseValue = player.getAttributes().getBaseValue(attribute.get());
							double currentValue = player.getAttributeValue(attribute.get());
							double differenceValue = currentValue - baseValue;
							if (differenceValue > 0) {
								mutableText.append(Text.of("+"));
							}
							mutableText.append(Text.of(Double.toString(differenceValue / 100)));
							mutableText.append(Text.of("%"));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "STRING_REQUIRES_ATTRIBUTE" -> {
						Identifier attributeId = Identifier.of(stringArray[2], stringArray[3]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							newText = Text.translatable(stringArray[1]);
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
				}
			} else if (stringArray.length == 5) {
				switch (stringArray[0]) {
					case "CUSTOM_ATTRIBUTE_VALUE_POST_STRING" -> {
						Identifier attributeId = Identifier.of(stringArray[2], stringArray[3]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(stringArray[1]);
							mutableText.append(Text.of(Double.toString(player.getAttributeValue(attribute.get()))));
							mutableText.append(Text.translatable(stringArray[4]));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
					case "CUSTOM_ATTRIBUTE_VALUE_DIFFERENCE_TO_BASE_POST_STRING" -> {
						Identifier attributeId = Identifier.of(stringArray[2], stringArray[3]);
						Optional<RegistryEntry.Reference<EntityAttribute>> attribute = Registries.ATTRIBUTE.getEntry(attributeId);
						if (attribute.isPresent() && player.getAttributes().hasAttribute(attribute.get())) {
							MutableText mutableText = Text.translatable(stringArray[1]);
							double baseValue = player.getAttributes().getBaseValue(attribute.get());
							double currentValue = player.getAttributeValue(attribute.get());
							double differenceValue = currentValue - baseValue;
							if (differenceValue > 0) {
								mutableText.append(Text.of("+"));
							}
							mutableText.append(Text.of(Double.toString(differenceValue)));
							mutableText.append(Text.translatable(stringArray[4]));
							newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
						}
					}
				}
			} else if (stringArray.length == 6) {
				if (stringArray[0].equals("ATTRIBUTE_RELATION")) {
					Identifier firstAttributeId = Identifier.of(stringArray[2], stringArray[3]);
					Identifier secondAttributeId = Identifier.of(stringArray[4], stringArray[5]);
					Optional<RegistryEntry.Reference<EntityAttribute>> firstAttribute = Registries.ATTRIBUTE.getEntry(firstAttributeId);
					Optional<RegistryEntry.Reference<EntityAttribute>> secondAttribute = Registries.ATTRIBUTE.getEntry(secondAttributeId);
					if (firstAttribute.isPresent() && secondAttribute.isPresent() && player.getAttributes().hasAttribute(firstAttribute.get()) && player.getAttributes().hasAttribute(secondAttribute.get())) {
						MutableText mutableText = Text.translatable(stringArray[1]);
						mutableText.append(Text.of(Double.toString(player.getAttributeValue(firstAttribute.get()))));
						mutableText.append(Text.of("/"));
						mutableText.append(Text.of(Double.toString(player.getAttributeValue(secondAttribute.get()))));
						newText = mutableText;
//						newToolTipList = new ArrayList<>(List.of());
					}
				}
			}
			if (newText != null) {
				newData.add(new MutablePair<>(newText, newToolTipList));
			}
		}
		return newData;
	}

}