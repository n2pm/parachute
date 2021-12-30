package pm.n2.parachute.render;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class OverlayRendererBase implements IOverlayRenderer {
    protected static final BufferBuilder BUFFER_QUADS = new BufferBuilder(2097152);
    protected static final BufferBuilder BUFFER_LINES = new BufferBuilder(2097152);
    //protected static final BufferBuilder BUFFER_3 = new BufferBuilder(2097152);

    protected final List<RenderObjectBase> renderObjects = new ArrayList<>();
    protected boolean renderThrough = false;
    protected float glLineWidth = 1f;
    protected BlockPos lastUpdatePos = BlockPos.ORIGIN;
    private Vec3d updateCameraPos = Vec3d.ZERO;

    @Override
    public final Vec3d getUpdatePosition() {
        return this.updateCameraPos;
    }

    @Override
    public final void setUpdatePosition(Vec3d cameraPosition) {
        this.updateCameraPos = cameraPosition;
    }

    protected void preRender() {
        RenderSystem.lineWidth(this.glLineWidth);

        if (this.renderThrough) {
            RenderSystem.disableDepthTest();
            //RenderSystem.depthMask(false);
        }
    }

    protected void postRender() {
        if (this.renderThrough) {
            RenderSystem.enableDepthTest();
            //RenderSystem.depthMask(true);
        }
    }

    @Override
    public void draw(MatrixStack matrixStack, Matrix4f projMatrix) {
        this.preRender();

        for (RenderObjectBase obj : this.renderObjects) {
            obj.draw(matrixStack, projMatrix);
        }

        this.postRender();
    }

    @Override
    public void deleteGlResources() {
        for (RenderObjectBase obj : this.renderObjects) {
            obj.deleteGlResources();
        }

        this.renderObjects.clear();
    }

    /**
     * Allocates a new VBO or display list, adds it to the list, and returns it
     *
     * @param glMode
     */
    protected void allocateBuffer(VertexFormat.DrawMode glMode, VertexFormat format, Supplier<Shader> shader) {
        RenderObjectBase obj = new RenderObjectVbo(glMode, format, shader);
        this.renderObjects.add(obj);
    }

    @Override
    public void allocateGlResources() {
        this.allocateBuffer(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorShader);
        // this.allocateBuffer(VertexFormat.DrawMode.LINES, VertexFormats.LINES, GameRenderer::getRenderTypeLinesShader);
        this.allocateBuffer(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorShader);
    }

    public void setRenderThrough(boolean renderThrough) {
        this.renderThrough = renderThrough;
    }

    public void setGlLineWidth(float lineWidth) {
        this.glLineWidth = lineWidth;
    }

    public String getSaveId() {
        return "";
    }

    @Nullable
    public JsonObject toJson() {
        return null;
    }

    public void fromJson(JsonObject obj) {
    }
}

