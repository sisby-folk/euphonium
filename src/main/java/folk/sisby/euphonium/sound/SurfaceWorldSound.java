package folk.sisby.euphonium.sound;

import folk.sisby.euphonium.WorldAmbience;
import folk.sisby.euphonium.helper.WorldHelper;
import net.minecraft.world.entity.player.Player;

public abstract class SurfaceWorldSound extends RepeatedWorldSound {
    protected SurfaceWorldSound(Player player) {
        super(player);
    }

    @Override
    public boolean isValidPlayerCondition() {
        return WorldHelper.isOutside(getPlayer());
    }

    @Override
    public double getVolumeScaling() {
        var cullDistance = WorldAmbience.CONFIG.cullSoundAboveGround;

        if (cullDistance > 0) {
            return super.getVolumeScaling() * Math.max(0.0D, 1.0D - (WorldHelper.distanceFromGround(getPlayer(), cullDistance) / cullDistance));
        } else {
            return super.getVolumeScaling();
        }
    }
}
