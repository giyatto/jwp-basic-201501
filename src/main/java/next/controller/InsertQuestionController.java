package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import core.utils.ServletRequestUtils;

public class InsertQuestionController extends AbstractController {

	private QuestionDao questionDao;

	public InsertQuestionController(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String writer = ServletRequestUtils.getRequiredStringParameter(request, "writer");
		String title = ServletRequestUtils.getRequiredStringParameter(request, "title");
		String contents = ServletRequestUtils.getRequiredStringParameter(request, "contents");

		questionDao.insert(new Question(writer, title, contents));

		ModelAndView mav = jstlView("redirect:/list.next");
		return mav;
	}
}
