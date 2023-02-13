package com.biglobby.entity;

import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConstructorBinding
public class RecommendDataModel {

    private Long userId;
    private Long productId;
    private Double rate;
}
