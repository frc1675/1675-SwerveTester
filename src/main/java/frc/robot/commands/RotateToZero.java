package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;

public class RotateToZero extends AbstractTest{

    public RotateToZero(Swerve swerve, Supplier<ModuleName> name) {
        super(swerve, name);
    }

    @Override
    public void execute() {
        if(swerve.rotateToAngle(name, 0)) {
            this.end(false);
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
    
}
