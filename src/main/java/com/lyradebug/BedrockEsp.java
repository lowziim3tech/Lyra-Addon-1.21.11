package com.lyradebug;

import com.lyradebug.LyraDebug;
import com.lyradebug.StringCipher;
import com.lyradebug.license.NativeGuard;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import meteordevelopment.meteorclient.events.packets.PacketEvent;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.events.world.BlockUpdateEvent;
import meteordevelopment.meteorclient.events.world.ChunkDataEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.ColorSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.class_1923;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2596;
import net.minecraft.class_2666;
import net.minecraft.class_2680;
import net.minecraft.class_2818;
import net.minecraft.class_2960;
import net.minecraft.class_7923;

public class BedrockEsp
extends Module {
    private final SettingGroup group01;
    private final SettingGroup group02;
    private final SettingGroup group03;
    private final Setting<Integer> setting04;
    private final Setting<Boolean> setting05;
    private final Setting<Boolean> setting06;
    private final Setting<Boolean> setting07;
    private final Setting<Integer> setting08;
    private final Setting<SettingColor> setting09;
    private final Setting<ShapeMode> setting10;
    private final Setting<SettingColor> setting11;
    private final Setting<Boolean> setting12;
    private final Setting<Integer> setting13;
    private final Set<class_2338> set14;
    private ExecutorService field15;
    private long counter16;
    private int counter17;

    public BedrockEsp() {
        super(LyraDebug.CATEGORY, "bedrock-esp", "Highlights missing bedrock and holes in Nether ceiling/floor.");
        this.group01 = this.settings.getDefaultGroup();
        this.group02 = this.settings.createGroup("Render");
        this.group03 = this.settings.createGroup("Performance");
        this.setting04 = this.group01.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("min-void-size")).description("Minimum contiguous void blocks to consider a hole.")).defaultValue(2)).min(1).sliderMax(50).onChanged(this::o0O0o)).build());
        this.setting05 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("show-esp")).description("Show bounding boxes for missing bedrock.")).defaultValue(true)).build());
        this.setting06 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("show-tracers")).description("Show tracers to detected bedrock holes.")).defaultValue(false)).build());
        this.setting07 = this.group01.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("chat-feedback")).description("Send a message in chat when bedrock holes are found.")).defaultValue(true)).build());
        this.setting08 = this.group01.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("max-messages-per-minute")).description("Max alerts sent per minute to avoid chat spam.")).defaultValue(10)).min(0).max(60).sliderRange(0, 60).visible(() -> this.setting07.get())).build());
        this.setting09 = this.group02.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("esp-color")).description("Color of the bounding box.")).defaultValue(new SettingColor(240, 85, 80, 128)).visible(() -> this.setting05.get())).build());
        this.setting10 = this.group02.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("shape-mode")).description("How the shape is rendered.")).defaultValue(ShapeMode.Both)).visible(() -> this.setting05.get())).build());
        this.setting11 = this.group02.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)new ColorSetting.Builder().name("tracer-color")).description("Color of the tracers.")).defaultValue(new SettingColor(255, 0, 0, 255)).visible(() -> this.setting06.get())).build());
        this.setting12 = this.group03.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)new BoolSetting.Builder().name("use-threading")).description("Process chunk scans asynchronously for better FPS.")).defaultValue(true)).build());
        this.setting13 = this.group03.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("thread-pool-size")).description("Number of worker threads.")).defaultValue(4)).min(1).max(8).sliderRange(1, 8).visible(() -> this.setting12.get())).build());
        this.set14 = ConcurrentHashMap.newKeySet();
        this.counter16 = 0L;
        this.counter17 = 0;
    }

    public void onActivate() {
        if (this.mc.field_1687 == null) {
            return;
        }
        if (((Boolean)this.setting12.get()).booleanValue()) {
            this.field15 = Executors.newFixedThreadPool((Integer)this.setting13.get(), runnable -> {
                Thread thread = new Thread(runnable, "LyraDebug-BedrockESP");
                thread.setDaemon(true);
                return thread;
            });
        }
        this.set14.clear();
        this.counter16 = 0L;
        this.counter17 = 0;
        this.i1Ili();
    }

    public void onDeactivate() {
        if (this.field15 != null && !this.field15.isShutdown()) {
            this.field15.shutdownNow();
            this.field15 = null;
        }
        this.set14.clear();
    }

    private void o0O0o(Integer n) {
        if (this.isActive() && this.mc.field_1687 != null) {
            this.set14.clear();
            this.i1Ili();
        }
    }

    private void i1Ili() {
        if (this.mc.field_1687 == null || this.mc.field_1724 == null) {
            return;
        }
        int n = (Integer)this.mc.field_1690.method_42503().method_41753();
        class_1923 class_19232 = this.mc.field_1724.method_31476();
        for (int i = class_19232.field_9181 - n; i <= class_19232.field_9181 + n; ++i) {
            for (int j = class_19232.field_9180 - n; j <= class_19232.field_9180 + n; ++j) {
                class_2818 class_28182;
                if (!this.mc.field_1687.method_8393(i, j) || (class_28182 = this.mc.field_1687.method_8497(i, j)) == null) continue;
                if (((Boolean)this.setting12.get()).booleanValue() && this.field15 != null && !this.field15.isShutdown()) {
                    this.field15.submit(() -> this.o0O0o(class_28182));
                    continue;
                }
                this.o0O0o(class_28182);
            }
        }
    }

    @EventHandler
    private void o0O0o(ChunkDataEvent chunkDataEvent) {
        class_2818 class_28182 = chunkDataEvent.chunk();
        if (class_28182 instanceof class_2818) {
            if (((Boolean)this.setting12.get()).booleanValue() && this.field15 != null && !this.field15.isShutdown()) {
                this.field15.submit(() -> this.o0O0o(class_28182));
            } else {
                this.o0O0o(class_28182);
            }
        }
    }

    @EventHandler
    private void o0O0o(BlockUpdateEvent blockUpdateEvent) {
        class_2818 class_28182;
        if (this.mc.field_1687 == null) {
            return;
        }
        class_2338 class_23382 = blockUpdateEvent.pos;
        int n = this.o0O0o();
        if (!BedrockEsp.o0O0o(n, class_23382.method_10264())) {
            return;
        }
        class_1923 class_19232 = new class_1923(class_23382);
        if (this.mc.field_1687.method_8393(class_19232.field_9181, class_19232.field_9180) && (class_28182 = this.mc.field_1687.method_8497(class_19232.field_9181, class_19232.field_9180)) != null) {
            if (((Boolean)this.setting12.get()).booleanValue() && this.field15 != null && !this.field15.isShutdown()) {
                this.field15.submit(() -> this.o0O0o(class_28182));
            } else {
                this.o0O0o(class_28182);
            }
        }
    }

    @EventHandler
    private void o0O0o(PacketEvent.Receive receive) {
        class_2596 packet = receive.packet;
        if (packet instanceof class_2666 unloadPacket) {
            class_1923 chunkPos = unloadPacket.comp_1726();
            this.set14.removeIf(blockPos -> BedrockEsp.Il1lI(chunkPos, blockPos));
        }
    }
private void o0O0o(class_2818 class_28182) {
        int n = 18977;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int[] nArray = null;
        block7: while (n != 0) {
            switch (n) {
                case 18977: {
                    if (this.mc.field_1687 == null || class_28182 == null) {
                        n = 0;
                        continue block7;
                    }
                    class_1923 class_19232 = class_28182.method_12004();
                    this.set14.removeIf(class_23382 -> new class_1923(class_23382).equals(class_19232));
                    n2 = this.o0O0o();
                    if (n2 == 0) {
                        n = 0;
                        continue block7;
                    }
                    n3 = class_19232.method_8326();
                    n4 = class_19232.method_8328();
                    n5 = n2 == 1 ? 5 : 10;
                    nArray = new int[n5 * 256];
                    n = 31508;
                    continue block7;
                }
                case 31508: {
                    int n6;
                    int n7;
                    int n8;
                    class_2338.class_2339 class_23392 = new class_2338.class_2339();
                    for (int i = 0; i < n5; ++i) {
                        int n9 = n2 == 1 ? i - 64 : (i < 5 ? i & 7 : 118 + i);
                        n8 = i * 256;
                        for (n7 = 0; n7 < 16; ++n7) {
                            for (n6 = 0; n6 < 16; ++n6) {
                                class_23392.method_10103(n3 + n7, n9, n4 + n6);
                                class_2680 class_26802 = class_28182.method_8320((class_2338)class_23392);
                                nArray[n8 + (n7 << 4) + n6] = class_2248.method_9507((class_2680)class_26802);
                            }
                        }
                    }
                    n = 40162;
                    continue block7;
                }
                case 40162: {
                    int n6;
                    int n7;
                    int n8;
                    int n10 = class_2248.method_9507((class_2680)((class_2248)class_7923.field_41175.method_63535(class_2960.method_60656((String)"bedrock-"))).method_9564());
                    long[] packedPositions = null;
                    if (NativeGuard.Il1lI()) {
                        try {
                            packedPositions = NativeGuard.processChunkBufferNative(n3, n4, n2, (Integer)this.setting04.get(), n10, nArray);
                        }
                        catch (Throwable throwable) {
                        }
                    }
                    if (packedPositions != null && packedPositions.length > 0) {
                        for (long packedPosition : packedPositions) {
                            this.set14.add(class_2338.method_10092(packedPosition));
                        }
                        this.o0O0o(packedPositions.length, class_2338.method_10092(packedPositions[0]));
                    } else if (packedPositions == null) {
                        boolean[] blArray = new boolean[nArray.length];
                        n8 = (Integer)this.setting04.get();
                        for (n7 = 0; n7 < n5; ++n7) {
                            for (n6 = 0; n6 < 16; ++n6) {
                                for (int i = 0; i < 16; ++i) {
                                    int n12 = n7 * 256 + (n6 << 4) + i;
                                    if (blArray[n12] || nArray[n12] == n10) continue;
                                    ArrayList<class_2338> arrayList = new ArrayList<class_2338>();
                                    LinkedList<Integer> linkedList = new LinkedList<Integer>();
                                    linkedList.add(n12);
                                    blArray[n12] = true;
                                    while (!linkedList.isEmpty() && arrayList.size() < 200) {
                                        int n13 = (Integer)linkedList.poll();
                                        int n14 = n13 & 0xF;
                                        int n15 = n13 >> 4 & 0xF;
                                        int n16 = n13 >> 8;
                                        int n17 = n2 == 1 ? n16 - 64 : (n16 < 5 ? n16 & 7 : 118 + n16);
                                        arrayList.add(new class_2338(n3 + n15, n17, n4 + n14));
                                        int[] nArray2 = new int[]{1, -1, 0, 0, 0, 0};
                                        int[] nArray3 = new int[]{0, 0, 1, -1, 0, 0};
                                        int[] nArray4 = new int[]{0, 0, 0, 0, 1, -1};
                                        for (int j = 0; j < 6; ++j) {
                                            int n18;
                                            int n19 = n15 + nArray2[j];
                                            int n20 = n16 + nArray3[j];
                                            int n21 = n14 + nArray4[j];
                                            if (n19 < 0 || n19 >= 16 || n21 < 0 || n21 >= 16 || n20 < 0 || n20 >= n5 || blArray[n18 = n20 * 256 + (n19 << 4) + n21] || nArray[n18] == n10) continue;
                                            blArray[n18] = true;
                                            linkedList.add(n18);
                                        }
                                    }
                                    if (arrayList.size() < n8) continue;
                                    this.set14.addAll(arrayList);
                                    this.o0O0o(arrayList.size(), (class_2338)arrayList.get(0));
                                }
                            }
                        }
                    }
                    n = 0;
                    continue block7;
                }
            }
            n = 0;
        }
    }

    private int o0O0o() {
        if (this.mc.field_1687 == null) {
            return 0;
        }
        String string = this.mc.field_1687.method_27983().method_29177().toString();
        if (string.equals("minecraft:overworld")) {
            return 1;
        }
        if (string.equals("minecraft:the_nether")) {
            return 2;
        }
        return 0;
    }

    private static boolean o0O0o(int n, int n2) {
        if (n == 1) {
            return n2 >= -64 && n2 <= -60;
        }
        if (n == 2) {
            return n2 >= 0 && n2 <= 4 || n2 >= 123 && n2 <= 127;
        }
        return false;
    }

    private void o0O0o(int n, class_2338 class_23382) {
        int n2;
        if (!((Boolean)this.setting07.get()).booleanValue()) {
            return;
        }
        long l = System.currentTimeMillis();
        long l2 = l / 60000L;
        if (l2 != this.counter16) {
            this.counter16 = l2;
            this.counter17 = 0;
        }
        if ((n2 = ((Integer)this.setting08.get()).intValue()) == 0 || this.counter17 < n2) {
            ChatUtils.info((String)("§5[§dBedrockESP§5] §bVoid found§5: §b" + n + " blocks at " + class_23382.method_23854()), (Object[])new Object[0]);
            ++this.counter17;
        }
    }

    @EventHandler
    private void o0O0o(Render3DEvent render3DEvent) {
        Color color;
        if (((Boolean)this.setting05.get()).booleanValue()) {
            color = (Color)this.setting09.get();
            for (class_2338 object : this.set14) {
                render3DEvent.renderer.box(object, color, color, (ShapeMode)this.setting10.get(), 0);
            }
        }
        if (((Boolean)this.setting06.get()).booleanValue() && this.mc.field_1724 != null) {
            color = (Color)this.setting11.get();
            class_243 class_2432 = this.mc.field_1724.method_33571();
            for (class_2338 class_23382 : this.set14) {
                render3DEvent.renderer.line(class_2432.field_1352, class_2432.field_1351, class_2432.field_1350, (double)class_23382.method_10263() + 0.5, (double)class_23382.method_10264() + 0.5, (double)class_23382.method_10260() + 0.5, color);
            }
        }
    }

    private static
boolean Il1lI(class_1923 class_19232, class_2338 class_23382) {
        return new class_1923(class_23382).equals(class_19232);
    }
}
