package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;

public class Rotate360 extends AbstractTest {

  private double startAngle;
  private int step = 1;

  public Rotate360(Swerve swerve, Supplier<ModuleName> name) {
    super(swerve, name);
  }

  @Override
  public void initialize() {
    startAngle = swerve.getModuleState(name).CANCoderPosition;
  }

  @Override
  public void execute() {
    if(swerve.rotateToAngle(name, startAngle + 45 * step)) {
      step++;
    }
  }

  @Override
  public boolean isFinished() {
    return step == 360 / 45;
  }
}
