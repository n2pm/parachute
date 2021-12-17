package pm.n2.parachute.events;

import fi.dy.masa.malilib.interfaces.IRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.Nullable;
import pm.n2.parachute.render.OverlayRenderer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RenderHandler implements IRenderer {
    private static final RenderHandler INSTANCE = new RenderHandler();

    private final MinecraftClient mc;
    private final Map<ChunkPos, CompletableFuture<WorldChunk>> chunkFutures = new HashMap<>();
    @Nullable
    private WorldChunk cachedClientChunk;


    public RenderHandler() {
        this.mc = MinecraftClient.getInstance();
    }

    public static RenderHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void onRenderWorldLast(MatrixStack matrixStack, Matrix4f projMatrix) {
        if (this.mc.world != null && this.mc.player != null && !this.mc.options.hudHidden) {
            OverlayRenderer.renderOverlays(matrixStack, projMatrix, this.mc);
        }
    }

}
