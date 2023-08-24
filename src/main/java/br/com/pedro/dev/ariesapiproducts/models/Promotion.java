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
@Table(name = "promocoes")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "desconto")
    private BigDecimal discount;

    @Column(name = "deconto_percentual")
    private Boolean discountPercentage;

    @Column(name = "ativo")
    private Boolean active;

    @Column(name = "dhi") 
    private LocalDateTime dhi;

    @Column(name = "dhf") 
    private LocalDateTime dhf;

    @CreatedDate 
    @Column(name = "dhc") 
    private LocalDateTime dhc;

    @LastModifiedDate 
    @Column(name = "dhu") 
    private LocalDateTime dhu;
    
}
