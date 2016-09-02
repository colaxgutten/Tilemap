package TilesFromWeb;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImageReaderFromOpenArt {
	String search;
	public ArrayList<Image> getImagesFromWeb(String search){
	Scanner scanner = new Scanner(System.in);
	Document doc = null;
	String subUrl ="http://opengameart.org/art-search-advanced?keys=";
	String endUrl="&title=&field_art_tags_tid_op=and&field_art_tags_tid=&name=&field_art_type_tid%5B%5D=9&sort_by=score&sort_order=DESC&items_per_page=24&Collection=";
	this.search = search;
	String totalUrl = subUrl+search+endUrl;
	ArrayList<URL> urls = new ArrayList<URL>();
	try {
		doc = Jsoup.connect(totalUrl).get();
	} catch (IOException e) {
		e.printStackTrace();
	}
	ArrayList<Element> elements = doc.getElementsByClass("view-content");
	for (Element e : elements){
		Elements imgsrc= e.getElementsByAttribute("src");
		for (Element imageurl : imgsrc){
			String url = imageurl.toString();
			if (url.length()>26){
			String urlsubstring = url.substring(10, url.length()-16);
			try {
				URL currenturl=new URL(urlsubstring);
				urls.add(currenturl);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			}
		}
	}
	ArrayList<Image> images = new ArrayList<Image>();
	for (URL urlloop : urls){
		BufferedImage image = null;
		try {
			image = ImageIO.read(urlloop);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (image!=null){
		Image convertedImage = SwingFXUtils.toFXImage(image,null);
		images.add(convertedImage);
		System.out.println(image.getHeight());
		}
	}
	return images;
	}
}
