import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class CDL_KataTest {
	
	CDL_Kata cut = new CDL_Kata();
	
	@Test
	public void testUserInputsForYesNo() {
		assertTrue("Test will be true because input is 'Yes'", cut.yesOrNo("Yes"));
		assertTrue("Test will be true because input is 'Y'", cut.yesOrNo("Y"));
		assertFalse("Test will be false because input is 'No'", cut.yesOrNo("N"));
		assertFalse("Test will be false because input is 'N'", cut.yesOrNo("N"));
	}
	
	@Test
	public void canGetItemsAsCharArray() {
		char[] items = cut.findItems("aabc ,gdbc342");
		assertThat("Length of output matches input AND excludes whitespace and comma", items.length, is(11));
		assertThat("Can format into a char array - First character", String.valueOf(items[0]), is("A"));
		assertThat("Can format into a char array - Fifth character", String.valueOf(items[4]), is("G"));
		assertThat("Can format into a char array - Tenth character", String.valueOf(items[9]), is("4"));
	}
	
}
