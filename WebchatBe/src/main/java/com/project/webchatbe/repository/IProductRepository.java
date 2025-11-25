package com.project.webchatbe.repository;

import com.project.webchatbe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("""
        select prd
        from Product prd 
        where  prd.id <> 0 
        and exists (select ct from Category ct where ct.id =:idCate and ct.id = prd.idCategoty)
    """)
    List<Product> findAny(@Param("idCate")Long idCate);

}
