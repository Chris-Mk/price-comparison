package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.models.binding.SellerRegisterModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SellerService extends UserDetailsService {

    void register(SellerRegisterModel sellerRegisterModel);

}
