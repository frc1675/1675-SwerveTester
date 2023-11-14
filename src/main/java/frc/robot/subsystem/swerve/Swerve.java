package frc.robot.subsystem.swerve;

import java.util.HashMap;
import java.util.List;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystem.swerve.SwerveModule.SwerveModuleState;

public class Swerve extends SubsystemBase{

    private HashMap<ModuleName, SwerveModule> modules = new HashMap<ModuleName, SwerveModule>();


    public Swerve(List<SwerveModule> moduleList) {
        for (SwerveModule m : moduleList) {
            modules.put(m.getName(), m);
        }
    }

    public SwerveModuleState getModuleState(ModuleName module) {
        return modules.get(module).getState();
    }

    public void driveTest(ModuleName module, double speed) {
        modules.get(module).setDriveSpeed(speed);
    }

    public void steerTest(ModuleName module, double speed) {
        modules.get(module).setSteerSpeed(speed);
    }

    public boolean rotateToAngle(ModuleName module, double angleDeg) {
        return modules.get(module).setSteerDesiredAngle(angleDeg);
    }

    public void stop(ModuleName module) {
        modules.get(module).setDriveSpeed(0).setSteerSpeed(0);
    }
}
