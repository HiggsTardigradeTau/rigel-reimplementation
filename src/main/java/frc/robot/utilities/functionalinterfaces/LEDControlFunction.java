/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.utilities.functionalinterfaces;

/**
 * Interface for LED Stuff.
 */
public interface LEDControlFunction {
    int[] getLedColor(int loop, int led);
}
