 import java.util.*;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Patient> patients = new ArrayList<>();
    static int nextId = 1;
    static int nextToken = 1;

    static String[] departments = {
        "General Medicine",
        "Cardiology",
        "Orthopedics",
        "Pediatrics",
        "ENT",
        "Emergency"
    };

    static class Patient {
        int id;
        String name;
        int age;
        String gender;
        String contact;
        int token;
        int priority;
        String priorityName;
        String department;
        String status;

        Patient(int id, String name, int age, String gender, String contact,
                int token, int priority, String priorityName,
                String department) {

            this.id = id;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.contact = contact;
            this.token = token;
            this.priority = priority;
            this.priorityName = priorityName;
            this.department = department;
            this.status = "Waiting";
        }
    }

    static int calculatePriority(int age, boolean emergency) {
        if (emergency) return 1;
        if (age >= 60) return 2;
        return 3;
    }

    static String getPriorityName(int priority) {
        if (priority == 1) return "Emergency";
        if (priority == 2) return "Senior Citizen";
        return "Normal";
    }

    static void registerPatient() {
        sc.nextLine();

        System.out.println("\n========== PATIENT REGISTRATION ==========");

        System.out.print("Enter patient name: ");
        String name = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();

        sc.nextLine();

        System.out.print("Enter gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter contact number: ");
        String contact = sc.nextLine();

        System.out.println("\nSelect Department:");

        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i]);
        }

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > departments.length) {
            System.out.println("Invalid department.");
            return;
        }

        System.out.print("Is this an emergency? (yes/no): ");
        String emergencyInput = sc.next();

        boolean emergency = emergencyInput.equalsIgnoreCase("yes");

        int priority = calculatePriority(age, emergency);
        String priorityName = getPriorityName(priority);

        String department = departments[choice - 1];

        Patient p = new Patient(
            nextId++,
            name,
            age,
            gender,
            contact,
            nextToken++,
            priority,
            priorityName,
            department
        );

        patients.add(p);

        System.out.println("\n==========================================");
        System.out.println("       PATIENT REGISTERED SUCCESSFULLY");
        System.out.println("==========================================");
        System.out.println("Patient ID : " + p.id);
        System.out.println("Name       : " + p.name);
        System.out.println("Department : " + p.department);
        System.out.println("Token      : " + p.token);
        System.out.println("Priority   : " + p.priorityName);
        System.out.println("Status     : " + p.status);
        System.out.println("==========================================");
    }

    static void displayQueue() {

        if (patients.isEmpty()) {
            System.out.println("\nNo patients registered.");
            return;
        }

        System.out.println("\n========== SELECT DEPARTMENT ==========");

        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i]);
        }

        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > departments.length) {
            System.out.println("Invalid department.");
            return;
        }

        String department = departments[choice - 1];

        ArrayList<Patient> queue = new ArrayList<>();

        for (Patient p : patients) {
            if (p.department.equals(department) &&
                (p.status.equals("Waiting") ||
                 p.status.equals("In Progress"))) {

                queue.add(p);
            }
        }

        queue.sort((a, b) -> {
            if (a.priority != b.priority)
                return Integer.compare(a.priority, b.priority);

            return Integer.compare(a.token, b.token);
        });

        System.out.println("\n========== " + department.toUpperCase() + " QUEUE ==========");

        if (queue.isEmpty()) {
            System.out.println("No patients waiting.");
            return;
        }

        System.out.printf(
            "%-8s %-20s %-6s %-18s %-15s %-15s%n",
            "Token", "Name", "Age", "Priority", "Status", "Contact"
        );

        System.out.println(
            "--------------------------------------------------------------------------------"
        );

        for (Patient p : queue) {
            System.out.printf(
                "%-8d %-20s %-6d %-18s %-15s %-15s%n",
                p.token,
                p.name,
                p.age,
                p.priorityName,
                p.status,
                p.contact
            );
        }
    }

    static void callNextPatient() {

        System.out.println("\n========== CALL NEXT PATIENT ==========");

        for (int i = 0; i < departments.length; i++) {
            System.out.println((i + 1) + ". " + departments[i]);
        }

        System.out.print("Select department: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > departments.length) {
            System.out.println("Invalid department.");
            return;
        }

        String department = departments[choice - 1];

        for (Patient p : patients) {
            if (p.department.equals(department) &&
                p.status.equals("In Progress")) {

                System.out.println(
                    "\nA patient is already being served: " + p.name
                );

                return;
            }
        }

        Patient nextPatient = null;

        for (Patient p : patients) {

            if (p.department.equals(department) &&
                p.status.equals("Waiting")) {

                if (nextPatient == null ||
                    p.priority < nextPatient.priority ||
                    (p.priority == nextPatient.priority &&
                     p.token < nextPatient.token)) {

                    nextPatient = p;
                }
            }
        }

        if (nextPatient == null) {
            System.out.println("\nNo patients waiting in this department.");
            return;
        }

        nextPatient.status = "In Progress";

        System.out.println("\n==========================================");
        System.out.println("          PATIENT CALLED");
        System.out.println("==========================================");
        System.out.println("Token      : " + nextPatient.token);
        System.out.println("Name       : " + nextPatient.name);
        System.out.println("Age        : " + nextPatient.age);
        System.out.println("Department : " + nextPatient.department);
        System.out.println("Priority   : " + nextPatient.priorityName);
        System.out.println("Status     : " + nextPatient.status);
        System.out.println("==========================================");
    }

    static void completePatient() {

        System.out.print("\nEnter token number to complete: ");
        int token = sc.nextInt();

        Patient patient = findPatientByToken(token);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (!patient.status.equals("In Progress")) {
            System.out.println(
                "Patient is not currently in consultation."
            );
            return;
        }

        patient.status = "Completed";

        System.out.println("\nConsultation completed successfully.");
        System.out.println("Patient: " + patient.name);
        System.out.println("Token: " + patient.token);
    }

    static void cancelPatient() {

        System.out.print("\nEnter token number to cancel: ");
        int token = sc.nextInt();

        Patient patient = findPatientByToken(token);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (patient.status.equals("Completed") ||
            patient.status.equals("Cancelled")) {

            System.out.println("This token cannot be cancelled.");
            return;
        }

        patient.status = "Cancelled";

        System.out.println("\nToken cancelled successfully.");
        System.out.println("Patient: " + patient.name);
        System.out.println("Token: " + patient.token);
    }

    static Patient findPatientByToken(int token) {

        for (Patient p : patients) {
            if (p.token == token) {
                return p;
            }
        }

        return null;
    }

    static void searchPatient() {

        sc.nextLine();

        System.out.print("\nEnter patient name to search: ");
        String name = sc.nextLine();

        boolean found = false;

        for (Patient p : patients) {

            if (p.name.equalsIgnoreCase(name)) {

                System.out.println("\n========== PATIENT DETAILS ==========");
                System.out.println("Patient ID : " + p.id);
                System.out.println("Name       : " + p.name);
                System.out.println("Age        : " + p.age);
                System.out.println("Gender     : " + p.gender);
                System.out.println("Contact    : " + p.contact);
                System.out.println("Department : " + p.department);
                System.out.println("Token      : " + p.token);
                System.out.println("Priority   : " + p.priorityName);
                System.out.println("Status     : " + p.status);
                System.out.println("====================================");

                found = true;
            }
        }

        if (!found) {
            System.out.println("Patient not found.");
        }
    }

    static void displayStatistics() {

        int waiting = 0;
        int inProgress = 0;
        int completed = 0;
        int cancelled = 0;
        int emergency = 0;
        int senior = 0;
        int normal = 0;

        for (Patient p : patients) {

            if (p.status.equals("Waiting"))
                waiting++;

            else if (p.status.equals("In Progress"))
                inProgress++;

            else if (p.status.equals("Completed"))
                completed++;

            else if (p.status.equals("Cancelled"))
                cancelled++;

            if (p.priority == 1)
                emergency++;

            else if (p.priority == 2)
                senior++;

            else
                normal++;
        }

        System.out.println("\n========== HOSPITAL STATISTICS ==========");

        System.out.println("Total Patients      : " + patients.size());
        System.out.println("Waiting Patients    : " + waiting);
        System.out.println("In Progress         : " + inProgress);
        System.out.println("Completed           : " + completed);
        System.out.println("Cancelled           : " + cancelled);

        System.out.println("\nPriority Statistics");
        System.out.println("---------------------------");
        System.out.println("Emergency           : " + emergency);
        System.out.println("Senior Citizens     : " + senior);
        System.out.println("Normal              : " + normal);

        System.out.println("=========================================");
    }

    static void displayAllPatients() {

        if (patients.isEmpty()) {
            System.out.println("\nNo patients registered.");
            return;
        }

        System.out.println("\n========== ALL PATIENTS ==========");

        for (Patient p : patients) {

            System.out.println("-----------------------------------");
            System.out.println("ID         : " + p.id);
            System.out.println("Name       : " + p.name);
            System.out.println("Age        : " + p.age);
            System.out.println("Gender     : " + p.gender);
            System.out.println("Contact    : " + p.contact);
            System.out.println("Department : " + p.department);
            System.out.println("Token      : " + p.token);
            System.out.println("Priority   : " + p.priorityName);
            System.out.println("Status     : " + p.status);
        }

        System.out.println("-----------------------------------");
    }

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("     HOSPITAL QUEUE MANAGEMENT SYSTEM");
        System.out.println("==============================================");

        while (true) {

            System.out.println("\n============== MAIN MENU ==============");
            System.out.println("1. Register Patient");
            System.out.println("2. View Department Queue");
            System.out.println("3. Call Next Patient");
            System.out.println("4. Complete Consultation");
            System.out.println("5. Cancel Token");
            System.out.println("6. Search Patient");
            System.out.println("7. View All Patients");
            System.out.println("8. View Statistics");
            System.out.println("9. Exit");
            System.out.println("========================================");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    registerPatient();
                    break;

                case 2:
                    displayQueue();
                    break;

                case 3:
                    callNextPatient();
                    break;

                case 4:
                    completePatient();
                    break;

                case 5:
                    cancelPatient();
                    break;

                case 6:
                    searchPatient();
                    break;

                case 7:
                    displayAllPatients();
                    break;

                case 8:
                    displayStatistics();
                    break;

                case 9:
                    System.out.println(
                        "\nThank you for using Hospital Queue Management System."
                    );
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
