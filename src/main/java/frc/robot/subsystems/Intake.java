package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.devices.PDP;
import frc.robot.utilities.lists.Ports;

public class Intake extends SubsystemBase {

    public enum IntakeState {
        UP,
        DOWN,
    }

    public enum LockState {
        LOCKED,
        UNLOCKED,
    }

    private IntakeState intakeState;
    private LockState lockState;
    
    // Motors
    private final VictorSPX rotateMotor = new VictorSPX(Ports.INTAKE_ARM_SPIN);
    private final VictorSPX pivotMotor = new VictorSPX(Ports.INTAKE_ARM_PIVOT);

    // Solenoid
    private final Solenoid lock =
        new Solenoid(Ports.PCM_1, PneumaticsModuleType.CTREPCM, Ports.INTAKE_LOCK);

    // PDP
    private PDP pdp;

    public Intake(PDP pdp) {
        this.pdp = pdp;
    }

    public void setRotateSpeed(double speed) {
        rotateMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setPivotSpeed(double speed) {
        pivotMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getRotateCurrent() {
        return pdp.getCurrent(9);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Intake");
        builder.addDoubleProperty("rotate_motor_current", this::getRotateCurrent, null);
    }
}
