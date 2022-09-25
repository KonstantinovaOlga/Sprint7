package Courier;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierObj {

    private String login;
    private String password;
    private String firstName;

    public CourierObj() {
    }

    public CourierObj(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public CourierObj getRandomCourier() {
        return new CourierObj(
                RandomStringUtils.randomAlphanumeric(10),
                "Password",
                RandomStringUtils.randomAlphabetic(10)
        );
    }
}
