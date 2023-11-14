package frc.robot;

public class Constants {

    private Constants(){}

    public static final double TEST_SPEED = 0.5;
    public static final double STEER_ANGLE_TOLERANCE = 1;

    //TODO correct IDs!
    public class FrontRight {
        private FrontRight(){}

        public static final int CANCODER_ID = 1;
        public static final int DRIVE_MOTOR_ID = 2;
        public static final int STEER_MOTOR_ID = 3;
    }

    public class FrontLeft {
        private FrontLeft(){}

        public static final int CANCODER_ID = 4;
        public static final int DRIVE_MOTOR_ID = 5;
        public static final int STEER_MOTOR_ID = 6;
    }

    public class BackRight {
        private BackRight(){}

        public static final int CANCODER_ID = 7;
        public static final int DRIVE_MOTOR_ID = 8;
        public static final int STEER_MOTOR_ID = 9;
    }

    public class BackLeft {
        private BackLeft(){}

        public static final int CANCODER_ID = 10;
        public static final int DRIVE_MOTOR_ID = 11;
        public static final int STEER_MOTOR_ID = 12;
    }
}
