package pm.n2.parachute.render;

import fi.dy.masa.malilib.util.IntBoundingBox;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.util.WorldDataStorage;

public class OverlayRendererWorldEditCUI extends OverlayRendererBase {
    protected static boolean needsUpdate = true;

    @Override
    public boolean shouldRender(MinecraftClient mc) {
        return Configs.FeatureConfigs.WORLDEDIT_CUI.getBooleanValue();
    }

    @Override
    public boolean needsUpdate(Entity entity, MinecraftClient mc) {
        if (needsUpdate) {
            return true;
        }

        int ex = (int) Math.floor(entity.getX());
        int ez = (int) Math.floor(entity.getZ());
        int lx = this.lastUpdatePos.getX();
        int lz = this.lastUpdatePos.getZ();

        return (ex >> 9) != (lx >> 9) || (ez >> 9) != (lz >> 9) || Math.abs(lx - ex) > 16 || Math.abs(lz - ez) > 16;
    }

    @Override
    public void update(Vec3d cameraPos, MatrixStack matrixStack, Entity entity, MinecraftClient mc) {
        setGlLineWidth(6.0f);
        RenderObjectBase renderQuads = this.renderObjects.get(0);
        RenderObjectBase renderLines = this.renderObjects.get(1);
        BUFFER_QUADS.begin(renderQuads.getGlMode(), VertexFormats.POSITION_COLOR);
        BUFFER_LINES.begin(renderLines.getGlMode(), VertexFormats.LINES);
        BlockPos[] pos = WorldDataStorage.getInstance().getWorldEditPos();
        if (pos[0] != null && pos[1] != null) {
            // I'm lazy
            int minX = Math.min(pos[0].getX(), pos[1].getX());
            int minY = Math.min(pos[0].getY(), pos[1].getY());
            int minZ = Math.min(pos[0].getZ(), pos[1].getZ());
            int maxX = Math.max(pos[0].getX(), pos[1].getX());
            int maxY = Math.max(pos[0].getY(), pos[1].getY());
            int maxZ = Math.max(pos[0].getZ(), pos[1].getZ());
            IntBoundingBox box = new IntBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
            RenderUtils.drawBox(box, cameraPos, RenderColors.TRANSPARENT_WHITE, RenderColors.OUTLINE_WHITE, BUFFER_QUADS, BUFFER_LINES);
        }
        if (pos[0] != null)
            RenderUtils.drawBlockBoundingBoxOutlinesBatchedLines(pos[0], cameraPos, RenderColors.OUTLINE_RED, 0, BUFFER_LINES);
        if (pos[1] != null)
            RenderUtils.drawBlockBoundingBoxOutlinesBatchedLines(pos[1], cameraPos, RenderColors.OUTLINE_BLUE, 0, BUFFER_LINES);
        BUFFER_QUADS.end();
        BUFFER_LINES.end();
        renderQuads.uploadData(BUFFER_QUADS);
        renderLines.uploadData(BUFFER_LINES);
    }
}
