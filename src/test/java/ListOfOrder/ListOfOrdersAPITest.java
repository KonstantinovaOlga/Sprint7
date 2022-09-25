package ListOfOrder;

import Order.OrderObj;
import Order.OrderAPI;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ListOfOrdersAPITest {
    private OrderObj order;
    private ListOfOrders listOfOrders;
    private ListOfOrdersAPI listOfOrdersAPI;

    @Before
    public void setUp() {
        RestAssured.baseURI = Config.URL.URL;
        String[][] colors = {{"BLACK"}, {"GREY"}, {"BLACK", "GREY"}};
        OrderAPI orderAPI;
        this.order = new OrderObj();
        for (int i = 0; i < 3; i++) {
            this.order = this.order.getRandomOrder(colors[i]);
            orderAPI = new OrderAPI(this.order);
            orderAPI.addOrder();
        }
        this.listOfOrdersAPI = new ListOfOrdersAPI();
        this.listOfOrders = new ListOfOrders();
    }

    @Test
    public void getListOfOrderDoesntEmpety() {

        this.listOfOrders = this.listOfOrdersAPI.getListOfOrder();

        List<CreatedOrder> createdOrders = this.listOfOrders.getCreatedOrders();
        int size = createdOrders.size();
        assertEquals(true, size >= 3);
    }
}