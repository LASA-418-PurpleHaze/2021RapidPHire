//Imports fot the Subsystem and its functions
package frc.robot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpiutil.math.MathUtil;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


public class HazyMecBase extends Subsystem{
    
    private TalonSRX rightFrontTalon;
    private TalonSRX leftFrontTalon;
    private TalonSRX leftBackTalon;
    private TalonSRX rightBackTalon;
    private double offset; 
    private boolean delayed;
    private boolean turnDelay;
    private double milStart;
    private double lastData;
    public static HazyMecBase instance;

    //Measurements for Distance Calculation
    double heightOfTarget = 98.0;
    double heightOfCamera = 22.0;
    double angleOfCamera = 13.0;
    
    public HazyMecBase(){
      rightFrontTalon = new TalonSRX(RobotMap.RIGHTFRONTTALONPORT);
      leftBackTalon = new TalonSRX(RobotMap.LEFTBACKTALONPORT);
      leftFrontTalon = new TalonSRX(RobotMap.LEFTFRONTTALONPORT);
      rightBackTalon = new TalonSRX(RobotMap.RIGHTBACKTALONPORT);
      Robot.hazyPort = new SerialPort(RobotMap.BAUDRATE, SerialPort.Port.kMXP);
      
      rightFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
      rightFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      rightFrontTalon.config_kI(0, RobotMap.DRIVEI, 30);
      rightFrontTalon.config_kD(0, RobotMap.DRIVED, 30);
      rightFrontTalon.configNeutralDeadband(0.01);

      rightBackTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
      rightBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      rightBackTalon.config_kI(0, RobotMap.DRIVEI, 30);
      rightBackTalon.config_kD(0, RobotMap.DRIVED, 30);
      rightBackTalon.configNeutralDeadband(0.01);

      leftFrontTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
      leftFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      leftFrontTalon.config_kI(0, RobotMap.DRIVEI, 30);
      leftFrontTalon.config_kD(0, RobotMap.DRIVED, 30);
      leftFrontTalon.configNeutralDeadband(0.01);

      leftBackTalon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
      leftBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      leftBackTalon.config_kI(0, RobotMap.DRIVEI, 30);
      leftBackTalon.config_kD(0, RobotMap.DRIVED, 30);
      leftBackTalon.configNeutralDeadband(0.01);

      Robot.hazyPort.enableTermination();
      delayed=true;
      turnDelay = true;

      Robot.table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    public void initialize(){}

    public static HazyMecBase getInstance(){
      if (instance==null)
          instance = new HazyMecBase();
      return instance;
    }
    double wierdBoi(double in){
      if(Math.abs(in)<20){
        return in;
      }
      if (Math.abs(in)<2000){
        return in*2;
      }
      return in;

    }
    protected void normalize(double[] wheelSpeeds) {
      double maxMagnitude = Math.abs(wheelSpeeds[0]);
      
      for (int i = 1; i < wheelSpeeds.length; i++) {
        double temp = Math.abs(wheelSpeeds[i]);
        if (maxMagnitude < temp) {
          maxMagnitude = temp;
        }
      }

      if (maxMagnitude > 1.0) {
        for (int i = 0; i < wheelSpeeds.length; i++) {
        wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
        }
      }
    }

    private double applyDeadband(double value, double deadband) {
      if (Math.abs(value) > deadband) {
        if (value > 0.0) 
          return (value - deadband) / (1.0 - deadband);
        else 
          return (value + deadband) / (1.0 - deadband);  
      } 
      else 
        return 0.0;
    }

    public void driveCartesian(double x, double y, double angle){
      
        y = MathUtil.clamp(y, -1.0, 1.0);
        y = applyDeadband(y, RobotMap.DEADBAND);
    
        x = MathUtil.clamp(x, -1.0, 1.0);
        x = applyDeadband(x, RobotMap.DEADBAND);
      
        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = x + y + angle;
        wheelSpeeds[1] = -x + y - angle;
        wheelSpeeds[2] = -x + y + angle;
        wheelSpeeds[3] = x + y - angle;
    
        normalize(wheelSpeeds);
    
        leftFrontTalon.set(ControlMode.PercentOutput, -wheelSpeeds[0] );
        rightFrontTalon.set(ControlMode.PercentOutput, -wheelSpeeds[1] * -1);
        leftBackTalon.set(ControlMode.PercentOutput, -wheelSpeeds[2]);
        rightBackTalon.set(ControlMode.PercentOutput, -wheelSpeeds[3]*-1);
    }

    // New Limelight Test Stuff

    public void limeTurnToTarget(){
      Robot.table.getEntry("ledMode").setNumber(3);
      rightFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      rightBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      leftFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      leftBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      if (delayed){
        milStart = java.lang.System.currentTimeMillis();
        delayed = false;
      }
      if(java.lang.System.currentTimeMillis() > milStart + RobotMap.VISIONDELAY){
        double offset = Robot.hazyLimelight.xOffset;
        double turnPower = clamp(RobotMap.VISIONTURN * offset);
        System.out.println("THIS IS OFFSET: " + offset);
        if(turnPower > -0.2 && turnPower < 0.0)
          turnPower = -0.2;
        else if(turnPower < 0.2 && turnPower > 0.0)
          turnPower = 0.2;
        
        if(Math.abs(offset) < 2.0)
          turnPower = 0.0;
        
        driveCartesian(0, 0, -turnPower);
      }
    }

    public void limeGoToTarget(){

      if (delayed){
        milStart = java.lang.System.currentTimeMillis();
        delayed = false;
      }

      if(java.lang.System.currentTimeMillis() > milStart + RobotMap.VISIONDELAY){
        double distance = Robot.hazyLimelight.distanceToTarget;
        double offset = Robot.hazyLimelight.xOffset;
        //System.out.println("THIS IS DISTANCE: " + distance);
        double travelDistance;
        if(distance < 0.0)
          travelDistance = 0.0;
        else
          travelDistance = RobotMap.SHOOTDISTANCE - distance;
        //System.out.println("THIS IS TRAVEL DISTANCE: " + travelDistance);
        //System.out.println(java.lang.System.currentTimeMillis()-lastData);
        double turnPower = clamp(RobotMap.VISIONTURN * offset);
        System.out.println("THIS IS OFFSET: " + offset);
        if(turnPower > -0.13 && turnPower < 0.0 && Math.abs(offset) >= 3.0)
          turnPower = -0.13;
        else if(turnPower < 0.13 && turnPower > 0.0 && Math.abs(offset) >= 3.0)
          turnPower = 0.13;
        
        if(Math.abs(offset) < 3.0)
          turnPower = 0.0;
        
        double forwardPower =clamp(travelDistance*RobotMap.VISIONSPEED);
        //System.out.println("turn: " + turnPower + " forward: " + forwardPower);
        driveCartesian(0, forwardPower, -turnPower);
      }
    }
    //limelight ends here

    
    public void goToTarget(){
      // Robot.solenoidToLight.set(true);

      // if (delayed){
      //   milStart = java.lang.System.currentTimeMillis();
      //   delayed = false;
      // }
      // if(java.lang.System.currentTimeMillis() > milStart + RobotMap.VISIONDELAY){
      //   double travelDistance;
      //   if(distance == -1.0)
      //     travelDistance = 0.0;
      //   else
      //     travelDistance = RobotMap.SHOOTDISTANCE - distance;
      //   //System.out.println(java.lang.System.currentTimeMillis()-lastData);
      //   double turnPower = clamp(RobotMap.VISIONTURN * offset);
      //   if(turnPower > -0.13 && turnPower < 0.0 && Math.abs(offset) >= 3.0)
      //     turnPower = -0.13;
      //   else if(turnPower < 0.13 && turnPower > 0.0 && Math.abs(offset) >= 3.0)
      //     turnPower = 0.13;
        
      //   if(Math.abs(offset) < 3.0)
      //     turnPower = 0.0;
        
      //   double forwardPower =clamp(-travelDistance*RobotMap.VISIONSPEED);
      //   //System.out.println("turn: " + turnPower + " forward: " + forwardPower);
      //   driveCartesian(0, -forwardPower, -turnPower);
      // }
    }

    public void turnToTarget(){
      Robot.solenoidToLight.set(true);
      rightFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      rightBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      leftFrontTalon.config_kP(0, RobotMap.DRIVEP, 30);
      leftBackTalon.config_kP(0, RobotMap.DRIVEP, 30);
      if (delayed){
        milStart = java.lang.System.currentTimeMillis();
        delayed = false;
      }
      if(java.lang.System.currentTimeMillis() > milStart + RobotMap.VISIONDELAY){
        double turnPower = RobotMap.VISIONVELTURN * (offset-RobotMap.RIGHTSIDEOFFSET);
        rightFrontTalon.set(ControlMode.Velocity,turnPower);
        rightBackTalon.set(ControlMode.Velocity,turnPower);
        leftFrontTalon.set(ControlMode.Velocity,turnPower);
        leftBackTalon.set(ControlMode.Velocity,turnPower);
      }
    }

    public void toggleDelayed(){
      delayed = true;
    }

    public void toggleTurnDelay(){
      turnDelay = true;
    }

    // public void readData(){
    //   String data = Robot.hazyPort.readString();
      
    //   //System.out.println(data);
    //   if(data.equals("none")){
    //     offset = 0.0;
    //     distance = -1.0;
    //   }
    //   if(!data.equals("") && !data.equals("none")){
    //     try{
    //     offset = Double.parseDouble(data.substring(8,data.indexOf("distance")));
    //     distance = Double.parseDouble(data.substring(data.indexOf("distance")+10));
    //     if(distance > 2000)
    //       distance = -1;   
        
        
    //     else{
    //       lastData = java.lang.System.currentTimeMillis();
    //     }
    //   }
    //     catch (Exception e){
    //       e.printStackTrace();
    //     }
    //   }
        
    // }

    private double clamp(double input){
      if(input>RobotMap.MAXVISIONSPEED)
        return RobotMap.MAXVISIONSPEED; 
      else if (input < -RobotMap.MAXVISIONSPEED)
        return -RobotMap.MAXVISIONSPEED;
      return input;
    }
    
    @Override
    public void initDefaultCommand(){
        setDefaultCommand(Robot.commandMecanum);
    }
}
