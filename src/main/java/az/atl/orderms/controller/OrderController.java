package az.atl.orderms.controller;

import az.atl.orderms.model.request.OrderProductDto;
import az.atl.orderms.model.response.OrderResponseDto;
import az.atl.orderms.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.status(OK).body(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Void> oderProduct(@RequestBody OrderProductDto dto) {
        orderService.oderProduct(dto);
        return ResponseEntity.status(OK).build();
    }
}
