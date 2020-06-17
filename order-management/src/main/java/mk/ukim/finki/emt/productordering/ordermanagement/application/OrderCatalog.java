package mk.ukim.finki.emt.productordering.ordermanagement.application;

import mk.ukim.finki.emt.productordering.ordermanagement.application.form.OrderForm;
import mk.ukim.finki.emt.productordering.ordermanagement.application.form.RecipientAddressForm;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.event.OrderCreated;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.event.OrderItemAdded;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.model.*;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class OrderCatalog {

    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final Validator validator;

    private ProductCatalog productCatalog;

    public OrderCatalog(OrderRepository orderRepository,
                        ProductCatalog productCatalog,
                        Validator validator,
                        ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.validator = validator;
        this.productCatalog = productCatalog;
    }

    public OrderId createOrder(@NonNull OrderForm order) {
        Objects.requireNonNull(order,"order must not be null");
        var constraintViolations = validator.validate(order);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException("The OrderForm is not valid", constraintViolations);
        }

        var newOrder = orderRepository.saveAndFlush(toDomainModel(order));
        applicationEventPublisher.publishEvent(new OrderCreated(newOrder.id(),newOrder.getOrderedOn()));
        newOrder.getItems().forEach(orderItem -> applicationEventPublisher.publishEvent(new OrderItemAdded(newOrder.id(),orderItem.id(),orderItem.getProductId(),orderItem.getQuantity(), Instant.now())));
        return newOrder.id();
    }

    @NonNull
    public Optional<Order> findById(@NonNull OrderId orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");
        return orderRepository.findById(orderId);
    }

    @NonNull
    private Order toDomainModel(@NonNull OrderForm orderForm) {
        var order = new Order(Instant.now(), orderForm.getCurrency(),
                toDomainModel(orderForm.getBillingAddress()));
        orderForm.getItems().forEach(item -> order.addItem(item.getProduct(), item.getQuantity()));
        return order;
    }

    @NonNull
    private RecipientAddress toDomainModel(@NonNull RecipientAddressForm form) {
        return new RecipientAddress(form.getName(), form.getAddress(),form.getCity(), form.getCountry());
    }



}
