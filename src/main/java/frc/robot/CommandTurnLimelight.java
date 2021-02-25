//Imports fot the Command
package frc.robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandTurnLimelight extends Command {
    public CommandTurnLimelight(){
        super.requires(Robot.hazyMecBase);
    }
    
    @Override
    protected void initialize(){}

    @Override
    protected void execute(){
        Robot.hazyMecBase.limeTurnToTarget();
        Robot.table.getEntry("ledMode").setNumber(3);
    }

    @Override
    protected boolean isFinished(){
        return true;
    }

    @Override
    protected void interrupted(){}
}