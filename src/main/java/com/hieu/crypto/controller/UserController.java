package com.hieu.crypto.controller;

import com.hieu.crypto.entity.Balance;
import com.hieu.crypto.entity.User;
import com.hieu.crypto.model.WalletCurrencyModel;
import com.hieu.crypto.service.core.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/{username}/wallets")
    public ResponseEntity<List<WalletCurrencyModel>> getUser(@PathVariable String username) {
        return ResponseEntity.ok().body(userService.getWalletCurrencies(username));
    }

}
