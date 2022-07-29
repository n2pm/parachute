package pm.n2.parachute.render;

import com.adryd.cauldron.api.render.helper.BufferBuilderProxy;
import com.adryd.cauldron.api.render.helper.OverlayRendererBase;
import com.adryd.cauldron.api.render.helper.RenderObject;
import com.adryd.cauldron.api.render.util.LineDrawing;
import com.adryd.cauldron.api.render.util.QuadDrawing;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import pm.n2.parachute.config.Configs;
import pm.n2.parachute.util.WorldDataStorage;

public class OverlayRendererWorldEditCUI extends OverlayRendererBase {
    protected boolean shouldUpdate = true;

    public OverlayRendererWorldEditCUI() {
        this.shouldUpdate = true;
        this.renderObjects.add(new RenderObject(VertexFormat.DrawMode.LINES, VertexFormats.LINES, GameRenderer::getRenderTypeLinesShader));
        this.renderObjects.add(new RenderObject(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorShader));
    }

    @Override
    public boolean shouldRender() {
        return Configs.FeatureConfigs.WORLDEDIT_CUI.getBooleanValue();
    }

    @Override
    public boolean shouldUpdate() {
        return this.shouldUpdate;
    }

    @Override
    public void update(MatrixStack matrixStack, Camera camera, float tickDelta) {
        RenderObject renderLines = this.renderObjects.get(0);
        RenderObject renderQuads = this.renderObjects.get(1);

        BufferBuilderProxy linesBuf = renderLines.startBuffer();
        BufferBuilderProxy quadsBuf = renderQuads.startBuffer();

        BlockPos[] pos = WorldDataStorage.getInstance().getWorldEditPos();
        if (pos[0] != null && pos[1] != null) {
            // I'm lazy
            int minX = Math.min(pos[0].getX(), pos[1].getX());
            int minY = Math.min(pos[0].getY(), pos[1].getY());
            int minZ = Math.min(pos[0].getZ(), pos[1].getZ());
            int maxX = Math.max(pos[0].getX(), pos[1].getX()) + 1;
            int maxY = Math.max(pos[0].getY(), pos[1].getY()) + 1;
            int maxZ = Math.max(pos[0].getZ(), pos[1].getZ()) + 1;
            Box box = new Box(minX, minY, minZ, maxX, maxY, maxZ);
            LineDrawing.drawBox(box, RenderColors.OUTLINE_WHITE, linesBuf);
            QuadDrawing.drawBox(box, RenderColors.TRANSPARENT_WHITE, quadsBuf);
        }
        if (pos[0] != null)
            LineDrawing.drawBox(new Box(pos[0]), RenderColors.OUTLINE_RED, linesBuf);
        if (pos[1] != null)
            LineDrawing.drawBox(new Box(pos[1]), RenderColors.OUTLINE_BLUE, linesBuf);

        renderLines.endBuffer();
        renderQuads.endBuffer();
    }
}
