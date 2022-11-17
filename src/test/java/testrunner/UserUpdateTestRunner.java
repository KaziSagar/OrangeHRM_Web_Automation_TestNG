package testrunner;

import io.qameta.allure.Allure;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.EmployeePage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;

import java.io.IOException;
import java.util.List;

public class UserUpdateTestRunner extends Setup {
    EmployeePage employeePage;

    //@BeforeTest
    @BeforeTest
    public void doLogin() throws IOException, ParseException {
        driver.get("https://opensource-demo.orangehrmlive.com");

        List data = Utils.readJSONArray("./src/test/resources/Users.json");
        LoginPage loginPage = new LoginPage(driver);
        JSONObject userObj = (JSONObject) data.get(data.size() - 1);
        String username = (String) userObj.get("userName");
        String password = (String) userObj.get("password");
        loginPage.doLogin(username, password);

        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.PIM.get(1).click();

        employeePage = new EmployeePage(driver);
        employeePage.myInfo.get(2).click();
        String urlActual = driver.getCurrentUrl();
        String urlExpected = "viewPersonalDetails";
        Assert.assertTrue(urlActual.contains(urlExpected));

        Allure.description("Employee can login successfully");
    }

    @Test(description = "Updated employee information")
    public void updateUserInfo() throws InterruptedException {
        List <WebElement> headerTitle = driver.findElements(By.className("orangehrm-main-title"));
        Utils.waitForElement(driver, headerTitle.get(0), 50);
        if(headerTitle.get(0).isDisplayed()){
            Thread.sleep(1000);

            employeePage.radioButton.get(0).click();

            Thread.sleep(1000);
            employeePage.dropdownBox.get(0).click();
            employeePage.dropdownBox.get(0).sendKeys("b");
            employeePage.dropdownBox.get(0).sendKeys(Keys.ARROW_DOWN);
            employeePage.dropdownBox.get(0).sendKeys(Keys.ARROW_DOWN);
            employeePage.dropdownBox.get(0).sendKeys(Keys.ENTER);

            Thread.sleep(1000);
            employeePage.dropdownBox.get(1).sendKeys("s");
            employeePage.dropdownBox.get(1).sendKeys(Keys.ENTER);

            List <WebElement> buttons = driver.findElements(By.cssSelector("[type = submit]"));
            buttons.get(1).click();

            String actualNationality = employeePage.dropdownBox.get(0).getText();
            String expectedNationality = "Bangladeshi";
            Assert.assertTrue(actualNationality.contains(expectedNationality));

            String actualMaritalStatus = employeePage.dropdownBox.get(1).getText();
            String expectedMaritalStatus = "Single";
            Assert.assertTrue(actualMaritalStatus.contains(expectedMaritalStatus));

            Allure.description("Employee can update own information successfully");
        }
    }

    @AfterTest
    public void logout() throws InterruptedException {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.btnProfileIcon.click();
        driver.findElement(By.partialLinkText("Logout")).click();
        Thread.sleep(2000);
        Allure.description("Employee can logout successfully");
    }
}
