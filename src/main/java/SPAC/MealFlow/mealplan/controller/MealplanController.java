package SPAC.MealFlow.mealplan.controller;

import SPAC.MealFlow.mealplan.controllerservice.MealplanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mealplan")
public class MealplanController {

    private final MealplanService mealplanService;

    public MealplanController(MealplanService mealplanService) {
        this.mealplanService = mealplanService;
    }

    @PostMapping
    public ResponseEntity<?> createMealplan(){
        return null;
    }

}
