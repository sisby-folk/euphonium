package folk.sisby.euphonium;

import folk.sisby.euphonium.sound.BiomeSound;
import folk.sisby.euphonium.sound.ISoundType;
import folk.sisby.euphonium.sound.SoundHandler;
import folk.sisby.euphonium.sounds.biome.Badlands;
import folk.sisby.euphonium.sounds.biome.Beach;
import folk.sisby.euphonium.sounds.biome.Caves;
import folk.sisby.euphonium.sounds.biome.Desert;
import folk.sisby.euphonium.sounds.biome.Forest;
import folk.sisby.euphonium.sounds.biome.Icy;
import folk.sisby.euphonium.sounds.biome.Jungle;
import folk.sisby.euphonium.sounds.biome.Mountains;
import folk.sisby.euphonium.sounds.biome.Ocean;
import folk.sisby.euphonium.sounds.biome.Plains;
import folk.sisby.euphonium.sounds.biome.River;
import folk.sisby.euphonium.sounds.biome.Savanna;
import folk.sisby.euphonium.sounds.biome.Swamp;
import folk.sisby.euphonium.sounds.biome.Taiga;
import folk.sisby.euphonium.sounds.biome.TheEnd;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BiomeAmbience {
	public static List<ResourceLocation> VALID_DIMENSIONS = new ArrayList<>();
	private static final ISoundType<BiomeSound> BADLANDS = new Badlands();
	private static final ISoundType<BiomeSound> BEACH = new Beach();
	private static final ISoundType<BiomeSound> CAVES = new Caves();
	private static final ISoundType<BiomeSound> DESERT = new Desert();
	private static final ISoundType<BiomeSound> FOREST = new Forest();
	private static final ISoundType<BiomeSound> ICY = new Icy();
	private static final ISoundType<BiomeSound> JUNGLE = new Jungle();
	private static final ISoundType<BiomeSound> MOUNTAINS = new Mountains();
	private static final ISoundType<BiomeSound> OCEAN = new Ocean();
	private static final ISoundType<BiomeSound> PLAINS = new Plains();
	private static final ISoundType<BiomeSound> RIVER = new River();
	private static final ISoundType<BiomeSound> SAVANNA = new Savanna();
	private static final ISoundType<BiomeSound> SWAMP = new Swamp();
	private static final ISoundType<BiomeSound> TAIGA = new Taiga();
	private static final ISoundType<BiomeSound> THE_END = new TheEnd();
	private static Handler handler;

	@SuppressWarnings("deprecation")
	public static BiomeAmbienceConfig CONFIG = BiomeAmbienceConfig.createToml(FabricLoader.getInstance().getConfigDir(), EuphoniumClient.ID, "biome",  BiomeAmbienceConfig.class);

	@SuppressWarnings("deprecation")
	public static void init() {
		if (CONFIG.enabled) {
			ClientEntityEvents.ENTITY_LOAD.register(BiomeAmbience::handleClientEntityJoin);
			ClientEntityEvents.ENTITY_UNLOAD.register(BiomeAmbience::handleClientEntityLeave);
			ClientTickEvents.END_CLIENT_TICK.register(BiomeAmbience::handleClientTick);
			CONFIG.dimensions.forEach(dim -> VALID_DIMENSIONS.add(new ResourceLocation(dim)));
		}
	}

	private static void handleClientTick(Minecraft client) {
		if (handler != null && !client.isPaused()) {
			handler.tick();
		}
	}

	private static void handleClientEntityLeave(Entity entity, Level level) {
		if (entity instanceof LocalPlayer && handler != null) {
			handler.stop();
		}
	}

	private static void handleClientEntityJoin(Entity entity, Level level) {
		if (entity instanceof LocalPlayer player) {
			trySetupSoundHandler(player);
		}
	}

	private static void trySetupSoundHandler(Player player) {
		if (!(player instanceof LocalPlayer)) return;

		if (handler == null) {
			handler = new Handler(player);
		}

		handler.updatePlayer(player);
	}

	public static class Handler extends SoundHandler<BiomeSound> {
		public Handler(@NotNull Player player) {
			super(player);

			BADLANDS.addSounds(this);
			BEACH.addSounds(this);
			CAVES.addSounds(this);
			DESERT.addSounds(this);
			FOREST.addSounds(this);
			ICY.addSounds(this);
			JUNGLE.addSounds(this);
			MOUNTAINS.addSounds(this);
			OCEAN.addSounds(this);
			PLAINS.addSounds(this);
			RIVER.addSounds(this);
			SAVANNA.addSounds(this);
			SWAMP.addSounds(this);
			TAIGA.addSounds(this);
			THE_END.addSounds(this);
		}
	}
}
