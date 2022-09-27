package order;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderAPI {

    public static final String ORDERS = "orders";
    private OrderObj order;

    public OrderAPI(OrderObj order) {
        this.order = order;
    }

    @Step("Creating of order")
    public Response addOrder() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(order)
                        .when()
                        .post(ORDERS);
        return response;
    }

}
