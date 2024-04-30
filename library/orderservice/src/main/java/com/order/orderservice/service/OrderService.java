package com.order.orderservice.service;


import com.order.orderservice.dto.OrderLineItemsDto;
import com.order.orderservice.dto.OrderRequest;
import com.order.orderservice.model.Order;
import com.order.orderservice.model.OrderLineItems;

import java.util.List;

public interface OrderService {
    String placeOrder(OrderRequest orderRequest);

    List<OrderLineItemsDto> getAllOrders();
}
