package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.entities.Category;
import com.mkolongo.price_comparison.domain.models.service.CategoryServiceModel;
import com.mkolongo.price_comparison.exception.CategoryNotFoundException;
import com.mkolongo.price_comparison.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository mockCategoryRepository;
    @Mock
    ModelMapper mockMapper;

    @InjectMocks
    CategoryServiceImpl mockCategoryService;

    Category mockCategory;
    CategoryServiceModel mockServiceModel;

    @BeforeEach
    void setUp() {
        mockCategory = new Category() {{
            setId("test id");
            setName("test name");
        }};

        mockServiceModel = new CategoryServiceModel() {{
            setId(mockCategory.getId());
            setName(mockCategory.getName());
        }};
    }

    @Test
    void add_withUniqueCategoryName_worksFine() {
//        when(mockCategoryRepository.saveAndFlush(any())).;

        try {
            mockCategoryService.add(anyString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void add_withExistingName_throwsException() {

    }

    @Test
    void getAll_ifPresent_returnsAllCategories() {
        List<Category> categories = new ArrayList<>() {{
            add(mockCategory);
        }};

        when(mockCategoryRepository.findAll()).thenReturn(categories);
        when(mockMapper.map(categories, new TypeToken<Set<CategoryServiceModel>>() {}.getType()))
                .thenReturn(new HashSet<>() {{add(mockServiceModel);}});

        final Set<CategoryServiceModel> serviceModels = mockCategoryService.getAll();

        assertEquals(1, serviceModels.size());
    }

    @Test
    void getAll_ifAbsent_returnsEmptyList() {
        List<Category> categories = new ArrayList<>();
        when(mockCategoryRepository.findAll()).thenReturn(categories);
        when(mockMapper.map(categories, new TypeToken<Set<CategoryServiceModel>>() {}.getType()))
                .thenReturn(new HashSet<>());

        final Set<CategoryServiceModel> serviceModels = mockCategoryService.getAll();

        assertEquals(0, serviceModels.size());
    }

    @Test
    void getById_withValidId_returnsCategory() {
        when(mockCategoryRepository.findById(mockCategory.getId())).thenReturn(Optional.ofNullable(mockCategory));
        when(mockMapper.map(mockCategory, CategoryServiceModel.class)).thenReturn(mockServiceModel);

        final CategoryServiceModel serviceModel = mockCategoryService.getById(mockCategory.getId());

        assertEquals(serviceModel.getId(), mockServiceModel.getId());
        assertEquals(serviceModel.getName(), mockServiceModel.getName());
    }

    @Test
    void getById_withInvalidId_throwsException() {
        when(mockCategoryRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> mockCategoryService.getById(anyString()));
    }

    @Test
    void editName_withValidName_worksFine() {
        when(mockCategoryRepository.findById(mockServiceModel.getId())).thenReturn(Optional.ofNullable(mockCategory));

        mockServiceModel.setName("new test name");
        mockCategoryService.editName(mockServiceModel);

        verify(mockCategoryRepository).saveAndFlush(mockCategory);
        assertEquals(mockServiceModel.getName(), mockCategory.getName());
    }

    @Test
    void editName_withInvalidId_throwsException() {
        when(mockCategoryRepository.findById(mockServiceModel.getId())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> mockCategoryService.editName(mockServiceModel));
    }

    @Test
    void delete_withValidId_worksFine() {
        mockCategoryService.delete(anyString());
        verify(mockCategoryRepository).deleteById(anyString());
    }
}