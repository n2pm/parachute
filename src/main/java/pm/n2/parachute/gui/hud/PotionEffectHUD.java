package pm.n2.parachute.gui.hud;

import com.google.common.collect.Ordering;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.util.Formatting;
import pm.n2.parachute.config.Configs;

import java.util.Collection;

/**
 * @author Cynosphere <gamers@riseup.net>
 */
public class PotionEffectHUD {
    public static PotionEffectHUD INSTANCE = new PotionEffectHUD();
    private final MinecraftClient client;

    public PotionEffectHUD(){
        this.client = MinecraftClient.getInstance();
    }

    public void render(MatrixStack matrixStack){
        int width = this.client.getWindow().getScaledWidth();
        int height = this.client.getWindow().getScaledHeight();

        int index = 0;

        Collection<StatusEffectInstance> effects = this.client.player.getStatusEffects();
        if (!effects.isEmpty()) {
            for(StatusEffectInstance statusEffect : Ordering.natural().sortedCopy(effects)) {
                StatusEffect effect = statusEffect.getEffectType();

                String name = I18n.translate(effect.getTranslationKey());
                int color = effect.getColor();
                if (Configs.FeatureConfigs.POTION_EFFECT_HUD_NO_COLOR.getBooleanValue()) {
                    color = 0xffffff;
                }
                int level = statusEffect.getAmplifier();

                StringBuilder out = new StringBuilder();
                if (statusEffect.isAmbient()) out.append("\u221e ");
                out.append(name);
                out.append(" ");
                out.append(level + 1);
                out.append(Formatting.GRAY);
                out.append(" (");
                out.append(StatusEffectUtil.durationToString(statusEffect, 1.0F));
                out.append(")");

                this.client.textRenderer.drawWithShadow(matrixStack, out.toString().trim(),width - this.client.textRenderer.getWidth(out.toString().trim()) - 2, height - 2 - this.client.textRenderer.fontHeight - (this.client.textRenderer.fontHeight*index), color);

                index++;
            }
        }
    }
}
