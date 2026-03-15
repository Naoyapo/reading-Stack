package com.example.readingStack.dto;

public class BookReviewResponse {
	/** メッセージ */
	private String message;
	/** ファイル名 */
	private String fileName;
	/** ファイルパス */
	private String filePath;

	/**
	 * コンストラクタ作成
	 */ 
	public BookReviewResponse(String message, String fileName, String filePath) {
		this.message = message;
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	public String getMessage() {
		return message;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFilePath() {
		return filePath;
	}
}
