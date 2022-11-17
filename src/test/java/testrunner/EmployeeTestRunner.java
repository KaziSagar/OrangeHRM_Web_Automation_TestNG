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

public class EmployeeTestRunner extends Setup {
    DashboardPage dashboardPage;

    @BeforeTest
    public void doLogin(){
        driver.get("https://opensource-demo.orangehrmlive.com");
        LoginPage loginPage = new LoginPage(driver);
        String adminUser ="admin";
        String adminPassword = "admin123";
        loginPage.doLogin(adminUser, adminPassword);
        Allure.description("Admin can login successfully to create and search employees");
    }

    @Test(priority = 1, description = "Create new employee")
    public void create2Employees() throws InterruptedException, IOException, ParseException {


        for(int i=0; i<2; i++){

            dashboardPage = new DashboardPage(driver);
            //Thread.sleep(5000);
            dashboardPage.PIM.get(1).click();

            EmployeePage employeePage = new EmployeePage(driver);

            Utils utils = new Utils();
            utils.generateRandomData();
            String firstName = utils.getFirstname();
            String lastName = utils.getLastname();
            int randomId = Utils.generateRandomNumber(1000, 9000);
            String userName = utils.getFirstname() + randomId;
            String password = "P@ssword123";
            String conFirmPassword = password;
            String employeeID = String.valueOf(randomId);

            employeePage.btnAddEmployee.get(2).click();
            Thread.sleep(3000);

            employeePage.toggleButton.click();

            employeePage.createEmployee(firstName, lastName, employeeID, userName, password, conFirmPassword);

            List <WebElement> headerTitle = driver.findElements(By.className("orangehrm-main-title"));
            Assert.assertTrue(headerTitle.get(0).isDisplayed());

            utils.waitForElement(driver, headerTitle.get(0), 50);

            if(headerTitle.get(0).isDisplayed()){
                utils.saveJsonList(userName, password, employeeID);
            }
        }
        Allure.description("Admin can create employees successfully");
    }

    @Test(priority = 2, description = "Search employees by ID")
    public void searchEmployeesByID() throws InterruptedException, IOException, ParseException {

        dashboardPage = new DashboardPage(driver);
        dashboardPage.PIM.get(1).click();

        for(int i=0; i<2; i++){

            List data = Utils.readJSONArray("./src/test/resources/Users.json");
            JSONObject userObj = (JSONObject) data.get(i);
            String userID = (String) userObj.get("userID");
            Thread.sleep(2000);
            dashboardPage.searchBar.get(1).click();
            dashboardPage.searchBar.get(1).sendKeys(userID);
            dashboardPage.btnSubmit.click();
            Thread.sleep(2000);

            String expectedResult = "Record Found";
            String actualResult = dashboardPage.recordsFound.get(14).getText();
            Assert.assertTrue(actualResult.contains(expectedResult));

            dashboardPage.searchBar.get(1).click();

            Thread.sleep(2000);
            dashboardPage.searchBar.get(1).sendKeys(Keys.CONTROL, "A");

            dashboardPage.searchBar.get(1).sendKeys(Keys.BACK_SPACE);
        }
        Allure.description("Admin can search employees successfully");
    }

    @Test(priority = 3, description = "Check if user exists")
    public void checkIfUserExists() throws IOException, ParseException, InterruptedException {
        dashboardPage = new DashboardPage(driver);
        dashboardPage.PIM.get(1).click();
        EmployeePage employeePage = new EmployeePage(driver);
        employeePage.btnAddEmployee.get(2).click();
        Thread.sleep(2000);
        employeePage.toggleButton.click();
        List data = Utils.readJSONArray("./src/test/resources/Users.json");
        JSONObject userObj = (JSONObject) data.get(data.size() - 1);
        String existingUserName = (String) userObj.get("userName");
        String validationMessageActual = employeePage.checkIfUserExists(existingUserName);
        String validationMessageExpected = "Username already exists";
        Assert.assertTrue(validationMessageActual.contains(validationMessageExpected));
        Allure.description("Check if user exists");
    }

    @AfterTest
    public void logout() throws InterruptedException {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.btnProfileIcon.click();
        Thread.sleep(1000);
        driver.findElement(By.partialLinkText("Logout")).click();
        Thread.sleep(3000);
        Allure.description("Admin can logout successfully");
    }
}