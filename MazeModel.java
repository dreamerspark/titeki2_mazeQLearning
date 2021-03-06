import java.io.*;
import java.util.Iterator;
import java.util.Stack;

/**
 * 強化学習によりゴールまでの経路を学習するクラス
 */
public class MazeModel implements Runnable {

	/**
	 * 強化学習によりゴールまでの経路を学習するオブジェクトを生成する
	 * 
	 * @param mazeFile
	 *            迷路データのファイル名
	 */
	public MazeModel(String mazeFile) {
		// 迷路データを生成
		mazeData = new MazeData(mazeFile);
		// ロボットを生成
		robot = new Robot(mazeData.getSX(), mazeData.getSY());
	}

	/**
	 * 実行用関数
	 */
	public void run() {
		try {

			// ここをがんばって作る

			// ゴール座標の取得
			int gx = mazeData.getGX();
			int gy = mazeData.getGY();
			// step 1:Q学習する
			// QLearningのインスタンスを作る
			int states = mazeData.getWidth() * mazeData.getHeight();
			int actions = 4;
			double alpha = 0.5;
			double gamma = 0.5;
			QLearning ql = new QLearning(states, actions, alpha, gamma);

			int trials = 500;
			int steps = 100;
			for (int i = 0; i < trials; i++) {
				robot.setX(mazeData.getSX());
				robot.setY(mazeData.getSY());
				for (int j = 0; j < steps; j++) {
					int state = mazeData.getWidth() * robot.getX() + robot.getY();
					double epsilon = 0.5;
					int action = ql.selectAction(state, epsilon);
					switch (action) {
					case 0:
						robot.setX(robot.getX() + 1);
						break;
					case 1:
						break;
					case 2:
						break;
					case 3:
						break;
					}

				}
				// ロボットの位置座標を更新
				robot.setX(x);
				robot.setY(y);

				// 現在の状態を描画する
				mazeView.repaint();

				// 速すぎるので 500msec 寝る
				Thread.sleep(500);

				// もしゴールに到達すれば終了
				if (x == gx && y == gy)
					break;

				// デバッグ用に現在位置を出力
				System.out.println("x = " + x + ", y = " + y);
			}
		} catch (

		Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * 描画用のビューを登録
	 */
	public void setView(MazeView view) {
		mazeView = view;
	}

	/**
	 * ロボットオブジェクトを取得する
	 * 
	 * @return ロボットオブジェクト
	 */
	public Robot getRobot() {
		return robot;
	}

	/**
	 * 迷路データオブジェクトを取得する
	 * 
	 * @return 迷路データオブジェクト
	 */
	public MazeData getMazeData() {
		return mazeData;
	}

	/** 迷路データ */
	private MazeData mazeData = null;
	/** ロボットデータ */
	private Robot robot = null;

	/** 描画用オブジェクト */
	private MazeView mazeView = null;
}
