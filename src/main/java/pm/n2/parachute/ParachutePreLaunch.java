package pm.n2.parachute;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.quiltmc.loader.impl.util.UrlConversionException;
import org.quiltmc.loader.impl.util.UrlUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ParachutePreLaunch implements PreLaunchEntrypoint {
    // Taken from https://github.com/kb-1000/no-telemetry/blob/main/src/main/java/de/kb1000/notelemetry/NoTelemetry.java

    private static final String[] libraryMixinTargets = {
            "com/mojang/authlib/yggdrasil/YggdrasilMinecraftSessionService.class",
            "com/mojang/authlib/yggdrasil/YggdrasilUserApiService.class",
            "com/mojang/brigadier/StringReader.class"
    };


    @Override
    public void onPreLaunch() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            Method m = classLoader.getClass().getMethod("addUrlFwd", URL.class);
            m.setAccessible(true);
            for (String mixinTarget : libraryMixinTargets) {
                try {
                    m.invoke(classLoader, getSource(classLoader.getParent().getParent().getParent(), mixinTarget).orElseThrow());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | NoSuchElementException e) {
            e.printStackTrace();
        }
    }
    private static Optional<URL> getSource(ClassLoader loader, String filename) throws MalformedURLException {
        URL url;
        if ((url = loader.getResource(filename)) != null) {
            try {
                URL urlSource = UrlUtil.getSource(filename, url);
                return Optional.of(urlSource);
            } catch (UrlConversionException e) {
                e.printStackTrace();
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
