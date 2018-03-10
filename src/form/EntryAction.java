package form;


import utils.LinkUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EntryAction extends JPanel {

    public interface IChangeListener {
        void changed(String value);
    }

    public EntryAction(String label, String name, AbstractAction abstractAction) {
        JLabel mLabel = new JLabel(label);
        mLabel.setPreferredSize(new Dimension(160, 26));
        JButton mButton = new JButton();
        mButton.setAction(abstractAction);
        mButton.setPreferredSize(new Dimension(120, 26));
        mButton.setText(name);
        mButton.setVisible(true);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setMaximumSize(new Dimension(Short.MAX_VALUE, 54));
        add(mLabel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(mButton);
        add(Box.createHorizontalGlue());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

}
