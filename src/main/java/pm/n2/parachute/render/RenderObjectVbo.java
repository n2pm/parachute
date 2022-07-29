package pm.n2.parachute.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormatElement;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

import java.util.function.Supplier;

public class RenderObjectVbo extends RenderObjectBase {
    protected final VertexBuffer vertexBuffer;
    protected final VertexFormat format;
    protected final boolean hasTexture;

    public RenderObjectVbo(VertexFormat.DrawMode glMode, VertexFormat format, Supplier<Shader> shader) {
        super(glMode, shader);

        this.vertexBuffer = new VertexBuffer();
        this.format = format;

        boolean hasTexture = false;

        // This isn't really that nice and clean, but it'll do for now...
        for (VertexFormatElement el : this.format.getElements()) {
            if (el.getType() == VertexFormatElement.Type.UV) {
                hasTexture = true;
                break;
            }
        }

        this.hasTexture = hasTexture;
    }

    @Override
    public void uploadData(BufferBuilder buffer) {
        this.vertexBuffer.bind();
        this.vertexBuffer.upload(buffer.end());
        VertexBuffer.unbind();
    }

    @Override
    public void draw(MatrixStack matrixStack, Matrix4f projMatrix) {
        if (this.hasTexture) {
            RenderSystem.enableTexture();
        }

        RenderSystem.setShader(this.getShader());
        this.vertexBuffer.bind();
        this.vertexBuffer.draw(matrixStack.peek().getPositionMatrix(), projMatrix, this.getShader().get());
        VertexBuffer.unbind();

        if (this.hasTexture) {
            RenderSystem.disableTexture();
        }
    }

    @Override
    public void deleteGlResources() {
        this.vertexBuffer.close();
    }
}
