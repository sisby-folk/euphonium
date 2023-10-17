package folk.sisby.euphonium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@SuppressWarnings("deprecation")
public class EuphoniumClient implements ClientModInitializer {
    public static final String ID = "euphonium";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	private static SoundSource source;

	public static EuphoniumConfig CONFIG = EuphoniumConfig.createToml(FabricLoader.getInstance().getConfigDir(), ID, "config",  EuphoniumConfig.class);

	public static ResourceLocation id(String path) {
		return new ResourceLocation(ID, path);
	}

	public static SoundSource getAudioChannel() {
		return source;
	}

	@Override
	public void onInitializeClient() {
		var key = CONFIG.channel.toLowerCase();
		source = Arrays.stream(SoundSource.values())
			.filter(v -> v.getName().equals(key))
			.findFirst()
			.orElse(SoundSource.AMBIENT);

		EuphoniumClient.LOGGER.info("Using sound channel/source: " + source.getName());

		BiomeAmbience.init();
		WorldAmbience.init();
	}
}
