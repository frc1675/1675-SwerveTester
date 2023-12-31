package frc.robot.subsystem.swerve;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;

public class RealSwerveModule extends AbstractSwerveModule{
    
    private final ModuleName name;

    private final CANCoder cancoder;
    private final CANSparkMax drive;
    private final CANSparkMax steer;

    private static final PIDController pid = new PIDController(Constants.SWERVE_P, Constants.SWERVE_I, Constants.SWERVE_D);;

    private final String toStr;

    public RealSwerveModule(ModuleName name, int coderID, int driveID, int steerID) {
        this.name = name;
        cancoder = new CANCoder(coderID);
        drive = new CANSparkMax(driveID, MotorType.kBrushless);
        steer = new CANSparkMax(steerID, MotorType.kBrushless);

        toStr = String.format("[D, S, CC] : [%d, %d, %d]", driveID, steerID, coderID);
    }

    public static PIDController getPIDController() {
        return pid;
    }

    @Override
    public ModuleName getName() {
        return name;
    }

    @Override
    public RealSwerveModule setDriveSpeed(double s) {
        drive.set(s);
        return this;
    }

    @Override
    public RealSwerveModule setSteerSpeed(double s) {
        steer.set(s);
        return this;
    }

    @Override
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
    @Override
    public boolean setSteerDesiredAngle(double angleDeg) {
        angleDeg = normalizeInput(angleDeg);
        double angleDif = angleDeg - cancoder.getAbsolutePosition();

        if(angleDif >= Constants.STEER_ANGLE_TOLERANCE){
            steer.set(pid.calculate(angleDif));
        }else {
            steer.set(0);
            return true;
        }
        return false;
    }

    private double normalizeInput(double degrees) {
        if(degrees > 360) {
            return normalizeInput(degrees - 360);
        }
        if(degrees < 0) {
            return normalizeInput(degrees + 360);
        }
        return degrees;
    }

    /**
     * Get the state of the swerve module at this instant in time.
     * @return Module state right now ({@code SwerveModuleState})
     */
    @Override
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

}
