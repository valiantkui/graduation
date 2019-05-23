package kui.common;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
public class Solution {
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        
    	
    	
    	
        ArrayList<Integer> list = new ArrayList<Integer>(2);
       
        int product = Integer.MAX_VALUE;
        int a = 0, b = array.length-1;
        int s = 0;
        while(a<b){
            s = array[a] +array[b];
            if(s<sum){
                //a指针右移
                a++;
                continue;
            }else if(s> sum){
                //b指针左移
                b--;
                continue;
            }
            int c = array[a] * array[b];
            
            if(c<product) {
                product = c;
                if(list.size()==0){
                      list.add(array[a]);
                        list.add(array[b]);
                }else{
                    list.set(0,array[a]);
                        list.set(1,array[b]);
                }
              
            }
            //如果相等
            a++;
        }
        return list;
    }
    
    public static void main(String[] args) {
		int [] array = {1,3,4,5,7,9,10,12};
		int sum = 15;
		ArrayList<Integer> list = new Solution().FindNumbersWithSum(array, sum);
		System.out.println(list);
	}
}