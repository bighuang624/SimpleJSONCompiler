package frontend.util;

public class Counter { 
    private static int count = 0;
    
    public static void addTabs(){
    	count++;
    }
    
    public static void reduceTabs(){
    	count--;
    }
    
    public static int getTabsNum(){
    	return count;
    }
}
