package Order;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderObjTest {

    private OrderObj order;
    private final String[] color;

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
        RestAssured.baseURI = Config.URL.URL;
        this.order = new OrderObj();
        this.order = this.order.getRandomOrder(color);
    }

    @Test
    public void createNewOrder() {

        OrderAPI orderAPIObj = new OrderAPI(this.order);
        Response response = orderAPIObj.addOrder();
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }
}