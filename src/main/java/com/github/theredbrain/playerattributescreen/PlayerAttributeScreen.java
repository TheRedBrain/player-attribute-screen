package com.github.theredbrain.playerattributescreen;

import com.github.theredbrain.playerattributescreen.config.ServerConfig;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerAttributeScreen implements ModInitializer {
	public static final String MOD_ID = "playerattributescreen";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ServerConfig SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new, RegisterType.BOTH);

	@Override
	public void onInitialize() {
		LOGGER.info("Displaying player attributes!");
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}

}