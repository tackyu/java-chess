package chess.domain.chesspiece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.Csv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class RoleTest {
    @ParameterizedTest
    @CsvSource(value = {"BLACK_BISHOP,BLACK_BISHOP", "WHITE_BISHOP,WHITE_BISHOP"
            , "EMPTY,EMPTY", "BLACK_KING,BLACK_KING", "WHITE_KING,WHITE_KING"
            , "BLACK_KNIGHT,BLACK_KNIGHT", "WHITE_KNIGHT,WHITE_KNIGHT"
            , "BLACK_PAWN,BLACK_PAWN", "WHITE_PAWN,WHITE_PAWN"
            , "BLACK_QUEEN,BLACK_QUEEN", "WHITE_QUEEN,WHITE_QUEEN"
            , "BLACK_ROOK,BLACK_ROOK", "WHITE_ROOK,WHITE_ROOK"})
    @DisplayName("문자열 데이터로 올바른 체스말을 매칭한다.")
    void Role_findPiece_by_string_date(String pieceData, Role role) {
        assertThat(Role.findType(pieceData).getRole()).isEqualTo(role);
    }

    @Test
    @DisplayName("문자열 데이터로 올바른 체스말을 매칭한다.")
    void Role_findPiece_by_wrong_data() {
        assertThatThrownBy(() -> Role.findType("RED_ROOK"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 체스 말입니다");
    }
}
