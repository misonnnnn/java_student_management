import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.*;

public class Main {
    private static final ArrayList<Student> students = new ArrayList<>();
    private static int nextId = 1;
    private static final Scanner scanner = new Scanner(System.in);

    private static final String FILE_NAME = "students.txt";

    public static void main(String[] args) {
        loadStudentsFromFile();
        initialChoices();
    }

    private static void initialChoices(){
        while (true) {
            System.out.println("\n=== Student Manager ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // clear newline
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                scanner.nextLine(); // clear invalid input
                continue; // restart the while loop
            }

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        for (Student s : students){
            if(s.getName().equals(name)){
                System.out.println("Name already existing!");
                addStudent();
                return;
            }
        }

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(nextId++, name, age);
        students.add(student);
        saveStudentsToFile();
        System.out.println("✅ Student added successfully!");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    private static void updateStudent() {
        System.out.print("Enter student ID to update: ");
        int id;
        try {
            id = scanner.nextInt();
            scanner.nextLine(); // clear newline
        } catch (InputMismatchException e) {
            System.out.println("❌ Invalid input. Please enter a number.");
            scanner.nextLine();
            return;
        }

        boolean found = false;

        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("Choose what to update with student "+ s.getName());
                System.out.println("1. Name");
                System.out.println("2. Age");
                System.out.println("3. Status");
                System.out.println("4. Back to main choices");
                System.out.print("Choose an option: ");


                while (true) {
                    int updateChoice;

                    found = true;

                    try {
                        updateChoice = scanner.nextInt();
                        scanner.nextLine(); // clear newline
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Invalid input. Please enter a number.");
                        scanner.nextLine(); // clear invalid input
                        continue; // restart the while loop
                    }

                    switch (updateChoice){
                        case 1 -> updateStudentName(id);
                        case 2 -> updateStudentAge(id);
                        case 3 -> updateStudentStatus(id);
                        case 4 -> initialChoices();
                        default -> System.out.println("Invalid choice. Try again.");
                    }

                    break;
                }

            }
        }

        if (!found) {
            System.out.println("❌ Student not found!");
        }
    }

    private static void updateStudentName(int studentID){
        System.out.print("Please enter new name for student ID " + studentID + ": ");
        String newStudentName = scanner.nextLine();

        for (Student s : students){
            if(s.getId() == studentID){
                s.setName(newStudentName);
                System.out.println("✅ Student ID "+ studentID +" name updated!");
                saveStudentsToFile();
                updateStudentName(studentID);
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }

    private static void updateStudentAge(int studentID){
        System.out.print("Please enter new age for student ID " + studentID + ": ");
        int newStudentAge = scanner.nextInt();

        for (Student s : students){
            if(s.getId() == studentID){
                s.setAge(newStudentAge);
                System.out.println("✅ Student "+ s.getName() +" age updated!");
                saveStudentsToFile();
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }

    private static void updateStudentStatus(int studentID){
        System.out.print("Please enter new status for student ID: " + studentID + ": ");
        String newStudentStatus = scanner.nextLine();

        if(!Arrays.asList("active", "inactive").contains(newStudentStatus)){
            System.out.println("invalid status value !");
            updateStudentStatus(studentID);
        }

        for (Student s : students){
            if(s.getId() == studentID){
                s.setStatus(newStudentStatus);
                System.out.println("✅ Student "+ s.getName() +" status updated!");
                saveStudentsToFile();
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }

    private static void deleteStudent() {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Student s : students) {
            if (s.getId() == id) {
                students.remove(s);
                System.out.println("🗑️ Student deleted!");
                saveStudentsToFile();
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }

    private static void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                // Write student data separated by commas
                writer.write(s.getId() + "," + s.getName() + "," + s.getAge() + "," + s.getStatus());
                writer.newLine();
            }
//            System.out.println("✅ Students saved successfully!");
        } catch (IOException e) {
            //.out.println("❌ Error saving students: " + e.getMessage());
        }
    }

    private static void loadStudentsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // No file yet on first run

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                int id = Integer.parseInt(data[0]);
                String name = data[1];
                int age = Integer.parseInt(data[2]);
                boolean status = Boolean.parseBoolean(data[3]);
                students.add(new Student(id, name, age, status));
            }

            // Ensure nextId starts after the last used ID
            if (!students.isEmpty()) {
                nextId = students.getLast().getId() + 1;
            }

            System.out.println("📂 Students loaded from file!");
        } catch (IOException e) {
            System.out.println("❌ Error loading students: " + e.getMessage());
        }
    }


}



