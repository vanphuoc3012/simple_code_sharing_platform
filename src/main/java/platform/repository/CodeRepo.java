package platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import platform.models.Code;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CodeRepo extends JpaRepository<Code, Integer> {
    Code save(Code code);

    Optional<Code> findById(UUID id);

    @Query(value = "SELECT * FROM code c WHERE time = 0 AND views = 0 ORDER BY date DESC LIMIT 10",
        nativeQuery = true)
    List<Code> findLatest();


    @Transactional
    void deleteById(UUID id);
}
