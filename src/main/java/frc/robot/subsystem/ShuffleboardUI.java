package frc.robot.subsystem;

import java.util.function.Supplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.simulation.DriverStationSim;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.commands.AbstractTest;
import frc.robot.commands.DriveTest;
import frc.robot.commands.Rotate360;
import frc.robot.commands.RotateNTimes;
import frc.robot.commands.SteerTest;
import frc.robot.subsystem.swerve.ModuleName;
import frc.robot.subsystem.swerve.Swerve;
import frc.robot.subsystem.swerve.SwerveModuleState;

public class ShuffleboardUI extends SubsystemBase{
    private AbstractTest cmd;
    private boolean cmdRunningPrev = false;
    private GenericEntry runBtn;
    private String runningName = "None";
    
    private SendableChooser<ModuleName> moduleChooser = new SendableChooser<ModuleName>();
    private SendableChooser<Supplier<AbstractTest>> testChooser = new SendableChooser<Supplier<AbstractTest>>();

    private SwerveModuleState currState;
    private Swerve swerve;

    public ShuffleboardUI(Swerve swerve) {
        this.swerve = swerve;
        ShuffleboardTab tab = Shuffleboard.getTab("Swerve Tester");

        //Module chooser
        {
            for (ModuleName m : ModuleName.values()) {
                moduleChooser.addOption(m.name, m);
            }
            moduleChooser.setDefaultOption(ModuleName.FRONT_RIGHT.name, ModuleName.FRONT_RIGHT);
            tab.add("Modules", moduleChooser).withPosition(0, 0).withSize(2, 1);
        }

        //Test chooser
        {
            tab.add("Test Routines", testChooser).withPosition(2, 0).withSize(2, 1);
            testChooser.setDefaultOption("Drive motor", () -> new DriveTest(swerve, () -> moduleChooser.getSelected()));
            testChooser.addOption("Steer motor", () -> new SteerTest(swerve, () -> moduleChooser.getSelected()));
            testChooser.addOption("Rotate 360 degrees", () -> new Rotate360(swerve, () -> moduleChooser.getSelected()));
            testChooser.addOption("Rotate 3 times", () -> new RotateNTimes(swerve, () -> moduleChooser.getSelected(), 3));
            
        }

        //Command information
        {
            ShuffleboardLayout cmdInfo = tab.getLayout("Command Info", BuiltInLayouts.kList).withPosition(0, 1).withSize(2, 2);
            runBtn = cmdInfo.add("Run", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
            cmdInfo.addString("Running Command", () -> runningName);
            cmdInfo.addBoolean("Command Running?", () -> !runningName.equals("None"));

            cmd = new DriveTest(swerve, () -> moduleChooser.getSelected());
        }

        //Selected module
        {    
            ShuffleboardLayout selectedModule = tab.getLayout("Selected Module", BuiltInLayouts.kList).withPosition(2, 1).withSize(2, 2);
            selectedModule.addString("Current Module", () -> moduleChooser.getSelected().name);
            selectedModule.addString("CAN IDs", () -> currState.CANIDString);
            selectedModule.addBoolean("Moving?", () -> currState.isRunning);
        }
        
        //Module state
        {
            ShuffleboardLayout moduleState = tab.getLayout("Module State", BuiltInLayouts.kList).withPosition(4, 0).withSize(5, 5);
            moduleState.addDouble("Drive Speed", () -> 100 * currState.driveSpeed).withWidget(BuiltInWidgets.kDial);
            moduleState.addDouble("Steer Speed", () -> currState.steerSpeed).withWidget(BuiltInWidgets.kGraph);
            moduleState.addDouble("CANCoder Position (Degrees)", () -> currState.CANCoderPosition).withWidget(BuiltInWidgets.kGraph);
        }
        
    }

    @Override
    public void periodic() {
        
        boolean isTeleop = Robot.isSimulation() ? DriverStationSim.getEnabled() : DriverStation.isTeleop();

        if(isTeleop) {
            //Command was unscheduled between last run and now
            if(!cmd.isScheduled() && cmdRunningPrev && runBtn.get().getBoolean()) {
                runBtn.setBoolean(false);
                runningName = "None";
            }
            //Button was set to false between last run and now
            else if(cmd.isScheduled() && cmdRunningPrev && !runBtn.get().getBoolean()) {
                cmd.cancel();
                runningName = "None";
            }
            //Button was set to true between last run and now
            else if(!cmd.isScheduled() && !cmdRunningPrev && runBtn.get().getBoolean()) {
                cmd = testChooser.getSelected().get();
                cmd.schedule();

                runningName = cmd.getName() + " on " + cmd.getModuleName().name;
            }

            cmdRunningPrev = cmd.isScheduled();
        }else {
            runBtn.setBoolean(false);
        }

        currState = swerve.getModuleState(moduleChooser.getSelected());   
    }

}
