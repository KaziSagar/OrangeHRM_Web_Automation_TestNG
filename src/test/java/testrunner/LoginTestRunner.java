package testrunner;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import setup.Setup;
import utils.Utils;

import java.util.List;

public class LoginTestRunner extends Setup {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @Test(priority = 1, description = "User cannot login with wrong creds")
    public void doLoginWithWrongCreds(){
        driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage = new LoginPage(driver);
        loginPage.doLogin("wronguser", "password");
        String validationMessageActual = driver.findElement(By.className("oxd-alert-content-text")).getText();
        String validationMessageExpected = "Invalid credentials";
        Assert.assertTrue(validationMessageActual.contains(validationMessageExpected));
        Allure.description("User Cannot run with wrong creds");
    }

    @Test(priority = 2, description = "Admin can login successfully")
    public void doLogin(){
        //driver.get("https://opensource-demo.orangehrmlive.com");
        loginPage = new LoginPage(driver);
        loginPage.doLogin("admin", "admin123");

        String urlActual = driver.getCurrentUrl();
        String urlExpected = "index";
        Assert.assertTrue(urlActual.contains(urlExpected));
    }

    @Test(priority = 3, description = "Admin profile image showing")
    public void isProfileImageExists(){
        dashboardPage = new DashboardPage(driver);
       //WebElement imgProfile = driver.findElement(By.className("oxd-userdropdown-img"));
        Assert.assertTrue(dashboardPage.imgProfile.isDisplayed());
    }

    @Test(priority = 4, description = "Dashboard in the URL")
    public void isURLExists(){
        dashboardPage = new DashboardPage(driver);
        String expectedURL = "dashboard";
        String actualURL = driver.getCurrentUrl();
        Assert.assertTrue(actualURL.contains(expectedURL));
    }






//
//
//
//
//
//
//    @Test(priority = 4, description = "Select employee status", enabled = false)
//    public void selectEmploymentStatus() throws InterruptedException {
//        dashboardPage.PIM.get(1).click();
//        Thread.sleep(2000);
//        dashboardPage.dropDowns.get(0).click();
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ENTER);
//        dashboardPage.btnSubmit.click();
//
//        Thread.sleep(3000);
//
//        WebElement txtData = dashboardPage.recordsFound.get(14);
//        String dataActual = txtData.getText();
//        String dataExpected = "Records Found";
//        Assert.assertTrue(dataActual.contains(dataExpected));
//    }
//
//    @Test(priority = 5, description = "Showing employee list", enabled = false)
//    public void listEmployee() {
//        Utils.doScroll(driver);
//
//        WebElement table= driver.findElement(By.className("oxd-table-body"));
//        List<WebElement> allRows= table.findElements(By.cssSelector("[role=row]"));
//        for (WebElement row:allRows) {
//            List<WebElement> cells= row.findElements(By.cssSelector("[role=cell]"));
//            System.out.println(cells.get(5).getText());
//            Assert.assertTrue(cells.get(5).getText().contains("Full-Time Contract"));
//
//        }
//        //Allure.description("Employee list showing properly");
//    }
//
//    @Test(priority = 6, description = "Showing no employee data if not in database", enabled = false)
//    public void noEmployeeData() throws InterruptedException {
//
//        dashboardPage = new DashboardPage(driver);
//        dashboardPage.PIM.get(1).click();
//        Thread.sleep(2000);
//        dashboardPage.dropDowns.get(0).click();
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ARROW_DOWN);
//        dashboardPage.dropDowns.get(0).sendKeys(Keys.ENTER);
//        Thread.sleep(2000);
//        dashboardPage.btnSubmit.click();
//
//        List <WebElement> records = driver.findElements(By.className("oxd-text--span"));
//        String dataStatusActual = records.get(11).getText();
//        String dataStatusExpected = "No Records Found";
//        Assert.assertEquals(dataStatusActual, dataStatusExpected);
//    }
}
