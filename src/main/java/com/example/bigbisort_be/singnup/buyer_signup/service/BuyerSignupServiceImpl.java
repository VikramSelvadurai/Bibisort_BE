package com.example.bigbisort_be.singnup.buyer_signup.service;

import com.example.bigbisort_be.exception.EmailorPhoneAlreadyExistException;
import com.example.bigbisort_be.exception.InvalidCredentialsException;
import com.example.bigbisort_be.exception.UserNameAlreadyExistException;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerSignupEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerSignupRepository;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerSignupRepositoryService;
import com.example.bigbisort_be.singnup.buyer_signup.assembler.BuyerSignupAssembler;
import com.example.bigbisort_be.singnup.buyer_signup.request.BuyerSigninRequestBean;
import com.example.bigbisort_be.singnup.buyer_signup.request.BuyerSignupRequestBean;
import com.example.bigbisort_be.singnup.buyer_signup.response.BuyerSignupResponseBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerSignupServiceImpl implements BuyerSignupService {
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final BuyerSignupRepositoryService buyerSignupRepositoryService;
    private final BuyerSignupRepository buyerSignupRepository;
    private final BuyerSignupAssembler buyerSignupAssembler;
    @Override
    public BuyerSignupResponseBean addBuyer(BuyerSignupRequestBean buyerSignupRequestBean)  {

        if((buyerSignupRequestBean.getEmail()!=null && buyerSignupRequestBean.getPhone()!=null) && (!buyerSignupRequestBean.getEmail().isEmpty() && !buyerSignupRequestBean.getPhone().isEmpty())){
            if(buyerSignupRepositoryService.existsByEmailIgnoreCaseOrPhone(buyerSignupRequestBean.getEmail(),buyerSignupRequestBean.getPhone())){
                throw new EmailorPhoneAlreadyExistException("Email or Phone number already exist, please choose another one");
            }
        }
        BuyerSignupEntity buyerSignupEntity = BuyerSignupEntity.builder()
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
                if(buyerSignupRepositoryService.existsByUsername(buyerSignupRequestBean.getUserName())){
                    throw new UserNameAlreadyExistException("UserName Already Exist");
                }
                buyerSignupEntity.setUserName(buyerSignupRequestBean.getUserName());

                buyerSignupEntity.setPassword(Base64.getEncoder()
                        .encodeToString(objectWriter.writeValueAsBytes(buyerSignupRequestBean.getPassword())));
            }
        }catch (Exception e){
            log.error("Exception occured :{}",e);
        }
        return buyerSignupAssembler.toModel(buyerSignupRepository.save(buyerSignupEntity));
    }

    @Override
    public String buyerLogin(BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException {
        if(buyerSigninRequestBean.getUserName()!=null && buyerSigninRequestBean.getPassword()!=null ){
            String password = Base64.getEncoder()
                    .encodeToString(objectWriter.writeValueAsBytes(buyerSigninRequestBean.getPassword()));
            if(!buyerSignupRepositoryService.existsByUserNameIgnoreCaseAndPassword(buyerSigninRequestBean.getUserName(),password)){
                throw new InvalidCredentialsException("Invalid Credentials");
            }
        }
        return "Successfully logged in";
    }

    @Override
    public boolean existUsername(String username) {
        if(username!=null && !username.isEmpty()){
            return buyerSignupRepository.existsByUserNameIgnoreCase(username);
        }
        return false;
    }

    @Override
    public boolean existEmailorPhone(String username, String phone) {
    if ((username != null && !username.isEmpty()) || (phone != null && !phone.isEmpty())) {
      return buyerSignupRepositoryService.existsByEmailIgnoreCaseOrPhone(username, phone);
        }
    return false;
    }


}
