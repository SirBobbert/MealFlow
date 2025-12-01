package SPAC.MealFlow.mealplan.service;

import SPAC.MealFlow.mealplan.dto.CreateMealPlanEntryDTO;
import SPAC.MealFlow.mealplan.dto.CreateMealPlanRequestDTO;
import SPAC.MealFlow.mealplan.model.MealPlan;
import SPAC.MealFlow.mealplan.model.MealPlanEntries;
import SPAC.MealFlow.mealplan.model.ShoppingListItems;
import SPAC.MealFlow.mealplan.repository.MealPlanRepository;
import SPAC.MealFlow.mealplan.repository.ShoppingListItemsRepository;
import SPAC.MealFlow.recipe.model.Ingredient;
import SPAC.MealFlow.recipe.model.RecipeIngredient;
import SPAC.MealFlow.recipe.service.RecipeService;
import SPAC.MealFlow.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MealPlanService {

    private final MealPlanRepository mealplanRepository;
    private final RecipeService recipeService;
    private final ShoppingListItemsRepository shoppingListItemsRepository;

    public MealPlanService(MealPlanRepository mealplanRepository, RecipeService recipeService, ShoppingListItemsRepository shoppingListItemsRepository) {
        this.mealplanRepository = mealplanRepository;
        this.recipeService = recipeService;
        this.shoppingListItemsRepository = shoppingListItemsRepository;
    }


    public List<ShoppingListItems> createShoppingListFromMealplan(int id) {

        // load mealplan
        MealPlan mealPlan = mealplanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mealplan not found: " + id));

        Map<Ingredient, ShoppingListItems> shoppingListMap = new HashMap<>();

        for (MealPlanEntries entry : mealPlan.getEntries()) {
            for (RecipeIngredient ri : entry.getRecipe().getRecipeIngredients()) {

                Ingredient ingredient = ri.getIngredient();

                // get existing shopping list item for this ingredient, if any
                ShoppingListItems existingItem = shoppingListMap.get(ingredient);

                // if no ingredient exists in the shopping list, create a new one and put it in the map
                if (existingItem == null) {
                    ShoppingListItems newItem = ShoppingListItems.builder()
                            .mealPlan(mealPlan)
                            .ingredient(ingredient)
                            .amount(ri.getAmount())
                            .unit(ri.getUnit())
                            .checked(false) // if you have a default
                            .build();

                    shoppingListMap.put(ingredient, newItem);
                } else {
                    // if the ingredient exists, aggregate the amount
                    existingItem.setAmount(existingItem.getAmount() + ri.getAmount());
                }
            }
        }

        // save once, after building the full list
        List<ShoppingListItems> itemsToSave = new ArrayList<>(shoppingListMap.values());
        shoppingListItemsRepository.saveAll(itemsToSave);

        return itemsToSave;
    }

    public List<ShoppingListItems> getOrCreateShoppingListFromMealplan(int mealPlanId) {

        // first, try to find existing shopping list items in DB
        List<ShoppingListItems> existing =
                shoppingListItemsRepository.findByMealPlanId(mealPlanId);

        if (!existing.isEmpty()) {
            return existing;
        }

        // if none exist, create them from the mealplan
        return createShoppingListFromMealplan(mealPlanId);
    }


    @Transactional
    public MealPlan createMealplan(CreateMealPlanRequestDTO request, User currentUser) {

        // build base mealplan entity
        MealPlan mealPlan = MealPlan.builder()
                .user(currentUser)
                .name(request.name())
                .build();

        // create entries and attach to mealplan
        for (CreateMealPlanEntryDTO entryDTO : request.listOfEntries()) {

            // build entry entity
            MealPlanEntries entry = MealPlanEntries.builder()
                    .recipe(recipeService.getRecipeEntityById(entryDTO.recipeId()))
                    .createdAt(new Date())
                    .mealType(entryDTO.mealType())
                    .servingsOverride(entryDTO.servingsOverride())
                    // .dayOfWeek(entryDTO.dayOfWeek()) // if you add it to DTO
                    .build();

            mealPlan.addEntry(entry);
        }

        return mealplanRepository.save(mealPlan);
    }
}
