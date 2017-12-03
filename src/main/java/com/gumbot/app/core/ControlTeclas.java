package com.gumbot.app.core;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;


//Guarda las teclas presionadas en un HashSet
public class ControlTeclas implements KeyListener {
	
	private static HashSet<Integer> teclasPresionadas;
	
	public ControlTeclas(){
		teclasPresionadas=new HashSet<Integer>();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		teclasPresionadas.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		teclasPresionadas.remove(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public static HashSet<Integer> getActiveKeys(){
		return teclasPresionadas;
	}
	

}


