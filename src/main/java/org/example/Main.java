package org.example;

import java.util.*;

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
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

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

                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option.");
            }
        } while (choice != 0);
    }
}