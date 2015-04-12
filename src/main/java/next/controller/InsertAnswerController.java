package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.DispatcherServlet;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class InsertAnswerController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private AnswerDao answerDao;
	private QuestionDao questionDao;
	
	public InsertAnswerController(AnswerDao answerDao, QuestionDao questionDao){
		super();
		this.answerDao = answerDao;
		this.questionDao = questionDao;
	}
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		String writer = ServletRequestUtils.getRequiredStringParameter(request, "writer");
		String contents = ServletRequestUtils.getRequiredStringParameter(request, "contents");
		
		logger.debug("questionId : {}, writer : {}, contents : {}", questionId, writer, contents);
		
		answerDao.insert(new Answer(writer, contents,questionId));
		ModelAndView mav = jstlView("redirect:/show.next?questionId="+questionId);
		questionDao.updateCommentCount(questionId);
		return mav;
	}
}
