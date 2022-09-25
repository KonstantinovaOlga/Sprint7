package Courier;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierAPI {
    public static final String COURIER = "courier/";
    public static final String LOGIN = "login";
    private final CourierObj courier;
    public CourierAPI(CourierObj courier) {
        this.courier = courier;
    }

    public Response addCourier() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(COURIER);
        return response;
    }

    public Response loginCourier() {
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .and()
                        .body(courier)
                        .when()
                        .post(COURIER + LOGIN);
        return response;
    }

    public String getCourierId() {
        String userId = "/" +
                given().
                        header("Content-type", "application/json").
                        and().
                        body(courier).
                        when().
                        post(COURIER + LOGIN).
                        then().
                        statusCode(200).
                        extract().
                        path("id");

        return userId;
    }

    public Response deleteCourier() {

        String userId = getCourierId();

        Response response =
                given().header("Content-type", "application/json")
                        .when()
                        .post(COURIER + userId);
        return response;
    }
}
