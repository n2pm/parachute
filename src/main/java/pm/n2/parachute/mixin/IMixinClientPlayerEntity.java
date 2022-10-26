package pm.n2.parachute.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ClientPlayerEntity.class)
public interface IMixinClientPlayerEntity {
    @Invoker("isCamera")
    boolean invokeIsCamera();


}
