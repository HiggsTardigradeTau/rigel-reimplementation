package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utilities.lists.Ports;

public class Shifter extends SubsystemBase {

    private DoubleSolenoid shift;
    private boolean oldShift;

    public Shifter() {
        shift = new DoubleSolenoid(Ports.PCM_1, PneumaticsModuleType.CTREPCM, Ports.SHIFT_SOLENOID_UP, Ports.SHIFT_SOLENOID_DOWN);
    }

    public void highGear() {
        oldShift = true;
        shift.set(Value.kForward);
    }

    public void lowGear() {
        oldShift = false;
        shift.set(Value.kReverse);
    }

    /**
     * Getes the shift state
     * 
     * @return the shift state where true is high and false is low
     */
    public boolean getShiftState(){
        return oldShift;
    }
}