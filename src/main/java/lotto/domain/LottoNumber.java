package lotto.domain;

public class LottoNumber {

    private Integer lottoNumber;

    public LottoNumber(Integer lottoNumber) {
        this.lottoNumber = lottoNumber;
    }

    public static LottoNumber from(Integer lottoNumber) {
        validateLottoNumber(lottoNumber);
        return new LottoNumber(lottoNumber);
    }

    private static void validateLottoNumber(Integer lottoNumber) {
        validateRange(lottoNumber);
    }

    private static void validateRange(Integer lottoNumber) {
        if (lottoNumber < 1 || lottoNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 각각의 당첨 번호는 1~45 범위의 정수이어야 합니다.");
        }
    }
}
