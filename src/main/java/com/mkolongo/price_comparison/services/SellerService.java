package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.models.service.SellerServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SellerService extends UserDetailsService {

    void register(SellerServiceModel sellerRegisterModel);

}
