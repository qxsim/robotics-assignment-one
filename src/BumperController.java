package rp.assignments.individual.ex1;

import lejos.nxt.Motor;
import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.DifferentialPilot;
import rp.robotics.DifferentialDriveRobot;
import rp.robotics.MobileRobot;
import rp.robotics.MobileRobotWrapper;
import rp.robotics.TouchSensorEvent;
import rp.robotics.TouchSensorListener;
import rp.systems.ControllerWithTouchSensor;
import rp.systems.StoppableRunnable;
import rp.util.Rate;

public class BumperController implements ControllerWithTouchSensor {
	
	DifferentialDriveRobot robot;
	RangeFinder ranger;
	Boolean pressed = false;
	Boolean run = false;
		
	public BumperController(DifferentialDriveRobot robot) {
		this.robot = robot;
	}
	
	public void setRangeScanner(RangeFinder ranger) {
		this.ranger = ranger;
	}
	
	@Override
	public void sensorPressed(TouchSensorEvent _e) {
		pressed = true;
	}
	
	@Override
	public void sensorReleased(TouchSensorEvent _e) {
		
	}
	
	@Override
	public void sensorBumped(TouchSensorEvent _e) {
		
	}
	
	@Override
	public void run() {
		DifferentialPilot DP = this.robot.getDifferentialPilot();
		run = true;
		Rate rate;
		float infinity = Float.POSITIVE_INFINITY;
		Double robotLength = robot.getRobotLength();
		
		while (run) {	
			DP.travel(infinity, true);
			rate = new Rate(40);
			while (DP.isMoving() && !pressed) {
				if (ranger != null) {
					if (ranger.getRange() != infinity) {
						if (this.ranger.getRange() < robotLength) {
							this.sensorPressed(null);
							rate.sleep();
							break;
						}
					}
				}
			}
			if (pressed) {
				DP.stop();
				DP.travel(-robotLength);
				pressed = false;
			}
			DP.rotate(180);
		}
	}
	
	@Override
	public void stop() {
		run = false;
	}
}
