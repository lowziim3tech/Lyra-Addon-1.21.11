package com.lyradebug.mixin;

import com.lyradebug.StringCipher;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_412;
import net.minecraft.class_419;
import net.minecraft.class_437;
import net.minecraft.class_639;
import net.minecraft.class_642;
import net.minecraft.class_9112;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={class_412.class})
public class BootstrapA {
    private static final class_2561 constant01 = class_2561.method_43470((String)"You do not have OpSec mod installed. Please install the mod and try again.");

    @Inject(method={"method_36877(Lnet/minecraft/class_437;Lnet/minecraft/class_310;Lnet/minecraft/class_639;Lnet/minecraft/class_642;ZLnet/minecraft/class_9112;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private static void hookConnect(class_437 class_4372, class_310 class_3102, class_639 class_6392, class_642 class_6422, boolean bl, class_9112 class_91122, CallbackInfo callbackInfo) {
        FabricLoader fabricLoader = FabricLoader.getInstance();
        if (!fabricLoader.isModLoaded("exploitpreventer") && !fabricLoader.isModLoaded("opsec")) {
            callbackInfo.cancel();
            class_3102.method_1507((class_437)new class_419(class_4372, (class_2561)class_2561.method_43471((String)"disconnect.lost"), constant01));
        }
    }
}

