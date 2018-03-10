package form;


import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;

public class EntryInput extends JPanel {

    public interface IChangeListener {
        void changed(String value);
    }

    private IChangeListener mChangeListener;
    private JTextField mValue;

    public EntryInput(String label, String defaultValue, IChangeListener changeListener) {
        mChangeListener = changeListener;
        JLabel mLabel = new JLabel(label);
        mLabel.setPreferredSize(new Dimension(160, 26));
        // mValue
        mValue = new JTextField(defaultValue, 10);
        Document dt = mValue.getDocument();
        dt.addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if (mChangeListener != null) {
                    mChangeListener.changed(mValue.getText());
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if (mChangeListener != null) {
                    mChangeListener.changed(mValue.getText());
                }
            }

            public void changedUpdate(DocumentEvent e) {
                if (mChangeListener != null) {
                    mChangeListener.changed(mValue.getText());
                }
            }
        });
        mValue.setPreferredSize(new Dimension(360, 26));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setMaximumSize(new Dimension(Short.MAX_VALUE, 54));
        add(mLabel);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(mValue);
        add(Box.createHorizontalGlue());
        setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
    }

}
