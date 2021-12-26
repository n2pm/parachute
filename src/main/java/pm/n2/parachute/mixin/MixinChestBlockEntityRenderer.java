package pm.n2.parachute.mixin;

import net.minecraft.client.render.block.entity.ChestBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.config.FeatureOverride;

@Mixin(ChestBlockEntityRenderer.class)
public class MixinChestBlockEntityRenderer {
    @ModifyArg(method = "render(Lnet/minecraft/block/entity/BlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getChestTexture(Lnet/minecraft/block/entity/BlockEntity;Lnet/minecraft/block/enums/ChestType;Z)Lnet/minecraft/client/util/SpriteIdentifier;"), index = 2)
    private boolean isChristmas(boolean christmas) {
        return switch ((FeatureOverride) Configs.TweakConfigs.IS_CHRISTMAS.getOptionListValue()) {
            case DEFAULT -> christmas;
            case FORCE_FALSE -> false;
            case FORCE_TRUE -> true;
        };
    }
}
