package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.BooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class rentHistoryController {
	final static Logger logger = LoggerFactory.getLogger(rentHistoryController.class);

	@Autowired
	private BooksService booksService;
	
	@RequestMapping(value = "/rentHistory", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String rentHistory(Locale locale, Model model) {
		model.addAttribute("bookList", booksService.rentHistory());
		return "rentHistory";
	}
}
	
	
	
