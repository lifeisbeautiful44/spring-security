package com.lifeIsbeautiful.config;

import com.lifeIsbeautiful.controller.dto.request.CustomerRegistrationDTO;
import com.lifeIsbeautiful.model.Customer;
import com.lifeIsbeautiful.repository.CustomerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder)
    {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }
    /**
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer userDetail = this.customerRepository.findByEmail(username).
                orElseThrow(() -> new UsernameNotFoundException("User with username: "+ username + " not found"));

        List<GrantedAuthority> authorities = userDetail.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userDetail.getRole()));
        return new User(userDetail.getEmail(),userDetail.getPwd(),authorities);
    }

    public String registerUser(CustomerRegistrationDTO customerRegistrationDTO)
    {
        Customer customer = new Customer();
        String hashedPassword = passwordEncoder.encode(customerRegistrationDTO.pwd());
        customer.setEmail(customerRegistrationDTO.email());
        customer.setRole(customerRegistrationDTO.role());
        customer.setPwd(hashedPassword);
        customerRepository.save(customer);

        return "Customer with username: " + customer.getEmail() +" register successfully.";

    }
}
