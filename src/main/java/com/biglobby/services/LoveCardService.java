package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.LoveCard; 

public interface LoveCardService {
	public ResponseEntity<LoveCard> get(Long id);

	public ResponseEntity<List<LoveCard>> get();

	public ResponseEntity<LoveCard> getByUserIdAndCardId(Long useId, Long cardId);

	public ResponseEntity<LoveCard> add(LoveCard entity);

	public ResponseEntity<LoveCard> update(Long id, LoveCard entity);

	public ResponseEntity<Integer> delete(Long id);
	
	// phần của quang dùng cho đối tác
	//ALL  
	public ResponseEntity<Page<LoveCard>> getListLoveCardAllByIdUser(Long id, Pageable pageable);
								
	//ALL SEARCH 
	public ResponseEntity<Page<LoveCard>> getListLoveCardAllByIdUserSearch(Long id, String textSearh, Pageable pageable);
	// end phần quang
}
