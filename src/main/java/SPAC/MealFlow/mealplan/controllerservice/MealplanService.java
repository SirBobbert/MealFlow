package SPAC.MealFlow.mealplan.controllerservice;

import SPAC.MealFlow.mealplan.repository.MealplanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MealplanService {

    @Autowired
    private final MealplanRepository mealplanRepository;

    public MealplanService(MealplanRepository mealplanRepository) {
        this.mealplanRepository = mealplanRepository;
    }


}
