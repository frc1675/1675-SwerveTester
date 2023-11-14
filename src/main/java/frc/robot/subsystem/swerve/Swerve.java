package frc.robot.subsystem.swerve;

import java.util.HashMap;
import java.util.List;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Swerve extends SubsystemBase{

    private HashMap<ModuleName, AbstractSwerveModule> modules = new HashMap<ModuleName, AbstractSwerveModule>();


    public Swerve(List<RealSwerveModule> moduleList) {
        for (RealSwerveModule m : moduleList) {
            modules.put(m.getName(), m);
        }
        modules.put(ModuleName.ALL, new VirtualSwerveModule(moduleList));
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
