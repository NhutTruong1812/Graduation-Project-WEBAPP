package com.biglobby.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biglobby.entity.Post;
import com.biglobby.services.PostService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/posts")
public class PostRestController {

	@Autowired
	private PostService postService;

	@GetMapping(params = { "page", "limit" })
	public ResponseEntity<Page<Post>> get(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {
		return postService.get(page, limit);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> get(@PathVariable("id") Long id) {
		return postService.get(id);
	}

	@PostMapping
	public ResponseEntity<Post> add(@RequestBody Post post) {
		return postService.add(post);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> update(@PathVariable("id") Long id, @RequestBody Post post) {
		return postService.update(id, post);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return postService.delete(id);
	}

	// phần của hồ - admin - bài viết
	@GetMapping(params = { "page", "limit", "status", "key" })
	public ResponseEntity<Page<Post>> getAdminPost(@RequestParam("page") int Npage, @RequestParam("limit") int Nitem,
			@RequestParam("status") int status, @RequestParam("key") String textSearch) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		if (status < 0) {
			if (status == -1) {
				return postService.getListPostAllSearch(textSearch, pageable);
			} else if (status == -2) {
				return postService.getListPostAllFilterAndSearch(2l, textSearch, pageable);
			} else if (status == -3) {
				return postService.getListPostAllFilterAndSearch(3l, textSearch, pageable);
			} else {
				return postService.getListPostAllSearch(textSearch, pageable);
			}
		} else {
			if (status == 1) {
				return postService.getListPostAll(pageable);
			} else if (status == 2) {
				return postService.getListPostAllFilterByStatusCard(2l, pageable);
			} else if (status == 3) {
				return postService.getListPostAllFilterByStatusCard(3l, pageable);
			} else {
				return postService.getListPostAll(pageable);
			}
		}
	}
	// kết thúc phần của hồ - admin - bài viết

	// phần của quang dùng cho đối tác
	@GetMapping(params = { "page", "limit", "status", "key", "user" })
	public ResponseEntity<Page<Post>> getAdminPostByIdUser(@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, @RequestParam("status") int status,
			@RequestParam("key") String textSearch, @RequestParam("user") Long id) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);
		// list page Post
		if (status < 0) {
			if (status == -1) {
				return postService.getListPostAllByIdUserSearch(id, textSearch, pageable);
			} else {
				return postService.getListPostAllByIdUserSearch(id, textSearch, pageable);
			}
		} else {
			if (status == 1) {
				return postService.getListPostAllByIdUser(id, pageable);
			} else {
				return postService.getListPostAllByIdUser(id, pageable);
			}
		}
	}
	// end

	@GetMapping("/{id}/posts")
	public ResponseEntity<Post> findByCardId(@PathVariable("id") Long id) {
		return postService.findByCardId(id);
	}

	@GetMapping(params = { "card.id" })
	public ResponseEntity<Post> findByCardIdUseHiddenCard(@RequestParam("card.id") Long id) {
		return postService.findByCardId(id);
	}

	@GetMapping(params = { "page", "limit", "status", "key", "idCard" })
	public ResponseEntity<Page<Post>> getListPostFromHideCardWithIdCard(@RequestParam("page") int Npage,
			@RequestParam("limit") int Nitem, @RequestParam("status") int status,
			@RequestParam("key") String textSearch, @RequestParam("idCard") Long idCard) {
		Pageable pageable = PageRequest.of(Npage - 1, Nitem);

		return postService.getListPostFromHideCardWithIdCard(idCard, pageable);
	}

	@GetMapping(params = { "loadAllPost" })
	public ResponseEntity<List<Post>> loadAllPost(@RequestParam("loadAllPost") int loadAllPost) {
		return postService.loadAllPost();
	}

	@GetMapping(params = { "loadAllPostOfUser", "idUser" })
	public ResponseEntity<List<Post>> loadAllPostByUser(@RequestParam("loadAllPostOfUser") int loadAllPost,
			@RequestParam("idUser") Long idUser) {
		return postService.loadAllPostOfUser(idUser);
	}

}
