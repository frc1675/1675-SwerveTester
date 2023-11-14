package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.Constants;
import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;

public class SteerTest extends AbstractTest {

  public SteerTest(Swerve swerve, Supplier<ModuleName> name) {
    super(swerve, name);
  }

  @Override
  public void initialize() {
    swerve.steerTest(name, Constants.TEST_SPEED);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
