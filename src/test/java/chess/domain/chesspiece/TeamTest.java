package chess.domain.chesspiece;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TeamTest {
    @ParameterizedTest
    @CsvSource(value = {"BLACK,BLACK", "NOTHING,NOTHING", "WHITE,WHITE"})
    @DisplayName("문자열 데이터로 올바른 팀을 매칭한다.")
    void Role_findPiece_by_string_date(String teamData, Team team) {
        assertThat(Team.findTeam(teamData)).isEqualTo(team);
    }

    @Test
    @DisplayName("문자열 데이터로 올바른 팀을 매칭한다.")
    void Role_findPiece_by_wrong_data() {
        assertThatThrownBy(() -> Team.findTeam("RED"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 팀입니다");
    }
}
