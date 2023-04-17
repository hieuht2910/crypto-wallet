package com.hieu.crypto.service.core.impl;

import com.hieu.crypto.entity.Balance;
import com.hieu.crypto.entity.User;
import com.hieu.crypto.model.WalletCurrencyModel;
import com.hieu.crypto.repository.UserRepository;
import com.hieu.crypto.service.core.UserService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        if (StringUtils.isNotEmpty(username))
            return userRepository.findByUsername(username);
        return null;
    }

    @Override
    public List<WalletCurrencyModel> getWalletCurrencies(String username) {
        User user = findByUsername(username);
        List<WalletCurrencyModel> wallet = new ArrayList<>();
        if (Objects.nonNull(user)) {
            List<Balance> balances = user.getBalances();
            balances.forEach(balance -> {
                WalletCurrencyModel walletCurrencyModel = new WalletCurrencyModel();
                walletCurrencyModel.setSymbol(balance.getCurrency().getSymbol());
                walletCurrencyModel.setBalance(balance.getQuantity());
                wallet.add(walletCurrencyModel);
            });
        }
        return wallet;
    }

    @Override
    public User save(User user) {
        if (Objects.nonNull(user))
            return userRepository.save(user);
        return null;
    }
}
