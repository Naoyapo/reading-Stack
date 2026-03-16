package com.example.readingStack.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.example.readingStack.dto.BookReviewRequest;
import com.example.readingStack.dto.BookReviewResponse;
import com.example.readingStack.service.BookReviewService;

@RestController
//リクエスト受け取り
@RequestMapping("/api")
public class BookReviewController {

	private final BookReviewService bookReviewService;
	//SpringがBookReviewServiceのインスタンスを渡す
	public BookReviewController(BookReviewService bookReviewService) {
		this.bookReviewService = bookReviewService;
	}
	
	@PostMapping("/book-reviews")
	public ResponseEntity<BookReviewResponse> createBookReview(
			//JSONをBookReviewRequestに変換して@Validを実行
			@RequestBody @Valid BookReviewRequest request) {
			
			BookReviewResponse response = bookReviewService.saveBookReview(request);
			//正常終了時はHTTPステータス(201)とレスポンスを返す
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
}
