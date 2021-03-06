package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;


public class JsonController extends AbstractController {

	private QuestionDao questionDao;
	
	public JsonController(QuestionDao questionDao){
		this.questionDao = questionDao;
	}
	
	
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Question> questions = questionDao.findAll();
		ModelAndView mav = jsonView();
		mav.addObject("questions", questions);
		return mav;
	}
}
