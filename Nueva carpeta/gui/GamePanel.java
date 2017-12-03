package gui;

import java.awt.Color;
import javax.swing.JPanel;
import core.ControlTeclas;
import gumbot.GumBot;

//Crea un panel para mover a gumbot y une las estadisticas del juego con el espacio jugable
public class GamePanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused") //Esto lo puse porque aparece un warning, porque no uso a gumbot directamente
	private GumBot gumbot;
	private ControlTeclas controlTeclas;
	private StatsPanel statsPanel = new StatsPanel();
	private PlayPanel playPanel = new PlayPanel();
	
	
	public GamePanel(){
		this.setRequestFocusEnabled(true);
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setBackground(Color.CYAN);

		this.add(statsPanel);
		statsPanel.setLocation(0, 0);

		this.add(playPanel);
		playPanel.setLocation(0, StatsPanel.STATS_HEIGHT);
		
		
		controlTeclas =new ControlTeclas();
		this.addKeyListener(controlTeclas);
	}
	
	public void addGumBot(GumBot gumbot) {
		this.gumbot = gumbot;
		playPanel.addGumBot(gumbot);
		statsPanel.addGumBot(gumbot);
	}
	
	public void reduceScore(int i){
		this.statsPanel.reduceScore(i);
	}
	
	public void repaintGame(){
		playPanel.repaint();
		statsPanel.repaint();
	}
	
}
