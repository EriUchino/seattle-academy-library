package jp.co.seattle.library.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import jp.co.seattle.library.dto.rentHistoryInfo;

@Configuration
public class rentHistoryRowMapper implements RowMapper<rentHistoryInfo> {

    @Override
    public rentHistoryInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Query結果（ResultSet rs）を、オブジェクトに格納する実装
    	rentHistoryInfo rentHistoryInfo = new rentHistoryInfo();
        // bookInfoの項目と、取得した結果(rs)のカラムをマッピングする
    	rentHistoryInfo.setId(rs.getInt("id"));
        rentHistoryInfo.setTitle(rs.getString("title"));
        rentHistoryInfo.setLendingDate(rs.getString("lending_date"));
        rentHistoryInfo.setReturnDate(rs.getString("return_date"));
        return rentHistoryInfo;
    }

}