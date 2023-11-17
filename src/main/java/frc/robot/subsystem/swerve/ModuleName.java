package frc.robot.subsystem.swerve;

public enum ModuleName {
    MODULE_ONE("Module One"),
    MODULE_TWO("Module Two"),
    MODULE_THREE("Module Three"),
    MODULE_FOUR("Module Four"),
    ALL("All Modules");

    public final String name;
    private ModuleName(String v) {
        name = v;
    }
}
