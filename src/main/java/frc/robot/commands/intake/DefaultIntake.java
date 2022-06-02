package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.oi.inputs.OIAxis;
import frc.robot.subsystems.Intake;
import frc.robot.utilities.Functions;

public class DefaultIntake extends CommandBase {
    private final Intake intake;

    OIAxis pivotAxis;

    public DefaultIntake(Intake intake, OIAxis pivotAxis) {
        this.intake = intake;
        this.pivotAxis = pivotAxis;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.setRotateSpeed(0);
    }

    @Override
    public void execute() {
        intake.setPivotSpeed(Functions.clampDouble(pivotAxis.get(), -1, 1));
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
    }
}
