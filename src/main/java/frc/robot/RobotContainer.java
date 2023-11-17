// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Constants.ModuleFour;
import frc.robot.Constants.ModuleThree;
import frc.robot.Constants.ModuleTwo;
import frc.robot.Constants.ModuleOne;
import frc.robot.subsystem.ShuffleboardUI;
import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;
import frc.robot.subsystem.swerve.RealSwerveModule;

public class RobotContainer {
  public RobotContainer() {
    new ShuffleboardUI(
      new Swerve(List.of(
        new RealSwerveModule(ModuleName.MODULE_ONE, ModuleOne.CANCODER_ID, ModuleOne.DRIVE_MOTOR_ID, ModuleOne.STEER_MOTOR_ID),
        new RealSwerveModule(ModuleName.MODULE_TWO, ModuleTwo.CANCODER_ID, ModuleTwo.DRIVE_MOTOR_ID, ModuleTwo.STEER_MOTOR_ID),
        new RealSwerveModule(ModuleName.MODULE_THREE, ModuleThree.CANCODER_ID, ModuleThree.DRIVE_MOTOR_ID, ModuleThree.STEER_MOTOR_ID),
        new RealSwerveModule(ModuleName.MODULE_FOUR, ModuleFour.CANCODER_ID, ModuleFour.DRIVE_MOTOR_ID, ModuleFour.STEER_MOTOR_ID)
      ))
    );
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command available.");
  }
}
