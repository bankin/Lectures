package application;

import application.dtos.*;
import application.entities.Category;
import application.entities.Product;
import application.entities.User;
import application.repositories.CategoryRepository;
import application.repositories.ProductRepository;
import application.repositories.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    @Transactional
    public void run(String... args) throws Exception {
//        callProcedure();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        ModelMapper mapper = new ModelMapper();

        parseCategories(gson, mapper);
        parseUsers(gson, mapper);
        parseProducts(gson, mapper);

        List<Product> products = this.productRepository
                .findByPriceBetweenAndBuyerIsNullOrderByPrice(
                        new BigDecimal(500), new BigDecimal(1000));

//        TypeMap<Product, ProductWithSellerDto> typeMap = mapper
//                .createTypeMap(Product.class, ProductWithSellerDto.class)
//                .addMapping(
//                        src -> (src.getSeller().getFirstName() == null ? "" : src.getSeller().getFirstName() + " ") + src.getSeller().getLastName(), ProductWithSellerDto::setSellerName);
//        ProductWithSellerDto pws = mapper.map(products.get(0), ProductWithSellerDto.class, typeMap.getName());

        List<ProductWithSellerDto> pws =
                products
                    .stream()
                    .map(ProductWithSellerDto::new)
                    .collect(Collectors.toList());

        System.out.println(gson.toJson(pws));

        List<User> users = this.userRepository.getAllSellers();
//
//        System.out.println(users.get(0).getLastName());

        List<Category> categories = (List<Category>) this.categoryRepository.findAll();

        TypeMap<Category, CategoryInfoDto> catToInfo = mapper.createTypeMap(Category.class, CategoryInfoDto.class)
                .addMapping(Category::getName, CategoryInfoDto::setCategory)
                .addMapping(src -> src.getProducts().size(), CategoryInfoDto::setProductsCount);

        Converter<Category, CategoryInfoDto> myConverter = context -> {
            Category cat = context.getSource();
            CategoryInfoDto dto = context.getDestination();

            long size = cat.getProducts().size();
            Optional<BigDecimal> sum = cat.getProducts()
                    .stream()
                    .map(Product::getPrice)
                    .reduce(BigDecimal::add);

            dto.setCategory(cat.getName());
            dto.setProductsCount(size);
            dto.setAveragePrice(sum.get().divide(new BigDecimal(size)));
            dto.setTotalRevenue(sum.get());

            return dto;
        };

//        mapper.addConverter(myConverter);

        CategoryInfoDto map = mapper.map(categories.get(0), CategoryInfoDto.class);

        System.out.println();
    }

    private void callProcedure() {
//        BigDecimal reyataz = this.productRepository.getPriceForName("REYATAZ");

//        System.out.println(reyataz);
    }

    private void parseCategories(Gson gson, ModelMapper mapper) throws FileNotFoundException {
        CategoryDto[] categoryDtos = gson.fromJson(
                new FileReader("data/categories.json"),
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
                new FileReader("data/products.json"),
                ProductDto[].class);

        List<User> all = (List<User>) this.userRepository.findAll();
        List<Category> allCat = (List<Category>) this.categoryRepository.findAll();

        List<Product> products = new ArrayList<Product>();
        for (ProductDto cat : productDtos) {
            Product product = mapper.map(cat, Product.class);
            Random random = new Random();
            int i = random.nextInt(all.size());
            product.setSeller(all.get(i));
            List<Category> categories = new ArrayList<>();
            categories.add(allCat.get(random.nextInt(allCat.size())));
            categories.add(allCat.get(random.nextInt(allCat.size())));
            categories.add(allCat.get(random.nextInt(allCat.size())));

            product.setCategories(categories);

            products.add(product);
        }

        this.productRepository.save(products);
    }

    private void parseUsers(Gson gson, ModelMapper mapper) throws FileNotFoundException {
        UserDto[] userDtos = gson.fromJson(
                new FileReader("data/users.json"),
                UserDto[].class);

        List<User> users = new ArrayList<User>();
        for (UserDto cat : userDtos) {
            User user = mapper.map(cat, User.class);
            users.add(user);
        }

        this.userRepository.save(users);
    }
}
