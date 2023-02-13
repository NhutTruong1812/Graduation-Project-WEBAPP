package com.biglobby.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.biglobby.entity.RegisterActive;
import com.biglobby.repository.RegisterActiveRepository;
import com.biglobby.services.RegisterActiveService;
import com.biglobby.services.SessionService;

@Service
public class RegisterActiveServiceImpl implements RegisterActiveService {

	@Autowired
	private RegisterActiveRepository regRepo;

	@Autowired
	SessionService session;

	@Override
	public ResponseEntity<RegisterActive> get(Long id) {
		Optional<RegisterActive> reg = regRepo.findById(id);
		if (reg.isPresent()) {
			return ResponseEntity.ok(reg.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<RegisterActive>> get() {
		List<RegisterActive> regs = regRepo.findAll();
		return ResponseEntity.ok(regs);
	}

	@Override
	public ResponseEntity<RegisterActive> add(RegisterActive entity) {
		RegisterActive added = regRepo.save(entity);
		if (added != null) {
			session.set("sessionRegisterActive", added.getId());
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<RegisterActive> update(Long id, RegisterActive entity) {
		Optional<RegisterActive> exist = regRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		RegisterActive updated = regRepo.save(entity);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<RegisterActive> exist = regRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = regRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<RegisterActive> getByIdUser(Long id) {
		Optional<RegisterActive> reg = regRepo.findByUserId(id);
		if (reg.isPresent()) {
			return ResponseEntity.ok(reg.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<RegisterActive> updateStatus(Long id) {
		Optional<RegisterActive> exist = regRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			exist.get().setId(id);
			exist.get().setActived(true);
			RegisterActive updated = regRepo.save(exist.get());
			while (updated == null) {
				System.err.println("bị null nè cu");
			}
			return ResponseEntity.ok(updated);
		}
	}

	@Override
	public ResponseEntity<Page<RegisterActive>> getListUserAll(Pageable pageable) {
		Optional<Page<RegisterActive>> User = regRepo.getListUserAllToRegister(pageable);
		return ResponseEntity.ok(User.get());
	}

	@Override
	public ResponseEntity<Page<RegisterActive>> getListUserAllSearch(String textSearh, Pageable pageable) {
		Optional<Page<RegisterActive>> User = regRepo.getListUserAllSearchToRegister(textSearh, pageable);
		return ResponseEntity.ok(User.get());
	}

	@Override
	public ResponseEntity<RegisterActive> getByUserId(Long userId) {
		Optional<RegisterActive> rega = regRepo.findByUserId(userId);
		if (rega.isPresent()) {
			return ResponseEntity.ok(rega.get());
		}
		return ResponseEntity.notFound().build();
	}

}
