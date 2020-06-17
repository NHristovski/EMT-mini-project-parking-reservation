package mk.ukim.finki.emt.productordering.productcatalog.application;

import mk.ukim.finki.emt.productordering.productcatalog.domain.model.Product;
import mk.ukim.finki.emt.productordering.productcatalog.domain.model.ProductId;
import mk.ukim.finki.emt.productordering.productcatalog.domain.repository.ProductRepository;
import mk.ukim.finki.emt.productordering.productcatalog.integration.OrderItemAddedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProductCatalog {

    private final ProductRepository productRepository;

    public ProductCatalog(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @NonNull
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @NonNull
    public Optional<Product> findById(@NonNull ProductId productId) {
        Objects.requireNonNull(productId, "productId must not be null");
        return productRepository.findById(productId);
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void onOrderCreatedEvent(OrderItemAddedEvent event) {
        Product p = productRepository.findById(event.getProductId()).orElseThrow(RuntimeException::new);
        p.subtractQuantity(event.getQuantity());
        productRepository.save(p);
    }
}
