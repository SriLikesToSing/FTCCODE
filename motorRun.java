/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Testbot: Teleop", group="Testbot")
public class motorRun extends LinearOpMode {

    // Declare OpMode members.
    HardwarePushbotMain robot = new HardwarePushbotMain();
    private ElapsedTime runtime = new ElapsedTime();
    double POWER_FULL = 0.5;
    double POWER_REVERSE_FULL = -0.5;
    double POWER_SERVO_FULL = 1.0;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configurat;ion
        // step (using the FTC Robot Controller app on the phone).
       // leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
    //    rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        robot.init(hardwareMap);


        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            telemetry.addData("Status", "Initialized");
            telemetry.update();


            // Setup a variable for each drive wheel to save power level for telemetry


            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.



            double drive = gamepad1.left_stick_y;
           // double strafe = gamepad1.left_stick_x;
            double strafe = gamepad1.left_stick_x;
            //double twist = gamepad1.right_stick_x;
            double twist = gamepad1.right_stick_x;
            //boolean shoot = gamepad1.right_bumper;
            float shoot = gamepad2.left_trigger;
            float intake = gamepad2.right_trigger;
            boolean button = gamepad2.b;
            boolean REVERSE = gamepad2.a;
            boolean dPadUp = gamepad2.dpad_up;
            boolean yBUtton = gamepad2.y;
            boolean dPadDown = gamepad2.dpad_down;
            boolean  leftBumper = gamepad2.left_bumper;
            boolean rightBumper = gamepad2.right_bumper;



            //boolean stopShoot = gamepad1.left_bumper;
            //boolean intake = gamepad1.left_bumper;

                double[] speeds = {
                       (drive + strafe + twist),
                        (drive - strafe - twist),
                        (drive - strafe + twist),
                        (drive + strafe - twist)
                };
//
            //
            //
            //
                double max = Math.abs(speeds[0]);
                for (int x = 0; x < speeds.length; x++){
                    if (max < Math.abs(speeds[x])) max = Math.abs(speeds[x]);
                }

                if (max > 1) {
                    for (int x = 0; x < speeds.length; x++) {
                        speeds[x] /= max;
                    }
                }

                robot.leftDrive.setPower(speeds[0]);
                robot.rightDrive.setPower(speeds[1]);
                robot.leftDriveTwo.setPower(speeds[2]);
                robot.rightDriveTwo.setPower(speeds[3]);
                //robot.shooter.setPower(1);
                //robot.sucker.setPower(1);


            if(shoot>=0.5){
                    robot.shooter.setPower(-0.60);
                    robot.shooterServo.setPosition(0);
                    //robot.sucker.setPower(-1);

                }else if(shoot<0.5){
                    robot.shooter.setPower(0);
                    robot.shooterServo.setPosition(0.5);
                   // robot.sucker.setPower(0);
                }

                if(intake>=0.5){
                    robot.intaker.setPower(0.6);
                    robot.sucker.setPower(-1);
                }else if(intake<0.5){
                    robot.intaker.setPower(0);
                    robot.sucker.setPower(0);
                }

                if(button){
                    robot.intaker.setPower(1);
                }else if(button == false){
                    robot.intaker.setPower(0);
                }

                if(REVERSE){
                    robot.intaker.setPower(-1);
                    robot.sucker.setPower(1);
                }else if(REVERSE == false){
                    robot.intaker.setPower(0);
                }

                if(yBUtton
                ){
                    robot.wobble.setPower(1);

                }else if(dPadUp == false){
                    robot.wobble.setPower(0);
                }

                if(dPadDown){
                    robot.wobble.setPower(-0.3);
                }else if(dPadDown == false){
                    robot.wobble.setPower(0);
                }

                if(leftBumper){
                    robot.clamp.setPosition(0.5);
                }

                if(rightBumper){
                    robot.clamp.setPosition(0);
                }


                 //robot.wobble.setPower(1);


        }
    }
}
