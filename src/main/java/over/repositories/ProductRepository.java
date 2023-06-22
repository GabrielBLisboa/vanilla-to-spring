package over.repositories;

import over.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT obj FROM Product obj WHERE LOWER(obj.name) LIKE LOWER(CONCAT('%',:name,'%'))")
    List<Product> productByName(String name);

    @Query("SELECT obj FROM Product obj WHERE obj.price <= :valorMaximo")
    Page<Product> priceLimit(Double valorMaximo, Pageable page);



}
