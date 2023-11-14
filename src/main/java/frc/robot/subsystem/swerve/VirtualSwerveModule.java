package frc.robot.subsystem.swerve;

import java.util.List;

public class VirtualSwerveModule extends AbstractSwerveModule{

    private List<RealSwerveModule> realModules;

    public VirtualSwerveModule(List<RealSwerveModule> list) {
        realModules = list;
    }

    @Override
    public ModuleName getName() {
        return ModuleName.ALL;
    }

    @Override
    public VirtualSwerveModule setDriveSpeed(double s) {
        for (RealSwerveModule m : realModules) {
            m.setDriveSpeed(s);   
        }
        return this;
    }

    @Override
    public VirtualSwerveModule setSteerSpeed(double s) {
        for (RealSwerveModule m : realModules) {
            m.setSteerSpeed(s);   
        }
        return this;
    }

    /**
     * Return the average CANCoder positions of all modules
     */
    @Override
    public double getCanCoderAbsolutePosition() {
        double rtn = 0;

        for (RealSwerveModule m : realModules) {
            rtn += m.getCanCoderAbsolutePosition();
        }

        return rtn / realModules.size();
    }

    @Override
    public boolean setSteerDesiredAngle(double angleDeg) {
        for (RealSwerveModule m : realModules) {
            m.setSteerDesiredAngle(angleDeg);   
        }
        return false;
    }

    private double getAverageDriveSpeed() {
        double rtn = 0;

        for (RealSwerveModule m : realModules) {
            rtn += m.getState().driveSpeed;
        }

        return rtn / realModules.size();
    }
    
    private double getAverageSteerSpeed() {
        double rtn = 0;

        for (RealSwerveModule m : realModules) {
            rtn += m.getState().steerSpeed;
        }

        return rtn / realModules.size();
    }

    private boolean areAnyRunning() {
        for (RealSwerveModule m : realModules) {
            if(m.getState().isRunning) {
                return true;
            }
        }
        return false;
    }


    @Override
    public SwerveModuleState getState() {
        return new SwerveModuleState(
            ModuleName.ALL,
            getAverageSteerSpeed(), 
            getAverageDriveSpeed(), 
            getCanCoderAbsolutePosition(), 
            areAnyRunning(),
            ModuleName.ALL.name
        );
    }
    
}
