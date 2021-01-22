//Imports fot the Command
package frc.robot;
import edu.wpi.first.wpilibj.command.Command;

public class CommandMecanum extends Command {
    public CommandMecanum(){
        super.requires(Robot.hazyMecBase);
    }

    @Override
    protected void initialize(){
        //Doesn't actually do anything right now, look into whether or not we even need an intialization command
        Robot.hazyMecBase.initialize();
    }

    @Override
    protected void execute(){
        Robot.hazyMecBase.driveCartesian(-1* OI.getLeftX(), OI.getLeftY(), -1 * OI.getRightX());
        Robot.hazyMecBase.readData();
        Robot.solenoidToLight.set(false);
    }

    @Override
    protected boolean isFinished(){
        return false;
    }

    @Override
    protected void interrupted(){
        Robot.hazyMecBase.driveCartesian(0, 0, 0);
    }
}