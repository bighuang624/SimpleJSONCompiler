package frontend.util;

public class TabsCounter {
    private static int count = 0;
    
    public static void addTabs(){
    	count++;
    	
    }
    
    public static void reduceTabs(){
    	count--;
//    	System.out.println("count:" + count);
    }
    
    public static int getTabsNum(){
//    	System.out.println("count:" + count);
    	return count;
    }
}
