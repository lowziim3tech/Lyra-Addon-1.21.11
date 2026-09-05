package com.lyradebug.mixin;

import com.lyradebug.LyraFreecam;
import net.minecraft.class_761;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={class_761.class})
public class WorldPatchB {
    @ModifyVariable(method={"method_74752"}, at=@At(value="HEAD"), argsOnly=true, ordinal=0, require=0, remap=false)
    private boolean hookSpectator(boolean bl) {
        return bl || LyraFreecam.li1Il();
    }
}

