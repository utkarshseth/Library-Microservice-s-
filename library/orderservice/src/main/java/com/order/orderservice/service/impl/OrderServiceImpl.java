package com.order.orderservice.service.impl;

import com.order.orderservice.dto.InventoryResponse;
import com.order.orderservice.dto.OrderLineItemsDto;
import com.order.orderservice.dto.OrderRequest;
import com.order.orderservice.model.Order;
import com.order.orderservice.model.OrderLineItems;
import com.order.orderservice.repository.OrderLineItemsRepository;
import com.order.orderservice.repository.OrderRepository;
import com.order.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private OrderLineItemsRepository orderLineItemsRepository;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        List<String> skuCodes = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(OrderLineItemsDto::getSkuCode)
                .collect(Collectors.toList());
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build()
                .get()
                .uri("http://inventoryservice/api/inventory/",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(item -> item.isInStock());

        if (allProductsInStock == false) {
            throw new IllegalArgumentException("Books not in stock, try later !!");
        }
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID()
                .toString());

        // Save the order first to generate its ID
        Order savedOrder = repository.save(order);

        // Map OrderLineItems and set the order ID
        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(item -> {
                    OrderLineItems orderLineItems = modelMapper.map(item, OrderLineItems.class);
                    orderLineItems.setOrderId(savedOrder.getId()); // Set the order ID
                    return orderLineItems;
                })
                .collect(Collectors.toList());

        // Save the list of OrderLineItems
        orderLineItemsRepository.saveAll(orderLineItemsList);
        return "Order placed successfully";
    }

    @Override
    public List<OrderLineItemsDto> getAllOrders() {
        return orderLineItemsRepository.findAll()
                .stream()
                .map(orderLineItems -> modelMapper.map(orderLineItems, OrderLineItemsDto.class))
                .collect(Collectors.toList());
    }


}
