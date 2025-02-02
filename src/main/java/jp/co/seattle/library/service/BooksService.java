package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.dto.BookInfo;
import jp.co.seattle.library.dto.rentHistoryInfo;
import jp.co.seattle.library.rowMapper.BookDetailsInfoRowMapper;
import jp.co.seattle.library.rowMapper.BookInfoRowMapper;
import jp.co.seattle.library.rowMapper.rentHistoryRowMapper;

/**
 * 書籍サービス
 * 
 * booksテーブルに関する処理を実装する
 */
@Service
public class BooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * 書籍リストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<BookInfo> getBookList() {

		// TODO 取得したい情報を取得するようにSQLを修正
		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT id, title, author, publisher, publish_date, thumbnail_name, thumbnail_url FROM books ORDER BY title ASC",
				new BookInfoRowMapper());

		return getedBookList;
	}

	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public BookDetailsInfo getBookInfo(int bookId) {
		System.out.println(bookId);
		// JSPに渡すデータを設定する
		String sql = "SELECT *, CASE WHEN lending_date is null THEN '貸出し可' ELSE '貸出し中' END as status FROM books LEFT OUTER JOIN rentbooks ON books.id = rentbooks.book_id WHERE books.id = "
				+ bookId;
		BookDetailsInfo bookDetailsInfo = jdbcTemplate.queryForObject(sql, new BookDetailsInfoRowMapper());
		return bookDetailsInfo;
	}

	/**
	 * 書籍を登録する
	 *
	 * @param bookInfo 書籍情報
	 */
	public void registBook(BookDetailsInfo bookInfo) {

		String sql = "INSERT INTO books (title, author, publisher, publish_date, thumbnail_name, thumbnail_url, isbn, description, reg_date, upd_date) VALUES ('"
				+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" + bookInfo.getIsbn() + "','" + bookInfo.getDescription() + "',"
				+ "now()," + "now())";

		jdbcTemplate.update(sql);

	}

	/**
	 * 
	 * 書籍情報を更新する
	 * 
	 * @param locale      ローケル情報
	 * @param title       書籍名
	 * @param author      著者名
	 * @param publisher   出版社
	 * @param publishDate 出版日
	 * @param thumbnail   サムネイル
	 * @palam model モデル
	 * @param isbn        コード
	 * @param description 説明文
	 * @param bookId      書籍ID
	 * @return 遷移先画面
	 */

	public void updateBook(BookDetailsInfo bookInfo) {
		String sql;
		if (bookInfo.getThumbnailUrl() == null) {
			sql = "update books set title ='" + bookInfo.getTitle() + "', author ='" + bookInfo.getAuthor()
					+ "' , publisher ='" + bookInfo.getPublisher() + "', publish_date ='" + bookInfo.getPublishDate()
					+ "' , upd_date = 'now()'" + ",isbn = '" + bookInfo.getIsbn() + "', description= '"
					+ bookInfo.getDescription() + "' where id =" + bookInfo.getBookId() + ";";

			sql = "update books set title ='" + bookInfo.getTitle() + "', author ='" + bookInfo.getAuthor()
					+ "' , publisher ='" + bookInfo.getPublisher() + "', publish_date ='" + bookInfo.getPublishDate()
					+ "' , thumbnail_url ='" + bookInfo.getThumbnailUrl() + "', thumbnail_name ='"
					+ bookInfo.getThumbnailName() + "' , upd_date = 'now()'" + ",isbn = '" + bookInfo.getIsbn()
					+ "', description = '" + bookInfo.getDescription() + "' where id =" + bookInfo.getBookId() + ";";

			jdbcTemplate.update(sql);
		}
	}

	/**
	 * 
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 * 
	 * @param bookId 書籍ID
	 * @return
	 */
	public void deleteBook(int bookId) {

		String sql = "DELETE FROM books WHERE id =" + bookId;

		jdbcTemplate.update(sql);
	}

	/**
	 * 
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 * 
	 * @param
	 * @return bookId
	 */
	public int getMaxbookId() {

		String sql = "SELECT max(id) FROM books";
		int bookId = jdbcTemplate.queryForObject(sql, Integer.class);
		return bookId;
	}

	/**
	 * 書籍を一括登録する
	 *
	 * @param bookInfo    書籍情報
	 * @param title       書籍名
	 * @param author      著者名
	 * @param publisher   出版社
	 * @param publishDate 出版日
	 * @param isbn        コード
	 * @param description 説明文
	 * @return 遷移先画面
	 */
	public void bulkRegist(BookDetailsInfo bookInfo) {
		String sql = "INSERT INTO books (title, author, publisher, publish_date, isbn, description, reg_date, upd_date) VALUES ('"
				+ bookInfo.getTitle() + "','" + bookInfo.getAuthor() + "','" + bookInfo.getPublisher() + "','"
				+ bookInfo.getPublishDate() + "','" + bookInfo.getIsbn() + "','" + bookInfo.getDescription() + "',"
				+ "now()," + "now())";

		jdbcTemplate.update(sql);

	}

	/**
	 * 書籍の貸し出し
	 * 
	 * 
	 * @param bookId 書籍ID
	 */

	public void rentBook(int bookId) {
		// task15
		
		String sql ="insert into rentbooks (book_id, lending_date, return_date) values (" + bookId + "," + " now()," + " now()) "
				+ "on conflict (book_id) do update set lending_date = "+"now()"+", return_date =null";
		jdbcTemplate.update(sql);

	}

	/**
	 * 書籍を返却する
	 * 
	 * 
	 * @param bookId 書籍ID
	 */
	public void returnBook(int bookId) {
		// task15
		String sql = "update rentbooks set lending_date = null, return_date = now() "
				+ "where rentbooks.book_id = "+bookId+" and return_date is null";
		jdbcTemplate.update(sql);

	}

	/**
	 * 
	 * 
	 * @param
	 * @return bookId 書籍ID
	 */

	public int count() {
		String sql = "select count (lending_date) from rentbooks";

		return jdbcTemplate.queryForObject(sql, int.class);

	}

	/**
	 * 
	 * 
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public int size(int bookId) {
		// task15
		String sql = "select count (lending_date) from rentbooks WHERE book_id=" + bookId + "";
		return jdbcTemplate.queryForObject(sql, int.class);
	}

	/**
	 * 書籍の検索
	 * 
	 * @param title 書籍名
	 * @return 書籍リスト
	 * 
	 */
	public List<BookInfo> searchbook(String title) {

		List<BookInfo> getedBookList = jdbcTemplate.query(
				"SELECT id, title, author, publisher, publish_date, thumbnail_name, thumbnail_url FROM books WHERE title like '%"
						+ title + "%' ORDER BY title ",
				new BookInfoRowMapper());

		return getedBookList;

	}

	/**
	 * 書籍IDに紐づく書籍貸出日と返却日を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 書籍情報
	 */
	public List<rentHistoryInfo> rentHistory() {
		// JSPに渡すデータを設定する
		List<rentHistoryInfo> getedHistoryList = jdbcTemplate.query(
				"select books.id, title, lending_date, return_date from books inner join rentbooks ON books.id = rentbooks.book_id",
				new rentHistoryRowMapper());
		return getedHistoryList;
	}

}
