package az.atl.orderms.service.serviceImpl;

import az.atl.orderms.client.CustomerClient;
import az.atl.orderms.client.ProductClient;
import az.atl.orderms.dao.entity.OrderEntity;
import az.atl.orderms.dao.repository.OrderRepository;
import az.atl.orderms.model.request.OrderProductDto;
import az.atl.orderms.model.response.OrderResponseDto;
import az.atl.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static az.atl.orderms.mapper.OrderMapper.ORDER_MAPPER;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;

    @Override
    public OrderResponseDto getOrderById(Long id) {
        var entity = fetchOrderIfExist(id);
        return ORDER_MAPPER.buildOrderResponseDto(entity);
    }

    @Override
    @Transactional
    public void oderProduct(OrderProductDto dto) {
        var product = productClient.getProductById(dto.getProductId());
        var totalAmount = product.getPrice().multiply(new BigDecimal(dto.getCount()));
        var customer = customerClient.getCustomerById(dto.getCustomerId());

        if (product.getCount() < dto.getCount()) throw new RuntimeException("INSUFFICIENT_QUANTITY");
        if (customer.getBalance().compareTo(totalAmount) < 0) throw new RuntimeException("INSUFFICIENT_BALANCE");

        productClient.reduceProductCount(dto.getProductId(), dto.getCount());
        customerClient.reduceBalance(dto.getCustomerId(), totalAmount);

        var orderEntity = ORDER_MAPPER.buildOrderEntity(dto, totalAmount);
        orderRepository.save(orderEntity);
    }


    private OrderEntity fetchOrderIfExist(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new RuntimeException("ORDER_NOT_FOUND")
        );
    }
}
