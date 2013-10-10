package uk.co.abbazaba.localprojectsrpgclient;

public class Token {
	String _id;
	String text;
	String etag;
	String color;
	String shortcode;
	transient int score;
	public transient TokenView viewReference;
}
