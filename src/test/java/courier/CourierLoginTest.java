package courier;

import config.MethodOfChecking;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;

public class CourierLoginTest {

    private CourierObj courier;
    private CourierAPI courierAPI;
    private boolean needDelete;
    private MethodOfChecking methods;

    @Before
    public void setUp() {
        RestAssured.baseURI = config.URL.URL;
        this.courier = new CourierObj();
        this.courier = this.courier.getRandomCourier();
        this.courierAPI = new CourierAPI(this.courier);
        needDelete = false;
        this.methods=new MethodOfChecking();

    }

    @Test
    @DisplayName("Login of courier")
    @Description("Check code and getting of id")
    public void loginCourier() {
        courierAPI.addCourier();
        Response response = courierAPI.loginCourier();
        this.methods.checkCode(response,SC_OK);
        this.methods.fieldDoesntEmpety(response,"id");
        needDelete = true;
    }

    @Test
    @DisplayName("Login of courier with incorrect login")
    @Description("Check code and message")
    public void loginCourierWithIncorrectLogin() {
        courierAPI.addCourier();
        CourierObj courierSecond = new CourierObj(this.courier.getLogin() + "1",
                this.courier.getPassword(),
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);
        Response response = courierAPISecond.loginCourier();
        this.methods.checkCode(response,SC_NOT_FOUND);
        this.methods.checkMessage(response,"Учетная запись не найдена");
        needDelete = true;
    }

    @Test
    @DisplayName("Login of courier with incorrect password")
    @Description("Check code and message")
    public void loginCourierWithIncorrectPassword() {
        courierAPI.addCourier();
        CourierObj courierSecond = new CourierObj(this.courier.getLogin(),
                this.courier.getPassword() + "1",
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);
        Response response = courierAPISecond.loginCourier();
        this.methods.checkCode(response,SC_NOT_FOUND);
        this.methods.checkMessage(response,"Учетная запись не найдена");
        needDelete = true;
    }

    @Test
    @DisplayName("Login of courier with unknown data")
    @Description("Check code and message")
    public void loginWithUnknownData() {
        Response response = courierAPI.loginCourier();
        this.methods.checkCode(response,SC_NOT_FOUND);
        this.methods.checkMessage(response,"Учетная запись не найдена");
        needDelete = false;
    }

    @Test
    @DisplayName("Login of courier with null login")
    @Description("Check code and message")
    public void loginWithNullLogin() {
        courierAPI.addCourier();
        CourierObj courierSecond = new CourierObj(null,
                this.courier.getPassword(),
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);
        Response response = courierAPISecond.loginCourier();
        this.methods.checkCode(response,SC_BAD_REQUEST);
        this.methods.checkMessage(response,"Недостаточно данных для входа");
        needDelete = true;
    }

    @Test
    @DisplayName("Login of courier with null password")
    @Description("Check code and message")
    public void loginWithNullPassword() {
        courierAPI.addCourier();
        CourierObj courierSecond = new CourierObj(this.courier.getLogin(),
                null,
                this.courier.getFirstName());
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);
        Response response = courierAPISecond.loginCourier();
        this.methods.checkCode(response,SC_BAD_REQUEST);
        this.methods.checkMessage(response,"Недостаточно данных для входа");
        needDelete = true;
    }

    @After
    public void tearDown() {
        if (needDelete) courierAPI.deleteCourier();
    }

}