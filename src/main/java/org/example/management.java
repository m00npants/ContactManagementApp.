package org.example;

import java.util.*;

public class management {
    private final List<Contact> contacts = new ArrayList<>();
    private final Set<Contact> contactSet = new HashSet<>();

    public boolean addContact(String name, String mobile) {
        Contact contact = new Contact(name, mobile);
        if (contactSet.contains(contact)) return false;
        contacts.add(contact);
        contactSet.add(contact);
        return true;
    }

    public List<Contact> searchByName(String query) {
        List<Contact> results = new ArrayList<>();
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(query.toLowerCase())) {
                results.add(c);
            }
        }
        return results;
    }

    public Contact searchByMobile(String mobile) {
        for (Contact c : contacts) {
            if (c.getMobile().equals(mobile)) return c;
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        List<Contact> sortedContacts = new ArrayList<>(contacts);
        sortedContacts.sort(Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER));
        return sortedContacts;
    }

    public boolean deleteByName(String name) {
        Iterator<Contact> iterator = contacts.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            Contact c = iterator.next();
            if (c.getName().equalsIgnoreCase(name)) {
                iterator.remove();
                contactSet.remove(c);
                removed = true;
            }
        }
        return removed;
    }

    public boolean deleteByMobile(String mobile) {
        Iterator<Contact> iterator = contacts.iterator();
        boolean removed = false;

        while (iterator.hasNext()) {
            Contact c = iterator.next();
            if (c.getMobile().equals(mobile)) {
                iterator.remove();
                contactSet.remove(c);
                removed = true;
                break;
            }
        }
        return removed;
    }

    public Contact updateContact(String identifier, String newName, String newMobile) {
        for (Contact c : contacts) {

            if (c.getName().equalsIgnoreCase(identifier) || c.getMobile().equals(identifier)) {
                Contact original = new Contact(c.getName(), c.getMobile());


                String updatedName = (newName == null || newName.isBlank()) ? c.getName() : newName.trim();
                String updatedMobile = (newMobile == null || newMobile.isBlank()) ? c.getMobile() : newMobile.trim();
                Contact updated = new Contact(updatedName, updatedMobile);


                if (contactSet.contains(updated)) {
                    return null;
                }


                contacts.remove(c);
                contactSet.remove(c);


                contacts.add(updated);
                contactSet.add(updated);

                System.out.println("Original: " + original);
                System.out.println("Updated: " + updated);

                return updated;
            }
        }
        return null;
    }
    public List<Contact> search(String query) {
        List<Contact> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(lowerQuery) || c.getMobile().contains(query)) {
                results.add(c);
            }
        }
        return results;
    }






}