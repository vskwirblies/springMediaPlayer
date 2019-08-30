package de.chaot.smp.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import de.chaot.smp.controller.IndexController;

@RunWith(SpringRunner.class)
public class IndexControllerTest {

	private IndexController classUnderTest;
	
	@Before
	public void setup() {
//		System.out.println("Hello world, this is junit!");
		classUnderTest = new IndexController();
	}
	
	@Test
	public void whenIndexCalled_thenReturnIndex() {
		String indexCallResult = classUnderTest.index();
		assertTrue(indexCallResult.equals("index"));
	}
}
