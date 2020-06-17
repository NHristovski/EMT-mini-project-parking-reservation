package mk.ukim.finki.emt.productordering.productcatalog;

import mk.ukim.finki.emt.productordering.productcatalog.domain.model.Product;
import mk.ukim.finki.emt.productordering.productcatalog.domain.model.ProductId;
import mk.ukim.finki.emt.productordering.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.financial.Currency;
import mk.ukim.finki.emt.productordering.sharedkernel.domain.financial.Money;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Component
class DataGenerator {

    private final ProductRepository productRepository;

    DataGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostConstruct
    @Transactional
    public void generateData() {
        if (productRepository.findAll().size()==0) {
            var products = new ArrayList<Product>();
            products.add(createProduct(new ProductId("1"),"Flashlight L",  new Money(Currency.MKD, 5642),10));
            products.add(createProduct(new ProductId("2"), "Flashlight M",  new Money(Currency.MKD, 4029),10));
            products.add(createProduct(new ProductId("3"),"Flashlight S",  new Money(Currency.MKD, 2416),10));
            productRepository.saveAll(products);
        }

    }

    private Product createProduct(ProductId productId, String name, Money price, int qnt) {
        var product = new Product(productId,name, price, qnt );
        return product;
    }
}
