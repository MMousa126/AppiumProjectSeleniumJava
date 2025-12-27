package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Point;
import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.util.List;
import java.util.*;
// this class concerns with any additional function that can helps me (General)
public class Utility {
 
    private static final String ScreenShoot_Path = "testPages.test-outputs/Screenshoots/";




    public static void Clicking_OnElement(AppiumDriver driver, By locator) {

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.elementToBeClickable(locator));

        driver.findElement(locator).click();
    }

    public static void Clicking_OnElementVisibility(AppiumDriver driver, By locator) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        driver.findElement(locator).click();
    }
    public static void Clicking_OnElementWithoutAnyWait(AppiumDriver driver, By locator) {
        driver.findElement(locator).click();
    }
    public static void Clicking_OnElementWithVisibility(AppiumDriver driver, By locator) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        driver.findElement(locator).click();

    }
    public static void waitPageToReload(AppiumDriver driver,By locator){

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.stalenessOf(Utility.ByToWebElement(driver,locator)));
    }

    public static void refreshPage(AppiumDriver driver){
        driver.navigate().refresh();
    }

    public static void waitPageToReload(AppiumDriver driver){

        new WebDriverWait(driver, Duration.ofSeconds(200))
                .until(AppiumDriver -> ((JavascriptExecutor) AppiumDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }

    public static void waitUntilThePresenceOfElement(AppiumDriver driver, By locator){

        new WebDriverWait(driver, Duration.ofSeconds(200))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitUntilButtonIsEnabled(AppiumDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.findElement(locator).isEnabled());

    }

    public static void waitUntilThePresenceOfText(AppiumDriver driver, By locator,String text){

        new WebDriverWait(driver, Duration.ofSeconds(200))
                .until(ExpectedConditions.textToBePresentInElementLocated(locator,text));
    }

    public static String getAttribute(AppiumDriver driver,By locator, String attribute){

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        return driver.findElement(locator).getAttribute(attribute);
    }
    public static String getAttributeBoolean(AppiumDriver driver,By locator, String attribute,Boolean exp){

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementSelectionStateToBe(locator,exp));

        return driver.findElement(locator).getAttribute(attribute);
    }

    public static void SendData(AppiumDriver driver, By locator, String DataToBeSend) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        driver.findElement(locator).sendKeys(DataToBeSend);
    }

    public static void SendDataWithoutWait(AppiumDriver driver, By locator, String DataToBeSend) {

        driver.findElement(locator).sendKeys(DataToBeSend);
    }


    public static void uploadingFileUsingSendKey(AppiumDriver driver, By locator, String path){
        driver.findElement(locator).sendKeys(path);
    }


    public static void uploadingFileUsingRobot(AppiumDriver driver,By locator, String path) {

        try {
//            Utility.ScrollingUsingJS(driver,locator);
//            Thread.sleep(2000);
            Utility.ScrollingUsingJS(driver,locator);
            Thread.sleep(2000);
            driver.findElement(locator).click();
            Thread.sleep(2000);
            Robot robot = new Robot();

            StringSelection stringSelection = new StringSelection(path); //CTRL+C
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null); //CTRL+C

            robot.delay(2000);


            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(2000);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(2000);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    private static boolean isFileDialogOpened(AppiumDriver driver) {
        // Customize this method based on your application's behavior
        // Example: check if a specific element becomes visible
        return driver.getWindowHandles().size() > 1; // Example: multiple windows
    }

    public static void uploadingFileUsingRobotInfinite(AppiumDriver driver,By locator, String path) {

        try {
            boolean isUploadDialogOpened = false;
            int maxRetries = 50; // Maximum retries to avoid infinite loop
            int attempts = 0;
            while (!isUploadDialogOpened && attempts < maxRetries) {
                try {
                    driver.findElement(locator).click();
                    Thread.sleep(500); // Small delay to allow upload dialog to open

                    // Check if the file dialog is opened (can be customized)
                    // This example assumes some UI state change or element check
                    // Add your specific condition here
                    if (isFileDialogOpened(driver)) {
                        isUploadDialogOpened = true;
                    }
                } catch (Exception e) {
                    System.out.println("Retry clicking the upload button. Attempt: " + (attempts + 1));
                }
                attempts++;
            }
////            Utility.ScrollingUsingJS(driver,locator);
////            Thread.sleep(2000);
////            Utility.ScrollingUsingJS(driver,locator);
//            Thread.sleep(2000);
//            driver.findElement(locator).click();
//            Thread.sleep(2000);
            Robot robot = new Robot();

            StringSelection stringSelection = new StringSelection(path); //CTRL+C
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null); //CTRL+C

            robot.delay(2000);


            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(2000);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(2000);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static String GetText(AppiumDriver driver, By locator) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        return driver.findElement(locator).getText();
    }

    public static WebDriverWait GeneralWait(AppiumDriver driver) {

        return new WebDriverWait(driver, Duration.ofSeconds(10));

    }

    public static WebElement ByToWebElement(AppiumDriver driver, By locator) {

        return driver.findElement(locator);
    }

    public static ArrayList<WebElement> FindingElementsArrayList(AppiumDriver driver, By locator) {

        return (ArrayList<WebElement>) driver.findElements(locator);
    }



    public static void ScrollingUsingJS(AppiumDriver driver, By locator) {

        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    /* For Creating Time Stamp for name of screenshots or email vonrability */
    /* Return the time when the testPages.test case run */
    public static String GetTimeStamp() {

        return new SimpleDateFormat("yyyy-MM-dd-hh-mm-ssa").format(new Date());
    }

    public static int numberOfElementsInDom(AppiumDriver driver, By locator){
        List<WebElement> elements = driver.findElements(locator);
        return elements.size();
    }
    public static void TakingScreenShot(AppiumDriver driver, String ScreenShootName) {

        try {
            File sourcefile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            File destinationfile = new File(ScreenShoot_Path + ScreenShootName + "-" + GetTimeStamp() + ".png");

            FileHandler.copy(sourcefile, destinationfile);

            Allure.addAttachment(ScreenShootName, Files.newInputStream(Path.of(destinationfile.getPath())));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void TakingScreenShotWithURL(AppiumDriver driver, String ScreenShootName) {

        try {
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(200))
                    .takeScreenshot(driver);

            ImageIO.write(screenshot.getImage(),"PNG", new File((ScreenShoot_Path + ScreenShootName + "-" + GetTimeStamp() + ".png")));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void takeFullDesktopScreenshot(String fileName) {

        try {
            Robot robot = new Robot();
            // Capture the screen size
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            // Take the screenshot
            BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
            File file = new File(ScreenShoot_Path + fileName + "-" + GetTimeStamp() + ".png");
            ImageIO.write(screenFullImage, "png", file);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void WaitUntilTheElementIsSelected(AppiumDriver driver, By locator){

        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElements(ByToWebElement(driver,locator)));
    }
    public static void WaitUntilTheElementIsVisible(AppiumDriver driver, By locator){

        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    public static void TakingScreenShotForSpecificElement(AppiumDriver driver, By locator, String ScreenShootName) {

        try {
            File sourcefile = (driver.findElement(locator)).getScreenshotAs(OutputType.FILE);

            File destinationfile = new File(ScreenShoot_Path + ScreenShootName + "-" + GetTimeStamp() + ".png");

            FileHandler.copy(sourcefile, destinationfile);

            Allure.addAttachment(ScreenShootName, Files.newInputStream(Path.of(destinationfile.getPath())));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TakingScreenShotForSpecificElementWithYandex(AppiumDriver driver, By locator, String ScreenShootName) {
        try {
            Screenshot screenshot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver,ByToWebElement(driver,locator));

            BufferedImage bufferedImage = screenshot.getImage();
            File file = new File(ScreenShoot_Path + ScreenShootName + "-" + GetTimeStamp() + ".png");
            ImageIO.write(bufferedImage,"png",file);
            Allure.addAttachment(ScreenShootName, Files.newInputStream(Path.of(file.getPath())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenshotWithHighlight(AppiumDriver driver, WebElement element, String ScreenShootName) {
        try {
            // Step 1: Take a screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

            // Step 2: Get element's position and size
            Point point = element.getLocation();
            int elementWidth = element.getSize().getWidth();
            int elementHeight = element.getSize().getHeight();

            // Step 3: Highlight the element by drawing a circle
            Graphics2D graphics = fullImg.createGraphics();
            graphics.setColor(Color.RED);
            graphics.setStroke(new BasicStroke(5));
            // Circle around the element
            graphics.drawOval(point.getX(), point.getY(), elementWidth, elementHeight);

            // Step 4: Save the modified image
            ImageIO.write(fullImg, "png", screenshot);
            File output = new File(ScreenShoot_Path + ScreenShootName + "-" + ".png");
            FileHandler.copy(screenshot, output);


            graphics.dispose(); // Cleanup graphics resources
        } catch (Exception e) {
            System.err.println("Error while taking screenshot with highlight: " + e.getMessage());
        }
    }

        // take locator for highlighting specific element
    public static void TakingFullScreenShot(AppiumDriver driver, By locator) {
        try {
            Shutterbug.shootPage(driver, Capture.FULL_SCROLL)
                    .highlight(ByToWebElement(driver, locator))
                    .save(ScreenShoot_Path);

        } catch (Exception e) {
            LogsUtility.LoggerError(e.getMessage());
        }
    }

    public static void TakingFullScreenShot(AppiumDriver driver, String screenshotName) {
        try {
            BufferedImage screenshot = Shutterbug.shootPage(driver, Capture.FULL_SCROLL).getImage();

            // Save the image using the specified full file path
            File outputFile = new File(ScreenShoot_Path+screenshotName+".png");
            ImageIO.write(screenshot, "png", outputFile);

        } catch (Exception e) {
            LogsUtility.LoggerError(e.getMessage());
        }
    }

    public static void SelectingFromDropDownByText(AppiumDriver driver, By locator, String option) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        new Select(ByToWebElement(driver, locator)).selectByVisibleText(option);
    }
    public static void SelectingFromDropDownByTextWWait(AppiumDriver driver, By locator, String option) {

        new Select(ByToWebElement(driver, locator)).selectByVisibleText(option);
    }

    public static void SelectingFromDropDownByIndex(AppiumDriver driver, By locator, int Index) {

        new WebDriverWait(driver,Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfAllElements(ByToWebElement(driver,locator)));

        new Select(ByToWebElement(driver, locator)).selectByIndex(Index);
    }
    public static boolean isElementPresent(AppiumDriver driver, By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public static void clearField(AppiumDriver driver, By locator){
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));

        driver.findElement(locator).clear();
    }

    // why +1 because the locator start with 1 not like the arrays
    // this function return random number between 1 and the upper number
    private static int GenerateRandomNumber(int upper) {
        return (new Random().nextInt(upper)) + 1;
    }

    // why set because set have only unique numbers
    // this function could throw an infinite loop if upper is less than  noofproduct
    // the upper should be more than the no of the products
    public static Set<Integer> GenerateUniqueRandomNumbers(int upper, int noofproduct) {

        Set<Integer> random = new HashSet<>();


        if (upper <= noofproduct) {
            LogsUtility.LoggerError("the Upper number is less than the number of the product");
            throw new IllegalArgumentException("The number of products should be smaller than the upper bound.");
        } else {
            while (random.size() < noofproduct) {
                random.add(GenerateRandomNumber(upper));
            }
            return random;

        }
    }

    public static boolean VerifyCurrentURLToExpected(AppiumDriver driver, String expectedURL) {

        try {
            GeneralWait(driver).until(ExpectedConditions.urlToBe(expectedURL));

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean ComparingTheCurrentURLToExpected(AppiumDriver driver, String expectedURL) {

        return driver.getCurrentUrl().equals(expectedURL);
    }

    public static String getCurrentURL (AppiumDriver driver){
        return driver.getCurrentUrl();
    }
    // for regression testPages.test for storing only one latest file from logs
    public static File GetLatestFile(String folderpath) {
        File folder = new File(folderpath);
        File[] files = folder.listFiles();
        assert files != null;
        if (files.length == 0) {
            return null;
        }
        Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

        return files[0];
    }

    // we want to make a session for login instead of every time I have to log in I will log in inside before testPages.test
    // So I don't need to log in every time using cookies

    /* Take all Cookies from The Website */
    public static Set<Cookie> GetAllCookies(AppiumDriver driver) {

        return driver.manage().getCookies();
    }

    /* Injecting all the cookies to the driver */
    public static void InjectCookies(AppiumDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().addCookie(cookie);
        }
    }
    public static void DeleteCookies(AppiumDriver driver, Set<Cookie> cookies) {
        for (Cookie cookie : cookies) {
            driver.manage().deleteCookie(cookie);
        }
    }

    public static void DeleteAllCookies(AppiumDriver driver, Set<Cookie> cookies) {
            driver.manage().deleteAllCookies();
    }
    /**
     * Function to get the first file name containing a partial name in the specified directory.
     *
     * @param directoryPath The path to the directory.
     * @param partialName   The partial name to search for.
     * @return The first matching file name, or null if no matches are found.
     */
    public static String getFirstFileByPartialName(String directoryPath, String partialName) {
        File directory = new File(directoryPath);
        // Ensure the path is a directory
        if (directory.isDirectory()) {
            // List files matching the partial name
            File[] matchingFiles = directory.listFiles((dir, name) -> name.contains(partialName));

            if (matchingFiles != null && matchingFiles.length > 0) {
                // Return the name of the first matching file
                return matchingFiles[0].getName();
            }
        }
        // Return null if no matching files are found
        return null;
    }

    public static void clearFile(String filePath){
        try {
            new File(filePath);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Function to get file names containing a partial name in the specified directory.
     *
     * @param directoryPath The path to the directory.
     * @param partialName   The partial name to search for.
     * @return An array of matching file names.
     */
    public static String[] getFilesByPartialName(String directoryPath, String partialName) {
        File directory = new File(directoryPath);

        // Ensure the path is a directory
        if (directory.isDirectory()) {
            // List files matching the partial name
            File[] matchingFiles = directory.listFiles((dir, name) -> name.contains(partialName));

            if (matchingFiles != null && matchingFiles.length > 0) {
                // Convert File array to String array of file names
                String[] fileNames = new String[matchingFiles.length];
                for (int i = 0; i < matchingFiles.length; i++) {
                    fileNames[i] = matchingFiles[i].getName();
                }
                return fileNames;
            }
        }
        // Return an empty array if no matching files are found
        return new String[0];
    }


    public static String TodayGeorgianDate(){
        return LocalDate.now().toString() ;
    }

    public static String TodayHijriDate(){
        return HijrahDate.now().toString().replace("Hijrah-umalqura AH ","") ;
    }

    /* Like injecting Registration for the preconditions */
    public static String InjectRequestUsingPostAPI(String postrequest_url, String contantrequesttype, String bodytobeposted) {

        return RestAssured
                .given()
                .contentType(contantrequesttype)
                .body(bodytobeposted)
                .when()
                .post(postrequest_url)
                .then()
                .extract()
                .body()
                .asString();
    }

    public static void typeString(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            typeCharacter(robot, c);
            robot.delay(100);
        }
    }

    public static void typeCharacter(Robot robot, char character) {
        try {
            boolean upperCase = Character.isUpperCase(character);
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(character);

            if (keyCode == KeyEvent.VK_UNDEFINED) {
                throw new IllegalArgumentException("Cannot type character: " + character);
            }

            if (upperCase) robot.keyPress(KeyEvent.VK_SHIFT);

            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);

            if (upperCase) robot.keyRelease(KeyEvent.VK_SHIFT);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
