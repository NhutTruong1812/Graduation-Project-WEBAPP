package com.biglobby.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BServiceAndprice {
	BService bservice;
	Double price;
	public BServiceAndprice(Object[] obs) {
		this.bservice = new BService(Long.valueOf(obs[0].toString()), obs[1].toString(), obs[2].toString(), obs[3].toString(), Boolean.valueOf(obs[4].toString()));
		this.price = Double.valueOf(obs[5].toString());
	}
	
	public static List<BServiceAndprice> becomeBServiceAndprice(List<Object[]> dsobs) {
		List<BServiceAndprice> ds0 = new ArrayList<>();
		for (Object[] obs : dsobs) {
			ds0.add(new BServiceAndprice(obs));
		}
		return ds0;
	}
	
}
