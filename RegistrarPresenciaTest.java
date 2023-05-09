// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class RegistrarPresenciaTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void registrarPresencia() {
    driver.get("https://localhost:8443/");
    driver.manage().window().setSize(new Dimension(1552, 832));
    driver.findElement(By.cssSelector("#kiosko > span")).click();
    driver.findElement(By.cssSelector("#boton_dni > span")).click();
    driver.findElement(By.id("dni")).click();
    driver.findElement(By.id("dni")).sendKeys("25");
    driver.findElement(By.id("confirmar")).click();
    driver.findElement(By.id("boton_dni")).click();
    driver.findElement(By.id("dni")).click();
    driver.findElement(By.id("dni")).sendKeys("49586372D");
    driver.findElement(By.id("confirmar")).click();
    driver.findElement(By.linkText("Volver")).click();
    driver.findElement(By.cssSelector("#boton_dni > span")).click();
    driver.findElement(By.id("dni")).click();
    driver.findElement(By.id("dni")).sendKeys("12345678A");
    driver.findElement(By.id("confirmar")).click();
    driver.findElement(By.id("imprimir")).click();
    driver.findElement(By.cssSelector("#boton_tarjeta > span")).click();
    driver.findElement(By.id("ntarjeta")).click();
    driver.findElement(By.id("ntarjeta")).sendKeys("205");
    driver.findElement(By.id("confirmar")).click();
    driver.findElement(By.id("boton_tarjeta")).click();
    driver.findElement(By.id("ntarjeta")).click();
    driver.findElement(By.id("ntarjeta")).sendKeys("888899990001");
    driver.findElement(By.id("confirmar")).click();
    driver.findElement(By.linkText("Volver")).click();
    driver.findElement(By.cssSelector("#boton_tarjeta > span")).click();
    driver.findElement(By.id("ntarjeta")).click();
    driver.findElement(By.id("ntarjeta")).sendKeys("123456789012");
    driver.findElement(By.id("confirmar")).click();
    driver.findElement(By.id("datos_paciente")).click();
  }
}
