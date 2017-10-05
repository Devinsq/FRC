package org.usfirst.frc.team6375.robot;

import edu.wpi.first.wpilibj.MotorSafetyHelper;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tInstances;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;

public class chassis extends Robot {
	public static final double kDefaultExpirationTime = 0.1;
	public static final double kDefaultSensitivity = 0.5;
	public static final double kDefaultMaxOutput = 1.0;
	protected static final int kMaxNumberOfMotors = 4;
	protected double m_sensitivity;
	protected double m_maxOutput;
	protected SpeedController m_LEFTMOTOR1;
	protected SpeedController m_LEFTMOTOR2;
	protected SpeedController m_LEFTMOTOR3;
	protected SpeedController m_RIGHTMOTOR1;
	protected SpeedController m_RIGHTMOTOR2;
	protected SpeedController m_RIGHTMOTOR3;
	protected boolean m_allocatedSpeedControllers;
	protected static boolean kArcadeRatioCurve_Reported = false;
	protected static boolean kTank_Reported = false;
	protected static boolean kArcadeStandard_Reported = false;
	protected static boolean kMecanumCartesian_Reported = false;
	protected static boolean kMecanumPolar_Reported = false;
	protected MotorSafetyHelper m_safetyHelper;
	
	public void chassis(double moveValue,double rotateValue,boolean squaredInputs){
    	if (!kArcadeStandard_Reported) {
    	      HAL.report(tResourceType.kResourceType_RobotDrive, getNumMotors(),
    	          tInstances.kRobotDrive_ArcadeStandard);
    	      kArcadeStandard_Reported = true;
    	    }
    	
    	double LEFTMOTORSP;
    	double RIGHTMOTORSP;
    	
    	moveValue = limit(moveValue);
        rotateValue = limit(rotateValue);

        if (squaredInputs) {
          // square the inputs (while preserving the sign) to increase fine control
          // while permitting full power
          if (moveValue >= 0.0) {
            moveValue = moveValue * moveValue;
          } else {
            moveValue = -(moveValue * moveValue);
          }
          if (rotateValue >= 0.0) {
            rotateValue = rotateValue * rotateValue;
          } else {
            rotateValue = -(rotateValue * rotateValue);
          }
        }

        if (moveValue > 0.0) {
          if (rotateValue > 0.0) {
        	LEFTMOTORSP = moveValue - rotateValue;
        	RIGHTMOTORSP = Math.max(moveValue, rotateValue);
          } else {
        	LEFTMOTORSP = Math.max(moveValue, -rotateValue);
            RIGHTMOTORSP = moveValue + rotateValue;
          }
        } else {
          if (rotateValue > 0.0) {
        	LEFTMOTORSP = -Math.max(-moveValue, rotateValue);
            RIGHTMOTORSP = moveValue + rotateValue;
          } else {
        	LEFTMOTORSP = moveValue - rotateValue;
            RIGHTMOTORSP = -Math.max(-moveValue, -rotateValue);
          }
        }
        setLeftRightMotorOutputs(LEFTMOTORSP, -RIGHTMOTORSP);
    }
    
    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        if (m_LEFTMOTOR1 == null || m_RIGHTMOTOR1 == null) {
          throw new NullPointerException("Null motor provided");
        }

        if (m_LEFTMOTOR1 != null) {
        	m_LEFTMOTOR1.set(limit(leftOutput) * m_maxOutput);
        	m_LEFTMOTOR2.set(limit(leftOutput) * m_maxOutput);
            m_LEFTMOTOR3.set(limit(leftOutput) * m_maxOutput);
        }
        

        if (m_RIGHTMOTOR1 != null) {
        	m_RIGHTMOTOR1.set(-limit(rightOutput) * m_maxOutput);
        	m_RIGHTMOTOR2.set(-limit(rightOutput) * m_maxOutput);
            m_RIGHTMOTOR3.set(-limit(rightOutput) * m_maxOutput);
        }

        if (m_safetyHelper != null) {
          m_safetyHelper.feed();
        }
      }

      /**
       * Limit motor values to the -1.0 to +1.0 range.
       */
      protected static double limit(double num) {
        if (num > 1.0) {
          return 1.0;
        }
        if (num < -1.0) {
          return -1.0;
        }
        return num;
      }
    
      protected int getNumMotors() {
    	    int motors = 0;
    	    if (m_LEFTMOTOR1 != null) {
    	      motors++;
    	    }
    	    if (m_LEFTMOTOR2 != null) {
    	      motors++;
    	    }
    	    if (m_LEFTMOTOR3 != null) {
      	      motors++;
      	    }
    	    if (m_RIGHTMOTOR1 != null) {
    	      motors++;
    	    }
    	    if (m_RIGHTMOTOR2 != null) {
      	      motors++;
      	    }
    	    if (m_RIGHTMOTOR3 != null) {
      	      motors++;
      	    }
    	    return motors;
    	  }
    

}
