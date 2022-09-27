package courier;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierAPI {
    public static final String COURIER = "courier/";
    public static final String LOGIN = "login";
    private final CourierObj courier;
    public CourierAPI(CourierObj courier) {
        this.courier = courier;
    }


    @Step("Creating of courier")
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

    @Step("Login of courier")
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

    @Step("Getting of courier's id")
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

    @Step("Deleting of courier")
    public Response deleteCourier() {

        String userId = getCourierId();

        Response response =
                given().header("Content-type", "application/json")
                        .when()
                        .post(COURIER + userId);
        return response;
    }
}
