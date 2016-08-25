public class Decoration {
	private String imageName;
	private double xPos;
	private double yPos;

	public Decoration(double xPos, double yPos, String imageName) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.imageName = imageName;
	}
	
	@Override
	public String toString() {
		return String.format("%.1f,%.1f,%s", xPos, yPos, imageName);
	}
}
