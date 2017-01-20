package TileMapEditor.TileMapEngine;

import java.util.ArrayList;
import java.util.List;

public class SearchTree<Value> {
	private Node<Value> root;

	private static class Node<Value> {
		private char c;
		private Node<Value> left, mid, right;
		private Value val;
	}
	
	public List<Value> searchAll(String prefix) {
		ArrayList<Value> list = new ArrayList<>();
		getNextChar(prefix, root, 0, list);
		
		return list;
	}
	
	public Value search(String prefix) {
		return getNextChar(prefix, root, 0);
	}

	public void put(String key, Value val) {
		root = put(root, key, val, 0);
	}

	private Node<Value> put(Node<Value> x, String key, Value val, int i) {
		char c = key.charAt(i);
		if (x == null) {
			x = new Node<Value>();
			x.c = c;
		}
		if (c < x.c)
			x.left = put(x.left, key, val, i);
		else if (c > x.c)
			x.right = put(x.right, key, val, i);
		else if (i < key.length() - 1)
			x.mid = put(x.mid, key, val, i + 1);
		else
			x.val = val;
		return x;
	}
	
	private void getAllChars(Node<Value> x, List<Value> list) {
		if (x.mid != null)
			getAllChars(x.mid, list);
		
		if (x.left != null)
			getAllChars(x.left, list);
		
		if (x.right != null)
			getAllChars(x.right, list);
		
		if(x.val != null)
			list.add(x.val);
			
	}

	private Value getFirstChar(Node<Value> x) {
		if (x.mid != null)
			return getFirstChar(x.mid);
		else if (x.mid != null)
			return getFirstChar(x.left);
		else if (x.right != null)
			return getFirstChar(x.right);
		else
			return x.val;
	}

	private void getNextChar(String prefix, Node<Value> x, int i, List<Value> list) {
		if(x == null)
			return;
		
		if(i >= prefix.length()) {
			getAllChars(x, list);
			return;
		}
		
		char c = prefix.charAt(i);
		
		if(x.c == c) {
			i++;
			x = x.mid;
			if(i == prefix.length()) {
				getAllChars(x, list);
				return;
			}
				
		}else if (x.c > c)
				x = x.left;
		else if (x.c < c)
				x = x.right;
		
		getNextChar(prefix, x, i, list);
	}
	
	private Value getNextChar(String prefix, Node<Value> x, int i) {
		if (x == null)
			return null;

		char c = prefix.charAt(i);

		System.out.println("x.c: " + x.c + " and x: " + getFirstChar(x));
		
		if (x.c > c)
			x = x.left;
		else if (x.c < c)
			x = x.right;
		else if (x.c == c) {
			i++;
			if (i == prefix.length())
				return getFirstChar(x);
			else
				x = x.mid;
		}

		return getNextChar(prefix, x, i);
	}
}