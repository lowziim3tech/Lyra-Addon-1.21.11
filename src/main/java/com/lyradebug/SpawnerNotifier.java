package com.lyradebug;

import com.lyradebug.LyraDebug;
import com.lyradebug.SpawnerToast;
import com.lyradebug.StringCipher;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import meteordevelopment.meteorclient.events.game.GameJoinedEvent;
import meteordevelopment.meteorclient.events.game.GameLeftEvent;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.Renderer2D;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.renderer.text.TextRenderer;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.PlayerUtils;
import meteordevelopment.meteorclient.utils.render.NametagUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1937;
import net.minecraft.class_1917;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2586;
import net.minecraft.class_2636;
import net.minecraft.class_2818;
import net.minecraft.class_2960;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_368;
import net.minecraft.class_638;
import net.minecraft.class_7225;
import net.minecraft.class_7923;
import org.joml.Vector3d;

public final class SpawnerNotifier
extends Module {
    private final SettingGroup group01;
    private final SettingGroup group02;
    private final SettingGroup group03;
    private final Setting<Integer> setting04;
    public final Setting<SettingColor> setting05;
    private final Setting<Integer> setting06;
    private final Setting<Boolean> setting07;
    private final Setting<SettingColor> setting08;
    private final Setting<Integer> setting09;
    private final Setting<Double> setting10;
    private final Setting<ShapeMode> setting11;
    private final Setting<Boolean> setting12;
    private final Setting<Integer> setting13;
    private final Setting<SettingColor> setting14;
    private final Setting<Boolean> setting15;
    private final Setting<Boolean> setting16;
    private final Map<class_2338, String> map17;
    private final Map<class_2338, String> map18;
    private final Set<Long> set19;
    private final Color field20;
    private final Color field21;
    private final Color field22;
    private final Color field23;
    private final Color field24;
    private final Color field25;
    private boolean flag26;
    private boolean flag27;
    private int counter28;

    public SpawnerNotifier() {
        super(LyraDebug.CATEGORY, "spawner-notifier", "bedrock-esp");
        this.group01 = this.settings.getDefaultGroup();
        this.group02 = this.settings.createGroup("Render");
        this.group03 = this.settings.createGroup("Alerts");
        this.setting04 = this.group01.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("range")).description("Detection range in blocks.")).defaultValue(256)).min(8).max(512).sliderMax(512).build());
        this.setting05 = this.group02.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("pillar-color")).description("Color of the vertical beacon pillar.")).defaultValue(new SettingColor(255, 0, 0, 80)).build());
        this.setting06 = this.group02.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("pillar-opacity")).description("Opacity of the vertical beacon pillar.")).defaultValue(80)).min(0).max(255).sliderMax(255).build());
        this.setting07 = this.group02.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("spawner-esp")).description("Render bounding box around spawners.")).defaultValue(true)).build());
        this.setting08 = this.group02.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("spawner-color")).description("Color of the spawner box.")).defaultValue(new SettingColor(255, 0, 0, 150)).visible(() -> this.setting07.get())).build());
        this.setting09 = this.group02.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("spawner-alpha")).description("Opacity of the spawner box.")).defaultValue(80)).min(0).max(255).sliderMax(255).visible(() -> this.setting07.get())).build());
        this.setting10 = this.group02.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("text-scale")).description("Scale of the spawner nametag.")).defaultValue(1.2).min(0.1).max(5.0).sliderMax(5.0).build());
        this.setting11 = this.group02.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("shape-mode")).description("Render mode for boxes.")).defaultValue(ShapeMode.Both)).build());
        this.setting12 = this.group03.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("toast-alerts")).description("Show custom on-screen toast notifications.")).defaultValue(true)).build());
        this.setting13 = this.group03.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("toast-duration")).description("Duration of the toast notification in ms.")).defaultValue(6500)).min(2500).max(15000).sliderRange(2500, 15000).visible(() -> this.setting12.get())).build());
        this.setting14 = this.group03.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("progress-bar-color")).description("Color of toast progress bar.")).defaultValue(new SettingColor(224, 69, 69, 255)).visible(() -> this.setting12.get())).build());
        this.setting15 = this.group03.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("chat-alerts")).description("Send notification in chat.")).defaultValue(true)).build());
        this.setting16 = this.group03.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("sound-alerts")).description("Play alert sound when a spawner is found.")).defaultValue(true)).build());
        this.map17 = new HashMap<class_2338, String>();
        this.map18 = new HashMap<class_2338, String>();
        this.set19 = new HashSet<Long>();
        this.field20 = new Color();
        this.field21 = new Color();
        this.field22 = new Color();
        this.field23 = new Color(14, 14, 14, 175);
        this.field24 = new Color(255, 255, 255, 255);
        this.field25 = new Color(184, 168, 232, 220);
    }

    public void onActivate() {
        this.IlIIl();
    }

    public void onDeactivate() {
        this.IlIIl();
    }

    private void IlIIl() {
        this.map17.clear();
        this.map18.clear();
        this.set19.clear();
        this.counter28 = 0;
        this.flag27 = true;
        this.flag26 = false;
    }

    @EventHandler
    private void o0O0o(GameJoinedEvent gameJoinedEvent) {
        this.IlIIl();
    }

    @EventHandler
    private void o0O0o(GameLeftEvent gameLeftEvent) {
        this.IlIIl();
    }

    @EventHandler
    private void o0O0o(TickEvent.Post post) {
        if (this.mc.field_1687 != null && this.mc.field_1724 != null) {
            if (!this.flag26) {
                this.set19.clear();
                this.map18.clear();
                this.map17.clear();
                this.counter28 = 0;
                this.flag27 = true;
                this.flag26 = true;
            }
            if (this.flag27) {
                this.flag27 = false;
            } else {
                ++this.counter28;
                if (this.counter28 < 20) {
                    return;
                }
                this.counter28 = 0;
            }
            int n = (Integer)this.setting04.get();
            int n2 = n * n;
            class_2338 class_23383 = this.mc.field_1724.method_24515();
            class_638 class_6382 = this.mc.field_1687;
            HashMap<class_2338, String> hashMap = new HashMap<class_2338, String>();
            HashSet<Long> hashSet = new HashSet<Long>();
            int n3 = class_23383.method_10263();
            int n4 = class_23383.method_10260();
            int n5 = n3 - n >> 4;
            int n6 = n3 + n >> 4;
            int n7 = n4 - n >> 4;
            int n8 = n4 + n >> 4;
            for (int i = n5; i <= n6; ++i) {
                for (int j = n7; j <= n8; ++j) {
                    if (!class_6382.method_8398().method_12123(i, j)) continue;
                    class_2818 class_28182 = class_6382.method_8497(i, j);
                    for (class_2586 class_25862 : class_28182.method_12214().values()) {
                        class_2636 class_26362;
                        class_2338 class_23384;
                        double d;
                        if (!(class_25862 instanceof class_2636) || !((d = PlayerUtils.squaredDistanceTo((double)((double)(class_23384 = (class_26362 = (class_2636)class_25862).method_11016()).method_10263() + 0.5), (double)((double)class_23384.method_10264() + 0.5), (double)((double)class_23384.method_10260() + 0.5))) <= (double)n2)) continue;
                        String string = this.o0O0o(class_26362, class_23384);
                        hashMap.put(class_23384, string);
                        long l2 = class_23384.method_10063();
                        hashSet.add(l2);
                        if (this.set19.contains(l2)) continue;
                        this.set19.add(l2);
                        if (((Boolean)this.setting12.get()).booleanValue() && this.mc.method_1566() != null) {
                            SettingColor settingColor = (SettingColor)this.setting14.get();
                            this.mc.method_1566().method_1999((class_368)new SpawnerToast(string, class_23384.method_10263(), class_23384.method_10264(), class_23384.method_10260(), ((Integer)this.setting13.get()).intValue(), (Boolean)this.setting16.get(), settingColor.r << 16 | settingColor.g << 8 | settingColor.b));
                        } else if (((Boolean)this.setting16.get()).booleanValue() && this.mc.field_1724 != null) {
                            this.mc.field_1724.method_5783((class_3414)class_3417.field_14622.comp_349(), 1.0f, 0.8f);
                        }
                        if (!((Boolean)this.setting15.get()).booleanValue() || this.mc.field_1724 == null) continue;
                        this.info("[Spawner] " + string + " Spawner at " + class_23384.method_10263() + ", " + class_23384.method_10264() + ", " + class_23384.method_10260(), new Object[0]);
                    }
                }
            }
            this.set19.removeIf(l -> !hashSet.contains(l));
            this.map18.keySet().removeIf(class_23382 -> !hashSet.contains(class_23382.method_10063()));
            this.map17.clear();
            this.map17.putAll(hashMap);
        } else {
            this.flag26 = false;
        }
    }

    private String o0O0o(class_2636 class_26362, class_2338 class_23382) {
        String string = this.o0O0o(class_26362);
        String string2 = this.map18.get(class_23382);
        if (this.o0O0o(string) && string2 != null && !this.o0O0o(string2)) {
            return string2;
        }
        if (!this.o0O0o(string)) {
            this.map18.put(class_23382, string);
            return string;
        }
        return string2 != null ? string2 : string;
    }

    private boolean o0O0o(String string) {
        return string == null || string.isEmpty() || "Unknown".equalsIgnoreCase(string) || "Pig".equalsIgnoreCase(string);
    }

    private String o0O0o(class_2636 class_26362) {
        try {
            class_1297 class_12972;
            String entityName;
            if (this.mc.field_1687 != null) {
                entityName = this.o0O0o(class_26362.method_38242((class_7225.class_7874)this.mc.field_1687.method_30349()));
                if (entityName != null) {
                    return entityName;
                }
                entityName = this.o0O0o(class_26362.method_58692((class_7225.class_7874)this.mc.field_1687.method_30349()));
                if (entityName != null) {
                    return entityName;
                }
            }
            class_1917 spawnerLogic = class_26362.method_11390();
            if (spawnerLogic != null && this.mc.field_1687 != null && (class_12972 = spawnerLogic.method_8283((class_1937)this.mc.field_1687, class_26362.method_11016())) != null) {
                return class_12972.method_5864().method_5897().getString();
            }
        }
        catch (Exception exception) {
        }
        return "Unknown";
    }

    private String o0O0o(class_2487 class_24872) {
        if (class_24872 == null) {
            return null;
        }
        for (String string : new String[]{"SpawnData", "spawn_data"}) {
            if (!class_24872.method_10545(string)) continue;
            Optional<?> spawnData = class_24872.method_10562(string);
            if (spawnData.isEmpty()) continue;
            class_2487 class_24873 = (class_2487)spawnData.get();
            if (class_24873.method_10545("entity")) {
                Optional<?> entityData = class_24873.method_10562("entity");
                if (entityData.isPresent()) {
                    String nestedName = this.Il1lI((class_2487)entityData.get());
                    if (nestedName != null) return nestedName;
                }
            }
            String directName = this.Il1lI(class_24873);
            if (directName != null) return directName;
        }
        return null;
    }

    private String Il1lI(class_2487 class_24872) {
        Optional<String> optional;
        if (class_24872 != null && class_24872.method_10545("id") && (optional = class_24872.method_10558("id")).isPresent() && !optional.get().isEmpty()) {
            String string = optional.get();
            class_1299 class_12992 = (class_1299)class_7923.field_41177.method_63535(class_2960.method_60654((String)string));
            return class_12992 != null ? class_12992.method_5897().getString() : null;
        }
        return null;
    }

    @EventHandler
    private void o0O0o(Render3DEvent render3DEvent) {
        if (this.mc.field_1687 != null && !this.map17.isEmpty() && this.mc.field_1724 != null) {
            SettingColor settingColor = (SettingColor)this.setting05.get();
            this.field20.set(settingColor.r, settingColor.g, settingColor.b, ((Integer)this.setting06.get()).intValue());
            SettingColor settingColor2 = (SettingColor)this.setting08.get();
            this.field21.set(settingColor2.r, settingColor2.g, settingColor2.b, ((Integer)this.setting09.get()).intValue());
            Color color = new Color(settingColor2.r, settingColor2.g, settingColor2.b, 255);
            double d = this.mc.field_1687.method_31607();
            double d2 = d + (double)this.mc.field_1687.method_31605();
            for (Map.Entry<class_2338, String> entry : this.map17.entrySet()) {
                class_2338 class_23382 = entry.getKey();
                double d3 = class_23382.method_10263();
                double d4 = class_23382.method_10264();
                double d5 = class_23382.method_10260();
                render3DEvent.renderer.box(d3 - 1.0, d, d5 - 1.0, d3 + 2.0, d2, d5 + 2.0, this.field20, this.field20, (ShapeMode)this.setting11.get(), 0);
                if (!((Boolean)this.setting07.get()).booleanValue()) continue;
                render3DEvent.renderer.box(d3, d4, d5, d3 + 1.0, d4 + 1.0, d5 + 1.0, this.field21, color, (ShapeMode)this.setting11.get(), 0);
            }
        }
    }

    @EventHandler
    private void o0O0o(Render2DEvent render2DEvent) {
        if (this.mc.field_1687 != null && !this.map17.isEmpty()) {
            SettingColor settingColor = (SettingColor)this.setting14.get();
            this.field22.set(settingColor.r, settingColor.g, settingColor.b, 255);
            for (Map.Entry<class_2338, String> entry : this.map17.entrySet()) {
                class_2338 class_23382 = entry.getKey();
                Vector3d vector3d = new Vector3d((double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264() + 1.35, (double)class_23382.method_10260() + 0.5);
                this.o0O0o(vector3d, entry.getValue(), class_23382);
            }
        }
    }

    private void o0O0o(Vector3d vector3d, String string, class_2338 class_23382) {
        if (NametagUtils.to2D((Vector3d)vector3d, (double)((Double)this.setting10.get()))) {
            NametagUtils.begin((Vector3d)vector3d);
            TextRenderer textRenderer = TextRenderer.get();
            textRenderer.begin(1.0, false, true);
            String string2 = "SPAWNER";
            int n = this.mc.field_1724 != null ? (int)Math.sqrt(this.mc.field_1724.method_5649((double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264() + 0.5, (double)class_23382.method_10260() + 0.5)) : 0;
            String string3 = n + "m";
            double d = textRenderer.getWidth(string);
            double d2 = textRenderer.getWidth(" ");
            double d3 = textRenderer.getWidth(string2);
            double d4 = textRenderer.getWidth(" \u2022 ");
            double d5 = textRenderer.getWidth(string3);
            double d6 = d + d2 + d3 + d4 + d5;
            double d7 = textRenderer.getHeight();
            double d8 = 6.0;
            double d9 = 3.0;
            double d10 = d6 + d8 * 2.0;
            double d11 = d7 + d9 * 2.0;
            double d12 = -d10 / 2.0;
            double d13 = -d9;
            Renderer2D.COLOR.begin();
            Renderer2D.COLOR.quad(d12, d13, d10, d11, this.field23);
            Renderer2D.COLOR.quad(d12, d13, 2.0, d11, this.field22);
            Renderer2D.COLOR.render();
            double d14 = -d6 / 2.0;
            textRenderer.render(string, d14, 0.0, this.field24);
            textRenderer.render(string2, d14 += d + d2, 0.0, this.field22);
            textRenderer.render(string3, d14 += d3 + d4, 0.0, this.field25);
            textRenderer.end();
            NametagUtils.end();
        }
    }

    public String getInfoString() {
        return Integer.toString(this.map17.size());
    }
}
