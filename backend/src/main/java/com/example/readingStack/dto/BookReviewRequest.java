package com.example.readingStack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BookReviewRequest {
	//メッセージ(Controllerで@Validを使用するとチェックし、引っかかった場合にメッセージを返す)
	@NotBlank(message = "読書日は必須です。")
	//正規表現で日付を記載しているかを確認
	@Pattern( regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "読書日はYYYY-MM-DD 形式で入力してください")
	/** 読書日 */
	private String reviewDate;
	@NotBlank(message = "タイトルは必須です。")
	/** タイトル */
	private String title;
	/** 著者名 */
	private String author;
	/** 読んだ章・範囲 */
	private String readingRange;
	/** 要約 */
	private String summary;
	/** 良かった点 */
	private String goodPoints;
	/** 気づきや学び */
	private String learnings;
	/** 次にやること・どうアウトプットするか */
	private String nextActions;

	//getter / setter
	public String getReviewDate() { return reviewDate; }
    	public void setReviewDate(String reviewDate) { this.reviewDate = reviewDate; }
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getAuthor() { return author; }
	public void setAuthor(String author) { this.author = author; }
	
	public String getReadingRange() { return readingRange; }
	public void setReadingRange(String readingRange) { this.readingRange = readingRange; }
	
	public String getSummary() { return summary; }
	public void setSummary(String summary) { this.summary = summary; }
	
	public String getGoodPoints() { return goodPoints; }
	public void setGoodPoints(String goodPoints) { this.goodPoints = goodPoints; }
	
	public String getLearnings() { return learnings; }
	public void setLearnings(String learnings) { this.learnings = learnings; }
	
	public String getNextActions() { return nextActions; }
	public void setNextActions(String nextActions) { this.nextActions = nextActions; }
}
