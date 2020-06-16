package com.mkolongo.product_shop.services;

import com.mkolongo.product_shop.domain.entities.Seller;
import com.mkolongo.product_shop.domain.models.SellerPrincipal;
import com.mkolongo.product_shop.domain.models.binding.SellerRegisterModel;
import com.mkolongo.product_shop.exception.SellerExistException;
import com.mkolongo.product_shop.exception.SellerNotFoundException;
import com.mkolongo.product_shop.repositories.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return sellerRepository.findByName(name)
                .map(SellerPrincipal::new)
                .orElseThrow(() -> new SellerNotFoundException(
                        "Seller with name <u>" + name + "</u> does not exist!"
                ));
    }

    @Override
    public void register(SellerRegisterModel sellerRegisterModel) {
        if (isValid(sellerRegisterModel)) {
            final Seller seller = mapper.map(sellerRegisterModel, Seller.class);
            seller.setPassword(passwordEncoder.encode(seller.getPassword()));
            sellerRepository.save(seller);
        }
    }

    private boolean isValid(SellerRegisterModel sellerRegisterModel) {
        final String name = sellerRegisterModel.getName();
        final String email = sellerRegisterModel.getEmail();

        if (sellerRepository.existsByName(name)) {
            throw new SellerExistException("Seller with name <u>" + name + "</u> already exist!");
        } else if (sellerRepository.existsByEmail(email)) {
            throw new SellerExistException("Seller with email <u>" + email + "</u> already exist!");
        }
        return true;
    }

}
