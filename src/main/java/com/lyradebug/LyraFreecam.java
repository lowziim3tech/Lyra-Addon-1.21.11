package com.lyradebug;

import com.lyradebug.LyraDebug;
import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
import meteordevelopment.meteorclient.events.meteor.KeyEvent;
import meteordevelopment.meteorclient.events.meteor.MouseClickEvent;
import meteordevelopment.meteorclient.events.meteor.MouseScrollEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.FreeLook;
import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1041;
import net.minecraft.class_243;
import net.minecraft.class_3532;
import net.minecraft.class_3675;
import net.minecraft.class_5498;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class LyraFreecam
extends Module {
    private final SettingGroup group01;
    private final Setting<Double> setting02;
    private final Setting<Boolean> setting03;
    private final Setting<Boolean> setting04;
    private final Setting<Boolean> setting05;
    public final Vector3d field06;
    public final Vector3d field07;
    public float value08;
    public float value09;
    public float value10;
    public float value11;
    private class_5498 field12;
    private double value13;
    private final Vector3d field14;
    private static final double constant15 = 0.35;
    private static final double constant16 = 0.88;
    private boolean flag17;
    private boolean flag18;
    private boolean flag19;
    private boolean flag20;
    private boolean flag21;
    private boolean flag22;
    private boolean flag23;
    private boolean flag24;
    private boolean flag25;
    private boolean flag26;
    private boolean flag27;
    private boolean flag28;
    private boolean flag29;

    public LyraFreecam() {
        super(LyraDebug.CATEGORY, "lyra-freecam", "Lets your camera move freely without actually moving.");
        this.group01 = this.settings.getDefaultGroup();
        this.setting02 = this.group01.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("speed")).description("Base movement speed.")).defaultValue(1.0).min(1.0).max(10.0).sliderRange(1.0, 10.0).onChanged(d -> {
            this.value13 = d;
        })).build());
        this.setting03 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("smooth")).description("Smooth camera movement with momentum.")).defaultValue(false)).build());
        this.setting04 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("continue-walking")).description("Keeps holding movement keys when entering freecam.")).defaultValue(true)).build());
        this.setting05 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("keep-culling-off")).description("Do not enable cave culling after disabling freecam.")).defaultValue(false)).build());
        this.field06 = new Vector3d();
        this.field07 = new Vector3d();
        this.field14 = new Vector3d();
    }

    public void onActivate() {
        if (this.mc.field_1724 == null) {
            this.toggle();
            return;
        }
        FreeLook freeLook = (FreeLook)Modules.get().get(FreeLook.class);
        if (freeLook != null && freeLook.isActive()) {
            freeLook.toggle();
        }
        this.mc.field_1690.method_42454().method_41748(0.0);
        this.mc.field_1690.method_42453().method_41748(0.0);
        this.value08 = this.mc.field_1724.method_36454();
        this.value09 = this.mc.field_1724.method_36455();
        this.field12 = this.mc.field_1690.method_31044();
        this.value13 = (Double)this.setting02.get();
        this.field14.zero();
        class_243 class_2432 = this.mc.field_1724.method_33571();
        this.field06.set(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
        this.field07.set(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350);
        if (this.mc.field_1690.method_31044() == class_5498.field_26666) {
            this.value08 += 180.0f;
            this.value09 *= -1.0f;
        }
        this.value10 = this.value08;
        this.value11 = this.value09;
        if (((Boolean)this.setting04.get()).booleanValue()) {
            this.flag23 = this.mc.field_1690.field_1894.method_1434();
            this.flag24 = this.mc.field_1690.field_1881.method_1434();
            this.flag25 = this.mc.field_1690.field_1849.method_1434();
            this.flag26 = this.mc.field_1690.field_1913.method_1434();
            this.flag27 = this.mc.field_1690.field_1903.method_1434();
            this.flag28 = this.mc.field_1690.field_1832.method_1434();
        }
        this.flag17 = this.mc.field_1690.field_1894.method_1434();
        this.flag18 = this.mc.field_1690.field_1881.method_1434();
        this.flag19 = this.mc.field_1690.field_1849.method_1434();
        this.flag20 = this.mc.field_1690.field_1913.method_1434();
        this.flag21 = this.mc.field_1690.field_1903.method_1434();
        this.flag22 = this.mc.field_1690.field_1832.method_1434();
        if (!((Boolean)this.setting04.get()).booleanValue()) {
            this.ll1II();
        }
        if (!this.II1ll() && this.mc.field_1769 != null) {
            this.mc.field_1769.method_3279();
        }
    }

    public void onDeactivate() {
        if (this.mc.field_1724 != null) {
            this.flag28 = false;
            this.flag27 = false;
            this.flag26 = false;
            this.flag25 = false;
            this.flag24 = false;
            this.flag23 = false;
            this.ll1II();
            this.field07.set((Vector3dc)this.field06);
            this.value10 = this.value08;
            this.value11 = this.value09;
            this.flag29 = true;
        }
    }

    public static boolean li1Il() {
        if (Modules.get() == null) {
            return false;
        }
        LyraFreecam lyraFreecam = (LyraFreecam)Modules.get().get(LyraFreecam.class);
        return lyraFreecam != null && (lyraFreecam.isActive() || lyraFreecam.II1ll());
    }

    private boolean II1ll() {
        return this.flag29 && (Boolean)this.setting05.get() != false;
    }

    private void ll1II() {
        if (this.mc.field_1690 == null) {
            return;
        }
        this.mc.field_1690.field_1894.method_23481(false);
        this.mc.field_1690.field_1881.method_23481(false);
        this.mc.field_1690.field_1849.method_23481(false);
        this.mc.field_1690.field_1913.method_23481(false);
        this.mc.field_1690.field_1903.method_23481(false);
        this.mc.field_1690.field_1832.method_23481(false);
    }

    @EventHandler
    private void o0O0o(OpenScreenEvent openScreenEvent) {
        this.ll1II();
        this.field07.set((Vector3dc)this.field06);
        this.value10 = this.value08;
        this.value11 = this.value09;
    }

    @EventHandler
    private void o0O0o(TickEvent.Post post) {
        if (this.mc.method_1560() != null) {
            if (this.field12 != null && !this.field12.method_31034()) {
                this.mc.field_1690.method_31043(class_5498.field_26664);
            }
            if (((Boolean)this.setting04.get()).booleanValue()) {
                if (this.flag23) {
                    this.mc.field_1690.field_1894.method_23481(true);
                }
                if (this.flag24) {
                    this.mc.field_1690.field_1881.method_23481(true);
                }
                if (this.flag25) {
                    this.mc.field_1690.field_1849.method_23481(true);
                }
                if (this.flag26) {
                    this.mc.field_1690.field_1913.method_23481(true);
                }
                if (this.flag27) {
                    this.mc.field_1690.field_1903.method_23481(true);
                }
                if (this.flag28) {
                    this.mc.field_1690.field_1832.method_23481(true);
                }
            }
            class_243 class_2432 = class_243.method_1030((float)0.0f, (float)this.value08);
            class_243 class_2433 = class_243.method_1030((float)0.0f, (float)(this.value08 + 90.0f));
            double d = 0.0;
            double d2 = 0.0;
            double d3 = 0.0;
            double d4 = this.mc.field_1690.field_1867.method_1434() ? 1.0 : 0.5;
            boolean bl = false;
            if (this.flag17) {
                d += class_2432.field_1352 * d4 * this.value13;
                d3 += class_2432.field_1350 * d4 * this.value13;
                bl = true;
            }
            if (this.flag18) {
                d -= class_2432.field_1352 * d4 * this.value13;
                d3 -= class_2432.field_1350 * d4 * this.value13;
                bl = true;
            }
            boolean bl2 = false;
            if (this.flag19) {
                d += class_2433.field_1352 * d4 * this.value13;
                d3 += class_2433.field_1350 * d4 * this.value13;
                bl2 = true;
            }
            if (this.flag20) {
                d -= class_2433.field_1352 * d4 * this.value13;
                d3 -= class_2433.field_1350 * d4 * this.value13;
                bl2 = true;
            }
            if (bl && bl2) {
                double d5 = 1.0 / Math.sqrt(2.0);
                d *= d5;
                d3 *= d5;
            }
            if (this.flag21) {
                d2 += d4 * this.value13;
            }
            if (this.flag22) {
                d2 -= d4 * this.value13;
            }
            if (((Boolean)this.setting03.get()).booleanValue()) {
                boolean bl3;
                boolean bl4 = bl3 = this.flag17 || this.flag18 || this.flag19 || this.flag20 || this.flag21 || this.flag22;
                if (bl3) {
                    this.field14.x += (d - this.field14.x) * 0.35;
                    this.field14.y += (d2 - this.field14.y) * 0.35;
                    this.field14.z += (d3 - this.field14.z) * 0.35;
                } else {
                    this.field14.mul(0.88);
                    if (this.field14.lengthSquared() < 1.0E-6) {
                        this.field14.zero();
                    }
                }
                d = this.field14.x;
                d2 = this.field14.y;
                d3 = this.field14.z;
            } else {
                this.field14.set(d, d2, d3);
            }
            this.field07.set((Vector3dc)this.field06);
            this.field06.set(this.field06.x + d, this.field06.y + d2, this.field06.z + d3);
        }
    }

    @EventHandler
    private void o0O0o(KeyEvent keyEvent) {
        if (this.mc.method_22683() != null && !class_3675.method_15987((class_1041)this.mc.method_22683(), (int)292)) {
            boolean bl;
            boolean bl2 = true;
            boolean bl3 = bl = keyEvent.action != KeyAction.Release;
            if (this.mc.field_1690.field_1894.method_1417(keyEvent.input)) {
                this.flag17 = bl;
                this.mc.field_1690.field_1894.method_23481(false);
            } else if (this.mc.field_1690.field_1881.method_1417(keyEvent.input)) {
                this.flag18 = bl;
                this.mc.field_1690.field_1881.method_23481(false);
            } else if (this.mc.field_1690.field_1849.method_1417(keyEvent.input)) {
                this.flag19 = bl;
                this.mc.field_1690.field_1849.method_23481(false);
            } else if (this.mc.field_1690.field_1913.method_1417(keyEvent.input)) {
                this.flag20 = bl;
                this.mc.field_1690.field_1913.method_23481(false);
            } else if (this.mc.field_1690.field_1903.method_1417(keyEvent.input)) {
                this.flag21 = bl;
                this.mc.field_1690.field_1903.method_23481(false);
            } else if (this.mc.field_1690.field_1832.method_1417(keyEvent.input)) {
                this.flag22 = bl;
                this.mc.field_1690.field_1832.method_23481(false);
            } else {
                bl2 = false;
            }
            if (bl2) {
                keyEvent.cancel();
            }
        }
    }

    @EventHandler
    private void o0O0o(MouseClickEvent mouseClickEvent) {
        boolean bl;
        boolean bl2 = true;
        boolean bl3 = bl = mouseClickEvent.action != KeyAction.Release;
        if (this.mc.field_1690.field_1894.method_1433(mouseClickEvent.click)) {
            this.flag17 = bl;
            this.mc.field_1690.field_1894.method_23481(false);
        } else if (this.mc.field_1690.field_1881.method_1433(mouseClickEvent.click)) {
            this.flag18 = bl;
            this.mc.field_1690.field_1881.method_23481(false);
        } else if (this.mc.field_1690.field_1849.method_1433(mouseClickEvent.click)) {
            this.flag19 = bl;
            this.mc.field_1690.field_1849.method_23481(false);
        } else if (this.mc.field_1690.field_1913.method_1433(mouseClickEvent.click)) {
            this.flag20 = bl;
            this.mc.field_1690.field_1913.method_23481(false);
        } else if (this.mc.field_1690.field_1903.method_1433(mouseClickEvent.click)) {
            this.flag21 = bl;
            this.mc.field_1690.field_1903.method_23481(false);
        } else if (this.mc.field_1690.field_1832.method_1433(mouseClickEvent.click)) {
            this.flag22 = bl;
            this.mc.field_1690.field_1832.method_23481(false);
        } else {
            bl2 = false;
        }
        if (bl2) {
            mouseClickEvent.cancel();
        }
    }

    @EventHandler
    private void o0O0o(MouseScrollEvent mouseScrollEvent) {
        if (this.mc.field_1755 == null) {
            this.value13 += mouseScrollEvent.value * 0.25 * this.value13;
            if (this.value13 < 0.1) {
                this.value13 = 0.1;
            }
            mouseScrollEvent.cancel();
        }
    }

    public void o0O0o(double d, double d2) {
        this.value10 = this.value08;
        this.value11 = this.value09;
        this.value08 = (float)((double)this.value08 + d);
        this.value09 = (float)((double)this.value09 + d2);
        this.value09 = class_3532.method_15363((float)this.value09, (float)-90.0f, (float)90.0f);
    }

    public double o0O0o(float f) {
        return class_3532.method_16436((double)f, (double)this.field07.x, (double)this.field06.x);
    }

    public double Il1lI(float f) {
        return class_3532.method_16436((double)f, (double)this.field07.y, (double)this.field06.y);
    }

    public double xXxXx(float f) {
        return class_3532.method_16436((double)f, (double)this.field07.z, (double)this.field06.z);
    }

    public double qQqQq(float f) {
        return class_3532.method_16439((float)f, (float)this.value10, (float)this.value08);
    }

    public double zZzZz(float f) {
        return class_3532.method_16439((float)f, (float)this.value11, (float)this.value09);
    }
}

