package frc.robot.subsystem.swerve;

public enum ModuleName {
    FRONT_RIGHT("Front Right"),
    FRONT_LEFT("Front Left"),
    BACK_RIGHT("Back Right"),
    BACK_LEFT("Back Left"),
    ALL("All Modules");

    public final String name;
    private ModuleName(String v) {
        name = v;
    }
}
