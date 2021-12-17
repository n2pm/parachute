package pm.n2.parachute.mixin;

import pm.n2.parachute.config.TweakConfigs;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BookEditScreen.class)
public class MixinBookEditScreen {
    @Inject(method="method_27596", at=@At("HEAD"), cancellable = true, remap = false)
    private void checkPageLength(String string, CallbackInfoReturnable<Boolean> cir) {
        if (TweakConfigs.TWEAK_IGNORE_BOOK_PAGE_LENGTH.getBooleanValue()) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
