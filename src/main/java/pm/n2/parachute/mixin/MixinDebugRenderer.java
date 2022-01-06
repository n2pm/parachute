package pm.n2.parachute.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pm.n2.parachute.util.DebugRenderers;

@Mixin(DebugRenderer.class)
public class MixinDebugRenderer {
    @Inject(method = "render", at = @At("HEAD"))
    private void renderDebugRenderers(MatrixStack matrixStack, VertexConsumerProvider.Immediate vertexConsumer, double camX, double camY, double camZ, CallbackInfo ci) {
        DebugRenderers.renderVanillaDebug(matrixStack, vertexConsumer, camX, camY, camZ);
    }
}
