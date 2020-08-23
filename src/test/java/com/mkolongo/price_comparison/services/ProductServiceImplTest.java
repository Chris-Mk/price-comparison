package com.mkolongo.price_comparison.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    
//    @Mock
//    ProductRepository mockProductRepository;
//    @Mock
//    ModelMapper mockMapper;
//
//    @InjectMocks
//    ProductServiceImpl mockProductService;
//
//    ProductServiceModel mockServiceModel;
//    Product mockProduct;
//
//    @BeforeEach
//    void setUp() {
//        mockServiceModel = new ProductServiceModel() {{
//            setName("test product");
//            setPrice(new BigDecimal("234.99"));
//            setImageUrl("http://www.image-url.com");
//            setDescription("test description");
//            setCategories(new HashSet<>());
//        }};
//
//        mockProduct = new Product() {{
//            setId("test id");
//            setName("test product");
//            setPrice(new BigDecimal("234.99"));
//            setImageUrl("http://www.image-url.com");
//            setDescription("test description");
//            setCategories(new HashSet<>());
//        }};
//    }
//
//    @Test
//    void addProduct_withValidInputData_worksFine() {
//        when(mockMapper.map(mockServiceModel, Product.class)).thenReturn(mockProduct);
//        when(mockMapper.map(mockProduct, ProductServiceModel.class)).thenReturn(mockServiceModel);
//        when(mockProductRepository.saveAndFlush(mockProduct)).thenReturn(mockProduct);
//
//        final ProductServiceModel serviceModel = mockProductService.addProduct(mockServiceModel);
//
//        verify(mockProductRepository).saveAndFlush(mockProduct);
//
//        assertEquals(serviceModel.getName(), mockProduct.getName());
//        assertEquals(serviceModel.getDescription(), mockProduct.getDescription());
//        assertEquals(serviceModel.getImageUrl(), mockProduct.getImageUrl());
//        assertEquals(serviceModel.getPrice(), mockProduct.getPrice());
//        assertEquals(serviceModel.getCategories().size(), mockProduct.getCategories().size());
//    }
//
//    @Test
//    void getAll_ifPresent_returnsAllProducts() {
//        when(mockProductRepository.findAll()).thenReturn(List.of(mockProduct));
//        when(mockMapper.map(mockProduct, ProductServiceModel.class)).thenReturn(mockServiceModel);
//
//        final Set<ProductServiceModel> products = mockProductService.getAll();
//        final ProductServiceModel next = products.iterator().next();
//
//        assertEquals(1, products.size());
//        assertEquals(mockServiceModel.getName(), next.getName());
//        assertEquals(mockServiceModel.getPrice(), next.getPrice());
//        assertEquals(mockServiceModel.getImageUrl(), next.getImageUrl());
//        assertEquals(mockServiceModel.getDescription(), next.getDescription());
//    }
//
//    @Test
//    void getById_withValidId_returnsProduct() {
//        when(mockProductRepository.findById(mockProduct.getId())).thenReturn(Optional.ofNullable(mockProduct));
//        when(mockMapper.map(mockProduct, ProductServiceModel.class)).thenReturn(mockServiceModel);
//
//        final ProductServiceModel serviceModel = mockProductService.getById(mockProduct.getId());
//
//        assertEquals(mockServiceModel.getName(), serviceModel.getName());
//        assertEquals(mockServiceModel.getDescription(), serviceModel.getDescription());
//        assertEquals(mockServiceModel.getImageUrl(), serviceModel.getImageUrl());
//        assertEquals(mockServiceModel.getPrice(), serviceModel.getPrice());
//        assertEquals(mockServiceModel.getCategories().size(), serviceModel.getCategories().size());
//    }
//
//    @Test
//    void getById_withInvalidId_throwsException() {
//        when(mockProductRepository.findById(anyString())).thenReturn(Optional.empty());
//        assertThrows(ProductNotFoundException.class, () -> mockProductService.getById(anyString()));
//    }
//
//    @Test
//    void delete_withCorrectId_worksFine() {
//        mockProductService.delete(mockProduct.getId());
//        verify(mockProductRepository).deleteById(mockProduct.getId());
//    }
//
//    @Test
//    void edit_withInvalidId_throwsException() {
//        when(mockProductRepository.findById(anyString())).thenReturn(Optional.empty());
//        final ProductEditModel editModel = new ProductEditModel() {{
//            setId("");
//        }};
//
//        assertThrows(ProductNotFoundException.class, () -> mockProductService.edit(editModel));
//    }
//
//    @Test
//    void edit_withValidInputData_worksFine() {
//        when(mockProductRepository.findById(anyString())).thenReturn(Optional.ofNullable(mockProduct));
//        final ProductEditModel editModel = new ProductEditModel() {{
//            setId(mockProduct.getId());
//            setName("new test name");
//            setDescription("new test description");
//            setPrice(BigDecimal.TEN);
//            setCategories(new HashSet<>());
//        }};
//
//        mockProductService.edit(editModel);
//
//        verify(mockProductRepository).saveAndFlush(mockProduct);
//
//        assertEquals(editModel.getName(), mockProduct.getName());
//        assertEquals(editModel.getPrice(), mockProduct.getPrice());
//        assertEquals(editModel.getDescription(), mockProduct.getDescription());
//        assertEquals(editModel.getCategories().size(), mockProduct.getCategories().size());
//    }
//
//    @Test
//    void getByCategory_withValidCategoryName_returnProducts() {
//        final Set<Product> mockProduct = Set.of(this.mockProduct);
//
//        when(mockProductRepository.findByCategory(anyString())).thenReturn(mockProduct);
//        when(mockMapper.map(mockProduct, new TypeToken<Set<ProductServiceModel>> () {}.getType()))
//                .thenReturn(Set.of(mockServiceModel));
//
//        final Set<ProductServiceModel> serviceModels = mockProductService.getByCategory(anyString());
//
//        assertEquals(1, serviceModels.size());
//        assertTrue(serviceModels.contains(mockServiceModel));
//    }
}