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

import com.biglobby.entity.Card;
import com.biglobby.entity.CardSubBanner;
import com.biglobby.entity.CommentCard;
import com.biglobby.entity.FailHistory;
import com.biglobby.entity.HideCard;
import com.biglobby.entity.LoveCard;
import com.biglobby.entity.Post;
import com.biglobby.entity.Product;
import com.biglobby.entity.RepComment;
import com.biglobby.entity.ReportCard;
import com.biglobby.entity.ShareCard;
import com.biglobby.repository.CardRepository;
import com.biglobby.repository.CardSubBannerRepository;
import com.biglobby.repository.CommentCardRepository;
import com.biglobby.repository.FailHistoryRepository;
import com.biglobby.repository.HideCardRepository;
import com.biglobby.repository.LoveCardRepository;
import com.biglobby.repository.RepCommentRepository;
import com.biglobby.repository.ReportCardRepository;
import com.biglobby.repository.ShareCardRepository;
import com.biglobby.server.configuration.Broker;
import com.biglobby.server.configuration.BrokerAction;
import com.biglobby.services.CardService;
import com.biglobby.services.PostService;
import com.biglobby.services.ProductService;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	private CardRepository cardRepo;

	@Autowired
	private PostService postService;

	@Autowired
	private ProductService productService;

	@Autowired
	private LoveCardRepository loveCardRepo;

	@Autowired
	private CommentCardRepository commentRepo;

	@Autowired
	private ShareCardRepository shareRepo;

	@Autowired
	private HideCardRepository hideRepo;

	@Autowired
	private ReportCardRepository reportRepo;

	@Autowired
	private FailHistoryRepository failRepo;

	@Autowired
	private CardSubBannerRepository subbannerRepo;

	@Autowired
	private RepCommentRepository replyRepo;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public ResponseEntity<Card> get(Long id) {
		Optional<Card> card = cardRepo.findById(id);
		if (card.isPresent()) {
			return ResponseEntity.ok(card.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<Card>> get() {
		List<Card> cards = cardRepo.findAll();
		return ResponseEntity.ok(cards);
	}

	@Override
	public ResponseEntity<Card> add(Card card) {
		if (card.getId() != null) {
			card.setId(null);
		}
		Card added = cardRepo.save(card);
		if (added != null) {
			simpMessagingTemplate.convertAndSend(Broker.CARD + "/" + BrokerAction.CREATE, added);
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Card> update(Long id, Card card) {
		Optional<Card> exist = cardRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		card.setId(id);
		Card updated = cardRepo.save(card);
		if (updated != null) { 
			simpMessagingTemplate.convertAndSend(Broker.CARD + "/" + BrokerAction.UPDATE, updated);
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> delete(Long id) {
		Optional<Card> exist = cardRepo.findById(id);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = cardRepo.removeById(id);
		if (deletedRow != null && deletedRow > 0) {
			simpMessagingTemplate.convertAndSend(Broker.CARD + "/" + BrokerAction.DELETE, exist.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Post> getPost(Long cardId) {
		return postService.get(cardId);
	}

	@Override
	public ResponseEntity<Product> getProduct(Long cardId) {
		return productService.get(cardId);
	}

	@Override
	public ResponseEntity<List<LoveCard>> getLoves(Long cardId) {
		Optional<Card> card = cardRepo.findById(cardId);
		if (!card.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(card.get().getLoves());
	}

	@Override
	public ResponseEntity<List<CommentCard>> getComments(Long cardId) {
		Optional<Card> card = cardRepo.findById(cardId);
		if (!card.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(card.get().getCommments());
	}

	@Override
	public ResponseEntity<List<ShareCard>> getShares(Long cardId) {
		Optional<Card> card = cardRepo.findById(cardId);
		if (!card.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(card.get().getShares());
	}

	@Override
	public ResponseEntity<List<HideCard>> getHides(Long cardId) {
		Optional<Card> card = cardRepo.findById(cardId);
		if (!card.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(card.get().getHides());
	}

	@Override
	public ResponseEntity<List<ReportCard>> getReports(Long cardId) {
		Optional<Card> card = cardRepo.findById(cardId);
		if (!card.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(card.get().getReports());
	}

	@Override
	public ResponseEntity<List<FailHistory>> getFailHistories(Long cardId) {
		Optional<Card> card = cardRepo.findById(cardId);
		if (!card.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(card.get().getFailHistories());
	}

	@Override
	public ResponseEntity<List<CardSubBanner>> getSubBanners(Long cardId) {
		Optional<Card> card = cardRepo.findById(cardId);
		if (!card.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(card.get().getSubBanners());
	}

	@Override
	public ResponseEntity<LoveCard> getLove(Long loveId) {
		Optional<LoveCard> loveCard = loveCardRepo.findById(loveId);
		if (loveCard.isPresent()) {
			return ResponseEntity.ok(loveCard.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<LoveCard> addLove(LoveCard loveCard) {
		if (loveCard.getId() != null) {
			loveCard.setId(null);
		}
		LoveCard added = loveCardRepo.save(loveCard);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<LoveCard> updateLove(Long loveId, LoveCard loveCard) {
		Optional<LoveCard> exist = loveCardRepo.findById(loveId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		loveCard.setId(loveId);
		LoveCard updated = loveCardRepo.save(loveCard);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteLove(Long loveId) {
		Optional<LoveCard> exist = loveCardRepo.findById(loveId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = loveCardRepo.removeById(loveId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<CommentCard> getComment(Long commentId) {
		Optional<CommentCard> commentCard = commentRepo.findById(commentId);
		if (commentCard.isPresent()) {
			return ResponseEntity.ok(commentCard.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<CommentCard> addComment(CommentCard comment) {
		if (comment.getId() != null) {
			comment.setId(null);
		}
		CommentCard added = commentRepo.save(comment);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<CommentCard> updateComment(Long commentId, CommentCard comment) {
		Optional<CommentCard> exist = commentRepo.findById(commentId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		comment.setId(commentId);
		CommentCard updated = commentRepo.save(comment);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteComment(Long commentId) {
		Optional<CommentCard> exist = commentRepo.findById(commentId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = commentRepo.removeById(commentId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ShareCard> getShare(Long shareId) {
		Optional<ShareCard> sc = shareRepo.findById(shareId);
		if (sc.isPresent()) {
			return ResponseEntity.ok(sc.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<ShareCard> addShare(ShareCard share) {
		if (share.getId() != null) {
			share.setId(null);
		}
		ShareCard added = shareRepo.save(share);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ShareCard> updateShare(Long shareId, ShareCard share) {
		Optional<ShareCard> exist = shareRepo.findById(shareId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		share.setId(shareId);
		ShareCard updated = shareRepo.save(share);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteShare(Long shareId) {
		Optional<ShareCard> exist = shareRepo.findById(shareId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = shareRepo.removeById(shareId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<HideCard> getHide(Long hideId) {
		Optional<HideCard> hideCard = hideRepo.findById(hideId);
		if (hideCard.isPresent()) {
			return ResponseEntity.ok(hideCard.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<HideCard> addHide(HideCard hide) {
		if (hide.getId() != null) {
			hide.setId(null);
		}
		HideCard added = hideRepo.save(hide);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<HideCard> updateHide(Long hideId, HideCard hide) {
		Optional<HideCard> exist = hideRepo.findById(hideId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		hide.setId(hideId);
		HideCard updated = hideRepo.save(hide);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteHide(Long hideId) {
		Optional<HideCard> exist = hideRepo.findById(hideId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = hideRepo.removeById(hideId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ReportCard> getReport(Long reportId) {
		Optional<ReportCard> rc = reportRepo.findById(reportId);
		if (rc.isPresent()) {
			return ResponseEntity.ok(rc.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<ReportCard> addReport(ReportCard report) {
		if (report.getId() != null) {
			report.setId(null);
		}
		ReportCard added = reportRepo.save(report);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<ReportCard> updateReport(Long rpId, ReportCard report) {
		Optional<ReportCard> exist = reportRepo.findById(rpId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		report.setId(rpId);
		ReportCard updated = reportRepo.save(report);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteReport(Long reportId) {
		Optional<ReportCard> exist = reportRepo.findById(reportId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = reportRepo.removeById(reportId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<FailHistory> getFailHistory(Long fid) {
		Optional<FailHistory> failHistory = failRepo.findById(fid);
		if (failHistory.isPresent()) {
			return ResponseEntity.ok(failHistory.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<FailHistory> addFailHistory(FailHistory fht) {
		if (fht.getId() != null) {
			fht.setId(null);
		}
		FailHistory added = failRepo.save(fht);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<FailHistory> updateFailHistory(Long fid, FailHistory fht) {
		Optional<FailHistory> exist = failRepo.findById(fid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		fht.setId(fid);
		FailHistory updated = failRepo.save(fht);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteFailHistory(Long fid) {
		Optional<FailHistory> exist = failRepo.findById(fid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = failRepo.removeById(fid);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<CardSubBanner> getSubBanner(Long sid) {
		Optional<CardSubBanner> sb = subbannerRepo.findById(sid);
		if (sb.isPresent()) {
			return ResponseEntity.ok(sb.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<CardSubBanner> addSubBanner(CardSubBanner subbanner) {
		if (subbanner.getId() != null) {
			subbanner.setId(null);
		}
		CardSubBanner added = subbannerRepo.save(subbanner);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<CardSubBanner> updateSubBanner(Long sid, CardSubBanner subbanner) {
		Optional<CardSubBanner> exist = subbannerRepo.findById(sid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		subbanner.setId(sid);
		CardSubBanner updated = subbannerRepo.save(subbanner);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteSubBanner(Long sid) {
		Optional<CardSubBanner> exist = subbannerRepo.findById(sid);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = subbannerRepo.removeById(sid);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<List<RepComment>> getCommentReplys(Long commentId) {
		List<RepComment> replys = replyRepo.findByCommentId(commentId);
		return ResponseEntity.ok(replys);
	}

	@Override
	public ResponseEntity<RepComment> getReply(Long repId) {
		Optional<RepComment> reply = replyRepo.findById(repId);
		if (reply.isPresent()) {
			return ResponseEntity.ok(reply.get());
		}
		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<RepComment> addReply(RepComment reply) {
		if (reply.getId() != null) {
			reply.setId(null);
		}
		RepComment added = replyRepo.save(reply);
		if (added != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(added);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<RepComment> updateReply(Long repId, RepComment reply) {
		Optional<RepComment> exist = replyRepo.findById(repId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		reply.setId(repId);
		RepComment updated = replyRepo.save(reply);
		if (updated != null) {
			return ResponseEntity.ok(updated);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Integer> deleteReply(Long repId) {
		Optional<RepComment> exist = replyRepo.findById(repId);
		if (!exist.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Integer deletedRow = replyRepo.removeById(repId);
		if (deletedRow != null && deletedRow > 0) {
			return ResponseEntity.ok(deletedRow);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Override
	public ResponseEntity<Page<CommentCard>> getPageComments(Long cardId, Integer pagenum, Integer limit) {
		Pageable pageable = PageRequest.of(pagenum, limit, Sort.by("commentDate").ascending());
		Page<CommentCard> commentCard = commentRepo.getCommentByCardId(cardId, pageable);
		return ResponseEntity.ok(commentCard);
	}

	@Override
	public ResponseEntity<Long> getCountOfLoves(Long cardId) {
		Long count = loveCardRepo.getCountByCardId(cardId);
		return ResponseEntity.ok(count);
	}

	@Override
	public ResponseEntity<Long> getCountOfComments(Long cardId) {
		Long count = commentRepo.getCountByCardId(cardId);
		return ResponseEntity.ok(count);
	}

	@Override
	public ResponseEntity<Long> getCountOfShares(Long cardId) {
		Long count = shareRepo.getCountByCardId(cardId);
		return ResponseEntity.ok(count);
	}

}
