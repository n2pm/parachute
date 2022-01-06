package pm.n2.parachute.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;
import pm.n2.parachute.config.Configs;

public class DebugRenderers {
    public static void renderVanillaDebug(MatrixStack matrixStack, VertexConsumerProvider.Immediate vertexConsumer, double camX, double camY, double camZ) {
        DebugRenderer renderer = MinecraftClient.getInstance().debugRenderer;

        if (Configs.DebugRendererConfigs.PATHFINDING.getBooleanValue()) {
            renderer.pathfindingDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.WATER.getBooleanValue()) {
            renderer.waterDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.HEIGHTMAP.getBooleanValue()) {
            renderer.heightmapDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.COLLISION.getBooleanValue()) {
            renderer.collisionDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.NEIGHBOR_UPDATE.getBooleanValue()) {
            renderer.neighborUpdateDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.STRUCTURE.getBooleanValue()) {
            renderer.structureDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.SKY_LIGHT.getBooleanValue()) {
            renderer.skyLightDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.WORLD_GEN_ATTEMPT.getBooleanValue()) {
            renderer.worldGenAttemptDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.BLOCK_OUTLINE.getBooleanValue()) {
            renderer.blockOutlineDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.CHUNK_LOADING.getBooleanValue()) {
            renderer.chunkLoadingDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.VILLAGE.getBooleanValue()) {
            renderer.villageDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.VILLAGE_SECTIONS.getBooleanValue()) {
            renderer.villageSectionsDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.BEE.getBooleanValue()) {
            renderer.beeDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.RAID_CENTER.getBooleanValue()) {
            renderer.raidCenterDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.GOAL_SELECTOR.getBooleanValue()) {
            renderer.goalSelectorDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }

        if (Configs.DebugRendererConfigs.GAME_EVENT.getBooleanValue()) {
            renderer.gameEventDebugRenderer.render(matrixStack, vertexConsumer, camX, camY, camZ);
        }
    }
}
