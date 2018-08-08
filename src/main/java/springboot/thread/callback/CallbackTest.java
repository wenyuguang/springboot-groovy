package springboot.thread.callback;

public class CallbackTest {

	public static void main(String[] args) {
		Student student = new Ricky();
		Teacher teacher = new Teacher(student);

		teacher.askQuestion();
	}
}