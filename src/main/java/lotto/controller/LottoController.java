package lotto.controller;

import lotto.model.LottoResult;
import lotto.model.User;
import lotto.model.WinningNumber;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    private static  User user;
    private static WinningNumber winningNumber;

    public void run() {
        inputAmount();
        purchaseLotto();
        inputWinningNumberAndBonusNumber();
        printLottoResult();
    }

    private void inputAmount() {
        try {
            int amount = InputView.inputAmount();
            user = User.from(amount);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            inputAmount();
        }
    }

    private void purchaseLotto() {
        OutputView.printPurchasedLottos(user.purchaseLottos());
    }

    private void inputWinningNumberAndBonusNumber() {
        try {
            String[] winningNumbers = InputView.inputWinningNumbers();
            int bonusNumber = InputView.inputBonusNumber();
            winningNumber = WinningNumber.from(winningNumbers, bonusNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            inputWinningNumberAndBonusNumber();
        }
    }

    private void printLottoResult() {
        LottoResult lottoResult = new LottoResult(user, winningNumber);
        OutputView.printLottoResult(lottoResult.getLottoResult(user.getPurchasedLottos(), winningNumber));
        OutputView.printYield(lottoResult.getYield());
    }
}
