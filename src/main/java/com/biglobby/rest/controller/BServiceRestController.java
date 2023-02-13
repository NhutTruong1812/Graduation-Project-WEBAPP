package com.biglobby.rest.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
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

import com.biglobby.entity.BService;
import com.biglobby.entity.BServiceAndprice;
import com.biglobby.entity.BServiceHistory;
import com.biglobby.entity.BServicePrice;
import com.biglobby.entity.BServiceSubBanner;
import com.biglobby.entity.MyBService; 
import com.biglobby.entity.PageTemp;
import com.biglobby.services.BServiceService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bservices")
public class BServiceRestController {

	@Autowired
	private BServiceService bsService;

	@GetMapping
	public ResponseEntity<List<BService>> get() {
		return bsService.get();
	}

	@GetMapping("/{id}")
	public ResponseEntity<BService> get(@PathVariable("id") Long id) {
		return bsService.get(id);
	}

	@PostMapping
	public ResponseEntity<BService> add(@RequestBody BService bservice) {
		return bsService.add(bservice);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BService> update(@PathVariable("id") Long id, @RequestBody BService bservice) {
		return bsService.update(id, bservice);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Integer> delete(@PathVariable("id") Long id) {
		return bsService.delete(id);
	}

	@GetMapping("/{id}/mybservices")
	public ResponseEntity<List<MyBService>> getMyBServices(@PathVariable("id") Long id) {
		return bsService.getMyBServices(id);
	}

	@GetMapping("/{id}/histories")
	public ResponseEntity<List<BServiceHistory>> getHistories(@PathVariable("id") Long id) {
		return bsService.getHistories(id);
	}

	@GetMapping("/{id}/prices")
	public ResponseEntity<List<BServicePrice>> getPrices(@PathVariable("id") Long id) {
		return bsService.getPrices(id);
	}

	@GetMapping("/{id}/subbanners")
	public ResponseEntity<List<BServiceSubBanner>> getSubBanners(@PathVariable("id") Long id) {
		return bsService.getSubBanners(id);
	}

	// Phần của quang
		@GetMapping(params = {"page", "limit", "status", "key"})
		public ResponseEntity<PageTemp<List<BServiceAndprice>>> getUserOrder(
				@RequestParam("page") int Npage,
				@RequestParam("limit") int Nitem 
				,@RequestParam("status") int status,
				@RequestParam("key") String textSearch) {
			//list page BService 
			if(status < 0) { 
				if (status == -1) {
						List<BServiceAndprice> page = BServiceAndprice.becomeBServiceAndprice(bsService.getListBServiceAllSearch(textSearch).getBody());
						return ResponseEntity.ok(new PageTemp(page, Npage-1, Nitem));
				}else {
					List<BServiceAndprice> page = BServiceAndprice.becomeBServiceAndprice(bsService.getListBServiceAllSearch(textSearch).getBody());
					return ResponseEntity.ok(new PageTemp(page, Npage-1, Nitem));
				}
			}else {
				 if(status == 1) {
					 List<BServiceAndprice> page = BServiceAndprice.becomeBServiceAndprice(bsService.getListBServiceAll().getBody());
						return ResponseEntity.ok(new PageTemp(page, Npage-1, Nitem));
					}else {
						List<BServiceAndprice> page = BServiceAndprice.becomeBServiceAndprice(bsService.getListBServiceAll().getBody());
						return ResponseEntity.ok(new PageTemp(page, Npage-1, Nitem));
					}

			}
		}
			
		// end phần của quang
}
