package mk.ukim.finki.emt.productordering.productcatalog.domain.repository;

import mk.ukim.finki.emt.productordering.productcatalog.domain.model.Product;
import mk.ukim.finki.emt.productordering.productcatalog.domain.model.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, ProductId> {
}
