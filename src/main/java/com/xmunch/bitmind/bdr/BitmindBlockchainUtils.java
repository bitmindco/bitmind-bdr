package com.xmunch.bitmind.bdr;

import org.junit.BeforeClass;

import com.blockcypher.context.BlockCypherContext;
import com.blockcypher.utils.BlockCypherRestfulConstants;

public class BitmindBlockchainUtils {

    protected static final String PRIVATE_KEY = "YOUR_PRIVATE_KEY";
    protected static final String API_TOKEN = "BC_API_TOKEN";

    protected static BlockCypherContext blockCypherContext;

    @BeforeClass
    public static void proxySettings() {
        // proxy only works for @GET, webhooks will NOT work!
        // setProxySettings();
        createBlockCypherContext();
    }

    protected static void createBlockCypherContext() {
        blockCypherContext = new BlockCypherContext(BlockCypherRestfulConstants.VERSION_V1,
                BlockCypherRestfulConstants.CURRENCY_BTC,
                BlockCypherRestfulConstants.NETWORK_MAIN, "BC_API_TOKEN");
    }

    private static void setProxySettings() {
        System.setProperty("http.proxyHost", "myproxy.com");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("http.nonProxyHosts", "localhost|127.*|192.*");
        System.setProperty("https.proxyHost", "myproxy.com");
        System.setProperty("https.proxyPort", "80");
        System.setProperty("https.nonProxyHosts", "localhost|127.*|192.*");
    }

}
