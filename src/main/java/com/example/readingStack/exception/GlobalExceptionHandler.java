package com.example.readingStack.exception;

import com.example.readingStack.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Controllerで発生したエラーをErrorResponseでJSONを返す
@RestControllerAdvice
public class GlobalExceptionHandler {
	//@NotBlankや@Patternで弾かれた場合
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
		//Springが投げた最初のエラーを取得する
		FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
		//ステータス400とエラーメッセージを返す
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(fieldError.getDefaultMessage()));
	}
	//カスタム例外(400)
	@ExceptionHandler(InvalidTitleCharacterException.class)
	public ResponseEntity<ErrorResponse> handleInvalidTitleCharacter(InvalidTitleCharacterException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
	}
	
	//カスタム例外(404)
	@ExceptionHandler(SaveDirectoryNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleSaveDirectoryNotFound(SaveDirectoryNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
	}
	
	//カスタム例外(409)
	@ExceptionHandler(FileAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleFileAlreadyExists(FileAlreadyExistsException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
	}

	//その他の例外(500)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleUnExpected(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("想定外のエラーが発生しました。"));
	}
