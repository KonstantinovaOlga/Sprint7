package list_of_order;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import order.OrderObj;
import order.OrderAPI;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class ListOfOrdersAPITest {
    private OrderObj order;
    private ListOfOrders listOfOrders;
    private ListOfOrdersAPI listOfOrdersAPI;

    @Before
    public void setUp() {
        RestAssured.baseURI = config.URL.URL;
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
    @DisplayName("Getting list of orders")
    @Description("Answer has list of orders")
    public void getListOfOrderDoesntEmpety() {
        this.listOfOrders = this.listOfOrdersAPI.getListOfOrder();
        List<CreatedOrder> createdOrders = this.listOfOrders.getCreatedOrders();
        int size = createdOrders.size();
        checkSizeOfList(size);
    }

    @Step("Check count of orders in the list")
    public void checkSizeOfList(int size)  {
        assertEquals(true, size >= 3);
    }
}