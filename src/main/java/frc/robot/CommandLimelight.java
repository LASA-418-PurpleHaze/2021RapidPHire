//Imports fot the Command
package frc.robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandLimelight extends Command {
    public CommandLimelight(){
        super.requires(Robot.hazyLimelight);
    }
    @Override
    protected void initialize(){
        // Robot.hazyColorSensor.initialize();
    }

    @Override
    protected void execute(){
        Robot.hazyLimelight.setLimelightX();
        Robot.hazyLimelight.setLimelightY();
        Robot.hazyLimelight.calculateDistance();
        Robot.hazyLimelight.printData();
    }

    @Override
    protected boolean isFinished(){
        return false;
    }

    @Override
    protected void interrupted(){}
}