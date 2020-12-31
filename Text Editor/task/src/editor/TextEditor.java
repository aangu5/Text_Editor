package editor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextEditor extends JFrame {

    private JTextField searchField;
    private JButton saveButton;
    private JButton loadButton;
    private final DocumentListener fileNameListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateButtons();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateButtons();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateButtons();
        }

        private void updateButtons() {

            boolean enable = !searchField.getText().trim().isEmpty();
            saveButton.setEnabled(enable);
            loadButton.setEnabled(enable);
        }
    };
    private JTextArea textArea;
    private JButton startSearchButton;
    private JButton previousMatchButton;
    private JButton nextMatchButton;
    private JCheckBox useRegexCheckbox;
    private boolean useRegex;
    private final ActionListener startSearchListener = actionEvent -> {
        String input = searchField.getText();
        String searchContent = textArea.getText();
        int index, end;

        if (useRegex) {
            Pattern p = Pattern.compile(input);
            Matcher m = p.matcher(searchContent);
            boolean b = m.find();
            index = m.start();
            end = m.end();
        } else {
            index = searchContent.indexOf(input);
            end = index + input.length();
        }
        textArea.setCaretPosition(end);
        textArea.select(index, end);
        textArea.grabFocus();

    };
    private final ActionListener nextMatchListener = actionEvent -> {
        int currentCursorLocation = textArea.getCaretPosition();
        String input = searchField.getText();
        String searchContent = textArea.getText();
        int index, end;

        if (useRegex) {
            String regexString;
            Pattern p = Pattern.compile(input);
            Matcher m;
            try {
                regexString = searchContent.substring(currentCursorLocation);
                m = p.matcher(regexString);
                boolean b = m.find();
                index = m.start() + currentCursorLocation;
                end = m.end() + currentCursorLocation;
            } catch (IllegalStateException e) {
                regexString = searchContent;
                m = p.matcher(regexString);
                boolean b = m.find();
                index = m.start();
                end = m.end();
            }
        } else {
            index = searchContent.indexOf(input, currentCursorLocation);
            if (index == -1) {
                index = searchContent.indexOf(input, searchContent.length());
            }
            end = index + input.length();
        }
        if (index != -1) {
            textArea.setCaretPosition(end);
            textArea.select(index, end);
            textArea.grabFocus();
        }
    };
    private final ActionListener previousMatchListener = actionEvent -> {
        int currentCursorLocation = textArea.getCaretPosition();
        String input = searchField.getText();
        String searchContent = textArea.getText();
        int index, end;

        if (useRegex) {
            String regexString;
            Pattern p = Pattern.compile(input);
            Matcher m;
            try {
                regexString = searchContent.substring(0, currentCursorLocation - input.length() - 1);
                m = p.matcher(regexString);
                boolean b = m.find();
                index = m.start();
                end = m.end();
            } catch (IllegalStateException e) {
                regexString = searchContent.substring(currentCursorLocation);
                m = p.matcher(regexString);
                boolean b = m.find();
                index = m.start() + currentCursorLocation;
                end = m.end() + currentCursorLocation;
            }
        } else {
            index = searchContent.lastIndexOf(input, currentCursorLocation - input.length() - 1);
            if (index == -1) {
                index = searchContent.lastIndexOf(input);
            }
            end = index + input.length();
        }
        if (index != -1) {
            textArea.setCaretPosition(end);
            textArea.select(index, end);
            textArea.grabFocus();
        }
    };
    private final ActionListener useRegexListener = actionEvent -> {
        useRegex = !useRegex;
        useRegexCheckbox.setSelected(useRegex);
        useRegexCheckbox.updateUI();
    };
    private JFileChooser fileChooser;
    private final ActionListener saveListener = actionEvent -> {

        try {
            fileChooser.setVisible(true);
            int returnValue = fileChooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Files.writeString(Paths.get(fileChooser.getSelectedFile().getAbsolutePath()), textArea.getText());
            }
            fileChooser.setVisible(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    private final ActionListener openListener = actionEvent -> {
        try {
            fileChooser.setVisible(true);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                textArea.setText(Files.readString(Paths.get(fileChooser.getSelectedFile().getAbsolutePath())));
            }
            fileChooser.setVisible(false);
        } catch (IOException e) {
            textArea.setText("");
            e.printStackTrace();
        }
    };

    public TextEditor() {

        super("Text Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        setup();

        setVisible(true);
    }

    private void setup() {

        fileChooser = new JFileChooser();
        fileChooser.setName("FileChooser");
        fileChooser.setVisible(false);
        this.add(fileChooser);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(controlPanel, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon("C:\\Users\\achen\\IdeaProjects\\Text Editor1\\Text Editor\\task\\src\\editor\\open_icon.png");
        Image small = icon.getImage();
        loadButton = new JButton(new ImageIcon(small.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        loadButton.setName("OpenButton");
        loadButton.addActionListener(openListener);
        controlPanel.add(loadButton);

        icon = new ImageIcon("C:\\Users\\achen\\IdeaProjects\\Text Editor1\\Text Editor\\task\\src\\editor\\save_icon.png");
        small = icon.getImage();
        saveButton = new JButton(new ImageIcon(small.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        saveButton.setName("SaveButton");
        saveButton.addActionListener(saveListener);
        controlPanel.add(saveButton);

        searchField = new JTextField();
        searchField.setName("SearchField");
        searchField.getDocument().addDocumentListener(fileNameListener);
        searchField.setPreferredSize(new Dimension(150, searchField.getPreferredSize().height + 4));
        controlPanel.add(searchField);

        icon = new ImageIcon("C:\\Users\\achen\\IdeaProjects\\Text Editor1\\Text Editor\\task\\src\\editor\\search_icon.png");
        small = icon.getImage();
        startSearchButton = new JButton(new ImageIcon(small.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        startSearchButton.setName("StartSearchButton");
        startSearchButton.addActionListener(startSearchListener);
        controlPanel.add(startSearchButton);

        icon = new ImageIcon("C:\\Users\\achen\\IdeaProjects\\Text Editor1\\Text Editor\\task\\src\\editor\\search_left.png");
        small = icon.getImage();
        previousMatchButton = new JButton(new ImageIcon(small.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        previousMatchButton.setName("PreviousMatchButton");
        previousMatchButton.addActionListener(previousMatchListener);
        controlPanel.add(previousMatchButton);

        icon = new ImageIcon("C:\\Users\\achen\\IdeaProjects\\Text Editor1\\Text Editor\\task\\src\\editor\\search_right.png");
        small = icon.getImage();
        nextMatchButton = new JButton(new ImageIcon(small.getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
        nextMatchButton.setName("NextMatchButton");
        nextMatchButton.addActionListener(nextMatchListener);
        controlPanel.add(nextMatchButton);

        useRegexCheckbox = new JCheckBox("Use regex");
        useRegexCheckbox.setName("UseRegExCheckbox");
        useRegexCheckbox.addActionListener(useRegexListener);
        controlPanel.add(useRegexCheckbox);

        textArea = new JTextArea(25, 80);
        textArea.setName("TextArea");

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setName("ScrollPane");
        add(scrollPane, BorderLayout.CENTER);
        setFileMenu();
    }

    private void setFileMenu() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        ////////////////////////////////////////////////

        JMenu fileMenu = new JMenu("File");
        fileMenu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        fileMenu.setName("MenuFile");
        menuBar.add(fileMenu);

        JMenuItem load = new JMenuItem("Open");
        load.setName("MenuOpen");
        load.addActionListener(openListener);
        fileMenu.add(load);

        JMenuItem save = new JMenuItem("Save");
        save.setName("MenuSave");
        save.addActionListener(saveListener);
        fileMenu.add(save);

        fileMenu.addSeparator();

        JMenuItem exit = new JMenuItem("Exit");
        exit.setName("MenuExit");
        fileMenu.add(exit);


        /////////////////////////////////////////////////////////

        JMenu searchMenu = new JMenu("Search");
        searchMenu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchMenu.setName("MenuSearch");
        menuBar.add(searchMenu);

        JMenuItem startSearch = new JMenuItem("Start search");
        startSearch.setName("MenuStartSearch");
        startSearch.addActionListener(startSearchListener);
        searchMenu.add(startSearch);

        JMenuItem previousSearch = new JMenuItem("Previous search");
        previousSearch.setName("MenuPreviousMatch");
        previousSearch.addActionListener(previousMatchListener);
        searchMenu.add(previousSearch);

        JMenuItem nextMatch = new JMenuItem("Next match");
        nextMatch.setName("MenuNextMatch");
        nextMatch.addActionListener(nextMatchListener);
        searchMenu.add(nextMatch);

        JMenuItem useRegex = new JMenuItem("Use regular expressions");
        useRegex.setName("MenuUseRegExp");
        useRegex.addActionListener(useRegexListener);
        searchMenu.add(useRegex);

        ///////////////////////////////////////////////////////////////

        setJMenuBar(menuBar);

        exit.addActionListener(e -> System.exit(0));

    }
}