package baeksproject.project.item.service;

import baeksproject.project.item.domain.Item;
import baeksproject.project.item.repository.ItemRepository;
import baeksproject.project.item.repository.ItemSearchCond;
import baeksproject.project.item.repository.ItemUpdateDto;
import baeksproject.project.login.domain.member.Member;
import baeksproject.project.login.repository.JpaMemberRepositoryV1;
import baeksproject.project.login.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ItemServiceV1 implements ItemService {

    private final ItemRepository itemRepository;
    private final MemberRespository memberRespository;


    public Item saveItemWithMember(Long memberId, Item item, MultipartFile imageFile) {
        Member member = memberRespository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));
        item.setMember(member);

        String imagePath = saveImageFile(imageFile);
        if (imagePath != null) {
            String mimeType = determineMimeType(imagePath);
            String base64EncodedImage = encodeImageToBase64(imagePath);

            item.setImagePath(base64EncodedImage);
            item.setMimeType(mimeType); // MIME 타입 저장
        }
        return itemRepository.save(item);
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam, MultipartFile imageFile, Long currentMemberId) {
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        // 소유권 확인
        if (!existingItem.getMember().getId().equals(currentMemberId)) {
            throw new RuntimeException("Unauthorized access");
        }

        if (!imageFile.isEmpty()) {
            // 새 이미지 파일 저장 및 Base64 인코딩과 MIME 타입 업데이트
            String imagePath = saveImageFile(imageFile);
            if (imagePath != null) {
                String mimeType = determineMimeType(imagePath);
                String base64EncodedImage = encodeImageToBase64(imagePath);

                updateParam.setImagePath(base64EncodedImage);
                updateParam.setMimeType(mimeType); // 새로운 MIME 타입 설정
            }
        } else {
            // 새 이미지 파일이 없으면, 기존 이미지 정보 유지
            updateParam.setImagePath(existingItem.getImagePath());
            updateParam.setMimeType(existingItem.getMimeType()); // 기존 MIME 타입 유지
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

    private String saveImageFile(MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            return null;
        }

        try {
            String directoryPath = "/home/baek/Downloads/project/src/main/resources/static/images";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = imageFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;

            // 여기에서 이미지 크기 조정 및 저장
            return resizeAndSaveImage(imageFile, directoryPath, storedFileName);
        } catch (IOException e) {
            log.error("Error processing image file: {}", e.getMessage(), e);
            return null;
        }
    }

    private String encodeImageToBase64(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(path);
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            log.error("Error encoding image to Base64: {}", imagePath, e);
            return null;
        }
    }

    private String determineMimeType(String imagePath) {
        try {
            Path path = Paths.get(imagePath);
            return Files.probeContentType(path);
        } catch (IOException e) {
            log.error("Error determining MIME type: {}", imagePath, e);
            return null;
        }
    }

    /**
     * base64 인코딩 하기 전 사이즈를 줄이는 함수 (사이즈가 크면 타임리프에서 출력 X)
     */
    private String resizeAndSaveImage(MultipartFile imageFile, String directoryPath, String storedFileName) throws IOException {
        // 이미지 파일 읽기
        BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

        // 이미지 크기 조정 (예: 너비 500, 높이 500)
        int targetWidth = 500;
        int targetHeight = 500;
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        // 조정된 이미지 저장
        Path filePath = Paths.get(directoryPath, storedFileName);
        ImageIO.write(resizedImage, "jpg", filePath.toFile()); // "jpg"는 이미지 형식에 따라 변경

        return filePath.toString();
    }


}