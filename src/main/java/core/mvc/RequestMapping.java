package core.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import next.controller.DeleteAnswerController;
import next.controller.DeleteController;
import next.controller.InsertAnswerController;
import next.controller.InsertQuestionController;
import next.controller.JsonController;
import next.controller.ListController;
import next.controller.ShowController;
import next.dao.AnswerDao;
import next.dao.JdbcAnswerDao;
import next.dao.JdbcQuestionDao;
import next.dao.QuestionDao;
import next.service.QnaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.jdbc.ConnectionManager;
import core.jdbc.JdbcTemplate;

public class RequestMapping {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private Map<String, Controller> mappings = new HashMap<String, Controller>();
	
	public void initMapping() {
		
		DataSource dataSource = ConnectionManager.getDataSource();
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		AnswerDao answerDao = new JdbcAnswerDao(jdbcTemplate);
		QuestionDao questionDao = new JdbcQuestionDao(jdbcTemplate, answerDao);
		
		QnaService qnaService = new QnaService(answerDao, questionDao);
		
		mappings.put("/list.next", new ListController(questionDao));
		mappings.put("/show.next", new ShowController(answerDao, questionDao));
		mappings.put("/form.next", new ForwardController("form.jsp"));
		mappings.put("/save.next", new InsertQuestionController(questionDao));
		mappings.put("/delete.next", new DeleteController(qnaService));
		mappings.put("/api/addanswer.next", new InsertAnswerController(answerDao, questionDao));
		mappings.put("/api/deleteanswer.next", new DeleteAnswerController(answerDao, questionDao));
		mappings.put("/api/list.next", new JsonController(questionDao));
		
		logger.info("Initialized Request Mapping!");
	}

	public Controller findController(String url) {
		return mappings.get(url);
	}

	void put(String url, Controller controller) {
		mappings.put(url, controller);
	}

}
