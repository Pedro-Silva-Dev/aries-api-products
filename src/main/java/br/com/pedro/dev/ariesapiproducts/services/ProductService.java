package br.com.pedro.dev.ariesapiproducts.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.BooleanBuilder;

import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.QProduct;
import br.com.pedro.dev.ariesapiproducts.models.mappers.ProductMapper;
import br.com.pedro.dev.ariesapiproducts.models.params.ProductParams;
import br.com.pedro.dev.ariesapiproducts.models.requests.ProductRequest;
import br.com.pedro.dev.ariesapiproducts.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class ProductService {
    
    private final ProductRepository productRepository;

    /**
     * 
     * @param productParams
     * @param pageable
     * @return
     */
    public Page<Product> getPageProduct(ProductParams productParams, Pageable pageable) {
        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

         if(productParams != null && productParams.name() != null) {
            String paramName = ("%"+productParams.name()+"%").toLowerCase();
            builder.and(qProduct.name.toLowerCase().like(paramName));
        }
        if(productParams != null && productParams.minPrice() != null && productParams.maxPrice() != null) {
            builder.and(qProduct.price.between(productParams.minPrice(), productParams.maxPrice()));
        }
        if(productParams != null && productParams.stock() != null) {
            builder.and(qProduct.stock.loe(productParams.stock()));
        }
        if(productParams != null && productParams.active() != null) {
            builder.and(qProduct.active.eq(productParams.active()));
        }
        if(productParams != null && productParams.categoryId() != null) {
            builder.and(qProduct.categoryId.eq(productParams.categoryId()));
        }
        return productRepository.findAll(builder, pageable);
    }

    /**
     * 
     * @param productParams
     * @return
     */
    public List<Product> getListProduct(ProductParams productParams) {
        QProduct qProduct = QProduct.product;
        BooleanBuilder builder = new BooleanBuilder();

        if(productParams != null && productParams.name() != null) {
            String paramName = ("%"+productParams.name()+"%").toLowerCase();
            builder.and(qProduct.name.toLowerCase().like(paramName));
        }
        if(productParams != null && productParams.minPrice() != null && productParams.maxPrice() != null) {
            builder.and(qProduct.price.between(productParams.minPrice(), productParams.maxPrice()));
        }
        if(productParams != null && productParams.stock() != null) {
            builder.and(qProduct.stock.loe(productParams.stock()));
        }
        if(productParams != null && productParams.active() != null) {
            builder.and(qProduct.active.eq(productParams.active()));
        }
        if(productParams != null && productParams.categoryId() != null) {
            builder.and(qProduct.categoryId.eq(productParams.categoryId()));
        }
        return (List<Product>) productRepository.findAll(builder);
    }

    /**
     * 
     * @param id
     * @return
     */
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * 
     * @param productRequest
     * @return
     */
    public Product createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.INSTANCE.toProduct(productRequest);
        return productRepository.save(product);
    }

    /**
     * 
     * @param id
     * @param productRequest
     * @return
     */
    public Product updateProduct(Long id, ProductRequest productRequest) {
        Product product = getProductById(id);
        if(product != null) {
            product.setName(productRequest.name());
            product.setDescription(productRequest.description());
            product.setPrice(productRequest.price());
            product.setStock(productRequest.stock());
            product.setActive(productRequest.active());
            product.setCategoryId(productRequest.categoryId());
            
            return productRepository.save(product);
        }
        return null;
    }

}
