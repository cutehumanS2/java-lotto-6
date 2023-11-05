package lotto;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import lotto.model.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import java.util.stream.Stream;

class LottoTest {
    @DisplayName("로또 번호의 개수가 6개가 넘어가면 예외가 발생한다.")
    @Test
    void createLottoByOverSize() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void createLottoByDuplicatedNumber() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 올바른지 검증한다.")
    @ParameterizedTest
    @MethodSource("provideNumbersForValidate")
    void 로또_번호_검증(List<Integer> numbers) {
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideNumbersForValidate() {
        return Stream.of(
                Arguments.of(List.of(1, 1, 2, 3, 4, 5)),
                Arguments.of(List.of(1, 46, 2, 3, 4, 5)),
                Arguments.of(List.of(1, 2, 3, 4, 5))
        );
    }

    @DisplayName("로또 번호가 오름차순으로 정렬된다.")
    @Test
    void 로또_번호_오름차순_정렬() {
        // given
        List<Integer> numbers = List.of(5, 41, 7, 33, 25, 18);
        Lotto lotto = new Lotto(numbers);

        // when
        List<Integer> sortedNumbers = lotto.getSortedNumbers();

        // then
        assertEquals(List.of(5, 7, 18, 25, 33, 41), sortedNumbers);
    }

    @DisplayName("로또 번호에 특정 번호가 포함되어 있는지 확인한다.")
    @ParameterizedTest
    @CsvSource({"2, true", "7, false"})
    void 특정_번호_포함_여부_확인(int number, boolean expectedResult) {
        // given
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        // when
        boolean result = lotto.hasNumber(number);

        // then
        assertEquals(expectedResult, result);
    }
}