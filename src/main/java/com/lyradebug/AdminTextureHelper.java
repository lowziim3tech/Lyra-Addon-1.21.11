package com.lyradebug;

import java.util.Locale;
import java.util.Map;
import net.minecraft.class_10799;
import net.minecraft.class_2960;
import net.minecraft.class_332;

public final class AdminTextureHelper {
    public static final Map<String, String> constant01 = Map.of("archivepedro", "archivePedro", "frwost", "Frwost", "w1zox_", "W1zoX_", "fluffymaster07", "Fluffymaster07", "bautiedgar", "bautiedgar", "showered", "Showered", "pastagamer08", "PastaGamer08", "itszdeath", "Itszdeath", "0gsummer", "0Gsummer");
    private static final Map<String, class_2960> constant02 = Map.of("archivepedro", AdminTextureHelper.o0O0o("archivepedro"), "frwost", AdminTextureHelper.o0O0o("frwost"), "w1zox_", AdminTextureHelper.o0O0o("w1zox_"), "fluffymaster07", AdminTextureHelper.o0O0o("fluffymaster07"), "bautiedgar", AdminTextureHelper.o0O0o("bautiedgar"), "showered", AdminTextureHelper.o0O0o("showered"), "pastagamer08", AdminTextureHelper.o0O0o("pastagamer08"), "itszdeath", AdminTextureHelper.o0O0o("itszdeath"), "0gsummer", AdminTextureHelper.o0O0o("0gsummer"));

    private AdminTextureHelper() {
    }

    private static class_2960 o0O0o(String string) {
        return class_2960.method_60655((String)"lyradebug", (String)("textures/admin/" + string + ".png"));
    }

    public static String Il1lI(String string) {
        return string == null ? "" : string.toLowerCase(Locale.ROOT);
    }

    public static String xXxXx(String string) {
        String string2 = AdminTextureHelper.Il1lI(string);
        return constant01.getOrDefault(string2, string == null ? "" : string);
    }

    public static void o0O0o(class_332 class_3322, String string, int n, int n2, int n3) {
        String string2 = AdminTextureHelper.Il1lI(string);
        class_2960 class_29602 = constant02.get(string2);
        float f = (float)n3 / 16.0f;
        class_3322.method_51448().pushMatrix();
        class_3322.method_51448().translate((float)n, (float)n2);
        class_3322.method_51448().scale(f, f);
        if (class_29602 != null) {
            class_3322.method_25290(class_10799.field_56883, class_29602, 0, 0, 0.0f, 0.0f, 16, 16, 16, 16);
        } else {
            class_3322.method_25294(0, 0, 16, 16, -14013910);
        }
        class_3322.method_51448().popMatrix();
    }
}

