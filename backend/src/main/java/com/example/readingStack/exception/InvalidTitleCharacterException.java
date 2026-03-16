package com.example.readingStack.exception;

public class InvalidTitleCharacterException extends RuntimeException {
	/** コンストラクタ */
	public InvalidTitleCharacterException(String errorMessage) {
		super(errorMessage);
	}
}
