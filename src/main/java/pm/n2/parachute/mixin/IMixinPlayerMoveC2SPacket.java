package pm.n2.parachute.mixin;

import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerMoveC2SPacket.class)
public interface IMixinPlayerMoveC2SPacket {
    @Accessor("x")
    void setX(double d);

    @Accessor("z")
    void setZ(double d);
}
