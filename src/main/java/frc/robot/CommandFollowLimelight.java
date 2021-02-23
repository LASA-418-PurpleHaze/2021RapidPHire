//Imports fot the Command
package frc.robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandFollowLimelight extends Command {
    public CommandFollowLimelight(){
        super.requires(Robot.hazyMecBase);
    }
    
    @Override
    protected void initialize(){
        // Robot.hazyEndArm.initialize();
    }

    @Override
    protected void execute(){
        //Robot.hazyMecBase.readData();
        //Robot.hazyMecBase.getLimelightData();
        Robot.hazyMecBase.calculateDistance();
        Robot.hazyMecBase.limeGoToTarget();

        //System.out.println("Pressed End Arm Down");
    }

    @Override
    protected boolean isFinished(){
        return true;
    }

    @Override
    protected void interrupted(){}
}