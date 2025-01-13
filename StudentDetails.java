import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
/*
 * Author: Muhammad Hasif bin Aminuddin(2417035)
 */
public class StudentDetails {
    // Scanner object to get user input
    Scanner scanner = new Scanner(System.in);
    private int studentIDCounter = 1; // Initialize the counter for student ID

    // Create a database to store student details
    static ArrayList<String> studentNames = new ArrayList<>();
    static ArrayList<String> studentClass = new ArrayList<>();
    static ArrayList<String> studentID = new ArrayList<>();

    // Add a file to store student details
    static File studentDataFile = new File("student_data.txt");

    // Count number of students
    static int count = 0;

    public StudentDetails() {
        // Load existing data from the file (if it exists)
        loadStudentData();
    }

    public void addStudent() {
        String name;

        // Get the names of the students
        while (true) {
            System.out.println("Enter the name of the student:");
            name = scanner.nextLine();

            if (name.isEmpty()) { // Check if the name is empty
                System.out.println("Name cannot be empty. Please enter a valid name.");
                continue;
            }

            if (studentNames.contains(name)) { // Check if the name already exists
                System.out.println("Student already exists. Please enter a different name.");
                continue;
            }

            break; // Exit the loop if the name is valid
        }

        studentNames.add(name); // Add the name to the database

        // Get the class details of the student
        System.out.println("Enter the class for the student:");
        String hafazan = scanner.nextLine();
        studentClass.add(hafazan); // Add student class

        // Generate a unique ID using the counter
        int newStudentID = studentIDCounter;
        studentID.add(String.valueOf(newStudentID)); // Add unique student ID
        studentIDCounter++; // Increment the counter for the next student

        // Save data to the file
        saveUserData();

        System.out.println("Student added successfully!");
    }

    public void editStudent() {
        // List all the students
        listStudent();

        boolean validInput = false; // Flag to track valid input

        while (!validInput) {
            try {
                // Prompt user to choose which student to edit
                System.out
                        .print("\nEnter the number of the student you want to edit (1-" + studentNames.size() + "): ");
                int studentIndex = (scanner.nextInt() - 1); // Subtract 1 to get the correct index
                scanner.nextLine(); // Flush the scanner buffer

                // Check if the student index is valid
                if (studentIndex >= 0 && studentIndex < studentNames.size()) {
                    System.out.print("Enter the new class detail for " + studentNames.get(studentIndex) + ": ");
                    String newHafazan = scanner.nextLine();
                    studentClass.set(studentIndex, newHafazan); // Update the Hafazan detail
                    System.out.println("Student detail updated successfully!");
                    validInput = true; // Input is valid, exit the loop
                } else {
                    System.out.println("Invalid student number! Please try again.");
                }
            } catch (Exception e) { // Catch any exceptions
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    public void deleteStudent() {
        // List all the students
        listStudent();

        boolean validInput = false; // Flag to track valid input

        while (!validInput) {
            try {
                // Prompt user to choose which student to delete
                System.out.print(
                        "\nEnter the number of the student you want to delete (1-" + studentNames.size() + "): ");
                int studentIndex = (scanner.nextInt() - 1); // Subtract 1 to get the correct index
                scanner.nextLine(); // Flush the scanner buffer

                // Check if the student index is valid
                if (studentIndex >= 0 && studentIndex < studentNames.size()) {
                    System.out.println("Deleting student " + studentNames.get(studentIndex) + "...");
                    studentNames.remove(studentIndex); // Remove the student name
                    studentClass.remove(studentIndex); // Remove the student class
                    studentID.remove(studentIndex); // Remove the student ID
                    System.out.println("Student and corresponding class detail deleted successfully!");

                    // Save the updated data to the file
                    saveUserData();

                    validInput = true; // Input is valid, exit the loop
                } else {
                    System.out.println("Invalid student number!");
                }
            } catch (Exception e) { // Catch any exceptions
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    public void listStudent() {
        // Print header
        System.out.println("\nList of Students:");
        System.out.printf("%-5s %-20s %-10s%n", "No.", "Name", "Class");
        System.out.println("--------------------------------------------");

        // Print each student's details in a formatted table
        for (int i = 0; i < studentNames.size(); i++) {
            System.out.printf("%-5d %-20s %-10s%n", (i + 1), studentNames.get(i), studentClass.get(i));
        }
    }

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(studentDataFile, true))) {
            for (int i = 0; i < studentNames.size(); i++) {
                String data = studentID.get(i) + "," + studentNames.get(i) + "," + studentClass.get(i);

                // Check if the data already exists in the file
                if (!isDataAlreadySaved(data)) {
                    writer.write(data);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }
    }

    private boolean isDataAlreadySaved(String data) {
        try (BufferedReader reader = new BufferedReader(new FileReader(studentDataFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(data)) {
                    return true; // Data already exists
                }
            }
        } catch (IOException e) {
            System.out.println("Error checking existing data: " + e.getMessage());
        }
        return false; // Data doesn't exist
    }

    public void loadStudentData() {
        try (Scanner fileScanner = new Scanner(studentDataFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] details = line.split(","); // Assuming data is saved as: ID,Name,Class

                if (details.length == 3) { // Ensure the line has valid data
                    studentID.add(details[0]);
                    studentNames.add(details[1]);
                    studentClass.add(details[2]);

                    // Update the counter to the next ID
                    String id = details[0].substring(1); // Extract numeric part of the ID
                    int numericID = Integer.parseInt(id);
                    studentIDCounter = Math.max(studentIDCounter, numericID + 1);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Student data file not found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }
}
