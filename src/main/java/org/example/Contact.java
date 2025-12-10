package org.example;

public class Contact {
    private final String name;
    private final String mobile;

    public Contact(String name, String mobile) {
        this.name = name.trim();
        this.mobile = mobile.trim();
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String format() {
        return name + "|" + mobile;
    }

    @Override
    public String toString() {
        return name + " (" + mobile + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Contact)) return false;
        Contact other = (Contact) obj;
        return this.format().equalsIgnoreCase(other.format());
    }

    @Override
    public int hashCode() {
        return format().toLowerCase().hashCode();
    }
}