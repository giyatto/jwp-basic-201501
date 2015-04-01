package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.utils.ServletRequestUtils;

public class InsertAnswerController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao = new QuestionDao();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		String writer = ServletRequestUtils.getRequiredStringParameter(request, "writer");
		String contents = ServletRequestUtils.getRequiredStringParameter(request, "contents");
		
		logger.debug("questionId : {}, writer : {}, contents : {}", questionId, writer, contents);
		
		answerDao.insert(new Answer(writer, contents,questionId));
		ModelAndView mav = jstlView("redirect:/show.next?questionId="+questionId);
		questionDao.updateCountOfComment(questionDao.findById(questionId), "plus");
		return mav;
	}
}
