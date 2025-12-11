package org.example;

import java.util.*;

//Menu
public class Main {
     static void main() {
        Scanner scanner = new Scanner(System.in);
        management dao = new management();
        int choice;

        do {
            System.out.println("\n=== Contact Management ===");
            System.out.println("1. Add Contact");
            System.out.println("2. Search by Name");
            System.out.println("3. Search by Mobile");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Delete by Name");
            System.out.println("6. Delete by Mobile");
            System.out.println("7. Update number or name");
            System.out.println("8.");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter mobile: ");
                    String mobile = scanner.nextLine();
                    boolean added = dao.addContact(name, mobile);
                    System.out.println(added ? "Contact added successfully!" : "Duplicate contact. Not added.");
                }
                case 2 -> {
                    System.out.print("Enter name to search: ");
                    String query = scanner.nextLine();
                    List<Contact> results = dao.searchByName(query);
                    if (results.isEmpty()) {
                        System.out.println("No matching contacts found.");
                    } else {
                        results.forEach(System.out::println);
                    }
                }
                case 3 -> {
                    System.out.print("Enter mobile to search: ");
                    String mobile = scanner.nextLine();
                    Contact result = dao.searchByMobile(mobile);
                    System.out.println(result != null ? result : "No contact found with that mobile.");
                }
                case 4 -> {
                    List<Contact> all = dao.getAllContacts();
                    if (all.isEmpty()) {
                        System.out.println("No contacts to display.");
                    } else {
                        int i = 1;
                        for (Contact c : all) {
                            System.out.println(i++ + ". " + c);
                        }
                    }
                }
                case 5 -> {
                    System.out.print("Enter name to delete: ");
                    String delName = scanner.nextLine();
                    boolean deleted = dao.deleteByName(delName);
                    System.out.println(deleted ? "Contact(s) deleted." : "No contact found with that name.");
                }
                case 6 -> {
                    System.out.print("Enter mobile to delete: ");
                    String delMobile = scanner.nextLine();
                    boolean deleted = dao.deleteByMobile(delMobile);
                    System.out.println(deleted ? "Contact deleted." : "No contact found with that mobile.");
                }
                case 7 -> {
                    System.out.print("Enter number or name of contact to update: ");
                    String oldMobile = scanner.nextLine();

                    System.out.print("Enter new name (leave blank to keep current): ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter new mobile (leave blank to keep current): ");
                    String newMobile = scanner.nextLine();

                    Contact updated = dao.updateContact(oldMobile, newName, newMobile);
                    if (updated != null) {
                        System.out.println("Contact updated successfully!");
                    } else {
                        System.out.println("Update failed (contact not found or duplicate).");
                    }
                }


                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }

}