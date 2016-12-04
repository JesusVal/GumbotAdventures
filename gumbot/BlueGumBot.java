package gumbot;

import java.io.IOException;
import javax.imageio.ImageIO;

public class BlueGumBot extends GumBot{

	//Carga imagenes para GumBot azul
	protected void loadSprites() {
		try {
			//images/gumbot/blue/r
			//blue_
			idle_R=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_rest_r.png"));
			idle_L=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_rest_l.png"));
			
			
			
			run_R[0]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_r1.png"));
			run_R[1]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_r2.png"));
			run_R[2]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_r3.png"));
			run_R[3]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_r4.png"));
			run_R[4]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_r5.png"));
			run_R[5]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_r6.png"));
			
			
			run_L[0]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_l1.png"));	
			run_L[1]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_l2.png"));
			run_L[2]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_l3.png"));
			run_L[3]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_l4.png"));
			run_L[4]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_l5.png"));
			run_L[5]=ImageIO.read(getClass().getResource("/sprites/gumbot_blue_l6.png"));
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		
	}

}
