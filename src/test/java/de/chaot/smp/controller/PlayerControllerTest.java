package de.chaot.smp.controller;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerControllerTest {

	@Mock
	private List list;
	
	@Test
	public void testListAddActuallyAdds() {
		list.add(100);
		
		
	}
}
