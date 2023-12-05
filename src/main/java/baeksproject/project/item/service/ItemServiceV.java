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

    private final ItemRepository itemRepository; // 아이템 리포지토리
    private final MemberRespository memberRespository; // 회원 리포지토리

    public Page<Item> findItems(ItemSearchCond itemSearch, int page, int size) {
        // 아이템 목록 조회
        return itemRepository.findPaginatedItems(itemSearch, page, size); // 검색 조건과 페이지 정보를 사용하여 페이징 처리된 아이템 조회
    }

    public Item saveItemWithMember(Long memberId, Item item, MultipartFile imageFile) {
        // 아이템 저장
        Member member = memberRespository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found")); // 회원 ID로 회원 조회, 없으면 예외 발생
        item.setMember(member); // 아이템에 회원 정보 설정

        if (!imageFile.isEmpty()) {
            // 이미지 파일 처리
            try {
                String mimeType = imageFile.getContentType(); // MIME 타입 추출
                String base64EncodedImage = resizeAndSaveImage(imageFile); // 이미지 리사이징 및 base64 인코딩

                item.setImagePath(base64EncodedImage); // 인코딩된 이미지 경로 설정
                item.setMimeType(mimeType); // MIME 타입 설정
            } catch (IOException e) {
                log.error("Error resizing and encoding image: {}", e.getMessage(), e); // 이미지 처리 중 오류 로깅
            }
        }

        return itemRepository.save(item); // 아이템 저장
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam, MultipartFile imageFile, Long currentMemberId) {
        // 아이템 수정
        Item existingItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found")); // 아이템 ID로 아이템 조회, 없으면 예외 발생
        if (!existingItem.getMember().getId().equals(currentMemberId)) {
            throw new RuntimeException("Unauthorized access"); // 권한 없음 예외 처리
        }

        // 이미지 파일 처리
        if (!imageFile.isEmpty()) {
            try {
                String mimeType = imageFile.getContentType(); // MIME 타입 추출
                String base64EncodedImage = resizeAndSaveImage(imageFile); // 이미지 리사이징 및 base64 인코딩

                updateParam.setImagePath(base64EncodedImage); // 인코딩된 이미지 경로 설정
                updateParam.setMimeType(mimeType); // MIME 타입 설정
            } catch (IOException e) {
                log.error("Error resizing and encoding image: {}", e.getMessage(), e); // 이미지 처리 중 오류 로깅
            }
        } else {
            // 이미지 파일이 없는 경우 기존 이미지 정보 사용
            updateParam.setImagePath(existingItem.getImagePath());
            updateParam.setMimeType(existingItem.getMimeType());
        }

        itemRepository.update(itemId, updateParam); // 아이템 수정
    }

    @Override
    public Optional<Item> findById(Long id) {
        // 아이템 ID로 조회
        return itemRepository.findById(id); // 아이템 ID로 아이템 조회
    }

    @Override
    public List<Item> findItems(ItemSearchCond cond) {
        // 검색 조건에 따른 아이템 목록 조회
        return itemRepository.findAll(cond); // 검색 조건으로 아이템 목록 조회
    }

    public void deleteItem(Long itemId, Long currentMemberId) {
        // 아이템 삭제
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found")); // 아이템 ID로 아이템 조회, 없으면 예외 발생

        if (!item.getMember().getId().equals(currentMemberId)) {
            throw new RuntimeException("Unauthorized access"); // 권한 없음 예외 처리
        }

        itemRepository.deleteById(item.getId()); // 아이템 삭제
    }

    /**
     * base64 인코딩 하기 전 사이즈를 줄이는 함수 (사이즈가 크면 타임리프에서 출력 X)
     */
    private String resizeAndSaveImage(MultipartFile imageFile) throws IOException {
        // 이미지 파일을 BufferedImage로 읽어옴
        BufferedImage originalImage = ImageIO.read(imageFile.getInputStream());

        // 목표 이미지 크기 설정
        int targetWidth = 500;
        int targetHeight = 500;

        // 새로운 BufferedImage 인스턴스 생성하여 크기 조정된 이미지를 저장
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null); // 원본 이미지를 새 크기에 맞게 그림
        graphics2D.dispose(); // 리소스 해제

        // ByteArrayOutputStream을 사용하여 이미지를 바이트 배열로 변환
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos); // BufferedImage를 jpg 형식으로 ByteArrayOutputStream에 쓰기
        byte[] imageBytes = baos.toByteArray(); // ByteArrayOutputStream을 바이트 배열로 변환

        // 바이트 배열을 Base64 인코딩된 문자열로 변환하여 반환
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}