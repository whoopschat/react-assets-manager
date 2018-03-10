package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * Icons used by plugin.
 * Created by beansoft on 17/4/1.
 */
public class Icons {

    public static final Icon React = load("/icons/ReactNative.png");

    private static Icon load(String path) {
        try {
            return IconLoader.getIcon(path, Icons.class);
        } catch (IllegalStateException e) {
            return null;
        }
    }


}
