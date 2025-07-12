package com.example.bigbisort_be.core.order.buyer.service;

import com.example.bigbisort_be.common.MapBuilder.MapBuilder;
import com.example.bigbisort_be.common.constants.CommonConstants;
import com.example.bigbisort_be.core.order.buyer.assembler.BuyerOrderAssembler;
import com.example.bigbisort_be.core.order.buyer.bean.BuyerOrderStatusCount;
import com.example.bigbisort_be.core.order.buyer.bean.BuyerOrdersCount;
import com.example.bigbisort_be.core.order.buyer.bean.request.BuyerOrderFilterRequestBean;
import com.example.bigbisort_be.core.order.buyer.bean.request.BuyerOrderRequestBean;
import com.example.bigbisort_be.core.order.buyer.bean.response.BuyerOrderResponseBean;
import com.example.bigbisort_be.core.order.buyer.enums.BuyerOrderStatusEnum;
import com.example.bigbisort_be.core.product.utils.CriteriaUtils;
import com.example.bigbisort_be.exception.IdNotFoundException;
import com.example.bigbisort_be.persistence.order.buyer.entity.BuyerOrderEntity;
import com.example.bigbisort_be.persistence.order.buyer.model.BuyerOrderRepositoryService;
import com.example.bigbisort_be.persistence.product.entity.ProductEntity;
import com.example.bigbisort_be.persistence.product.model.ProductsRepositoryService;
import com.example.bigbisort_be.persistence.signup.buyer_signup.entity.BuyerEntity;
import com.example.bigbisort_be.persistence.signup.buyer_signup.model.BuyerRepositoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.example.bigbisort_be.core.contact.service.ContactServiceImpl.MESSAGE;

@Service
@RequiredArgsConstructor
public class BuyerOrderServiceImpl implements BuyerOrderService{

    private final BuyerRepositoryService buyerRepositoryService;
    private final ProductsRepositoryService productsRepositoryService;
    private final BuyerOrderRepositoryService buyerOrderRepositoryService;
    private final BuyerOrderAssembler buyerOrderAssembler;
    private final PagedResourcesAssembler<BuyerOrderEntity> buyerOrderEntityPagedResourcesAssembler;

    @Override
    public BuyerOrderResponseBean saveOrder(BuyerOrderRequestBean buyerOrderRequestBean) throws IdNotFoundException {

        BuyerEntity buyerEntity = buyerRepositoryService.findById(UUID.fromString(buyerOrderRequestBean.getBuyerId()));
        Set<ProductEntity> productEntitySet = productsRepositoryService.findAllByIdIsIn(buyerOrderRequestBean.getProductIds());
        BuyerOrderEntity buyerOrderEntity = BuyerOrderEntity.builder()
                .orderDate(buyerOrderRequestBean.getOrderDate())
                .billingCompanyName(buyerOrderRequestBean.getBillingCompanyName())
                .quantity(buyerOrderRequestBean.getQuantity())
                .shippingName(buyerOrderRequestBean.getShippingName())
                .paymentMethod(buyerOrderRequestBean.getPaymentMethod())
                .estimationDateOfArrival(buyerOrderRequestBean.getEstimationDateOfArrival())
                .buyerEntity(buyerEntity)
                .build();
        buyerOrderEntity.setProductEntitySet(productEntitySet);

        buyerOrderEntity = buyerOrderRepositoryService.save(buyerOrderEntity);

    return buyerOrderAssembler.toModel(buyerOrderEntity);
    }

    @Override
    public BuyerOrderResponseBean getBuyer(UUID orderId) throws IdNotFoundException {
       return buyerOrderAssembler.toModel(buyerOrderRepositoryService.findById(orderId));
    }

    @Override
    public PagedModel<BuyerOrderResponseBean> getOrderBasedOnBuyer(BuyerOrderFilterRequestBean buyerOrderFilterRequestBean, Pageable pageable) throws IdNotFoundException {

        PageRequest pageRequest =
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize());
        Page<BuyerOrderEntity> buyerOrderEntityPage =
                buyerOrderRepositoryService.findAll(
                        (root, query, criteriaBuilder) -> {
                            List<Predicate> predicates = new ArrayList<>();
                            buyerNameCriteria(buyerOrderFilterRequestBean.getBuyerName(), root, criteriaBuilder, predicates);
                            buyerIdCategoryCriteria(buyerOrderFilterRequestBean.getBuyerId(), root, criteriaBuilder, predicates);
                            orderIdCategoryCriteria(buyerOrderFilterRequestBean.getOrderId(), root, criteriaBuilder, predicates);
                            shippingNameCriteria(buyerOrderFilterRequestBean.getShippingName(), root, criteriaBuilder, predicates);
                            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                        },
                        pageRequest);

        return buyerOrderEntityPagedResourcesAssembler.toModel(buyerOrderEntityPage,buyerOrderAssembler);

    }

    @Override
    public Map<String, String> updateOrderStatus(UUID orderId, BuyerOrderStatusEnum buyerOrderStatusEnum) throws IdNotFoundException {

        BuyerOrderEntity buyerOrderEntity = buyerOrderRepositoryService.findById(orderId);
        buyerOrderEntity.setStatus(buyerOrderStatusEnum);
        buyerOrderRepositoryService.save(buyerOrderEntity);

        return MapBuilder.of(MESSAGE, CommonConstants.STATUS_UPDATED);
    }

    @Override
    public BuyerOrdersCount getOrderStatusCount(UUID buyerId) throws IdNotFoundException {
        List<BuyerOrderStatusCount>  buyerOrderStatusCounts = new ArrayList<>();
        if(Objects.isNull(buyerId)){
            buyerOrderStatusCounts = buyerOrderRepositoryService.getOrderStatusCounts();
        }else {
            buyerOrderStatusCounts = buyerOrderRepositoryService.getBuyerOrderStatusCounts(buyerId);
        }

        AtomicReference<Long> totalOrders = new AtomicReference<>(0L);
        Map<BuyerOrderStatusEnum,Long> buyerOrderStatusCountsMap = new HashMap<>();
        buyerOrderStatusCounts.forEach(buyerOrderStatusCount -> {
            buyerOrderStatusCountsMap.put(buyerOrderStatusCount.getStatus(),buyerOrderStatusCount.getCount());
            totalOrders.set(totalOrders.get() + buyerOrderStatusCount.getCount());
        });

        return BuyerOrdersCount.builder().buyerOrderStatusCounts(buyerOrderStatusCountsMap).TotalOrders(totalOrders.get()).build();
    }

    private void shippingNameCriteria(String shippingName, Root<BuyerOrderEntity> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if(StringUtils.isNotEmpty(shippingName)) {
            predicates.add(criteriaBuilder.and(criteriaBuilder.like(root.get(BuyerOrderEntity.Fields.shippingName), " % " + CriteriaUtils.escapeForLike(shippingName).toLowerCase(Locale.ROOT) + "%")));
        }
    }

    private void orderIdCategoryCriteria(String orderId, Root<BuyerOrderEntity> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if(StringUtils.isNotEmpty(orderId)) {
            UUID orderIdUUID = UUID.fromString(orderId);
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(BuyerOrderEntity.Fields.id), orderIdUUID)));
        }
    }

    private void buyerIdCategoryCriteria(String buyerId, Root<BuyerOrderEntity> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if(StringUtils.isNotEmpty(buyerId)){
            UUID uuid = UUID.fromString(buyerId);
            predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get(BuyerOrderEntity.Fields.buyerEntity).get(BuyerEntity.Fields.id), uuid)));
        }
    }

    private void buyerNameCriteria(String buyerName, Root<BuyerOrderEntity> root, CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
        if (!Objects.toString(buyerName, "").equals("")) {
            predicates.add(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(
                                    criteriaBuilder.lower(root.get(BuyerOrderEntity.Fields.buyerEntity).get(BuyerEntity.Fields.name)),
                                     CriteriaUtils.escapeForLike(buyerName).toLowerCase(Locale.ROOT))));
        }
    }
}
