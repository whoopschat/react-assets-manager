package utils;

import com.google.gson.Gson;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utils for find some dirs.
 * Created by beansoft on 2017/3/14.
 */
public class ConfigUtil {

    private static String _IDEA_DIR = ".idea" + File.separator;


    public static void initConfigFromFile(Project project, String configName, Map map) {
        String path = project.getBasePath();
        File ideaFolder = new File(path, _IDEA_DIR);
        if (!ideaFolder.exists()) {
            ideaFolder.mkdirs();
        }
        try {
            File file = new File(path, _IDEA_DIR + configName);
            String json = new Gson().toJson(map, Map.class);
            FileUtil.writeToFile(file, json);
        } catch (IOException e) {
        }
    }

    public static String getConfigFromFile(Project project, String configName, String key) {
        String path = project.getBasePath();
        File file = new File(path, _IDEA_DIR + configName);
        try {
            Map m = new Gson().fromJson(new FileReader(file), Map.class);
            return (String) m.get(key);
        } catch (IOException e) {
            return null;
        }
    }

    public static void saveConfigFromFile(Project project, String configName, String key, String jsAppPath) {
        String path = project.getBasePath();
        File file = new File(path, _IDEA_DIR + configName);
        try {
            Map m = new HashMap();
            m.put(key, jsAppPath);
            String json = new Gson().toJson(m, Map.class);
            FileUtil.writeToFile(file, json);
        } catch (IOException e) {
        }
    }


}