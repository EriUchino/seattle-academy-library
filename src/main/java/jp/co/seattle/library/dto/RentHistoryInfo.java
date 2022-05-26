package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 書籍詳細情報格納DTO
 *
 */
@Configuration
@Data
public class rentHistoryInfo {

    private int id;

    private String title;

    private String lendingDate;
 
    private String returnDate;

    public rentHistoryInfo() {

    }

    public rentHistoryInfo(int id, String title, String lendingDate, String returnDate) {
        this.id = id;
        this.title = title;
        this.lendingDate = lendingDate;
        this.returnDate = returnDate;
       
    }

}