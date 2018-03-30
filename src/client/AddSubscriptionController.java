package client;

import service.ConnectionService;
import service.SubscriptionService;
import ui.AddSubscriptionUI;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class AddSubscriptionController {
    private SubscriptionService subscriptionService;
    private AddSubscriptionUI addSubscriptionUI;
    private String username;
    private String mbid;

    public AddSubscriptionController(AddSubscriptionUI addSubUI) {
        addSubscriptionUI = addSubUI;
        initServices();
    }

    public void addSubscription() {
        getInputs();
        if (isEmptyInput()) {
            addSubscriptionUI.getErrorLabel().setText("Error: empty input fields");
        } else {
            int min = 15;
            int max = 500;
            int sid = ThreadLocalRandom.current().nextInt(min, max);
            String status = "true";
            int mbidInt = Integer.parseInt(mbid);
            try {
                Date date = stringToDate("2018-04-06");
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                subscriptionService.addSubscription(sid, status, sqlDate, 1,
                        username, mbidInt);
                addSubscriptionUI.getErrorLabel().setText("Subscription was successfully added!");
            } catch (Exception e) {
                addSubscriptionUI.getErrorLabel().setText("Error: failed to add a subscription");
            }
        }
    }

    public boolean isEmptyInput() {
        return (username.isEmpty() || mbid.isEmpty());
    }

    public void getInputs() {
        username = addSubscriptionUI.getUsernameField().getText();
        mbid = addSubscriptionUI.getMbidField().getText();
    }

    public Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(dateStr);
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.subscriptionService = new SubscriptionService(conn);
    }
}
