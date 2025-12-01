package SPAC.MealFlow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FullstackApplication {


    // TODO:
    // Creating recipe needs ingredients to be fetched from the database
    // Create a populate DB script to add some ingredients, users and recipes to the DB at start up


    // Make so serving/size can be altered
    // add week day to mealplan
	public static void main(String[] args) {
		SpringApplication.run(FullstackApplication.class, args);
	}

}
