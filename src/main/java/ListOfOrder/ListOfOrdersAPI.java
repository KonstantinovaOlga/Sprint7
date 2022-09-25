package ListOfOrder;
import static io.restassured.RestAssured.given;

public class ListOfOrdersAPI {
    public static final String LIST_OF_ORDER = "orders";

    public ListOfOrdersAPI() {
    }

    public ListOfOrders getListOfOrder() {
        ListOfOrders listOfOrders =
                given().header("Content-type", "application/json")
                        .when()
                        .get(LIST_OF_ORDER)
                        .body().as(ListOfOrders.class);

        return listOfOrders;
    }
}
