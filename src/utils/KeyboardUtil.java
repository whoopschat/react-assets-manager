package utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class KeyboardUtil {

    private KeyboardUtil() {
    }

    public static void input(Robot r, int key) {
        if (r == null) {
            return;
        }
        r.keyPress(KeyEvent.VK_CONTROL);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyRelease(KeyEvent.VK_CONTROL);
        r.delay(100);
    }

}
