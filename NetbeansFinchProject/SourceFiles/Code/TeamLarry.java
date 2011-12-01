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
        long minTime=200;
        long maxTime=500;
        
        long time = (long)Time*1000;
        long lefttime=0;
        long righttime=0;
        long time1 = (long)System.currentTimeMillis();
        int velr=0;
        int vell=0;
       
        while (time>0){
           if (lefttime<=0){
               lefttime=(long)(Math.random()*(maxTime-minTime)+minTime);
               vell =(int)(Math.random()*2*maxvel-maxvel);}
               
           if (righttime<=0){
            righttime=(long)(Math.random()*(maxTime-minTime)+minTime);
            velr =(int)(Math.random()*2*maxvel-maxvel);}
           
           
        
        
        
                
            myFinch.setWheelVelocities(vell,velr);
            long difference =(long)(System.currentTimeMillis()-time1);
            time-=difference;
            lefttime-=difference;
            righttime-=difference;
            time1=System.currentTimeMillis();
            
        }
        myFinch.setWheelVelocities(0,0);
        
        
        
    }
    
}

