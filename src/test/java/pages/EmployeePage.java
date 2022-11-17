package pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class EmployeePage {

    @FindBy(className = "oxd-button")
    public List<WebElement> btnAddEmployee;

    @FindBy(name = "firstName")
    WebElement txtFirstName;

    @FindBy(name = "lastName")
    WebElement txtLastName;

    @FindBy(className = "oxd-switch-input")
    public WebElement toggleButton;

    @FindBy(className = "oxd-input")
    public List <WebElement> txtUserCreds;

    @FindBy(css = "[type = submit]")
    WebElement btnSubmit;

    @FindBy(className = "oxd-main-menu-item--name")
    public List<WebElement> myInfo;

    @FindBy(className = "oxd-select-text-input")
    public List <WebElement>  dropdownBox;

    @FindBy(className = "oxd-radio-input")
    public List <WebElement> radioButton;

    @FindBy(className = "oxd-input-field-error-message")
    public WebElement lblValidationError;

    public EmployeePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public String checkIfUserExists(String username){
        txtUserCreds.get(5).sendKeys(username);
        return lblValidationError.getText();
    }

    public  void createEmployee(String firstName, String lastName, String employeeID, String userName, String password, String confirmPassword) throws InterruptedException {

        txtFirstName.sendKeys(firstName);
        txtLastName.sendKeys(lastName);

        txtUserCreds.get(4).sendKeys(Keys.CONTROL, "A");
        txtUserCreds.get(4).sendKeys(employeeID);

        Thread.sleep(2000);
        txtUserCreds.get(5).sendKeys(userName);
        txtUserCreds.get(6).sendKeys(password);
        txtUserCreds.get(7).sendKeys(confirmPassword);
        btnSubmit.click();
    }
}
