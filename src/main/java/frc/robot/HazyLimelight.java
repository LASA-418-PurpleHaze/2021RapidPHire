//Imports fot the Subsystem and its functions
package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

//import edu.wpi.first.wpilibj.Timer;


public class HazyLimelight extends Subsystem {

  //Limelight
  public static double xOffset;
  public static double yOffset;
  public static double distanceToTarget;

  //Measurements for Distance Calculation
  double heightOfTarget = 98.0;
  double heightOfCamera = 22.0;
  double angleOfCamera = Math.toRadians(13.0);

  public HazyLimelight() {
    Robot.table = NetworkTableInstance.getDefault().getTable("limelight");
    xOffset = 0;
    yOffset = 0;
  }

   // New Limelight Test Stuff
   public void setLimelightX(){ //needs to be run periodically
    NetworkTableEntry tx = Robot.table.getEntry("tx");
    double x = tx.getDouble(0.0);
    xOffset = x;
  }

  public void setLimelightY(){ //needs to be run periodically
    NetworkTableEntry ty = Robot.table.getEntry("ty");
    double y = ty.getDouble(0.0);
    yOffset = y;
  }

  public void calculateDistance() {
    NetworkTableEntry ty = Robot.table.getEntry("ty");
    double y = ty.getDouble(0.0);
    double angleFromVision = Math.toRadians(y);
    //System.out.println("ANGLEFROMVISION: " + angleFromVision);
    distanceToTarget = (heightOfTarget-heightOfCamera) / Math.tan(angleOfCamera + angleFromVision);
  }

  public void printData(){
    //System.out.println("xOffset: " + xOffset + " yOffset: " + yOffset + " Distance to target: " + distanceToTarget);
  }
  

  @Override
  public void initDefaultCommand(){
      setDefaultCommand(Robot.commandLimelight);
  }
}