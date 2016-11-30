package core;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import core.ControlTeclas;
import gui.GamePanel;
import gumbot.*;

//Esta clase interconecta a los elementos de la gui con el jugador
public class GameManager extends Thread{
	//Delay de hilo
	private static final int MAIN_SLEEP_TIME=18;
		
	//Referencia al jugador y la gui
	private GumBot gumbot;
	private GamePanel gamePanel;
	//Define si el juego esta corriendo
	private boolean gameIsRunning;
	
	//Nivel actual
	private static final int FINAL_LEVEL = 4;
	private int currentlevel = 1;
	private World world;
	
		
	public GameManager(GamePanel gamePanel){	
		//Inicializa mundo
		this.world = new World();
		this.world.initializeStage(currentlevel);
		
		//Inicializa y junta a jugador y gui
		this.gumbot = new BlueGumBot();
		this.gamePanel = gamePanel;
		this.gamePanel.addGumBot(gumbot);	
		//Cambia el estado del juego a corriendo
		
		
		
		this.gameIsRunning=true;
	}
	
	@Override
	public void run() {
		
		while(gameIsRunning){
			
			
			if(gumbot.outLimits()){//Cambia de nivel
				if((currentlevel >= FINAL_LEVEL)){
					stopped();
				}
				world.initializeStage(++currentlevel);
				gumbot.reinitialize();
				
			}
			
			gumbot.checkFalling();
			gumbot.checkJumping();
			
			//Revisa que las teclas esten presionadas
			manageKeys();
			
			gumbot.checkSpecialBlocks();
			gumbot.checkCollision();
			gumbot.checkRestoring();
			gamePanel.repaintGame();
		
			try {
				Thread.sleep(MAIN_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopped() {
        gameIsRunning = false;
        GameManager.interrupted();
    }
	
	//Revisa las teclas presionadas
	private void manageKeys() {
		//Revisa las teclas presionadas
		HashSet<Integer> currentKeys=ControlTeclas.getActiveKeys();
			
		//Dependiendo la tecla presionada realiza las acciones
		if(currentKeys.contains(KeyEvent.VK_RIGHT)){
			gumbot.move(KeyEvent.VK_RIGHT);
		} else if (currentKeys.contains(KeyEvent.VK_LEFT)){
			gumbot.move(KeyEvent.VK_LEFT);
		} else if(currentKeys.isEmpty() && !gumbot.getJumping() && !gumbot.getFalling()){
			gumbot.stop();
		}
			
		//Esta se encuentra separada para poder usar dos teclas a la vez
		if(currentKeys.contains(KeyEvent.VK_SPACE)) {
			if(!gumbot.getJumping() && !gumbot.getFalling()){
				gumbot.jump();
			}
		}
			
	}

	//Retorna la referencia del jugador
	public GumBot getGumBot(){
		return gumbot;
	}
	
}
	