package client;

import service.ConnectionService;
import service.CustomerService;
import ui.MainUI;

import java.sql.Connection;

public class CustomerController {
    private CustomerService customerService;
    private MainUI mainUI;

    public CustomerController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();


    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.customerService = new CustomerService(conn);
    }
}
