
package selenium;

import static com.neotys.selenium.proxies.NLWebDriverFactory.addProxyCapabilitiesIfNecessary;
import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.neotys.selenium.proxies.NLWebDriver;
import com.neotys.selenium.proxies.NLWebDriverFactory;

public class TestNeoload{

    //EXAMPLE 1 NEOLOAD DOCUMENTATION
    private static final String CHROME_DRIVER_PATH = "C:\\BrowseDrivers\\chromedriver.exe";

    static{
        final File file = new File(CHROME_DRIVER_PATH);
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
    }

    static NLWebDriver driver;
    String currentUrl = "https://petstore.octoperf.com/actions/Catalog.action";

    @BeforeClass
    public static void before() {
    System.setProperty("nl.selenium.proxy.mode", "Design");
    final ChromeDriver webDriver = new ChromeDriver(addProxyCapabilitiesIfNecessary(new DesiredCapabilities()));
        final String projectPath = "C:\\NeoloadProjects\\SeleniumNeoload\\SeleniumNeoload.nlp";
        driver = NLWebDriverFactory.newNLWebDriver(webDriver, "SeleniumUserPath", projectPath);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @Test
    public void testSubmit(){

        //Load App and check Title
        driver.startTransaction("LoadApp");
        driver.get(currentUrl);
        driver.stopTransaction();
        String ActualTitle = driver.getTitle();
        String ExpectedTitle = "JPetStore Demo";
        Assert.assertEquals(ActualTitle, ExpectedTitle);
        System.out.println("[TITLE] - Assertion passed!");


        //Button SigIn
        driver.startTransaction("ButtonSigIn");
        driver.findElement(By.xpath("//*[@id=\"MenuContent\"]/a[2]")).click();
        driver.stopTransaction();

        //Click register
        driver.startTransaction("ButtonRegisterNow");
        driver.findElement(By.xpath("//*[@id=\"Catalog\"]/a")).click();
        boolean PasswordText = driver.getPageSource().contains("Password");
        Assert.assertTrue(PasswordText);
        System.out.println("[TEXT PASSWORD] - Assertion passed!");
        driver.stopTransaction();

        //Fill UserFormFields
        driver.startTransaction("DataUserForm");
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("ricardo2021");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("1234");
        driver.findElement(By.name("repeatedPassword")).clear();
        driver.findElement(By.name("repeatedPassword")).sendKeys("1234");
        boolean UserInformationText = driver.getPageSource().contains("User Information");
        Assert.assertTrue(UserInformationText);
        System.out.println("[TEXT User Information] - Assertion passed!");
        driver.stopTransaction();

        //Select Category

        //Add to cart
        //Proceed to checkout

        //Finish

        //Logout
        //Search Something
        /*
        driver.startTransaction("SearchField");
        driver.findElement(By.name("keyword")).clear();
        driver.findElement(By.name("keyword")).sendKeys("Fish");
        driver.findElement(By.name("searchProducts")).click();
        driver.stopTransaction();
        */
    }

    @AfterClass
    public static void after() {
            driver.quit();
    }

}
