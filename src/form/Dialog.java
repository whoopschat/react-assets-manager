package form;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBScrollPane;
import listener.ICancelListener;
import listener.IConfirmListener;
import org.jetbrains.annotations.NotNull;
import utils.LinkUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Dialog extends JPanel {

    private Project mProject;
    private IConfirmListener mConfirmListener;
    private ICancelListener mCancelListener;
    private String mResourcesDir;
    private String mResourcesService;
    private JButton mConfirm;

    public Dialog(Project project, String resourcesDir, String resourcesService, IConfirmListener confirmListener, ICancelListener cancelListener) {
        mProject = project;
        mResourcesDir = resourcesDir;
        mResourcesService = resourcesService;
        mConfirmListener = confirmListener;
        mCancelListener = cancelListener;
        setPreferredSize(new Dimension(740, 260));
        setMaximumSize(new Dimension(800, 800));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        addContent();
        addButtons();
    }

    private void addContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(getContentPanel());
        add(contentPanel, BorderLayout.CENTER);
    }

    @NotNull
    private JBScrollPane getContentPanel() {
        JPanel injectionsPanel = new JPanel();
        injectionsPanel.setLayout(new BoxLayout(injectionsPanel, BoxLayout.PAGE_AXIS));
        injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        EntryInput resDir = new EntryInput("Resources Folder", mResourcesDir, new EntryInput.IChangeListener() {
            @Override
            public void changed(String value) {
                mResourcesDir = value;
            }
        });
        injectionsPanel.add(resDir);
        injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        EntryInput resJs = new EntryInput("Export Resources File", mResourcesService, new EntryInput.IChangeListener() {
            @Override
            public void changed(String value) {
                mResourcesService = value;
            }
        });
        injectionsPanel.add(resJs);
        injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        EntryAction help = new EntryAction("Actions", "Help", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LinkUtil.openUrl("https://github.com/whoopschat/react-assets-manager");
            }
        });
        injectionsPanel.add(help);
        injectionsPanel.add(Box.createVerticalGlue());
        injectionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        return new JBScrollPane(injectionsPanel);
    }

    private void addButtons() {

        JButton mCancel = new JButton();
        mCancel.setAction(new CancelAction());
        mCancel.setPreferredSize(new Dimension(120, 26));
        mCancel.setText("Cancel");
        mCancel.setVisible(true);

        mConfirm = new JButton();
        mConfirm.setAction(new ConfirmAction());
        mConfirm.setPreferredSize(new Dimension(120, 26));
        mConfirm.setText("Confirm");
        mConfirm.setVisible(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(mCancel);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(mConfirm);
        add(buttonPanel, BorderLayout.PAGE_END);
    }

    public JButton getConfirmButton() {
        return mConfirm;
    }

    private class ConfirmAction extends AbstractAction {
        public void actionPerformed(ActionEvent event) {
            if (mConfirmListener != null) {
                mConfirmListener.onConfirm(mProject, mResourcesDir, mResourcesService);
            }
        }
    }

    private class CancelAction extends AbstractAction {
        public void actionPerformed(ActionEvent event) {
            if (mCancelListener != null) {
                mCancelListener.onCancel();
            }
        }
    }
}
