package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;

public abstract class AbstractTest extends CommandBase{

    protected final Swerve swerve;
    protected final ModuleName name;

    protected AbstractTest(Swerve swerve, Supplier<ModuleName> name) {
        this.swerve = swerve;
        this.name = name.get();

        addRequirements(swerve);
    }

    public ModuleName getModuleName() {
        return name;
    }

    @Override
    public final void end(boolean interrupted) {
        swerve.stop(name);
    }

}
