package com.lyradebug;

import com.lyradebug.LyraDebug;
import com.lyradebug.StringCipher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.StringListSetting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1268;
import net.minecraft.class_1297;
import net.minecraft.class_1560;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_2338;
import net.minecraft.class_239;
import net.minecraft.class_2561;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_465;

public class ClickBetter
extends Module {
    private final SettingGroup group01;
    private final SettingGroup group02;
    private final SettingGroup group03;
    private final SettingGroup group04;
    private final SettingGroup group05;
    private final SettingGroup group06;
    private final SettingGroup group07;
    private final Setting<Boolean> setting08;
    private final Setting<Boolean> setting09;
    private final Setting<Boolean> setting10;
    private final Setting<Integer> setting11;
    private final Setting<Boolean> setting12;
    private final Setting<Boolean> setting13;
    private final Setting<Boolean> setting14;
    private final Setting<Boolean> setting15;
    private final Setting<Integer> setting16;
    private final Setting<Boolean> setting17;
    private final Setting<Integer> setting18;
    private final Setting<Integer> setting19;
    private final Setting<Integer> setting20;
    private final Setting<Integer> setting21;
    private final Setting<Double> setting22;
    private final Setting<Double> setting23;
    private final Setting<Double> setting24;
    private final Setting<Boolean> setting25;
    private final Setting<Boolean> setting26;
    private final Setting<Boolean> setting27;
    private final Setting<Boolean> setting28;
    private final Setting<Integer> setting29;
    private final Setting<Integer> setting30;
    private final Setting<Integer> setting31;
    private final Setting<Integer> setting32;
    private final Setting<Integer> setting33;
    private final Setting<Boolean> setting34;
    private final Setting<Boolean> setting35;
    private final Setting<Double> setting36;
    private final Setting<Boolean> setting37;
    private final Setting<Integer> setting38;
    private final Setting<Integer> setting39;
    private final Setting<Integer> setting40;
    private final Setting<Integer> setting41;
    private final Setting<Boolean> setting42;
    private final Setting<Double> setting43;
    private final Setting<Double> setting44;
    private final Setting<List<String>> setting45;
    private final Random field46;
    private int counter47;
    private class_2338 field48;
    private LookState field49;
    private float value50;
    private float value51;
    private float value52;
    private float value53;
    private int counter54;
    private int counter55;
    private String field56;
    private int counter57;
    private int counter58;
    private boolean flag59;
    private float value60;
    private class_1297 field61;
    private int counter62;
    private int counter63;
    private boolean flag64;
    private int counter65;
    private final Map<UUID, Boolean> map66;

    private String zZzZz() {
        class_239 class_2392 = this.mc.field_1765;
        if (class_2392 == null) {
            return "No Target";
        }
        if (class_2392.method_17783() == class_239.class_240.field_1332) {
            class_2338 class_23382 = ((class_3965)class_2392).method_17777();
            return this.mc.field_1687.method_8320(class_23382).method_26204().method_9518().getString();
        }
        if (class_2392.method_17783() == class_239.class_240.field_1331) {
            return ((class_3966)class_2392).method_17782().method_5477().getString();
        }
        return "No Target";
    }

    private void I1l1I(String string) {
        if (!((Boolean)this.setting34.get()).booleanValue() || this.mc.field_1724 == null) {
            return;
        }
        this.mc.field_1724.method_7353((class_2561)class_2561.method_43470((String)("[ClickBetter] " + string)), true);
    }

    public ClickBetter() {
        super(LyraDebug.CATEGORY, "click-better", "Human-like auto clicker and farming AI.");
        this.group01 = this.settings.getDefaultGroup();
        this.group02 = this.settings.createGroup("Left Click");
        this.group03 = this.settings.createGroup("Right Click");
        this.group04 = this.settings.createGroup("Look Randomize");
        this.group05 = this.settings.createGroup("Enderman Avoidance");
        this.group06 = this.settings.createGroup("Damage Reaction");
        this.group07 = this.settings.createGroup("Player Detect");
        this.setting08 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("only-on-block")).description("Only interact when crosshair is on a block (ignore entities).")).defaultValue(false)).build());
        this.setting09 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("pause-on-gui")).description("Pause automatically when GUI / inventory is open.")).defaultValue(true)).build());
        this.setting10 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("pause-on-empty-hand")).description("Pause when hand is empty.")).defaultValue(false)).build());
        this.setting11 = this.group01.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("delay-ticks")).description("Ticks to wait between clicks. 0 = continuous.")).defaultValue(0)).min(0).max(20).sliderMax(20).build());
        this.setting12 = this.group02.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("enable-left-click")).description("Enable automatic left clicking.")).defaultValue(false)).build());
        this.setting13 = this.group02.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("attack-blocks")).description("Automatically mine block in front of crosshair.")).defaultValue(true)).visible(() -> this.setting12.get())).build());
        this.setting14 = this.group02.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("attack-entities")).description("Automatically attack entity in front of crosshair.")).defaultValue(true)).visible(() -> this.setting12.get())).build());
        this.setting15 = this.group03.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("enable-right-click")).description("Enable automatic right clicking.")).defaultValue(true)).build());
        this.setting16 = this.group03.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("clicks-per-tick")).description("Right clicks per tick. 1 = standard, 4 = max.")).defaultValue(1)).min(1).max(4).sliderMax(4).visible(() -> this.setting15.get())).build());
        this.setting17 = this.group04.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("enable-look-randomize")).description("Randomly glance away and return like a real human player.")).defaultValue(true)).build());
        this.setting18 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("random-look-time-min")).description("Minimum interval (seconds) before glancing away.")).defaultValue(10)).min(1).max(300).sliderMax(120).visible(() -> this.setting17.get())).build());
        this.setting19 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("random-look-time-max")).description("Maximum interval (seconds) before glancing away.")).defaultValue(50)).min(1).max(300).sliderMax(120).visible(() -> this.setting17.get())).build());
        this.setting20 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("look-away-hold-min")).description("Minimum ticks to hold glance before returning.")).defaultValue(10)).min(1).max(200).sliderMax(100).visible(() -> this.setting17.get())).build());
        this.setting21 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("look-away-hold-max")).description("Maximum ticks to hold glance before returning.")).defaultValue(40)).min(1).max(400).sliderMax(200).visible(() -> this.setting17.get())).build());
        this.setting22 = this.group04.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("turn-speed")).description("Rotation speed per tick when turning away / back.")).defaultValue(10.0).range(1.0, 30.0).sliderRange(1.0, 30.0).visible(() -> this.setting17.get())).build());
        this.setting23 = this.group04.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("look-pitch-min")).description("Minimum vertical pitch when looking around.")).defaultValue(-20.0).range(-90.0, 90.0).sliderRange(-90.0, 90.0).visible(() -> this.setting17.get())).build());
        this.setting24 = this.group04.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("look-pitch-max")).description("Maximum vertical pitch when looking around.")).defaultValue(45.0).range(-90.0, 90.0).sliderRange(-90.0, 90.0).visible(() -> this.setting17.get())).build());
        this.setting25 = this.group04.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("enable-random-movement")).description("Randomly strafe or jump while glancing around.")).defaultValue(true)).visible(() -> this.setting17.get())).build());
        this.setting26 = this.group04.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("allow-strafe-left")).description("Allow AI to strafe left.")).defaultValue(true)).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false)).build());
        this.setting27 = this.group04.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("allow-strafe-right")).description("Allow AI to strafe right.")).defaultValue(true)).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false)).build());
        this.setting28 = this.group04.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("allow-jump")).description("Allow AI to jump.")).defaultValue(true)).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false)).build());
        this.setting29 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("movement-chance")).description("Chance (%) to move while looking away.")).defaultValue(50)).min(0).max(100).sliderMax(100).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false)).build());
        this.setting30 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("time-strafe-left-min")).description("Minimum ticks to hold strafe left.")).defaultValue(4)).min(1).max(60).sliderMax(40).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false && (Boolean)this.setting26.get() != false)).build());
        this.setting31 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("time-strafe-left-max")).description("Maximum ticks to hold strafe left.")).defaultValue(12)).min(1).max(80).sliderMax(60).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false && (Boolean)this.setting26.get() != false)).build());
        this.setting32 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("time-strafe-right-min")).description("Minimum ticks to hold strafe right.")).defaultValue(4)).min(1).max(60).sliderMax(40).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false && (Boolean)this.setting27.get() != false)).build());
        this.setting33 = this.group04.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("time-strafe-right-max")).description("Maximum ticks to hold strafe right.")).defaultValue(12)).min(1).max(80).sliderMax(60).visible(() -> (Boolean)this.setting17.get() != false && (Boolean)this.setting25.get() != false && (Boolean)this.setting27.get() != false)).build());
        this.setting34 = this.group04.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("debug-log")).description("Show status in actionbar.")).defaultValue(true)).visible(() -> this.setting17.get())).build());
        this.setting35 = this.group05.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("avoid-eye-contact")).description("Automatically glance away if looking directly at an Enderman.")).defaultValue(true)).build());
        this.setting36 = this.group05.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("dodge-yaw-range")).description("Yaw angle offset when dodging Enderman gaze.")).defaultValue(50.0).range(15.0, 120.0).sliderRange(15.0, 120.0).visible(() -> this.setting35.get())).build());
        this.setting37 = this.group06.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("enable-damage-reaction")).description("Look at attacker, sneak spam, and wait when damaged.")).defaultValue(true)).build());
        this.setting38 = this.group06.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("sneak-spam-count")).description("Number of sneak taps when damaged.")).defaultValue(3)).min(1).max(10).sliderMax(10).visible(() -> this.setting37.get())).build());
        this.setting39 = this.group06.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("sneak-press-ticks")).description("Ticks to hold sneak.")).defaultValue(3)).min(1).max(20).sliderMax(20).visible(() -> this.setting37.get())).build());
        this.setting40 = this.group06.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("sneak-release-ticks")).description("Ticks between sneaks.")).defaultValue(3)).min(1).max(20).sliderMax(20).visible(() -> this.setting37.get())).build());
        this.setting41 = this.group06.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("wait-after-seconds")).description("Seconds to wait after sneaking before farming again.")).defaultValue(5)).min(1).max(60).sliderMax(30).visible(() -> this.setting37.get())).build());
        this.setting42 = this.group07.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("enable-player-detect")).description("Disconnect automatically when another player approaches.")).defaultValue(false)).build());
        this.setting43 = this.group07.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("detect-range")).description("Radius to detect nearby players in meters.")).defaultValue(30.0).min(1.0).max(100.0).sliderMax(64.0).visible(() -> this.setting42.get())).build());
        this.setting44 = this.group07.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("admin-tpa-range")).description("Sudden appearance threshold below this is treated as admin check.")).defaultValue(6.0).min(1.0).max(20.0).sliderMax(15.0).visible(() -> this.setting42.get())).build());
        this.setting45 = this.group07.add((Setting)((StringListSetting.Builder)((StringListSetting.Builder)((StringListSetting.Builder)((StringListSetting.Builder)new StringListSetting.Builder().name("whitelist")).description("Player names to ignore.")).defaultValue(Collections.emptyList())).visible(() -> this.setting42.get())).build());
        this.field46 = new Random();
        this.counter47 = 0;
        this.field48 = null;
        this.field49 = LookState.constant01;
        this.counter54 = 0;
        this.counter55 = 0;
        this.field56 = "Unknown";
        this.counter57 = 0;
        this.counter58 = 0;
        this.flag59 = false;
        this.value60 = -1.0f;
        this.field61 = null;
        this.counter62 = 0;
        this.counter63 = 0;
        this.flag64 = false;
        this.counter65 = 0;
        this.map66 = new HashMap<UUID, Boolean>();
    }

    public void onActivate() {
        this.counter47 = 0;
        this.field48 = null;
        this.field49 = LookState.constant01;
        this.counter54 = this.Il1lI();
        this.map66.clear();
    }

    public void onDeactivate() {
        this.methodCollision07();
        this.methodCollision11();
        this.mc.field_1690.field_1832.method_23481(false);
        this.map66.clear();
    }

    @EventHandler
    private void o0O0o(TickEvent.Pre pre) {
        block25: {
            boolean bl;
            class_239 class_2392;
            block26: {
                boolean bl2;
                class_1799 class_17992;
                if (this.mc.field_1724 == null || this.mc.field_1687 == null || this.mc.field_1761 == null) {
                    return;
                }
                if (((Boolean)this.setting42.get()).booleanValue()) {
                    this.methodCollision01();
                    if (this.mc.field_1724 == null || this.mc.field_1687 == null) {
                        return;
                    }
                }
                if (((Boolean)this.setting09.get()).booleanValue() && this.mc.field_1755 instanceof class_465) {
                    this.methodCollision07();
                    return;
                }
                if (((Boolean)this.setting10.get()).booleanValue() && ((class_17992 = this.mc.field_1724.method_6047()) == null || class_17992.method_7960())) {
                    this.methodCollision07();
                    return;
                }
                float f = this.mc.field_1724.method_6032();
                if (this.value60 < 0.0f) {
                    this.value60 = f;
                }
                if (((Boolean)this.setting37.get()).booleanValue() && f < this.value60 && this.field49 != LookState.constant05 && this.field49 != LookState.constant06 && this.field49 != LookState.constant07) {
                    this.methodCollision03();
                }
                this.value60 = f;
                if (((Boolean)this.setting35.get()).booleanValue() && this.I1l1I()) {
                    float f2 = this.mc.field_1724.method_36454() + this.o0O0o((Double)this.setting36.get());
                    float f3 = this.Il1lI(this.mc.field_1724.method_36455() + (this.field46.nextBoolean() ? 10.0f : -10.0f));
                    this.o0O0o(f2, f3, ((Double)this.setting22.get()).floatValue());
                    this.methodCollision07();
                    return;
                }
                boolean bl3 = this.O0o0O();
                if (!bl3) {
                    this.methodCollision07();
                    return;
                }
                if ((Integer)this.setting11.get() > 0) {
                    ++this.counter47;
                    if (this.counter47 < (Integer)this.setting11.get()) {
                        return;
                    }
                    this.counter47 = 0;
                }
                if ((class_2392 = this.mc.field_1765) == null) {
                    this.methodCollision07();
                    return;
                }
                if (((Boolean)this.setting08.get()).booleanValue() && class_2392.method_17783() != class_239.class_240.field_1332) {
                    this.methodCollision07();
                    return;
                }
                bl = class_2392.method_17783() == class_239.class_240.field_1332;
                boolean bl4 = bl2 = class_2392.method_17783() == class_239.class_240.field_1331;
                if (((Boolean)this.setting12.get()).booleanValue()) {
                    if (bl2 && ((Boolean)this.setting14.get()).booleanValue()) {
                        class_1297 targetEntity = ((class_3966)class_2392).method_17782();
                        this.mc.field_1761.method_2918((class_1657)this.mc.field_1724, targetEntity);
                        this.mc.field_1724.method_6104(class_1268.field_5808);
                        this.methodCollision07();
                    } else if (bl && ((Boolean)this.setting13.get()).booleanValue()) {
                        class_3965 blockHit = (class_3965)class_2392;
                        class_2338 class_23382 = blockHit.method_17777();
                        if (!class_23382.equals(this.field48)) {
                            this.methodCollision07();
                            this.mc.field_1761.method_2910(class_23382, blockHit.method_17780());
                            this.field48 = class_23382;
                        } else if (!this.mc.field_1761.method_2902(class_23382, blockHit.method_17780())) {
                            this.field48 = null;
                        }
                        this.mc.field_1724.method_6104(class_1268.field_5808);
                    } else {
                        this.methodCollision07();
                    }
                } else {
                    this.methodCollision07();
                }
                if (!((Boolean)this.setting15.get()).booleanValue()) break block25;
                if (!((Boolean)this.setting08.get()).booleanValue()) break block26;
                if (!bl) break block25;
                class_3965 blockHit = (class_3965)class_2392;
                for (int i = 0; i < (Integer)this.setting16.get(); ++i) {
                    this.mc.field_1761.method_2896(this.mc.field_1724, class_1268.field_5808, blockHit);
                    this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, class_1268.field_5808);
                }
                break block25;
            }
            for (int i = 0; i < (Integer)this.setting16.get(); ++i) {
                if (bl) {
                    this.mc.field_1761.method_2896(this.mc.field_1724, class_1268.field_5808, (class_3965)class_2392);
                }
                this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, class_1268.field_5808);
            }
        }
    }

    private boolean O0o0O() {
        if (!((Boolean)this.setting17.get()).booleanValue()) {
            return true;
        }
        switch (this.field49.ordinal()) {
            case 0: {
                this.field56 = this.zZzZz();
                if (this.counter54 % 20 == 0) {
                    int n = this.counter54 / 20;
                    this.I1l1I("§4x1&,\0251" + this.field56 + " p{p,qt\001Rhx>" + n + "]");
                }
                --this.counter54;
                if (this.counter54 <= 0) {
                    this.value50 = this.mc.field_1724.method_36454();
                    this.value51 = this.mc.field_1724.method_36455();
                    this.value52 = this.o0O0o(this.field46.nextFloat() * 360.0f - 180.0f);
                    this.value53 = (float)((Double)this.setting23.get() + this.field46.nextDouble() * ((Double)this.setting24.get() - (Double)this.setting23.get()));
                    this.field49 = LookState.constant02;
                }
                return true;
            }
            case 1: {
                boolean bl = this.o0O0o(this.value52, this.value53, ((Double)this.setting22.get()).floatValue());
                if (bl) {
                    this.counter55 = this.o0O0o((Integer)this.setting20.get(), (Integer)this.setting21.get());
                    this.methodCollision09();
                    this.field49 = LookState.constant03;
                }
                return false;
            }
            case 2: {
                this.methodCollision05();
                --this.counter55;
                if (this.counter55 <= 0) {
                    this.methodCollision11();
                    this.field49 = LookState.constant04;
                }
                return false;
            }
            case 3: {
                boolean bl = this.o0O0o(this.value50, this.value51, ((Double)this.setting22.get()).floatValue());
                if (bl) {
                    this.field49 = LookState.constant01;
                    this.counter54 = this.Il1lI();
                    return true;
                }
                return false;
            }
            case 4: {
                if (this.field61 == null || !this.field61.method_5805()) {
                    this.field49 = LookState.constant06;
                    return false;
                }
                float[] fArray = this.o0O0o(this.field61);
                boolean bl = this.o0O0o(fArray[0], fArray[1], ((Double)this.setting22.get()).floatValue());
                if (bl) {
                    this.field49 = LookState.constant06;
                }
                return false;
            }
            case 5: {
                if (this.field61 != null && this.field61.method_5805()) {
                    float[] fArray = this.o0O0o(this.field61);
                    this.o0O0o(fArray[0], fArray[1], ((Double)this.setting22.get()).floatValue());
                }
                --this.counter63;
                if (this.counter63 <= 0) {
                    this.flag64 = !this.flag64;
                    this.mc.field_1690.field_1832.method_23481(this.flag64);
                    this.counter63 = this.flag64 ? (Integer)this.setting39.get() : (Integer)this.setting40.get();
                    if (!this.flag64) {
                        ++this.counter62;
                    }
                }
                if (this.counter62 >= (Integer)this.setting38.get() && !this.flag64) {
                    this.mc.field_1690.field_1832.method_23481(false);
                    this.counter65 = (Integer)this.setting41.get() * 20;
                    this.field49 = LookState.constant07;
                }
                return false;
            }
            case 6: {
                --this.counter65;
                if (this.counter65 <= 0) {
                    this.field49 = LookState.constant04;
                }
                return false;
            }
        }
        return true;
    }

    private boolean o0O0o(float f, float f2, float f3) {
        float f4 = class_3532.method_15393((float)(f - this.mc.field_1724.method_36454()));
        float f5 = f2 - this.mc.field_1724.method_36455();
        float f6 = class_3532.method_15363((float)(f4 * 0.3f), (float)(-f3), (float)f3);
        float f7 = class_3532.method_15363((float)(f5 * 0.3f), (float)(-f3), (float)f3);
        this.mc.field_1724.method_36456(this.mc.field_1724.method_36454() + f6);
        this.mc.field_1724.method_36457(this.Il1lI(this.mc.field_1724.method_36455() + f7));
        return Math.abs(f4) < 1.0f && Math.abs(f5) < 1.0f;
    }

    private int Il1lI() {
        int n = (Integer)this.setting18.get() * 20;
        int n2 = Math.max(n, (Integer)this.setting19.get() * 20);
        return n + this.field46.nextInt(n2 - n + 1);
    }

    private int o0O0o(int n, int n2) {
        int n3 = Math.min(n, n2);
        int n4 = Math.max(n, n2);
        return n3 + this.field46.nextInt(n4 - n3 + 1);
    }

    private float o0O0o(float f) {
        return class_3532.method_15393((float)f);
    }

    private void methodCollision09() {
        this.counter57 = 0;
        this.counter58 = 0;
        this.flag59 = false;
        if (!((Boolean)this.setting25.get()).booleanValue()) {
            return;
        }
        if (this.field46.nextInt(100) >= (Integer)this.setting29.get()) {
            return;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (((Boolean)this.setting26.get()).booleanValue()) {
            arrayList.add(-1);
        }
        if (((Boolean)this.setting27.get()).booleanValue()) {
            arrayList.add(1);
        }
        if (((Boolean)this.setting28.get()).booleanValue()) {
            arrayList.add(0);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        int n = (Integer)arrayList.get(this.field46.nextInt(arrayList.size()));
        if (n == -1) {
            this.counter57 = -1;
            this.counter58 = this.o0O0o((Integer)this.setting30.get(), (Integer)this.setting31.get());
        } else if (n == 1) {
            this.counter57 = 1;
            this.counter58 = this.o0O0o((Integer)this.setting32.get(), (Integer)this.setting33.get());
        } else {
            this.flag59 = true;
        }
    }

    private void methodCollision05() {
        if (!((Boolean)this.setting25.get()).booleanValue()) {
            return;
        }
        if (this.counter57 != 0 && this.counter58 > 0) {
            this.mc.field_1690.field_1913.method_23481(this.counter57 < 0);
            this.mc.field_1690.field_1849.method_23481(this.counter57 > 0);
            --this.counter58;
            if (this.counter58 <= 0) {
                this.mc.field_1690.field_1913.method_23481(false);
                this.mc.field_1690.field_1849.method_23481(false);
                this.counter57 = 0;
            }
        }
        if (this.flag59 && this.mc.field_1724.method_24828()) {
            this.mc.field_1724.method_6043();
            this.flag59 = false;
        }
    }

    private void methodCollision11() {
        this.mc.field_1690.field_1913.method_23481(false);
        this.mc.field_1690.field_1849.method_23481(false);
        this.counter57 = 0;
        this.counter58 = 0;
        this.flag59 = false;
    }

    private boolean I1l1I() {
        class_3966 class_39662;
        class_239 class_2392 = this.mc.field_1765;
        return class_2392 instanceof class_3966 && (class_39662 = (class_3966)class_2392).method_17782() instanceof class_1560;
    }

    private float o0O0o(double d) {
        float f = (float)(d * 0.5 + this.field46.nextDouble() * d * 0.5);
        return this.field46.nextBoolean() ? f : -f;
    }

    private float Il1lI(float f) {
        return class_3532.method_15363((float)f, (float)-90.0f, (float)90.0f);
    }

    private float[] o0O0o(class_1297 class_12972) {
        double d = class_12972.method_23317() - this.mc.field_1724.method_23317();
        double d2 = class_12972.method_23321() - this.mc.field_1724.method_23321();
        double d3 = (class_12972.method_5829().field_1322 + class_12972.method_5829().field_1325) / 2.0 - (this.mc.field_1724.method_23318() + (double)this.mc.field_1724.method_18381(this.mc.field_1724.method_18376()));
        double d4 = Math.sqrt(d * d + d2 * d2);
        float f = (float)(Math.toDegrees(Math.atan2(d2, d)) - 90.0);
        float f2 = (float)(-Math.toDegrees(Math.atan2(d3, d4)));
        return new float[]{f, f2};
    }

    private void methodCollision03() {
        this.field61 = this.mc.field_1724.method_6065();
        if (this.field49 == LookState.constant01) {
            this.value50 = this.mc.field_1724.method_36454();
            this.value51 = this.mc.field_1724.method_36455();
        }
        this.counter62 = 0;
        this.counter63 = 0;
        this.flag64 = false;
        this.field49 = this.field61 != null ? LookState.constant05 : LookState.constant06;
    }

    private void methodCollision07() {
        if (this.field48 != null) {
            this.mc.field_1761.method_2925();
            this.field48 = null;
        }
    }

    private void methodCollision01() {
        if (this.mc.field_1687 == null || this.mc.field_1724 == null) {
            return;
        }
        HashSet<UUID> hashSet = new HashSet<UUID>();
        for (class_1657 class_16572 : this.mc.field_1687.method_18456()) {
            if (class_16572 == this.mc.field_1724) continue;
            hashSet.add(class_16572.method_5667());
        }
        this.map66.keySet().retainAll(hashSet);
        for (class_1657 class_16572 : this.mc.field_1687.method_18456()) {
            Boolean bl;
            if (class_16572 == this.mc.field_1724) continue;
            String string = class_16572.method_5477().getString();
            boolean bl2 = false;
            for (String string2 : this.setting45.get()) {
                if (!string2.trim().equalsIgnoreCase(string.trim())) continue;
                bl2 = true;
                break;
            }
            if (bl2) continue;
            UUID uUID = class_16572.method_5667();
            double d = this.mc.field_1724.method_5739((class_1297)class_16572);
            if (!this.map66.containsKey(uUID)) {
                boolean bl3 = d < (Double)this.setting44.get();
                this.map66.put(uUID, bl3);
            }
            if ((bl = this.map66.get(uUID)) == null || bl.booleanValue() || !(d <= (Double)this.setting43.get())) continue;
            if (this.mc.method_1562() == null || this.mc.method_1562().method_48296() == null) break;
            this.mc.method_1562().method_48296().method_10747((class_2561)class_2561.method_43470((String)("r~ae%d+Wl?6e\0339P6" + string)));
            break;
        }
    }

    enum LookState {
        constant01,
        constant02,
        constant03,
        constant04,
        constant05,
        constant06,
        constant07
    }
}
