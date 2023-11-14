// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.BackLeft;
import frc.robot.Constants.BackRight;
import frc.robot.Constants.FrontLeft;
import frc.robot.Constants.FrontRight;
import frc.robot.subsystem.ShuffleboardUI;
import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;
import frc.robot.subsystem.swerve.SwerveModule;

public class RobotContainer {
  public RobotContainer() {
    new ShuffleboardUI(
      new Swerve(List.of(
        new SwerveModule(ModuleName.FRONT_RIGHT, FrontRight.CANCODER_ID, FrontRight.DRIVE_MOTOR_ID, FrontRight.STEER_MOTOR_ID),
        new SwerveModule(ModuleName.FRONT_LEFT, FrontLeft.CANCODER_ID, FrontLeft.DRIVE_MOTOR_ID, FrontLeft.STEER_MOTOR_ID),
        new SwerveModule(ModuleName.BACK_RIGHT, BackRight.CANCODER_ID, BackRight.DRIVE_MOTOR_ID, BackRight.STEER_MOTOR_ID),
        new SwerveModule(ModuleName.BACK_LEFT, BackLeft.CANCODER_ID, BackLeft.DRIVE_MOTOR_ID, BackLeft.STEER_MOTOR_ID)
      ))
    );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command available.");
  }
}
