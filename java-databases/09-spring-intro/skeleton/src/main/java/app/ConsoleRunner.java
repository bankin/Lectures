package app;

import app.model.ingredients.BasicIngredient;
import app.model.shampoos.BasicShampoo;
import app.repositories.IngredientRepository;
import app.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.util.*;

@SpringBootApplication
public class ConsoleRunner implements CommandLineRunner {

    private ShampooRepository shampooRepository;
    private IngredientRepository ingredientRepository;

    @Autowired
    public ConsoleRunner(ShampooRepository shampooRepository, IngredientRepository ingredientRepository) {
        this.shampooRepository = shampooRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        BasicIngredient apple = this.ingredientRepository.findOne(1L);
        List<BasicIngredient> basicIngredients = Collections.singletonList(apple);

        List<BasicShampoo> byIngredient =
                this.shampooRepository.findByHavingIngredients(basicIngredients);

//        List<BasicShampoo> byName =
//                this.shampooRepository.findByHavingName("Fresh it Up!");

        for (BasicShampoo s : byIngredient) {
            System.out.println(s.getBrand());
        }

        byIngredient =
                this.shampooRepository.findByHavingIngredientIds(
                        Collections.singletonList(1L));

        for (BasicShampoo s : byIngredient) {
            System.out.println(s.getBrand());
        }

        List<String> names = new ArrayList<String>();
        names.add("Aloe Vera");
        names.add("Apple");
        names.add("Nettle");
        names.add("asd");

        List<BasicIngredient> byNameIn = this.ingredientRepository.findByNameIn(names);

        for (BasicIngredient i : byNameIn) {
            System.out.println(i.getName());
        }

        System.out.println(
                this.shampooRepository.countByPriceLessThan(new BigDecimal(8.50)));

//        this.ingredientRepository.deleteByName("Nettle");

        this.ingredientRepository.updateAllPrices();
    }
}
