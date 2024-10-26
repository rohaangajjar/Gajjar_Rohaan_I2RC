// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.util.Units;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  private AHRS navx = new AHRS(SPI.Port.kMXP);
  private WPI_TalonSRX leftDriveTalon = new WPI_TalonSRX(Constants.OperatorConstants.LeftDriveTalon); 
  private WPI_TalonSRX rightDriveTalon = new WPI_TalonSRX(Constants.OperatorConstants.RightDriveTalon);

  private ShuffleboardTab DTTab = Shuffleboard.getTab("Drivetrain");
  private GenericEntry leftVoltage = DTTab.add("Left Voltage", 0.0).getEntry();
  private GenericEntry rightVoltage = DTTab.add("Right Voltage", 0.0).getEntry();

  /** Creates a new ExampleSubsystem. */
  public Drivetrain() {
    leftDriveTalon.setNeutralMode(NeutralMode.Coast);
    rightDriveTalon.setNeutralMode(NeutralMode.Coast);
    rightDriveTalon.setInverted(true);

    leftDriveTalon.setSensorPhase(true);
    rightDriveTalon.setSensorPhase(true);

    leftDriveTalon.configFactoryDefault();
    leftDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);

    rightDriveTalon.configFactoryDefault();
    rightDriveTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Left Voltage", leftDriveTalon.getMotorOutputPercent());
    SmartDashboard.putNumber("Right Voltage", rightDriveTalon.getMotorOutputPercent());
    SmartDashboard.putNumber("Angle", navx.getAngle());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /**
   * @param leftSpeed
   * @param rightSpeed
   */
  public void tankDrive(double leftSpeed, double rightSpeed) {
    leftDriveTalon.set(leftSpeed);
    rightDriveTalon.set(rightSpeed);
  }

  public double getAngle() {
    return navx.getAngle();
  }

  public void reset() {
    navx.reset();
  }

  public double getTicks() {
    return (leftDriveTalon.getSelectedSensorPosition(0) + 
      rightDriveTalon.getSelectedSensorPosition(0)) / 2;
  }

  public double getMeters() {
    return (getTicks() / 4096) * 6 * Math.PI;
  }
  public void resetEncoders() {
    leftDriveTalon.setSelectedSensorPosition(0);
    rightDriveTalon.setSelectedSensorPosition(0);
  }
 
}
