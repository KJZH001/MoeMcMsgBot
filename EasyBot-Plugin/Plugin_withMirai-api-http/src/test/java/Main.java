import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(genVerifyCode(5));
            Integer.parseInt(args[1]);
        }


    }

    /**
     * 生成验证码
     * @return verify code
     */
    private static String genVerifyCode(int strLength) {
        SecureRandom rm = new SecureRandom();
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);
        String fixLenthString = String.valueOf(pross);
        return fixLenthString.substring(1, strLength + 1);
    }
}
