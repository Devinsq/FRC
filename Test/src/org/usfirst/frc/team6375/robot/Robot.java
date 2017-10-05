package org.usfirst.frc.team6375.robot;

import com.ctre.CANTalon;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	/* talons for arcade drive */
	CANTalon _frontLeftMotor = new CANTalon(3); 		/* device IDs here (1 of 2) */
	CANTalon _rearLeftMotor = new CANTalon(4);
	CANTalon _frontRightMotor = new CANTalon(1);
	CANTalon _rearRightMotor = new CANTalon(2);
	CANTalon _ShotBall = new CANTalon(5);
	CANTalon _Climber = new CANTalon(6);
	CANTalon _Belt0 = new CANTalon(7);
	CANTalon _spin = new CANTalon(8);
	Servo LeftGear = new Servo(0);
	Servo RightGear = new Servo(1);
	Servo GetBall = new Servo(2);
	public ADXRS450_Gyro g1 = new ADXRS450_Gyro();
	public Encoder Left_Encoder = new Encoder(0,1, false, Encoder.EncodingType.k4X);
	public Encoder Right_Encoder = new Encoder(2,3, true, Encoder.EncodingType.k4X);
	RobotDrive _drive = new RobotDrive(_rearLeftMotor,_frontLeftMotor,_frontRightMotor,_rearRightMotor);
	Joystick _joy = new Joystick(0);
	Joystick _joy1 = new Joystick(1);
	Timer timer = new Timer();
	public int a=1;

	// gyro calibration constant, may need to be adjusted;
	// gyro value of 360 is set to correspond to one full revolution
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     * @return 
     */

    public void robotInit() {
    	CameraServer server1 = CameraServer.getInstance();
    	//CameraServer server2 = CameraServer.getInstance();
    	UsbCamera camera1 = new UsbCamera("cam0",0);
        camera1.setResolution(640,480);
        camera1.setFPS(30);
        
        server1.startAutomaticCapture(camera1);      
        g1.calibrate();
    }
    

    
    public void autonomousInit() {
    	timer.reset();
		timer.start();
		g1.reset();
		Right_Encoder.reset();
		Right_Encoder.setDistancePerPulse(5);
		Right_Encoder.getDistance();
    	}
    public void autonomousPeriodic(){  
    	Right_Encoder.reset();
    	boolean running=true;//Robot run
    	
    	if(running==true){
    		//System.out.println("qqqq");
    		//Red, Near Boiler
	    	switch(a){
	    	/*case 1:		
	    		Timer.delay(0.3);
	    		LeftGear.setAngle(60);
	    		Timer.delay(0.2);
	    		RightGear.setAngle(120);	
	    		double TargetPosition = 1065.4;
	    		while(Math.abs(Right_Encoder.get()) < Math.abs(TargetPosition) && isAutonomous()){
	    			_drive.arcadeDrive(Math.copySign(0.5, -TargetPosition), 0);
	    		}
	    		_drive.stopMotor();      	
	    		a++;
	    		break;
	    	case 2:
	    		Timer.delay(0.3);
	    		Right_Encoder.reset();
	    		g1.reset();
	    		double TargetAngel1=-43;
	    		while(Math.abs(g1.getAngle()) < Math.abs(TargetAngel1) && isAutonomous()){
	    			_drive.setLeftRightMotorOutputs( Math.copySign(0.5,TargetAngel1),0);
	    		}
	    		_drive.stopMotor();	    
	    		a++;
	    		break;
	    	case 3:
	    		Timer.delay(0.3);
	    		Right_Encoder.reset();
	    		double TargetPosition1=840;
	    		while(Math.abs(Right_Encoder.get()) < Math.abs(TargetPosition1) && isAutonomous()){
	    			_drive.arcadeDrive(Math.copySign(0.5, -TargetPosition1), 0);
	    		}
	    		_drive.stopMotor();	        	       	
	    		a++;
	    		break;
	    	/*case 1:
	    		Right_Encoder.reset();
	    		double TargetPosition=1480;
	    		while(Math.abs(Right_Encoder.get()) < Math.abs(TargetPosition) && isAutonomous()){
	    			_drive.arcadeDrive(Math.copySign(0.5, -TargetPosition), 0);
	    		}
	    		_drive.stopMotor();	        	       	
	    		a++;
	    		break;
	    	default:
	    		_drive.stopMotor();
	    		running=false;
	    		break;*/
	    	case 1:		
	    		/*LeftGear.setAngle(60);
	    		Timer.delay(0.2);
	    		RightGear.setAngle(120);	
	    		double TargetPosition=3290;
	    		while(Math.abs(Right_Encoder.get()) < Math.abs(TargetPosition) && isAutonomous()){
	    			_drive.arcadeDrive(Math.copySign(0.8, -TargetPosition), 0);
	    			
	    		}*/
	    		_ShotBall.set(0.5);
	    		Timer.delay(2);
	    		_drive.stopMotor();      	
	    		a++;
	    		break;
	    	}
    	}
    	_drive.stopMotor();
    	
    }

    /**
     * This function is called periodically during operator control
     */ 
    @Override
    public void disabledInit(){
    	_drive.stopMotor();
    }
    
    public void teleopInit() {
    	//_drive.stopMotor();	
    }
    	
    @Override
    public void teleopPeriodic() {
    	double forward = deadzone(_joy1.getRawAxis(1),0.1); // logitech gampad left X, positive is forward
    	double turn =-deadzone(_joy1.getRawAxis(2),0.1); //logitech gampad right X, positive means turn right
    	_drive.arcadeDrive(0.5*forward,0.7*turn);
    	//SmartDashboard.putData("Cam", );
    	Timer.delay(0.005);
    	
    	if(_joy.getTrigger()==true){
    		_ShotBall.set(-0.6);
    	}
    	else{
    		_ShotBall.set(0);
    	}
    	if(_joy.getRawButton(4)==true){
    		LeftGear.setAngle(60);
    		Timer.delay(0.2);
    		RightGear.setAngle(120);
    	}
    	if(_joy.getRawButton(6)==true){
    		//LeftGear.setAngle(180);
    		//Timer.delay(0.2);
    		RightGear.setAngle(0);	
    	}
    	if(_joy.getRawButton(7)==true&&_joy.getRawButton(8)==false){
    		_Climber.set(1);
    	}
    	else{
    		_Climber.set(0);
    	}
    	if(_joy.getRawButton(8)==true&&_joy.getRawButton(7)==false){
    		_Climber.set(-1);
    	}
    	else{
    		_Climber.set(0);
    	}
    	if(_joy.getRawButton(5)==true&&_joy.getRawButton(3)==false){
    		_Belt0.set(-1);
    	}
    	
    	if(_joy.getRawButton(3)==true&&_joy.getRawButton(5)==false){
    		_Belt0.set(1);
    	}
    	if(_joy.getRawButton(5)==false&&_joy.getRawButton(3)==false){
    		_Belt0.set(0);
    	}
    	if(_joy.getRawButton(11)==true&&_joy.getRawButton(12)==false){
    		_spin.set(-1);
    	}
    	if(_joy.getRawButton(10)==true&&_joy.getRawButton(12)==false&&_joy.getRawButton(11)==false){
    		_spin.set(0.0);
    	}
    	if(_joy.getRawButton(12)==true&&_joy.getRawButton(11)==false){
    		_spin.set(1);
    	}
    	if(_joy1.getRawButton(5)==true){
    		GetBall.setAngle(0);
    	}
    	if(_joy1.getRawButton(3)==true){
    		GetBall.setAngle(90);
    	}
    }
    public void disabledPeriodic() {
    	_drive.stopMotor();
    	Right_Encoder.reset();
    } 
    
    double deadzone(double input,double deadzone){
    	if(Math.abs(input)>deadzone)
    	{
    		return input;
    	}else
    	{
    		return 0;
    	}
    }
}

