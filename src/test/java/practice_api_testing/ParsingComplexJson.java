package practice_api_testing;

import io.restassured.path.json.JsonPath;
import payloads.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.response.ResponseOptions.*;

public class ParsingComplexJson {

	public static void main(String[] args) {
		
		
		JsonPath js = new JsonPath(Payload.complextJsonCourseAPI());
		
		// Problems to solve 
		
		//1. Print No of courses returned by API
		
		    int countOfCourses =  js.getInt("courses.size()");
		    
		    System.out.println(countOfCourses);
		
		// 2.Print Purchase Amount
		    
		   int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		   
		    System.out.println(purchaseAmount);
		
		// 3. Print Title of the first course
		    
		    String firstCourseTitle = js.getString("courses[0].title");
		    
		    System.out.println(firstCourseTitle);
		
		//4. Print All course titles and their respective Prices
		    
		    for ( int i =0; i < countOfCourses; i++)
		    {
		    	 String courseTitle = js.get("courses["+i+"].title");
		    	 int coursePrice = js.getInt("courses["+i+"].price");
		    	 
		    	 System.out.print(courseTitle+" : ");
		    	 System.out.println(coursePrice);
		    }
		
		//5. Print no of copies sold by RPA Course
		    
		    System.out.println("Print no of copies sold by RPA Course");
		    
		    int courseCopies=0;
		    
		    for ( int i =0; i < countOfCourses; i++)
		    {
		    	 String courseTitle = js.get("courses["+i+"].title");
		    	 
		    	 if ( courseTitle.equalsIgnoreCase("RPA"))
		    	 {
		    		 courseCopies = js.getInt("courses["+i+"].copies");
		    		 break;
		    	 }
		    	
		    }
		    
		    System.out.println(courseCopies);
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		    
		    System.out.println("Verify if Sum of all Course prices matches with Purchase Amount");
		    
		    int sumOfCoursePrices=0;
		    
		    for ( int i =0; i < countOfCourses; i++)
		    {
		    	 
		    	 int coursePrice = js.getInt("courses["+i+"].price");
		    	 int copies = js.getInt("courses["+i+"].copies");
		    	 
		    	 sumOfCoursePrices = sumOfCoursePrices + coursePrice*copies;
		    	 
		    
		    }
		    
		    System.out.println(sumOfCoursePrices);
		    if(sumOfCoursePrices==purchaseAmount)  System.out.println("successfull");
		    

	}

}
