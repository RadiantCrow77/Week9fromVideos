import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestDemoTest {
private TestDemo testDemo;

	@BeforeEach
	void setUp(){
		// 4.a) in setUP method, create TestDemo obj
		// ensures new TestDemo obj created before each test
		 testDemo = new TestDemo();
	}


	@ParameterizedTest
	@MethodSource("TestDemoTest#argumentsForAddPositive")
	void assertThatTwoPositiveNumbersAreAddedCorrectly(int a, int b, int expected, boolean expectException) {
		
		
		// Test here:
		// if expectException false, assert that when TestDemo.addPositive is called w/ a and b, the result is the same as expected
		if(!expectException) {
			  assertThat(testDemo.addPositive(a, b)).isEqualTo(expected);
		}else {
			assertThatThrownBy(() -> testDemo.addPositive(a,b)).isInstanceOf(IllegalArgumentException.class);
		}
	}
	

	public static Stream<Arguments> argumentsForAddPositive() {
		//@formatter:off
		return Stream.of(
			Arguments.of(2, 4, 6, false), // 2+4=6, not exception
			Arguments.of(117, 3, 120, false),
			Arguments.of(1,-5, 3, true),
			Arguments.of(-1,-5, 3, true),
			Arguments.of(77, -100, -2, true),
			Arguments.of(0, 7, -5, true)
				);
		//@formatter:on
	}
	
	@Test
	@Disabled
	void assertThatNumberSquaredIsCorrect() {
		TestDemo mockDemo = spy(testDemo);
		doReturn(5).when(mockDemo).getRandomInt();
		int fiveSquared = mockDemo.randomNumberSquared();
		assertThat(fiveSquared).isEqualTo(25);
		
	}
	
	} // end file


