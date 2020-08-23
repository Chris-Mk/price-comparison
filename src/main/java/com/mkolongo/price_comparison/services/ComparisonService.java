package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.models.view.ComparisonViewModel;

import java.util.Set;

public interface ComparisonService {
    
    void addProduct(String productId, String userEmail);

    void  removeProduct(String comparisonId, String productId);

    void deleteComparison(String comparisonId);

    ComparisonViewModel getComparisonById(String comparisonId);

    Set<ComparisonViewModel> getAllComparisons(String userEmail);
}
