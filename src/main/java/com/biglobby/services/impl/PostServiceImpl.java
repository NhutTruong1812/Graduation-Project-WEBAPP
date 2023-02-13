package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.Post;
import com.biglobby.repository.PostRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<Post> get(Long id) {
		Optional<Post> post = postRepo.findById(id);
		if (post.isPresent()) {
			return ResponseEntity.ok(post.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Page<Post>> get(Integer page, Integer elnum) {
		Pageable pageable = PageRequest.of(page, elnum, Sort.by("card.postAt").descending());
		Page<Post> posts = postRepo.findAll(pageable);
		return ResponseEntity.ok(posts);
	}

	@Override
	public ResponseEntity<Post> add(Post entity) {
		Post added = postRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.POST + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Post> update(Long id, Post entity) {
		Optional<Post> exist = postRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		Post updated = postRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.POST + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Post> exist = postRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = postRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.POST + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// phần của hồ - admin - bài viết
	@Override
	public ResponseEntity<Page<Post>> getListPostAll(Pageable pageable) {
		Optional<Page<Post>> exist = postRepo.getListPostAll(pageable);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<Page<Post>> getListPostAllSearch(String textSearch, Pageable pageable) {
		Optional<Page<Post>> exist = postRepo.getListPostAllSearch(textSearch, pageable);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<Page<Post>> getListPostAllFilterByStatusCard(Long idStatus, Pageable pageable) {
		Optional<Page<Post>> exist = postRepo.getListPostAllFilterByStatusCard(idStatus, pageable);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<Page<Post>> getListPostAllFilterAndSearch(Long idStatus, String textSearch,
			Pageable pageable) {
		Optional<Page<Post>> exist = postRepo.getListPostAllFilterAndSearch(idStatus, textSearch, pageable);
		return ResponseEntity.ok(exist.get());
	}
	// kết thúc phần của hồ - admin - bài viết

	// phần của quang dùng cho đối tác
	@Override
	public ResponseEntity<Page<Post>> getListPostAllByIdUser(Long id, Pageable pageable) {
		Optional<Page<Post>> Post = postRepo.getListPostAllByIdUser(id, pageable);
		return ResponseEntity.ok(Post.get());
	}

	@Override
	public ResponseEntity<Page<Post>> getListPostAllByIdUserSearch(Long id, String textSearh, Pageable pageable) {
		Optional<Page<Post>> Post = postRepo.getListPostAllByIdUserSearch(id, textSearh, pageable);
		return ResponseEntity.ok(Post.get());
	}

	// end phần quang
	@Override
	public ResponseEntity<Post> findByCardId(Long cardId) {
		Optional<Post> exist = postRepo.findByCardId(cardId);
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<Page<Post>> getListPostFromHideCardWithIdCard(Long idCard, Pageable pageable) {
		Optional<Page<Post>> Post = postRepo.getListPostFromHideCardWithIdCard(idCard, pageable);
		return ResponseEntity.ok(Post.get());
	}

	@Override
	public ResponseEntity<List<Post>> loadAllPost() {
		Optional<List<Post>> exist = postRepo.loadAllPost();
		return ResponseEntity.ok(exist.get());
	}

	@Override
	public ResponseEntity<List<Post>> loadAllPostOfUser(Long idUser) {
		Optional<List<Post>> exist = postRepo.loadAllPostOfUser(idUser);
		return ResponseEntity.ok(exist.get());
	}
	


}
