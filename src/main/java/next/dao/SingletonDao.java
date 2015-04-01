package next.dao;

public class SingletonDao {
	
	private static AnswerDao answerDao = new AnswerDao();
	private static QuestionDao questionDao = new QuestionDao();
	
	public static AnswerDao getAnswerDao(){
		return answerDao;
	}
	
	public static QuestionDao getQuestionDao(){
		return questionDao;
	}
}
