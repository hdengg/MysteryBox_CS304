package client;

import model.Customer;
import service.ConnectionService;
import service.CustomerService;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private CustomerService customerService;
    private MainUI mainUI;
    private JTable allCustomers;
    private JButton editCustomer;

    public CustomerController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
        initAdminCustomerPanel();

    }

    private void initAdminCustomerPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Username", "Password", "First Name", "Last Name", "Phone", "Email"};
        dtm.setColumnIdentifiers(header);
        allCustomers = mainUI.getAdminCustomerTable();
        allCustomers.setModel(dtm);

        try {
            List<Customer> customers = customerService.getAllCustomers();
            for (int i = 0; i < customers.size(); i++) {
                Customer customer = customers.get(i);
                dtm.addRow(new Object[] {customer.getUsername(), customer.getPassword(),
                        customer.getFirstName(), customer.getLastName(), customer.getPhoneNum(),
                customer.getEmail()});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Customer getCurrentCustomer() {
        JTable customerTable = mainUI.getAdminCustomerTable();
        int row = customerTable.getSelectedRow();

        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < customerTable.getColumnCount(); i++) {
            data.add(customerTable.getModel().getValueAt(row, i).toString());
        }

        String username = data.get(0);
        String password = data.get(1);
        String first_name = data.get(2);
        String last_name = data.get(3);
        String phone_num = data.get(4);
        String email = data.get(5);

        return new Customer(username, password, first_name, last_name, phone_num, email);
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.customerService = new CustomerService(conn);
    }
}
