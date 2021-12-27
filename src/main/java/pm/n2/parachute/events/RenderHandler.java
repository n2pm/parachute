package pm.n2.parachute.events;

import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.gui.hud.ArmorHUD;
import pm.n2.parachute.gui.hud.PotionEffectHUD;
import pm.n2.parachute.render.OverlayRenderer;

public class RenderHandler implements IRenderer {
    private static final RenderHandler INSTANCE = new RenderHandler();

    private final MinecraftClient client;


    public RenderHandler() {
        this.client = MinecraftClient.getInstance();
    }

    public static RenderHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void onRenderWorldLast(MatrixStack matrixStack, Matrix4f projMatrix) {
        if (this.client.world != null && this.client.player != null && !this.client.options.hudHidden) {
            OverlayRenderer.renderOverlays(matrixStack, projMatrix, this.client);
        }
    }

    @Override
    public void onRenderGameOverlayPost(MatrixStack matrixStack) {
        if (!this.client.options.debugEnabled && this.client.player != null) {
            if (Configs.FeatureConfigs.POTION_EFFECT_HUD.getBooleanValue())
                PotionEffectHUD.INSTANCE.render(matrixStack);

            if (Configs.FeatureConfigs.ARMOR_HUD.getBooleanValue())
                ArmorHUD.INSTANCE.render(matrixStack);
        }
    }

}
