package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ContactManagerGUI extends JFrame {
    private final management dao = new management();

    // Table with three action columns
    private final String[] columnNames = {"Name", "Mobile", "Change Name", "Change Number", "Delete"};
    private final DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            // Only action columns are editable
            return column >= 2;
        }
    };
    private final JTable contactTable = new JTable(tableModel);

    private final JTextField nameField = new JTextField();
    private final JTextField mobileField = new JTextField();

    public ContactManagerGUI() {
        super("Contact Manager");

        // Modern Look & Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLayout(new BorderLayout());

        // Table styling
        contactTable.setFillsViewportHeight(true);
        contactTable.setRowHeight(25);
        contactTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        contactTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Alternate row colors
        contactTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        });

        // Attach button renderers/editors
        contactTable.getColumn("Change Name").setCellRenderer(new ButtonRenderer());
        contactTable.getColumn("Change Name").setCellEditor(new ButtonEditor("Change Name"));

        contactTable.getColumn("Change Number").setCellRenderer(new ButtonRenderer());
        contactTable.getColumn("Change Number").setCellEditor(new ButtonEditor("Change Number"));

        contactTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        contactTable.getColumn("Delete").setCellEditor(new ButtonEditor("Delete"));

        add(new JScrollPane(contactTable), BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Mobile:"));
        inputPanel.add(mobileField);
        add(inputPanel, BorderLayout.NORTH);

        // Toolbar-style buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addBtn = new JButton("Add");
        JButton searchBtn = new JButton("Search");
        JButton showAllBtn = new JButton("Show All");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 13);
        for (JButton btn : new JButton[]{addBtn, searchBtn, showAllBtn}) {
            btn.setFont(btnFont);
            btn.setFocusPainted(false);
            buttonPanel.add(btn);
        }
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String mobile = mobileField.getText();
            boolean added = dao.addContact(name, mobile);
            JOptionPane.showMessageDialog(this, added ? "Contact added successfully!" : "Duplicate contact. Not added.");
            refreshTable(dao.getAllContacts());
        });

        searchBtn.addActionListener(e -> {
            String query = nameField.getText().isEmpty() ? mobileField.getText() : nameField.getText();
            List<Contact> results = dao.search(query);
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No matching contacts found.");
            }
            refreshTable(results);
        });

        showAllBtn.addActionListener(e -> refreshTable(dao.getAllContacts()));
    }

    // Refresh table with contacts
    private void refreshTable(List<Contact> contacts) {
        tableModel.setRowCount(0);
        for (Contact c : contacts) {
            tableModel.addRow(new Object[]{c.getName(), c.getMobile(), "Change Name", "Change Number", "Delete"});
        }
    }

    // Renderer for button columns
    class ButtonRenderer extends JButton implements javax.swing.table.TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Editor for button columns
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String actionType;
        private boolean clicked;
        private int row;

        public ButtonEditor(String actionType) {
            super(new JCheckBox());
            this.actionType = actionType;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            button.setText(actionType);
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                String name = (String) tableModel.getValueAt(row, 0);
                String mobile = (String) tableModel.getValueAt(row, 1);

                switch (actionType) {
                    case "Change Name" -> {
                        String newName = JOptionPane.showInputDialog(ContactManagerGUI.this,
                                "Enter new name (leave blank to keep current):", name);
                        Contact updated = dao.updateContact(mobile, newName, null);
                        JOptionPane.showMessageDialog(ContactManagerGUI.this,
                                updated != null ? "Name updated!" : "Update failed.");
                    }
                    case "Change Number" -> {
                        String newMobile = JOptionPane.showInputDialog(ContactManagerGUI.this,
                                "Enter new mobile (leave blank to keep current):", mobile);
                        Contact updated = dao.updateContact(mobile, null, newMobile);
                        JOptionPane.showMessageDialog(ContactManagerGUI.this,
                                updated != null ? "Number updated!" : "Update failed.");
                    }
                    case "Delete" -> {
                        boolean deleted = dao.deleteByMobile(mobile);
                        JOptionPane.showMessageDialog(ContactManagerGUI.this,
                                deleted ? "Contact deleted!" : "Delete failed.");
                    }
                }
                refreshTable(dao.getAllContacts());
            }
            clicked = false;
            return actionType;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }
    }

    // Entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactManagerGUI gui = new ContactManagerGUI();
            gui.setVisible(true);
        });
    }
}