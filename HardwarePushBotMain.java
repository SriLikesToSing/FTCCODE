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

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class HardwarePushbotMain {
    /* Public OpMode members. */
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor rightDriveTwo = null;
    public DcMotor leftDriveTwo = null;
    public DcMotor shooter = null;
    public DcMotor intaker = null;
    public DcMotor wobble = null;
    public Servo clamp = null;
    public CRServo sucker = null;
    //public DcMotor succ = null;
    //public DcMotor shot = null;


    //public DcMotor  leftArm     = null;
    // public Servo    leftClaw    = null;
    //  public Servo    rightClaw   = null;

    /*
    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
    */

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwarePushbotMain() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftDrive = hwMap.get(DcMotor.class, "left_drive");
        leftDriveTwo = hwMap.get(DcMotor.class, "left_drive_two");
        rightDrive = hwMap.get(DcMotor.class, "right_drive");
        rightDriveTwo = hwMap.get(DcMotor.class, "right_drive_two");
        shooter = hwMap.get(DcMotor.class, "shooter");
        intaker = hwMap.get(DcMotor.class, "intaker");
        wobble = hwMap.get(DcMotor.class, "wobble");
        clamp = hwMap.get(Servo.class, "clamp");
        sucker = hwMap.crservo.get("sucker");

      //  succ = hwMap.get(DcMotor.class, "succ");

        //    shot = hwMap.get(DcMotor.class, "shot");

        // leftArm    = hwMap.get(DcMotor.class, "left_arm");
        leftDrive.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        leftDriveTwo.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors
        rightDriveTwo.setDirection(DcMotor.Direction.FORWARD);
        shooter.setDirection(DcMotor.Direction.FORWARD);
        intaker.setDirection(DcMotor.Direction.FORWARD);
        wobble.setDirection(DcMotor.Direction.FORWARD);
        clamp.setDirection(Servo.Direction.FORWARD);
        sucker.setDirection(CRServo.Direction.FORWARD);
     //   succ.setDirection(DcMotor.Direction.FORWARD);
        //shooter.setDirection(CRServo.Direction.FORWARD);
        //shot.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftDrive.setPower(0);
        leftDriveTwo.setPower(0);
        rightDrive.setPower(0);
        rightDriveTwo.setPower(0);
        shooter.setPower(0);
        intaker.setPower(0);
        wobble.setPower(0);
        clamp.setPosition(0);
        sucker.setPower(0);
     //   succ.setPower(0);
        //leftArm.setPower(0);
        //shooter.setPower(0);
        //shot.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftDriveTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDriveTwo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intaker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wobble.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

       // succ.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //shot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        /*
        leftClaw  = hwMap.get(Servo.class, "left_hand");
        rightClaw = hwMap.get(Servo.class, "right_hand");
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);
        */
    }
}

;
