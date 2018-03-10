package listener;

import com.intellij.openapi.project.Project;

public interface IConfirmListener {

    void onConfirm(Project project, String mResourcesDir, String mResourcesService);

}
