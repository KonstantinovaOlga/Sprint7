package Courier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class CourierObjTest {

    private CourierObj courier;
    private CourierAPI courierAPI;
    private boolean needDelete;

    @Before
    public void setUp() {
        RestAssured.baseURI = Config.URL.URL;
        this.courier = new CourierObj();
        this.courier = this.courier.getRandomCourier();
        this.courierAPI = new CourierAPI(this.courier);
        needDelete = false;
    }

    @Test
    public void createNewCourier() {

        Response response = this.courierAPI.addCourier();

        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));

        needDelete = true;
    }

    @Test
    public void createDoubleCourier() {

        courierAPI.addCourier();
        Response responseRepeat = courierAPI.addCourier();

        responseRepeat.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .body("code", equalTo(409));

        needDelete = true;
    }

    @Test
    public void createCourierWithSameLogin() {

        courierAPI.addCourier();

        CourierObj courierSecond = new CourierObj(this.courier.getLogin(),
                this.courier.getPassword() + "1",
                this.courier.getFirstName() + "1");
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);
        Response responseRepeat = courierAPISecond.addCourier();

        responseRepeat.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .body("code", equalTo(409));

        needDelete = true;
    }

    @Test
    public void createNewCourierWithNullLogin() {

        this.courier.setLogin(null);
        CourierAPI courierAPIObj = new CourierAPI(this.courier);
        Response response = courierAPIObj.addCourier();

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        needDelete = false;
    }

    @Test
    public void createNewCourierWithNullPassword() {

        courier.setPassword(null);
        CourierAPI courierAPIObj = new CourierAPI(courier);

        Response response = courierAPIObj.addCourier();

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));

        needDelete = false;
    }

    @Test
    public void createNewCourierWithNullFirstName() {

        courier.setFirstName(null);
        CourierAPI courierAPIObj = new CourierAPI(courier);

        Response response = courierAPIObj.addCourier();

        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));

        needDelete = true;
    }

    @Test
    public void loginCourier() {

        courierAPI.addCourier();
        Response response = courierAPI.loginCourier();

        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());

        needDelete = true;
    }

    @Test
    public void loginCourierWithIncorrectLogin() {

        courierAPI.addCourier();

        CourierObj courierSecond = new CourierObj(this.courier.getLogin() + "1",
                this.courier.getPassword(),
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);

        Response response = courierAPISecond.loginCourier();

        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

        needDelete = true;
    }

    @Test
    public void loginCourierWithIncorrectPassword() {

        courierAPI.addCourier();

        CourierObj courierSecond = new CourierObj(this.courier.getLogin(),
                this.courier.getPassword() + "1",
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);

        Response response = courierAPISecond.loginCourier();

        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

        needDelete = true;
    }

    @Test
    public void loginWithUnknownData() {

        Response response = courierAPI.loginCourier();

        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

        needDelete = false;
    }

    @Test
    public void loginWithNullLogin() {

        courierAPI.addCourier();

        CourierObj courierSecond = new CourierObj(null,
                this.courier.getPassword(),
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);

        Response response = courierAPISecond.loginCourier();

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

        needDelete = true;
    }

    @Test
    public void loginWithNullPassword() {

        courierAPI.addCourier();

        CourierObj courierSecond = new CourierObj(this.courier.getLogin(),
                null,
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);
        Response response = courierAPISecond.loginCourier();

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

        needDelete = true;
    }

    @After
    public void tearDown() {
        if (needDelete) courierAPI.deleteCourier();
    }

}