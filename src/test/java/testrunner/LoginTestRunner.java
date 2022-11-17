package testrunner;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import setup.Setup;

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

        loginPage = new LoginPage(driver);
        loginPage.doLogin("admin", "admin123");

        String urlActual = driver.getCurrentUrl();
        String urlExpected = "index";
        Assert.assertTrue(urlActual.contains(urlExpected));
        Allure.description("Admin can login successfully");
    }

    @Test(priority = 3, description = "Admin profile image showing")
    public void isProfileImageExists(){
        dashboardPage = new DashboardPage(driver);
        Assert.assertTrue(dashboardPage.imgProfile.isDisplayed());
        Allure.description("Admin profile image showing");
    }

    @Test(priority = 4, description = "Dashboard in the URL")
    public void isURLExists(){
        dashboardPage = new DashboardPage(driver);
        String expectedURL = "dashboard";
        String actualURL = driver.getCurrentUrl();
        Assert.assertTrue(actualURL.contains(expectedURL));
        Allure.description("Dashboard in the URL");
    }

    @AfterTest
    public void logout() throws InterruptedException {
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.btnProfileIcon.click();
        driver.findElement(By.partialLinkText("Logout")).click();
        Allure.description("Admin can logout successfully");
    }
}
