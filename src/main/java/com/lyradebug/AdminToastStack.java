package com.lyradebug;

import com.lyradebug.AdminTextureHelper;
import com.lyradebug.RoundedRectRenderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_3414;
import net.minecraft.class_3417;

public final class AdminToastStack {
    private static final int constant01 = 210;
    private static final int constant02 = 40;
    private static final int constant03 = 2;
    private static final int constant04 = 6;
    private static final int constant05 = 10;
    private static final long constant06 = 5500L;
    private static final long constant07 = 220L;
    private static final int constant08 = -267777520;
    private static final int constant09 = -12723084;
    private static final int constant10 = -42422;
    private static final int constant11 = -5197636;
    private static final int constant12 = -16119284;
    private final List<ToastEntry> list13 = new ArrayList<ToastEntry>();

    public void o0O0o(String string, boolean bl, boolean bl2) {
        this.list13.add(0, new ToastEntry(string, bl, bl2));
        while (this.list13.size() > 6) {
            this.list13.remove(this.list13.size() - 1);
        }
    }

    public void I1Il1() {
        this.list13.clear();
    }

    public void o0O0o(class_332 class_3322) {
        class_310 class_3102 = MeteorClient.mc;
        if (!this.list13.isEmpty() && class_3102 != null && class_3102.method_22683() != null && class_3102.field_1772 != null) {
            try {
                long l = System.currentTimeMillis();
                Iterator<ToastEntry> iterator = this.list13.iterator();
                while (iterator.hasNext()) {
                    ToastEntry toastEntry = iterator.next();
                    if (l - toastEntry.counter04 < 5500L) continue;
                    iterator.remove();
                }
                if (this.list13.isEmpty()) {
                    return;
                }
                int n = 6;
                for (ToastEntry toastEntry : this.list13) {
                    float f;
                    long l2 = l - toastEntry.counter04;
                    float f2 = Math.min(1.0f, (float)l2 / 5500.0f);
                    float f3 = AdminToastStack.xXxXx(Math.min(1.0f, (float)l2 / 220.0f));
                    float f4 = Math.min(1.0f, (float)l2 / 140.0f);
                    float f5 = f4 * (f = l2 > 5100L ? 1.0f - (float)(l2 - 5100L) / 400.0f : 1.0f);
                    int n2 = Math.max(0, Math.min(255, (int)(f5 * 255.0f)));
                    if (n2 <= 4) {
                        n += 42;
                        continue;
                    }
                    if (!toastEntry.flag05 && toastEntry.flag03) {
                        if (class_3102.field_1724 != null) {
                            class_3102.field_1724.method_5783(toastEntry.flag02 ? (class_3414)class_3417.field_14622.comp_349() : (class_3414)class_3417.field_14624.comp_349(), toastEntry.flag02 ? 0.5f : 0.4f, toastEntry.flag02 ? 1.12f : 0.92f);
                        }
                        toastEntry.flag05 = true;
                    }
                    int n3 = (int)((1.0f - f3) * 12.0f);
                    int n4 = 6 + n3;
                    int n5 = toastEntry.flag02 ? -12723084 : -42422;
                    RoundedRectRenderer.o0O0o(class_3322, n4, n, 210, 40, 10, AdminToastStack.Il1lI(-267777520, n2));
                    class_3322.method_25294(n4 + 1, n + 10, n4 + 3, n + 40 - 10, AdminToastStack.Il1lI(n5, n2));
                    AdminTextureHelper.o0O0o(class_3322, toastEntry.field01, n4 + 8, n + 8, 24);
                    String string = AdminTextureHelper.xXxXx(toastEntry.field01);
                    String string2 = (string + (toastEntry.flag02 ? " JOINED" : " LEFT")).toUpperCase();
                    String string3 = toastEntry.flag02 ? "Online in Tab" : "Left the Game";
                    AdminToastStack.o0O0o(class_3322, string2, n4 + 36, n + 9, AdminToastStack.Il1lI(n5, n2), 0.95f);
                    AdminToastStack.o0O0o(class_3322, string3, n4 + 36, n + 22, AdminToastStack.Il1lI(-5197636, Math.min(n2, 220)), 0.85f);
                    int n6 = n4 + 6;
                    int n7 = n4 + 210 - 6;
                    int n8 = Math.max(0, (int)((float)(n7 - n6) * (1.0f - f2)));
                    class_3322.method_25294(n6, n + 40 - 3, n7, n + 40 - 1, AdminToastStack.Il1lI(-16119284, n2));
                    if (n8 > 0) {
                        class_3322.method_25294(n6, n + 40 - 3, n6 + n8, n + 40 - 1, AdminToastStack.Il1lI(n5, n2));
                    }
                    n += 42;
                }
            }
            catch (Throwable throwable) {
                System.err.println("[AdminToastStack] Render error: " + String.valueOf(throwable));
            }
        }
    }

    private static void o0O0o(class_332 class_3322, String string, int n, int n2, int n3, float f) {
        class_310 class_3102 = MeteorClient.mc;
        if (class_3102 != null && class_3102.field_1772 != null) {
            class_3322.method_51448().pushMatrix();
            class_3322.method_51448().translate((float)n, (float)n2);
            class_3322.method_51448().scale(f, f);
            class_3322.method_51433(class_3102.field_1772, string, 0, 0, n3, true);
            class_3322.method_51448().popMatrix();
        }
    }

    private static int Il1lI(int n, int n2) {
        int n3 = n >>> 24 & 0xFF;
        int n4 = Math.min(n2, n3 == 0 ? 255 : n3);
        return n4 << 24 | n & 0xFFFFFF;
    }

    private static float xXxXx(float f) {
        float f2 = 1.0f - f;
        return 1.0f - f2 * f2 * f2;
    }

    static final class ToastEntry {
        final String field01;
        final boolean flag02;
        final boolean flag03;
        final long counter04 = System.currentTimeMillis();
        boolean flag05;

        ToastEntry(String string, boolean bl, boolean bl2) {
            this.field01 = string;
            this.flag02 = bl;
            this.flag03 = bl2;
        }
    }
}

