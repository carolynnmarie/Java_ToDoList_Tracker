package io.carolynn.todolisttracker;


import static java.lang.Boolean.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import javax.swing.text.BadLocationException;
import javax.swing.GroupLayout.*;


public class Demo extends JFrame implements DocumentListener {

    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JTextArea textArea;

    private static final String COMMIT_ACTION = "commit";
    private final List<String> words;

    private enum Mode { INSERT, COMPLETION }
    private Mode mode;


    public Demo() {
        super("TextAreaDemo");
        this.words = new ArrayList<>(Arrays.asList("spark","special","spectacles","spectacular","swing"));
        this.mode = Mode.INSERT;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.jLabel1 = new JLabel("Try typing 'spectacular' or 'Swing'...");
        this.textArea = new JTextArea();
        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setWrapStyleWord(true);
        this.jScrollPane1 = new JScrollPane(textArea);

        initComponents();

        textArea.getDocument().addDocumentListener(this);
        InputMap inputMap = textArea.getInputMap();
        ActionMap actionMap = textArea.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        actionMap.put(COMMIT_ACTION, new CommitAction());


    }


    private void initComponents() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        ParallelGroup parallelGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

        SequentialGroup sequentialGroup = layout.createSequentialGroup();
        ParallelGroup parallelGroup2 = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);

        parallelGroup2.addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);
        parallelGroup2.addComponent(jLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE);

        sequentialGroup.addContainerGap();
        sequentialGroup.addGroup(parallelGroup2);
        sequentialGroup.addContainerGap();

        parallelGroup.addGroup(Alignment.TRAILING,sequentialGroup);
        layout.setHorizontalGroup(parallelGroup);

        ParallelGroup vGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING);

        SequentialGroup v1 = layout.createSequentialGroup();
        v1.addContainerGap();
        v1.addComponent(jLabel1);
        v1.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED);
        v1.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE);
        v1.addContainerGap();

        vGroup.addGroup(v1);

        layout.setVerticalGroup(vGroup);
        pack();
    }

        // Listener methods

    public void changedUpdate(DocumentEvent ev) {
    }

    public void removeUpdate(DocumentEvent ev) {
    }

    public void insertUpdate(DocumentEvent documentEvent) {
        if (documentEvent.getLength() != 1) {
            return;
        }
        int position = documentEvent.getOffset();
        String content = null;
        try {
            content = textArea.getText(0, position + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        int i;
        for (i = position; i >= 0; i--) {
            if (! Character.isLetter(content.charAt(i))) {
                break;
            }
        }
        if (position - i < 2) {
            return;
        }
        String prefix = content.substring(i + 1).toLowerCase();
        int j = Collections.binarySearch(words, prefix);
        if (j < 0 && -j <= words.size()) {
            String match = words.get(-j - 1);
            if (match.startsWith(prefix)) {
                String completion = match.substring(position - i);
                SwingUtilities.invokeLater(new CompletionTask(completion, position + 1));
            }
        } else {
            mode = Mode.INSERT;
        }
    }

    private class CompletionTask implements Runnable {
        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            textArea.insert(completion, position);
            textArea.setCaretPosition(position + completion.length());
            textArea.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction {
        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = textArea.getSelectionEnd();
                textArea.insert(" ", pos);
                textArea.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                textArea.replaceSelection("\n");
            }
        }
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", FALSE);
                new Demo().setVisible(true);
            }
        });
    }


}
