import java.util.Random;

public class TestDemo {

	public int addPositive(int a, int b) {
	if(a>0 && b>0) { // if a and b are positive, return the sum
		return a + b;
	}else {
		throw new IllegalArgumentException("Both parameters must be positive!"); //otherwise throw exception, this is a built in package
	}
	}
	
	public int randomNumberSquared() { // method obtains rand int from 1-10 and returns the square of the #


		int randNum = getRandomInt();
		return randNum*randNum;
	}

	int getRandomInt() {
	Random random = new Random();
	return random.nextInt(10)+1;
	}
	

}
