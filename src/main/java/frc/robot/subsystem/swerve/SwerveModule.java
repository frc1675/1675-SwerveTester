package frc.robot.subsystem.swerve;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;

public class SwerveModule {
    
    private final ModuleName name;

    private final CANCoder cancoder;
    private final CANSparkMax drive;
    private final CANSparkMax steer;

    private final PIDController pid;

    private final String toStr;

    public SwerveModule(ModuleName name, int coderID, int driveID, int steerID) {
        this.name = name;
        cancoder = new CANCoder(coderID);
        drive = new CANSparkMax(driveID, MotorType.kBrushless);
        steer = new CANSparkMax(steerID, MotorType.kBrushless);

        pid = new PIDController(Constants.SWERVE_P, Constants.SWERVE_I, Constants.SWERVE_D);

        toStr = String.format("[D, S, CC] : [%d, %d, %d]", driveID, steerID, coderID);
    }

    public ModuleName getName() {
        return name;
    }

    public SwerveModule setDriveSpeed(double s) {
        drive.set(s);
        return this;
    }

    public SwerveModule setSteerSpeed(double s) {
        steer.set(s);
        return this;
    }

    public double getCanCoderAbsolutePosition() {
        return cancoder.getAbsolutePosition();
    }

    /**
     * Set the speed of the steer motor based on the PID controller in order to
     * rotate to the given angle. Method should be called continously until the
     * angle is reached.
     * 
     * @param angleDeg The desired angle in degrees.
     * @return true if the module is within the tolerance, false otherwise
     */
    public boolean setSteerDesiredAngle(double angleDeg) {
        double angleDif = angleDeg - cancoder.getAbsolutePosition();

        if(angleDif >= Constants.STEER_ANGLE_TOLERANCE){
            steer.set(pid.calculate(angleDif));
        }else {
            steer.set(0);
            return true;
        }
        return false;
    }

    /**
     * Get the state of the swerve module at this instant in time.
     * @return Module state right now ({@code SwerveModuleState})
     */
    public SwerveModuleState getState() {
        return new SwerveModuleState(
            name,
            steer.get(), 
            drive.get(), 
            getCanCoderAbsolutePosition(), 
            (steer.get() != 0) || (drive.get() != 0),
            toStr
        );
    }

    /**
     * Represents the state of a swerve module at an instant in time. The time is the time reported by {@code System.currentTimeMillis()}.
     * The module name and CANIDString do not change with time, given the module is the same.
     */
    public class SwerveModuleState {
        public final ModuleName name;
        public final String CANIDString;

        public final double steerSpeed;
        public final double driveSpeed;
        public final double CANCoderPosition;
        public final boolean isRunning;
        
        public final long time;

        private SwerveModuleState(ModuleName name, double steerSpeed, double driveSpeed, double CANCoderPosition, boolean isRunning, String CANIDString) {
            this.name = name;
            this.steerSpeed = steerSpeed;
            this.driveSpeed = driveSpeed;
            this.CANCoderPosition = CANCoderPosition;
            this.isRunning = isRunning;
            this.CANIDString = CANIDString;

            this.time = System.currentTimeMillis();
        }
    }

}
