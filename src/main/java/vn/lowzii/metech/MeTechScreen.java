package vn.lowzii.metech;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public final class MeTechScreen extends Screen {
    private ButtonWidget chop;
    private ButtonWidget camera;
    public MeTechScreen() { super(Text.literal("MeTech Auto Sell")); }
    @Override protected void init() {
        int x = width / 2 - 100;
        int y = height / 2 - 40;
        chop = addDrawableChild(ButtonWidget.builder(Text.empty(), b -> {
            MeTechClient.INSTANCE.toggle(); refresh();
        }).dimensions(x, y, 200, 20).build());
        camera = addDrawableChild(ButtonWidget.builder(Text.empty(), b -> {
            MeTechClient.INSTANCE.toggleCamera(); refresh();
        }).dimensions(x, y + 26, 200, 20).build());
        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close()).dimensions(x, y + 76, 200, 20).build());
        refresh();
    }
    private void refresh() {
        chop.setMessage(Text.literal("Auto Chop: " + (MeTechClient.INSTANCE.enabled() ? "ON" : "OFF")));
        camera.setMessage(Text.literal("Look Up / 2 min: " + (MeTechClient.INSTANCE.correctCamera() ? "ON" : "OFF")));
    }
    @Override public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(textRenderer, title, width / 2, height / 2 - 70, 0xFFFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal(MeTechClient.INSTANCE.status()), width / 2, height / 2 + 15, 0xFFAAAAAA);
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Change keys: Options > Controls > Key Binds"), width / 2, height / 2 + 70, 0xFFAAAAAA);
    }
    @Override public boolean shouldPause() { return false; }
}
