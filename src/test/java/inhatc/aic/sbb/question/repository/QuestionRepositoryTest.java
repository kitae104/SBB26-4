package inhatc.aic.sbb.question.repository;

import inhatc.aic.sbb.question.entity.Question;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionRepositoryTest {

    @Autowired // 생성자 대신 자동으로 객체를 주입해주는 어노테이션
    private QuestionRepository questionRepository;

    @Test
    public void unitTest(){
        System.out.println("유닛 테스트 확인!!");
    }

    @Test
    public void testSave(){
        Question question = new Question();
        question.setSubject("테스트 제목");
        question.setContent("테스트 내용");
        System.out.println("저장 전 : " + question);
        Question saved = questionRepository.save(question);
        System.out.println("저장 후 : " + saved);
    }

    @Test
    public void testFindAll(){
        List<Question> questions = questionRepository.findAll();
        System.out.println("전체 조회 : " + questions);
    }

    @Test
    public void testFindById(){
        questionRepository.findById(4L).ifPresent(question -> {
            System.out.println("조회 결과 : " + question);
        });

        Question question = questionRepository.findById(3L).orElseThrow(() -> {
            return new EntityNotFoundException("해당 객체가 존재 하지 않습니다.");
        });
        assertEquals(3, question.getId());
    }

    @Test
    public void testFindLike(){
        List<Question> questionList = questionRepository.findBySubjectLike("%1%");
        assertEquals(1, questionList.size());
    }

    // 책을 보고 나머지 Test 진행해보기.
}