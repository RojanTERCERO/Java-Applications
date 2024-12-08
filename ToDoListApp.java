import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ToDoListApp extends JFrame {
    private ToDoList toDoList;
    private JTextField taskInput;
    private JList<String> taskList;
    private DefaultListModel<String> listModel;

    public ToDoListApp() {
        toDoList = new ToDoList();
        listModel = new DefaultListModel<>();

        // Set up JFrame
        setTitle("To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Create components
        taskInput = new JTextField();
        JButton addButton = new JButton("Add Task");
        JButton completeButton = new JButton("Mark as Completed");
        JButton removeButton = new JButton("Remove Task");

        taskList = new JList<>(listModel);

        // Set layout
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(taskInput);
        inputPanel.add(addButton);

        // Add components to the JFrame
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(completeButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        completeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markAsCompleted();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });
    }

    private void addTask() {
        String description = taskInput.getText().trim();
        if (!description.isEmpty()) {
            toDoList.addTask(description);
            updateTaskList();
            taskInput.setText("");
        }
    }

    private void markAsCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            toDoList.markTaskAsCompleted(selectedIndex);
            updateTaskList();
        }
    }

    private void removeTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            toDoList.removeTask(selectedIndex);
            updateTaskList();
        }
    }

    private void updateTaskList() {
        listModel.clear();
        for (Task task : toDoList.getTasks()) {
            listModel.addElement(task.getDescription() + (task.isCompleted() ? " (Completed)" : ""));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ToDoListApp().setVisible(true);
        });
    }
}

class Task {
    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markAsCompleted() {
        this.completed = true;
    }
}

class ToDoList {
    private List<Task> tasks;

    public ToDoList() {
        tasks = new ArrayList<>();
    }

    public void addTask(String description) {
        Task task = new Task(description);
        tasks.add(task);
    }

    public void markTaskAsCompleted(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsCompleted();
        }
    }

    public void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
