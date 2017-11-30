package application;

import application.dtos.CategoryDto;
import application.dtos.ProductDto;
import application.dtos.ProductWithSellerDto;
import application.dtos.UserDto;
import application.entities.Category;
import application.entities.Product;
import application.entities.User;
import application.repositories.CategoryRepository;
import application.repositories.ProductRepository;
import application.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    @Autowired
    public ConsoleRunner(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        callProcedure();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        ModelMapper mapper = new ModelMapper();

//        parseCategories(gson, mapper);
//        parseUsers(gson, mapper);
//        parseProducts(gson, mapper);

        List<Product> products = this.productRepository
                .findByPriceBetweenAndBuyerIsNullOrderByPrice(
                        new BigDecimal(500), new BigDecimal(1000));

        TypeMap<Product, ProductWithSellerDto> typeMap = mapper
                .createTypeMap(Product.class, ProductWithSellerDto.class)
                .addMapping(
                        src -> (src.getSeller().getFirstName() == null ? "" : src.getSeller().getFirstName() + " ") + src.getSeller().getLastName(), ProductWithSellerDto::setSellerName);
        ProductWithSellerDto pws = mapper.map(products.get(0), ProductWithSellerDto.class, typeMap.getName());

//        List<ProductWithSellerDto> pws =
//                products
//                    .stream()
//                    .map(ProductWithSellerDto::new)
//                    .collect(Collectors.toList());

        System.out.println(gson.toJson(pws));

        List<User> users = this.userRepository.getAllSellers();

        System.out.println(users.get(0).getLastName());

    }

    private void callProcedure() {
//        BigDecimal reyataz = this.productRepository.getPriceForName("REYATAZ");

//        System.out.println(reyataz);
    }

    private void parseCategories(Gson gson, ModelMapper mapper) throws FileNotFoundException {
        CategoryDto[] categoryDtos = gson.fromJson(
                new FileReader("E:\\softuni\\Lectures\\produstsshow\\src\\main\\resources\\data\\categories.json"),
                CategoryDto[].class);

        List<Category> categories = new ArrayList<Category>();
        for (CategoryDto cat : categoryDtos) {
            Category category = mapper.map(cat, Category.class);
            categories.add(category);
        }

        this.categoryRepository.save(categories);
    }

    private void parseProducts(Gson gson, ModelMapper mapper) throws FileNotFoundException {
        ProductDto[] productDtos = gson.fromJson(
                new FileReader("E:\\softuni\\Lectures\\produstsshow\\src\\main\\resources\\data\\products.json"),
                ProductDto[].class);

        List<User> all = (List<User>) this.userRepository.findAll();

        List<Product> products = new ArrayList<Product>();
        for (ProductDto cat : productDtos) {
            Product product = mapper.map(cat, Product.class);
            Random random = new Random();
            int i = random.nextInt(all.size());
            product.setSeller(all.get(i));

            products.add(product);
        }

        this.productRepository.save(products);
    }

    private void parseUsers(Gson gson, ModelMapper mapper) throws FileNotFoundException {
        UserDto[] userDtos = gson.fromJson(
                new FileReader("E:\\softuni\\Lectures\\produstsshow\\src\\main\\resources\\data\\users.json"),
                UserDto[].class);

        List<User> users = new ArrayList<User>();
        for (UserDto cat : userDtos) {
            User user = mapper.map(cat, User.class);
            users.add(user);
        }

        this.userRepository.save(users);
    }
}
