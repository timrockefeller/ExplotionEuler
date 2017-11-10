/*
s = {1,545,894,64,564,86}
s' = {}
k = 4
|s'|.max
*/




import java.util.*;
public class getMaxMod {
    public static void main(String[] args){
        //generate .begin
		//
		
        Scanner in = new Scanner(System.in);
		System.out.print("Please enter a k (2<=k<=99) :");
		int k = in.nextInt();
		
		
		
		int m = ((int)(Math.random() * 999))+2;
		int[] s = new int[m];
		for(int i = 0 ; i < m;i++){
			s[i] = ((int)(Math.random()*(Math.pow(10, 6)-1)))+1;
		}
        //generate .end
        
        
        /*/
        int k = 4;
        int m = 7;
        int[] s = new int[m];
        s[0] = 1;
		s[1] = 2;
		s[2] = 3;
		s[3] = 4;
		s[4] = 5;
		s[5] = 9;
		s[6] = 10;
		
		
        /*/
        //moded 
        int[] modedS =  new int[m];
        for (int i = 0; i < m; i ++){
            modedS[i] = s[i] % k;
        }
        //count num of all mods
        int[] countMod = new int[k];
        for(int j = 0; j < k; j ++){
            int temp=0;
            for (int i=0;i<m;i++){
                if(j == modedS[i]){
                    temp++;
                }
            }
            countMod[j]=temp;
        }
        
        
        int _m = m;
        if(countMod[0] >= 1){
            _m -= countMod[0] - 1;
        }
        for (int p = 1; p <= k / 2; p++ ){
            if(p == k / 2.0f){
                _m -= countMod[p] - 1;
            } else {
                if ( countMod[p] <= countMod[k - p] ) {
                    _m -= countMod[p];
                }
                else{
                    _m -= countMod[k - p];
                }
            }
            
        }
        
        //out put
        int _n=0;
        System.out.println(" k   = "+k);
        for (int p : countMod) {
        	System.out.println(" "+_n+"   : "+p);
        	_n++;
    	}
        System.out.println("|s'| = "+_m);
    }
}