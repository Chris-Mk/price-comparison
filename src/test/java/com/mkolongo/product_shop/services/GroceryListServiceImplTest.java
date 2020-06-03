package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.GroceryList;
import com.mkolongo.product_shop.domain.entities.Product;
import com.mkolongo.product_shop.domain.entities.User;
import com.mkolongo.product_shop.exception.GroceryListExistException;
import com.mkolongo.product_shop.exception.GroceryListNotFoundException;
import com.mkolongo.product_shop.exception.ProductNotFoundException;
import com.mkolongo.product_shop.repositories.GroceryListRepository;
import com.mkolongo.product_shop.repositories.ProductRepository;
import com.mkolongo.product_shop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroceryListServiceImplTest {

    @Mock
    ProductRepository mockProductRepository;
    @Mock
    GroceryListRepository mockGroceryListRepository;
    @Mock
    UserRepository mockUserRepository;
    @Mock
    ModelMapper mockMapper;

    @InjectMocks
    GroceryListServiceImpl mockGroceryListService;

    GroceryList mockGroceryList;
    Product mockProduct;
    User mockUser;

    @BeforeEach
    void setUp() {
        mockProduct = new Product() {{
            setId("test id");
        }};

        mockUser = new User() {{
            setId("testId");
            setEmail("test@test.test");
            setGroceryLists(new HashSet<>());
        }};

        mockGroceryList = new GroceryList() {{
            setId("test id");
            setName("test name");
            setProducts(new HashSet<>());
        }};
    }

    @Test
    void addProduct_withValidInputData_workFine() {
        when(mockGroceryListRepository.findById(mockGroceryList.getId())).thenReturn(Optional.ofNullable(mockGroceryList));
        when(mockProductRepository.findById(mockProduct.getId())).thenReturn(Optional.ofNullable(mockProduct));

        mockGroceryListService.addProduct(mockGroceryList.getId(), mockProduct.getId());

        verify(mockGroceryListRepository).save(mockGroceryList);
        assertEquals(1, mockGroceryList.getProducts().size());
    }

    @Test
    void addProduct_withInvalidProductId_throwsException() {
        when(mockProductRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class,
                () -> mockGroceryListService.addProduct(mockProduct.getId(), anyString()));
    }

    @Test
    void addProduct_withInvalidListId_throwsException() {
        when(mockProductRepository.findById(mockProduct.getId())).thenReturn(Optional.ofNullable(mockProduct));
        when(mockGroceryListRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(GroceryListNotFoundException.class,
                () -> mockGroceryListService.addProduct(mockProduct.getId(), mockGroceryList.getId()));
    }

    @Test
    void createList_withValidInput_worksFine() {
        when(mockUserRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.ofNullable(mockUser));

        mockGroceryListService.createList(mockUser.getEmail(), "New List");

        verify(mockGroceryListRepository).save(any(GroceryList.class));
    }

    @Test
    void createList_withInvalidUserEmail_throwsException() {
        when(mockUserRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> mockGroceryListService.createList(anyString(), "New List"));
    }

    @Test
    void createList_withAnExistingListName_throwsException() {
        when(mockUserRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.ofNullable(mockUser));

        mockUser.getGroceryLists().add(mockGroceryList);

        assertThrows(GroceryListExistException.class,
                () -> mockGroceryListService.createList(mockUser.getEmail(), mockGroceryList.getName()));
    }

    //    @Test
//    void getById_withValidId_returnsOrder() {
//        when(mockGroceryListRepository.findById(mockGroceryList.getId())).thenReturn(Optional.ofNullable(mockGroceryList));
//        final OrderDetailsModel viewModel = new OrderDetailsModel() {{
//            setId("test id");
//            setQuantity(12);
//            setCustomerName("test customer name");
//            setProductDescription("test product description");
//            setImageUrl("test image url");
//            setTotalPrice(BigDecimal.TEN);
//        }};
//
//        when(mockMapper.map(mockGroceryList, OrderDetailsModel.class)).thenReturn(viewModel);
//
//        final OrderDetailsModel order = mockOrderService.getById(mockGroceryList.getId());
//
//        assertEquals(viewModel.getId(), order.getId());
//        assertEquals(viewModel.getCustomerName(), order.getCustomerName());
//        assertEquals(viewModel.getImageUrl(), order.getImageUrl());
//        assertEquals(viewModel.getProductDescription(), order.getProductDescription());
//        assertEquals(viewModel.getQuantity(), order.getQuantity());
//        assertEquals(viewModel.getTotalPrice(), order.getTotalPrice());
//    }

//    @Test
//    void getById_withInvalidOrderId_throwsException() {
//        when(mockGroceryListRepository.findById(anyString())).thenReturn(Optional.empty());
//        assertThrows(OrderNotFoundException.class, () -> mockOrderService.getById(anyString()));
//    }

//    @Test
//    void getOrderByCustomerName_withValidCustomerName_returnsOrders() {
//        final Set<GroceryList> groceryLists = Set.of(this.mockGroceryList);
//        when(mockGroceryListRepository.findByUser_EmailAndAndName()).thenReturn(groceryLists);
//        when(mockMapper.map(groceryLists, new TypeToken<Set<OrderServiceModel>>() {}.getType())).thenReturn(Set.of(mockServiceModel));
//
//        final Set<OrderServiceModel> serviceModels = mockOrderService.getOrdersByCustomerName(mockUser.getUsername());
//
//        assertEquals(1, serviceModels.size());
//    }

//    @Test
//    void getAllOrders_returnsOrdersList() {
//        final List<GroceryList> groceryLists = List.of(this.mockGroceryList);
//        when(mockGroceryListRepository.findAll()).thenReturn(groceryLists);
//        when(mockMapper.map(groceryLists, new TypeToken<Set<OrderServiceModel>>() {}.getType())).thenReturn(Set.of(mockServiceModel));
//
//        final Set<OrderServiceModel> serviceModels = mockOrderService.getAllOrders();
//
//        assertEquals(1, serviceModels.size());
//    }
}