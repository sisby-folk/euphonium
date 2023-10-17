package folk.sisby.euphonium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class EuphoniumClient implements ClientModInitializer {
    public static final String ID = "euphonium";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	public static EuphoniumConfig CONFIG = EuphoniumConfig.createToml(FabricLoader.getInstance().getConfigDir(), "", ID,  EuphoniumConfig.class);

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}

	@Override
	public void onInitializeClient() {
		EuphoniumBiome.init();
		EuphoniumWorld.init();
		EuphoniumClient.LOGGER.info("[Euphonium] Initialized! Using sound channel: {}", CONFIG.channel.getName());
	}
}
