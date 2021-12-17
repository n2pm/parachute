package pm.n2.parachute.util;

import net.minecraft.client.network.ServerInfo;

public class GlobalDataStorage {
    private static final GlobalDataStorage INSTANCE = new GlobalDataStorage();

    public static GlobalDataStorage getInstance()
    {
        return INSTANCE;
    }

    private ServerInfo lastServer;

    public void setLastServer(ServerInfo server) {
        this.lastServer = server;
    }

    public ServerInfo getLastServer() {
        return this.lastServer;
    }
}
