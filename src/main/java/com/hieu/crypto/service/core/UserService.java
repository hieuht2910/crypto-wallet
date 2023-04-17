package com.hieu.crypto.service.core;

import com.hieu.crypto.entity.User;
import com.hieu.crypto.model.WalletCurrencyModel;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    List<WalletCurrencyModel> getWalletCurrencies(String username);

    User save(User user);
}
