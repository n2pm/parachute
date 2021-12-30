package pm.n2.parachute.render;

import fi.dy.masa.malilib.util.EntityUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Matrix4f;

public class OverlayRenderer {
    private static long loginTime;
    private static boolean canRender;

    public static void resetRenderTimeout() {
        canRender = false;
        loginTime = System.currentTimeMillis();
    }

    public static void renderOverlays(MatrixStack matrixStack, Matrix4f projMatrix, MinecraftClient mc) {
        Entity entity = EntityUtils.getCameraEntity();

        if (entity == null) {
            return;
        }

        if (!canRender) {
            // Don't render before the player has been placed in the actual proper position,
            // otherwise some of the renderers mess up.
            // The magic 8.5, 65, 8.5 comes from the WorldClient constructor
            if (System.currentTimeMillis() - loginTime >= 5000 || entity.getX() != 8.5 || entity.getY() != 65 || entity.getZ() != 8.5) {
                canRender = true;
            } else {
                return;
            }
        }

        RenderContainer.INSTANCE.render(entity, matrixStack, projMatrix, mc);
    }
}
