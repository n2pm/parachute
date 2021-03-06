package pm.n2.parachute.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.include.com.google.common.base.Joiner;
import pm.n2.parachute.Parachute;
import pm.n2.parachute.util.WorldDataStorage;

import java.nio.charset.StandardCharsets;

public class WorldEditCUINetworkHandler {
    private static final Identifier CHANNEL = new Identifier("worldedit:cui");
    private static final int PROTOCOL_VERSION = 4;

    public static void registerReceiver() {
        // TODO: Only register when Configs.FeatureConfigs.WORLDEDIT_CUI is enabled
        ClientPlayNetworking.registerReceiver(CHANNEL, WorldEditCUINetworkHandler::onPacket);
        handshake();
    }

    public static void unregisterReceiver() {
        ClientPlayNetworking.unregisterReceiver(CHANNEL);
    }

    private static void onPacket(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf data, PacketSender packetSender) {
        int readableBytes = data.readableBytes();
        if (readableBytes > 0) {
            String message = data.toString(0, data.readableBytes(), StandardCharsets.UTF_8);
            String[] split = message.split("\\|", -1);
            boolean multi = split[0].startsWith("+");
            String type = split[0].substring(multi ? 1 : 0);
            String[] args = message.substring(type.length() + (multi ? 2 : 1)).split("\\|", -1);
            Parachute.LOGGER.debug("WorldEditCUINetworkHandler#onPacket(): Received CUI packet (" + type + ") with args: " + Joiner.on(", ").join(args));
            if (type.equals("p")) {
                try {
                    if (WorldDataStorage.getInstance().getWorldEditSelectionMode().equals("cuboid")) {
                        WorldDataStorage.getInstance().setWorldEditPos(Integer.parseInt(args[0]), new BlockPos(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])));
                    }
                } catch (NumberFormatException e) {
                    Parachute.LOGGER.warn("WorldEditCUINetworkHandler#onPacket(): Failed int parsing of position");
                    e.printStackTrace();
                }
            }
            if (type.equals("s")) {
                WorldDataStorage.getInstance().setWorldEditSelectionMode(args[0]);
                WorldDataStorage.getInstance().resetWorldEditPos();
            }
        } else {
            Parachute.LOGGER.warn("WorldEditCUINetworkHandler#onPacket(): Received CUI packet of length zero");
        }
    }

    private static void handshake() {
        String message = "v|" + PROTOCOL_VERSION;
        ByteBuf buf = Unpooled.copiedBuffer(message, StandardCharsets.UTF_8);
        ClientPlayNetworking.send(CHANNEL, new PacketByteBuf(buf));
    }
}
