package com.biglobby.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

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

public interface CardService {

	public ResponseEntity<Card> get(Long id);

	public ResponseEntity<List<Card>> get();

	public ResponseEntity<Card> add(Card card);

	public ResponseEntity<Card> update(Long id, Card card);

	public ResponseEntity<Integer> delete(Long id);

	public ResponseEntity<Post> getPost(Long cardId);

	public ResponseEntity<Product> getProduct(Long cardId);

	public ResponseEntity<List<LoveCard>> getLoves(Long cardId);

	public ResponseEntity<Long> getCountOfLoves(Long cardId);

	public ResponseEntity<LoveCard> getLove(Long loveId);

	public ResponseEntity<LoveCard> addLove(LoveCard loveCard);

	public ResponseEntity<LoveCard> updateLove(Long loveId, LoveCard loveCard);

	public ResponseEntity<Integer> deleteLove(Long loveId);

	public ResponseEntity<List<CommentCard>> getComments(Long cardId);

	public ResponseEntity<Page<CommentCard>> getPageComments(Long cardId, Integer pagenum, Integer limit);

	public ResponseEntity<Long> getCountOfComments(Long cardId);

	public ResponseEntity<CommentCard> getComment(Long commentId);

	public ResponseEntity<CommentCard> addComment(CommentCard comment);

	public ResponseEntity<CommentCard> updateComment(Long commentId, CommentCard comment);

	public ResponseEntity<Integer> deleteComment(Long commentId);

	public ResponseEntity<List<RepComment>> getCommentReplys(Long commentId);

	public ResponseEntity<RepComment> getReply(Long repId);

	public ResponseEntity<RepComment> addReply(RepComment reply);

	public ResponseEntity<RepComment> updateReply(Long repId, RepComment reply);

	public ResponseEntity<Integer> deleteReply(Long repId);

	public ResponseEntity<List<ShareCard>> getShares(Long cardId);

	public ResponseEntity<ShareCard> getShare(Long shareId);

	public ResponseEntity<Long> getCountOfShares(Long cardId);

	public ResponseEntity<ShareCard> addShare(ShareCard share);

	public ResponseEntity<ShareCard> updateShare(Long shareId, ShareCard share);

	public ResponseEntity<Integer> deleteShare(Long shareId);

	public ResponseEntity<List<HideCard>> getHides(Long cardId);

	public ResponseEntity<HideCard> getHide(Long hideId);

	public ResponseEntity<HideCard> addHide(HideCard hide);

	public ResponseEntity<HideCard> updateHide(Long hideId, HideCard hide);

	public ResponseEntity<Integer> deleteHide(Long hideId);

	public ResponseEntity<List<ReportCard>> getReports(Long cardId);

	public ResponseEntity<ReportCard> getReport(Long reportId);

	public ResponseEntity<ReportCard> addReport(ReportCard report);

	public ResponseEntity<ReportCard> updateReport(Long rpId, ReportCard report);

	public ResponseEntity<Integer> deleteReport(Long reportId);

	public ResponseEntity<List<FailHistory>> getFailHistories(Long cardId);

	public ResponseEntity<FailHistory> getFailHistory(Long fid);

	public ResponseEntity<FailHistory> addFailHistory(FailHistory fht);

	public ResponseEntity<FailHistory> updateFailHistory(Long fid, FailHistory fht);

	public ResponseEntity<Integer> deleteFailHistory(Long fid);

	public ResponseEntity<List<CardSubBanner>> getSubBanners(Long cardId);

	public ResponseEntity<CardSubBanner> getSubBanner(Long sid);

	public ResponseEntity<CardSubBanner> addSubBanner(CardSubBanner subbanner);

	public ResponseEntity<CardSubBanner> updateSubBanner(Long sid, CardSubBanner subbanner);

	public ResponseEntity<Integer> deleteSubBanner(Long sid);

}
