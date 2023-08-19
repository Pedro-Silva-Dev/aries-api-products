package br.com.pedro.dev.ariesapiproducts.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder(setterPrefix = "set")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "produtos")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "preco")
    private BigDecimal price;

    @Column(name = "estoque")
    private Integer stock;

    @Column(name = "ativo")
    private Boolean active;

    @CreatedDate 
    @Column(name = "dhc") 
    private LocalDateTime dhc;

    @LastModifiedDate 
    @Column(name = "dhu") 
    private LocalDateTime dhu;

    @Column(name = "categoria_id")
    private Integer categoryId;
    
}
