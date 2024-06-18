import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Grade {
    private String subject;
    private double score;

    public Grade(String subject, double score) {
        this.subject = subject;
        this.score = score;
    }

    public String getSubject() {
        return subject;
    }

    public double getScore() {
        return score;
    }
}

public class StudentGradeTracker extends JFrame {
    private ArrayList<Grade> grades;
    private JTextField subjectField;
    private JTextField gradeField;
    private JTextArea resultArea;

    public StudentGradeTracker() {
        grades = new ArrayList<>();
        createUI();
    }

    private void createUI() {
        setTitle("Student Grade Tracker");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        inputPanel.add(subjectField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Grade");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGrade();
            }
        });
        inputPanel.add(addButton);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAndDisplayResults();
            }
        });
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void addGrade() {
        String subject = subjectField.getText();
        String gradeText = gradeField.getText();

        if (subject.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both subject and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            grades.add(new Grade(subject, grade));
            resultArea.append("Added: " + subject + " - " + grade + "\n");
            subjectField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid grade.");
        }
    }

    private void calculateAndDisplayResults() {
        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No grades entered.");
            return;
        }

        double total = 0;
        for (Grade grade : grades) {
            total += grade.getScore();
        }

        double average = total / grades.size();
        char letterGrade = calculateLetterGrade(average);
        double gpa = calculateGPA(average);

        resultArea.append("\nAverage Grade: " + average);
        resultArea.append("\nLetter Grade: " + letterGrade);
        resultArea.append("\nGPA: " + gpa + "\n");
    }

    private char calculateLetterGrade(double average) {
        if (average >= 90) return 'A';
        else if (average >= 80) return 'B';
        else if (average >= 70) return 'C';
        else if (average >= 60) return 'D';
        else return 'F';
    }

    private double calculateGPA(double average) {
        if (average >= 90) return 4.0;
        else if (average >= 80) return 3.0;
        else if (average >= 70) return 2.0;
        else if (average >= 60) return 1.0;
        else return 0.0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentGradeTracker().setVisible(true);
            }
        });
    }
}

