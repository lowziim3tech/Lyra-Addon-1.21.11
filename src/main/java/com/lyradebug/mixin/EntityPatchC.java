package com.lyradebug.mixin;

import com.lyradebug.LyraFreecam;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.class_1297;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_1297.class})
public class EntityPatchC {
    @Inject(method={"method_5872"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookLook(double d, double d2, CallbackInfo callbackInfo) {
        LyraFreecam lyraFreecam;
        if ((Object)this == MeteorClient.mc.field_1724 && Modules.get() != null && (lyraFreecam = (LyraFreecam)Modules.get().get(LyraFreecam.class)) != null && lyraFreecam.isActive()) {
            lyraFreecam.o0O0o(d * 0.15, d2 * 0.15);
            callbackInfo.cancel();
        }
    }
}
