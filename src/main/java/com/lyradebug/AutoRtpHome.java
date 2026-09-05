package com.lyradebug;

import com.lyradebug.LyraDebug;
import com.lyradebug.StringCipher;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.EnumSetting;
import meteordevelopment.meteorclient.settings.IntSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;

public final class AutoRtpHome
extends Module {
    private static final long constant01 = 12000L;
    private static final double constant02 = 64.0;
    private final SettingGroup group03;
    private final Setting<Double> setting04;
    private final Setting<String> setting05;
    private final Setting<Region> setting06;
    private final Setting<Integer> setting07;
    private final Setting<Integer> setting08;
    private int counter09;
    private long counter10;
    private long counter11;
    private double value12;
    private double value13;
    private boolean flag14;

    public AutoRtpHome() {
        super(LyraDebug.CATEGORY, "auto-rtp-home", "Automatically /delhome, /sethome, /rtp, and /home when falling below trigger Y.");
        this.group03 = this.settings.getDefaultGroup();
        this.setting04 = this.group03.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)new DoubleSetting.Builder().name("trigger-y")).description("Y level threshold to trigger Auto RTP Home.")).defaultValue(-1.0).min(-64.0).max(320.0).sliderMin(-64.0).sliderMax(320.0).build());
        this.setting05 = this.group03.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)new StringSetting.Builder().name("home-slot")).description("Home slot name or number to use.")).defaultValue("1")).build());
        this.setting06 = this.group03.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)new EnumSetting.Builder().name("rtp-region")).description("Region to use for RTP command.")).defaultValue(Region.constant01)).build());
        this.setting07 = this.group03.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("command-delay")).description("Delay between commands in milliseconds.")).defaultValue(500)).min(100).max(3000).sliderMin(100).sliderMax(3000).build());
        this.setting08 = this.group03.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)new IntSetting.Builder().name("rtp-delay")).description("Delay after RTP before returning home in milliseconds.")).defaultValue(2000)).min(500).max(10000).sliderMin(500).sliderMax(10000).build());
    }

    public void onActivate() {
        this.counter09 = 0;
        this.counter10 = 0L;
        this.counter11 = 0L;
        this.flag14 = true;
    }

    public void onDeactivate() {
        this.counter09 = 0;
        this.counter10 = 0L;
        this.counter11 = 0L;
    }

    @EventHandler
    private void o0O0o(TickEvent.Post post) {
        if (this.mc.field_1724 == null || this.mc.method_1562() == null) {
            return;
        }
        long l = System.currentTimeMillis();
        if (this.counter09 == 0) {
            double d = this.mc.field_1724.method_23318();
            if (d > (Double)this.setting04.get()) {
                this.flag14 = true;
            }
            if (this.flag14 && d <= (Double)this.setting04.get()) {
                this.flag14 = false;
                this.counter09 = 1;
                this.counter10 = l + 10L;
                this.info("[AutoRTPHome] Đã rơi xuống dưới Y = " + (int)d, new Object[0]);
            }
            return;
        }
        if (l < this.counter10 && this.counter09 != 4) {
            return;
        }
        String string = (String)this.setting05.get();
        String string2 = ((Region)(this.setting06.get())).xXxXx();
        long l2 = ((Integer)this.setting07.get()).intValue();
        switch (this.counter09) {
            case 1: {
                this.O0o0O("delhome " + string);
                this.info("[AutoRTPHome] Đang xóa home: " + string, new Object[0]);
                this.counter09 = 2;
                this.counter10 = l + l2;
                break;
            }
            case 2: {
                this.O0o0O("sethome " + string);
                this.info("[AutoRTPHome] Đang set home: " + string, new Object[0]);
                this.counter09 = 3;
                this.counter10 = l + l2;
                break;
            }
            case 3: {
                this.O0o0O("rtp " + string2);
                this.info("[AutoRTPHome] Đang RTP region: " + string2, new Object[0]);
                this.value12 = this.mc.field_1724.method_23317();
                this.value13 = this.mc.field_1724.method_23321();
                this.counter11 = l;
                this.counter09 = 4;
                this.counter10 = 0L;
                break;
            }
            case 4: {
                double d = this.mc.field_1724.method_23317() - this.value12;
                double d2 = this.mc.field_1724.method_23321() - this.value13;
                boolean bl = Math.sqrt(d * d + d2 * d2) >= 64.0;
                long l3 = ((Integer)this.setting08.get()).intValue();
                if (bl) {
                    this.counter09 = 5;
                    this.counter10 = l + l3;
                    this.info("[AutoRTPHome] Đã dịch chuyển thành công! Đang chờ " + l3 + " ms trước khi về home...", new Object[0]);
                    break;
                }
                if (l - this.counter11 <= 12000L) break;
                this.counter09 = 5;
                this.counter10 = l + l3;
                this.info("[AutoRTPHome] Hết thời gian chờ RTP (" + "12" + "s). Vẫn thử về home...", new Object[0]);
                break;
            }
            case 5: {
                if (l < this.counter10) {
                    return;
                }
                this.O0o0O("home " + string);
                this.info("[AutoRTPHome] Đang dịch chuyển về home " + string + " và tắt module.", new Object[0]);
                this.toggle();
            }
        }
    }

    private void O0o0O(String string) {
        if (this.mc.method_1562() != null) {
            this.mc.method_1562().method_45730(string);
        }
    }

    public enum Region {
        constant01("east"),
        constant02("west"),
        constant03("eu-central"),
        constant04("eu-west"),
        constant05("asia"),
        constant06("oceania");

        private final String field07;

        Region(String string2) {
            this.field07 = string2;
        }

        public String xXxXx() {
            return this.field07;
        }

        public String qQqQq() {
            return this.field07;
        }
    }
}
