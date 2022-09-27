package courier;

import config.MethodOfChecking;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class CourierObjTest {

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
    @DisplayName("Creating of courier")
    @Description("Check code and answer")
    public void createNewCourier() {
        Response response = this.courierAPI.addCourier();
        this.methods.checkCode(response,SC_CREATED);
        this.methods.checkFieldBooleanValue(response,"ok",true);
        needDelete = true;
    }

    @Test
    @DisplayName("Creating double of courier")
    @Description("Check code and message")
    public void createDoubleCourier() {
        courierAPI.addCourier();
        Response responseRepeat = courierAPI.addCourier();
        this.methods.checkCode(responseRepeat,SC_CONFLICT);
        this.methods.checkMessage(responseRepeat,"Этот логин уже используется. Попробуйте другой.");
        needDelete = true;
    }

    @Test
    @DisplayName("Creating of courier with busy login")
    @Description("Check code and message")
    public void createCourierWithSameLogin() {
        courierAPI.addCourier();
        CourierObj courierSecond = new CourierObj(this.courier.getLogin(),
                this.courier.getPassword() + "1",
                this.courier.getFirstName() + "1");
        CourierAPI courierAPISecond = new CourierAPI(courierSecond);
        Response responseRepeat = courierAPISecond.addCourier();
        this.methods.checkCode(responseRepeat,SC_CONFLICT);
        this.methods.checkMessage(responseRepeat,"Этот логин уже используется. Попробуйте другой.");
        this.methods.checkFieldIntValue(responseRepeat, "code", SC_CONFLICT);
        needDelete = true;
    }

    @Test
    @DisplayName("Creating of courier with null login")
    @Description("Check code and message")
    public void createNewCourierWithNullLogin() {
        this.courier.setLogin(null);
        CourierAPI courierAPIObj = new CourierAPI(this.courier);
        Response response = courierAPIObj.addCourier();
        this.methods.checkCode(response,SC_BAD_REQUEST);
        this.methods.checkMessage(response,"Недостаточно данных для создания учетной записи");
        needDelete = false;
    }

    @Test
    @DisplayName("Creating of courier with null password")
    @Description("Check code and message")
    public void createNewCourierWithNullPassword() {
        courier.setPassword(null);
        CourierAPI courierAPIObj = new CourierAPI(courier);
        Response response = courierAPIObj.addCourier();
        this.methods.checkCode(response,SC_BAD_REQUEST);
        this.methods.checkMessage(response,"Недостаточно данных для создания учетной записи");
        needDelete = false;
    }

    @Test
    @DisplayName("Creating of courier with null Name ")
    @Description("Check code and message")
    public void createNewCourierWithNullFirstName() {
        courier.setFirstName(null);
        CourierAPI courierAPIObj = new CourierAPI(courier);
        Response response = courierAPIObj.addCourier();
        this.methods.checkCode(response,SC_CREATED);
        this.methods.checkFieldBooleanValue(response,"ok",true);
        needDelete = true;
    }

    @After
    public void tearDown() {
        if (needDelete) courierAPI.deleteCourier();
    }

}