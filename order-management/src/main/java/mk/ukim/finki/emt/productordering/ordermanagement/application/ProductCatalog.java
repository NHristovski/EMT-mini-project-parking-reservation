package mk.ukim.finki.emt.productordering.ordermanagement.application;

import mk.ukim.finki.emt.productordering.ordermanagement.domain.model.Product;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.model.ProductId;

import java.util.List;

public interface ProductCatalog {

    List<Product> findAll();

    Product findById(ProductId id);


}
