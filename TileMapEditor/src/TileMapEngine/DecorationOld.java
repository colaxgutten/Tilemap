package TileMapEngine;
import java.util.Locale;

public class DecorationOld {
	private String imageName;
	private int xPos;
	private int yPos;
	
	private double xAdjust;
	private double yAdjust;

	public DecorationOld(int xPos, int yPos, String imageName) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.imageName = imageName;
	}

	public DecorationOld(int xPos, int yPos, double xAdjust, double yAdjust, String imageName) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.xAdjust = xAdjust;
		this.yAdjust = yAdjust;
		this.imageName = imageName;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public double getxAdjust() {
		return xAdjust;
	}

	public void setxAdjust(double xAdjust) {
		this.xAdjust = xAdjust;
	}

	public double getyAdjust() {
		return yAdjust;
	}

	public void setyAdjust(double yAdjust) {
		this.yAdjust = yAdjust;
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%d,%d,%.2f,%.2f,%s", xPos, yPos, xAdjust, yAdjust, imageName);
	}
}
