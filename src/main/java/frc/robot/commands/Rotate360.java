package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;

public class Rotate360 extends AbstractTest {

  private double startAngle;
  private boolean rotateBack = false;

  public Rotate360(Swerve swerve, Supplier<ModuleName> name) {
    super(swerve, name);
  }

  @Override
  public void initialize() {
    startAngle = swerve.getModuleState(name).CANCoderPosition;
  }

  @Override
  public void execute() {
    if(!rotateBack) {
      rotateBack = swerve.rotateToAngle(name, startAngle + 185);
    }else {
      if(swerve.rotateToAngle(name, startAngle)) {
        this.end(false);
      }
    }
    
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
