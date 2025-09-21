package com.example.bigbisort_be.common.siginup.conroller;

import com.example.bigbisort_be.common.enums.AuthenticationType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/common")
public class CommonController {

    @GetMapping("/sign-sign-types")
    public Map<AuthenticationType, String> getSignTypeList(){
        return  Arrays.stream(AuthenticationType.values()).sorted(Comparator.reverseOrder())
                .collect(Collectors.toMap(
                        authenticationType -> authenticationType,
                        AuthenticationType::getAuthenticationTypeValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new));
    }
}
