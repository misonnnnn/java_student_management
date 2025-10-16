import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static ArrayList<Student> students = new ArrayList<>();
    private static int nextId = 1;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
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

        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Student student = new Student(nextId++, name, age);
        students.add(student);

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
                        case 1 -> {
                            updateStudentName(id);
                        }
                        case 2 -> {
                            updateStudentAge(id);
                        }
                        case 3 -> {
                            updateStudentStatus(id);
                        }
                        case 4 -> initialChoices();

                        default -> {
                            System.out.println("Invalid choice. Try again.");
                        }
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
                System.out.println("✅ Student ID "+ studentID +" age updated!");
                updateStudentName(studentID);
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }

    private static void updateStudentAge(int studentID){
        System.out.print("Please enter new age for student ID: " + studentID + ": ");
        int newStudentAge = scanner.nextInt();

        for (Student s : students){
            if(s.getId() == studentID){
                s.setAge(newStudentAge);
                System.out.println("✅ Student "+ s.getName() +" age updated!");
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
                return;
            }
        }

        System.out.println("❌ Student not found!");
    }


}



