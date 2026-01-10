package testPages;

import Factory.MobileDriverFactory;
import Utilities.Utility;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.junit.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

public class test {

    @Test
    public void test() throws MalformedURLException, URISyntaxException  {

        // starting the appium server
//        AppiumDriverLocalService appiumServiceBuilder = new AppiumServiceBuilder()
//                .withAppiumJS(new File("C:\\Users\\M.Moussa\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
//                .withIPAddress("127.0.0.1")
//                .usingPort(4723)
//                .build();
//        appiumServiceBuilder.start();
        MobileDriverFactory.startUpAppiumServer
                ("C:\\Users\\M.Moussa\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"
                ,"127.0.0.1"
                ,4723);




        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName("MousaSmartPhone")
                .setApp("D:\\Appium Setup\\Appium Projects\\AppiumProject\\src\\main\\resources\\ApiDemos-debug.apk");

//        AndroidDriver androidDriver = new AndroidDriver(
//                new URI("http://127.0.0.1:4723").toURL(),
//                options
//        );

        MobileDriverFactory.setUpThreadDriverMob("android","http://127.0.0.1:4723",options);


//        Utility.Clicking_OnElement(MobileDriverFactory.getThreadDriverMob("Android"), AppiumBy.accessibilityId("Preference"));
        Utility.Clicking_OnElement(MobileDriverFactory.getThreadDriverMob("Android"), AppiumBy.accessibilityId("Views"));
//        Utility.scrollToElementUIAutomator(MobileDriverFactory.getThreadDriverMob("Android"),"Webview3");

        Utility.Clicking_OnElement(MobileDriverFactory.getThreadDriverMob("Android"), AppiumBy.accessibilityId("Gallery"));
        Utility.Clicking_OnElement(MobileDriverFactory.getThreadDriverMob("Android"), AppiumBy.accessibilityId("1. Photos"));
        Utility.Clicking_OnElement(MobileDriverFactory.getThreadDriverMob("Android"), AppiumBy.xpath("(//android.widget.ImageView)[1]"));
//        Utility.swipeDirections(MobileDriverFactory.getThreadDriverMob("Android"),By.xpath("(//android.widget.ImageView)[1]"),"left");
//        System.out.println("|||||||||||||||||||||||||" + Utility.getAttribute(MobileDriverFactory.getThreadDriverMob("Android"),
//                By.xpath("(//android.widget.ImageView)[3]"),"focusable")+"||||||||||||||||||||||||||||");
//        //        androidDriver.quit();

//        appiumServiceBuilder.close();


//        MobileDriverFactory.QuitThreadDriver();
//        MobileDriverFactory.closeAppiumServer();





    }
}
