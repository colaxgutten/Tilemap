import java.util.Locale;

public class Decoration {
	private String imageName;
	private double xPos;
	private double yPos;

	public Decoration(double xPos, double yPos, String imageName) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.imageName = imageName;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public double getxPos() {
		return xPos;
	}

	public void setxPos(double xPos) {
		this.xPos = xPos;
	}

	public double getyPos() {
		return yPos;
	}

	public void setyPos(double yPos) {
		this.yPos = yPos;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%.1f,%.1f,%s", xPos, yPos, imageName);
	}
}
