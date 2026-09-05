package com.lyradebug;

import com.lyradebug.LyraDebug;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;

public class SwingSpeed
extends Module {
    private final SettingGroup group01;
    public final Setting<Double> setting02;

    public SwingSpeed() {
        super(LyraDebug.CATEGORY, "swing-speed", "Changes the speed of the hand swinging animation.");
        this.group01 = this.settings.getDefaultGroup();
        this.setting02 = this.group01.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("speed")).description("Swing animation speed multiplier.")).defaultValue(0.3).min(0.1).max(3.0).sliderRange(0.1, 3.0).build());
    }

    public float o0O0o() {
        return ((Double)this.setting02.get()).floatValue();
    }
}

