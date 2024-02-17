import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class NumberSort extends JFrame implements ActionListener {
    private JTextField inputField;
    private JButton sortButton;
    private JComboBox<String> orderComboBox;
    private JButton saveButton;
    private JTextArea outputArea;

    public NumberSort() {
        setTitle("Insertion Sort Visualization");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel inputLabel = new JLabel("Enter integers separated by commas:");
        inputField = new JTextField();
        sortButton = new JButton("Sort");
        sortButton.addActionListener(this);
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sortButton, BorderLayout.SOUTH);

        // Order ComboBox
        String[] orderOptions = {"Least to Greatest", "Greatest to Least"};
        orderComboBox = new JComboBox<>(orderOptions);
        JPanel orderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        orderPanel.add(new JLabel("Sort Order:"));
        orderPanel.add(orderComboBox);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        // Output Area
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        // Panel for input, order, and save components
        JPanel inputOrderSavePanel = new JPanel(new BorderLayout());
        JPanel inputOrderPanel = new JPanel(new BorderLayout());
        inputOrderPanel.add(inputPanel, BorderLayout.NORTH);
        inputOrderPanel.add(orderPanel, BorderLayout.CENTER);
        inputOrderPanel.add(saveButton, BorderLayout.SOUTH);
        inputOrderSavePanel.add(inputOrderPanel, BorderLayout.NORTH);

        // Add components to JFrame
        add(inputOrderSavePanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sortButton) {
            String input = inputField.getText();
            String[] strArray = input.split(",");
            int[] arr = new int[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                arr[i] = Integer.parseInt(strArray[i].trim());
            }
            String selectedOrder = (String) orderComboBox.getSelectedItem();
            outputArea.setText(""); // Clear the output area
            if (selectedOrder.equals("Least to Greatest")) {
                insertionSort(arr, true);
            } else {
                insertionSort(arr, false);
            }
        } else if (e.getSource() == saveButton) {
            saveToFile(outputArea.getText());
        }
    }

    public void insertionSort(int[] arr, boolean ascending) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && (ascending ? arr[j] > key : arr[j] < key)) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
            // Print updated array
            printArray(arr);
        }
    }

    public void printArray(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int num : arr) {
            sb.append(num).append(" ");
        }
        outputArea.append(sb.toString() + "\n");
    }

    public void saveToFile(String content) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save As");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave.getAbsolutePath())) {
                writer.write(content);
                JOptionPane.showMessageDialog(this, "File saved successfully!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        // Create and display the GUI
        SwingUtilities.invokeLater(() -> {
            NumberSort frame = new NumberSort();
            frame.setVisible(true);
        });
    }
}