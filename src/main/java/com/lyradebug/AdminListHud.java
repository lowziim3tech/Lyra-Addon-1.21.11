package com.lyradebug;

import com.lyradebug.AdminTextureHelper;
import com.lyradebug.RoundedRectRenderer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.settings.Setting;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_408;
import org.lwjgl.glfw.GLFW;

public final class AdminListHud {
    private static final int constant01 = 168;
    private static final int constant02 = 22;
    private static final int constant03 = 20;
    private static final int constant04 = 8;
    private static final int constant05 = 16;
    private static final int constant06 = 10;
    private static final long constant07 = 350L;
    private static final long constant08 = 300L;
    private final List<AdminEntry> list09 = new ArrayList<AdminEntry>();
    private boolean flag10;
    private double value11;
    private double value12;

    public void o0O0o(Set<String> set, Set<String> set2, Set<String> set3, boolean bl) {
        long l = System.currentTimeMillis();
        if (bl) {
            this.list09.clear();
            for (String string : set) {
                this.list09.add(new AdminEntry(string, false));
            }
            this.list09.sort(Comparator.comparing(adminEntry -> AdminTextureHelper.xXxXx(adminEntry.field01).toLowerCase(Locale.ROOT)));
        } else {
            AdminEntry adminEntry2;
            for (String object : set2) {
                adminEntry2 = this.o0O0o(object);
                if (adminEntry2 == null) {
                    this.list09.add(new AdminEntry(object, true));
                    continue;
                }
                adminEntry2.flag03 = false;
                adminEntry2.flag02 = true;
                adminEntry2.counter04 = l;
            }
            for (String string : set3) {
                adminEntry2 = this.o0O0o(string);
                if (adminEntry2 == null) continue;
                adminEntry2.flag03 = true;
                adminEntry2.flag02 = false;
                adminEntry2.counter04 = l;
            }
            for (AdminEntry adminEntry3 : this.list09) {
                if (set.contains(adminEntry3.field01) && !set3.contains(adminEntry3.field01)) {
                    adminEntry3.flag03 = false;
                    continue;
                }
                if (set.contains(adminEntry3.field01) || adminEntry3.flag03) continue;
                adminEntry3.flag03 = true;
                adminEntry3.flag02 = false;
                adminEntry3.counter04 = l;
            }
            this.list09.sort(Comparator.comparing(adminEntry -> AdminTextureHelper.xXxXx(adminEntry.field01).toLowerCase(Locale.ROOT)));
            this.o0O0o(l);
        }
    }

    public void I1Il1() {
        this.list09.clear();
        this.flag10 = false;
    }

    public void o0O0o(Setting<Integer> setting, Setting<Integer> setting2, Setting<Integer> setting3) {
        class_310 class_3102 = MeteorClient.mc;
        if (class_3102.method_22683() != null && class_3102.field_1755 instanceof class_408) {
            boolean bl;
            float f = AdminListHud.o0O0o(setting3);
            int n = class_3102.method_22683().method_4486();
            int n2 = class_3102.method_22683().method_4502();
            int n3 = AdminListHud.Il1lI(168, f);
            int n4 = AdminListHud.Il1lI(22, f);
            int n5 = this.o0O0o(setting, n, f);
            int n6 = this.Il1lI(setting2, n2, f);
            double d = class_3102.method_22683().method_4495();
            double d2 = class_3102.field_1729.method_1603() / d;
            double d3 = class_3102.field_1729.method_1604() / d;
            boolean bl2 = GLFW.glfwGetMouseButton((long)class_3102.method_22683().method_4490(), (int)0) == 1;
            boolean bl3 = bl = d2 >= (double)n5 && d2 < (double)(n5 + n3) && d3 >= (double)n6 && d3 < (double)(n6 + n4);
            if (bl2 && bl && !this.flag10) {
                this.flag10 = true;
                this.value11 = d2 - (double)n5;
                this.value12 = d3 - (double)n6;
            }
            if (!bl2) {
                this.flag10 = false;
            } else if (this.flag10) {
                int n7 = AdminListHud.Il1lI(this.o0OO0(), f);
                int n8 = (int)Math.round(Math.max(0.0, Math.min(d2 - this.value11, (double)(n - n3))));
                int n9 = (int)Math.round(Math.max(0.0, Math.min(d3 - this.value12, (double)(n2 - n7))));
                if ((Integer)setting.get() != n8) {
                    setting.set(n8);
                }
                if ((Integer)setting2.get() != n9) {
                    setting2.set(n9);
                }
            }
        } else {
            this.flag10 = false;
        }
    }

    public void o0O0o(class_332 class_3322, Setting<Integer> setting, Setting<Integer> setting2, Setting<Integer> setting3) {
        class_310 class_3102 = MeteorClient.mc;
        if (class_3102 != null && class_3102.method_22683() != null && class_3102.field_1772 != null) {
            try {
                float f = AdminListHud.o0O0o(setting3);
                int n = class_3102.method_22683().method_4486();
                int n2 = class_3102.method_22683().method_4502();
                int n3 = this.o0O0o(setting, n, f);
                int n4 = this.Il1lI(setting2, n2, f);
                int n5 = AdminListHud.Il1lI(168, f);
                int n6 = AdminListHud.Il1lI(this.o0OO0(), f);
                int n7 = AdminListHud.Il1lI(8, f);
                int n8 = Math.max(8, AdminListHud.Il1lI(16, f));
                int n9 = AdminListHud.Il1lI(22, f);
                int n10 = AdminListHud.Il1lI(20, f);
                int n11 = Math.max(3, AdminListHud.Il1lI(10, f));
                RoundedRectRenderer.o0O0o(class_3322, n3 + 2, n4 + 2, n5, n6, n11, 0x55000000);
                RoundedRectRenderer.o0O0o(class_3322, n3, n4, n5, n6, n11, -401995250);
                long l = System.currentTimeMillis();
                this.o0O0o(l);
                AdminListHud.o0O0o(class_3322, "Online Admins", n3 + n7, n4 + AdminListHud.Il1lI(7, f), -1, f);
                int n12 = n4 + n9;
                boolean bl = false;
                for (AdminEntry adminEntry : this.list09) {
                    float f2;
                    if (!this.methodCollision02(adminEntry, l) || (f2 = this.methodCollision01(adminEntry, l)) <= 0.01f && adminEntry.flag03) continue;
                    bl = true;
                    int n13 = Math.max(0, Math.min(255, (int)(f2 * 255.0f)));
                    int n14 = adminEntry.flag03 ? 0 : (int)((1.0f - AdminListHud.xXxXx(f2)) * 8.0f * f);
                    int n15 = adminEntry.flag03 ? (int)((1.0f - f2) * -6.0f * f) : 0;
                    int n16 = n3 + n7 + n14;
                    int n17 = n12 + n15;
                    AdminTextureHelper.o0O0o(class_3322, adminEntry.field01, n16, n17 + AdminListHud.Il1lI(2, f), n8);
                    AdminListHud.o0O0o(class_3322, AdminTextureHelper.xXxXx(adminEntry.field01), n16 + n8 + AdminListHud.Il1lI(6, f), n17 + AdminListHud.Il1lI(5, f), AdminListHud.Il1lI(-855304, n13), f);
                    n12 += (int)((float)n10 * f2);
                }
                if (!bl) {
                    AdminListHud.o0O0o(class_3322, "None online", n3 + n7, n4 + AdminListHud.Il1lI(28, f), -6250326, f);
                }
            }
            catch (Throwable throwable) {
                System.err.println("[AdminListHud] Render error: " + String.valueOf(throwable));
            }
        }
    }

    private static void o0O0o(class_332 class_3322, String string, int n, int n2, int n3, float f) {
        class_310 class_3102 = MeteorClient.mc;
        if (class_3102 != null && class_3102.field_1772 != null && string != null && !string.isEmpty()) {
            float f2 = 1.05f * f;
            class_3322.method_51448().pushMatrix();
            class_3322.method_51448().translate((float)n, (float)n2);
            class_3322.method_51448().scale(f2, f2);
            class_3322.method_51433(class_3102.field_1772, string, 0, 0, n3, true);
            class_3322.method_51448().popMatrix();
        }
    }

    private static int Il1lI(int n, int n2) {
        return Math.min(n2, n >>> 24 & 0xFF) << 24 | n & 0xFFFFFF;
    }

    private int o0OO0() {
        int n = 0;
        long l = System.currentTimeMillis();
        for (AdminEntry adminEntry : this.list09) {
            if (!this.methodCollision02(adminEntry, l)) continue;
            ++n;
        }
        return n == 0 ? 50 : 22 + n * 20 + 8;
    }

    private int o0O0o(Setting<Integer> setting, int n, float f) {
        int n2 = (Integer)setting.get();
        int n3 = AdminListHud.Il1lI(168, f);
        return n2 >= 0 && n2 <= n - 20 ? n2 : Math.max(0, n - n3 - 10);
    }

    private int Il1lI(Setting<Integer> setting, int n, float f) {
        int n2 = (Integer)setting.get();
        int n3 = AdminListHud.Il1lI(this.o0OO0(), f);
        return n2 >= 0 && n2 <= n - 20 ? Math.min(n2, Math.max(0, n - n3)) : 10;
    }

    private static float o0O0o(Setting<Integer> setting) {
        return (float)((Integer)setting.get()).intValue() / 100.0f;
    }

    private static int Il1lI(int n, float f) {
        return Math.max(1, Math.round((float)n * f));
    }

    private AdminEntry o0O0o(String string) {
        for (AdminEntry adminEntry : this.list09) {
            if (!adminEntry.field01.equals(string)) continue;
            return adminEntry;
        }
        return null;
    }

    private void o0O0o(long l) {
        Iterator<AdminEntry> iterator = this.list09.iterator();
        while (iterator.hasNext()) {
            AdminEntry adminEntry = iterator.next();
            if (!adminEntry.flag03 || !(this.methodCollision01(adminEntry, l) <= 0.01f)) continue;
            iterator.remove();
        }
    }

    private boolean methodCollision02(AdminEntry adminEntry, long l) {
        return !adminEntry.flag03 || this.methodCollision01(adminEntry, l) > 0.01f;
    }

    private float methodCollision01(AdminEntry adminEntry, long l) {
        long l2 = l - adminEntry.counter04;
        if (adminEntry.flag03) {
            return 1.0f - AdminListHud.xXxXx(Math.min(1.0f, (float)l2 / 300.0f));
        }
        return adminEntry.flag02 ? AdminListHud.xXxXx(Math.min(1.0f, (float)l2 / 350.0f)) : 1.0f;
    }

    private static float xXxXx(float f) {
        float f2 = 1.0f - f;
        return 1.0f - f2 * f2 * f2;
    }

    static final class AdminEntry {
        final String field01;
        boolean flag02;
        boolean flag03;
        long counter04;

        AdminEntry(String string, boolean bl) {
            this.field01 = string;
            this.flag02 = bl;
            this.counter04 = System.currentTimeMillis();
        }
    }
}

