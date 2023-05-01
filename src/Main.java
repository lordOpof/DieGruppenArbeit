public class Main {
	public static void main(String[] args) {
		Model m = new Model(72, 128); //NOTE: this resolution is good
		UI ui = new UI();
		//noinspection unused
		Control controller = new Control(m, ui);
	}
}
