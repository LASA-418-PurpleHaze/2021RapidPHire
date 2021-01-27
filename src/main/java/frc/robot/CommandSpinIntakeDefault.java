//Imports fot the Command
package frc.robot;

import edu.wpi.first.wpilibj.command.Command;

public class CommandSpinIntakeDefault extends Command {
    public CommandSpinIntakeDefault(){
        super.requires(Robot.hazyIntake);
    }

    @Override
    protected void initialize(){
        // Robot.hazyColorSensor.initialize();
    }

    @Override
    protected void execute(){
        Robot.hazyIntake.intakeStopSpin();
    }

    @Override
    protected boolean isFinished(){
        return false;
    }

    @Override
    protected void interrupted(){}
}