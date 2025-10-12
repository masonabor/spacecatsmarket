package com.edu.web.spacecatsmarket.ordering.domain.order;

import com.edu.web.spacecatsmarket.ordering.domain.CustomerDetailsId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Order {

    OrderId id;
    TransactionId transactionId;
    CartId cartId;
    CustomerDetailsId customerId;
    TotalPrice price;
}
