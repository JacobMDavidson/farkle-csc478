import static org.junit.Assert.*;

import org.junit.Test;

public class SampleJUnitTest {

	@Test
	public void test() {
		int a = 0, b = 1;
		
		assertTrue(true);
		assertFalse(false);
		
		if(a == b) {
			fail();
		}
	}

}
