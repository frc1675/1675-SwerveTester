package frc.robot.subsystem.swerve;

/**
 * Represents the state of a swerve module at an instant in time. The time is
 * the time reported by {@code System.currentTimeMillis()}.
 * The module name and CANIDString do not change with time, given the module is
 * the same.
 */
public class SwerveModuleState {
    public final ModuleName name;
    public final String CANIDString;

    public final double steerSpeed;
    public final double driveSpeed;
    public final double CANCoderPosition;
    public final boolean isRunning;

    public final long time;

    public SwerveModuleState(ModuleName name, double steerSpeed, double driveSpeed, double CANCoderPosition,
            boolean isRunning, String CANIDString) {
        this.name = name;
        this.steerSpeed = steerSpeed;
        this.driveSpeed = driveSpeed;
        this.CANCoderPosition = CANCoderPosition;
        this.isRunning = isRunning;
        this.CANIDString = CANIDString;

        this.time = System.currentTimeMillis();
    }
}
