import java.util.Random;

/**
 * Ｑ学習を行うクラス
 */
public class QLearning {

	private int numaction;

	private Random rand;

	/**
	 * Ｑ学習を行うオブジェクトを生成する
	 * 
	 * @param states
	 *            状態数
	 * @param actions
	 *            行動数
	 * @param alpha
	 *            学習率（0.0〜1.0）
	 * @param gamma
	 *            割引率（0.0〜1.0）
	 */
	public QLearning(int states, int actions, double alpha, double gamma) {
		this.qTable = new double[states][actions];
		this.alpha = alpha;
		this.gamma = gamma;

		numaction = actions;
		rand = new Random();
	}

	/**
	 * epsilon-Greedy 法により行動を選択する
	 * 
	 * @param state
	 *            現在の状態
	 * @param epsilon
	 *            ランダムに行動を選択する確率（0.0〜1.0）
	 * @return 選択された行動番号
	 */
	public int selectAction(int state, double epsilon) {
		int bestaction = 0;
		for (int i = 1; i < numaction; i++) {
			if (qTable[state][i] > qTable[state][bestaction])
				bestaction = i;
		}
		if (rand.nextDouble() < epsilon) {
			bestaction = rand.nextInt(numaction);
		}
		return bestaction;
	}

	/**
	 * Greedy 法により行動を選択する
	 * 
	 * @param state
	 *            現在の状態
	 * @return 選択された行動番号
	 */
	public int selectAction(int state) {
		int bestaction = 0;
		for (int i = 0; i < numaction; i++) {
			if (qTable[state][i] > qTable[state][bestaction])
				bestaction = i;
		}
		return bestaction;
	}

	/**
	 * Ｑ値を更新する
	 * 
	 * @param before
	 *            状態
	 * @param action
	 *            行動
	 * @param after
	 *            遷移後の状態
	 * @param reward
	 *            報酬
	 */
	public void update(int before, int action, int after, double reward) {
		int afaction = selectAction(after);
		double aa = qTable[before][action]
				+ alpha * (reward + gamma * qTable[after][afaction] - qTable[before][action]);
		qTable[before][action] = aa;
	}

	// フィールド
	private double qTable[][] = null;
	private double alpha = 0;
	private double gamma = 0;
}
