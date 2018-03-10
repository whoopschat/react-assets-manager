package utils;

import com.google.gson.Gson;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigUtil {

    private static String _IDEA_DIR = ".idea" + File.separator;


    public static void initConfigFromFile(Project project, String configName, Map map) {
        String path = project.getBasePath();
        File ideaFolder = new File(path, _IDEA_DIR);
        boolean flag = true;
        if (!ideaFolder.exists()) {
            flag = ideaFolder.mkdirs();
        }
        if (!flag) {
            return;
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

    public static void saveConfigFromFile(Project project, String configName, String key, String value) {
        String path = project.getBasePath();
        File file = new File(path, _IDEA_DIR + configName);
        try {
            Map m = new HashMap();
            m.put(key, value);
            String json = new Gson().toJson(m, Map.class);
            FileUtil.writeToFile(file, json);
        } catch (IOException e) {
        }
    }


}