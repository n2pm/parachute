package pm.n2.parachute.render;

import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.IntBoundingBox;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RenderUtils extends fi.dy.masa.malilib.render.RenderUtils {

    public static void drawBox(IntBoundingBox bb, Vec3d cameraPos, Color4f fillColor, Color4f outlineColor, BufferBuilder bufferQuads, BufferBuilder bufferLines) {
        double minX = bb.minX - cameraPos.x;
        double minY = bb.minY - cameraPos.y;
        double minZ = bb.minZ - cameraPos.z;
        double maxX = bb.maxX + 1 - cameraPos.x;
        double maxY = bb.maxY + 1 - cameraPos.y;
        double maxZ = bb.maxZ + 1 - cameraPos.z;

        drawBoxAllSidesBatchedQuads(minX, minY, minZ, maxX, maxY, maxZ, fillColor, bufferQuads);
        drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, outlineColor, bufferLines);
    }

    public static void drawBlockBoundingBoxOutlinesBatchedLines(BlockPos pos, Vec3d cameraPos, Color4f color, double expand, BufferBuilder buffer) {
        double minX = pos.getX() - expand - cameraPos.x;
        double minY = pos.getY() - expand - cameraPos.y;
        double minZ = pos.getZ() - expand - cameraPos.z;
        double maxX = pos.getX() + expand + 1 - cameraPos.x;
        double maxY = pos.getY() + expand + 1 - cameraPos.y;
        double maxZ = pos.getZ() + expand + 1 - cameraPos.z;
        drawBoxAllEdgesBatchedLines(minX, minY, minZ, maxX, maxY, maxZ, color, buffer);
    }

    public static void drawBoxAllEdgesBatchedLines(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, Color4f color, BufferBuilder buffer) {
        // West side
        drawLine(minX, minY, minZ, minX, minY, maxZ, color, buffer);
        drawLine(minX, minY, maxZ, minX, maxY, maxZ, color, buffer);
        drawLine(minX, maxY, maxZ, minX, maxY, minZ, color, buffer);
        drawLine(minX, maxY, minZ, minX, minY, minZ, color, buffer);

        // East side
        drawLine(maxX, minY, maxZ, maxX, minY, minZ, color, buffer);
        drawLine(maxX, minY, minZ, maxX, maxY, minZ, color, buffer);
        drawLine(maxX, maxY, minZ, maxX, maxY, maxZ, color, buffer);
        drawLine(maxX, maxY, maxZ, maxX, minY, maxZ, color, buffer);

        // North side (don't repeat the vertical lines that are done by the east/west sides)
        drawLine(maxX, minY, minZ, minX, minY, minZ, color, buffer);
        drawLine(minX, maxY, minZ, maxX, maxY, minZ, color, buffer);

        // South side (don't repeat the vertical lines that are done by the east/west sides)
        drawLine(minX, minY, maxZ, maxX, minY, maxZ, color, buffer);
        drawLine(maxX, maxY, maxZ, minX, maxY, maxZ, color, buffer);
    }

    public static void drawLine(double startX, double startY, double startZ, double endX, double endY, double endZ, Color4f color, BufferBuilder buffer) {
        float lenX = (float) (endX - startX);
        float lenY = (float) (endY - startY);
        float lenZ = (float) (endZ - startZ);
        float distance = (float) Math.sqrt(lenX * lenX + lenY * lenY + lenZ * lenZ);
        lenX /= distance;
        lenY /= distance;
        lenZ /= distance;

        buffer.vertex(startX, startY, startZ).color(color.r, color.g, color.b, color.a).normal(lenX, lenY, lenZ).next();
        buffer.vertex(endX, endY, endZ).color(color.r, color.g, color.b, color.a).normal(lenX, lenY, lenZ).next();
    }

}
