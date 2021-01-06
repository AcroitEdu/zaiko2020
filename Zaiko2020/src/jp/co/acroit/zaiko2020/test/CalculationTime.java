package jp.co.acroit.zaiko2020.test;

public class CalculationTime {
	public static void main(String[] args) {

        // 処理前の時刻を取得
        long startTime = System.currentTimeMillis();

        // 時間計測をしたい処理
        int result = 0;
        for (int i = 0; i < 1000000; i++) {
            result += 1;
        }
        long intervalTime = System.currentTimeMillis();

        // 処理後の時刻を取得
        long endTime = System.currentTimeMillis();

        System.out.println("開始時刻：" + startTime + " ms");
        System.out.println("開始時刻：" + intervalTime + " ms");
        System.out.println("終了時刻：" + endTime + " ms");
        System.out.println("処理時間：" + (endTime - startTime) + " ms");
        System.out.println("処理時間(途中)：" + (intervalTime - startTime) + " ms");

        System.out.println("処理時間(s)");
    }
}
