package next.controller;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class DeleteController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(DeleteController.class);
	private AnswerDao answerDao = AnswerDao.getInstance();
	private QuestionDao questionDao = QuestionDao.getInstance();
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = ServletRequestUtils.getRequiredLongParameter(request, "questionId");
		logger.debug("질문 번호는 ?? questionId = {}", questionId);
		
		Question question = questionDao.findById(questionId);
		if (question == null) {
			throw new ServletException("존재하지 않는 질문입니다.");
		}
		
		List<Answer> answers = answerDao.findAllByQuestionId(questionId);
		if (answers.isEmpty()) {
			questionDao.delete(questionId);
			return jstlView("redirect:/list.next");
		}
		
		boolean canDelete = true;
		for (Answer answer : answers) {
			String writer = question.getWriter();
			if (!writer.equals(answer.getWriter())) {
				canDelete = false;
				break;
			}
		}
		
		if (canDelete) {
			questionDao.delete(questionId);
			return jstlView("redirect:/list.next");
		}
		
		ModelAndView mav = jstlView("show.jsp");
		mav.addObject("question", question);
		mav.addObject("answers", answers);
		mav.addObject("errorMessage", "다른 사용자가 추가한 댓글이 존재해 삭제할 수 없습니다.");
		return mav;
	}

}
