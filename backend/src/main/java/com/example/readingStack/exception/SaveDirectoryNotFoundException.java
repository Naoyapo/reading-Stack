package com.example.readingStack.exception;

public class SaveDirectoryNotFoundException extends RuntimeException {
	/** コンストラクタ */
	public SaveDirectoryNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
