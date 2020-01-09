package ui;


import model.*;
import model.exceptions.BlankEntryException;
import model.exceptions.TaskAlreadyInListException;
import model.exceptions.TaskDoesNotExistException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToDoListGUI extends JFrame implements ActionListener, ListSelectionListener {

    private ToDoList toDoList;
    private JPanel todolistPanel;
    private JList<String> taskList;
    private JLabel categoryTaskInfo;
    private JPanel categoryPanel;
    private JButton addUrgentTaskButton;
    private JButton addRegularTaskButton;
    private JButton removeTaskButton;
    private JButton backButton;
    private DefaultListModel<String> taskModel;
    private Category currentCategory;

    private ToDoListGUI(ToDoList toDoList) {
        super("TODOLIST :D");
        this.toDoList = toDoList;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 250));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        todolistPanel = new JPanel();
        todolistPanel.setLayout(new GridLayout(7, 0));
        JLabel greeting = new JLabel("Welcome to your ultra spicy ToDoList");
        JButton school = new JButton("School");
        configureButton(school);
        JButton chores = new JButton("Chores");
        configureButton(chores);
        JButton errands = new JButton("Errands");
        configureButton(errands);
        JButton personal = new JButton("Personal");
        configureButton(personal);
        JButton miscellaneous = new JButton("Miscellaneous");
        configureButton(miscellaneous);
        JButton save = new JButton("Save List");
        configureButton(save);
        todolistPanel.add(greeting);
        todolistPanel.add(school);
        todolistPanel.add(chores);
        todolistPanel.add(errands);
        todolistPanel.add(personal);
        todolistPanel.add(miscellaneous);
        todolistPanel.add(save);
        this.add(todolistPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    private void configureButton(JButton button) {
        button.setActionCommand(button.getText());
        button.addActionListener(this);
    }

    //REQUIRES: valid action event
    //MODIFIES: this
    //EFFECTS: adds, removes, saves, and opens new screen for the appropriate button click
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Save List")) {
            saveToDoList();
        } else if (e.getActionCommand().equals("Add a regular task")) {
            addRegularTaskProcedure();
        } else if (e.getActionCommand().equals("Add an urgent task")) {
            addUrgentTaskProcedure();
        } else if (e.getActionCommand().equals("Back to menu")) {
            switchScreen();
        } else if (e.getActionCommand().equals("Remove a task")) {
            prepForRemoval();
            removeATaskProcedure();
        } else {
            openCategoryScreen(e);
        }
    }

    private void removeATaskProcedure() {
        try {
            removeATask();
        } catch (BlankEntryException ex) {
            blankSubmissionError();
        }
    }

    private void addUrgentTaskProcedure() {
        try {
            addUrgentTask();
        } catch (BlankEntryException ex) {
            blankSubmissionError();
        }
    }

    private void addRegularTaskProcedure() {
        try {
            addRegularTask();
        } catch (BlankEntryException ex) {
            blankSubmissionError();
        }
    }

    private void prepForRemoval() {
        categoryTaskInfo.setText(currentCategory.getCategoryName());
        taskList.clearSelection();
    }

    private void switchScreen() {
        categoryPanel.setVisible(false);
        todolistPanel.setVisible(true);
    }

    private void blankSubmissionError() {
        JOptionPane blankEntryException = new JOptionPane();
        blankEntryException.showMessageDialog(this,
                "Submission(s) were blank.",
                "Blank Submission",
                blankEntryException.ERROR_MESSAGE);
    }

    private void saveToDoList() {
        try {
            toDoList.save("data/toDoListFile.sav");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void removeATask() throws BlankEntryException {
        JOptionPane removeTaskPane = new JOptionPane();
        String taskName = removeTaskPane.showInputDialog(this,
                "What is the name of the task that you want to remove?", "");
        if (taskName != null) {
            checkBlankEntryForRemoval(taskName);
            try {
                categoryTaskInfo.setText(currentCategory.getCategoryName());
                taskModel.removeElement(taskName);
                toDoList.removeTask(taskName);
            } catch (TaskDoesNotExistException ex) {
                JOptionPane taskDoesNotExistException = new JOptionPane();
                taskDoesNotExistException.showMessageDialog(this,
                        "That task is not in the list.",
                        "Not in List",
                        taskDoesNotExistException.ERROR_MESSAGE);
            }
        }
    }

    private void checkBlankEntryForRemoval(String taskName) throws BlankEntryException {
        if (taskName.trim().equals("")) {
            throw new BlankEntryException();
        }
    }

    private void addUrgentTask() throws BlankEntryException {
        JOptionPane addTaskPane = new JOptionPane();
        String taskName = addTaskPane.showInputDialog(this,
                "What is the name of your task?", "");
        continueAddingUrgentTask(addTaskPane, taskName);
    }

    private void continueAddingUrgentTask(JOptionPane addTaskPane, String taskName) throws BlankEntryException {
        if (taskName != null) {
            String taskDeadline = addTaskPane.showInputDialog(this,
                    "When do you need to finish your task by?", "");
            //above code taken from https://stackoverflow.com/questions/8852560/how-to-make-popup-window-in-java
            if (taskDeadline != null) {
                try {
                    if (!isEntryValid(taskName, taskDeadline)) {
                        toDoList.addInList(
                                new UrgentTask(taskName, taskDeadline, currentCategory), currentCategory);
                        taskModel.addElement(taskName);
                    } else {
                        throw new BlankEntryException();
                    }
                } catch (TaskAlreadyInListException ex) {
                    taskAlreadyInListError();
                }
            }
        }
    }

    private void taskAlreadyInListError() {
        JOptionPane alreadyInListException = new JOptionPane();
        alreadyInListException.showMessageDialog(this,
                "That task is already in the list.",
                "Already in List",
                alreadyInListException.ERROR_MESSAGE);
    }

    private boolean isEntryValid(String taskName, String taskDeadline) {
        String trimmedTaskName = taskName.trim();
        String trimmedTaskDeadline = taskDeadline.trim();
        return trimmedTaskDeadline.equals("") | trimmedTaskName.equals("");
    }

    private void addRegularTask() throws BlankEntryException {
        JOptionPane addTaskPane = new JOptionPane();
        String taskName = addTaskPane.showInputDialog(this,
                "What is the name of your task?", "");
        continueAddingRegularTask(addTaskPane, taskName);
    }

    private void continueAddingRegularTask(JOptionPane addTaskPane, String taskName) throws BlankEntryException {
        if (taskName != null) {
            String taskDeadline = addTaskPane.showInputDialog(this,
                    "When do you need to finish your task by?", "");
            //above code taken from https://stackoverflow.com/questions/8852560/how-to-make-popup-window-in-java
            if (taskDeadline != null) {
                try {
                    if (!isEntryValid(taskName, taskDeadline)) {
                        toDoList.addInList(
                                new RegularTask(taskName, taskDeadline, currentCategory), currentCategory);
                        taskModel.addElement(taskName);
                    } else {
                        throw new BlankEntryException();
                    }
                } catch (TaskAlreadyInListException ex) {
                    taskAlreadyInListError();
                }
            }
        }
    }

    private void openCategoryScreen(ActionEvent e) {
        currentCategory = toDoList.getCategory(e.getActionCommand());
        todolistPanel.setVisible(false);
        categoryPanel = new JPanel();
        categoryPanel.setPreferredSize(new Dimension(400, 250));
        categoryTaskInfo = new JLabel(e.getActionCommand());
        taskList = generateTaskList(currentCategory, categoryPanel, categoryTaskInfo);
        taskList.addListSelectionListener(this);
        configureTaskButtons(categoryPanel);
        add(categoryPanel);
        categoryPanel.setVisible(true);
    }

    private void configureTaskButtons(JPanel categoryPanel) {
        addRegularTaskButton = new JButton("Add a regular task");
        addUrgentTaskButton = new JButton("Add an urgent task");
        removeTaskButton = new JButton("Remove a task");
        backButton = new JButton("Back to menu");

        configureButton(addRegularTaskButton);
        configureButton(addUrgentTaskButton);
        configureButton(removeTaskButton);
        configureButton(backButton);

        categoryPanel.add(addRegularTaskButton);
        categoryPanel.add(addUrgentTaskButton);
        categoryPanel.add(removeTaskButton);
        categoryPanel.add(backButton);
    }

    private JList<String> generateTaskList(Category category, JPanel categoryPanel, JLabel categoryTaskInfo) {
        List<Task> tasks = category.getTasks();
        List<String> taskNames = new ArrayList<>();
        for (Task t : tasks) {
            taskNames.add(t.getTaskName());
        }
        taskModel = new DefaultListModel<>();
        for (String s : taskNames) {
            taskModel.addElement(s);
        }
        JList<String> taskList = new JList<>();
        taskList.setModel(taskModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setLayoutOrientation(JList.VERTICAL);
        taskList.setVisibleRowCount(5);
        createPanes(categoryPanel, taskList, categoryTaskInfo);
        return taskList;
    }

    private void createPanes(JPanel categoryPanel, JList<String> taskList, JLabel categoryTaskInfo) {
        JScrollPane taskScroll = new JScrollPane(taskList);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setPreferredSize(new Dimension(300, 125));
        splitPane.setLeftComponent(taskScroll);
        splitPane.setRightComponent(new JScrollPane(categoryTaskInfo));
        splitPane.setResizeWeight(0.5);
        categoryPanel.add(splitPane);
    }


    //EFFECTS: starts the program
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ToDoList toDoList = new ToDoList();
        toDoList.load("data/toDoListFile.sav");
        new ToDoListGUI(toDoList);
    }


    @Override
    //REQUIRES: valid ListSelectionEvent
    //MODIFIES: this
    //EFFECTS: displays the task information
    public void valueChanged(ListSelectionEvent e) {

        if (taskList.getSelectedValue() != null) {
            Task selectedTask = toDoList.getTask(taskList.getSelectedValue());
            categoryTaskInfo.setText("Due: " + selectedTask.getDeadline());
        }

    }
}