package selenium_docker.selenium_docker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless");
		options.addArguments("--disable-gpu");
		options.addArguments("--no-sandbox");
		options.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.google.com");
		Thread.sleep(5000);
		System.out.println("Title: " + driver.getTitle());
		driver.quit();
	}
}
