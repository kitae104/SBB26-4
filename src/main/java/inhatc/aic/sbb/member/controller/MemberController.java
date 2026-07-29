package inhatc.aic.sbb.member.controller;

import inhatc.aic.sbb.member.dto.MemberDto;
import inhatc.aic.sbb.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/signup")
    public String signup(MemberDto memberDto) {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberDto memberDto,
                         BindingResult bindingResult,
                         Model model) {

        if(bindingResult.hasErrors()) {
            return "member/signup";
        }

        // 패스워드 일치 여부 확인
        if(!memberDto.getPassword1().equals(memberDto.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
            return "member/signup";
        }

        try {
            memberService.createMember(memberDto);
        } catch(DataIntegrityViolationException e){
            log.info("=============================회원가입 실패: 이미 등록된 사용자입니다.");
            model.addAttribute("errorMessage","이미 등록된 사용자입니다.");
            return "member/signup";
        } catch(Exception e){
            log.info("=============================회원가입 실패: " + e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "member/signup";
        }

        // 회원가입 처리 로직 추가 필요
        return "redirect:/";
    }
}
