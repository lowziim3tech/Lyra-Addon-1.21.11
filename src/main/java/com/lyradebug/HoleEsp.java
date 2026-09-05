package com.lyradebug;

import com.lyradebug.LyraDebug;
import com.lyradebug.StringCipher;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.renderer.Renderer3D;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.Utils;
import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1923;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_238;
import net.minecraft.class_2680;
import net.minecraft.class_2791;
import net.minecraft.class_2826;
import net.minecraft.class_2902;
import net.minecraft.class_638;

public class HoleEsp
extends Module {
    private final SettingGroup group01;
    private final SettingGroup group02;
    private final Setting<Boolean> setting03;
    private final Setting<Boolean> setting04;
    private final Setting<Integer> setting05;
    private static final SettingColor constant06 = new SettingColor(255, 20, 147, 63);
    private static final SettingColor constant07 = new SettingColor(255, 0, 0, 63);
    private static final SettingColor constant08 = new SettingColor(0, 0, 0, 0);
    private final Long2ObjectMap<ChunkHoles> map09;
    private final Deque<class_2791> queue10;
    private final Set<Long> set11;
    private final Map<Long, Map<class_2338, HoleType>> map12;
    private volatile boolean flag13;
    private final AtomicBoolean atomic14;
    private final AtomicBoolean atomic15;
    private long counter16;
    private final Set<class_238> set17;
    private final Set<class_238> set18;

    public HoleEsp() {
        super(LyraDebug.CATEGORY, "hole-esp", "Highlights 1x1 and 3x3 holes in terrain.");
        this.group01 = this.settings.getDefaultGroup();
        this.group02 = this.settings.createGroup("Hole Params");
        this.setting03 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("detect-1x1")).description("Detect 1x1 holes.")).defaultValue(true)).build());
        this.setting04 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("detect-3x3")).description("Detect 3x3 holes.")).defaultValue(true)).build());
        this.setting05 = this.group02.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("min-hole-depth")).description("Minimum depth of holes to detect.")).defaultValue(4)).min(1).sliderMax(15).build());
        this.map09 = new Long2ObjectOpenHashMap();
        this.queue10 = new ArrayDeque<class_2791>();
        this.set11 = new HashSet<Long>();
        this.map12 = new ConcurrentHashMap<Long, Map<class_2338, HoleType>>();
        this.flag13 = false;
        this.atomic14 = new AtomicBoolean(false);
        this.atomic15 = new AtomicBoolean(false);
        this.counter16 = 0L;
        this.set17 = Collections.newSetFromMap(new ConcurrentHashMap());
        this.set18 = Collections.newSetFromMap(new ConcurrentHashMap());
    }

    public Set<class_238> Il1lI() {
        return new HashSet<class_238>(this.set17);
    }

    public Set<class_238> xXxXx() {
        return new HashSet<class_238>(this.set18);
    }

    public void onActivate() {
        this.counter16 = System.currentTimeMillis();
    }

    public void onDeactivate() {
        this.map09.clear();
        this.queue10.clear();
        this.set11.clear();
        this.map12.clear();
        this.set17.clear();
        this.set18.clear();
        this.flag13 = false;
        this.atomic14.set(false);
        this.atomic15.set(false);
    }
@EventHandler
    private void o0O0o(TickEvent.Post post) {
        if (!this.isActive() || this.mc.field_1724 == null) {
            return;
        }
        Long2ObjectMap<ChunkHoles> long2ObjectMap = this.map09;
        synchronized (long2ObjectMap) {
            for (ChunkHoles chunkHoles : this.map09.values()) {
                chunkHoles.flag03 = false;
            }
            for (class_2791 chunk : Utils.chunks((boolean)true)) {
                long l = class_1923.method_8331((int)chunk.method_12004().field_9181, (int)chunk.method_12004().field_9180);
                if (this.map09.containsKey(l)) {
                    ((ChunkHoles)this.map09.get((long)l)).flag03 = true;
                    continue;
                }
                if (!this.set11.add(l)) continue;
                this.queue10.add(chunk);
            }
            this.OOo0O();
            Iterator<Map.Entry<Long, ChunkHoles>> iterator = this.map09.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Long, ChunkHoles> entry = iterator.next();
                if (entry.getValue().flag03) continue;
                this.map12.remove(entry.getKey());
                this.flag13 = true;
                iterator.remove();
            }
        }
        this.oOO0o();
        if (this.flag13) {
            this.flag13 = false;
            this.iI1li();
        }
    }

    private void oOO0o() {
        if (this.mc.field_1687 == null) {
            return;
        }
        HashSet<Long> hashSet = new HashSet<Long>();
        for (class_2791 class_27912 : Utils.chunks((boolean)true)) {
            hashSet.add(class_1923.method_8331((int)class_27912.method_12004().field_9181, (int)class_27912.method_12004().field_9180));
        }
        this.set17.removeIf(class_2382 -> {
            int n;
            int n2 = (int)Math.floor(class_2382.method_1005().method_10216()) >> 4;
            return !hashSet.contains(class_1923.method_8331((int)n2, (int)(n = (int)Math.floor(class_2382.method_1005().method_10215()) >> 4)));
        });
        this.set18.removeIf(class_2382 -> {
            int n;
            int n2 = (int)Math.floor(class_2382.method_1005().method_10216()) >> 4;
            return !hashSet.contains(class_1923.method_8331((int)n2, (int)(n = (int)Math.floor(class_2382.method_1005().method_10215()) >> 4)));
        });
    }

    private void OOo0O() {
        int n = 5;
        int n2 = 0;
        while (!this.queue10.isEmpty() && n2 < n) {
            class_2791 class_27912 = this.queue10.poll();
            if (class_27912 == null) continue;
            long l = class_1923.method_8331((int)class_27912.method_12004().field_9181, (int)class_27912.method_12004().field_9180);
            this.set11.remove(l);
            ChunkHoles chunkHoles = new ChunkHoles(this, class_27912.method_12004().field_9181, class_27912.method_12004().field_9180);
            this.map09.put(chunkHoles.o0O0o(), chunkHoles);
            MeteorExecutor.execute(() -> {
                try {
                    this.o0O0o(class_27912, chunkHoles.o0O0o());
                }
                catch (Throwable throwable) {
                }
            });
            ++n2;
        }
    }

    private void iI1li() {
        if (!this.atomic14.compareAndSet(false, true)) {
            this.atomic15.set(true);
            return;
        }
        MeteorExecutor.execute(this::li1Il);
    }

    private void li1Il() {
        try {
            this.II1ll();
        }
        catch (Throwable throwable) {
        }
        finally {
            this.atomic14.set(false);
            if (this.atomic15.compareAndSet(true, false)) {
                this.iI1li();
            }
        }
    }

    private void o0O0o(class_2791 class_27912, long l) {
        int n;
        class_638 class_6382 = this.mc.field_1687;
        if (class_6382 == null) {
            return;
        }
        class_2826[] class_2826Array = class_27912.method_12006();
        boolean bl = false;
        for (class_2826 class_28262 : class_2826Array) {
            if (class_28262 == null || class_28262.method_38292()) continue;
            bl = true;
            break;
        }
        if (!bl) {
            return;
        }
        int n2 = class_6382.method_31607();
        int n3 = class_6382.method_31600();
        int n4 = n2;
        int n5 = n3;
        int n6 = class_27912.method_12004().method_8326();
        int n7 = class_27912.method_12004().method_8328();
        class_2902 class_29022 = class_27912.method_12032(class_2902.class_2903.field_13202);
        int n8 = n4;
        for (n = 0; n < 16; ++n) {
            for (int i = 0; i < 16; ++i) {
                n8 = Math.max(n8, class_29022.method_12603(n, i));
            }
        }
        n = Math.min(n5, n8 + 20);
        HashMap<class_2338, HoleType> hashMap = new HashMap<class_2338, HoleType>();
        class_2338.class_2339 class_23392 = new class_2338.class_2339();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int n9 = n6 + i;
                int n10 = n7 + j;
                this.o0O0o(class_27912, i, j, n9, n10, n, n4, n5, hashMap, class_23392);
            }
        }
        this.map12.put(l, hashMap);
        this.flag13 = true;
    }

    private void o0O0o(class_2791 class_27912, int n, int n2, int n3, int n4, int n5, int n6, int n7, Map<class_2338, HoleType> map, class_2338.class_2339 class_23392) {
        class_638 class_6382 = this.mc.field_1687;
        if (class_6382 == null) {
            return;
        }
        int n8 = (Integer)this.setting05.get() * 14 / 4;
        int n9 = 70;
        int n10 = 3;
        int n11 = 2;
        for (int i = n5; i >= n6 + n8; --i) {
            int n12;
            int n13;
            boolean bl;
            boolean bl2;
            if (!this.o0O0o((class_1937)class_6382, class_23392, n3, i, n4)) continue;
            class_23392.method_10103(n3, i, n4);
            boolean bl3 = !class_6382.method_8320((class_2338)class_23392).method_26215();
            class_23392.method_10103(n3, i + 1, n4);
            class_2680 class_26802 = class_6382.method_8320((class_2338)class_23392);
            boolean bl4 = class_26802.method_26215();
            boolean bl5 = bl2 = !class_26802.method_26227().method_15769();
            if (bl3) {
                if (bl4 || bl2) continue;
                bl = true;
            } else {
                bl = false;
                if (bl4 && (!this.o0O0o(class_27912, (class_1937)class_6382, class_23392, n3, i, n4) || this.o0O0o(class_27912, (class_1937)class_6382, class_23392, n3, i + 1, n4))) continue;
            }
            int n14 = i;
            int n15 = 0;
            int n16 = 0;
            int n17 = 0;
            int[] nArray = new int[16];
            int n18 = 0;
            int n19 = 0;
            int n20 = 0;
            int n21 = i;
            boolean bl6 = false;
            for (int j = i; j >= n6 && i - j + 1 <= n9; --j) {
                if (this.o0O0o((class_1937)class_6382, class_23392, n3, j, n4)) {
                    if (n15 > 0) {
                        if (++n16 > n11) break;
                        n15 = 0;
                    }
                    n14 = j;
                    ++n17;
                    if (!bl6 && this.o0O0o(class_27912, class_23392, n3, j, n4)) {
                        bl6 = true;
                    }
                    n13 = 0;
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3, j, n4 - 1)) {
                        n13 |= 1;
                    }
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3, j, n4 + 1)) {
                        n13 |= 2;
                    }
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3 - 1, j, n4)) {
                        n13 |= 4;
                    }
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3 + 1, j, n4)) {
                        n13 |= 8;
                    }
                    int n22 = n13;
                    nArray[n22] = nArray[n22] + 1;
                    if (Integer.bitCount(n13) >= 3) {
                        ++n20;
                        n21 = j;
                    }
                    if (n13 == 15) {
                        ++n18;
                    }
                    n12 = 0;
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3, j, n4 - 2)) {
                        ++n12;
                    }
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3, j, n4 + 2)) {
                        ++n12;
                    }
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3 - 2, j, n4)) {
                        ++n12;
                    }
                    if (this.Il1lI((class_1937)class_6382, class_23392, n3 + 2, j, n4)) {
                        ++n12;
                    }
                    if (n12 < 3) continue;
                    ++n19;
                    continue;
                }
                if (++n15 > n10) break;
            }
            if ((n12 = i - (n13 = n14) + 1) >= n8) {
                int n23 = -1;
                int n24 = 0;
                for (int j = 0; j < 16; ++j) {
                    if (nArray[j] <= n24) continue;
                    n24 = nArray[j];
                    n23 = j;
                }
                map.put(new class_2338(n3, i, n4), new HoleType(i, n12, n17, n20, n21, n23, n24, n18, n19, bl6, bl));
            }
            i = n13;
        }
    }

    private boolean o0O0o(class_1937 class_19372, class_2338.class_2339 class_23392, int n, int n2, int n3) {
        class_23392.method_10103(n, n2, n3);
        class_2680 class_26802 = class_19372.method_8320((class_2338)class_23392);
        return class_26802.method_26215() || !class_26802.method_26227().method_15769();
    }

    private boolean o0O0o(class_2791 class_27912, class_1937 class_19372, class_2338.class_2339 class_23392, int n, int n2, int n3) {
        int n4;
        class_23392.method_10103(n, n2, n3);
        if (!this.o0O0o(class_19372, class_23392, n, n2, n3)) {
            return false;
        }
        int n5 = 0;
        if (this.Il1lI(class_19372, class_23392, n, n2, n3 - 1)) {
            ++n5;
        }
        if (this.Il1lI(class_19372, class_23392, n, n2, n3 + 1)) {
            ++n5;
        }
        if (this.Il1lI(class_19372, class_23392, n - 1, n2, n3)) {
            ++n5;
        }
        if (this.Il1lI(class_19372, class_23392, n + 1, n2, n3)) {
            ++n5;
        }
        return 4 - n5 <= (n4 = 1);
    }

    private void II1ll() {
        int n;
        boolean bl;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        class_638 class_6382 = this.mc.field_1687;
        if (class_6382 == null) {
            return;
        }
        Set set = Collections.newSetFromMap(new ConcurrentHashMap());
        Set set2 = Collections.newSetFromMap(new ConcurrentHashMap());
        HashMap<class_2338, HoleType> hashMap = new HashMap<class_2338, HoleType>();
        for (Map<class_2338, HoleType> class_233922 : this.map12.values()) {
            hashMap.putAll(class_233922);
        }
        HashSet hashSet = new HashSet();
        if (((Boolean)this.setting04.get()).booleanValue()) {
            class_2338.class_2339 class_23392 = new class_2338.class_2339();
            int[][] dimensions = new int[][]{{3, 3}, {3, 2}, {2, 3}, {3, 1}, {1, 3}, {2, 2}, {2, 1}, {1, 2}};
            block1: for (Map.Entry<class_2338, HoleType> entry : hashMap.entrySet()) {
                class_2338 origin = entry.getKey();
                if (hashSet.contains(origin)) continue;
                HoleType holeType2 = entry.getValue();
                n7 = holeType2.qQqQq();
                n6 = 0;
                for (int[] dimension : dimensions) {
                    if (n6 != 0) continue block1;
                    int width = dimension[0];
                    int depth = dimension[1];
                    for (n5 = 0; n5 < width && n6 == 0; ++n5) {
                        for (n4 = 0; n4 < depth && n6 == 0; ++n4) {
                            int n8;
                            int n9;
                            int n10;
                            int n11;
                            n3 = origin.method_10263() - n5;
                            n2 = origin.method_10260() - n4;
                            bl = true;
                            ArrayList<class_2338> arrayList = new ArrayList<class_2338>(width * depth);
                            int n12 = holeType2.I1l1I();
                            int n13 = holeType2.qQqQq();
                            block5: for (n11 = 0; n11 < width && bl; ++n11) {
                                block6: for (n10 = 0; n10 < depth && bl; ++n10) {
                                    n9 = n3 + n11;
                                    n8 = n2 + n10;
                                    class_2338 class_23382 = new class_2338(n9, n7, n8);
                                    HoleType holeType3 = (HoleType)hashMap.get(class_23382);
                                    if (holeType3 == null && (holeType3 = (HoleType)hashMap.get(class_23382 = new class_2338(n9, n7 + 1, n8))) == null) {
                                        class_23382 = new class_2338(n9, n7 - 1, n8);
                                        holeType3 = (HoleType)hashMap.get(class_23382);
                                    }
                                    if (holeType3 != null) {
                                        if (hashSet.contains(class_23382)) {
                                            bl = false;
                                            continue block5;
                                        }
                                        arrayList.add(class_23382);
                                        n12 = Math.max(n12, holeType3.I1l1I());
                                        n13 = Math.max(n13, holeType3.qQqQq());
                                        continue;
                                    }
                                    for (n = n13; n >= n12; --n) {
                                        if (this.o0O0o((class_1937)class_6382, class_23392, n9, n, n8)) continue;
                                        bl = false;
                                        continue block6;
                                    }
                                }
                            }
                            if (!bl || arrayList.isEmpty() || (n11 = n13 - n12 + 1) < (n10 = (Integer)this.setting05.get() * 14 / 4)) continue;
                            n9 = (2 * width + 2 * depth) * n11;
                            n8 = 0;
                            for (int i = n13; i >= n12; --i) {
                                int n14;
                                for (n14 = 0; n14 < width; ++n14) {
                                    if (!this.Il1lI((class_1937)class_6382, class_23392, n3 + n14, i, n2 - 1)) {
                                        ++n8;
                                    }
                                    if (this.Il1lI((class_1937)class_6382, class_23392, n3 + n14, i, n2 + depth)) continue;
                                    ++n8;
                                }
                                for (n14 = 0; n14 < depth; ++n14) {
                                    if (!this.Il1lI((class_1937)class_6382, class_23392, n3 - 1, i, n2 + n14)) {
                                        ++n8;
                                    }
                                    if (this.Il1lI((class_1937)class_6382, class_23392, n3 + width, i, n2 + n14)) continue;
                                    ++n8;
                                }
                            }
                            double d = (double)n8 / (double)Math.max(1, n9);
                            if (!(d <= 0.15)) continue;
                            set2.add(new class_238((double)n3, (double)n12, (double)n2, (double)(n3 + width), (double)(n13 + 1), (double)(n2 + depth)));
                            hashSet.addAll(arrayList);
                            n6 = 1;
                        }
                    }
                }
            }
        }
        if (((Boolean)this.setting03.get()).booleanValue()) {
            class_2338.class_2339 class_23393 = new class_2338.class_2339();
            for (Map.Entry<class_2338, HoleType> entry : hashMap.entrySet()) {
                class_2338 class_23383 = entry.getKey();
                HoleType holeType = entry.getValue();
                if (hashSet.contains(class_23383) || holeType.OOo0O() && !holeType.iI1li()) continue;
                int n15 = holeType.zZzZz();
                n7 = Math.max(1, holeType.O0o0O());
                n6 = holeType.qQqQq();
                int n16 = holeType.I1l1I();
                int n17 = (Integer)this.setting05.get() * 14 / 4;
                if (holeType.O0o0O() < n17) continue;
                double d = (double)holeType.lIIll() / (double)n7;
                double d2 = (double)holeType.l1ll1() / (double)n7;
                n5 = d >= 0.85 ? 1 : 0;
                n4 = Integer.bitCount(holeType.i1Ili()) >= 3 ? 1 : 0;
                n3 = d2 >= 0.8 ? 1 : 0;
                n2 = n16 <= class_6382.method_31607() || this.Il1lI((class_1937)class_6382, class_23393, class_23383.method_10263(), n16 - 1, class_23383.method_10260()) || holeType.Il1Il() >= n7 * 2 / 3 ? 1 : 0;
                bl = false;
                if (!holeType.iI1li()) {
                    int[] nArray;
                    int n18 = 5;
                    int[] nArray2 = new int[]{0, 0, -1, 1};
                    int[] nArray3 = new int[]{-1, 1, 0, 0};
                    block12: for (int n19 : nArray = new int[]{n6, n16}) {
                        for (int i = 0; i < 4; ++i) {
                            if ((holeType.i1Ili() & 1 << i) != 0) continue;
                            n = nArray2[i];
                            int n20 = nArray3[i];
                            int n21 = 0;
                            for (int j = 1; j <= 5; ++j) {
                                class_23393.method_10103(class_23383.method_10263() + n * j, n19, class_23383.method_10260() + n20 * j);
                                if (!class_6382.method_8320((class_2338)class_23393).method_26215()) break;
                                ++n21;
                            }
                            if (n21 < 5) continue;
                            bl = true;
                            break block12;
                        }
                    }
                }
                if (n5 == 0 || n4 == 0 || n3 == 0 || n2 == 0 || bl) continue;
                set.add(new class_238((double)class_23383.method_10263(), (double)n16, (double)class_23383.method_10260(), (double)(class_23383.method_10263() + 1), (double)(n6 + 1), (double)(class_23383.method_10260() + 1)));
            }
        }
        this.set17.clear();
        this.set17.addAll(set);
        this.set18.clear();
        this.set18.addAll(set2);
    }

    private boolean o0O0o(class_2791 class_27912, class_2338.class_2339 class_23392, int n, int n2, int n3) {
        class_23392.method_10103(n, n2, n3);
        class_2248 class_22482 = class_27912.method_8320((class_2338)class_23392).method_26204();
        return class_22482 == class_2246.field_9993 || class_22482 == class_2246.field_10463 || class_22482 == class_2246.field_10376 || class_22482 == class_2246.field_10238 || class_22482 == class_2246.field_10597 || class_22482 == class_2246.field_28675 || class_22482 == class_2246.field_28676 || class_22482 == class_2246.field_28411 || class_22482 == class_2246.field_10588 || class_22482.method_63499().contains("coral");
    }

    private boolean Il1lI(class_1937 class_19372, class_2338.class_2339 class_23392, int n, int n2, int n3) {
        class_23392.method_10103(n, n2, n3);
        class_2680 class_26802 = class_19372.method_8320((class_2338)class_23392);
        if (class_26802.method_26215() || !class_26802.method_26227().method_15769()) {
            return false;
        }
        class_2248 class_22482 = class_26802.method_26204();
        return class_22482 != class_2246.field_10033 && class_22482 != class_2246.field_10285 && class_22482 != class_2246.field_10597 && class_22482 != class_2246.field_28675 && class_22482 != class_2246.field_28676 && class_22482 != class_2246.field_9993 && class_22482 != class_2246.field_10463 && class_22482 != class_2246.field_10376 && class_22482 != class_2246.field_10238;
    }

    private boolean xXxXx(class_1937 class_19372, class_2338.class_2339 class_23392, int n, int n2, int n3) {
        class_23392.method_10103(n, n2, n3);
        class_2680 class_26802 = class_19372.method_8320((class_2338)class_23392);
        if (class_26802.method_26215()) {
            return false;
        }
        class_2248 class_22482 = class_26802.method_26204();
        String string = class_22482.method_63499();
        return string.contains("rail") || string.contains("fence") || string.contains("chain") || class_22482 == class_2246.field_10343 || class_22482 == class_2246.field_10161 || class_22482 == class_2246.field_10075;
    }

    @EventHandler
    private void o0O0o(Render3DEvent render3DEvent) {
        if (!this.isActive() || this.mc.field_1724 == null) {
            return;
        }
        Renderer3D renderer3D = render3DEvent.renderer;
        if (((Boolean)this.setting03.get()).booleanValue()) {
            for (class_238 class_2382 : this.set17) {
                renderer3D.box(class_2382.field_1323, class_2382.field_1322, class_2382.field_1321, class_2382.field_1320, class_2382.field_1325, class_2382.field_1324, (Color)constant06, (Color)constant08, ShapeMode.Sides, 0);
            }
        }
        if (((Boolean)this.setting04.get()).booleanValue()) {
            for (class_238 class_2382 : this.set18) {
                renderer3D.box(class_2382.field_1323, class_2382.field_1322, class_2382.field_1321, class_2382.field_1320, class_2382.field_1325, class_2382.field_1324, (Color)constant07, (Color)constant08, ShapeMode.Sides, 0);
            }
        }
    }

    class ChunkHoles {
        private final int counter01;
        private final int counter02;
        public boolean flag03;

        public ChunkHoles(HoleEsp holeEsp, int n, int n2) {
            this.counter01 = n;
            this.counter02 = n2;
            this.flag03 = true;
        }

        public long o0O0o() {
            return class_1923.method_8331((int)this.counter01, (int)this.counter02);
        }
    }

    public static final class HoleType {
        private final int counter01;
        private final int counter02;
        private final int counter03;
        private final int counter04;
        private final int counter05;
        private final int counter06;
        private final int counter07;
        private final int counter08;
        private final int counter09;
        private final boolean flag10;
        private final boolean flag11;

        public HoleType(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, boolean bl, boolean bl2) {
            this.counter01 = n;
            this.counter02 = n2;
            this.counter03 = n3;
            this.counter04 = n4;
            this.counter05 = n5;
            this.counter06 = n6;
            this.counter07 = n7;
            this.counter08 = n8;
            this.counter09 = n9;
            this.flag10 = bl;
            this.flag11 = bl2;
        }

        public final String methodCollision02() {
            return this.toString();
        }

        public final int xXxXx() {
            return this.hashCode();
        }

        public final boolean o0O0o(Object object) {
            return this.equals(object);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (!(object instanceof HoleType other)) return false;
            return this.counter01 == other.counter01 && this.counter02 == other.counter02
                && this.counter03 == other.counter03 && this.counter04 == other.counter04
                && this.counter05 == other.counter05 && this.counter06 == other.counter06
                && this.counter07 == other.counter07 && this.counter08 == other.counter08
                && this.counter09 == other.counter09 && this.flag10 == other.flag10
                && this.flag11 == other.flag11;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.counter01, this.counter02, this.counter03, this.counter04,
                this.counter05, this.counter06, this.counter07, this.counter08, this.counter09,
                this.flag10, this.flag11);
        }

        @Override
        public String toString() {
            return "HoleType[topY=" + this.counter01 + ", depth=" + this.counter02
                + ", interiorLayers=" + this.counter03 + ", shaftLayers=" + this.counter04
                + ", shaftBottomY=" + this.counter05 + ", dominantPattern=" + this.counter06
                + ", dominantCount=" + this.counter07 + ", fullySolidLayers=" + this.counter08
                + ", outerSolidLayers=" + this.counter09 + ", hasVegetation=" + this.flag10
                + ", isUnderwaterContext=" + this.flag11 + "]";
        }

        public int qQqQq() {
            return this.counter01;
        }

        public int zZzZz() {
            return this.counter02;
        }

        public int methodCollision01() {
            return this.counter03;
        }

        public int O0o0O() {
            return this.counter04;
        }

        public int I1l1I() {
            return this.counter05;
        }

        public int i1Ili() {
            return this.counter06;
        }

        public int lIIll() {
            return this.counter07;
        }

        public int Il1Il() {
            return this.counter08;
        }

        public int l1ll1() {
            return this.counter09;
        }

        public boolean OOo0O() {
            return this.flag10;
        }

        public boolean iI1li() {
            return this.flag11;
        }
    }
}
