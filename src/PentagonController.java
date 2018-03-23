package rp.assignments.individual.ex1;

import lejos.nxt.Motor;
import lejos.robotics.RangeFinder;
import lejos.robotics.navigation.DifferentialPilot;
import rp.robotics.DifferentialDriveRobot;
import rp.robotics.MobileRobot;
import rp.robotics.MobileRobotWrapper;
import rp.systems.StoppableRunnable;

public class PentagonController implements StoppableRunnable {

	DifferentialDriveRobot robot;
	float sideLength;
	RangeFinder ranger;
	
	public PentagonController(DifferentialDriveRobot robot, float sideLength) {
		this.robot = robot;
		this.sideLength = sideLength;
	}
	
	public void setRangeScanner(RangeFinder ranger) {
		this.ranger = ranger;
	}
	
	@Override
	public void run() {
		final DifferentialPilot DP = this.robot.getDifferentialPilot();
	
		for (int i=0; i<5; i++) {
			DP.travel(this.sideLength);
			DP.rotate(72);
		}
	}

	@Override
	public void stop() {
		
	}
}