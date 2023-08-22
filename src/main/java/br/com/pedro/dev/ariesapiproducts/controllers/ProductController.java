package br.com.pedro.dev.ariesapiproducts.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedro.dev.ariesapiproducts.config.requests.NotAcceptableRequestException;
import br.com.pedro.dev.ariesapiproducts.config.requests.NotFoundRequestException;
import br.com.pedro.dev.ariesapiproducts.models.Product;
import br.com.pedro.dev.ariesapiproducts.models.mappers.ProductMapper;
import br.com.pedro.dev.ariesapiproducts.models.params.ProductParams;
import br.com.pedro.dev.ariesapiproducts.models.requests.ProductRequest;
import br.com.pedro.dev.ariesapiproducts.models.responses.ProductResponse;
import br.com.pedro.dev.ariesapiproducts.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    
    private final ProductService productService;

    /**
     * 
     * @param productParams
     * @param pageable
     * @return
     */
    @GetMapping("/page.json")
    public ResponseEntity<Page<ProductResponse>> getProductPage(ProductParams productParams, Pageable pageable) {
        Page<Product> productPage = productService.getPageProduct(productParams, pageable);
        List<ProductResponse> productResponseList = productPage.getContent().stream().map(ProductMapper.INSTANCE::toProductResponse).toList();
        PageImpl<ProductResponse> page = new PageImpl<>(productResponseList, productPage.getPageable(), productPage.getTotalElements());
        return ResponseEntity.status(HttpStatus.OK.value()).body(page);
    }

    /**
     * 
     * @param productParams
     * @return
     */
    @GetMapping("/list.json")
    public ResponseEntity<List<ProductResponse>> getProductList(ProductParams productParams) {
        List<Product> productList = productService.getListProduct(productParams);
        List<ProductResponse> list = productList.stream().map(ProductMapper.INSTANCE::toProductResponse).toList();
        return ResponseEntity.status(HttpStatus.OK.value()).body(list);
    }

    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}.json")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if(product != null) {
            ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(product);
            return ResponseEntity.status(HttpStatus.OK.value()).body(productResponse);
        }
        throw new NotFoundRequestException("Product not found.");
    }

    /**
     * 
     * @param productRequest
     * @return
     */
    @PostMapping("/create.json")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        if(product != null) {
            ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(product);
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(productResponse);
        }
        throw new NotAcceptableRequestException("Product cannot have an id to register.");
    }

    /**
     * 
     * @param id
     * @param productRequest
     * @return
     */
    @PutMapping("/{id}.json")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        if(product != null) {
            ProductResponse productResponse = ProductMapper.INSTANCE.toProductResponse(product);
            return ResponseEntity.status(HttpStatus.CREATED.value()).body(productResponse);
        }
        throw new NotFoundRequestException("Product not found for update.");
    }

}
