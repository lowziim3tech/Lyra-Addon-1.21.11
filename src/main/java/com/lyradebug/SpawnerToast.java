package com.lyradebug;

import meteordevelopment.meteorclient.MeteorClient;
import net.minecraft.class_10799;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2960;
import net.minecraft.class_327;
import net.minecraft.class_332;
import net.minecraft.class_3417;
import net.minecraft.class_3532;
import net.minecraft.class_368;
import net.minecraft.class_374;

public final class SpawnerToast
implements class_368 {
    private static final class_2960 constant01 = class_2960.method_60656((String)"toast/advancement");
    private static final class_1799 constant02 = class_1802.field_8849.method_7854();
    private static final int constant03 = 160;
    private static final int constant04 = 32;
    private final String field05;
    private final String field06;
    private final String field07;
    private final boolean flag08;
    private final long counter09;
    private final int counter10;
    private long counter11 = -1L;
    private boolean flag12;
    private class_368.class_369 field13 = class_368.class_369.field_2209;

    public SpawnerToast(String string, int n, int n2, int n3, long l, boolean bl, int n4) {
        int n5;
        this.counter09 = Math.max(2500L, l);
        this.field05 = string == null || string.isEmpty() ? "Spawner" : string;
        this.flag08 = bl;
        this.counter10 = 0xFF000000 | n4 & 0xFFFFFF;
        int n6 = n5 = MeteorClient.mc.field_1724 != null ? MeteorClient.mc.field_1724.method_31478() : n2;
        String string2 = n2 < n5 ? "below" : (n2 > n5 ? "above" : "same level");
        this.field06 = "Spawner " + string2;
        this.field07 = "X: " + n + " Y: " + n2 + " Z: " + n3;
    }

    public int oOO0o() {
        return 160;
    }

    public int OOo0O() {
        return 32;
    }

    public class_368.class_369 o0O0o() {
        return this.field13;
    }

    public void o0O0o(class_374 class_3742, long l) {
        if (this.counter11 == -1L) {
            this.counter11 = l;
        }
        class_368.class_369 class_3692 = this.field13 = l - this.counter11 >= this.counter09 ? class_368.class_369.field_2209 : class_368.class_369.field_2210;
        if (!this.flag12 && this.flag08 && MeteorClient.mc.field_1724 != null) {
            MeteorClient.mc.field_1724.method_5783(class_3417.field_15195, 1.0f, 1.0f);
            this.flag12 = true;
        }
    }

    public void o0O0o(class_332 class_3322, class_327 class_3272, long l) {
        if (this.counter11 >= 0L) {
            long l2 = l - this.counter11;
            float f = 1.0f - class_3532.method_15363((float)((float)l2 / (float)this.counter09), (float)0.0f, (float)1.0f);
            class_3322.method_52706(class_10799.field_56883, constant01, 0, 0, 160, 32);
            class_3322.method_25294(0, 0, 160, 32, -535423466);
            class_3322.method_25294(0, 0, 2, 32, this.counter10);
            class_3322.method_51427(constant02, 5, 8);
            int n = 127;
            String string = class_3272.method_27523(this.field05 + " Spawner", n);
            String string2 = this.o0O0o(class_3272, n);
            class_3322.method_51433(class_3272, string, 30, 7, -1, true);
            class_3322.method_51433(class_3272, string2, 30, 18, -4675352, false);
            int n2 = 5;
            int n3 = 157;
            int n4 = n3 - n2;
            int n5 = Math.max(0, (int)((float)n4 * f));
            class_3322.method_25294(n2, 30, n3, 31, -14540254);
            if (n5 > 0) {
                class_3322.method_25294(n2, 30, n2 + n5, 31, this.counter10);
            }
        }
    }

    private String o0O0o(class_327 class_3272, int n) {
        String string = this.field06 + " \u2022 " + this.field07;
        return class_3272.method_1727(string) <= n ? string : this.field07;
    }
}

