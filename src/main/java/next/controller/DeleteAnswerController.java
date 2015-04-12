package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.DispatcherServlet;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class DeleteAnswerController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	private AnswerDao answerDao;
	private QuestionDao questionDao;
	
	public DeleteAnswerController(AnswerDao answerDao, QuestionDao questionDao){
		super();
		this.answerDao = answerDao;
		this.questionDao = questionDao;
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		long answerId = ServletRequestUtils.getRequiredLongParameter(request, "answerId");
		
		logger.debug("questionId : {}, answerId : {}", questionId, answerId);
		
		answerDao.delete(answerId);
		ModelAndView mav = jstlView("redirect:/show.next?questionId="+questionId);
		questionDao.updateCommentCount(questionId);
		return mav;
	}
}
