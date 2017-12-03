package gumbot;

import java.io.IOException;

import javax.imageio.ImageIO;

public class RedGumBot extends GumBot {

	protected void loadSprites() {
		try {
			//Carga imagenes para GumBot rojo
			idle_R=ImageIO.read(getClass().getResource("/sprites/gumbot_red_rest_r.png"));
			idle_L=ImageIO.read(getClass().getResource("/sprites/gumbot_red_rest_l.png"));
			
			
			
			run_R[0]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_r1.png"));
			run_R[1]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_r2.png"));
			run_R[2]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_r3.png"));
			run_R[3]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_r4.png"));
			run_R[4]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_r5.png"));
			run_R[5]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_r6.png"));
			
			
			run_L[0]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_l1.png"));	
			run_L[1]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_l2.png"));
			run_L[2]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_l3.png"));
			run_L[3]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_l4.png"));
			run_L[4]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_l5.png"));
			run_L[5]=ImageIO.read(getClass().getResource("/sprites/gumbot_red_l6.png"));
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
