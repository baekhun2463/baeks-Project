package baeksproject.project.item.web;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import baeksproject.project.item.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService; // 아이템 서비스 의존성 주입

    @GetMapping
    public String items(@ModelAttribute("itemSearch") ItemSearchCond itemSearch,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "5") int size,
                        Model model) {
        // 아이템 목록 조회
        Page<Item> itemPage = itemService.findItems(itemSearch, page, size); // 페이징 처리된 아이템 목록 가져오기

        model.addAttribute("items", itemPage.getContent()); // 페이지에 해당하는 아이템 목록
        model.addAttribute("page", itemPage); // 페이지 정보 (Page 객체)

        return "item/items"; // 아이템 목록 뷰 반환
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model, HttpServletRequest request) {
        // 아이템 상세 조회
        Item item = itemService.findById(itemId).get(); // 아이템 ID로 아이템 조회
        model.addAttribute("item", item); // 모델에 아이템 추가

        HttpSession session = request.getSession(false); // 현재 세션 가져오기
        if (session != null) {
            Long memberId = (Long) session.getAttribute("memberId"); // 세션에서 회원 ID 가져오기
            model.addAttribute("currentMemberId", memberId); // 모델에 회원 ID 추가
        }
        return "item/item"; // 아이템 상세 뷰 반환
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpServletRequest request) {
        // 아이템 추가 폼
        HttpSession session = request.getSession(false); // 현재 세션 가져오기
        if (session != null) {
            Long memberId = (Long) session.getAttribute("memberId"); // 세션에서 회원 ID 가져오기
            if (memberId != null) {
                model.addAttribute("memberId", memberId); // 모델에 회원 ID 추가
            }
        }
        return "item/addForm"; // 아이템 추가 폼 뷰 반환
    }

    @PostMapping("/add")
    public String addItem(@Valid @ModelAttribute Item item, @RequestParam Long memberId, @RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        // 아이템 추가 처리
        Item savedItem = itemService.saveItemWithMember(memberId, item, imageFile); // 아이템 저장

        redirectAttributes.addAttribute("itemId", savedItem.getId()); // 리다이렉트 속성에 아이템 ID 추가
        redirectAttributes.addAttribute("status", true); // 리다이렉트 속성에 상태 추가
        return "redirect:/items/{itemId}"; // 아이템 상세 페이지로 리다이렉트
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        // 아이템 수정 폼
        Item item = itemService.findById(itemId).get(); // 아이템 ID로 아이템 조회
        model.addAttribute("item", item); // 모델에 아이템 추가
        return "item/editForm"; // 아이템 수정 폼 뷰 반환
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemUpdateDto updateParam, @RequestParam("image") MultipartFile imageFile, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 아이템 수정 처리
        HttpSession session = request.getSession(false); // 현재 세션 가져오기
        if (session == null) {
            // 세션 없음, 로그인 페이지로 리디렉트
            return "redirect:/login";
        }

        Long memberId = (Long) session.getAttribute("memberId"); // 세션에서 회원 ID 가져오기
        if (memberId == null) {
            // 세션에 회원 ID 없음, 로그인 페이지로 리디렉트
            return "redirect:/login";
        }

        itemService.update(itemId, updateParam, imageFile, memberId); // 아이템 수정
        redirectAttributes.addAttribute("itemId", itemId); // 리다이렉트 속성에 아이템 ID 추가
        return "redirect:/items/{itemId}"; // 아이템 상세 페이지로 리다이렉트
    }

    @PostMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        // 아이템 삭제 처리
        HttpSession session = request.getSession(false); // 현재 세션 가져오기
        if (session == null || session.getAttribute("memberId") == null) {
            // 세션 없거나 회원 ID 없음, 로그인 페이지로 리디렉트
            return "redirect:/login";
        }

        Long currentMemberId = (Long) session.getAttribute("memberId"); // 세션에서 회원 ID 가져오기
        try {
            itemService.deleteItem(itemId, currentMemberId); // 아이템 삭제
        } catch (RuntimeException e) {
            // 예외 처리, 에러 메시지 추가
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/items";
        }

        redirectAttributes.addFlashAttribute("message", "Item deleted successfully."); // 삭제 성공 메시지 추가
        return "redirect:/items"; // 아이템 목록 페이지로 리다이렉트
    }
}