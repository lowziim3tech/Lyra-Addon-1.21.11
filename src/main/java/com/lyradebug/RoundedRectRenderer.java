package com.lyradebug;

import net.minecraft.class_332;

public final class RoundedRectRenderer {
    private RoundedRectRenderer() {
    }

    public static void o0O0o(class_332 class_3322, int n, int n2, int n3, int n4, int n5, int n6) {
        if (n5 <= 0) {
            class_3322.method_25294(n, n2, n + n3, n2 + n4, n6);
            return;
        }
        int n7 = Math.min(n5, Math.min(n3 / 2, n4 / 2));
        class_3322.method_25294(n + n7, n2, n + n3 - n7, n2 + n4, n6);
        class_3322.method_25294(n, n2 + n7, n + n7, n2 + n4 - n7, n6);
        class_3322.method_25294(n + n3 - n7, n2 + n7, n + n3, n2 + n4 - n7, n6);
        for (int i = 0; i < n7; ++i) {
            int n8 = (int)Math.round(Math.sqrt(n7 * n7 - (n7 - i - 1) * (n7 - i - 1)));
            class_3322.method_25294(n + n7 - n8, n2 + i, n + n7, n2 + i + 1, n6);
            class_3322.method_25294(n + n3 - n7, n2 + i, n + n3 - n7 + n8, n2 + i + 1, n6);
            class_3322.method_25294(n + n7 - n8, n2 + n4 - i - 1, n + n7, n2 + n4 - i, n6);
            class_3322.method_25294(n + n3 - n7, n2 + n4 - i - 1, n + n3 - n7 + n8, n2 + n4 - i, n6);
        }
    }
}

