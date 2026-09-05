package com.lyradebug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class NativeLoader {
    private static boolean loaded = false;
public static synchronized boolean load() {
        if (loaded) {
            return true;
        }
        String string = System.getProperty("os.name").toLowerCase();
        if (!string.contains("win")) {
            return false;
        }
        String string2 = "/assets/lyradebug/natives/lyraguard.dll";
        try (InputStream inputStream = NativeLoader.class.getResourceAsStream(string2);){
            if (inputStream == null) {
                boolean bl2 = false;
                return bl2;
            }
            File file = File.createTempFile("lyraguard_", ".dll");
            file.deleteOnExit();
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);){
                int n;
                byte[] byArray = new byte[8192];
                while ((n = inputStream.read(byArray)) != -1) {
                    fileOutputStream.write(byArray, 0, n);
                }
            }
            System.load(file.getAbsolutePath());
            loaded = true;
            boolean bl = true;
            return bl;
        }
        catch (Throwable throwable3) {
            return false;
        }
    }

    public static boolean isLoaded() {
        return loaded;
    }
}

