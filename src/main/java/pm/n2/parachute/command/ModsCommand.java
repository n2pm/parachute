package pm.n2.parachute.command;

import com.adryd.cauldron.api.command.CauldronClientCommandSource;
import com.adryd.cauldron.api.command.ClientCommandManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.CustomValue.CvType;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModsCommand {
    public static Text getInformationText(ModMetadata modMeta) {
        return Texts.bracketed(new LiteralText(modMeta.getId())).styled((style) -> {
            Style temp = style.withColor(Formatting.GREEN)
                    .withInsertion(StringArgumentType.escapeIfRequired(modMeta.getName()))
                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new LiteralText("")).append(modMeta.getName()).append("\n").append(modMeta.getVersion().toString())));
            if (modMeta.getContact().get("sources").isPresent()) {
                return temp.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, modMeta.getContact().get("sources").get()));
            }
            return temp;
        });
    }

    public static boolean shouldShow(ModMetadata modMeta, boolean showAll) {
        String modId = modMeta.getId();
        if (modId.equals("java")) return false;
        if (showAll) return true;
        if (
                modMeta.containsCustomValue("fabric-loom:generated")
                        && modMeta.getCustomValue("fabric-loom:generated").getType() == CvType.BOOLEAN
                        && modMeta.getCustomValue("fabric-loom:generated").getAsBoolean()
        ) return false;
        if (modId.equals("minecraft")) return false;
        if (modId.startsWith("fabric-") && !modId.equals("fabric-api")) return false;
        return true;
    }

    public static void register(CommandDispatcher<CauldronClientCommandSource> dispatcher) {
        LiteralArgumentBuilder<CauldronClientCommandSource> command = ClientCommandManager.literal("mods")
                .then(
                        ClientCommandManager.literal("all")
                                .executes((context) -> execute(context.getSource(), true))
                ).executes((context) -> execute(context.getSource(), false));
        dispatcher.register(command);
    }

    public static int execute(CauldronClientCommandSource source, boolean showAll) {
        // New arraylist because for some reason .toList makes an immutable collection
        List<? extends Text> mods = new ArrayList<>(
                FabricLoader
                        .getInstance()
                        .getAllMods()
                        .stream()
                        .filter((modContainer) -> shouldShow(modContainer.getMetadata(), showAll))
                        .map((modContainer) -> getInformationText(modContainer.getMetadata()))
                        .toList()
        );

        // Sort alphabetically
        mods.sort(Comparator.comparing(Text::getString));

        source.sendFeedback(new LiteralText("There are " + mods.size() + " mods loaded" + (showAll ? "" : "*") + ": ").append(Texts.join(mods, new LiteralText(", "))));
        return 1;
    }
}