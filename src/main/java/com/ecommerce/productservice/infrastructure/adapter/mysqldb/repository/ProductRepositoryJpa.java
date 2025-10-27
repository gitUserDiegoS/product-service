package com.ecommerce.productservice.infrastructure.adapter.mysqldb.repository;


import com.ecommerce.productservice.infrastructure.adapter.mysqldb.entity.ProductEntity;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * Repository interface for performing CRUD operations on {@link ProductEntity}
 */
public interface ProductRepositoryJpa extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p " +
            "WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:minPrice IS NULL OR p.unitPrice >= :minPrice) " +
            "AND (:maxPrice IS NULL OR p.unitPrice <= :maxPrice)")
    Page<ProductEntity> search(
            @Param("name") String name,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable);
}
