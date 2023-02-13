package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.biglobby.entity.News;
import com.biglobby.repository.NewsRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.NewsService;

@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	private NewsRepository newsRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<News> get(Long id) {
		Optional<News> news = newsRepo.findById(id);
		if (news.isPresent()) {
			return ResponseEntity.ok(news.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<News>> get() {
		List<News> news = newsRepo.findAll();
		return ResponseEntity.ok(news);
	}

	@Override
	public ResponseEntity<News> add(News entity) {
		if (entity.getId() != null) {
			entity.setId(null);
		}
		News added = newsRepo.save(entity);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.NEWS + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<News> update(Long id, News entity) {
		Optional<News> exist = newsRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		entity.setId(id);
		News updated = newsRepo.save(entity);
		if (updated != null) {
			simpMessagingTemplate.convertAndSend(Broker.NEWS + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<News> exist = newsRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = newsRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.NEWS + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
