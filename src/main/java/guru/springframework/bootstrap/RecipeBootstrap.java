package guru.springframework.bootstrap;

import guru.springframework.model.*;
import guru.springframework.repository.CategoryRepository;
import guru.springframework.repository.RecipeRepository;
import guru.springframework.repository.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private static Map<String, UnitOfMeasure> unitOfMeasureMap = new HashMap<>();
    private static Map<String, Category> categoryMap = new HashMap<>();
    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>(2);
        findAllUnitOfMeasures();
        findAllCategories();
        recipes.add(buildPerfectGuacamole());
        recipes.add(buildSpicyGrilledChickenTacos());
        return recipes;
    }

    private Recipe buildSpicyGrilledChickenTacos() {
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);
        tacosRecipe.getCategories().add(categoryMap.get("American"));

        tacosRecipe.setDirection("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
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
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Note tacoNotes = new Note();
        tacoNotes.setNote("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacoNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNote(tacoNotes);
        tacosRecipe.setIngredients(fillIngredientsForTacos(tacosRecipe));
        return tacosRecipe;
    }

    private Set<Ingredient> fillIngredientsForTacos(Recipe recipe) {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("Ancho Chili Powder", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("Dried Oregano", new BigDecimal(1), unitOfMeasureMap.get("Teaspoon"), recipe));
        ingredients.add(new Ingredient("Dried Cumin", new BigDecimal(1), unitOfMeasureMap.get("Teaspoon"), recipe));
        ingredients.add(new Ingredient("Sugar", new BigDecimal(1), unitOfMeasureMap.get("Teaspoon"), recipe));
        ingredients.add(new Ingredient("Salt", new BigDecimal(".5"), unitOfMeasureMap.get("Teaspoon"), recipe));
        ingredients.add(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("finely grated orange zestr", new BigDecimal(1), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("Olive Oil", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("boneless chicken thighs", new BigDecimal(4), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("small corn tortillas", new BigDecimal(8), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("packed baby arugula", new BigDecimal(3), unitOfMeasureMap.get("Cup"), recipe));
        ingredients.add(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("radishes, thinly sliced", new BigDecimal(4), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), unitOfMeasureMap.get("Pint"), recipe));
        ingredients.add(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), unitOfMeasureMap.get("Cup"), recipe));
        ingredients.add(new Ingredient("lime, cut into wedges", new BigDecimal(4), unitOfMeasureMap.get("Each"), recipe));
        return ingredients;
    }

    private void findAllUnitOfMeasures() {
        Iterable<UnitOfMeasure> unitOfMeasureIterable = unitOfMeasureRepository.findAll();
        unitOfMeasureIterable.forEach(s -> unitOfMeasureMap.put(s.getDescription(), s));
    }

    private void findAllCategories() {
        Iterable<Category> categoryIterable = categoryRepository.findAll();
        categoryIterable.forEach(s -> categoryMap.put(s.getDescription(), s));
    }

    private Recipe buildPerfectGuacamole() {
        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setDifficulty(Difficulty.EASY);
        perfectGuacamole.getCategories().add(categoryMap.get("Mexican"));
        perfectGuacamole.setDirection("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
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
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");
        Note note = new Note();
        note.setNote("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\\n\" +\n" +
                "                \"Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\\n\" +\n" +
                "                \"The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\\n\" +\n" +
                "                \"To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        note.setRecipe(perfectGuacamole);
        perfectGuacamole.setNote(note);
        perfectGuacamole.setIngredients(fillIngredientsForGuacamole(perfectGuacamole));
        return perfectGuacamole;
    }

    private Set<Ingredient> fillIngredientsForGuacamole(Recipe recipe) {
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(new Ingredient("ripe avocados", new BigDecimal(2), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("Kosher salt", new BigDecimal(.5), unitOfMeasureMap.get("Teaspoon"), recipe));
        ingredients.add(new Ingredient("of fresh lime juice or lemon juice", new BigDecimal(1), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("of minced red onion or thinly sliced green onion", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), unitOfMeasureMap.get("Each"), recipe));
        ingredients.add(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), unitOfMeasureMap.get("Tablespoon"), recipe));
        ingredients.add(new Ingredient("of freshly grated black pepper", new BigDecimal(2), unitOfMeasureMap.get("Dash"), recipe));
        ingredients.add(new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(.5), unitOfMeasureMap.get("Each"), recipe));
        return ingredients;
    }

    private byte[] readImage(String fileName) {
        byte[] data = null;
        try {
            BufferedImage bImage = ImageIO.read(new File(fileName));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "jpg", bos);
            data = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void fillCategories(Set<Category> categories, String... categoryList) {
        Arrays.stream(categoryList).forEach(s -> categories.add(new Category(s)));
    }

    public static Map<String, UnitOfMeasure> getUnitOfMeasureMap() {
        return unitOfMeasureMap;
    }

    public static void setUnitOfMeasureMap(Map<String, UnitOfMeasure> unitOfMeasureMap) {
        RecipeBootstrap.unitOfMeasureMap = unitOfMeasureMap;
    }

    public static Map<String, Category> getCategoryMap() {
        return categoryMap;
    }

    public static void setCategoryMap(Map<String, Category> categoryMap) {
        RecipeBootstrap.categoryMap = categoryMap;
    }
}
