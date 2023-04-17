package com.hieu.crypto;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestParse {

    @Test
    public void test() {
        String pair = "ETHUSDT";
        String[] cryptocurrencies = pair.split("(?=\\p{Upper})");
        for (String cryptocurrency : cryptocurrencies) {
            System.out.println(cryptocurrency);
        }


        Pattern pattern = Pattern.compile("([A-Z]+)([A-Z]+)");
        Matcher matcher = pattern.matcher(pair);
        if (matcher.matches()) {
            String baseCurrency = matcher.group(1); // ETH
            String quoteCurrency = matcher.group(2); // USDT

            System.out.println("base : " + baseCurrency);
            System.out.println("quote: " + quoteCurrency);
        }
    }
}
