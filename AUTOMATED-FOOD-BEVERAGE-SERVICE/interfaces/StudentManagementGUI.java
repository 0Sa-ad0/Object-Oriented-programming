package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentManagementGUI extends JFrame {
    private AutomatedFoodandBraverage foodAndBeverageService; // The service that implements IStudents
    private JTextArea outputArea;
    private JTextField studentNameField, studentIdField;

    public StudentManagementGUI() {
        foodAndBeverageService = new AutomatedFoodandBraverage(); // Initialize the service

        setTitle("Student Management");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create Components
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JLabel nameLabel = new JLabel("Student Name: ");
        JLabel idLabel = new JLabel("Student ID: ");
        studentNameField = new JTextField();
        studentIdField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(studentNameField);
        inputPanel.add(idLabel);
        inputPanel.add(studentIdField);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Student");
        JButton removeButton = new JButton("Remove Student");
        JButton showButton = new JButton("Show All Students");

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(showButton);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = studentNameField.getText();
                String id = studentIdField.getText();
                if (!name.isEmpty() && !id.isEmpty()) {
                    Student student = new Student(name, id);
                    foodAndBeverageService.insertStudent(student); // Insert student using service
                    outputArea.append("Student " + name + " added!\n");
                    studentNameField.setText("");
                    studentIdField.setText("");
                } else {
                    outputArea.append("Please enter valid details.\n");
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = studentIdField.getText();
                if (!id.isEmpty()) {
                    Student student = foodAndBeverageService.getStudent(id); // Fetch student by ID
                    if (student != null) {
                        foodAndBeverageService.removeStudent(student); // Remove student using service
                        outputArea.append("Student with ID " + id + " removed.\n");
                    } else {
                        outputArea.append("Student not found.\n");
                    }
                    studentIdField.setText("");
                } else {
                    outputArea.append("Please enter a valid student ID.\n");
                }
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputArea.setText(""); // Clear previous results
                foodAndBeverageService.showAllStudents(); // Show all students
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementGUI().setVisible(true);
            }
        });
    }
}
