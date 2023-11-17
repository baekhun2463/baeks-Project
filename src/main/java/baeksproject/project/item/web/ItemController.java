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
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemService.findById(itemId).get();
        model.addAttribute("item", item);
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
    public String addItem(@ModelAttribute Item item,@RequestParam Long memberId ,RedirectAttributes redirectAttributes) {
        Item savedItem = itemService.saveItemWithMember(memberId, item);
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
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemUpdateDto updateParam) {
        itemService.update(itemId, updateParam);
        return "redirect:/items/{itemId}";
    }
}
