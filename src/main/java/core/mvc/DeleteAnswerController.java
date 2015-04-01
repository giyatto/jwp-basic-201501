package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dao.SingletonDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.ServletRequestUtils;

public class DeleteAnswerController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	AnswerDao answerDao = SingletonDao.getAnswerDao();
	QuestionDao questionDao = SingletonDao.getQuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		long answerId = ServletRequestUtils.getRequiredLongParameter(request, "answerId");
		
		logger.debug("questionId : {}, answerId : {}", questionId, answerId);
		
		answerDao.delete(questionId, answerId);
		ModelAndView mav = jstlView("redirect:/show.next?questionId="+questionId);
		questionDao.updateCountOfComment(questionDao.findById(questionId), "minus");
		return mav;
	}
}
