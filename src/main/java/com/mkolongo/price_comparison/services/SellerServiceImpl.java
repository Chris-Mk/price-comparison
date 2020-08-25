package com.mkolongo.price_comparison.services;

import com.mkolongo.price_comparison.domain.entities.Role;
import com.mkolongo.price_comparison.domain.entities.Seller;
import com.mkolongo.price_comparison.domain.models.SellerPrincipal;
import com.mkolongo.price_comparison.domain.models.service.SellerServiceModel;
import com.mkolongo.price_comparison.exception.SellerExistException;
import com.mkolongo.price_comparison.exception.SellerNotFoundException;
import com.mkolongo.price_comparison.repositories.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final SellerRepository sellerRepository;
    private final ModelMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String name) {
        return sellerRepository.findByName(name)
                .map(SellerPrincipal::new)
                .orElseThrow(() -> new SellerNotFoundException(
                        "Seller with name <u>" + name + "</u> does not exist!"
                ));
    }

    @Override
    public void register(SellerServiceModel sellerServiceModel) {
        if (isValid(sellerServiceModel)) {
            final Seller seller = mapper.map(sellerServiceModel, Seller.class);
            seller.setPassword(passwordEncoder.encode(seller.getPassword()));
            seller.setRoles(Set.of(Role.ROLE_SELLER));
            sellerRepository.save(seller);
        }
    }

    private boolean isValid(SellerServiceModel sellerServiceModel) {
        final String name = sellerServiceModel.getName();
        final String email = sellerServiceModel.getEmail();

        if (sellerRepository.existsByName(name)) {
            throw new SellerExistException("Seller with name <u>" + name + "</u> already exist!");
        } else if (sellerRepository.existsByEmail(email)) {
            throw new SellerExistException("Seller with email <u>" + email + "</u> already exist!");
        }
        return true;
    }

}
