package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.io.*;
import java.util.*;
/**
 * Created by:
 * Date:
 * A starter file to use the Finch
 */
public class TeamLarry {

    public static void main(final String[] args) {
        // Instantiating the Finch object

        Finch myFinch = new Finch();
        
        // Write some code here!
        //myFinch.buzz(262,100000);

        
        derp(10,255,myFinch);


        // Always end your program with finch.quit()
        myFinch.quit();
        System.exit(0);
    }
    
    public static void derp(float Time,int maxvel, Finch myFinch)
    {
      //  Random rand = new Random();
        float time = Time*1000;
        float lefttime=0;
        float righttime=0;
        float time1 = System.currentTimeMillis();
        int velr;
        int vell;
       
        while (time>0){
           if (lefttime<=0){
               lefttime=(long)(Math.random()*2000);
               vell =(int)(Math.random()*2*maxvel-maxvel);}
               
           if (righttime<=0){
            righttime=(long)(Math.random()*2000);
            velr =(int)(Math.random()*2*maxvel-maxvel);}
           
           
        
        
        
                
            myFinch.setWheelVelocities(vell,velr);
            float difference =(System.currentTimeMillis()-time1);
            time-=difference;
            lefttime-=difference;
            righttime-=difference;
            
        }
        myFinch.setWheelVelocities(0,0);
        
        
        
    }
    
}

