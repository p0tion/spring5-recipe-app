package guru.springframework.bootstrap;

import guru.springframework.model.*;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.repository.UnitOfMeasureRepository;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Data
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Setter
    @Getter
    private static Map<String, UnitOfMeasure> unitOfMeasureMap = new HashMap<>();
    @Setter
    @Getter
    private static Map<String, Category> categoryMap = new HashMap<>();
    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        log.debug("In constructor");
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        log.debug("Getting recipes");
        List<Recipe> recipes = new ArrayList<>(2);
        findAllUnitOfMeasures();
        findAllCategories();
        recipes.add(buildPerfectGuacamole());
        recipes.add(buildSpicyGrilledChickenTacos());
        return recipes;
    }

    private Recipe buildSpicyGrilledChickenTacos() {
        log.debug("Making spicy grilled chicken tacos");
        Recipe tacosRecipe = Recipe
                .builder()
                .description("Spicy Grilled Chicken Taco")
                .cookTime(9)
                .prepTime(20)
                .difficulty(Difficulty.MODERATE)
                .direction("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                        "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                        "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                        "\n" +
                        "\n" +
                        "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                        "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                        "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                        "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                        "\n" +
                        "\n" +
                        "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm")
                .build();
        tacosRecipe.getCategories().add(categoryMap.get("American"));

        Note tacoNotes = Note
                .builder()
                .note("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                        "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                        "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                        "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                        "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                        "\n" +
                        "\n" +
                        "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ")
                .build();
        tacosRecipe.setNote(tacoNotes);
        tacosRecipe = fillIngredientsForTacos(tacosRecipe);
        return tacosRecipe;
    }

    private Recipe fillIngredientsForTacos(Recipe recipe) {
        recipe.addIngridient(makeIngredient("Ancho Chili Powder", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("Dried Oregano", new BigDecimal(1), unitOfMeasureMap.get("Teaspoon")));
        recipe.addIngridient(makeIngredient("Dried Cumin", new BigDecimal(1), unitOfMeasureMap.get("Teaspoon")));
        recipe.addIngridient(makeIngredient("Sugar", new BigDecimal(1), unitOfMeasureMap.get("Teaspoon")));
        recipe.addIngridient(makeIngredient("Salt", new BigDecimal(.5), unitOfMeasureMap.get("Teaspoon")));
        recipe.addIngridient(makeIngredient("Clove of Garlic, Choppedr", new BigDecimal(1), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("finely grated orange zestr", new BigDecimal(1), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("fresh-squeezed orange juice", new BigDecimal(3), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("Olive Oil", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("boneless chicken thighs", new BigDecimal(4), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("small corn tortillas", new BigDecimal(8), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("packed baby arugula", new BigDecimal(3), unitOfMeasureMap.get("Cup")));
        recipe.addIngridient(makeIngredient("medium ripe avocados, slic", new BigDecimal(2), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("radishes, thinly sliced", new BigDecimal(4), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("cherry tomatoes, halved", new BigDecimal(.5), unitOfMeasureMap.get("Pint")));
        recipe.addIngridient(makeIngredient("red onion, thinly sliced", new BigDecimal(.25), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("Roughly chopped cilantro", new BigDecimal(4), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), unitOfMeasureMap.get("Cup")));
        recipe.addIngridient(makeIngredient("lime, cut into wedges", new BigDecimal(4), unitOfMeasureMap.get("Each")));
        return recipe;
    }

    private Ingredient makeIngredient(String name, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        return Ingredient.builder().name(name).amount(amount).unitOfMeasure(unitOfMeasure).build();
    }

    private void findAllUnitOfMeasures() {
        log.debug("Getting all unitOfMeasures");
        Iterable<UnitOfMeasure> unitOfMeasureIterable = unitOfMeasureRepository.findAll();
        unitOfMeasureIterable.forEach(s -> unitOfMeasureMap.put(s.getDescription(), s));
    }

    private void findAllCategories() {
        log.debug("Getting all categories");
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        categoryIterable.forEach(s -> categoryMap.put(s.getDescription(), s));
    }

    private Recipe buildPerfectGuacamole() {
        log.debug("Making perfect guacamole");
        Recipe perfectGuacamole = Recipe
                .builder()
                .description("Perfect Guacamole")
                .prepTime(10)
                .cookTime(0)
                .difficulty(Difficulty.EASY)
                .direction("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                        "\n" +
                        "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                        "\n" +
                        "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                        "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                        "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                        "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                        "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                        "\n" +
                        "\n" +
                        "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd")
                .build();
        perfectGuacamole.getCategories().add(categoryMap.get("Mexican"));
        Note note = Note
                .builder()
                .note("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\\n\" +\n" +
                "                \"Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\\n\" +\n" +
                "                \"The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\\n\" +\n" +
                "                \"To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws")
                .build();
        perfectGuacamole.setNote(note);
        perfectGuacamole = fillIngredientsForGuacamole(perfectGuacamole);
        return perfectGuacamole;
    }

    private Recipe fillIngredientsForGuacamole(Recipe recipe) {
        recipe.addIngridient(makeIngredient("ripe avocados", new BigDecimal(2), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("Kosher salt", new BigDecimal(.5), unitOfMeasureMap.get("Teaspoon")));
        recipe.addIngridient(makeIngredient("of fresh lime juice or lemon juice", new BigDecimal(1), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("of minced red onion or thinly sliced green onion", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), unitOfMeasureMap.get("Each")));
        recipe.addIngridient(makeIngredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon")));
        recipe.addIngridient(makeIngredient("of freshly grated black pepper", new BigDecimal(2), unitOfMeasureMap.get("Dash")));
        recipe.addIngridient(makeIngredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(.5), unitOfMeasureMap.get("Each")));
        return recipe;
    }
}
