package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class EncoderDrive extends Command {
    private Drivetrain drivetrain;
    private double setpoint;
    
    public EncoderDrive(Drivetrain drivetrain, double setpoint) {
        this.drivetrain = drivetrain;
        this.setpoint = setpoint;
        addRequirements(drivetrain);
    }

    public void initialize() {
        drivetrain.tankDrive(setpoint, setpoint);
    }

    public void execute() {
        while(!isFinished()) {
            drivetrain.tankDrive(5, 5);
        }
    }
   
    public boolean isFinished() {
        return drivetrain.getMeters() == 5;
    }
    public void end(){
        drivetrain.resetEncoders();
    }
}
