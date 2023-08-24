package br.com.pedro.dev.ariesapiproducts.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

import br.com.pedro.dev.ariesapiproducts.models.Promotion;
import br.com.pedro.dev.ariesapiproducts.models.QPromotion;
import br.com.pedro.dev.ariesapiproducts.models.mappers.PromotionMapper;
import br.com.pedro.dev.ariesapiproducts.models.params.PromotionParams;
import br.com.pedro.dev.ariesapiproducts.models.requests.PromotionRequest;
import br.com.pedro.dev.ariesapiproducts.repositories.PromotionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class PromotionService {
    
    private final PromotionRepository promotionRepository;

    /**
     * 
     * @param promotionParams
     * @param pageable
     * @return
     */
    public Page<Promotion> getPagePromotion(PromotionParams promotionParams, Pageable pageable) {
        QPromotion qPromotion = QPromotion.promotion;
        BooleanBuilder builder = new BooleanBuilder();

        if(promotionParams != null) {
            if(promotionParams.name() != null) {
                String paramName = ("%"+promotionParams.name()+"%");
                builder.and(qPromotion.name.likeIgnoreCase(paramName));
            }
            if(promotionParams.minDiscount() != null && promotionParams.maxDiscount() != null) {
                builder.and(qPromotion.discount.between(promotionParams.minDiscount(), promotionParams.maxDiscount()));
            }
            if(promotionParams.discountPercentage() != null) {
                builder.and(qPromotion.discountPercentage.eq(promotionParams.discountPercentage()));
            }
            if(promotionParams.startDate() != null && promotionParams.endDate() != null) {
                builder.and(qPromotion.dhi.between(promotionParams.startDate(), promotionParams.endDate()));
            }
            if(promotionParams.active() != null) {
                builder.and(qPromotion.active.eq(promotionParams.active()));
            }
        }
        return promotionRepository.findAll(builder, pageable);
    }

    /**
     * 
     * @param promotionParams
     * @return
     */
    public List<Promotion> getListPromotion(PromotionParams promotionParams) {
        QPromotion qPromotion = QPromotion.promotion;
        BooleanBuilder builder = new BooleanBuilder();

        if(promotionParams != null) {
            if(promotionParams.name() != null) {
                String paramName = ("%"+promotionParams.name()+"%");
                builder.and(qPromotion.name.likeIgnoreCase(paramName));
            }
            if(promotionParams.minDiscount() != null && promotionParams.maxDiscount() != null) {
                builder.and(qPromotion.discount.between(promotionParams.minDiscount(), promotionParams.maxDiscount()));
            }
            if(promotionParams.discountPercentage() != null) {
                builder.and(qPromotion.discountPercentage.eq(promotionParams.discountPercentage()));
            }
            if(promotionParams.startDate() != null && promotionParams.endDate() != null) {
                builder.and(qPromotion.dhi.between(promotionParams.startDate(), promotionParams.endDate()));
            }
            if(promotionParams.active() != null) {
                builder.and(qPromotion.active.eq(promotionParams.active()));
            }
        }
        return (List<Promotion>) promotionRepository.findAll(builder);
    }

    /**
     * 
     * @param id
     * @return
     */
    public Promotion getPromotionById(Long id) {
        return promotionRepository.findById(id).orElse(null);
    }

    /**
     * 
     * @param promotionRequest
     * @return
     */
    public Promotion createPromotion(PromotionRequest promotionRequest) {
        Promotion promotion = PromotionMapper.INSTANCE.toPromotion(promotionRequest);
        return promotionRepository.save(promotion);
    }

    /**
     * 
     * @param id
     * @param promotionRequest
     * @return
     */
    public Promotion updatePromotion(Long id, PromotionRequest promotionRequest) {
        Promotion promotion = getPromotionById(id);
        if(promotion != null) {
            promotion.setName(promotionRequest.name());
            promotion.setDescription(promotionRequest.description());
            promotion.setDiscount(promotionRequest.discount());
            promotion.setDiscountPercentage(promotionRequest.discountPercentage());
            promotion.setDhi(promotionRequest.dhi());
            promotion.setDhf(promotionRequest.dhf());
            promotion.setActive(promotionRequest.active());
            return promotionRepository.save(promotion);
        }
        return null;
    }

}
