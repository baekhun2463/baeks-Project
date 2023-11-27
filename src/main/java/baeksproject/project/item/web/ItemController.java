package baeksproject.project.item.web;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import baeksproject.project.item.service.ItemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public String items(@ModelAttribute("itemSearch") ItemSearchCond itemSearch, Model model) {
        List<Item> items = itemService.findItems(itemSearch);
        model.addAttribute("items", items);
        return "item/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model, HttpServletRequest request) {
        Item item = itemService.findById(itemId).get();
        model.addAttribute("item", item);

        HttpSession session = request.getSession(false);
        if (session != null) {
            Long memberId = (Long) session.getAttribute("memberId");
            model.addAttribute("currentMemberId", memberId);
        }
        return "item/item";
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Long memberId = (Long) session.getAttribute("memberId");
            if (memberId != null) {
                model.addAttribute("memberId", memberId);
            }
        }
        return "item/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, @RequestParam Long memberId , @RequestParam("image") MultipartFile imageFile, RedirectAttributes redirectAttributes) {
        Item savedItem = itemService.saveItemWithMember(memberId, item, imageFile);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId).get();
        model.addAttribute("item", item);
        return "item/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @ModelAttribute ItemUpdateDto updateParam,
                       @RequestParam("image") MultipartFile imageFile,
                       HttpServletRequest request,
                       RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            // 세션 없음, 로그인 페이지로 리디렉트 등의 처리
            return "redirect:/login";
        }

        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId == null) {
            // 세션에 memberId 없음, 에러 처리
            //return "error/unauthorized";
            return "redirect:/login";
        }

        itemService.update(itemId, updateParam, imageFile, memberId);
        redirectAttributes.addAttribute("itemId", itemId);
        return "redirect:/items/{itemId}";
    }

    @PostMapping("/delete/{itemId}")
    public String deleteItem(@PathVariable Long itemId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("memberId") == null) {
            return "redirect:/login"; // 로그인 페이지로 리디렉트
        }

        Long currentMemberId = (Long) session.getAttribute("memberId");
        try {
            itemService.deleteItem(itemId, currentMemberId);
        } catch (RuntimeException e) {
            // 적절한 예외 처리 (예: 접근 거부 메시지 표시)
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/items";
        }

        redirectAttributes.addFlashAttribute("message", "Item deleted successfully.");
        return "redirect:/items";
    }
}
