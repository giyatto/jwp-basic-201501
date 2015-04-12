package next.dao;

import java.util.List;

import next.ResourceNotFoundException;
import next.model.Question;

public interface QuestionDao {

	void insert(Question question);

	List<Question> findAll();

	Question findById(long questionId);

	void delete(long questionId);

	void updateCommentCount(long questionId);

	Question findWithAnswersById(long questionId) throws ResourceNotFoundException;

}