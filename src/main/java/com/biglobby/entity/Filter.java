package com.biglobby.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Filter {  
	private String key;
	private Double from;
	private Double to;
	private String[] category;
	private String[] location;
	private Long user;
}
