package utils;

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void openFile(Project project, String filename) {
        try {
            VirtualFile virtualFile = project.getBaseDir();
            if (virtualFile != null) {
                virtualFile = virtualFile.findFileByRelativePath(filename);
            }
            if (virtualFile != null) {
                FileEditorManager.getInstance(project).openFile(virtualFile, true);
                virtualFile.refresh(true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void refreshFile(Project project, String filename) {
        try {
            VirtualFile virtualFile = project.getBaseDir();
            if (virtualFile != null) {
                virtualFile = virtualFile.findFileByRelativePath(filename);
            }
            if (virtualFile != null) {
                virtualFile.refresh(true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
