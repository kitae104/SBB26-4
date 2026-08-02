package inhatc.aic.sbb.question.controller;

import inhatc.aic.sbb.answer.dto.AnswerDto;
import inhatc.aic.sbb.member.entity.Member;
import inhatc.aic.sbb.member.service.MemberService;
import inhatc.aic.sbb.question.dto.QuestionDto;
import inhatc.aic.sbb.question.entity.Question;
import inhatc.aic.sbb.question.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.Binding;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

//    @GetMapping("/list")
//    public String questionList(Model model){
//
//        List<Question> questionList = questionService.getQuestionList();
//        log.info("questionList = {}", questionList);
//
//        model.addAttribute("questionList", questionList);
//        return "question/list";
//    }

    @GetMapping("/list")
    public String questionList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model){

        Page<Question> paging = questionService.getQuestionList(page);
        log.info("paging = {}", paging);

        model.addAttribute("paging", paging);
        return "question/list";
    }

    @GetMapping("/detail/{id}")
    public String questionDetail(@PathVariable Long id,
                                 AnswerDto answerDto,
                                 Model model){
        log.info("id = {}", id);
        Question question = questionService.getQuestion(id);
        log.info("question = {}", question);
        model.addAttribute("question", question);
        model.addAttribute("answerDto", answerDto);

        return "question/detail";
    }

    @GetMapping("/create")
    public String questionCreateForm(QuestionDto questionDto, Model model){
        model.addAttribute("questionDto", questionDto);
        return "question/inputForm";
    }

    @PostMapping("/create")
    //  public String createQuestion(@RequestParam(value = "subject") String subject,
    //                               @RequestParam(value = "content") String content) {
    //  public String questionCreate(QuestionDto questionDto){
    public String questionCreate(@Valid QuestionDto questionDto,
                                 BindingResult bindingResult,
                                 Principal principal // 현재 로그인한 사용자 정보
                                 ){
        log.info("questionDto = {}", questionDto);
        if(bindingResult.hasErrors()){
            return "question/inputForm";
        }

        log.info("=====================> 로그인 사용자 : {}", principal.getName());

        Member member = memberService.getMember(principal.getName());

        questionService.questionCreate(questionDto, member);
        return "redirect:/question/list";
    }

    // 질문 수정을 위해 입력 폼으로 이동(Model을 이용하는 경우와 비교)
    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능, 사용하기전 Principal 추가 및 @EnableMethodSecurity 추가
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id, QuestionDto questionDto, Principal principal, Model model){
        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionDto.setSubject(question.getSubject());
        questionDto.setContent(question.getContent());
        model.addAttribute("questionDto", questionDto);

        return "question/inputForm";
    }

    @PreAuthorize("isAuthenticated()") // 로그인한 사용자만 접근 가능, 사용하기전 Principal 추가 및 @EnableMethodSecurity 추가
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
                         @Valid QuestionDto questionDto,
                         BindingResult bindingResult,
                         Principal principal){

        if(bindingResult.hasErrors()){
            return "question/inputForm";
        }

        Question question = questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        questionService.questionModify(question, questionDto);

        return "redirect:/question/detail/" + id;
    }
}
