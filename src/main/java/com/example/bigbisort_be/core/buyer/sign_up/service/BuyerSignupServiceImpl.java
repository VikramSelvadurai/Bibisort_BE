package com.example.bigbisort_be.core.buyer.sign_up.service;

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
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerSignupServiceImpl implements BuyerSignupService {
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private final BuyerRepositoryService buyerRepositoryService;
    private final BuyerRepository buyerRepository;
    private final BuyerSignupAssembler buyerSignupAssembler;
    @Override
    public BuyerSignupResponseBean addBuyer(BuyerSignupRequestBean buyerSignupRequestBean)  {

        if((buyerSignupRequestBean.getEmail()!=null && buyerSignupRequestBean.getPhone()!=null) && (!buyerSignupRequestBean.getEmail().isEmpty() && !buyerSignupRequestBean.getPhone().isEmpty())){
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
            log.error("Exception occured :{}",e);
        }
        return buyerSignupAssembler.toModel(buyerRepository.save(buyerEntity));
    }

    @Override
    public String buyerLogin(BuyerSigninRequestBean buyerSigninRequestBean) throws JsonProcessingException {
        if(buyerSigninRequestBean.getUserName()!=null && buyerSigninRequestBean.getPassword()!=null ){
            String password = Base64.getEncoder()
                    .encodeToString(objectWriter.writeValueAsBytes(buyerSigninRequestBean.getPassword()));
            if(!buyerRepositoryService.existsByUserNameIgnoreCaseAndPassword(buyerSigninRequestBean.getUserName(),password)){
                throw new InvalidCredentialsException("Invalid Credentials");
            }
        }
        return "Successfully logged in";
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
