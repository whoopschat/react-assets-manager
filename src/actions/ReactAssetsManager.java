package actions;

import base.BaseAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.io.FileUtil;
import form.Dialog;
import icons.Icons;
import listener.ICancelListener;
import listener.IConfirmListener;
import utils.ConfigUtil;
import utils.LinkUtil;
import utils.NotificationUtil;
import utils.PathUtil;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReactAssetsManager extends BaseAction implements ICancelListener, IConfirmListener {

    private JFrame mDialog;
    private static final String configName = "react_assets_manager.json";
    private static final String configResourcesDir = "resourcesDir";
    private static final String configResourcesService = "resourcesService";
    private static final String defaultResourcesDir = "/src/res/";
    private static final String defaultResourcesService = "/src/common/resources/resources.js";

    public ReactAssetsManager() {
        super(Icons.React);
    }

    public ReactAssetsManager(Icon icon) {
        super(icon);
    }

    @Override
    public void actionPerformed() {
        showDialog();
    }

    @Override
    public void onCancel() {
        hideDialog();
    }

    @Override
    public void onConfirm(Project project, String mResourcesDir, String mResourcesService) {
        Map<String, String> map = new HashMap<>();
        map.put(configResourcesDir, mResourcesDir);
        map.put(configResourcesService, mResourcesService);
        ConfigUtil.initConfigFromFile(currentProject, configName, map);
        action(mResourcesDir, mResourcesService);
        hideDialog();
    }

    private void action(String mResourcesDir, String mResourcesService) {
        String path = currentProject.getBasePath();
        try {
            List<FileTree> files = fileTree(path + mResourcesDir);
            File file = new File(path + mResourcesService);
            FileUtil.writeToFile(file, "'use strict';\n" +
                    "\n" +
                    "export default " +
                    createResourcesJs("", PathUtil.relativePath(mResourcesService, mResourcesDir), "", files));
            LinkUtil.refreshFile(currentProject, mResourcesService);
            NotificationUtil.showInfoNotification(currentProject, "Create Resources File Success");
        } catch (Exception e) {
            e.printStackTrace();
            NotificationUtil.showErrorNotification(currentProject, "Create Resources File Fail");
        }
    }

    private void showDialog() {
        String resourcesDir = ConfigUtil.getConfigFromFile(currentProject, configName, configResourcesDir);
        String resourcesService = ConfigUtil.getConfigFromFile(currentProject, configName, configResourcesService);
        if (resourcesDir == null) {
            resourcesDir = defaultResourcesDir;
        }
        if (resourcesService == null) {
            resourcesService = defaultResourcesService;
        }
        Dialog panel = new Dialog(currentProject, resourcesDir, resourcesService, this, this);
        mDialog = new JFrame();
        mDialog.setTitle("React Assets Manager");
        mDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mDialog.getRootPane().setDefaultButton(panel.getConfirmButton());
        mDialog.getContentPane().add(panel);
        mDialog.pack();
        mDialog.setLocationRelativeTo(null);
        mDialog.setVisible(true);
    }

    private void hideDialog() {
        if (mDialog == null) {
            return;
        }
        mDialog.setVisible(false);
        mDialog.dispose();
    }


    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////

    private String createResourcesJs(String tab, String relativePath, String key, List<FileTree> files) {
        StringBuilder js = new StringBuilder(tab + "{\n");
        for (FileTree fileTree : files) {
            js.append("\t").append(tab);
            if (fileTree.isTree) {
                js.append(fileTree.key)
                        .append(": ")
                        .append(createResourcesJs(tab + "\t", relativePath, fileTree.key, fileTree.trees))
                        .append(",\n");
            } else {
                js.append(fileTree.key)
                        .append(": ")
                        .append("require('")
                        .append(relativePath)
                        .append(key)
                        .append("/")
                        .append(fileTree.path)
                        .append("'),\n");
            }
        }
        js.append(tab).append("}");
        return js.toString().replaceAll(",\n\t}", "\n\t}").replaceAll("},\n}", "}\n}");
    }


    private final class FileTree {
        String key = "";
        String path = null;
        List<FileTree> trees = null;
        boolean isTree;

        FileTree(String key, String path) {
            this.key = key;
            this.path = path;
            this.isTree = false;
        }

        FileTree(String key, List<FileTree> trees) {
            this.key = key;
            this.trees = trees;
            this.isTree = true;
        }
    }

    private List<FileTree> fileTree(String strPath) {
        List<FileTree> fileTrees = new ArrayList<>();
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileTrees.add(new FileTree(fixName(file.getName()), fileTree(file.getAbsolutePath())));
                } else {
                    fileTrees.add(new FileTree(fixName(file.getName()), file.getName()));
                }
            }
        }
        return fileTrees;
    }

    private String fixName(String name) {
        if (name.indexOf(".") > 0) {
            return name.substring(0, name.indexOf("."));
        }
        return name;
    }

}
