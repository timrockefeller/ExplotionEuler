import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import javax.swing.*;

public class DanmakuGame extends JFrame {
	
	DanmakuGame(){
		this.Init();
	}
	
	private static final long serialVersionUID = 1L;
	
    private MyPanel mp = null;
    
    public static void main(String[] args) {
            new DanmakuGame();
    }
    
    private void Init(){
        this.mp = new MyPanel();
        
        this.setTitle("Green Hat");
        
        this.add(this.mp);	
        this.addKeyListener(this.mp);
        
        this.setSize(400,600);
        this.setVisible(true);
        //this.setBackground(new Color(255,255,255));
        
        this.mp.update();
    }
}

class MyPanel extends JPanel implements KeyListener {
	
	private static final long serialVersionUID = 2L;
	
    private Element self = new Element(ElementType.Self,200,500);
    
    public int heal = 100;
    
    private long score = 0;
    
    private ArrayList<Element> Enemies = new ArrayList<Element>();
    
    /**
     * Time Control
     */
    private long deltaTime = 200;
    
    private long fastestTime = 14;
    
    private long baseTime = 0;
    
    private boolean run = true;
    private int HealingStep = 0;
    public void update() {

    	while (true) {try{if(this.run) {
            //update
            this.baseTime++;
            int sideEx = 0;
            for (Element enemy : this.Enemies) {try{
            	
            	if(enemy.type == ElementType.Healing) {
            		enemy.goDownByStep(-2);
            		continue;
            	}
            	
            	//hush-hush
                if(enemy.type==ElementType.sideDamage) {
                	enemy.type =ElementType.Damage;
                }
                
                if(enemy.type == ElementType.Damage) {
                	enemy.goDown();
                	if(
                		(enemy.x+10 == this.self.x||enemy.x-10 == this.self.x || enemy.x == this.self.x ) &&
                		(enemy.y+10 == this.self.y||enemy.y-10 == this.self.y || enemy.y == this.self.y )
                	){
                		enemy.type = ElementType.sideDamage;
                		sideEx++;
                	}
                }
                
                if(enemy.y>=1000){
                    this.refreshEnemy();
                }
            }catch(Exception x) {}}
            
            if(heal < 95) {
            	this.heal += sideEx;
        	}else if(heal <= 100) {
        		this.heal = 100;
        	}
            
            this.repaint();
            this.ifCrash();
            
            this.score+=(long)(Math.pow(baseTime,1.2)/10.0) //add score
            		  + (long)(sideEx * Math.pow(this.baseTime/2.0,1.1)/2.0);
            
            //addDamage
            double max = (this.baseTime / 100 < 1000 ? this.baseTime / 100 : 10) + Math.random() * 2;

            for (long num = 0; num < max; num ++) {
                this.Enemies.add( new Element(ElementType.Damage) );
            }
            
            //addHealing
            
            this.HealingStep++;
            int healingFrq = 100;
            if(this.HealingStep>=healingFrq) {
            	this.HealingStep -= healingFrq;
            	this.Enemies.add(new Element(ElementType.Healing));
            }
            
            
            TimeUnit.MILLISECONDS.sleep(this.fastestTime
            						 + (long)( Math.pow(0.5, this.baseTime/100.0) 
            								 * (this.deltaTime - this.fastestTime)) );
            
    	}} catch (InterruptedException ex) {ex.printStackTrace();}}
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        //print self
        g.setColor(Color.BLACK);
        g.fillOval(this.self.x, this.self.y, 10, 10);
        
        //print enemies
        
        for (Element enemy : this.Enemies){
        	if(!enemy.crashed) {
        		switch(enemy.type) {
        		case Damage:
        			g.setColor(Color.RED);
        			break;
        		case sideDamage:
        			g.setColor(Color.PINK);
        			g.drawString("Ex", enemy.x, enemy.y);
        			break;
        		case Healing:
        			g.setColor(Color.GREEN);
        			g.drawString("HEAL", enemy.x-10, enemy.y);
        			break;
        		default:
        			g.setColor(Color.RED);
        			break;
        		}
            g.fillOval(enemy.x, enemy.y, 10, 10);
        	}
        }
        
        //if died
        if(this.self.type == ElementType.diedSelf) {
        	g.setColor(Color.GRAY);
        	g.fillOval(this.self.x, this.self.y, 10, 10);
        }
        
        //paint heal
        g.setColor(Color.RED);
        g.fillRect(0, 540, 400, 10);
        g.setColor(Color.GREEN);
        g.fillRect(0, 540, (int)(400.0*this.heal/100.0), 10);
        //paint score
        g.setColor(Color.BLUE);
        g.drawString("Score: "+this.score, 20, 20);
    }
    
    private void ifCrash() {
    	if(this.run) {
    		for(Element enemy: this.Enemies) {
    			if(!enemy.crashed) {
    				if(enemy.x == this.self.x && enemy.y == this.self.y) {
    					//if Catches
    					enemy.crashed = true;
    					if (enemy.type == ElementType.Healing) {//Gain Heal
    						this.heal = 100;
    						//remove it
    						
    						continue;
    					}
    					//if damage
    					this.heal -= 25;
    					if(this.heal<=0) {
    						this.gg();
    					}
    					break;
        			}
    			}
    		}
    	}
    }
    
    private void refreshEnemy() {//Limit memory using
    	ArrayList<Element> temp = new ArrayList<Element>();
    	for (Element old : this.Enemies) {
    		if(old.y <= 580) {
    			temp.add( new Element(old.type, old.x, old.y) );
    		}
    	}
    	this.Enemies = temp;
    }
    
    private void gg() {
		this.run = false;
    	this.self.type = ElementType.diedSelf;
    	//say something to player?
    	String rank = this.score<2000?"E":
    				  this.score<5000?"D":
    				  this.score<8000?"C":
    				  this.score<10000?"B":
    				  this.score<15000?"A You are now steped in.":
    				  this.score<25000?"S Awesome":
    				  this.score<40000?"SS !!Show score to PYQ now!!":
    				  this.score<70000?"SSS !!!!let me your name!!!":
    					  			   "X !!You are Monster!";
    	String message = "Score: "+this.score+"\nYour Rank:\n"+rank+"\nRetry?";
    	String title = "Good Job";
    	int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE); 
    	if(reply == JOptionPane.YES_NO_OPTION) {
    		this.restart();
    	}
    }
    
    private void restart() {
    	this.self = new Element(ElementType.Self,200,500);
    	this.score =0;
    	this.Enemies = new ArrayList<Element>();
    	this.heal = 100;
    	this.baseTime = 0;
    	this.run = true;
    	this.update();
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
    	if(this.self.type == ElementType.Self) {
    		if(e.getKeyCode()==KeyEvent.VK_DOWN & this.self.y<540){
    			this.self.y+=10;
        	}else if(e.getKeyCode()==KeyEvent.VK_UP & this.self.y>2){
        		this.self.y-=10;
        	}else if(e.getKeyCode()==KeyEvent.VK_LEFT & this.self.x>2){
        		this.self.x-=10;
        	}else if(e.getKeyCode()==KeyEvent.VK_RIGHT & this.self.x<365){
        		this.self.x+=10;
        	}
    		this.repaint();
    		this.ifCrash();
        	
        	
    	}
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
}

/**
 * Element Class is use for create a buffer to storage something need to be rendered
 * -> *ArrayList*
 */
class Element{//for render
    
    Element (ElementType type){
        this.type = type;
        this.y = 0;
        this.x = (int)(Math.random()*39)*10;
    }
    
    Element (ElementType type,int x,int y){
        this.type = type;
        this.x = x;
        this.y = y;
    }
    
    
    
    public ElementType type;
    
    public int x;
    
    public int y;
    
    public boolean crashed = false;
    
    public void goDown(){
        this.y += 10;
    }
    
    
    private int stepconter = 0;
    
    public void goDownByStep(int step) {
    	this.stepconter++;
    	if(step>=0) {
    		if (stepconter>=step) {
    			this.stepconter -= step;
    			this.goDown();
    		}
    	}else {
    		for(int i =0;i<-step;i++) {
    			this.goDown();
    		}
    	}
    }
}
enum ElementType{
	Self,
	diedSelf,
	Damage,
	sideDamage,
	Healing
}