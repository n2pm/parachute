package pm.n2.parachute.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import pm.n2.parachute.config.Configs;

/**
 * @author Cynosphere <gamers@riseup.net>
 */
public class ArmorHUD {
    public static ArmorHUD INSTANCE = new ArmorHUD();
    private final MinecraftClient client;
    private final ItemRenderer itemRenderer;

    public ArmorHUD() {
        this.client = MinecraftClient.getInstance();
        this.itemRenderer = client.getItemRenderer();
    }

    public void render(DrawContext drawContext) {
        if (!Configs.FEATURE_CONFIGS.ARMOR_HUD.getBooleanValue()) return;
        int width = this.client.getWindow().getScaledWidth();
        int height = this.client.getWindow().getScaledHeight();

        ClientPlayerEntity player = this.client.player;
        if (player != null && !player.isCreative() && !player.isSpectator()) {
            int center = width / 2;
            int index = 0;
            int breath = player.getAir();
            int maxBreath = player.getMaxAir();

            EquipmentSlot[] types = new EquipmentSlot[]{
                    EquipmentSlot.HEAD,
                    EquipmentSlot.CHEST,
                    EquipmentSlot.LEGS,
                    EquipmentSlot.FEET
            };

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            for (EquipmentSlot slot : types) {
                ItemStack stack = player.getInventory().armor.get(slot.getEntitySlotId());
                int x = center + (91 / 2) - (5 * 8 - 12) + (16 * index);
                int y = height - 56 - Configs.FEATURE_CONFIGS.ARMOR_HUD_OFFSET.getIntegerValue();
                if (player.isSubmergedIn(FluidTags.WATER) || breath < maxBreath) y -= 10;

                drawContext.drawItem(stack,x,y);
//                drawContext.drawItemTooltip(this.client.textRenderer,stack,x,y);

                index++;
            }

            RenderSystem.disableBlend();
        }
    }
}

