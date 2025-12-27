package Factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

/* This for parallel execution and thread local */
public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void SetupThreadDriver(String browser) {

        String actualBrowser = browser.toLowerCase();

        switch (actualBrowser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                driverThreadLocal.set(new ChromeDriver(chromeOptions));
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driverThreadLocal.set(new EdgeDriver(edgeOptions));
                break;
            case "safari":
                driverThreadLocal.set(new SafariDriver());
                break;
            case "chrome-headless":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                options.addArguments("--start-maximized");
                driverThreadLocal.set(new ChromeDriver(options));
                break;
            case "edge-headless":
                EdgeOptions edgeoptions = new EdgeOptions();
                edgeoptions.addArguments("--headless");
                edgeoptions.addArguments("--start-maximized");
                driverThreadLocal.set(new EdgeDriver(edgeoptions));
                break;
            default:
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//                firefoxOptions.addArguments("--width=1680");
//                firefoxOptions.addArguments("--height=900");
//                driverThreadLocal.set(new FirefoxDriver(firefoxOptions));
                driverThreadLocal.set(new FirefoxDriver());
                driverThreadLocal.get().manage().window().maximize();

        }

    }

    public static WebDriver GetThreadDriver() {
        return driverThreadLocal.get();
    }

    public static void RemoveThreadDriver() {
        driverThreadLocal.remove();
    }

    public static void QuitThreadDriver() {

        GetThreadDriver().quit();
        RemoveThreadDriver();

    }
}
