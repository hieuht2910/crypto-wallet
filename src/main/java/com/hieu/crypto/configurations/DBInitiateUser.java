package com.hieu.crypto.configurations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieu.crypto.entity.Balance;
import com.hieu.crypto.entity.Currency;
import com.hieu.crypto.entity.User;
import com.hieu.crypto.model.PairDefinition;
import com.hieu.crypto.service.core.CurrencyService;
import com.hieu.crypto.service.core.UserService;
import com.hieu.crypto.service.price.PairDefinitionService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DBInitiateUser {
    private static final String DEFAULT_USER_FILE = "default-user.json";

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private UserService userService;

    public void initUser() throws IOException {
        DefaultUser defaultUser = readDefaultUser();
        if (Objects.isNull(defaultUser)) {
            throw new RuntimeException("Default user not found");
        }

        User user = new User();
        user.setUsername(defaultUser.getUsername());

        Currency currency = currencyService.findBySymbol(defaultUser.getCurrency());
        if (Objects.isNull(currency)) {
            throw new RuntimeException("Currency not found: " + defaultUser.getCurrency());
        }

        Balance balance = new Balance();
        balance.setUser(user);
        balance.setCurrency(currency);
        balance.setQuantity(BigDecimal.valueOf(Double.parseDouble(defaultUser.getAmount())));

        user.setBalances(List.of(balance));
        userService.save(user);
    }

    private DefaultUser readDefaultUser() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource(DEFAULT_USER_FILE);
        InputStream input = resource.getInputStream();

        return mapper.readValue(input, new TypeReference<>() {});
    }

    @Data @NoArgsConstructor
    static class DefaultUser {
        private String username;
        private String currency;
        private String amount;
    }
}
