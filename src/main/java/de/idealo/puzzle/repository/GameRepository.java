package de.idealo.puzzle.repository;

import de.idealo.puzzle.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for the {@link Game} entity.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
@Repository
public interface GameRepository extends MongoRepository<Game, String> {
}
