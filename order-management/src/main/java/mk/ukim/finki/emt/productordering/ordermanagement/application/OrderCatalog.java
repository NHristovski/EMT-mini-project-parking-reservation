package mk.ukim.finki.emt.productordering.ordermanagement.application;

import mk.ukim.finki.emt.productordering.ordermanagement.domain.event.OrderCreated;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.model.Order;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.model.OrderId;
import mk.ukim.finki.emt.productordering.ordermanagement.domain.repository.OrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class OrderCatalog {

    private final OrderRepository orderRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderCatalog(OrderRepository orderRepository,
                        ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public OrderId createOrder(@NonNull Order order) {
        Objects.requireNonNull(order,"order must not be null");
        var newOrder = orderRepository.saveAndFlush(order);
        applicationEventPublisher.publishEvent(new OrderCreated(newOrder.id(),newOrder.getOrderedOn()));
        return newOrder.id();
    }
}
