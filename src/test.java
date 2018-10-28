import java.util.Scanner;

public class test {
    public static void main(String args[]) {
        int choose;
        Scanner input = new Scanner(System.in);
        System.out.println("输入1为时间戳转时间,输入2为时间转时间戳:");
        choose = input.nextInt();
        input.nextLine();
        if (choose == 1) {
            System.out.println("请输入时间戳:");
            TimeStamp timeStamp = new TimeStamp(input.nextLong());
            System.out.print("转换的日期为:" + timeStamp.stampTimeToDate());
        } else if (choose == 2) {
            System.out.println("请输入日期:");
            TimeStamp timeStamp = new TimeStamp(input.nextLine());
            System.out.println("转换的时间戳为:" + timeStamp.dateToStamptime());
        }

    }
}
