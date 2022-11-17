package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DashboardPage {
    @FindBy(className = "oxd-userdropdown-img")
    public WebElement imgProfile;

    @FindBy(className = "oxd-main-menu-item-wrapper")
    public List<WebElement> PIM;

    @FindBy(className = "oxd-select-text-input")
    public List<WebElement> dropDowns;
    @FindBy(css = "[type = submit]")
    public WebElement btnSubmit;

    @FindBy(tagName = "span")
    public List <WebElement> recordsFound;

    @FindBy(className = "oxd-userdropdown-tab")
    public WebElement btnProfileIcon;

    @FindBy(className = "oxd-input")
    public List <WebElement> searchBar;

    public DashboardPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
}
