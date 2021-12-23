package pm.n2.parachute.util;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FakeCommandSource implements CommandSource {
    private final MinecraftClient client;
    private final ChatHud chatHud;
    private final ClientPlayerEntity player;

    public FakeCommandSource(MinecraftClient client) {
        this.client = client;
        this.player = this.client.player;
        this.chatHud = this.client.inGameHud.getChatHud();
    }

    public void sendFeedback(Text message) {
        this.chatHud.addMessage(message);
    }

    public void sendError(Text message) {
        this.chatHud.addMessage(new LiteralText("").append(message).formatted(Formatting.RED));
    }

    public MinecraftClient getClient() {
        return this.client;
    }

    public ClientPlayerEntity getPlayer() {
        return this.player;
    }

    public Vec3d getPosition() {
        return this.player.getPos();
    }

    @Override
    public Collection<String> getPlayerNames() {
        return client.getNetworkHandler().getPlayerList().stream().map(e -> e.getProfile().getName()).collect(Collectors.toList());
    }

    @Override
    public Collection<String> getTeamNames() {
        return null;
    }

    @Override
    public Collection<Identifier> getSoundIds() {
        return null;
    }

    @Override
    public Stream<Identifier> getRecipeIds() {
        return null;
    }

    @Override
    public CompletableFuture<Suggestions> getCompletions(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
        return null;
    }

    @Override
    public Set<RegistryKey<World>> getWorldKeys() {
        return null;
    }

    @Override
    public DynamicRegistryManager getRegistryManager() {
        return null;
    }

    @Override
    public boolean hasPermissionLevel(int level) {
        return true;
    }
}