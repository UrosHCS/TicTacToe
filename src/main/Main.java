package main;

import game.Game;

public class Main {

	public static void main(String[] args) {
		go();

	}
	
	public static void go() {
		Game xo = Game.getInstance();
		xo.fillLabelMatrix();
		xo.buildGUI();
	}

}
