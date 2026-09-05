package com.lyradebug;

import com.lyradebug.AdminDetector;
import com.lyradebug.AutoRtpHome;
import com.lyradebug.BedrockEsp;
import com.lyradebug.ClickBetter;
import com.lyradebug.HoleEsp;
import com.lyradebug.LyraFreecam;
import com.lyradebug.PearlTrajectory;
import com.lyradebug.SpawnerNotifier;
import com.lyradebug.StringCipher;
import com.lyradebug.SusChunkFinder;
import com.lyradebug.SwingSpeed;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LyraDebug
extends MeteorAddon {
    public static final Logger LOGGER = LoggerFactory.getLogger((String)"larfmsi");
    public static Category CATEGORY;

    public static boolean isLicensed() {
        return true;
    }

    public void onRegisterCategories() {
        CATEGORY = new Category("LyraDebug");
        Modules.registerCategory((Category)CATEGORY);
    }

    public void onInitialize() {
        registerModules();
    }

    public static void registerModules() {
        Module[] moduleArray;
        if (Modules.get() == null) {
            return;
        }
        for (Module module : moduleArray = new Module[]{new SusChunkFinder(), new BedrockEsp(), new AdminDetector(), new HoleEsp(), new ClickBetter(), new AutoRtpHome(), new SpawnerNotifier(), new SwingSpeed(), new PearlTrajectory(), new LyraFreecam()}) {
            if (Modules.get().get(module.getClass()) != null) continue;
            Modules.get().add(module);
        }
    }

    public static void disableModules() {
        if (Modules.get() == null || CATEGORY == null) {
            return;
        }
        for (Module module : Modules.get().getGroup(CATEGORY)) {
            if (!module.isActive()) continue;
            module.toggle();
        }
    }

    public String getPackage() {
        return "com.lyradebug";
    }
}
