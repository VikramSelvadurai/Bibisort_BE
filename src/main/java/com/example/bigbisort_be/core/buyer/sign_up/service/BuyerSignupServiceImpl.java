package com.example.bigbisort_be.core.buyer.sign_up.service;

import com.example.bigbisort_be.common.MapBuilder.MapBuilder;
import com.example.bigbisort_be.common.constants.CommonConstants;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.exception.EmailorPhoneAlreadyExistException;
import com.example.bigbisort_be.exception.InvalidCredentialsException;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerRepository;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerRepositoryService;
import com.example.bigbisort_be.core.buyer.sign_up.assembler.BuyerSignupAssembler;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerSignupResponseBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static com.example.bigbisort_be.core.contact.service.ContactServiceImpl.MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerSignupServiceImpl implements BuyerSignupService {
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final BuyerRepositoryService buyerRepositoryService;
    private final BuyerRepository buyerRepository;
    private final BuyerSignupAssembler buyerSignupAssembler;
//    @Autowired
//    private final AuthenticationManager authenticationManager;
    @Override
    public BuyerSignupResponseBean buyerSignUp(BuyerSignupRequestBean buyerSignupRequestBean) throws UserNameAlreadyExistException {

        if(StringUtils.isNotEmpty(buyerSignupRequestBean.getEmail()) && StringUtils.isNotEmpty(buyerSignupRequestBean.getPhone())){
            if(buyerRepositoryService.existsByEmailIgnoreCaseOrPhone(buyerSignupRequestBean.getEmail(),buyerSignupRequestBean.getPhone())){
                throw new EmailorPhoneAlreadyExistException("Email or Phone number already exist, please choose another one");
            }
        }
        BuyerEntity buyerEntity = BuyerEntity.builder()
                        .name(buyerSignupRequestBean.getName())
                                .email(buyerSignupRequestBean.getEmail())
                                                .address(buyerSignupRequestBean.getAddress())
                                                        .phone(buyerSignupRequestBean.getPhone())
                                                                .state(buyerSignupRequestBean.getState())
                                                                        .zip(buyerSignupRequestBean.getZip())
                                                                                .city(buyerSignupRequestBean.getCity())
                .country(buyerSignupRequestBean.getCountry())
                                                                                        .build();
        try{
            if(buyerSignupRequestBean.getUserName()!=null  && buyerSignupRequestBean.getPassword()!=null){
                if(buyerRepositoryService.existsByUsername(buyerSignupRequestBean.getUserName())){
                    throw new UserNameAlreadyExistException("UserName Already Exist");
                }
                buyerEntity.setUserName(buyerSignupRequestBean.getUserName());

                buyerEntity.setPassword(Base64.getEncoder()
                        .encodeToString(objectWriter.writeValueAsBytes(buyerSignupRequestBean.getPassword())));
            }
        }catch (Exception e){
            throw new UserNameAlreadyExistException("UserName Already Exist");
        }
        return buyerSignupAssembler.toModel(buyerRepository.save(buyerEntity));
    }

    @Override
    public Map<String, String> buyerLogin(BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException {

        Map<String,Object> finalResponse = new HashMap<>();
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(buyerSigninRequestBean.getUserName(),buyerSigninRequestBean.getPassword()));
////        if(authentication.isAuthenticated()){
////
////        }
        if (StringUtils.isNotEmpty(buyerSigninRequestBean.getUserName() ) && StringUtils.isNotEmpty(buyerSigninRequestBean.getPassword() )) {
            String password = Base64.getEncoder()
                    .encodeToString(objectWriter.writeValueAsBytes(buyerSigninRequestBean.getPassword()));
            if (!buyerRepositoryService.existsByUserNameIgnoreCaseAndPassword(buyerSigninRequestBean.getUserName(), password)) {
                throw new InvalidCredentialsException("Invalid Credentials");
            }
//            if(authentication.isAuthenticated()){
//                return "Successfully logged in";
//            }else {
//                return "Login fails";
//            }
        }
//        return "Successfully logged in";

        return MapBuilder.of(MESSAGE, CommonConstants.LOGIN_SUCCESSFULLY);
    }

    @Override
    public boolean existUsername(String username) {
        if(username!=null && !username.isEmpty()){
            return buyerRepository.existsByUserNameIgnoreCase(username);
        }
        return false;
    }

    @Override
    public boolean existEmailorPhone(String username, String phone) {
    if ((username != null && !username.isEmpty()) || (phone != null && !phone.isEmpty())) {
      return buyerRepositoryService.existsByEmailIgnoreCaseOrPhone(username, phone);
        }
    return false;
    }


}
