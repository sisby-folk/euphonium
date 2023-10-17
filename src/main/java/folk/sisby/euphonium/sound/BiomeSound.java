package folk.sisby.euphonium.sound;

import folk.sisby.euphonium.EuphoniumBiome;
import folk.sisby.euphonium.EuphoniumClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.biome.Biome;

import java.util.ConcurrentModificationException;
import java.util.List;

public abstract class BiomeSound implements ISoundInstance {
    protected Minecraft client;
    protected boolean isValid = false;
    protected Player player;
    protected ClientLevel level;
    protected LoopingSound soundInstance = null;
    protected float blendScaling = 1.0F;
    protected float volumeScaleFade = 0.005F;

    protected BiomeSound(Player player) {
        this.client = Minecraft.getInstance();
        this.player = player;
        this.level = (ClientLevel) player.level;
    }

    public abstract boolean isValidBiomeCondition(Holder<Biome> holder, ResourceKey<Biome> key);

    @Override
    public void updatePlayer(Player player) {
        this.player = player;
        this.level = (ClientLevel) player.level;
    }

    @Override
    public ClientLevel getLevel() {
        return level;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public AbstractTickableSoundInstance getSoundInstance() {
        return soundInstance;
    }

    @Override
    public double getVolumeScaling() {
        return EuphoniumClient.CONFIG.biomeAmbience.volumeScaling;
    }

    @Override
    public void tick() {
        var log = EuphoniumClient.LOGGER;
        boolean nowValid = isValid();

        if (isValid && !nowValid) isValid = false;
        if (!isValid && nowValid) isValid = true;

        if (isValid) {
            var volume = getVolume() * getVolumeScaling() * blendScaling;

            if (!isPlaying()) {
                soundInstance = new LoopingSound(player, getSound(), (float)volume, getPitch(), p -> isValid);
                try {
                    getSoundManager().play(this.soundInstance);
                } catch (ConcurrentModificationException e) {
                    log.debug("[{}] Exception in tick", this.getClass().getSimpleName());
                }
            } else {

                // Adjust sound volume with a fade.
                if (soundInstance.maxVolume != volume) {
                    if (soundInstance.maxVolume < volume) {
                        soundInstance.maxVolume += volumeScaleFade;
                    } else {
                        soundInstance.maxVolume -= volumeScaleFade;
                    }
                }
            }
        }
    }

    @Override
    public boolean isValid() {
        if (client.level == null || level == null) return false;
        if (!player.isAlive()) return false;
        if (!isValidPlayerCondition()) return false;

        if (!EuphoniumBiome.VALID_DIMENSIONS.contains(level.dimension().location())) {
            return false;
        }

        var pos = player.blockPosition();
        var blend = (float) EuphoniumClient.CONFIG.biomeAmbience.biomeBlend;

        if (blend > 0) {

            // Sample points.
            var directions = List.of(
                Direction.EAST,
                Direction.WEST,
                Direction.NORTH,
                Direction.SOUTH
            );

            for (var direction : directions) {
                for (int i = 0; i < blend; i += 2) {
                    var relativePos = pos.relative(direction, i);

                    // Get the biome and key for condition check.
                    var holder = getBiomeHolder(relativePos);
                    var key = getBiomeKey(relativePos);
                    if (isValidBiomeCondition(holder, key)) {
                        this.blendScaling = 1.0F - ((float) i / blend);
                        return true;
                    }
                }
            }

        } else {

            // Get the biome and key for condition check.
            var holder = getBiomeHolder(pos);
            var key = getBiomeKey(pos);

            if (isValidBiomeCondition(holder, key)) {
                this.blendScaling = 1.0F;
                return true;
            }
        }

        return false;
    }
}
