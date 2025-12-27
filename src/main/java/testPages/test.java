import Utilities.Utility;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class test {

    @Test
    public void test() throws MalformedURLException, URISyntaxException  {

        // starting the appium server
        AppiumDriverLocalService appiumServiceBuilder = new AppiumServiceBuilder()
                .withAppiumJS(new File("C:\\Users\\M.Moussa\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();

        appiumServiceBuilder.start();



        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("MousaSmartPhone")
                .setApp("D:\\Appium Setup\\Appium Projects\\AppiumProject\\src\\main\\resources\\ApiDemos-debug.apk");

        AndroidDriver androidDriver = new AndroidDriver(
                new URI("http://127.0.0.1:4723").toURL(),
                options
        );

        Utility.Clicking_OnElement(androidDriver, AppiumBy.accessibilityId("Preference"));
//        androidDriver.quit();

        appiumServiceBuilder.close();


    }
}
