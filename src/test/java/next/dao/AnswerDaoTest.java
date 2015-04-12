package next.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import next.model.Answer;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.ConnectionManager;

public class AnswerDaoTest {
	
	private AnswerDao dut;
	
	@Before
	public void setup() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
	}

	@Test
	public void crud() throws Exception {
		long questionId = 1L;
		Answer expected = new Answer("javajigi", "answer contents", questionId);
		dut.insert(expected);
		
		List<Answer> answers = dut.findAllByQuestionId(questionId);
		assertTrue(answers.size() > 0);
	}
}
