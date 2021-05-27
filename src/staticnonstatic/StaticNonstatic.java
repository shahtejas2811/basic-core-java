package staticnonstatic;

public class StaticNonstatic {
	
	public static void main(String[] args) {
		try {
			Class.forName("staticnonstatic.car");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

class car {
	
	private String model;
	private static int noOfCar;
	
	static {
		System.out.println("in static block.");
	}

	// Instance method can access both instance and static methods and
	// variables.
	public int getNoofCarInShop() {
		// instance varibale
		System.out.println("Model " + model);
		// static variable
		return noOfCar;
	}

}