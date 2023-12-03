package baeksproject.project.item.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceV implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberRespository memberRespository;

    public Page<Item> findItems(ItemSearchCond itemSearch, int page, int size) {
        // 검색 조건과 페이지 정보를 사용하여 아이템 조회
        return itemRepository.findPaginatedItems(itemSearch, page, size);
    }

    public Item saveItemWithMember(Long memberId, Item item, MultipartFile imageFile) {
        Member member = memberRespository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        item.setMember(member);

        if (!imageFile.isEmpty()) {
            try {
                String mimeType = imageFile.getContentType(); // MIME 타입 추출
                String base64EncodedImage = resizeAndSaveImage(imageFile);

                item.setImagePath(base64EncodedImage);
                item.setMimeType(mimeType); // 추출한 MIME 타입 저장
            } catch (IOException e) {
                log.error("Error resizing and encoding image: {}", e.getMessage(), e);
            }
        }

        return itemRepository.save(item);
    }


    @Override
    public void update(Long itemId, ItemUpdateDto updateParam, MultipartFile imageFile, Long currentMemberId) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        if (!existingItem.getMember().getId().equals(currentMemberId)) {
            throw new RuntimeException("Unauthorized access");
        }

        if (!imageFile.isEmpty()) {
            try {
                String mimeType = imageFile.getContentType(); // MIME 타입 추출
                String base64EncodedImage = resizeAndSaveImage(imageFile);

                updateParam.setImagePath(base64EncodedImage);
                updateParam.setMimeType(mimeType); // 새로운 MIME 타입 설정
            } catch (IOException e) {
                log.error("Error resizing and encoding image: {}", e.getMessage(), e);
            }
        } else {
            updateParam.setImagePath(existingItem.getImagePath());
            updateParam.setMimeType(existingItem.getMimeType());
        }

        itemRepository.update(itemId, updateParam);
    }


    @Override
    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    @Override
    public List<Item> findItems(ItemSearchCond cond) {
        return itemRepository.findAll(cond);
    }

    public void deleteItem(Long itemId, Long currentMemberId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (!item.getMember().getId().equals(currentMemberId)) {
            throw new RuntimeException("Unauthorized access");
        }

        itemRepository.deleteById(item.getId());
    }


    /**
     * base64 인코딩 하기 전 사이즈를 줄이는 함수 (사이즈가 크면 타임리프에서 출력 X)
     */
    private String resizeAndSaveImage(MultipartFile imageFile) throws IOException {
        BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

        int targetWidth = 500;
        int targetHeight = 500;
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        byte[] imageBytes = baos.toByteArray();

        return Base64.getEncoder().encodeToString(imageBytes);
    }
}