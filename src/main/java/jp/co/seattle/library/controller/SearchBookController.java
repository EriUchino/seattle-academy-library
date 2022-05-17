package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

/**
 * 書籍検索コントローラー
 */
@Controller // APIの入り口
public class SearchBookController {
	final static Logger logger = LoggerFactory.getLogger(SearchBookController.class);

	@Autowired
	private BooksService booksService;

	/**
	 * 
	 * @param locale ロケール情報
	 * @param title  書籍名
	 * @param model  モデル
	 * @return 遷移先画面
	 */
	@Transactional
	@RequestMapping(value = "/searchbook", method = RequestMethod.POST, produces = "text/plain;charset=utf-8") // value＝actionで指定したパラメータ
	public String searchbook(Locale locale, @RequestParam("title") String title, Model model) {
		logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

		model.addAttribute("bookList", booksService.searchbook(title));
		return "home";
	}
}