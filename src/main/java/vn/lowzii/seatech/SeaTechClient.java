package vn.lowzii.seatech;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.lwjgl.glfw.GLFW;

public final class SeaTechClient implements ClientModInitializer {
    public static final SeaTechClient INSTANCE = new SeaTechClient();
    private final Interval camera = new Interval(120_000_000_000L);
    private final Interval attack = new Interval(500_000_000L);
    private KeyBinding guiKey;
    private KeyBinding chopKey;
    private boolean enabled;
    private boolean correctCamera = true;
    private boolean savedPause;
    private String status = "OFF";

    @Override public void onInitializeClient() {
        INSTANCE.initialize();
    }
    private void initialize() {
        var category = KeyBinding.Category.create(Identifier.of("seatech_auto_sell", "main"));
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.seatech_auto_sell.gui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, category));
        chopKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.seatech_auto_sell.toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, category));
        ClientTickEvents.END_CLIENT_TICK.register(this::tick);
    }
    public boolean enabled() { return enabled; }
    public boolean correctCamera() { return correctCamera; }
    public String status() { return status; }
    public void toggleCamera() { correctCamera = !correctCamera; camera.reset(System.nanoTime()); }
    public void toggle() {
        var client = MinecraftClient.getInstance();
        if (!enabled && (client.player == null || client.world == null)) return;
        enabled = !enabled;
        if (enabled) {
            savedPause = client.options.pauseOnLostFocus;
            client.options.pauseOnLostFocus = false;
            camera.reset(System.nanoTime());
            attack.reset(System.nanoTime());
            status = "ON";
        } else {
            client.options.pauseOnLostFocus = savedPause;
            if (client.interactionManager != null) client.interactionManager.cancelBlockBreaking();
            status = "OFF";
        }
        if (client.player != null) client.player.sendMessage(Text.literal("SeaTech Auto Chop: " + status), true);
    }
    private void tick(MinecraftClient client) {
        while (chopKey.wasPressed()) {
            if (client.currentScreen == null) toggle();
        }
        while (guiKey.wasPressed()) {
            if (client.currentScreen == null && client.player != null) client.setScreen(new SeaTechScreen());
        }
        if (!enabled) return;
        if (client.player == null || client.world == null || !client.player.isAlive()) {
            toggle(); // Never carry an armed clicker into another server or respawn.
            return;
        }
        long now = System.nanoTime();
        if (camera.due(now) && correctCamera && client.player.getPitch() != -90.0F) {
            client.player.setPitch(-90.0F); // Up, retaining yaw.
        }
        if (client.interactionManager == null || client.isPaused()) return;
        if (!attack.due(now)) return;
        if (!client.player.getMainHandStack().isIn(ItemTags.AXES)) {
            status = "Waiting for axe";
            return;
        }
        // A fresh player raycast works while an inventory/GUI is open.
        HitResult target = client.player.raycast(client.player.getBlockInteractionRange(), 1.0F, false);
        if (!(target instanceof BlockHitResult hit) || target.getType() != HitResult.Type.BLOCK
                || !(client.world.getBlockState(hit.getBlockPos()).getBlock() instanceof ChestBlock)) {
            status = "Waiting for chest";
            return;
        }
        // Distinct short clicks; do not hold mining progress against the chest.
        client.interactionManager.cancelBlockBreaking();
        client.interactionManager.attackBlock(hit.getBlockPos(), hit.getSide());
        client.player.swingHand(Hand.MAIN_HAND);
        client.interactionManager.cancelBlockBreaking();
        status = "Chopping";
    }
}
