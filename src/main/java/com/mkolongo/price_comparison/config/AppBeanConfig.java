package com.mkolongo.price_comparison.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppBeanConfig {

    @Bean
    @SuppressWarnings("unchecked")
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();

//        Converter<Set<Category>, Set<String>> getCategoryName = ctx -> ctx.getSource()
//                .stream()
//                .map(NamedEntity::getName)
//                .collect(Collectors.toSet());
//
//        Converter<LocalDateTime, String> dateFormatter = ctx -> ctx.getSource()
//                .format(DateTimeFormatter.ofPattern("EE yyyy-MMM-dd hh:mm a"));

//        mapper.createTypeMap(UserRegisterModel.class, UserServiceModel.class)
//                .addMappings(m -> m.map(source -> source.getFirstName() + " " + source.getLastName(),
//                        UserServiceModel::setUsername));
//                        (destination, value) -> destination.setUsername(((String) value))));

//        mapper.createTypeMap(Product.class, ProductServiceModel.class)
//                .addMappings(m -> m.using(getCategoryName)
//                        .map(Product::getCategories,
//                                (destination, value) -> destination.setCategories(((Set<String>) value))));
//
//        mapper.createTypeMap(GroceryList.class, OrderServiceModel.class)
//                .addMappings(m -> {
//                    m.map(source -> source.getUser().getUsername(),
//                            (destination, value) -> destination.setCustomerName(((String) value)));
//                    m.map(source -> source.getProduct().getImageUrl(),
//                            (destination, value) -> destination.setImageUrl(((String) value)));
//                    m.using(dateFormatter).map(GroceryList::getOrderDate,
//                            (destination, value) -> destination.setOrderDate(((String) value)));
//                    m.map(source -> source.getProduct().getName(),
//                            (destination, value) -> destination.setProductName(((String) value)));
//                    m.map(source -> source.getProduct().getDescription(),
//                            (destination, value) -> destination.setProductDescription(((String) value)));
//                });
//
//        mapper.createTypeMap(GroceryList.class, OrderDetailsModel.class)
//                .addMappings(m -> {
//                    m.map(source -> source.getProduct().getImageUrl(),
//                            (destination, value) -> destination.setImageUrl(((String) value)));
//                    m.map(source -> source.getProduct().getName(),
//                            (destination, value) -> destination.setProductName(((String) value)));
//                    m.map(source -> source.getProduct().getDescription(),
//                            (destination, value) -> destination.setProductDescription(((String) value)));
//                    m.map(source -> source.getUser().getUsername(),
//                            (destination, value) -> destination.setCustomerName(((String) value)));
//                    m.using(dateFormatter).map(GroceryList::getOrderDate,
//                            (destination, value) -> destination.setOrderDate(((String) value)));
//                });

        return mapper;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
