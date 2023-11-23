import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

public class Collector {

    private WebDriver driver;
    private String URL;

    @BeforeClass
    public static void setupWebdriverChromeDriver() {
        WebDriverManager.chromedriver().setup();

    }

    @Before
    public void setup(String URL) {
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(opt);
        this.URL = URL;
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void startSteamCollection() throws InterruptedException, IOException {
        driver.get(URL);
        WebElement countBox = driver.findElement(By.xpath("/html/body/div[1]/div[7]" +
                "/div[5]/div[1]/div[4]/div[11]/div[2]/div/div[5]/div/div[1]/div[1]/span[1]/span[last()]"));
        int count = Integer.parseInt(countBox.getText());
        WebDriverWait wait = new WebDriverWait(driver,  Duration.ofSeconds(10));

        ArrayList<ArrayList<String>> comments = new ArrayList<ArrayList<String>>();
        WebElement container;
        WebElement nextComment;
        for (int x = 0; x<count; x++){
            if (x != 0){
                nextComment = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[7]/div[5]/div[1]/div[4]/div[11]/div[2]/div/div[5]/div/div[1]/div[1]/a[2]")));
                nextComment.click();
            }
            Thread.sleep(2000);
            container = driver.findElement(By.xpath("/html/body/div[1]/div[7]/div[5]/div[1]/div[4]/div[11]/div[2]/div/div[5]/div/div[2]/div"));
            int children = container.findElements(By.xpath("./div")).size();
            ArrayList<String> page = new ArrayList<String>();
            for (int y = 1; y <= children; y++){
                WebElement comment = driver.findElement(By.xpath("/html/body/div[1]/div[7]/div[5]/div[1]/div[4]/div[11]/div[2]/div/div[5]/div/div[2]/div/div["+ y + "]/div[2]/div[2]"));
                System.out.println(comment.getText());
                page.add(comment.getText());
            }
            comments.add(page);
        }


        try {
            FileWriter writer = new FileWriter(System.getProperty("user.dir") + "\\output\\output.txt", false);
            for (int x = 0; x < comments.size(); x++){
                writer.write("///////////////////Page " + (x+1)+ "///////////////");
                writer.write(System.getProperty("line.separator"));
                for (int y = 0; y < comments.get(x).size(); y++){
                    writer.write("Comment " + (y+1) + ":");
                    writer.write(System.getProperty("line.separator"));
                    writer.write(System.getProperty("line.separator"));
                    writer.write(comments.get(x).get(y));
                    writer.write(System.getProperty("line.separator"));
                    writer.write(System.getProperty("line.separator"));
                }
            }
            writer.close();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
}