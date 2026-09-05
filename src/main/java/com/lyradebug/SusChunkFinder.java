package com.lyradebug;

import com.lyradebug.LyraDebug;
import com.lyradebug.StringCipher;
import com.lyradebug.license.NativeGuard;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import meteordevelopment.meteorclient.events.game.GameJoinedEvent;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render2DEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.ChunkDataEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.renderer.text.TextRenderer;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.NametagUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1923;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2596;
import net.minecraft.class_2666;
import net.minecraft.class_2680;
import net.minecraft.class_2741;
import net.minecraft.class_2769;
import net.minecraft.class_2818;
import net.minecraft.class_2826;
import net.minecraft.class_2960;
import net.minecraft.class_7923;
import org.joml.Vector3d;

public class SusChunkFinder
extends Module {
    private final SettingGroup group01;
    private final Setting<Integer> setting02;
    private final Setting<Integer> setting03;
    private final Setting<Boolean> setting04;
    private final Setting<Boolean> setting05;
    private final Setting<Boolean> setting06;
    private final Setting<Boolean> setting07;
    private final Setting<Boolean> setting08;
    private static final Color constant09 = new Color(255, 130, 165, 255);
    private static final Color constant10 = new Color(255, 180, 200, 255);
    private static final Color constant11 = new Color(255, 255, 255, 255);
    private static final int constant12 = 0;
    private static final int constant13 = 5;
    private static final Map<class_2248, Set<class_2248>> constant14 = new HashMap<class_2248, Set<class_2248>>();
    private final Map<class_1923, Integer> map15;
    private final Map<class_1923, Integer> map16;
    private final Map<class_1923, Integer> map17;
    private final Map<class_1923, Boolean> map18;
    private final Map<class_1923, Set<class_2338>> map19;
    private final Set<class_2338> set20;
    private final Set<class_1923> set21;
    private final Set<Long> set22;
    private final ChunkKey field23;
    private ExecutorService field24;

    private static class_2248 o0O0o(String string) {
        return (class_2248)class_7923.field_41175.method_63535(class_2960.method_60656((String)StringCipher.decode(string)));
    }

    public SusChunkFinder() {
        super(LyraDebug.CATEGORY, "lyra-sus-chunk-finder", "Finds Suspicious Chunks , And Detect Base LOL");
        this.group01 = this.settings.getDefaultGroup();
        this.map15 = new ConcurrentHashMap<class_1923, Integer>();
        this.map16 = new ConcurrentHashMap<class_1923, Integer>();
        this.map17 = new ConcurrentHashMap<class_1923, Integer>();
        this.map18 = new ConcurrentHashMap<class_1923, Boolean>();
        this.map19 = new ConcurrentHashMap<class_1923, Set<class_2338>>();
        this.set20 = ConcurrentHashMap.newKeySet();
        this.set21 = ConcurrentHashMap.newKeySet();
        this.set22 = ConcurrentHashMap.newKeySet();
        this.field23 = new ChunkKey();
        this.setting02 = this.group01.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("spread-radius")).description("Radius to spread tick checks.")).defaultValue(4)).min(2).max(16).sliderRange(2, 16).build());
        this.setting03 = this.group01.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("sensitivity")).description("Sensitivity threshold for heat score.")).defaultValue(3)).min(1).max(20).sliderRange(1, 20).build());
        this.setting04 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("numbers")).description("Show score number labels over suspicious chunks.")).defaultValue(true)).build());
        this.setting05 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("auto-adjust")).description("Automatically adjust sensitivity threshold.")).defaultValue(false)).build());
        this.setting06 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("amethyst")).description("Check for modified amethyst clusters.")).defaultValue(true)).build());
        this.setting07 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("bee-nest")).description("Check for modified bee nests.")).defaultValue(true)).build());
        this.setting08 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("rotated-deepslate")).description("Check for player-rotated deepslate blocks.")).defaultValue(true)).build());
    }

    public void onActivate() {
        this.lIIlI();
        this.field24 = Executors.newSingleThreadExecutor(runnable -> {
            Thread thread = new Thread(runnable, "SusChunkFinder-Scan");
            thread.setDaemon(true);
            return thread;
        });
        if (this.mc.field_1687 != null && this.mc.field_1724 != null) {
            int n = (Integer)this.mc.field_1690.method_42503().method_41753();
            class_1923 class_19232 = this.mc.field_1724.method_31476();
            for (int i = class_19232.field_9181 - n; i <= class_19232.field_9181 + n; ++i) {
                for (int j = class_19232.field_9180 - n; j <= class_19232.field_9180 + n; ++j) {
                    if (!this.mc.field_1687.method_8393(i, j)) continue;
                    this.zZzZz(this.mc.field_1687.method_8497(i, j));
                }
            }
        }
    }

    public void onDeactivate() {
        if (this.field24 != null) {
            this.field24.shutdownNow();
        }
        this.lIIlI();
    }

    private void lIIlI() {
        this.map15.clear();
        this.map16.clear();
        this.map17.clear();
        this.map18.clear();
        this.map19.clear();
        this.set20.clear();
        this.set21.clear();
        this.set22.clear();
        this.field23.I1Il1();
    }

    @EventHandler
    private void o0O0o(GameJoinedEvent gameJoinedEvent) {
        this.lIIlI();
    }

    @EventHandler
    private void o0O0o(ChunkDataEvent chunkDataEvent) {
        this.zZzZz(chunkDataEvent.chunk());
    }

    @EventHandler
    private void Il1lI(PacketEvent.Receive receive) {
        class_2596 packet = receive.packet;
        if (packet instanceof class_2666) {
            class_2666 unloadPacket = (class_2666)packet;
            class_1923 chunkPos = unloadPacket.comp_1726();
            if (this.field24 != null && !this.field24.isShutdown()) {
                this.field24.submit(() -> this.zZzZz(chunkPos));
            } else {
                this.methodCollision01(chunkPos);
            }
        }
    }

    private void methodCollision01(class_1923 class_19233) {
        int n3 = (Integer)this.setting02.get();
        Integer n4 = this.map17.remove(class_19233);
        Boolean bl = this.map18.remove(class_19233);
        Set<class_2338> set = this.map19.remove(class_19233);
        this.set21.remove(class_19233);
        this.field23.methodCollision04(class_19233.field_9181, class_19233.field_9180);
        this.set22.remove(class_19233.method_8324());
        if (n4 != null && n4 > 0) {
            for (int i = -n3; i <= n3; ++i) {
                for (int j = -n3; j <= n3; ++j) {
                    if (Math.abs(i) + Math.abs(j) > n3) continue;
                    class_1923 class_19234 = new class_1923(class_19233.field_9181 + i, class_19233.field_9180 + j);
                    this.map15.computeIfPresent(class_19234, (class_19232, n2) -> n2 - n4 <= 0 ? null : Integer.valueOf(n2 - n4));
                }
            }
        }
        if (Boolean.TRUE.equals(bl)) {
            this.map16.computeIfPresent(class_19233, (class_19232, n) -> n - 1 <= 0 ? null : Integer.valueOf(n - 1));
        }
        if (set != null) {
            this.set20.removeAll(set);
        }
    }

    private void zZzZz(class_2818 class_28182) {
        if (this.field24 != null && !this.field24.isShutdown() && class_28182 != null) {
            class_1923 class_19232 = class_28182.method_12004();
            this.field24.submit(() -> {
                try {
                    if (this.mc.field_1687 == null) {
                        return;
                    }
                    this.methodCollision04(class_19232, class_28182);
                    this.set21.add(class_19232);
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        }
    }

    private void methodCollision04(class_1923 class_19233, class_2818 class_28182) {
        int n;
        int n3;
        int n4;
        int n5 = (Integer)this.setting02.get();
        int n6 = this.map17.getOrDefault(class_19233, 0);
        boolean bl = Boolean.TRUE.equals(this.map18.get(class_19233));
        Set<class_2338> set = this.map19.remove(class_19233);
        if (n6 > 0) {
            for (n4 = -n5; n4 <= n5; ++n4) {
                for (n3 = -n5; n3 <= n5; ++n3) {
                    this.map15.compute(new class_1923(class_19233.field_9181 + n4, class_19233.field_9180 + n3), (class_19232, n2) -> {
                        int updatedHeat = (n2 == null ? 0 : n2) - n6;
                        return updatedHeat <= 0 ? null : Integer.valueOf(updatedHeat);
                    });
                }
            }
        }
        if (bl) {
            for (n4 = -n5; n4 <= n5; ++n4) {
                for (n3 = -n5; n3 <= n5; ++n3) {
                    this.qQqQq(new class_1923(class_19233.field_9181 + n4, class_19233.field_9180 + n3));
                }
            }
        }
        if (set != null && !set.isEmpty()) {
            this.set20.removeAll(set);
        }
        ScanResult scanResult = this.methodCollision03(class_19233, class_28182);
        this.map17.put(class_19233, scanResult.O0OO0());
        this.map18.put(class_19233, scanResult.lIIlI());
        if (scanResult.lIIlI()) {
            for (n3 = -n5; n3 <= n5; ++n3) {
                for (n = -n5; n <= n5; ++n) {
                    this.xXxXx(new class_1923(class_19233.field_9181 + n3, class_19233.field_9180 + n));
                }
            }
        }
        if (scanResult.O0OO0() > 0) {
            for (n3 = -n5; n3 <= n5; ++n3) {
                for (n = -n5; n <= n5; ++n) {
                    this.map15.merge(new class_1923(class_19233.field_9181 + n3, class_19233.field_9180 + n), scanResult.O0OO0(), Integer::sum);
                }
            }
        }
        if (!scanResult.l1Il1().isEmpty()) {
            this.map19.put(class_19233, scanResult.l1Il1());
            this.set20.addAll(scanResult.l1Il1());
        }
        this.Il1lI(class_19233, class_28182);
    }

    private void Il1lI(class_1923 class_19232, class_2818 class_28182) {
        int[][] nArray = SusChunkFinder.o0O0o(class_28182);
        long l = class_19232.method_8324();
        for (long l2 : this.field23.methodCollision01(class_19232.field_9181, class_19232.field_9180, nArray)) {
            class_2248 class_22482 = (class_2248)class_7923.field_41175.method_10200(ChunkKey.o0O0o(l2));
            class_2248 class_22483 = (class_2248)class_7923.field_41175.method_10200(ChunkKey.Il1lI(l2));
            if (!constant14.getOrDefault(class_22482, Set.of()).contains(class_22483)) continue;
            this.set22.add(l);
            this.Il1lI(class_19232);
            break;
        }
        this.field23.methodCollision02(class_19232.field_9181, class_19232.field_9180, nArray);
    }

    private void Il1lI(class_1923 class_19232) {
        int n = Math.max(3, (Integer)this.setting03.get());
        this.map15.merge(class_19232, n, Integer::sum);
    }

    private static int[][] o0O0o(class_2818 class_28182) {
        class_2826[] class_2826Array = class_28182.method_12006();
        int[][] nArrayArray = new int[class_2826Array.length][];
        for (int i = 0; i <= 5 && i < class_2826Array.length; ++i) {
            class_2826 class_28262 = class_2826Array[i];
            if (class_28262 == null || class_28262.method_38292()) continue;
            int[] nArray = new int[4096];
            int n = 0;
            for (int j = 0; j < 16; ++j) {
                for (int k = 0; k < 16; ++k) {
                    for (int i2 = 0; i2 < 16; ++i2) {
                        nArray[n++] = class_7923.field_41175.method_10206(class_28262.method_12254(i2, j, k).method_26204());
                    }
                }
            }
            nArrayArray[i] = nArray;
        }
        return nArrayArray;
    }

    private void xXxXx(class_1923 class_19232) {
        this.map16.merge(class_19232, 1, Integer::sum);
    }

    private void qQqQq(class_1923 class_19233) {
        this.map16.compute(class_19233, (class_19232, n) -> {
            if (n == null) {
                return null;
            }
            int n2 = n - 1;
            return n2 <= 0 ? null : Integer.valueOf(n2);
        });
    }

    private boolean methodCollision02(class_1923 class_19232) {
        return this.map16.containsKey(class_19232);
    }

    private ScanResult methodCollision03(class_1923 class_19232, class_2818 class_28182) {
        int n;
        int n2 = 35866;
        class_2826[] class_2826Array = null;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        ConcurrentHashMap.KeySetView keySetView = ConcurrentHashMap.newKeySet();
        int n10 = 0;
        block7: while (n2 != 0) {
            switch (n2) {
                case 35866: {
                    class_2826Array = class_28182.method_12006();
                    n3 = class_28182.method_31607();
                    n4 = class_19232.method_8326();
                    n5 = class_19232.method_8328();
                    bl = (Boolean)this.setting06.get() != false && this.IlIIl();
                    n10 = 0;
                    n2 = 16171;
                    continue block7;
                }
                case 57005: {
                    n6 ^= 0x5A;
                    n2 = 16171;
                    continue block7;
                }
                case 16171: {
                    if (class_2826Array != null && n10 < class_2826Array.length) {
                        n2 = 23966;
                        continue block7;
                    }
                    n2 = 37105;
                    continue block7;
                }
                case 23966: {
                    class_2826 class_28262 = class_2826Array[n10];
                    if (class_28262 != null && !class_28262.method_38292()) {
                        n = n3 + (n10 << 4);
                        if (bl) {
                            Predicate<class_2680> predicate;
                            if (!bl2 && class_28262.method_19523(predicate = class_26802 -> class_26802.method_27852(SusChunkFinder.o0O0o("PxQTDU1mQCEcBglYSlUTGwcF")) || class_26802.method_27852(SusChunkFinder.o0O0o("IRwWCFRUfi0UFxVJQFI4JhAURQ==")) || class_26802.method_27852(SusChunkFinder.o0O0o("IBgABkRmQCEcBglYSlUTGwcF")))) {
                                bl2 = true;
                            }
                            if (!bl3 && class_28262.method_19523(class_26802 -> class_26802.method_27852(SusChunkFinder.o0O0o("LRQXFUlAUjgmEQ1USlUpCw==")))) {
                                bl3 = true;
                            }
                        }
                        for (int i = 0; i < 16; ++i) {
                            for (int j = 0; j < 16; ++j) {
                                for (int k = 0; k < 16; ++k) {
                                    class_2350 class_23502;
                                    class_2680 class_26803 = class_28262.method_12254(i, j, k);
                                    int n11 = n4 + i;
                                    int n12 = n + j;
                                    int n13 = n5 + k;
                                    if (((Boolean)this.setting06.get()).booleanValue() && !bl) {
                                        if (class_26803.method_27852(SusChunkFinder.o0O0o("LRQXFUlAUjgmEQ1USlUpCw=="))) {
                                            ++n6;
                                        } else if ((class_26803.method_27852(SusChunkFinder.o0O0o("PxQTDU1mQCEcBglYSlUTGwcF")) || class_26803.method_27852(SusChunkFinder.o0O0o("IRwWCFRUfi0UFxVJQFI4JhAURQ==")) || class_26803.method_27852(SusChunkFinder.o0O0o("IBgABkRmQCEcBglYSlUTGwcF"))) && class_26803.method_28498((class_2769)class_2741.field_12525)) {
                                            class_23502 = (class_2350)class_26803.method_11654((class_2769)class_2741.field_12525);
                                            class_2338 class_23382 = new class_2338(n11, n12, n13).method_10093(class_23502.method_10153());
                                            if (this.mc.field_1687 != null && this.mc.field_1687.method_8320(class_23382).method_27852(SusChunkFinder.o0O0o("LgwWBUhXRhMYHwRVUVg/DQ=="))) {
                                                ++n7;
                                            } else {
                                                ++n6;
                                            }
                                        }
                                    }
                                    if (((Boolean)this.setting07.get()).booleanValue() && class_26803.method_27852(SusChunkFinder.o0O0o("LhwXPk9cUjg=")) && class_26803.method_28498((class_2769)class_2741.field_20432)) {
                                        if ((Integer)class_26803.method_11654((class_2769)class_2741.field_20432) == 5) {
                                            ++n8;
                                        } else {
                                            ++n9;
                                        }
                                    }
                                    if (!((Boolean)this.setting08.get()).booleanValue() || !class_26803.method_27852(SusChunkFinder.o0O0o("KBwXEVJVQDgc")) || !class_26803.method_28498((class_2769)class_2741.field_12496) || class_26803.method_11654((class_2769)class_2741.field_12496) == class_2350.class_2351.field_11052 || n12 < 0 || n12 > 60) continue;
                                    class_2338 suspiciousBlockPos = new class_2338(n11, n12, n13);
                                    boolean bl4 = true;
                                    if (this.mc.field_1687 != null) {
                                        for (class_2350 class_23503 : class_2350.values()) {
                                            if (!this.mc.field_1687.method_8320(suspiciousBlockPos.method_10093(class_23503)).method_26215()) continue;
                                            bl4 = false;
                                            break;
                                        }
                                    }
                                    if (!bl4) continue;
                                    keySetView.add(suspiciousBlockPos);
                                }
                            }
                        }
                    }
                    ++n10;
                    n2 = 16171;
                    continue block7;
                }
                case 37105: {
                    if (bl) {
                        if (bl2) {
                            ++n7;
                        } else if (bl3) {
                            ++n6;
                        }
                    }
                    n2 = 0;
                    continue block7;
                }
            }
            n2 = 0;
        }
        int n14 = ((Boolean)this.setting06.get() != false ? n7 : 0) + ((Boolean)this.setting07.get() != false ? n9 : 0);
        n = ((Boolean)this.setting06.get() != false ? n6 : 0) + ((Boolean)this.setting07.get() != false ? n8 : 0);
        return new ScanResult(n, n14 > 0, keySetView);
    }

    private Set<class_1923> qQqQq() {
        if (this.mc.field_1687 == null) {
            return Set.of();
        }
        int n = (Integer)this.setting03.get();
        HashMap<class_1923, Integer> hashMap = new HashMap<class_1923, Integer>();
        for (Map.Entry<class_1923, Integer> object2 : this.map15.entrySet()) {
            class_1923 class_19232 = object2.getKey();
            if (object2.getValue() < n || this.methodCollision02(class_19232) || !this.mc.field_1687.method_8393(class_19232.field_9181, class_19232.field_9180)) continue;
            int n2 = 0;
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    if (i == 0 && j == 0 || !this.set21.contains(new class_1923(class_19232.field_9181 + i, class_19232.field_9180 + j))) continue;
                    ++n2;
                }
            }
            if (n2 < 3) continue;
            hashMap.put(class_19232, object2.getValue());
        }
        if (!((Boolean)this.setting05.get()).booleanValue()) {
            return hashMap.keySet();
        }
        HashSet hashSet = new HashSet();
        HashSet<class_1923> hashSet2 = new HashSet<class_1923>();
        for (class_1923 class_19233 : hashMap.keySet()) {
            if (!hashSet.add(class_19233)) continue;
            ArrayList<class_1923> arrayList = new ArrayList<class_1923>();
            ArrayDeque<class_1923> arrayDeque = new ArrayDeque<class_1923>();
            arrayDeque.add(class_19233);
            while (!arrayDeque.isEmpty()) {
                class_1923 class_19234 = (class_1923)arrayDeque.poll();
                arrayList.add(class_19234);
                for (class_1923 class_19235 : new class_1923[]{new class_1923(class_19234.field_9181 + 1, class_19234.field_9180), new class_1923(class_19234.field_9181 - 1, class_19234.field_9180), new class_1923(class_19234.field_9181, class_19234.field_9180 + 1), new class_1923(class_19234.field_9181, class_19234.field_9180 - 1)}) {
                    if (!hashMap.containsKey(class_19235) || !hashSet.add(class_19235)) continue;
                    arrayDeque.add(class_19235);
                }
            }
            int class_19234 = (Integer)hashMap.get(arrayList.get(0));
            for (class_1923 class_19237 : arrayList) {
                class_19234 = Math.max(class_19234, (Integer)hashMap.get(class_19237));
            }
            for (class_1923 class_19236 : arrayList) {
                if ((Integer)hashMap.get(class_19236) != class_19234) continue;
                hashSet2.add(class_19236);
            }
        }
        return hashSet2;
    }

    private Set<class_1923> zZzZz() {
        Set<class_1923> set = this.qQqQq();
        if (set.isEmpty() || this.mc.field_1687 == null) {
            return Set.of();
        }
        HashSet<class_1923> hashSet = new HashSet<class_1923>();
        int n = 1;
        int n2 = 0;
        for (class_1923 class_19232 : set) {
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    class_1923 class_19233 = new class_1923(class_19232.field_9181 + i + n, class_19232.field_9180 + j + n2);
                    if (!this.mc.field_1687.method_8393(class_19233.field_9181, class_19233.field_9180)) continue;
                    hashSet.add(class_19233);
                }
            }
        }
        return hashSet;
    }

    @EventHandler
    private void Il1lI(Render3DEvent render3DEvent) {
        if (this.mc.field_1687 != null) {
            Color color = new Color(255, 192, 203, 80);
            Set<class_1923> set = this.zZzZz();
            for (class_1923 object : set) {
                int n = 0;
                if (set.contains(new class_1923(object.field_9181, object.field_9180 - 1))) {
                    n |= 8;
                }
                if (set.contains(new class_1923(object.field_9181, object.field_9180 + 1))) {
                    n |= 0x10;
                }
                if (set.contains(new class_1923(object.field_9181 - 1, object.field_9180))) {
                    n |= 0x20;
                }
                if (set.contains(new class_1923(object.field_9181 + 1, object.field_9180))) {
                    n |= 0x40;
                }
                double d = object.method_8326();
                double d2 = object.method_8328();
                double d3 = d + 16.0;
                double d4 = d2 + 16.0;
                double d5 = 63.0;
                render3DEvent.renderer.boxSides(d, d5, d2, d3, d5 + 0.1, d4, color, n | 8 | 0x10 | 0x20 | 0x40);
                render3DEvent.renderer.boxSides(d, d5, d2, d3, d5 + 0.1, d4, constant10, n | 2 | 4);
                render3DEvent.renderer.boxLines(d, d5, d2, d3, d5 + 0.1, d4, constant09, n);
                render3DEvent.renderer.boxLines(d + 0.12, d5, d2 + 0.12, d3 - 0.12, d5 + 0.1, d4 - 0.12, constant09, n);
            }
            if (!this.set20.isEmpty()) {
                Color color2 = new Color(0, 255, 255, 80);
                this.set20.removeIf(class_23382 -> this.mc.field_1687 == null || !this.mc.field_1687.method_8393(class_23382.method_10263() >> 4, class_23382.method_10260() >> 4));
                for (class_2338 class_23383 : this.set20) {
                    render3DEvent.renderer.box((double)class_23383.method_10263(), (double)class_23383.method_10264(), (double)class_23383.method_10260(), (double)(class_23383.method_10263() + 1), (double)(class_23383.method_10264() + 1), (double)(class_23383.method_10260() + 1), color2, color2, ShapeMode.Sides, 0);
                }
            }
        }
    }

    @EventHandler
    private void o0O0o(Render2DEvent render2DEvent) {
        if (((Boolean)this.setting04.get()).booleanValue() && this.mc.field_1687 != null && this.mc.field_1724 != null) {
            TextRenderer textRenderer = TextRenderer.get();
            for (class_1923 class_19232 : this.zZzZz()) {
                Vector3d vector3d;
                Integer n = this.map15.get(class_19232);
                if (n == null || !NametagUtils.to2D((Vector3d)(vector3d = new Vector3d((double)(class_19232.method_8326() + 8), 64.5, (double)(class_19232.method_8328() + 8))), (double)2.0, (boolean)true)) continue;
                String string = String.valueOf(n);
                NametagUtils.begin((Vector3d)vector3d);
                textRenderer.beginBig();
                textRenderer.render(string, -textRenderer.getWidth(string, true) / 2.0, -textRenderer.getHeight(true) / 2.0, constant11, true);
                textRenderer.end();
                NametagUtils.end();
            }
        }
    }

    private boolean IlIIl() {
        if (this.mc.method_1562() == null) {
            return false;
        }
        String string = this.mc.method_1562().method_52790();
        return string != null && string.contains("DonutFolia");
    }

    private
void zZzZz(class_1923 class_19232) {
        this.methodCollision01(class_19232);
    }

    static {
        constant14.put(SusChunkFinder.o0O0o("KhgADE1YTyg="), Set.of(SusChunkFinder.o0O0o("KBAAFQ=="), SusChunkFinder.o0O0o("KBAAFX5JQDgR")));
        constant14.put(SusChunkFinder.o0O0o("OxEXAFU="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LxgAE05NUg=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("PBYGAFVWRD8="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LhwXFVNWTjgK"), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("IhwGCURLfjsYABU="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("Pw4XBFVmQykLABh+W1Q/EQ=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("PwwVAFNmQi0XFw=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LhgfA05W"), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LxgRFVRK"), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("JxweEQ=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("JxweEX5JTS0XBg=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("IxgZPk1cQDocAQ=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("PwkAFEJcfiAcExdESg=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LhAAAklmTSkYBARS"), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("JgwcBk1cfiAcExdESg=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LRoTAkhYfiAcExdESg=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("KBgACn5WQCcmHgRAT0Q/"), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("IRgcBlNWVykmHgRAT0Q/"), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LxEXE1NAfiAcExdESg=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("LQMTDURYfiAcExdESg=="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
        constant14.put(SusChunkFinder.o0O0o("KhUdFkRLSCIeLQBbWE0pGC0NRFhXKQo="), Set.of(SusChunkFinder.o0O0o("LRAA"), SusChunkFinder.o0O0o("LxgEBH5YSD4=")));
    }

    static class ChunkKey {
        private final Map<Long, int[][]> map01 = new ConcurrentHashMap<Long, int[][]>();

        ChunkKey() {
        }

        public void methodCollision02(int n, int n2, int[][] nArray) {
            this.map01.put(class_1923.method_8331((int)n, (int)n2), nArray);
        }

        public void methodCollision04(int n, int n2) {
            this.map01.remove(class_1923.method_8331((int)n, (int)n2));
        }

        public void I1Il1() {
            this.map01.clear();
        }

        public static long methodCollision03(int n, int n2) {
            return (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
        }

        public static int o0O0o(long l) {
            return (int)(l >>> 32);
        }

        public static int Il1lI(long l) {
            return (int)(l & 0xFFFFFFFFL);
        }

        private static boolean o0O0o(int[][] nArray) {
            if (nArray == null) {
                return true;
            }
            for (int[] nArray2 : nArray) {
                if (nArray2 == null) continue;
                return false;
            }
            return true;
        }

        public List<Long> methodCollision01(int n, int n2, int[][] nArray) {
            int[][] nArray2 = this.map01.get(class_1923.method_8331((int)n, (int)n2));
            if (nArray2 != null && !ChunkKey.o0O0o(nArray2)) {
                if (NativeGuard.Il1lI()) {
                    try {
                        long[] nativeDiff = NativeGuard.diffSnapshotsNative(nArray, nArray2, 0, 5);
                        if (nativeDiff != null) {
                            ArrayList<Long> arrayList = new ArrayList<Long>(nativeDiff.length);
                            for (long packedChange : nativeDiff) {
                                arrayList.add(packedChange);
                            }
                            return arrayList;
                        }
                    }
                    catch (Throwable throwable) {
                    }
                }
                List<Long> changes = new ArrayList<Long>();
                for (int i = 0; i <= 5; ++i) {
                    int[] nArray3;
                    int[] nArray4 = i < nArray2.length ? nArray2[i] : null;
                    int[] nArray5 = nArray3 = i < nArray.length ? nArray[i] : null;
                    if (nArray4 == null && nArray3 == null) continue;
                    if (nArray4 != null && nArray3 != null) {
                        if (Arrays.equals(nArray4, nArray3)) continue;
                        for (int j = 0; j < nArray4.length; ++j) {
                            if (nArray4[j] == nArray3[j]) continue;
                            changes.add(ChunkKey.methodCollision03(nArray4[j], nArray3[j]));
                        }
                        continue;
                    }
                    int[] nArray6 = nArray4 != null ? nArray4 : nArray3;
                    for (int j = 0; j < nArray6.length; ++j) {
                        int n3;
                        int n4 = nArray4 != null ? nArray4[j] : 0;
                        int n5 = n3 = nArray3 != null ? nArray3[j] : 0;
                        if (n4 == n3) continue;
                        changes.add(ChunkKey.methodCollision03(n4, n3));
                    }
                }
                return changes;
            }
            return List.of();
        }
    }

    static final class ScanResult {
        private final int counter01;
        private final boolean flag02;
        private final Set<class_2338> set03;

        ScanResult(int n, boolean bl, Set<class_2338> set) {
            this.counter01 = n;
            this.flag02 = bl;
            this.set03 = set;
        }

        public final String O0o0O() {
            return this.toString();
        }

        public final int I11lI() {
            return this.hashCode();
        }

        public final boolean Il1lI(Object object) {
            return this.equals(object);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof ScanResult other)) return false;
            return this.counter01 == other.counter01 && this.flag02 == other.flag02
                && Objects.equals(this.set03, other.set03);
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.counter01, this.flag02, this.set03);
        }

        @Override
        public String toString() {
            return "ScanResult[selfHeat=" + this.counter01 + ", hasUngrown=" + this.flag02
                + ", rotated=" + this.set03 + "]";
        }

        public int O0OO0() {
            return this.counter01;
        }

        public boolean lIIlI() {
            return this.flag02;
        }

        public Set<class_2338> l1Il1() {
            return this.set03;
        }
    }
}
