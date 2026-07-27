package inhatc.aic.sbb.question.repository;

import inhatc.aic.sbb.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// <엔티티 이름, id 타입>
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findBySubjectLike(String s);

    Page<Question> findAll(Pageable pageable);
}
