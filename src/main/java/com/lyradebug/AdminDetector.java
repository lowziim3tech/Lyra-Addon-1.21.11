package com.lyradebug;

import com.lyradebug.AdminListHud;
import com.lyradebug.AdminTextureHelper;
import com.lyradebug.AdminToastStack;
import com.lyradebug.LyraDebug;
import com.lyradebug.StringCipher;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_634;
import net.minecraft.class_640;
import net.minecraft.class_2561;

public final class AdminDetector
extends Module {
    private static final int constant01 = 10;
    private static final int constant02 = 90000;
    private static final int constant03 = 15000;
    private static final int constant04 = -10;
    private static final int constant05 = 15;
    private static final int constant06 = 30;
    private static final int constant07 = 60;
    private static final Set<String> constant08 = Set.of("archivepedro", "frwost", "w1zox_", "fluffymaster07", "bautiedgar", "showered", "pastagamer08", "itszdeath", "0gsummer");
    private final SettingGroup group09;
    private final SettingGroup group10;
    private final Setting<Boolean> setting11;
    private final Setting<Boolean> setting12;
    private final Setting<Integer> setting13;
    private final Setting<Integer> setting14;
    private final Setting<Integer> setting15;
    private final Set<String> set16;
    private final AdminListHud field17;
    private final AdminToastStack field18;
    private long counter19;
    private long counter20;
    private double value21;
    private boolean flag22;
    private boolean flag23;
    private int counter24;

    public AdminDetector() {
        super(LyraDebug.CATEGORY, "admin-detector", "Alerts when an admin joins or leaves tab.");
        this.group09 = this.settings.getDefaultGroup();
        this.group10 = this.settings.createGroup("HUD");
        this.setting11 = this.group09.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("alerts")).description("Toast and sound on join or leave.")).defaultValue(true)).build());
        this.setting12 = this.group09.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("admin-list")).description("Show the Online Admin List panel.")).defaultValue(true)).build());
        this.setting13 = this.group10.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("hud-x")).description("Panel X position (-1 for default).")).defaultValue(-1)).min(-1).max(4000).sliderMax(4000).build());
        this.setting14 = this.group10.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("hud-y")).description("Panel Y position (-1 for default).")).defaultValue(-1)).min(-1).max(4000).sliderMax(4000).build());
        this.setting15 = this.group10.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("admin-list-size")).description("Scale of the Online Admin List panel, heads, and text.")).defaultValue(100)).min(70).max(150).sliderRange(70, 150).build());
        this.set16 = new HashSet<String>();
        this.field17 = new AdminListHud();
        this.field18 = new AdminToastStack();
        this.value21 = -999.0;
    }

    public void onActivate() {
        this.methodCollision01();
    }

    public void onDeactivate() {
        this.methodCollision01();
    }

    public String getInfoString() {
        return this.set16.isEmpty() ? "clear" : this.set16.size() + " online";
    }

    private void methodCollision01() {
        this.set16.clear();
        this.field17.I1Il1();
        this.field18.I1Il1();
        this.counter19 = System.currentTimeMillis() + 3000L;
        this.counter20 = 0L;
        this.value21 = -999.0;
        this.flag22 = false;
        this.flag23 = false;
        this.counter24 = 0;
    }

    @EventHandler
    private void o0O0o(TickEvent.Post post) {
        if (this.mc.field_1724 != null && this.mc.field_1687 != null && this.mc.method_1562() != null) {
            boolean bl;
            ++this.counter24;
            boolean bl2 = bl = this.counter24 % 10 == 0;
            if (bl || this.zZzZz()) {
                Set<String> set = this.o0O0o();
                if (!this.flag23) {
                    this.set16.clear();
                    this.set16.addAll(set);
                    this.field17.o0O0o(set, Set.of(), Set.of(), true);
                    this.flag23 = true;
                } else {
                    HashSet<String> hashSet = new HashSet<String>(set);
                    hashSet.removeAll(this.set16);
                    HashSet<String> hashSet2 = new HashSet<String>(this.set16);
                    hashSet2.removeAll(set);
                    this.field17.o0O0o(set, hashSet, hashSet2, false);
                    if (!(hashSet.isEmpty() && hashSet2.isEmpty() || !((Boolean)this.setting11.get()).booleanValue())) {
                        for (String string : hashSet) {
                            this.field18.o0O0o(string, true, true);
                        }
                        for (String string : hashSet2) {
                            this.field18.o0O0o(string, false, true);
                        }
                    }
                    this.set16.clear();
                    this.set16.addAll(set);
                }
            }
        }
    }

    @EventHandler
    private void o0O0o(Render2DEvent render2DEvent) {
        if (((Boolean)this.setting12.get()).booleanValue()) {
            this.field17.o0O0o(this.setting13, this.setting14, this.setting15);
            this.field17.o0O0o(render2DEvent.drawContext, this.setting13, this.setting14, this.setting15);
        }
        this.field18.o0O0o(render2DEvent.drawContext);
    }

    private boolean zZzZz() {
        long l = System.currentTimeMillis();
        if (this.mc.field_1724 == null) {
            return false;
        }
        double d = this.mc.field_1724.method_23318();
        if (l >= this.counter19) {
            this.O0o0O();
            return true;
        }
        if (d <= -10.0 && this.methodCollision02()) {
            this.I1l1I();
            return true;
        }
        if (d > this.value21) {
            this.value21 = d;
        }
        if (this.value21 - d >= 15.0 && this.methodCollision02()) {
            this.I1l1I();
            this.value21 = d;
            return true;
        }
        if (!this.flag22 && this.mc.field_1724.field_6012 > 60) {
            this.flag22 = true;
            return true;
        }
        return false;
    }

    private void O0o0O() {
        this.counter19 = System.currentTimeMillis() + 90000L - 15000L + (long)(Math.random() * 15000.0 * 2.0);
    }

    private boolean methodCollision02() {
        return System.currentTimeMillis() - this.counter20 >= 30000L;
    }

    private void I1l1I() {
        this.counter20 = System.currentTimeMillis();
    }

    private Set<String> o0O0o() {
        String string;
        HashSet<String> hashSet = new HashSet<String>();
        class_634 class_6342 = this.mc.method_1562();
        if (class_6342 == null) {
            return hashSet;
        }
        for (class_640 object : class_6342.method_2880()) {
            string = this.o0O0o(object);
            if (string == null) continue;
            hashSet.add(string);
        }
        for (String string2 : constant08) {
            string = AdminTextureHelper.xXxXx(string2);
            if (class_6342.method_2874(string) != null) {
                hashSet.add(string2);
            }
            if (class_6342.method_2874(string2) == null) continue;
            hashSet.add(string2);
        }
        return hashSet;
    }

    private String o0O0o(class_640 class_6402) {
        String string;
        String profileName;
        if (class_6402.method_2966() != null && class_6402.method_2966().name() != null && constant08.contains(profileName = class_6402.method_2966().name().toLowerCase(Locale.ROOT))) {
            return profileName;
        }
        class_2561 displayName = class_6402.method_2971();
        if (displayName != null && constant08.contains(string = AdminDetector.o0O0o(displayName.getString()))) {
            if (class_6402.method_2966() != null && class_6402.method_2966().name() != null && !class_6402.method_2966().name().isEmpty()) {
                return class_6402.method_2966().name().toLowerCase(Locale.ROOT);
            }
            return string;
        }
        return null;
    }

    private static String o0O0o(String string) {
        return string.replaceAll("§.", "").trim().toLowerCase(Locale.ROOT);
    }
}
