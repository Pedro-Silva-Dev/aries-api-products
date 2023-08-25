package br.com.pedro.dev.ariesapiproducts.models.mappers;

import br.com.pedro.dev.ariesapiproducts.models.Promotion;
import br.com.pedro.dev.ariesapiproducts.models.requests.PromotionRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.PromotionResponse;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-24T21:13:19-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.35.0.v20230721-1147, environment: Java 17.0.7 (Eclipse Adoptium)"
)
public class PromotionMapperImpl extends PromotionMapper {

    @Override
    public Promotion toPromotion(PromotionRequest PromotionCreateRequest) {
        if ( PromotionCreateRequest == null ) {
            return null;
        }

        Promotion.PromotionBuilder promotion = Promotion.builder();

        promotion.setActive( PromotionCreateRequest.active() );
        promotion.setDescription( PromotionCreateRequest.description() );
        promotion.setDhf( PromotionCreateRequest.dhf() );
        promotion.setDhi( PromotionCreateRequest.dhi() );
        promotion.setDiscount( PromotionCreateRequest.discount() );
        promotion.setDiscountPercentage( PromotionCreateRequest.discountPercentage() );
        promotion.setName( PromotionCreateRequest.name() );

        return promotion.build();
    }

    @Override
    public PromotionResponse toPromotionResponse(Promotion promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionResponse.PromotionResponseBuilder promotionResponse = PromotionResponse.builder();

        promotionResponse.setActive( promotion.getActive() );
        promotionResponse.setDescription( promotion.getDescription() );
        promotionResponse.setDhf( promotion.getDhf() );
        promotionResponse.setDhi( promotion.getDhi() );
        promotionResponse.setDiscount( promotion.getDiscount() );
        promotionResponse.setDiscountPercentage( promotion.getDiscountPercentage() );
        promotionResponse.setId( promotion.getId() );
        promotionResponse.setName( promotion.getName() );

        return promotionResponse.build();
    }
}
