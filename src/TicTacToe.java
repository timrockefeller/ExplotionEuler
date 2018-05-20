import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
//import java.util.Iterator;

public class TicTacToe {
    
    public static void main (String[] args){
        //gameplay
        Game game = new Game();
    }
    
}

enum ChessType{
    CROSS,
    CIRCLE,
    BLANK,
    NULL
}

class Position{
    
    public Position(){
        this.x=0;this.y=0;
    }
    
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int x;
    
    public int y;
    
}

class Board{
    
    public Board () {
        //init
        this.map = new ChessType[3][3];
        for (int xm = 0;xm<3;xm++){
            for (int ym = 0;ym<3;ym++){
                this.map[xm][ym]= ChessType.BLANK;
            }
        }
    }
    
    public Board(ChessType[][] _map){
        this.map= new ChessType[3][3];
        for(int xm=0; xm<3;xm++){
            for(int ym=0;ym<3;ym++){
                this.map[xm][ym]= _map[xm][ym]==ChessType.CIRCLE?ChessType.CIRCLE:
                                  _map[xm][ym]==ChessType.CROSS?ChessType.CROSS:ChessType.BLANK;
            }
        }
    }
    
    private ChessType[][] map;
    
    public ChessType getBoard(Position pos){
        int x = pos.x,y = pos.y;
        if(x>=0&&x<3&&y>=0&&y<3)return this.map[x][y];
        else return ChessType.NULL;
    }
    
    public boolean setBoard(Position pos,ChessType type){
        int x = pos.x,y = pos.y;
        if(x>=0&&x<3&&y>=0&&y<3){
            if(this.map[x][y] == ChessType.BLANK){
                this.map[x][y] = type;
                return true;
            }else{
                System.out.print("Error: Not blank..\n");
                return false;
            }
        }else{
            System.out.print("Error: out if bound\n");
        }
        return false;
    }
    
    public void setBoardF(Position pos,ChessType type){
        int x= pos.x,y=pos.y;
        this.map[x][y]=type;
    }
    
    public void drawBoard(){
        System.out.print(" "+gs(map[0][0])+" | "+gs(map[0][1])+" | "+gs(map[0][2])+" \n");
        System.out.print("---+---+---\n");
        System.out.print(" "+gs(map[1][0])+" | "+gs(map[1][1])+" | "+gs(map[1][2])+" \n");
        System.out.print("---+---+---\n");
        System.out.print(" "+gs(map[2][0])+" | "+gs(map[2][1])+" | "+gs(map[2][2])+" \n");
    }
    
    private String gs(ChessType t){
        return t==ChessType.CROSS?"X":t==ChessType.CIRCLE?"O":" ";
    }
    
    public ChessType judge(){
        //linear
        for(int i = 0; i<3;i++){
            if (this.map[i][0]==this.map[i][1]
            &&  this.map[i][2]==this.map[i][1]
            &&  this.map[i][1]!=ChessType.BLANK)
            return this.map[i][0];
            
            if (this.map[0][i]==this.map[1][i]
            &&  this.map[2][i]==this.map[1][i]
            &&  this.map[1][i]!=ChessType.BLANK)
            return this.map[0][i];
        }
        //cross
        if (this.map[0][0]==this.map[1][1]
        &&  this.map[2][2]==this.map[1][1]
        &&  this.map[1][1]!=ChessType.BLANK)
        return this.map[1][1];
        if (this.map[0][2]==this.map[1][1]
        &&  this.map[2][0]==this.map[1][1]
        &&  this.map[1][1]!=ChessType.BLANK)
        return this.map[1][1];
        //default
        return ChessType.BLANK;
    }
    
    public boolean isFull(){
        for (int i=0;i<3;i++)
        for (int j=0;j<3;j++){
            if (map[i][j]==ChessType.BLANK){
                return false;
            }
        }
        return true;
    }
    
    public Board clone(){
        return new Board(this.map);
    }
    
}

abstract class Player{
    
    public abstract void onTurn(Board b);
    
    public Position hand;
    
    public ChessType type;
    
}

class Player_Human extends Player{
    
    public Player_Human(ChessType _type){
        this.type= _type;
        this.hand = new Position();
    }
    
    @Override
    public void onTurn(Board b){
        this.readInput();
    }
    
    private void readInput(){
        Scanner input = new Scanner (System.in);
        System.out.print("input x and y (1-3): ");
        this.hand.x = input.nextInt() - 1;
        this.hand.y = input.nextInt() - 1;
    }
    
}

class Node{
    
    public Node(){
        this.p= new Position(0,0);
    }
    
    public Node(Position pos,int i){
        this.p=pos;
        this.inspire=i;
    }
    
    public Position p;
    
    public int inspire;
    
}

class Way {
    
    public Way() {
        this.step = new Node();
    }
    
    public Way(Node s){
        this.step = new Node(new Position(s.p.x, s.p.y), s.inspire);
    }
    
    public int stats;//0:draw , 1:win , -1:lose
    
    public Node step;
    
    @Override
    public Way clone(){
        return new Way(this.step);
    }
    
}

class Player_PC extends Player{
    
    public Player_PC (ChessType _type) {
        this.type = _type;
        this.hand = new Position();
    }
    
    @Override
    public void onTurn(Board b){
        System.out.println("Thinking...");
        for (int i = 0; i<3; i++){
        for (int j = 0; j<3; j++){
            if(b.getBoard(new Position(i, j)) == ChessType.BLANK){
                b.setBoardF(new Position(i, j), this.type);
                if (b.judge() == this.type){
                    this.hand= new Position(i, j);
                    System.out.println("kati."+(i+1)+(j+1));
                    b.setBoardF(new Position(i, j), ChessType.BLANK);
                    return;
                }
                b.setBoardF(new Position(i,j), ChessType.BLANK);
            }
        }}
        int imm=0;
        for ( int i = 0; i<3; i++) {
        for ( int j = 0; j<3; j++) {
            if(b.getBoard(new Position(i, j))==ChessType.BLANK) {
                imm++;
                b.setBoardF(new Position(i, j), this.getOppoType(this.type));
                if(b.judge() == this.getOppoType(this.type)) {
                    this.hand = new Position(i, j);
                    b.setBoardF(new Position(i, j), ChessType.BLANK);
                    System.out.println("nana."+(i+1)+(j+1));
                    return;
                }
                b.setBoardF(new Position(i, j), ChessType.BLANK);
            }
        }}
        System.out.println(imm);
        this.ways = new ArrayList<Way>();
        Way root = new Way();
        this.ways.add(root);
        this.nextStop(b.clone(), root, 0, this.type);
        int mindepth =  200;
        int maxdepth = -200;
        int value[][]= new int[3][3];
        for (int oi = 0; oi < this.ways.size(); oi++){
            Way ov = this.ways.get(oi);
            value[ov.step.p.x][ov.step.p.y]+=ov.stats;
            /*if (ov.stats < mindepth){
                mindepth = ov.stats;
                this.hand=ov.step.p;
            }
            if (ov.stats > maxdepth){
                maxdepth = ov.stats;
                //this.hand = ov.step.p;
            }*/
        }
        for(int hi = 0; hi < 3; hi++) {
        for(int hj = 0; hj < 3; hj++) {
            if(value[hi][hj]>maxdepth&&b.getBoard(new Position(hi,hj))==ChessType.BLANK){
                this.hand=new Position(hi,hj);
                maxdepth=value[hi][hj];
            }
        }}

    }
    
    private static ChessType getOppoType(ChessType _type){
        return _type==ChessType.CIRCLE?ChessType.CROSS:ChessType.CIRCLE;
    }
    
    private ArrayList<Way> ways;
    
    private void nextStop(Board b,Way way,int depth,ChessType inType){
        boolean newWay = false;
        boolean full = true;
        for(int x = 0; x<3;x++){
            for(int y = 0; y<3;y++){
                if(b.getBoard(new Position(x,y))==ChessType.BLANK){
                    full = false;
                    Way _way;
                    if ( newWay ) {
                        _way = way.clone();
                        this.ways.add(_way);
                    } else { 
                        newWay = true;
                        _way = way;
                    }
                    if(depth==0)
                         _way.step=new Node(new Position(x, y), depth);
                    b.setBoardF(new Position(x, y), inType);
                    ChessType wms=b.judge();
                    if(!b.isFull())
                        this.nextStop(b.clone(), _way, depth+1, getOppoType(inType));
                    else if(wms != ChessType.BLANK)
                        _way.stats=wms==this.type?(100-depth):(depth-100);
                    b.setBoardF(new Position(x,y),ChessType.BLANK);
                }
            }
        }
    }
    
}

class Game{
    
    public Game(){
        //init
        //set players
        Scanner input = new Scanner(System.in);
        System.out.print("Select Player 1 (0-Human, 1-PC) : ");
        if(input.nextInt()==1){
            this.players[0] = new Player_PC(ChessType.CIRCLE);
        }else{
            this.players[0] = new Player_Human(ChessType.CIRCLE);
        }
        System.out.print("Select Player 2 : ");
        if(input.nextInt()==1){
            this.players[1] = new Player_PC(ChessType.CROSS);
        }else{
            this.players[1]= new Player_Human(ChessType.CROSS);
        }
        this.run();
    }
    
    private int round;

    private Board board;
    
    private Player[] players = new Player[2];
    
    private int turn;
    
    private boolean gaming;
    
    private void nextTurn(){
        ChessType imw = this.board.judge();
        if(imw != ChessType.BLANK){
            this.gaming = false;
            this.gameEnd(imw);
            return;
        }
        if (this.turn == 1){
            this.turn = 0;
            this.round += 1;
        } else this.turn ++;
        //if full
        
        if (this.board.isFull()) {
            this.gaming=false; 
            this.board.drawBoard();
            System.out.println("Draw!");
        }
    }
    
    private ChessType getTurnType(int _turn ){
        return _turn == 0? ChessType.CIRCLE:ChessType.CROSS;
    }
    
    private void run(){
        this.round = 0;
        this.board = new Board();
        this.turn = 0;
        this.gaming = true;
        while (this.gaming){
            this.board.drawBoard();
            try{Runtime.getRuntime().exec("cls");}catch(Exception e){}
            this.players[this.turn].onTurn(this.board.clone());
            if(this.board.setBoard(this.players[this.turn].hand,this.getTurnType(this.turn)))
                this.nextTurn();
            //else this.gaming=false;
        }
    }
    
    private void gameEnd(ChessType type){
        //end message
        this.board.drawBoard();
        System.out.println(type+" won the game !!!");
    }
}
