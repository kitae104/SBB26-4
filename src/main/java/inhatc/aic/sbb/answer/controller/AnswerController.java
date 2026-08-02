package inhatc.aic.sbb.answer.controller;

import inhatc.aic.sbb.answer.dto.AnswerDto;
import inhatc.aic.sbb.answer.entity.Answer;
import inhatc.aic.sbb.answer.service.AnswerService;
import inhatc.aic.sbb.member.entity.Member;
import inhatc.aic.sbb.member.service.MemberService;
import inhatc.aic.sbb.question.entity.Question;
import inhatc.aic.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
@Slf4j
public class AnswerController {

    private final QuestionService questionService;

    private final AnswerService answerService;
    private final MemberService memberService;

//    DTO 사용전
//    @PostMapping("/create/{id}")
//    public String create(@PathVariable("id") Long id, @RequestParam("content") String content) {
//        log.info("Answer created for question ID: {}, Content: {}", id, content);
//
//        Question question = questionService.getQuestion(id);
//
//        answerService.createAnswer(question, content);
//
//        return "redirect:/question/detail/" + id;  // redirect 용도 확인.
//    }

    @PostMapping("/create/{id}")
    public String create(@PathVariable("id") Long id,
                         @Valid AnswerDto answerDto,
                         BindingResult bindingResult,
                         Principal principal,
                         Model model ) {
        log.info("AnswerDto : {}", answerDto);
        Question question = questionService.getQuestion(id);

        Member member = memberService.getMember(principal.getName());

        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            model.addAttribute("answerDto", answerDto);
            return "question/detail";
        }


        answerService.createAnswer(question, answerDto, member);

        return "redirect:/question/detail/" + id;  // redirect 용도 확인.
    }
}
