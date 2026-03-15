package com.example.readingStack.dto;

public class ErrorResponse {
	/** エラーメッセージ */
	private String errorMessage;

	/** コンストラクタ */
	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
