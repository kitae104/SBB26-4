package inhatc.aic.sbb.question.service;

import inhatc.aic.sbb.question.dto.QuestionDto;
import inhatc.aic.sbb.question.entity.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Test
//    @Transactional
    void createData(){
        for (int i = 1; i < 301; i++) {
            QuestionDto questionDto = QuestionDto.builder()
                    .subject("질문 제목 : " + i)
                    .content("질문 내용 테스트 데이터입니다. " + i)
                    .build();
//            questionService.questionCreate(questionDto);
        }

//        assertEquals(300, questionService.getQuestionList().size());
    }
}