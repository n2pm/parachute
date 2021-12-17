package pm.n2.parachute.mixin;

import pm.n2.parachute.config.TweakConfigs;
import net.minecraft.client.network.Address;
import net.minecraft.client.network.AllowedAddressResolver;
import net.minecraft.client.network.BlockListChecker;
import net.minecraft.client.network.ServerAddress;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.function.Predicate;

@Mixin(AllowedAddressResolver.class)
public class MixinAllowedAddressResolver {
    @Final
    @Shadow private BlockListChecker blockListChecker;

    @Redirect(method = "resolve", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/network/BlockListChecker;isAllowed(Lnet/minecraft/client/network/Address;)Z"))
    private boolean bypassServerBanCheckAddress(BlockListChecker instance, Address address) {
        if (TweakConfigs.TWEAK_BYPASS_SERVER_BLOCKIST.getBooleanValue()) {
            return true;
        }
        return instance.isAllowed(address);
    }

    @Redirect(method = "resolve", at=@At(value = "INVOKE", target = "Lnet/minecraft/client/network/BlockListChecker;isAllowed(Lnet/minecraft/client/network/ServerAddress;)Z"))
    private boolean bypassServerBanCheckServerAddress(BlockListChecker instance, ServerAddress address) {
        if (TweakConfigs.TWEAK_BYPASS_SERVER_BLOCKIST.getBooleanValue()) {
            return true;
        }
        return instance.isAllowed(address);
    }

    @Redirect(method = "resolve", at=@At(value = "INVOKE", target = "Ljava/util/Optional;filter(Ljava/util/function/Predicate;)Ljava/util/Optional;"))
    private <T> Optional<Address> bypassServerBanCheckResolvedAddress(Optional<Address> instance, Predicate<? super T> predicate) {
        if (TweakConfigs.TWEAK_BYPASS_SERVER_BLOCKIST.getBooleanValue()) {
            return instance;
        }
        return instance.filter(blockListChecker::isAllowed);
    }
}
