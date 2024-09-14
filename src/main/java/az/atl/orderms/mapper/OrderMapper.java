package az.atl.orderms.mapper;

import az.atl.orderms.dao.entity.OrderEntity;
import az.atl.orderms.model.request.OrderProductDto;
import az.atl.orderms.model.response.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    OrderMapper ORDER_MAPPER = Mappers.getMapper(OrderMapper.class);

    OrderResponseDto buildOrderResponseDto(OrderEntity entity);

    @Mapping(target = "price", expression = "java(price)")
    OrderEntity buildOrderEntity(OrderProductDto dto, BigDecimal price);
}
