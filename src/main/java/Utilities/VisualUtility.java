package Utilities;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class VisualUtility {

    private static final String baseLineScreenshot = "test-outputs/VisualTest/baseLineScreenshot/";
    private static final String expectedScreenshot =  "test-outputs/VisualTest/expectedScreenshot/";
    private static final String diffImagePath = "test-outputs/VisualTest/differentialImage/";


    public static void TakingScreenShotVTBaseLine(WebDriver driver, String ScreenShootName) {

        try {
            BufferedImage screenshot = Shutterbug.shootPage(driver, Capture.FULL_SCROLL).getImage();

            // Save the image using the specified full file path
            File outputFile = new File(baseLineScreenshot+ScreenShootName+".png");
            ImageIO.write(screenshot, "png", outputFile);
            Allure.addAttachment(ScreenShootName, Files.newInputStream(Path.of(baseLineScreenshot+ScreenShootName+".png")));

        } catch (Exception e) {
            LogsUtility.LoggerError(e.getMessage());
        }
    }
    public static void TakingScreenShotVTExpected(WebDriver driver, String ScreenShootName) {

        try {
            BufferedImage screenshot = Shutterbug.shootPage(driver, Capture.FULL_SCROLL).getImage();

            // Save the image using the specified full file path
            File outputFile = new File(expectedScreenshot+ScreenShootName+".png");
            ImageIO.write(screenshot, "png", outputFile);
            Allure.addAttachment(ScreenShootName, Files.newInputStream(Path.of(expectedScreenshot+ScreenShootName+".png")));

        } catch (Exception e) {
            LogsUtility.LoggerError(e.getMessage());
        }
    }
    public static boolean areImageEqual(String baseScreenshotFilename, String expectedScreenShotFilename, String diffFilename){

        BufferedImage bufferedImageBase = null;
        BufferedImage bufferedImageExpected = null;

        try {
            bufferedImageBase = ImageIO.read(new File(baseLineScreenshot +baseScreenshotFilename+".png"));
            bufferedImageExpected = ImageIO.read(new File(expectedScreenshot+expectedScreenShotFilename+".png"));
        }catch (Exception e){
            e.printStackTrace();
        }
        ImageDiff diff = new ImageDiffer().makeDiff(bufferedImageBase,bufferedImageExpected);

        if (diff.hasDiff()){
            BufferedImage bufferedImage = diff.getMarkedImage();

            try {
                ImageIO.write(bufferedImage,"png",new File(diffImagePath+diffFilename+".png"));
                Allure.addAttachment(diffFilename, Files.newInputStream(Path.of(diffImagePath+diffFilename+".png")));

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return !(diff.hasDiff());
    }
}
