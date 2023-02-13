package com.biglobby.entity;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Data;

@Service
@Data
public class PageTemp<T> {
	public PageTemp() {
		super();
	}

	List<T> content;
	Integer totalPages = 0;
	Integer number;

	public PageTemp(List<T> content, Integer page, Integer onepage) {
		super();
		int sizetemp = content.size();
		int fulltemp = page * onepage + onepage;
		int turn = page * onepage + onepage;
		if (sizetemp < fulltemp) {
			turn = sizetemp;
		}
		List<T> contentTemp = new ArrayList<>();
		for (int i = page * onepage; i < turn; i++) {
			contentTemp.add(content.get(i));
		}
		this.content = contentTemp;
		this.totalPages = (int) Math.ceil((double) content.size() / onepage);
		this.number = page;
	}

}