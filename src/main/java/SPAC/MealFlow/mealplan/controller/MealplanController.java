package SPAC.MealFlow.mealplan.controller;

import SPAC.MealFlow.auth.user.AuthUserDetails;
import SPAC.MealFlow.mealplan.dto.*;
import SPAC.MealFlow.mealplan.model.ShoppingListItems;
import SPAC.MealFlow.mealplan.service.MealPlanService;
import SPAC.MealFlow.mealplan.model.MealPlan;
import SPAC.MealFlow.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mealplan")
public class MealPlanController {

    private final MealPlanService mealplanService;

    public MealPlanController(MealPlanService mealplanService) {
        this.mealplanService = mealplanService;
    }

    @PostMapping
    public ResponseEntity<?> createMealPlan(
            @RequestBody CreateMealPlanRequestDTO request) {

        // get current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUserDetails principal = (AuthUserDetails) auth.getPrincipal();
        User currentUser = principal.getUser();

        // create mealplan using service (this persists everything)
        MealPlan created = mealplanService.createMealplan(request, currentUser);

        // map persisted entry entities to entry response DTOs
        List<CreateMealPlanEntryResponseDTO> entryResponseDTOs = created.getEntries().stream()
                .map(entry -> new CreateMealPlanEntryResponseDTO(
                        entry.getId(),
                        entry.getRecipe().getId(),
                        entry.getRecipe().getTitle(),
                        entry.getMealType(),
                        entry.getServingsOverride()
                ))
                .toList();

        // build top-level response DTO
        CreateMealPlanResponseDTO response = new CreateMealPlanResponseDTO(
                created.getId(),
                created.getName(),
                created.getUser().getId(),
                created.getUser().getName(),
                entryResponseDTOs
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/create_shopping_list/{id}")
    public ResponseEntity<?> createShoppingList(@PathVariable int id) {

        List<ShoppingListItems> shoppingListItems =
                mealplanService.getOrCreateShoppingListFromMealplan(id);

        List<ShoppingListItemDTO> dtoItems = shoppingListItems.stream()
                .map(item -> new ShoppingListItemDTO(
                        item.getIngredient().getId(),
                        item.getIngredient().getName(),
                        item.getIngredient().getCategory(),
                        item.getAmount(),
                        item.getUnit(),
                        item.isChecked()
                ))
                .toList();

        GetShoppingListResponseDTO response = new GetShoppingListResponseDTO(dtoItems);

        return ResponseEntity.ok(response);
    }

}
