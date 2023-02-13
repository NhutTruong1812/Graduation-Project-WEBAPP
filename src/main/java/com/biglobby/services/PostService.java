package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.biglobby.entity.Post;

public interface PostService {

	public ResponseEntity<Post> get(Long cardId);

	public ResponseEntity<Page<Post>> get(Integer page, Integer elnum);

	public ResponseEntity<Post> add(Post entity);

	public ResponseEntity<Post> update(Long cardId, Post entity);

	public ResponseEntity<Integer> delete(Long cardId);

	// phần của hồ - admin - bài viết
	public ResponseEntity<Page<Post>> getListPostAll(Pageable pageable);

	public ResponseEntity<Page<Post>> getListPostAllSearch(String textSearch, Pageable pageable);

	public ResponseEntity<Page<Post>> getListPostAllFilterByStatusCard(Long idStatus, Pageable pageable);

	public ResponseEntity<Page<Post>> getListPostAllFilterAndSearch(Long idStatus, String textSearch,
			Pageable pageable);
	// kết thúc phần của hồ - admin - bài viết

	// phần của quang dùng cho đối tác
	// ALL
	public ResponseEntity<Page<Post>> getListPostAllByIdUser(Long id, Pageable pageable);

	// ALL SEARCH
	public ResponseEntity<Page<Post>> getListPostAllByIdUserSearch(Long id, String textSearh, Pageable pageable);
	// end phần quang

	public ResponseEntity<Post> findByCardId(Long cardId);
	
	public ResponseEntity<Page<Post>> getListPostFromHideCardWithIdCard(Long idCard, Pageable pageable);
	
	public ResponseEntity<List<Post>> loadAllPost();
	
	public ResponseEntity<List<Post>> loadAllPostOfUser(Long idUser);
}
