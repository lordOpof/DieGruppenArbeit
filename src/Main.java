public class Main {
	public static void main(String[] args) {
		Model m = new Model(50, 50);
		UI ui = new UI();
		//noinspection unused
		Control controller = new Control(m, ui);
	}
}
