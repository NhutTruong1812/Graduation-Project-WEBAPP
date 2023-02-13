package com.biglobby.entity;

import org.springframework.stereotype.Service;
 

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class OrderAndSumPriceOrder {
    Order order;
    Double sumPrice;
}