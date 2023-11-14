package frc.robot.commands;

import java.util.function.Supplier;

import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;

public class RotateNTimes extends AbstractTest{

    private double startAngle;
    private boolean rotateBack = false;

    private int count = 0;
    private final int limit;

    /**
     * Rotate the given module 360 degrees {@code n} times.
     */
    public RotateNTimes(Swerve swerve, Supplier<ModuleName> name, int n) {
        super(swerve, name);
        this.limit = n;

        startAngle = swerve.getModuleState(name.get()).CANCoderPosition;

    }

    @Override
    public void execute() {
        if(!rotateBack) {
            rotateBack = swerve.rotateToAngle(name, startAngle + 185);
          }else {
            if(swerve.rotateToAngle(name, startAngle)) {
                count++;
                rotateBack = !rotateBack;
            }
          }   
    }

    @Override
    public boolean isFinished() {
        return count >= limit;
    }
    
}
