package com.example.bigbisort_be.core.buyer.sign_up.service;

import com.example.bigbisort_be.common.MapBuilder.MapBuilder;
import com.example.bigbisort_be.common.constants.CommonConstants;
import com.example.bigbisort_be.common.enums.AuthenticationType;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerInfoBean;
import com.example.bigbisort_be.exception.*;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerRepository;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerRepositoryService;
import com.example.bigbisort_be.core.buyer.sign_up.assembler.BuyerSignupAssembler;
import com.example.bigbisort_be.core.buyer.sign_up.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.core.buyer.sign_up.response.BuyerSignupResponseBean;
import com.example.bigbisort_be.persistence.signup.user.entity.UsersEntity;
import com.example.bigbisort_be.persistence.signup.user.model.UserRepositoryServiceImpl;
import com.example.bigbisort_be.security.core.utils.EncryptionUtils;
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

    private static final String EMAIL_ALREADY_EXIST="exception.auth.email.already.exist";
    private final UserRepositoryServiceImpl userRepositoryServiceImpl;
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final BuyerRepositoryService buyerRepositoryService;
    private final BuyerRepository buyerRepository;
    private final BuyerSignupAssembler buyerSignupAssembler;
    private final EncryptionUtils encryptionUtils ;
//    @Autowired
//    private final AuthenticationManager authenticationManager;
    @Override
    public BuyerSignupResponseBean buyerSignUp(BuyerSignupRequestBean buyerSignupRequestBean) throws Exception {

        if(StringUtils.isNotEmpty(buyerSignupRequestBean.getEmail()) && StringUtils.isNotEmpty(buyerSignupRequestBean.getPhone())){
            if(buyerRepositoryService.existsByEmailIgnoreCaseOrPhone(buyerSignupRequestBean.getEmail(),buyerSignupRequestBean.getPhone())){
                throw new EmailorPhoneAlreadyExistException(EMAIL_ALREADY_EXIST);
            }
        }

        try{
            if(buyerSignupRequestBean.getUserName()!=null  && buyerSignupRequestBean.getPassword()!=null){
                if(buyerRepositoryService.existsByUsername(buyerSignupRequestBean.getUserName())){
                    throw new UserNameAlreadyExistException("UserName Already Exist");
                }
//                buyerEntity.setUserName(buyerSignupRequestBean.getUserName());
//
//                buyerEntity.setPassword(Base64.getEncoder()
//                        .encodeToString(objectWriter.writeValueAsBytes(buyerSignupRequestBean.getPassword())));
            }
        }catch (Exception e){
            throw new UserNameAlreadyExistException("UserName Already Exist");
        }
        UsersEntity usersEntity = UsersEntity.builder()
                .authenticationType(AuthenticationType.BUYER)
                .email(buyerSignupRequestBean.getEmail())
                .name(buyerSignupRequestBean.getName())
                .userName(buyerSignupRequestBean.getUserName())
                .sPhrase(encryptionUtils.encrypt(buyerSignupRequestBean.getPassword()))
                .build();

        UsersEntity usersEntitySaved = userRepositoryServiceImpl.save(usersEntity);

        BuyerEntity buyerEntity = BuyerEntity.builder()
                        .name(buyerSignupRequestBean.getName())
                                .email(buyerSignupRequestBean.getEmail())
                                                .address(buyerSignupRequestBean.getAddress())
                                                        .phone(buyerSignupRequestBean.getPhone())
                                                                .state(buyerSignupRequestBean.getState())
                                                                        .zip(buyerSignupRequestBean.getZip())
                                                                                .city(buyerSignupRequestBean.getCity())
                .usersEntity(usersEntitySaved)
                .country(buyerSignupRequestBean.getCountry())
                                                                                        .build();

        return buyerSignupAssembler.toModel(buyerRepository.save(buyerEntity));
    }

    @Override
    public BuyerInfoBean buyerLogin(BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException, ResourceNotAvailableException {

        Map<String,Object> finalResponse = new HashMap<>();
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(buyerSigninRequestBean.getUserName(),buyerSigninRequestBean.getPassword()));
////        if(authentication.isAuthenticated()){
////
////        }
        BuyerInfoBean buyerInfoBean = new BuyerInfoBean();
        if (StringUtils.isNotEmpty(buyerSigninRequestBean.getUserName() ) && StringUtils.isNotEmpty(buyerSigninRequestBean.getPassword() )) {
            String password = Base64.getEncoder()
                    .encodeToString(objectWriter.writeValueAsBytes(buyerSigninRequestBean.getPassword()));
            if (!buyerRepositoryService.existsByUserNameIgnoreCaseAndPassword(buyerSigninRequestBean.getUserName(), password)) {
                throw new InvalidCredentialsException("Invalid Credentials");
            }
            BuyerEntity buyerEntity = buyerRepositoryService.findByUsernameIgnoreCase(buyerSigninRequestBean.getUserName());
            buyerInfoBean = BuyerInfoBean.builder().buyerId(buyerEntity.getId()).name(buyerEntity.getName()).phone(buyerEntity.getPhone()).email(buyerEntity.getEmail()).message(CommonConstants.LOGIN_SUCCESSFULLY).userName(buyerEntity.getUserName()).build();
            //            if(authentication.isAuthenticated()){
//                return "Successfully logged in";
//            }else {
//                return "Login fails";
//            }
        }
//        return "Successfully logged in";


        return buyerInfoBean;
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
