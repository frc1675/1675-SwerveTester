package frc.robot.subsystem.swerve;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxRelativeEncoder.Type;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;

public class RealSwerveModule extends AbstractSwerveModule{
    private static final PIDController shuffleboardPID = new PIDController(Constants.SWERVE_P, Constants.SWERVE_I, Constants.SWERVE_D);
    
    private final ModuleName name;

    private final CANCoder cancoder;
    private final CANSparkMax drive;
    private final CANSparkMax steer;

    private final RelativeEncoder driveEncoder;
    private final RelativeEncoder steerEncoder;

    private final SparkMaxPIDController pid;

    private final String toStr;

    public RealSwerveModule(ModuleName name, int coderID, int driveID, int steerID) {
        this.name = name;
        cancoder = new CANCoder(coderID);
        drive = new CANSparkMax(driveID, MotorType.kBrushless);
        steer = new CANSparkMax(steerID, MotorType.kBrushless);

        driveEncoder = drive.getEncoder(Type.kHallSensor, 42);
        steerEncoder = steer.getEncoder(Type.kHallSensor, 42);

        pid = steer.getPIDController();
        pid.setFeedbackDevice(steerEncoder);
        pid.setPositionPIDWrappingEnabled(true);

        toStr = String.format("[D, S, CC] : [%d, %d, %d]", driveID, steerID, coderID);
    }

    public static PIDController getPIDController() {
        return shuffleboardPID;
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
        changeCoefficients();

        steerEncoder.setPosition(cancoder.getAbsolutePosition() / 360.0);
        pid.setReference((angleDeg / 360.0), ControlType.kPosition);

        return withinContinousInputTolerance(angleDeg, cancoder.getAbsolutePosition());
    }

    /**
     * Return whether or not the two parameters' distance from each other with
     * consideration for position wrapping (360 -> 0) is within the tolerance
     * defined in {@code Constants}.
     * 
     * @param a A position in degrees
     * @param b A position in degrees
     * @return {@code true} if the distance between the positions is within
     *         tolerance, {@code false} otherwise.
     */
    private boolean withinContinousInputTolerance(double a, double b) {
        if(Math.abs(a - b) <= Constants.STEER_ANGLE_TOLERANCE) {
            return true;
        }
        if(a > b) {
            a += 360;
        }else {
            b += 360;
        }
        return Math.abs(a - b) <= Constants.STEER_ANGLE_TOLERANCE;

        
    }

    /**
     * Set the PID coefficients from the PID controller which is controlled by the
     * shuffleboard. The shuffleboard PID does not do anything other than hold PID
     * values which are copied to the working PID controller. This choice was made
     * because each swerve module requires its own PID controller, but they should
     * also all have the same coefficients.
     */
    private void changeCoefficients() {
        pid.setP(shuffleboardPID.getP());
        pid.setI(shuffleboardPID.getI());
        pid.setD(shuffleboardPID.getD());
    }

    /**
     * Get the state of the swerve module at this instant in time.
     * @return Module state right now ({@code SwerveModuleState})
     */
    @Override
    public SwerveModuleState getState() {
        return new SwerveModuleState(
            name,
            steerEncoder.getVelocity(),
            driveEncoder.getVelocity(), 
            getCanCoderAbsolutePosition(), 
            (steerEncoder.getVelocity() > Constants.SPEED_TOLERANCE) || (driveEncoder.getVelocity() > Constants.SPEED_TOLERANCE),
            toStr
        );
    }

}
