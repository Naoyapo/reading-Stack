package com.example.readingStack.exception;

public class FileAlreadyExistsException extends RuntimeException {
	/** コンストラクタ */
	public FileAlreadyExistsException(String errorMessage) {
		super(errorMessage);
	}
}
