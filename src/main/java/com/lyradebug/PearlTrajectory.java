package com.lyradebug;

import com.lyradebug.LyraDebug;
import java.util.ArrayList;
import java.util.List;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1802;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_238;
import net.minecraft.class_239;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_3959;
import net.minecraft.class_3965;
import net.minecraft.class_638;

public class PearlTrajectory
extends Module {
    private final SettingGroup group01;
    private final Setting<SettingColor> setting02;
    private final Setting<SettingColor> setting03;
    private final Setting<Boolean> setting04;
    private final Setting<Boolean> setting05;
    private final Setting<Boolean> setting06;
    private final Setting<Boolean> setting07;
    private final Setting<Double> setting08;
    private final Setting<Boolean> setting09;
    private final Setting<Boolean> setting10;
    private static final boolean constant11 = true;
    private static final int constant12 = 300;
    private static final int constant13 = 4;
    private static final double constant14 = 0.35;
    private static final boolean constant15 = true;
    private static final SettingColor constant16 = new SettingColor(255, 70, 70, 255);
    private static final double constant17 = 1.5;
    private static final double constant18 = 0.03;
    private static final double constant19 = 0.99;

    public PearlTrajectory() {
        super(LyraDebug.CATEGORY, "pearl-trajectory", "Predicts and displays ender pearl trajectory with collision detection.");
        this.group01 = this.settings.getDefaultGroup();
        this.setting02 = this.group01.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("line-color")).description("Color of the trajectory line.")).defaultValue(new SettingColor(255, 180, 220, 255)).build());
        this.setting03 = this.group01.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("landing-color")).description("Color of the landing box.")).defaultValue(new SettingColor(255, 180, 220, 255)).build());
        this.setting04 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("warning-color")).description("Highlight when trajectory hits an entity.")).defaultValue(false)).build());
        this.setting05 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("landing-box")).description("Render landing box.")).defaultValue(true)).build());
        this.setting06 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("crosshair")).description("Render landing crosshair.")).defaultValue(true)).build());
        this.setting07 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("beam")).description("Render vertical beacon beam at landing point.")).defaultValue(false)).build());
        this.setting08 = this.group01.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("beam-height")).description("Height of the vertical beam.")).defaultValue(25.0).min(1.0).sliderMax(60.0).visible(() -> this.setting07.get())).build());
        this.setting09 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("gradient")).description("Gradient color fade along the line.")).defaultValue(true)).build());
        this.setting10 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("entity-detection")).description("Detect entities along trajectory.")).defaultValue(true)).build());
    }

    private boolean ll1II() {
        return this.mc.field_1724 != null && (this.mc.field_1724.method_6047().method_7909() == class_1802.field_8634 || this.mc.field_1724.method_6079().method_7909() == class_1802.field_8634);
    }

    @EventHandler
    private void Il1lI(Render3DEvent render3DEvent) {
        if (this.mc.field_1724 == null || this.mc.field_1687 == null) {
            return;
        }
        if (!this.ll1II()) {
            return;
        }
        TrajectoryResult trajectoryResult = this.o0O0o(render3DEvent.tickDelta);
        if (trajectoryResult == null || trajectoryResult.list01.size() < 2) {
            return;
        }
        boolean bl = (Boolean)this.setting04.get() != false && trajectoryResult.flag02;
        SettingColor settingColor = bl ? constant16 : (SettingColor)this.setting03.get();
        this.o0O0o(render3DEvent, trajectoryResult.list01);
        class_243 class_2432 = trajectoryResult.list01.get(trajectoryResult.list01.size() - 1);
        if (((Boolean)this.setting07.get()).booleanValue()) {
            this.o0O0o(render3DEvent, class_2432, settingColor);
        }
        if (((Boolean)this.setting05.get()).booleanValue() && trajectoryResult.field03 != null) {
            this.o0O0o(render3DEvent, trajectoryResult.field03, settingColor);
        }
        if (((Boolean)this.setting06.get()).booleanValue()) {
            this.o0O0o(render3DEvent, class_2432, settingColor, trajectoryResult.field04);
        }
    }

    private void o0O0o(Render3DEvent render3DEvent, List<class_243> list) {
        SettingColor settingColor = (SettingColor)this.setting02.get();
        int n = list.size() - 1;
        for (int i = 0; i < n; ++i) {
            class_243 class_2432 = list.get(i);
            class_243 class_2433 = list.get(i + 1);
            int n2 = settingColor.a;
            if (((Boolean)this.setting09.get()).booleanValue()) {
                double d = (double)i / (double)n;
                n2 = (int)((double)settingColor.a * (1.0 - 0.75 * d));
            }
            render3DEvent.renderer.line(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350, class_2433.field_1352, class_2433.field_1351, class_2433.field_1350, (Color)new SettingColor(settingColor.r, settingColor.g, settingColor.b, n2));
        }
    }

    private void o0O0o(Render3DEvent render3DEvent, class_243 class_2432, SettingColor settingColor) {
        double d = class_2432.field_1352;
        double d2 = class_2432.field_1350;
        double d3 = class_2432.field_1351;
        double d4 = d3 + (Double)this.setting08.get();
        int n = 24;
        for (int i = 0; i < n; ++i) {
            double d5 = (double)i / (double)n;
            double d6 = (double)(i + 1) / (double)n;
            double d7 = class_3532.method_16436((double)d5, (double)d3, (double)d4);
            double d8 = class_3532.method_16436((double)d6, (double)d3, (double)d4);
            int n2 = settingColor.a;
            if (((Boolean)this.setting09.get()).booleanValue()) {
                double d9 = (d5 + d6) / 2.0;
                n2 = (int)((double)settingColor.a * (1.0 - 0.85 * d9));
            }
            render3DEvent.renderer.line(d, d7, d2, d, d8, d2, (Color)new SettingColor(settingColor.r, settingColor.g, settingColor.b, n2));
        }
    }

    private void o0O0o(Render3DEvent render3DEvent, class_2338 class_23382, SettingColor settingColor) {
        render3DEvent.renderer.box((double)class_23382.method_10263(), (double)class_23382.method_10264(), (double)class_23382.method_10260(), (double)(class_23382.method_10263() + 1), (double)(class_23382.method_10264() + 1), (double)(class_23382.method_10260() + 1), (Color)new SettingColor(settingColor.r, settingColor.g, settingColor.b, 60), (Color)settingColor, ShapeMode.Both, 0);
    }

    private void o0O0o(Render3DEvent render3DEvent, class_243 class_2432, SettingColor settingColor, class_2350 class_23502) {
        double d = 0.35;
        double d2 = 0.02;
        if (class_23502 == null) {
            class_23502 = class_2350.field_11036;
        }
        double d3 = class_2432.field_1352 + (double)class_23502.method_10148() * d2;
        double d4 = class_2432.field_1351 + (double)class_23502.method_10164() * d2;
        double d5 = class_2432.field_1350 + (double)class_23502.method_10165() * d2;
        switch (class_23502.method_10166()) {
            case field_11052: {
                render3DEvent.renderer.line(d3 - d, d4, d5 - d, d3 + d, d4, d5 + d, (Color)settingColor);
                render3DEvent.renderer.line(d3 - d, d4, d5 + d, d3 + d, d4, d5 - d, (Color)settingColor);
                break;
            }
            case field_11051: {
                render3DEvent.renderer.line(d3 - d, d4 - d, d5, d3 + d, d4 + d, d5, (Color)settingColor);
                render3DEvent.renderer.line(d3 - d, d4 + d, d5, d3 + d, d4 - d, d5, (Color)settingColor);
                break;
            }
            case field_11048: {
                render3DEvent.renderer.line(d3, d4 - d, d5 - d, d3, d4 + d, d5 + d, (Color)settingColor);
                render3DEvent.renderer.line(d3, d4 - d, d5 + d, d3, d4 + d, d5 - d, (Color)settingColor);
            }
        }
    }

    private TrajectoryResult o0O0o(float f) {
        class_638 class_6382 = this.mc.field_1687;
        class_243 class_2432 = this.mc.field_1724.method_5836(f).method_1023(0.0, 0.35, 0.0);
        class_243 class_2433 = this.mc.field_1724.method_5828(f).method_1021(1.5);
        ArrayList<class_243> arrayList = new ArrayList<class_243>();
        arrayList.add(class_2432);
        for (int i = 0; i < 300; ++i) {
            class_243 class_2434 = class_2432.method_1019(class_2433);
            class_1297 hitEntity = this.o0O0o(class_2432, class_2434);
            if (((Boolean)this.setting10.get()).booleanValue() && i >= 4 && hitEntity != null) {
                arrayList.add(class_2434);
                return new TrajectoryResult(arrayList, true, null, class_2350.field_11036);
            }
            class_3959 raycastContext = new class_3959(class_2432, class_2434, class_3959.class_3960.field_17558, class_3959.class_242.field_1347, (class_1297)this.mc.field_1724);
            class_3965 class_39652 = class_6382.method_17742(raycastContext);
            if (class_39652 != null && class_39652.method_17783() != class_239.class_240.field_1333) {
                arrayList.add(class_39652.method_17784());
                return new TrajectoryResult(arrayList, false, class_39652.method_17777(), class_39652.method_17780());
            }
            arrayList.add(class_2434);
            class_2432 = class_2434;
            class_2433 = class_2433.method_1021(0.99);
            class_2433 = class_2433.method_1023(0.0, 0.03, 0.0);
        }
        return new TrajectoryResult(arrayList, false, null, class_2350.field_11036);
    }

    private class_1297 o0O0o(class_243 class_2432, class_243 class_2433) {
        class_238 class_2382 = new class_238(class_2432, class_2433).method_1014(1.0);
        for (class_1297 class_12972 : this.mc.field_1687.method_8335((class_1297)this.mc.field_1724, class_2382)) {
            if (!(class_12972 instanceof class_1309) || !class_12972.method_5805() || class_12972 instanceof class_1657 || !class_12972.method_5829().method_1014(0.3).method_994(new class_238(class_2432, class_2433))) continue;
            return class_12972;
        }
        return null;
    }

    static class TrajectoryResult {
        final List<class_243> list01;
        final boolean flag02;
        final class_2338 field03;
        final class_2350 field04;

        TrajectoryResult(List<class_243> list, boolean bl, class_2338 class_23382, class_2350 class_23502) {
            this.list01 = list;
            this.flag02 = bl;
            this.field03 = class_23382;
            this.field04 = class_23502;
        }
    }
}
