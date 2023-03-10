package com.biglobby.access.controller.napxu;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.biglobby.entity.Action;
import com.biglobby.entity.BCoinHistory;
import com.biglobby.entity.MyBCoin;
import com.biglobby.entity.User;
import com.biglobby.repository.BCoinHistoryRepository;
import com.biglobby.repository.MyBCoinRepository;
import com.biglobby.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class NapxuController {
	@GetMapping("/napxu")
	public String getNapxu(Model m) {
		return "demonangcao/index-napxu";
	}

	@Autowired
	BCoinHistoryRepository daobch;
	@Autowired
	MyBCoinRepository daombc;
	@Autowired
	UserRepository daou;
	
	@PostMapping("/napxu")
	public void postNapxu(Model m, HttpServletRequest req, HttpServletResponse resp) {
		//
		BCoinHistory tempbch = new BCoinHistory();
		tempbch.setUser(daou.getById(Long.valueOf(req.getParameter("nguoinap"))));
		tempbch.setQuantity(Integer.valueOf(req.getParameter("soxu")));
		tempbch.setAction(new Action(1l));
		tempbch.setNote("NAP XU THANH CONG");
		tempbch.setCoinDate(new Timestamp(new Date().getTime()));
		tempbch.setActBy(new User(1l));
		//
		BCoinHistory tempbchSave = daobch.save(tempbch);
		if(tempbchSave != null) {
			Optional<MyBCoin> tempmbc = daombc.findByUserId(Long.valueOf(req.getParameter("nguoinap")));
			if(tempmbc.isPresent()) {
				System.err.println("111");
				MyBCoin tempmbc2 =tempmbc.get();
				tempmbc2.setCoinnum(tempmbc2.getCoinnum()+Integer.valueOf(req.getParameter("soxu")));
				daombc.save(tempmbc2);
			}else {
				MyBCoin tempmbc2 = new MyBCoin(null, daou.getById(Long.valueOf(req.getParameter("nguoinap"))), Integer.valueOf(req.getParameter("soxu")));
				daombc.save(tempmbc2);
				System.err.println("222");
			}
			postVNPAY(req, resp, "dn"+tempbchSave.getId());
		}else {
			
		}
	}

	@GetMapping("/ketqua")
	public String ketquaNapxu(Model m) {
		return "demonangcao/index-ketqua";
	}

	private void postVNPAY(HttpServletRequest req, HttpServletResponse resp, String iddonnap) {
		try {
			// M???C ?????NH - md
			String vnp_Version = "2.1.0";
			String vnp_Command = "pay";
			// md > lo???i h??a ????n
//					String orderType = req.getParameter("ordertype");
			String orderType = "billpayment";
//					String orderType = "250000";
			// md - h??nh th???c thanh to??n: qua ng??n h??ng NCB
//					String bank_code = req.getParameter("bankcode");
			String bank_code = null;
//					TH??? M???U
//					Ng??n h??ng: NCB
//					S??? th???: 9704198526191432198
//					T??n ch??? th???: NGUYEN VAN A
//					Ng??y ph??t h??nh: 07/15
//					M???t kh???u OTP: 123456
			// md - ng??n ng??? h??? tr??? : Vietnam - vi
//					String locate = req.getParameter("language");
			String locate = null;
			// md - ng??y t???o
			Date dt = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			String vnp_CreateDate = formatter.format(dt);
			// md - ng??y h???t h???n
			Calendar cldvnp_ExpireDate = Calendar.getInstance();
			cldvnp_ExpireDate.add(Calendar.SECOND, 120);
			Date vnp_ExpireDateD = cldvnp_ExpireDate.getTime();
			String vnp_ExpireDate = formatter.format(vnp_ExpireDateD);

			// THAY ?????I - td
			// C???U H??NH B??N PAY.CONTROLLER.CONFIG
			// td - Config.vnp_Returnurl: g???i controller ????? update th??ng tin,
			// td - Config.vnp_TmnCode: m?? ????ng k?? thanh to??n b???ng VNPAY: 46NNXWON
			String vnp_TmnCode = Config.vnp_TmnCode;
			// td - Config.vnp_HashSecret: chu???i b?? m???t
			//
			// NH???N TR??N REQUEST HO???C SO???N S???N
			// td > n???i dung thanh to??n: kh??ng d???u: nh???p ho???c s???n
//			String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
			String vnp_OrderInfo = "nap xu thanh cong";
			// td > id ????n thanh to??n (id_bcoinhistory, id_bservicehistory): l???y t??? csdl
//			String vnp_TxnRef = Config.getRandomNumber(8);
//			String vnp_TxnRef = String.valueOf(301);
			// td > ?????a ch??? IP g???i thanh to??n: nh???n t??? req
			String vnp_IpAddr = Config.getIpAddress(req);
			// td > s??? ti???n thanh to??n: nh???p ho???c s???n
//			int amount = Integer.parseInt(req.getParameter("amount")) * 100;
//			int amount = 77700000;
			//
			// NH???N V??O T??? REQ
			String vnp_TxnRef = iddonnap;
			int amount = Integer.valueOf(req.getParameter("soxu")) * 1000 * 100;
			String nguoinap = req.getParameter("nguoinap");
			System.err.println("nguoinap " + nguoinap);

			// KHAI B??O JSON G???I V??? VNPAY
			Map<String, String> vnp_Params = new HashMap<>();
			vnp_Params.put("vnp_Version", vnp_Version);
			vnp_Params.put("vnp_Command", vnp_Command);
			vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
			vnp_Params.put("vnp_Amount", String.valueOf(amount));
			vnp_Params.put("vnp_CurrCode", "VND");
			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
			vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
			vnp_Params.put("vnp_OrderType", orderType);
			vnp_Params.put("vnp_Locale", "vn");
			vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

			// listtentruong
			List fieldNames = new ArrayList(vnp_Params.keySet());
			Collections.sort(fieldNames);
			StringBuilder hashData = new StringBuilder();
			StringBuilder query = new StringBuilder();
			// t???o ra v??ng l???p listtentruong
			Iterator itr = fieldNames.iterator();
			while (itr.hasNext()) {
				String fieldName = (String) itr.next();
				String fieldValue = (String) vnp_Params.get(fieldName);
				if ((fieldValue != null) && (fieldValue.length() > 0)) {
					// Build hash data
					hashData.append(fieldName);
					hashData.append('=');
					// hashData.append(fieldValue); //s?????? d??????ng v???? 2.0.0 v???? 2.0.1 checksum
					// sha256
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					// s?????? d??????ng
					// v2.1.0 check
					// sum sha512

					// Build query
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
					query.append('=');
					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					//
					if (itr.hasNext()) {
						query.append('&');
						hashData.append('&');
					}
				}
			}

			// KH???I T???O QUERY STRING
			String queryUrl = query.toString();
			// String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret +
			// hashData.toString());
			String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
			String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
			//
			com.google.gson.JsonObject job = new JsonObject();
			job.addProperty("code", "00");
			job.addProperty("message", "success");
			job.addProperty("data", paymentUrl);
			Gson gson = new Gson();
			//
			resp.getWriter().write(gson.toJson(job));
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}
	}

//	private void postVNPAY_origin(HttpServletRequest req, HttpServletResponse resp) {
//		try {
//			// M???C ?????NH - md
//			String vnp_Version = "2.1.0";
//			String vnp_Command = "pay";
//			// md > lo???i h??a ????n
////					String orderType = req.getParameter("ordertype");
//			String orderType = "billpayment";
////					String orderType = "250000";
//			// md - h??nh th???c thanh to??n: qua ng??n h??ng NCB
////					String bank_code = req.getParameter("bankcode");
//			String bank_code = null;
////					TH??? M???U
////					Ng??n h??ng: NCB
////					S??? th???: 9704198526191432198
////					T??n ch??? th???: NGUYEN VAN A
////					Ng??y ph??t h??nh: 07/15
////					M???t kh???u OTP: 123456
//			// md - ng??n ng??? h??? tr??? : Vietnam - vi
////					String locate = req.getParameter("language");
//			String locate = null;
//			// md - ng??y t???o
//			Date dt = new Date();
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//			String vnp_CreateDate = formatter.format(dt);
//			// md - ng??y h???t h???n
//			Calendar cldvnp_ExpireDate = Calendar.getInstance();
//			cldvnp_ExpireDate.add(Calendar.SECOND, 60);
//			Date vnp_ExpireDateD = cldvnp_ExpireDate.getTime();
//			String vnp_ExpireDate = formatter.format(vnp_ExpireDateD);
//			// md > ?????a ch??? IP g???i thanh to??n: nh???n t??? req
//			String vnp_IpAddr = Config.getIpAddress(req);
//
//			// THAY ?????I - td
//			// C???U H??NH B??N PAY.CONTROLLER.CONFIG
//			// td - Config.vnp_Returnurl: g???i controller ????? update th??ng tin,
//			// td - Config.vnp_TmnCode: m?? ????ng k?? thanh to??n b???ng VNPAY: 46NNXWON
//			String vnp_TmnCode = Config.vnp_TmnCode;
//			// td - Config.vnp_HashSecret: chu???i b?? m???t
//			//
//			// NH???N TR??N REQUEST HO???C SO???N S???N
//			// td > n???i dung thanh to??n: kh??ng d???u: nh???p ho???c s???n
////			String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
//			String vnp_OrderInfo = "nap xu thanh cong";
//			// td > id ????n thanh to??n (id_bcoinhistory, id_bservicehistory): l???y t??? csdl
//			String vnp_TxnRef = Config.getRandomNumber(8);
////			String vnp_TxnRef = String.valueOf(1117);
//			// td > s??? ti???n thanh to??n: nh???p ho???c s???n
////			int amount = Integer.parseInt(req.getParameter("amount")) * 100;
//			int amount = 77700000;
//
//			// KHAI B??O JSON G???I V??? VNPAY
//			Map<String, String> vnp_Params = new HashMap<>();
//			vnp_Params.put("vnp_Version", vnp_Version);
//			vnp_Params.put("vnp_Command", vnp_Command);
//			vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
//			vnp_Params.put("vnp_Amount", String.valueOf(amount));
//			vnp_Params.put("vnp_CurrCode", "VND");
//			vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
//			vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
//			vnp_Params.put("vnp_OrderType", orderType);
//			vnp_Params.put("vnp_Locale", "vn");
//			vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
//			vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
//			vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
//			vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
//
//			// listtentruong
//			List fieldNames = new ArrayList(vnp_Params.keySet());
//			Collections.sort(fieldNames);
//			StringBuilder hashData = new StringBuilder();
//			StringBuilder query = new StringBuilder();
//			// t???o ra v??ng l???p listtentruong
//			Iterator itr = fieldNames.iterator();
//			while (itr.hasNext()) {
//				String fieldName = (String) itr.next();
//				String fieldValue = (String) vnp_Params.get(fieldName);
//				if ((fieldValue != null) && (fieldValue.length() > 0)) {
//					// Build hash data
//					hashData.append(fieldName);
//					hashData.append('=');
//					// hashData.append(fieldValue); //s?????? d??????ng v???? 2.0.0 v???? 2.0.1 checksum
//					// sha256
//					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//					// s?????? d??????ng
//					// v2.1.0 check
//					// sum sha512
//
//					// Build query
//					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
//					query.append('=');
//					query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//					//
//					if (itr.hasNext()) {
//						query.append('&');
//						hashData.append('&');
//					}
//				}
//			}
//
//			// KH???I T???O QUERY STRING
//			String queryUrl = query.toString();
//			// String vnp_SecureHash = Config.Sha256(Config.vnp_HashSecret +
//			// hashData.toString());
//			String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
//			queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
//			String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//			//
//			com.google.gson.JsonObject job = new JsonObject();
//			job.addProperty("code", "00");
//			job.addProperty("message", "success");
//			job.addProperty("data", paymentUrl);
//			Gson gson = new Gson();
//			//
//			resp.getWriter().write(gson.toJson(job));
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.err.println(e);
//		}
//	}

}
