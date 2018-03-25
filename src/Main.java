import service.ConnectionService;
import client.MainFrameController;
import model.Session;
import service.CustomerService;

public class Main {

    public static void main(String[] args) {
        ConnectionService connectionService = new ConnectionService();
        connectionService.connect("ora_j2z8", "a31484116");
        Session session = new Session();
        CustomerService customerService = new CustomerService(connectionService.getConnection());
        MainFrameController mfc = new MainFrameController(customerService);
    }
}