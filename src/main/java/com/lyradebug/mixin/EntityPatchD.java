package com.lyradebug.mixin;

import com.lyradebug.SwingSpeed;
import com.lyradebug.license.NativeGuard;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.class_1309;
import net.minecraft.class_310;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={class_1309.class})
public class EntityPatchD {
    @Inject(method={"method_6028"}, at={@At(value="RETURN")}, cancellable=true)
    private void hookHandSwingDuration(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if ((Object)this != class_310.method_1551().field_1724) {
            return;
        }
        if (Modules.get() == null) {
            return;
        }
        SwingSpeed swingSpeed = (SwingSpeed)Modules.get().get(SwingSpeed.class);
        if (swingSpeed == null || !swingSpeed.isActive()) {
            return;
        }
        float f = swingSpeed.o0O0o();
        if (f <= 0.01f) {
            return;
        }
        int n = (Integer)callbackInfoReturnable.getReturnValue();
        callbackInfoReturnable.setReturnValue(NativeGuard.o0O0o(n, f));
    }
}
