   /* Copyright (c) 2018 FIRST. All rights reserved.
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

   //package org.firstinspires.ftc.robotcontroller.external.samples;
   package org.firstinspires.ftc.teamcode;

   import com.qualcomm.robotcore.eventloop.opmode.Disabled;
   import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
   import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
   import java.util.List;
   import org.firstinspires.ftc.robotcore.external.ClassFactory;

   import com.qualcomm.robotcore.hardware.DcMotor;
   import com.qualcomm.robotcore.hardware.VoltageSensor;
   import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
   import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
   import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
   import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

   import org.firstinspires.ftc.teamcode.HardwarePushbotMain;

   /**
    * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
    * determine the position of the gold and silver minerals.
    *
    * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
    * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
    *
    * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
    * is explained below.
    */
   @TeleOp(name = "Concept: TensorFlow Object Detection", group = "Concept")
   public class ConceptTensorFlowObjectDetection extends LinearOpMode {
       HardwarePushbotMain robot = new HardwarePushbotMain();
       private static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
       private static final String LABEL_FIRST_ELEMENT = "QUAD";
       private static final String LABEL_SECOND_ELEMENT = "Single";

       /**
        * Initialize the Vuforia localization engine.
        */
       private void initVuforia() {
           /*
            * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
            */
           VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

           parameters.vuforiaLicenseKey = VUFORIA_KEY;
           parameters.cameraDirection = CameraDirection.BACK;

           //  Instantiate the Vuforia engine
           vuforia = ClassFactory.getInstance().createVuforia(parameters);

           // Loading trackables is not necessary for the TensorFlow Object Detection engine.
       }

       /**
        * Initialize the TensorFlow Object Detection engine.
        */
       private void initTfod() {
           int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                   "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
           TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
           tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
           tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
       }

       /*
        * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
        * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
        * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
        * web site at https://developer.vuforia.com/license-manager.
        *
        * Vuforia license keys are always 380 characters long, and look as if they contain mostly
        * random data. As an example, here is a example of a fragment of a valid key:
        *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
        * Once you've obtained a license key, copy the string from the Vuforia web site
        * and paste it in to your code on the next line, between the double quotes.
        */
       public double getBatteryVoltage() {
           double result = Double.POSITIVE_INFINITY;
           for (VoltageSensor sensor : hardwareMap.voltageSensor) {
               double voltage = sensor.getVoltage();
               if (voltage > 0) {
                   result = Math.min(result, voltage);
               }
           }
           return result;



       }

       public void driveForwardDistance(double power, int distance){
           distance = (int)((distance/(4*Math.PI))*1440);
           robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

           robot.rightDrive.setTargetPosition(-distance);
           robot.rightDriveTwo.setTargetPosition(-distance);
           robot.leftDrive.setTargetPosition(-distance);
           robot.leftDriveTwo.setTargetPosition(-distance);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

           robot.rightDrive.setPower(-power);
           robot.rightDriveTwo.setPower(-power);
           robot.leftDrive.setPower(-power);
           robot.leftDriveTwo.setPower(-power);

           while(robot.rightDrive.isBusy() && robot.rightDriveTwo.isBusy() && robot.leftDrive.isBusy() && robot.leftDriveTwo.isBusy()){
               telemetry.addData("motor 1", robot.rightDrive.getCurrentPosition());
               telemetry.addData("motor 2", robot.rightDriveTwo.getCurrentPosition());
               telemetry.addData("motor 3", robot.leftDrive.getCurrentPosition());
               telemetry.addData("motor 4", robot.leftDriveTwo.getCurrentPosition());
               telemetry.update();

           }

           robot.rightDrive.setPower(0);
           robot.rightDriveTwo.setPower(0);
           robot.leftDrive.setPower(0);
           robot.leftDriveTwo.setPower(0);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       }
       public void driveBackwardDistance(double power, int distance){
           distance = (int)((distance/(4*Math.PI))*1440);
           robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

           robot.rightDrive.setTargetPosition(distance);
           robot.rightDriveTwo.setTargetPosition(distance);
           robot.leftDrive.setTargetPosition(distance);
           robot.leftDriveTwo.setTargetPosition(distance);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

           robot.rightDrive.setPower(power);
           robot.rightDriveTwo.setPower(power);
           robot.leftDrive.setPower(power);
           robot.leftDriveTwo.setPower(power);

           while(robot.rightDrive.isBusy() && robot.rightDriveTwo.isBusy() && robot.leftDrive.isBusy() && robot.leftDriveTwo.isBusy()){

           }

           robot.rightDrive.setPower(0);
           robot.rightDriveTwo.setPower(0);
           robot.leftDrive.setPower(0);
           robot.leftDriveTwo.setPower(0);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       }
       public void turnDistance(double power, int distance){
           distance = (int)((distance/(4*Math.PI))*1440);
           robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

           robot.rightDrive.setTargetPosition(-distance);
           robot.rightDriveTwo.setTargetPosition(-distance);
           robot.leftDrive.setTargetPosition(distance);
           robot.leftDriveTwo.setTargetPosition(distance);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

           robot.rightDrive.setPower(-power);
           robot.rightDriveTwo.setPower(-power);
           robot.leftDrive.setPower(power);
           robot.leftDriveTwo.setPower(power);

           while(robot.rightDrive.isBusy() && robot.rightDriveTwo.isBusy() && robot.leftDrive.isBusy() && robot.leftDriveTwo.isBusy()){

           }

           robot.rightDrive.setPower(0);
           robot.rightDriveTwo.setPower(0);
           robot.leftDrive.setPower(0);
           robot.leftDriveTwo.setPower(0);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       }
       public void turnDistanceTwo(double power, int distance){
           distance = (int)((distance/(4*Math.PI))*1440);
           robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

           robot.rightDrive.setTargetPosition(distance);
           robot.rightDriveTwo.setTargetPosition(distance);
           robot.leftDrive.setTargetPosition(-distance);
           robot.leftDriveTwo.setTargetPosition(-distance);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

           robot.rightDrive.setPower(power);
           robot.rightDriveTwo.setPower(power);
           robot.leftDrive.setPower(-power);
           robot.leftDriveTwo.setPower(-power);

           while(robot.rightDrive.isBusy() && robot.rightDriveTwo.isBusy() && robot.leftDrive.isBusy() && robot.leftDriveTwo.isBusy()){

           }

           robot.rightDrive.setPower(0);
           robot.rightDriveTwo.setPower(0);
           robot.leftDrive.setPower(0);
           robot.leftDriveTwo.setPower(0);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       }
       public void turnRightDistance(double power, int distance){
           distance = (int)((distance/(4*Math.PI))*1440);
           robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

           robot.rightDrive.setTargetPosition(distance);
           robot.rightDriveTwo.setTargetPosition(-distance);
           robot.leftDrive.setTargetPosition(-distance);
           robot.leftDriveTwo.setTargetPosition(distance);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

           robot.rightDrive.setPower(power);
           robot.rightDriveTwo.setPower(-power);
           robot.leftDrive.setPower(-power);
           robot.leftDriveTwo.setPower(power);

           while(robot.rightDrive.isBusy() && robot.rightDriveTwo.isBusy() && robot.leftDrive.isBusy() && robot.leftDriveTwo.isBusy()){

           }

           robot.rightDrive.setPower(0);
           robot.rightDriveTwo.setPower(0);
           robot.leftDrive.setPower(0);
           robot.leftDriveTwo.setPower(0);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       }
       public void turnLeftDistance(double power, int distance){
           distance = (int)((distance/(4*Math.PI))*1440);
           robot.rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

           robot.rightDrive.setTargetPosition(-distance);
           robot.rightDriveTwo.setTargetPosition(distance);
           robot.leftDrive.setTargetPosition(distance);
           robot.leftDriveTwo.setTargetPosition(-distance);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);

           robot.rightDrive.setPower(-power);
           robot.rightDriveTwo.setPower(power);
           robot.leftDrive.setPower(power);
           robot.leftDriveTwo.setPower(-power);

           while(robot.rightDrive.isBusy() && robot.rightDriveTwo.isBusy() && robot.leftDrive.isBusy() && robot.leftDriveTwo.isBusy()){

           }

           robot.rightDrive.setPower(0);
           robot.rightDriveTwo.setPower(0);
           robot.leftDrive.setPower(0);
           robot.leftDriveTwo.setPower(0);

           robot.rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.rightDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
           robot.leftDriveTwo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
       }






       private static final String VUFORIA_KEY = "AZ7T643/////AAABmdqJoNTmBUx8vIitLoGrYW1MxKnejF37jlVcBCGQyrjPualVhJPdefn7U9axsxiEIjYFbUT01TBnWWDX76E41TYHNNlMHNE/fwKWlpwyt17Duj4Hy7dfFeyO6OxZLCtD7alIGSWnnP0H5hCLQZEoZUdk4DUUAroH9nYIWeppn4XMGTAfq9j/4qJYaUG3YArHyRK4NeLtJRUVhU0RUYYAeqyYdApbNM6bYbSN6iSyt0Abxbiu1t5Bjg87X02Y2g2tOtQu9Cc65mCu3onYunMxBVY8+knQOoggLixcvcuDv1tIYL4tSaNHW/z9DPQCrl7VZ3dEHqDl30XY5/QvKM7hl3aGdBdiaujsfPnWlSBhifit";

       /**
        * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
        * localization engine.
        */
       private VuforiaLocalizer vuforia;

       /**
        * {@link #tfod} is the variable we will use to store our instance of the TensorFlow Object
        * Detection engine.
        */
       private TFObjectDetector tfod;

       @Override
       public void runOpMode() {
           // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
           // first.
           initVuforia();
           initTfod();
           robot.init(hardwareMap);
           int COUNT = 0;


           /** Wait for the game to begin */
           telemetry.addData(">", "Press Play to start tracking");
           telemetry.update();
           waitForStart();
           boolean STOPPER = false;
           double VOLTAGE = getBatteryVoltage();
           double powerOne = 0.2;
           double powerTwo = 0.4;
           telemetry.addData(">", VOLTAGE);

           //double powerOne = 0.2;
           //double powerTwo = 0.4;

           if (opModeIsActive()) {
               //            /** Activate TensorFlow Object Detection. */
               if (tfod != null) {
                   tfod.activate();
               }
               if (opModeIsActive()) {
                   while (opModeIsActive()) {
                       if(COUNT == 300000) {



                           //(v-12.6)/12.6 + 2
                           //example
                           // VOLTAGE = 12.9
                           //  robot.rightDrive.setPower(-((VOLTAGE-12.6)/12.6) + 0.2);


                           //end of example
                           //robot.rightDrive.setPower(-powerOne);
                           //robot.rightDriveTwo.setPower(-powerOne);
                           //robot.leftDrive.setPower(powerOne);
                           //robot.leftDriveTwo.setPower(powerOne);

                           turnDistance(powerOne, 17);
                           robot.rightDrive.setPower(0);
                           robot.rightDriveTwo.setPower(0);
                           robot.leftDrive.setPower(0);
                           robot.leftDriveTwo.setPower(0);
                           sleep(1000);

                           // turnDistance(powerOne, distance);
                          //  robot.rightDrive.setPower(powerTwo);
                           // robot.rightDriveTwo.setPower(-powerTwo);
                           // robot.leftDrive.setPower(-powerTwo);
                           // robot.leftDriveTwo.setPower(powerTwo);
                         //   turnRightDistance(powerTwo, 2);
                            driveForwardDistance(powerTwo, 57);
                            robot.rightDrive.setPower(0);
                            robot.rightDriveTwo.setPower(0);
                            robot.leftDrive.setPower(0);
                            robot.leftDriveTwo.setPower(0);
                            sleep(2000);
                            turnDistance(powerTwo, 16);
                            //drive forward
                          //  turnRightDistance(powerTwo, 4);
                            //sleep(4200);
                            //rest
                            //robot.rightDrive.setPower(-powerTwo);
                            //robot.rightDriveTwo.setPower(-powerTwo);
                            //robot.leftDrive.setPower(-powerTwo);
                            //robot.leftDriveTwo.setPower(-powerTwo);
                           // driveForwardDistance(powerTwo, distance);
                            //drive forward
                            robot.rightDrive.setPower(0);
                            robot.rightDriveTwo.setPower(0);
                            robot.leftDrive.setPower(0);
                            robot.leftDriveTwo.setPower(0);
                            sleep(300);
                            robot.wobble.setPower(-0.4);
                            sleep(700);
                            robot.clamp.setPosition(0.5);
                            sleep(400);
                            robot.wobble.setPower(0);
                            sleep(500);
                            robot.wobble.setPower(0.4);
                            sleep(2000);
                            //put down wobble
                            turnDistanceTwo(powerTwo, 16);
                            //drive left
                            turnLeftDistance(powerTwo, 26);
                            //turn

                           turnDistanceTwo(powerTwo, 3);
                            sleep(600);
                            //rest
                            robot.shooter.setPower(-0.75);
                            sleep(4000);
                            robot.intaker.setPower(1);
                            robot.sucker.setPower(-1);
                            robot.shooter.setPower(-0.75);
                            sleep(750);
                            robot.intaker.setPower(0);
                            robot.sucker.setPower(0);
                            robot.shooter.setPower(-0.75);
                            sleep(2000);
                            robot.intaker.setPower(1);
                            robot.sucker.setPower(-1);
                            robot.shooter.setPower(-0.75);
                            sleep(3000);
                           driveForwardDistance(powerOne, 2);
                            STOPPER = true;
                       }
                       if (STOPPER == true) {
                           break;
                       }
                       if (tfod != null) {
                           // getUpdatedRecognitions() will return null if no new information is available since
                           // the last time that call was made.
                           List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                           if (updatedRecognitions != null) {
                               telemetry.addData("# Object Detected", updatedRecognitions.size());

                               // step through the list of recognitions and display boundary info.
                               int i = 0;
                               telemetry.addData("# Object Detected", updatedRecognitions.size());
                               telemetry.addData(">", VOLTAGE);

                               telemetry.addData("# Object Detected", updatedRecognitions.size());

                               for (Recognition recognition : updatedRecognitions) {
                                   telemetry.addData(String.format("label (%d)", i), recognition.getLabel());

                                   if (updatedRecognitions.size() == 0) {
                                       telemetry.addData(String.format("label (%d)", i), updatedRecognitions.size());

                                       robot.rightDrive.setPower(powerOne);
                                       robot.rightDriveTwo.setPower(powerOne);
                                       robot.leftDrive.setPower(powerOne);
                                       robot.leftDriveTwo.setPower(powerOne);
                                       sleep(3200);
                                       robot.rightDrive.setPower(powerTwo);
                                       robot.rightDriveTwo.setPower(-powerTwo);
                                       robot.leftDrive.setPower(-powerTwo);
                                       robot.leftDriveTwo.setPower(powerTwo);
                                       // sleep(1000);
                                       sleep(4100);
                                       robot.rightDrive.setPower(0);
                                       robot.rightDriveTwo.setPower(0);
                                       robot.leftDrive.setPower(0);
                                       robot.leftDriveTwo.setPower(0);
                                       sleep(2000);
                                       robot.rightDrive.setPower(-powerTwo);
                                       robot.rightDriveTwo.setPower(-powerTwo);
                                       robot.leftDrive.setPower(-powerTwo);
                                       robot.leftDriveTwo.setPower(-powerTwo);
                                       sleep(3200);
                                       robot.rightDrive.setPower(0);
                                       robot.rightDriveTwo.setPower(0);
                                       robot.leftDrive.setPower(0);
                                       robot.leftDriveTwo.setPower(0);
                                       robot.wobble.setPower(-0.4);
                                       robot.clamp.setPosition(0.5);
                                       sleep(300);
                                       robot.wobble.setPower(0);
                                       sleep(500);
                                       robot.wobble.setPower(0.4);
                                       sleep(2000);
                                       robot.wobble.setPower(0);
                                       sleep(500);
                                       robot.rightDrive.setPower(-powerTwo);
                                       robot.rightDriveTwo.setPower(powerTwo);
                                       robot.leftDrive.setPower(powerTwo);
                                       robot.leftDriveTwo.setPower(-powerTwo);
                                       sleep(1500);
                                       robot.rightDrive.setPower(-powerTwo);
                                       robot.rightDriveTwo.setPower(-powerTwo);
                                       robot.leftDrive.setPower(-powerTwo);
                                       robot.leftDriveTwo.setPower(-powerTwo);
                                       sleep(500);
                                       robot.rightDrive.setPower(-powerTwo);
                                       robot.rightDriveTwo.setPower(powerTwo);
                                       robot.leftDrive.setPower(powerTwo);
                                       robot.leftDriveTwo.setPower(-powerTwo);
                                       sleep(2700);
                                       robot.shooter.setPower(-1);
                                       robot.intaker.setPower(1);
                                       robot.sucker.setPower(-1);
                                       sleep(7000);
                                       robot.rightDrive.setPower(-powerTwo);
                                       robot.rightDriveTwo.setPower(-powerTwo);
                                       robot.leftDrive.setPower(-powerTwo);
                                       robot.leftDriveTwo.setPower(-powerTwo);
                                       sleep(100);
                                       STOPPER = true;

                                   }

                                   if (recognition.getLabel() == "QUAD") {

                                       turnDistance(powerOne, 17);
                                       robot.rightDrive.setPower(0);
                                       robot.rightDriveTwo.setPower(0);
                                       robot.leftDrive.setPower(0);
                                       robot.leftDriveTwo.setPower(0);
                                       sleep(1000);

                                       turnRightDistance(powerTwo, 3);

                                       driveForwardDistance(powerTwo, 107);
                                       robot.rightDrive.setPower(0);
                                       robot.rightDriveTwo.setPower(0);
                                       robot.leftDrive.setPower(0);
                                       robot.leftDriveTwo.setPower(0);
                                       sleep(2000);


                                       turnDistance(powerTwo, 16);

                                       sleep(300);
                                       robot.wobble.setPower(-0.4);
                                       sleep(700);
                                       robot.clamp.setPosition(0.5);
                                       sleep(400);
                                       robot.wobble.setPower(0);
                                       sleep(500);
                                       robot.wobble.setPower(0.4);
                                       sleep(2000);
                                       //put down wobble



                                       driveBackwardDistance(powerTwo, 30);

                                       //drive left
                                       turnLeftDistance(powerTwo, 26);
                                       //turn

                                       turnDistanceTwo(powerTwo, 3);
                                       sleep(600);
                                       //rest
                                       robot.shooter.setPower(-0.75);
                                       sleep(4000);
                                       robot.intaker.setPower(1);
                                       robot.sucker.setPower(-1);
                                       robot.shooter.setPower(-0.75);
                                       sleep(750);
                                       robot.intaker.setPower(0);
                                       robot.sucker.setPower(0);
                                       robot.shooter.setPower(-0.75);
                                       sleep(2000);
                                       robot.intaker.setPower(1);
                                       robot.sucker.setPower(-1);
                                       robot.shooter.setPower(-0.75);
                                       sleep(3000);
                                       driveForwardDistance(powerOne, 2);
                                       STOPPER = true;

                                   } else if (recognition.getLabel() == "Single") {

                                       turnDistance(powerOne, 17);
                                       robot.rightDrive.setPower(0);
                                       robot.rightDriveTwo.setPower(0);
                                       robot.leftDrive.setPower(0);
                                       robot.leftDriveTwo.setPower(0);
                                       sleep(1000);

                                       turnRightDistance(powerTwo, 3);

                                       driveForwardDistance(powerTwo, 77);
                                       robot.rightDrive.setPower(0);
                                       robot.rightDriveTwo.setPower(0);
                                       robot.leftDrive.setPower(0);
                                       robot.leftDriveTwo.setPower(0);
                                       sleep(2000);


                                       turnLeftDistance(powerTwo, 3);

                                       sleep(300);
                                       robot.wobble.setPower(-0.4);
                                       sleep(700);
                                       robot.clamp.setPosition(0.5);
                                       sleep(400);
                                       robot.wobble.setPower(0);
                                       sleep(500);
                                       robot.wobble.setPower(0.4);
                                       sleep(2000);
                                       //put down wobble



                                       driveBackwardDistance(powerTwo, 20);

                                       //drive left
                                       turnLeftDistance(powerTwo, 26);
                                       //turn

                                       turnDistanceTwo(powerTwo, 3);
                                       sleep(600);
                                       //rest
                                       robot.shooter.setPower(-0.75);
                                       sleep(4000);
                                       robot.intaker.setPower(1);
                                       robot.sucker.setPower(-1);
                                       robot.shooter.setPower(-0.75);
                                       sleep(750);
                                       robot.intaker.setPower(0);
                                       robot.sucker.setPower(0);
                                       robot.shooter.setPower(-0.75);
                                       sleep(2000);
                                       robot.intaker.setPower(1);
                                       robot.sucker.setPower(-1);
                                       robot.shooter.setPower(-0.75);
                                       sleep(3000);
                                       driveForwardDistance(powerOne, 2);
                                       STOPPER = true;

                                   }
                               }
                               telemetry.update();
                           }

                       }
                       COUNT++;
                   }
               }

               if (tfod != null) {
                   tfod.shutdown();
               }
           }
       }
   }
