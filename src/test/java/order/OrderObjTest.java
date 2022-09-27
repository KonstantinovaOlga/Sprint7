package order;
import config.MethodOfChecking;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderObjTest {

    private OrderObj order;
    private final String[] color;
    private MethodOfChecking methods;
    public OrderObjTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[] getOrderData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK", "GRAY"}},
                {new String[]{""}}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = config.URL.URL;
        this.order = new OrderObj();
        this.order = this.order.getRandomOrder(color);
        this.methods=new MethodOfChecking();
    }

    @Test
    @DisplayName("Creating of order")
    @Description("Creating of order with different colors, check code and getting of track")
    public void createNewOrder() {
        OrderAPI orderAPIObj = new OrderAPI(this.order);
        Response response = orderAPIObj.addOrder();
        this.methods.checkCode(response,SC_CREATED);
        this.methods.fieldDoesntEmpety(response,"track");
    }
}