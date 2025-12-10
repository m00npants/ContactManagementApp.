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
        return new ArrayList<>(contacts);
    }
}