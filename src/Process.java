
import java.util.Arrays;
import java.util.List;

public class Process{
  public int proc_id, proc_size, text, data, heap, pages, ext, frag;

  public static int id = 1000;

 public Process(int low,int high)
 {
  id += 1;
  proc_id = id;

  int lower,higher;
  double db = Math.random()*(high-low)+low;   //generates a number between low and high
  int k=1,sum=0;
  proc_size=(int) db;
  
  if(proc_size%32==0){                 //Process fits into the frame or multiple frames correctly
   pages=proc_size/32;
   
   }
   else
   {
	ext =proc_size%32;
        pages=(proc_size/32)+1;
        frag =32- ext;
   }
   lower=(proc_size/3)/2;                    // each proc has consists of text, data and heap
   higher=(proc_size/3)*2;                   
   int total=proc_size;
   while(k==1) 
   {
	double a = Math.random()*(higher-lower)+lower;  
    text=(int) a;
    total =total-text;     
    double b = Math.random()*(higher-lower)+lower;  
    data=(int) b;
    total =total- data;     
    if(total<=higher && total>=lower)
	{
     heap=total;
     k=0;
	}
    else
      total=proc_size;                       //repeating the procedure until random no.s generated satisfies the condition.
   }
  }

 public String[] toStringArray () {
   String [] stringArray = { getProc_id(), getProc_size(), getText(), getData(), getHeap()};
   return  stringArray;
  }

 public String getProc_id() {
  return String.valueOf(proc_id);
 }

 public String getProc_size() {
  return String.valueOf(proc_size);
 }
 public String getText() {
  return String.valueOf(text);
 }

 public String getData() {
  return String.valueOf(data);
 }

 public String getHeap() {
  return String.valueOf(heap);
 }
}
