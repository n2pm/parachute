package pm.n2.parachute.mixin;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pm.n2.parachute.config.Configs;

@Mixin(value = StringReader.class, remap = false)
public class MixinStringReader_brigadier {
    private static final char SYNTAX_ESCAPE = '\\';

    /**
     * @author Mary Strodl <ipadlover8322@gmail.com>
     * @reason Backport of https://github.com/Mojang/brigadier/pull/90
     */
    @Inject(method = "readStringUntil", at=@At("HEAD"), cancellable = true, remap = false)
    public void readStringUntil(char terminator, CallbackInfoReturnable<String> cir) throws CommandSyntaxException {
        if (Configs.TweakConfigs.BRIGADIER_STRING_ESCAPES.getBooleanValue()) {
            StringReader self = (StringReader) (Object) this;
            final StringBuilder result = new StringBuilder();
            boolean escaped = false;
            while (self.canRead()) {
                final char c = self.read();
                if (escaped) {
                    Character mogrified = null;
                    if (c == terminator) {
                        mogrified = terminator;
                    } else {
                        switch (c) {
                            case SYNTAX_ESCAPE:
                                mogrified = SYNTAX_ESCAPE;
                                break;
                            case '/':
                                mogrified = '/';
                                break;
                            case 'b':
                                mogrified = '\b';
                                break;
                            case 'f':
                                mogrified = '\f';
                                break;
                            case 'n':
                                mogrified = '\n';
                                break;
                            case 'r':
                                mogrified = '\r';
                                break;
                            case 't':
                                mogrified = '\t';
                                break;
                        }
                    }
                    if (mogrified != null) {
                        result.append(mogrified);
                        escaped = false;
                    } else {
                        self.setCursor(self.getCursor() - 1);
                        throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidEscape().createWithContext(self, String.valueOf(c));
                    }
                } else if (c == SYNTAX_ESCAPE) {
                    escaped = true;
                } else if (c == terminator) {
                    cir.setReturnValue(result.toString());
                    return;
                } else {
                    result.append(c);
                }
            }
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerExpectedEndOfQuote().createWithContext(self);
        }
    }
}