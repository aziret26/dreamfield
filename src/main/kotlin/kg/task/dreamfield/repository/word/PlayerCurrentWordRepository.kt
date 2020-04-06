package kg.task.dreamfield.repository.word

import kg.task.dreamfield.domain.user.Player
import kg.task.dreamfield.domain.word.PlayerCurrentWord
import kg.task.dreamfield.domain.word.Word
import kg.task.dreamfield.repository.BaseRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface PlayerCurrentWordRepository : BaseRepository<PlayerCurrentWord> {
    fun existsByPlayer(player: Player): Boolean
    fun findByPlayer(user: Player): PlayerCurrentWord?

    @Modifying
    @Query("delete from PlayerCurrentWord pcw where pcw.player=:player")
    fun deleteByPlayer(player: Player)
}