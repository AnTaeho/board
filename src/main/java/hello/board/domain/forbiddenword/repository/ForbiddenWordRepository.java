package hello.board.domain.forbiddenword.repository;

import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForbiddenWordRepository extends JpaRepository<ForbiddenWord, Long> {
}
