import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UstazUI ustaz = new UstazUI();
        StudentDetails student = new StudentDetails();
        SurahManagement surah = new SurahManagement();

        boolean loginSuccessful = false;
        boolean studentSelected = false;
        boolean surahSelected = false;

        try (Scanner scan = new Scanner(System.in)) {
            while (true) {
                try {
                    // Main menu
                    System.out.println("\n1. Login\n2. Register\n3. Logout\n4. Exit");
                    System.out.print("Choose an option: ");
                    int choice = Integer.parseInt(scan.nextLine()); // Always use nextLine to avoid newline issues

                    // Perform actions based on choice
                    switch (choice) {
                        case 1 -> loginSuccessful = ustaz.login();
                        case 2 -> ustaz.register();
                        case 3 -> ustaz.logout();
                        case 4 -> {
                            System.out.println("Exiting...");
                            System.exit(0);
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }

                while (loginSuccessful) {
                    try {
                        // Logged-in menu
                        System.out.println("\n1. Student\n2. Surah\n3. Logout");
                        System.out.print("Choose an option: ");
                        int choice = Integer.parseInt(scan.nextLine());

                        switch (choice) {
                            case 1 -> studentSelected = true;
                            case 2 -> surahSelected = true;
                            case 3 -> {
                                System.out.println("Logging out...");
                                loginSuccessful = false;
                            }
                            default -> System.out.println("Invalid choice. Try again.");
                        }

                        // Student menu
                        while (studentSelected) {
                            System.out.println("\n1. Add Student\n2. Edit Student\n3. Delete Student\n4. List Student\n5. Back");
                            System.out.print("Choose an option: ");
                            choice = Integer.parseInt(scan.nextLine());

                            switch (choice) {
                                case 1 -> student.addStudent();
                                case 2 -> student.editStudent();
                                case 3 -> student.deleteStudent();
                                case 4 -> student.listStudent();
                                case 5 -> studentSelected = false; // Exit student menu
                                default -> System.out.println("Invalid choice. Try again.");
                            }
                        }

                        // Surah menu
                        while (surahSelected) {
                            surahSelected =surah.manageSurahs();
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }
            }
        }
    }
}
