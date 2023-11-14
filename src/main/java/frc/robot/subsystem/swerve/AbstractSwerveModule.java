package frc.robot.subsystem.swerve;

public abstract class AbstractSwerveModule {
    
    public abstract ModuleName getName();

    public abstract AbstractSwerveModule setDriveSpeed(double s);

    public abstract AbstractSwerveModule setSteerSpeed(double s);

    public abstract double getCanCoderAbsolutePosition();

    /**
     * Set the speed of the steer motor based on the PID controller in order to
     * rotate to the given angle. Method should be called continously until the
     * angle is reached.
     * 
     * @param angleDeg The desired angle in degrees.
     * @return true if the module is within the tolerance, false otherwise
     */
    public abstract boolean setSteerDesiredAngle(double angleDeg);

    /**
     * Get the state of the swerve module at this instant in time.
     * @return Module state right now ({@code SwerveModuleState})
     */
    public abstract SwerveModuleState getState();

}
