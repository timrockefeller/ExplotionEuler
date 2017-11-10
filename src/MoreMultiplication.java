//package class1102;

import java.util.*;

public class MoreMultiplication {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.print("Please enter a number: ");
		String a = in.nextLine();
		System.out.print("Please enter another number: ");
		String b = in.nextLine();
		
        if(a.length() < b.length()) {
            String t = a;
            a = b;
            b = t;
        }else{
        	if( a.length() == b.length() ) {
        		for(int p = 0; p<a.length();p++){
        			if(a.substring(p,p+1)>b.substring(p,p+1)){
        				break;
        			}else if(a.substring(p,p+1)==b.substring(p,p+1)){
        				continue;
        			}else{
        				String t = a;
            			a = b;
            			b = t;
            			break;
        			}
        		}
        	}
        }
		Stack<String> outmap = new Stack<String>();

	    String[] buffer = new String[3];
	    buffer[0] = a+"";
	    buffer[1] = b+"";
	    buffer[2] = ((long)a*(long)b)+"";//Huge number multiply
	    

	    
	    String l1="+";//line I 这里是我们的第一个"+"
	    String l2="|   ";//line II
	    for(int i = 0; i < buffer[0].length(); i++){
	    	l1 += "----";
	    	l2 += buffer[0].substring(i,i+1)+"   ";
	    }
	    l1 += "---+";
	    l2 += "|";
	    
	    outmap.add(l1);
	    outmap.add(l2);
	    
	    
	    //Init Map
	    String[] map = new String[4*buffer[1].length()+1];
	    String hr = "+";
	    for(int i = 0; i < buffer[0].length(); i ++) {// hr君，就是传说中的分割行，很丑，hr = '+' + '---+---+---+';
        	hr+="---+";
    	}
    	map[0]=hr;
    	int cur = 1;
    	String m1="|",m2="|",m3="|";
    	for (int i = 0; i<buffer[0].length();i++) {//每三行进行一次制表
        	m1+="  /|";
        	m2+=" / |";
        	m3+="/  |";
    	}
    	for(int j = 0; j < buffer[1].length(); j ++) {
        	map[cur] = m1;
        	cur ++;
        	map[cur] = m2;
        	cur ++;
        	map[cur] = m3;
        	cur ++;
        	map[cur] = hr;
        	cur ++;
    	}
    	//replace sections
    	for(int i = 0; i < buffer[0].length(); i ++) {
        	for(int j = 0; j < buffer[1].length(); j ++) {
            	map[1 + 4 * j] = replacePos(map[1 + 4 * j], 1 + 4 * i, (int)((Integer.parseInt(buffer[0].substring(i,i+1)) * Integer.parseInt(buffer[1].substring(j,j+1))) / 10));
            	map[3 + 4 * j] = replacePos(map[3 + 4 * j], 3 + 4 * i, (int)((Integer.parseInt(buffer[0].substring(i,i+1)) * Integer.parseInt(buffer[1].substring(j,j+1))) % 10));
        	}
    	}
    	//map end
    	
    	
    	
    	////.rewrite/////
    	String[] inservedAns = new String[buffer[0].length()+buffer[1].length()];
    	
    	//for(int o = buffer[0].length()+buffer[1].length() - 1;o >= 0;o--){
    	
    	int numofspace = buffer[0].length() + buffer[1].length() - buffer[2].length();
    	for(int d = 0;d < numofspace; d++){
    		inservedAns[d]=" ";
    	}
    	for(int d = numofspace;d<buffer[0].length()+buffer[1].length();d++){
    		inservedAns[d]=buffer[2].substring(d-numofspace,d-numofspace + 1);
    	}
    	/////////////////
    	
    	
	    
	    for (int m = 0; m < /*map.length()*/ 4*buffer[1].length() + 1; m ++) {
        	outmap.push("|"+ ( m % 4 == 3 ? inservedAns[(m - 3) / 4] : (m % 4 == 1 && m != 1 ? "/" : " ") ) + map[m] + (m % 4 == 2 ? buffer[1].substring( (int)((m - 2) / 4) ,(int)((m - 2) / 4)+1) : " ") + "|");
    	}
    	
    	
    	//倒数第二行
    	String str_2 = "|";
    	for(int i = 0; i < buffer[0].length(); i ++) {
        	str_2 += "/ " + inservedAns[buffer[1].length() + i] + " ";
    	}
    	str_2 += "   |";
    	outmap.push(str_2);

    	//最后一行
    	
    	outmap.push(l1);
    	for (String p : outmap) {
        	System.out.println(p);
    	}
	}
	
	
	
	public static String replacePos (String strObj,int pos, int replacetext){
		String str = strObj.substring(0, pos) + replacetext + strObj.substring(pos + 1, strObj.length());
		return str;
	}

}
