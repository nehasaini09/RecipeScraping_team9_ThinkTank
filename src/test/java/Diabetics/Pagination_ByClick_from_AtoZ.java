package Diabetics;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//import Utilities.ExcelUtility;

public class Pagination_ByClick_from_AtoZ {
	public static void main(String[] args) throws InterruptedException, IOException {
		
		WebDriver driver;
		
		driver = new ChromeDriver();
		driver.get("https://www.tarladalal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().deleteAllCookies();
		
		driver.findElement(By.xpath("//a[@title='Recipea A to Z']")).click();
		
		List<WebElement> recipesAtoZ =	driver.findElements(By.cssSelector("td[style='white-space:nowrap;']>a:nth-child(1)"));
//		System.out.println("RecipesAtoZ Size:" + recipesAtoZ.size());
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 700)");
		
	    List<String> allRecipeNames = new ArrayList<>();
        List<String> allRecipeIds = new ArrayList<>();
	   
		
		for(char k= 'A' ;k<= 'Z';k++)
		  {
			 int next_page = 1;
	    	  String paginationSelector ="https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+ k + "&pageindex="+next_page;		       
			  driver.get(paginationSelector);
			  
			 List<WebElement> pages_full_data =	driver.findElements(By.xpath("//div[contains(text(),'Goto Page')]/a"));
			 
			 if(pages_full_data.isEmpty()) {
				 System.out.println("No recipe found");
				 continue;
			 }
				
			 
		     WebElement element_1_XX = pages_full_data.get(pages_full_data.size()-1);
		     
			 String pagenation_last_page_XX = element_1_XX.getText();
		     int last_page_number = Integer.parseInt (pagenation_last_page_XX);
			 System.out.println("Page Data: " + "for alphabet " + k + " " + pagenation_last_page_XX);
		     for (int j = 0 ; j <= last_page_number - 1; j++)
		     {
		    	  paginationSelector ="https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith="+ k + "&pageindex="+next_page;		       
				  driver.get(paginationSelector);
		    	  List<WebElement> curr_page = driver.findElements(By.xpath("//a[@class='rescurrpg']"));
			      WebElement element_page = curr_page.get(0);
			      String pagenation_last_page = element_page.getText();
			      int pagenation_last_page_integer = Integer.parseInt (pagenation_last_page);
			      next_page = pagenation_last_page_integer + 1;
			      System.out.println("Last Page: " + pagenation_last_page);
				  List<WebElement> recipeNames = driver.findElements(By.xpath("//div//span[@class='rcc_recipename']"));
			      List<WebElement> recipeId = driver.findElements(By.xpath("//div//span[contains(text(),'Recipe#')]"));
			     
//			      System.out.println("Recipe Size:" + recipeNames.size());
			   for (int i = 0; i < recipeNames.size(); i++)
		         {
		           WebElement element = recipeNames.get(i);
		           WebElement id = recipeId.get(i);
		           String recipeName = element.getText();
		           String recipeid = id.getText();
//		           System.out.println("Recipe Name: " + recipeName);
	              
		           String firstPart_recipeid = recipeid.split(" ")[1];
	            
		           allRecipeNames.add(recipeName);
	               allRecipeIds.add(firstPart_recipeid);
		          
		         
		     }}
		  }
	   // ExcelUtility.writeDataToExcel(allRecipeNames, allRecipeIds);
	    driver.quit();
	}
}