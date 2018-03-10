package utils;

import com.intellij.openapi.util.io.FileUtil;

public class PathUtil {

    private PathUtil() {
    }

    public static String relativePath(String path1, String path2) {
        if (path1 == null || path2 == null) {
            return "";
        }
        if (path1.lastIndexOf("/") > 0) {
            path1 = path1.substring(0, path1.lastIndexOf("/"));
        }
        return FileUtil.getRelativePath(path1, path2, '/', false);
    }

}
