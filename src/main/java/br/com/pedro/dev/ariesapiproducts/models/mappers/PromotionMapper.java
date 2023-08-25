package br.com.pedro.dev.ariesapiproducts.models.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.pedro.dev.ariesapiproducts.models.Promotion;
import br.com.pedro.dev.ariesapiproducts.models.requests.PromotionRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.PromotionResponse;

@Mapper(componentModel = "promotionMapper")
public abstract class PromotionMapper {

    public static final PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    public abstract Promotion toPromotion(PromotionRequest PromotionCreateRequest);
    public abstract PromotionResponse toPromotionResponse(Promotion promotion);
    
}
