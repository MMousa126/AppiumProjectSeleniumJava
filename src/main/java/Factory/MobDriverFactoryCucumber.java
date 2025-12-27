package Factory;


import Utilities.LogsUtility;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 * MobileDriverFactory is a utility class to manage the Appium server and mobile drivers
 * for Android and iOS when using Cucumber.
 * It supports parallel execution using ThreadLocal drivers, which ensures that each
 * scenario gets its own isolated driver instance.
 *
 * <p>
 * This class provides methods to:
 * <ul>
 *     <li>Start and stop the Appium server programmatically.</li>
 *     <li>Initialize mobile drivers (AppiumDriver or IOSDriver) in a ThreadLocal
 *         for each scenario.</li>
 *     <li>Safely quit or remove drivers after scenarios.</li>
 * </ul>
 * </p>
 *
 * <p>
 * When used with Cucumber, it is recommended to call the start/stop server methods
 * in {@code @Before} and {@code @After} hooks, and manage driver setup/teardown
 * per scenario to support parallel execution.
 * </p>
 */

public class MobDriverFactoryCucumber extends AbstractTestNGCucumberTests {

    private static final ThreadLocal<AppiumDriver> mobDriverThreadLocal = new ThreadLocal<>();
    private static AppiumDriverLocalService appiumServiceBuilder = null;

    /**
     * Start Appium server
     * @param appDirOS Path to Appium JS file
     * @param localIPAddress IP address to bind
     * @param portNo Port number to use
     */
    @Before(order = 0)
    public static void startUpAppiumServer(String appDirOS, String localIPAddress, int portNo) {

        if (appDirOS != null && !appDirOS.isEmpty()) {

            File appiumJsFile = new File(appDirOS);
            // Validate Appium JS file
            if (appiumJsFile.exists() && appiumJsFile.isFile()) {
                // Validate IP
                if (localIPAddress != null && !localIPAddress.isEmpty()) {
                    // Validate port
                    if (portNo > 0 && portNo <= 65535) {
                        // If server already running, return
                        if (appiumServiceBuilder == null || !appiumServiceBuilder.isRunning()) {

                            appiumServiceBuilder = new AppiumServiceBuilder()
                                    .withAppiumJS(appiumJsFile)
                                    .withIPAddress(localIPAddress)
                                    .usingPort(portNo)
                                    .build();

                            appiumServiceBuilder.start();

                            if (appiumServiceBuilder.isRunning()) {
                                LogsUtility.LoggerInfo("Appium server started at: " + appiumServiceBuilder.getUrl());
                            } else {
                                LogsUtility.LoggerInfo("Failed to start Appium server");
                                throw new RuntimeException("Failed to start Appium server");
                            }
                        } else {
                            LogsUtility.LoggerInfo("Appium server is already running at: " + appiumServiceBuilder.getUrl());
                        }
                    } else {
                        LogsUtility.LoggerInfo("Port number must be between 1 and 65535");
                        throw new IllegalArgumentException("Port number must be between 1 and 65535");
                    }
                } else {
                    LogsUtility.LoggerInfo("Local IP address cannot be null or empty");
                    throw new IllegalArgumentException("Local IP address cannot be null or empty");
                }
            } else {
                LogsUtility.LoggerInfo("Appium JS file does not exist: " + appDirOS);
                throw new IllegalArgumentException("Appium JS file does not exist: " + appDirOS);
            }
        } else {
            LogsUtility.LoggerInfo("Appium JS path cannot be null or empty");
            throw new IllegalArgumentException("Appium JS path cannot be null or empty");
        }
    }

    /**
     * Stop Appium server
     */
    @After(order = 1)
    public static void closeAppiumServer() {
        if (appiumServiceBuilder != null && appiumServiceBuilder.isRunning()) {
            appiumServiceBuilder.stop();
            Logger.getLogger("Appium server stopped successfully.");
        } else {
            System.out.println("No Appium server was running.");
        }
    }

    /**
     * Sets up a mobile driver (Android or iOS) in a ThreadLocal for parallel execution.
     *
     * @param obOSSystemAndroid_IOS   The mobile OS to use. Accepted values: "android" or "ios".
     * @param appiumUrl     The Appium server URL as a string (e.g., "http://127.0.0.1:4723").
     * @param appiumOptions The platform-specific Appium options.
     *                      Use UiAutomator2Options for Android, XCUITestOptions for iOS.
     *
     * @throws IllegalArgumentException if the obOSSystemAndroid_IOS is not "android" or "ios".
     */

    @Before(order = 1)
    public static void setUpThreadDriverMob(String obOSSystemAndroid_IOS,
                                            String appiumUrl,
                                            BaseOptions<?> appiumOptions){
        switch (obOSSystemAndroid_IOS.toLowerCase()) {
            case "android":
                try {
                    mobDriverThreadLocal.set(
                            new AppiumDriver(new URI(appiumUrl).toURL(), appiumOptions)
                    );
                } catch (URISyntaxException | MalformedURLException e){
                    e.printStackTrace();
                }
                break;
            case "ios":
                try {
                    mobDriverThreadLocal.set(
                            new IOSDriver(new URI(appiumUrl).toURL(), appiumOptions)
                    );
                } catch (URISyntaxException | MalformedURLException e){
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "Unsupported options type: " + appiumOptions.getClass()
                );
        }
    }

    public static AppiumDriver getThreadDriverMob() {
        return mobDriverThreadLocal.get();
    }

    public static void removeThreadDriverMob() {
        mobDriverThreadLocal.remove();
    }
    @After
    public static void QuitThreadDriver() {
        AppiumDriver driver = getThreadDriverMob();

        if (driver != null) {
            try {
                if (appiumServiceBuilder != null && appiumServiceBuilder.isRunning() && driver.getSessionId() != null) {
                    driver.quit();
                    LogsUtility.LoggerInfo("Driver session quit successfully.");
                } else {
                    LogsUtility.LoggerInfo("Cannot quit driver: Appium server is not running or session already closed.");
                }
            } catch (Exception e) {
                LogsUtility.LoggerInfo("Error quitting driver: " + e.getMessage());
            } finally {
                removeThreadDriverMob();
            }
        }
    }

    public static void QuitThreadDriver(String obOSSystemAndroid_IOS) {
        AppiumDriver driver = getThreadDriverMob();

        if (driver == null) {
            throw new IllegalStateException("Driver has not been initialized for this thread.");
        }

        try {
            if (appiumServiceBuilder != null && appiumServiceBuilder.isRunning() && driver.getSessionId() != null) {

                if (obOSSystemAndroid_IOS.equalsIgnoreCase("android")) {
                    if (driver instanceof AndroidDriver) {
                        driver.quit();
                        LogsUtility.LoggerInfo("Android driver quit successfully.");
                    } else {
                        throw new IllegalStateException("Expected AndroidDriver, but got " + driver.getClass().getSimpleName());
                    }
                } else if (obOSSystemAndroid_IOS.equalsIgnoreCase("ios")) {
                    if (driver instanceof IOSDriver) {
                        driver.quit();
                        LogsUtility.LoggerInfo("iOS driver quit successfully.");
                    } else {
                        throw new IllegalStateException("Expected IOSDriver, but got " + driver.getClass().getSimpleName());
                    }
                } else {
                    throw new IllegalArgumentException("Unsupported OS: " + obOSSystemAndroid_IOS);
                }

            } else {
                LogsUtility.LoggerInfo("Cannot quit driver: Appium server is not running or session already closed.");
            }
        } catch (Exception e) {
            LogsUtility.LoggerInfo("Error quitting driver: " + e.getMessage());
        } finally {
            removeThreadDriverMob();
        }
    }
}
