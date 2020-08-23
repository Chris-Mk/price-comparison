package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.models.service.SellerServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SellerService extends UserDetailsService {

    void register(SellerServiceModel sellerRegisterModel);

}
