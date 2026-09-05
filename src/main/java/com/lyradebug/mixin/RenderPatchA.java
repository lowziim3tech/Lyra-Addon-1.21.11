package com.lyradebug.mixin;

import com.lyradebug.LyraFreecam;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_4184;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_4184.class})
public abstract class RenderPatchA {
    @Mutable
    @Shadow
    private boolean field_18719;

    @Shadow
    protected abstract void method_19325(float var1, float var2);

    @Shadow
    protected abstract void method_19327(double var1, double var3, double var5);

    @Inject(method={"method_19321"}, at={@At(value="TAIL")})
    private void hookUpdate(class_1937 class_19372, class_1297 class_12972, boolean bl, boolean bl2, float f, CallbackInfo callbackInfo) {
        LyraFreecam lyraFreecam = RenderPatchA.freecam();
        if (lyraFreecam != null) {
            this.field_18719 = true;
            this.method_19325((float)lyraFreecam.qQqQq(f), (float)lyraFreecam.zZzZz(f));
            this.method_19327(lyraFreecam.o0O0o(f), lyraFreecam.Il1lI(f), lyraFreecam.xXxXx(f));
        }
    }

    @Inject(method={"method_19318"}, at={@At(value="HEAD")}, cancellable=true)
    private void hookClip(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (RenderPatchA.freecam() != null) {
            callbackInfoReturnable.setReturnValue(f);
        }
    }

    private static LyraFreecam freecam() {
        if (Modules.get() == null) {
            return null;
        }
        LyraFreecam lyraFreecam = (LyraFreecam)Modules.get().get(LyraFreecam.class);
        return lyraFreecam != null && lyraFreecam.isActive() ? lyraFreecam : null;
    }
}
