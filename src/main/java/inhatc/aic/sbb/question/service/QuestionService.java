package inhatc.aic.sbb.question.service;

import inhatc.aic.sbb.member.entity.Member;
import inhatc.aic.sbb.question.dto.QuestionDto;
import inhatc.aic.sbb.question.entity.Question;
import inhatc.aic.sbb.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;


    public Page<Question> getQuestionList(int page) {
        List<Sort.Order> orders = List.of(Sort.Order.desc("id")); // id를 기준으로 내림차순 정렬
        Pageable pageable = PageRequest.of(page, 10, Sort.by(orders)); // 페이지 번호와 페이지 크기를 설정 (예: 10개씩)
        Page<Question> paging = questionRepository.findAll(pageable);
        return paging;
    }

    public Question getQuestion(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id가 존재하지 않습니다: " + id));
        return question;
    }

    public void questionCreate(QuestionDto questionDto, Member member) {
        Question question = Question.builder()
                .content(questionDto.getContent())
                .subject(questionDto.getSubject())
                .author(member)
                .build();
        questionRepository.save(question);
    }

    public void questionModify(Question question, @Valid QuestionDto questionDto) {
        question.setSubject(questionDto.getSubject());
        question.setContent(questionDto.getContent());
        questionRepository.save(question); // id 여부에 따라 insert or update
    }
}
