package pm.n2.parachute.render;

import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.IntBoundingBox;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import pm.n2.parachute.config.TweakConfigs;
import pm.n2.parachute.util.WorldDataStorage;

public class OverlayRendererWorldEditCUI extends OverlayRendererBase {
    protected static boolean needsUpdate = true;

    private final Color4f white = new Color4f(1f, 1f, 1f, 0.25f);
    private final Color4f red = new Color4f(1f, 0f, 0f, 1f);
    private final Color4f blue = new Color4f(0f, 0f, 1f, 1f);

    @Override
    public boolean shouldRender(MinecraftClient mc) {
        return TweakConfigs.TWEAK_WORLDEDIT_CUI.getBooleanValue();
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
    public void update(Vec3d cameraPos, Entity entity, MinecraftClient mc) {
        RenderObjectBase renderQuads = this.renderObjects.get(0);
        RenderObjectBase renderLines = this.renderObjects.get(1);
        BUFFER_1.begin(renderQuads.getGlMode(), VertexFormats.POSITION_COLOR);
        BUFFER_2.begin(renderLines.getGlMode(), VertexFormats.POSITION_COLOR);
        BlockPos[] pos = WorldDataStorage.getInstance().getWorldEditPos();
        if (pos[0] != null && pos[1] != null) {
            int minX = Math.min(pos[0].getX(), pos[1].getX());
            int minY = Math.min(pos[0].getY(), pos[1].getY());
            int minZ = Math.min(pos[0].getZ(), pos[1].getZ());
            int maxX = Math.max(pos[0].getX(), pos[1].getX());
            int maxY = Math.max(pos[0].getY(), pos[1].getY());
            int maxZ = Math.max(pos[0].getZ(), pos[1].getZ());
            BlockBox box = new BlockBox(minX, minY, minZ, maxX, maxY, maxZ);
            fi.dy.masa.malilib.render.RenderUtils.drawBox(IntBoundingBox.fromVanillaBox(box), cameraPos, white, BUFFER_1, BUFFER_2);
        }
        if (pos[0] != null)
            fi.dy.masa.malilib.render.RenderUtils.drawBlockBoundingBoxOutlinesBatchedLines(pos[0], cameraPos, red, 0.04, BUFFER_2);
        if (pos[1] != null)
            fi.dy.masa.malilib.render.RenderUtils.drawBlockBoundingBoxOutlinesBatchedLines(pos[1], cameraPos, blue, 0.05, BUFFER_2);
        BUFFER_1.end();
        BUFFER_2.end();
        renderQuads.uploadData(BUFFER_1);
        renderLines.uploadData(BUFFER_2);
    }
}
