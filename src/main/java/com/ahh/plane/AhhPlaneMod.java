package com.ahh.plane;

import com.ahh.plane.entity.ModEntityTypes;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AhhPlaneMod implements ModInitializer {
	public static final String MOD_ID = "plane";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEntityTypes.init();
	}
}