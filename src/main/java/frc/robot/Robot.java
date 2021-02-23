/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//Imports for the Robot
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.*;
import edu.wpi.first.networktables.NetworkTable;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //Variables for the Mecanum Drive
  public static HazyMecBase hazyMecBase; 
  public static CommandMecanum commandMecanum;
  public static CommandPreciseMecanum commandPreciseMecanum;
  public static CommandToggleDelay commandToggleDelay;
  public static CommandTurnVision commandTurnVision;
  public static CommandToggleTurnDelay commandToggleTurnDelay;

  //Variables for the Color Sensor and Arm
  public static HazyColorSensor hazyColorSensor;
  public static HazyColorArm hazyColorArm;
  // public static CommandColor commandColor;
  public static CommandFoldUp commandFoldUp;
  public static CommandFoldDown commandFoldDown;
  public static CommandSpinWheel commandSpinWheel;
  public static CommandColorArmDefault commandColorArmDefault;
  public static CommandToggleColorArm commandToggleColorArm;

  //Variables for the Intake
  public static HazyIntake hazyIntake;
  public static CommandMoveIntakeDefault commandMoveIntakeDefault;
  public static CommandSpitIntake commandSpitIntake;
  public static CommandSwallowIntake commandSwallowIntake;
  public static CommandSwitchIntakeDir commandSwitchIntakeDir;
  public static CommandStopSpinning commandStopSpinning;
  //public static CommandSpinIntakeDefault commandSpinIntakeDefault;

   //Variables for Low Ball Feeder
   public static HazyLowFeeder hazyLowFeeder;
   public static CommandSwallowLowFeed commandSwallowLowFeed;
   public static CommandSpitLowFeed commandSpitLowFeed;
   public static CommandLowFeedDefault commandLowFeedDefault;

  //Variables for High Ball Feeder
  public static HazyHighFeeder hazyHighFeeder;
  public static CommandSwallowHighFeed commandSwallowHighFeed;

  public static CommandSpitHighFeed commandSpitHighFeed;
  public static CommandHighFeedDefault commandHighFeedDefault;
 
  //Variables for the End Arm
  public static HazyEndArm hazyEndArm;
  public static CommandEndArmUp commandEndArmUp;
  public static CommandEndArmDown commandEndArmDown;
  public static CommandEndArmDefault commandEndArmDefault;

  //Variables for the Shooter
  public static HazyShooter hazyShooter;
  public static CommandShooterSpit commandShooterSpit;
  public static CommandShooterSwallow commandShooterSwallow;
  public static CommandShooterDefault commandShooterDefault;

  //Serial Port 
  public static SerialPort hazyPort;

  //Vision Light
  public static Solenoid solenoidToLight;
  //Limelight Setup
  public static HazyLimelight hazyLimelight;
  public static NetworkTable table; 
  public static CommandLimelight commandLimelight;
  public static CommandFollowLimelight commandFollowLimelight;
  

  

  //Autonomous
  public static HazyAuton hazyAuton;
  public static CommandFollowVision commandFollowVision;
  public static CommandAutonZero commandAutonZero;
  public static CommandAutonOne commandAutonOne;
  //public static CommandAutonTwo commandAutonTwo;
  public static CommandAutonThree commandAutonThree;
  public static CommandGoTrench commandGoTrench;
  public static CommandAuton180 commandAuton180;
  public static CommandToggleTurn commandToggleTurn;
  public static Timer hazyTime;
  
  //OI
  OI hazyOI; //OI object for all the buttons and their resulting commands


  @Override
  public void robotInit() {
    //Initialization Code for the Mechanum Drive
    hazyMecBase = new HazyMecBase();
    commandMecanum = new CommandMecanum();
    commandPreciseMecanum = new CommandPreciseMecanum();
    commandToggleDelay = new CommandToggleDelay();
    commandTurnVision = new CommandTurnVision();
    commandToggleTurnDelay = new CommandToggleTurnDelay();

    //Limelight
    hazyLimelight = new HazyLimelight();
    commandLimelight = new CommandLimelight();
    commandFollowLimelight = new CommandFollowLimelight();

    //Initialization Code for the Color Sensor and Arm
    hazyColorSensor = new HazyColorSensor();
    hazyColorArm = new HazyColorArm();

    //commandColor = new CommandColor();
    commandFoldUp = new CommandFoldUp();
    commandFoldDown = new CommandFoldDown();
    commandSpinWheel = new CommandSpinWheel();
    commandColorArmDefault = new CommandColorArmDefault();
    commandToggleColorArm = new CommandToggleColorArm();
    
    //Initialization Code for the Intake
    hazyIntake = new HazyIntake();
    commandMoveIntakeDefault = new CommandMoveIntakeDefault();
    commandSpitIntake = new CommandSpitIntake();
    commandSwallowIntake = new CommandSwallowIntake();
    commandSwitchIntakeDir = new CommandSwitchIntakeDir();
    commandStopSpinning = new CommandStopSpinning();
   
    //Initialization Code for End Arm
    hazyEndArm = new HazyEndArm();
    commandEndArmUp = new CommandEndArmUp();
    commandEndArmDown = new CommandEndArmDown();
    commandEndArmDefault = new CommandEndArmDefault();

    //Initialization Code for Low Ball Feeder
    hazyLowFeeder = new HazyLowFeeder();
    commandSwallowLowFeed = new CommandSwallowLowFeed();
    commandSpitLowFeed = new CommandSpitLowFeed();
    commandLowFeedDefault = new CommandLowFeedDefault();

    //Initialization Code for High Ball Feeder
    hazyHighFeeder = new HazyHighFeeder();
    commandSwallowHighFeed = new CommandSwallowHighFeed();
    commandSpitHighFeed = new CommandSpitHighFeed();
    commandHighFeedDefault = new CommandHighFeedDefault();

    //Initialization Code for Shooter
    hazyShooter = new HazyShooter();
    commandShooterSpit = new CommandShooterSpit();
    commandShooterSwallow = new CommandShooterSwallow();
    commandShooterDefault =  new CommandShooterDefault();
 
    //Initialization Code for Autonomous
    hazyAuton = new HazyAuton();
    solenoidToLight = new Solenoid(0);
    hazyTime = new Timer();
    commandFollowVision = new CommandFollowVision();
    commandAutonZero = new CommandAutonZero();
    commandAutonOne = new CommandAutonOne();
    //commandAutonTwo = new CommandAutonTwo();
    commandAutonThree = new CommandAutonThree();
    commandGoTrench = new CommandGoTrench();
    commandToggleTurn = new CommandToggleTurn();
    commandAuton180 = new CommandAuton180();
    CameraServer.getInstance().startAutomaticCapture();  }

  @Override
  public void autonomousInit() {
    hazyAuton.resetEncoders();
    //commandSwitchIntakeDir.execute();
    //commandAuton180.execute();
    // //commandAutonZero.execute();
    //commandAutonOne.execute();
    // //commandAutonTwo.execute();
    commandAutonThree.execute();
    //commandGoTrench.execute();
    //hazyAuton.strafeLeft(2);
    
  }

  @Override
  public void autonomousPeriodic() {
    //commandMoveIntakeDefault.execute();
  }

  @Override
  public void teleopInit() {
    Scheduler.getInstance().removeAll();
    Scheduler.getInstance().add(commandMecanum);
    Scheduler.getInstance().add(commandColorArmDefault);
    Scheduler.getInstance().add(commandMoveIntakeDefault);
    Scheduler.getInstance().add(commandEndArmDefault);
    Scheduler.getInstance().add(commandLowFeedDefault);
    Scheduler.getInstance().add(commandHighFeedDefault);
    //Scheduler.getInstance().add(commandLimelight);
    hazyOI = new OI(); //OI object for all the buttons and their resulting commands
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    hazyOI.runMethods();   
    Robot.hazyShooter.UpdateRPM();
  }

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
