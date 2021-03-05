package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Order;
import tacos.Taco;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
    private IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            TacoRepository designRepo) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));

        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    @GetMapping
    public String showDesignForm(Model model) {
        return "design";
    }
    //tag::filterByType[]
    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
    //end::filterByType[]

    @PostMapping
    public String processDesign(@Valid  Taco design, Errors errors, @ModelAttribute Order order) {
        if (errors.hasErrors()) {
            log.info(errors.toString());
            return "design";
        }
        // Save the taco design...
        // We'll do this in chapter 3
        Taco saved = designRepo.save(design);
        log.info("Processing design: " + saved);
        order.addDesign(saved);
        log.info("Processing order: " + order);
        return "redirect:/orders/current";
    }
}
