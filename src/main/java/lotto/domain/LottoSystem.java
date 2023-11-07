package lotto.domain;

import lotto.config.Prize;
import lotto.domain.lotto.Lotto;
import lotto.domain.lotto.LottoNumber;
import lotto.domain.player.Player;
import lotto.dto.response.PrizeResponse;
import lotto.util.RandomUtil;

import java.util.*;

public class LottoSystem {

    private List<Lotto> winningLottos;
    private Player player;

    private LottoSystem(List<Lotto> winningLottos, Player player) {
        this.winningLottos = winningLottos;
        this.player = player;
    }

    public static LottoSystem create(List<Lotto> winningLottos, Player player) {
        return new LottoSystem(winningLottos, player);
    }

    public List<Lotto> generateWinningLottos() {
        List<Lotto> winningLottos = new ArrayList<>();
        for (int i = 0; i < player.getPurchasedLottoCount(); i++) {
            winningLottos.add(generateWinningLotto());
        }
        return winningLottos;
    }

    private Lotto generateWinningLotto() {
        return Lotto.create(RandomUtil.generateRandomLottoNumber());
    }

    private int getPurchasedLottoCount() {
        return player.getPurchasedLottoCount();
    }

    public List<Prize> getWinningResult() {
        List<Prize> prizes = new ArrayList<>();
        for (int i = 0; i < getPurchasedLottoCount(); i++) {
            Lotto winningLotto = winningLottos.get(i);
            int totalMatchingNumberCount = getMatchingNumberCount(winningLotto) + getMatchingBonusNumberCount(winningLotto);
            getPrize(totalMatchingNumberCount, hasBonusNumber(winningLotto)).ifPresent(prizes::add);
        }
        return prizes;
    }

    private Prize getWinningPrize(int matchingNumberCount, boolean hasBonusNumber) {
        if (matchingNumberCount == 5 && hasBonusNumber) {
            return Prize.FIVE_NUMBER_AND_BONUS_NUMBER_MATCH;
        }
        return Arrays.stream(Prize.values())
                .filter(prize -> prize.getMatchingNumberCount() == matchingNumberCount)
                .findAny()
                .orElse(Prize.NONE);
    }

    private int getMatchingNumberCount(Lotto winningLotto) {
        return (int) winningLotto.getLottoNumbers()
                .stream()
                .filter(winningLottoNumber -> player.getLotto().getLottoNumbers().contains(winningLottoNumber))
                .count();
    }

    private boolean hasBonusNumber(Lotto winningLotto) {
        return winningLotto.getLottoNumbers()
                .stream()
                .anyMatch(lottoNumber -> Objects.equals(lottoNumber.getLottoNumber(), player.getBonusNumber()));
    }

    public double calculateProfitRate(List<Prize> prizes) {
        int purchaseAmount = player.getPurchaseAmount();
        int totalProfit = 0;
        for (Prize prize : prizes) {
            totalProfit += prize.getPrizeMoney();
        }
        return ((double) totalProfit / purchaseAmount) * 100;
    }
}
