package com.biglobby.access.controller.qr;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.biglobby.entity.Order;
import com.biglobby.entity.Status;
import com.biglobby.repository.OrderRepository;
import com.biglobby.repository.StatusRepository;

@Controller
public class QrController {
//	1
	@GetMapping("/demo/qrban")
	public String demoqrban(Model m) {
		return "demonangcao/demoqrban";
	}

//	2
	@GetMapping("/demo/qrmua")
	public String demoqrmua(Model m) {
		return "demonangcao/demoqrmua";
	}
	
	@Autowired
	OrderRepository daoo;
	@Autowired
	StatusRepository daost;

//	3
	@GetMapping("/xacnhanqr")
	public String xacnhanqr(HttpServletRequest req, HttpServletResponse resp) {
		String qrdata = req.getParameter("qrdata");
		String idsaler = qrdata.substring(0, qrdata.indexOf("xacnhan"));
		String idorder = qrdata.substring(qrdata.indexOf("xacnhan")+7, qrdata.indexOf("qr"));
		String idbuyer = qrdata.substring(qrdata.indexOf("qr")+2, qrdata.length());
		//
		Order o = daoo.getById(Long.valueOf(idorder));
		Long idsalercheck = o.getSaler().getId();
		Long idbuyercheck = o.getBuyer().getId();
		if(Long.valueOf(idbuyer) == idbuyercheck && Long.valueOf(idsaler) == idsalercheck) {
			Status st = daost.getById(7l);
			o.setStatus(st);
			daoo.save(o);
			return "redirect:/user/index.html#!/biglobby/lichsumua";
		}else {
			return "redirect:/user/index.html#!/biglobby/lichsumua";
		}
	}

}
