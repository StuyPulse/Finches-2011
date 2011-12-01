package Code;
// Needs a package declaration to move to another folder

import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import java.io.*;
import java.util.*;
/**
 * Created by: George, and not the rest of the team because they're all derps
 * Date:12/1/11
 * A starter file to use the Finch
 */
public class TeamLarry {

    public static void main(final String[] args) {
        // Instantiating the Finch object

        Finch myFinch = new Finch();
        
        derp(5,100,255,0,300,myFinch);


        // Always end your program with finch.quit()
        myFinch.quit();
        System.exit(0);
    }
    
    public static void derp(float Time,int MinVel,int MaxVel,long MinTime, long MaxTime, Finch myFinch)
    {
        long minTime=200;
        long maxTime=500;
        long minVel=0;
        long maxVel=255;
        
        minTime=MinTime;
        maxTime=MaxTime;
        minVel=MinVel;
        maxVel=MaxVel;
        
        long time = (long)(Time*1000);
        long lefttime=0;
        long righttime=0;
        long time1 = (long)System.currentTimeMillis();
        long timeStart = (long)System.currentTimeMillis();
        int velr=0;
        int vell=0;
        boolean hasChanged=false;
       
        while (time+timeStart>(long)System.currentTimeMillis()){
           if (lefttime<=0){
               lefttime=(long)(Math.random()*(maxTime-minTime)+minTime);
               vell =(int)(Math.random()*(maxVel-minVel)+minVel)*(int)(Math.round(Math.random())*2-1);
               hasChanged=true;}
               
           if (righttime<=0){
               righttime=(long)(Math.random()*(maxTime-minTime)+minTime);
               velr =(int)(Math.random()*(maxVel-minVel)+minVel)*(int)(Math.round(Math.random())*2-1);
               hasChanged=true;}
                  
               
            if(hasChanged){
                myFinch.setWheelVelocities(vell,velr);
                hasChanged=false;
            }
            long difference =(long)(System.currentTimeMillis()-time1);
            lefttime-=difference;
            righttime-=difference;
            time1=System.currentTimeMillis();
            
        }
        myFinch.setWheelVelocities(0,0);
    }
    
}

