package folk.sisby.euphonium.helper;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundHelper {
    public static SoundEvent sound(ResourceLocation id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
