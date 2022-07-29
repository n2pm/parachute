package pm.n2.parachute.mixin;

import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pm.n2.parachute.config.Configs;

@Mixin(BookEditScreen.class)
public class MixinBookEditScreen {
    @Redirect(method = "appendNewPage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/BookEditScreen;countPages()I"))
    private int checkPageLength(BookEditScreen instance) {
        if (Configs.TweakConfigs.NO_BOOK_PAGE_LENGTH.getBooleanValue()) {
            return 0;
        } else {
            return ((IMixinBookEditScreen) instance).getPages().size();
        }
    }
}
