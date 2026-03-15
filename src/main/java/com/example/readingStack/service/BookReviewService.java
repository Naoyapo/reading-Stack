package com.example.readingStack.service;

import com.example.readingStack.dto.BookReviewRequest;
import com.example.readingStack.dto.BookReviewResponse;
import com.example.readingStack.exception.FileAlreadyExistsException;
import com.example.readingStack.exception.InvalidTitleCharacterException;
import com.example.readingStack.exception.SaveDirectoryNotFoundException;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class BookReviewService {

	private static final Path SAVE_DIR = Paths.get("C:\\Users\\owner\\daily-and-books\\books");

	public BookReviewResponse saveBookReview(BookReviewRequest request) {
		validateTitleCharacters(request.getTitle());
		checkDirectoryExists(SAVE_DIR);
		Path filePath = buildFilePath(request.getReviewDate(), request.getTitle());
		checkFileNotExists(filePath);
		//生成したMarkdown
		String content = generateMarkdown(request);

		//ファイル保存
		try {
			Files.writeString(filePath, content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("ファイル保存に失敗しました。", e);
		}

		//成功時の戻り値
		return new BookReviewResponse("読書記録を保存しました。", filePath.getFileName().toString(), filePath.toString());
	}
	
	//タイトル禁止文字チェック
	private void validateTitleCharacters(String title) {
		if(title.matches(".*[\\\\/:*?\"<>|].*")) {
			throw new InvalidTitleCharacterException("使用できない文字があります。");
		}
	}

	//保存先フォルダ存在チェック
	private void checkDirectoryExists(Path directory) {
		if(!Files.isDirectory(directory)) {
			throw new SaveDirectoryNotFoundException("保存先フォルダが存在しません。");
		}
	}

	//同名ファイル存在チェック
	private void checkFileNotExists(Path filePath) {
		if(Files.exists(filePath)) {
			throw new FileAlreadyExistsException("同じ名前のファイルが既に存在します。");
		}
	}

	//ファイルパス生成
	private Path buildFilePath(String date, String title) {
		String fileName = date + "_" + title + ".md";
		return SAVE_DIR.resolve(fileName);
	}

	//Markdown作成
	private String generateMarkdown(BookReviewRequest request) {
		return  "# Book Review (" + request.getReviewDate() + ")\n\n"
                + "## 本の情報\n"
                + "- タイトル：" + request.getTitle() + "\n"
                + "- 著者：" + (request.getAuthor() != null ? request.getAuthor() : "") + "\n"
                + "- 読んだ範囲：" + (request.getReadingRange() != null ? request.getReadingRange() : "") + "\n\n"
                + "## 一言まとめ\n"
                + "- " + (request.getSummary() != null ? request.getSummary() : "") + "\n\n"
                + "## 良かった点\n"
                + "- " + (request.getGoodPoints() != null ? request.getGoodPoints() : "") + "\n\n"
                + "## 使えそうな学び\n"
                + "- " + (request.getLearnings() != null ? request.getLearnings() : "") + "\n\n"
                + "## 次にやること\n"
                + "- " + (request.getNextActions() != null ? request.getNextActions() : "") + "\n";
	}
}
