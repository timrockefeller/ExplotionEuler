import java.util.*;

class LargestGrid {
    public static void main (String[] args){
        //init grid
        int[][] grid = new int[20][20];
        for(int i = 0 ; i < 20 ; i++){
            for(int a = 0 ; a < 20 ; a++){
                grid[i][a]=(int)(Math.random()*100);
            }
        }
        //find max number
        int flag = 0;
        //0: -
        //1: |
        //2: \
        //3: /
        int maxans = 0;
        int maxposX = 0;
        int maxposY = 0;
        // "-"
        for(int x = 0;x < 20 - 4 + 1; x++){
            for (int y = 0; y < 20;y++){
                if( grid[x][y]*grid[x+1][y]*grid[x+2][y]*grid[x+3][y]>maxans){
                    maxans=grid[x][y]*grid[x+1][y]*grid[x+2][y]*grid[x+3][y];
                    maxposX = x;
                    maxposY = y;
                    flag = 0;
                }
            }
        }
        // "|"
        for(int x = 0;x < 20; x++){
            for (int y = 0; y < 20 - 4 + 1;y++){
                if( grid[x][y]*grid[x][y+1]*grid[x][y+2]*grid[x][y+3]>maxans){
                    maxans=grid[x][y]*grid[x][y+1]*grid[x][y+2]*grid[x][y+3];
                    maxposX = x;
                    maxposY = y;
                    flag = 1;
                }
            }
        }
        // "\"
        for(int x = 0;x < 20 - 4 + 1; x++){
            for (int y = 0; y < 20 - 4 + 1;y++){
                if( grid[x][y]*grid[x+1][y+1]*grid[x+2][y+2]*grid[x+3][y+3]>maxans){
                    maxans=grid[x][y]*grid[x+1][y+1]*grid[x+2][y+2]*grid[x+3][y+3];
                    maxposX = x;
                    maxposY = y;
                    flag = 2;
                }
            }
        }
        // "/"
        for(int x = 3;x < 20; x++){
            for (int y = 0; y < 20 - 4 + 1;y++){
                if( grid[x][y]*grid[x-1][y+1]*grid[x-2][y+2]*grid[x-3][y+3]>maxans){
                    maxans=grid[x][y]*grid[x-1][y+1]*grid[x-2][y+2]*grid[x-3][y+3];
                    maxposX = x;
                    maxposY = y;
                    flag = 3;
                }
            }
        }
        
        
        //print grid
        String[][] grid1 = new String[20][20];
        for(int i = 0 ; i < 20 ; i++){
            for(int a = 0; a < 20 ; a++){
                if(grid[i][a]<10){
                    grid1[i][a] = grid[i][a]+"";
                    grid1[i][a] = "0"+grid1[i][a];
                }else{
                    grid1[i][a] = grid[i][a]+"";
                }
                
                switch(flag){
                    case 0:
                        if(i>=maxposX && i<=maxposX + 3 && a==maxposY){
                            System.out.print(grid1[i][a]+"< ");
                            
                        }else{
                            System.out.print(grid1[i][a]+"  ");
                        }
                        break;
                    case 1:
                        if(i==maxposX && a<=maxposY + 3 && a>= maxposY){
                            System.out.print(grid1[i][a]+"< ");
                            
                        }else{
                            System.out.print(grid1[i][a]+"  ");
                        }
                        break;
                    case 2:
                        if(i==maxposX && a == maxposY ||i==maxposX + 3 && a==maxposY + 3 || i==maxposX + 2 && a==maxposY + 2|| i==maxposX + 1 && a==maxposY + 1){
                            System.out.print(grid1[i][a]+"< ");
                            
                        }else{
                            System.out.print(grid1[i][a]+"  ");
                        }
                        break;
                    case 3:
                        if(i==maxposX-3 && a==maxposY || i==maxposX && a==maxposY + 3|| i==maxposX -1 && a==maxposY + 2|| i==maxposX-2 && a==maxposY + 1){
                            System.out.print(grid1[i][a]+"< ");
                            
                        }else{
                            System.out.print(grid1[i][a]+"  ");
                        }
                        break;
                    
                    
                }
                
                if(a == 19){
                    System.out.println();
                }
            }
        }
        System.out.println("maxnum : "+maxans);
        
    }
    
    
}