package utils;

import com.intellij.openapi.util.SystemInfo;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public final class LinkUtil {

    private LinkUtil() {
    }

    public static void openUrl(String url) {
        if (SystemInfo.isWindows) {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                URI uri = new URI(url);
                Desktop desktop = null;
                if (Desktop.isDesktopSupported()) {
                    desktop = Desktop.getDesktop();
                }
                if (desktop != null)
                    desktop.browse(uri);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
